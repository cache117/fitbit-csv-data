package edu.byu.cache.fitbit.log;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class ActivitiesLog extends FitbitLog
{
    private final Map<Date, Activities> activities;

    private ActivitiesLog()
    {
        activities = new HashMap<>();
    }

    public ActivitiesLog(List<String> log)
    {
        this(linesToStringStripTitle(log));
    }

    private ActivitiesLog(String log)
    {
        this();
        try (CSVParser parser = CSVParser.parse(log, CSVFormat.EXCEL.withHeader()))
        {
            for (CSVRecord record : parser)
            {
                Activities activities = new Activities(record);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private class Activities extends LogLine
    {
        private int caloriesBurned, steps, floors, minutesSedentary, minutesLightlyActive, minutesFairlyActive, minutesVeryActive, activityCalories;
        private double distance;

        public Activities(CSVRecord record)
        {
            super(record);
        }

        @Override
        protected void parseCSVRecord(CSVRecord record)
        {
            caloriesBurned = parseInt(record.get("Calories Burned"));
            steps = parseInt(record.get("Steps"));
            distance = parseDouble(record.get("Distance"));
            floors = parseInt(record.get("Floors"));
            minutesSedentary = parseInt(record.get("Minutes Sedentary"));
            minutesLightlyActive = parseInt(record.get("Minutes Lightly Active"));
            minutesFairlyActive = parseInt(record.get("Minutes Fairly Active"));
            minutesVeryActive = parseInt(record.get("Minutes Very Active"));
            activityCalories = parseInt(record.get("Activity Calories"));
        }
    }
}
