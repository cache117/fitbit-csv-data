package edu.byu.cache.fitbit.log;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

import static java.lang.Double.parseDouble;

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
                this.activities.put(activities.getDate(), activities);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public int getCaloriesBurned(Date date)
    {
        if (activities.get(date) != null)
            return activities.get(date)
                    .getCaloriesBurned();
        return 0;
    }

    public int getSteps(Date date)
    {
        if (activities.get(date) != null)
            return activities.get(date)
                    .getSteps();
        return 0;
    }

    public int getFloors(Date date)
    {
        if (activities.get(date) != null)
            return activities.get(date)
                    .getFloors();
        return 0;
    }

    public int getMinutesSedentary(Date date)
    {
        if (activities.get(date) != null)
            return activities.get(date)
                    .getMinutesSedentary();
        return 0;
    }

    public int getMinutesLightlyActive(Date date)
    {
        if (activities.get(date) != null)
            return activities.get(date)
                    .getMinutesLightlyActive();
        return 0;
    }

    public int getMinutesFairlyActive(Date date)
    {
        if (activities.get(date) != null)
            return activities.get(date)
                    .getMinutesFairlyActive();
        return 0;
    }

    public int getMinutesVeryActive(Date date)
    {
        if (activities.get(date) != null)
            return activities.get(date)
                    .getMinutesVeryActive();
        return 0;
    }

    public int getActivityCalories(Date date)
    {
        if (activities.get(date) != null)
            return activities.get(date)
                    .getActivityCalories();
        return 0;
    }

    public double getDistance(Date date)
    {
        if (activities.get(date) != null)
            return activities.get(date)
                    .getDistance();
        return 0;
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
            NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
            try
            {
                caloriesBurned = format.parse(record.get("Calories Burned"))
                        .intValue();
                steps = format.parse(record.get("Steps"))
                        .intValue();
                distance = parseDouble(record.get("Distance"));
                floors = format.parse(record.get("Floors"))
                        .intValue();
                minutesSedentary = format.parse(record.get("Minutes Sedentary"))
                        .intValue();
                minutesLightlyActive = format.parse(record.get("Minutes Lightly Active"))
                        .intValue();
                minutesFairlyActive = format.parse(record.get("Minutes Fairly Active"))
                        .intValue();
                minutesVeryActive = format.parse(record.get("Minutes Very Active"))
                        .intValue();
                activityCalories = format.parse(record.get("Activity Calories"))
                        .intValue();
            } catch (Exception e)
            {

            }
        }

        public int getCaloriesBurned()
        {
            return caloriesBurned;
        }

        public int getSteps()
        {
            return steps;
        }

        public int getFloors()
        {
            return floors;
        }

        public int getMinutesSedentary()
        {
            return minutesSedentary;
        }

        public int getMinutesLightlyActive()
        {
            return minutesLightlyActive;
        }

        public int getMinutesFairlyActive()
        {
            return minutesFairlyActive;
        }

        public int getMinutesVeryActive()
        {
            return minutesVeryActive;
        }

        public int getActivityCalories()
        {
            return activityCalories;
        }

        public double getDistance()
        {
            return distance;
        }
    }
}
