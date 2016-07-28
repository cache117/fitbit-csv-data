package edu.byu.cache.fitbit.file;

import com.google.common.io.Files;
import edu.byu.cache.fitbit.json.HealthyMePrinter;
import edu.byu.cache.fitbit.log.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by cstaheli on 7/20/2016.
 */
public class FitbitCSVFile
{
    private final File file;
    private FileMarkers bodyMarkers;
    private FileMarkers foodsMarkers;
    private FileMarkers activitiesMarkers;
    private FileMarkers sleepMarkers;
    private List<FileMarkers> foodLogsMarkers;
    private Map<Section, List<String>> normalSections;
    private List<List<String>> foodLogsSections;
    private BodyLog body;
    private CaloriesLog foods;
    private ActivitiesLog activities;
    private SleepLog sleep;
    private FoodLogs foodLogs;

    public FitbitCSVFile(String filePath) throws FileNotFoundException
    {
        this.file = new File(filePath);
        this.parseFile(this.file);
        this.compileSections();
        this.generateObjects();
    }

    private static String linesToString(List<String> lines)
    {
        StringBuilder builder = new StringBuilder();
        for (String line : lines)
        {
            builder.append(line)
                    .append("\n");
        }
        return builder.toString();
    }

    private static String linesToStringStripTitle(List<String> lines)
    {
        return linesToString(lines.subList(1, lines.size()));
    }

    public static void main(String... args)
    {
        try
        {
            FitbitCSVFile file = new FitbitCSVFile("C:\\Users\\cstaheli\\IdeaProjects\\SHealth-to-FitBit\\src\\main\\fitbit_export_20160712.csv");
            Calendar calendar = Calendar.getInstance();
            calendar.set(2016, Calendar.JULY, 17);
            Date start = calendar.getTime();
            calendar.set(2016, Calendar.JULY, 18);
            Date end = calendar.getTime();
            String Json = file.toJson(JsonType.HEALTHY_ME, start, end);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private void parseFile(File file) throws FileNotFoundException
    {
        /*
        Format of file
        Body
        ...
        Foods
        ...
        Activities
        ...
        Sleep
        ...
        Food Log yyyymmdd
        ...
        Food Log yyyymmdd
        ...
        etc
         */
        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file))))
        {
            int lineNumber = -1;
            int sectionStart = -1;
            int sectionEnd = -1;
            foodLogsMarkers = new ArrayList<>();
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                ++lineNumber;
                switch (line)
                {
                    case "Body":
                        sectionStart = lineNumber;
                        break;
                    case "Foods":
                        bodyMarkers = new FileMarkers(sectionStart, sectionEnd);
                        sectionStart = lineNumber;
                        break;
                    case "Activities":
                        foodsMarkers = new FileMarkers(sectionStart, sectionEnd);
                        sectionStart = lineNumber;
                        break;
                    case "Sleep":
                        activitiesMarkers = new FileMarkers(sectionStart, sectionEnd);
                        sectionStart = lineNumber;
                        break;
                    case "":
                        sectionEnd = lineNumber;
                        break;
                    default:
                        if (Pattern.compile("Food Log \\d{8}$")
                                .matcher(line)
                                .matches())
                        {
                            if (sleepMarkers == null)
                            {
                                sleepMarkers = new FileMarkers(sectionStart, sectionEnd);
                            }
                            foodLogsMarkers.add(new FileMarkers(sectionStart, sectionEnd));
                            sectionStart = lineNumber;
                        }
                }
            }
        }
    }

    private void compileSections()
    {
        normalSections = new HashMap<>();
        normalSections.put(Section.BODY, getLines(bodyMarkers));
        normalSections.put(Section.FOODS, getLines(foodsMarkers));
        normalSections.put(Section.ACTIVITIES, getLines(activitiesMarkers));
        normalSections.put(Section.SLEEP, getLines(sleepMarkers));
        foodLogsSections = new ArrayList<>();
        foodLogsSections.addAll(foodLogsMarkers.stream()
                .map(this::getLines)
                .collect(Collectors.toList()));
    }

    private void generateObjects()
    {
        body = getBodyLog();
        foods = getFoodsLog();
        activities = getActivitiesLog();
        sleep = getSleepLog();
        foodLogs = null;
        //put together food logs.
    }

    private SleepLog getSleepLog()
    {
        return new SleepLog(getSection(Section.SLEEP));
    }

    private ActivitiesLog getActivitiesLog()
    {
        return new ActivitiesLog(getSection(Section.ACTIVITIES));
    }

    private CaloriesLog getFoodsLog()
    {
        return new CaloriesLog(getSection(Section.FOODS));
    }

    private BodyLog getBodyLog()
    {
        return new BodyLog(getSection(Section.BODY));
    }

    private List<String> getLines()
    {
        try
        {
            return Files.readLines(this.file, Charset.defaultCharset());
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private List<String> getLines(int startIndex, int endIndex)
    {
        return getLines().subList(startIndex, endIndex);
    }

    private List<String> getLines(FileMarkers markers)
    {
        return getLines(markers.start(), markers.end());
    }

    private List<String> getSection(Section section)
    {
        return normalSections.get(section);
    }

    public String toJson(JsonType type, Date start, Date end)
    {
        switch (type)
        {
            case HEALTHY_ME:
                return HealthyMePrinter.printJson(activities, sleep, foodLogs, start, end);
            default:
                throw new RuntimeException("Not implemented");
        }
    }

    private enum Section
    {
        BODY, FOODS, ACTIVITIES, SLEEP, FOOD_LOGS
    }

    public enum JsonType
    {
        HEALTHY_ME
    }
}
