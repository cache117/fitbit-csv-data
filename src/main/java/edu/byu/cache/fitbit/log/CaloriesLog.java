package edu.byu.cache.fitbit.log;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

public class CaloriesLog extends FitbitLog
{
    private final Map<Date, Calories> calories;

    public CaloriesLog(List<String> log)
    {
        this(linesToStringStripTitle(log));
    }

    private CaloriesLog()
    {
        calories = new HashMap<>();
    }

    private CaloriesLog(String log)
    {
        this();
        try (CSVParser parser = CSVParser.parse(log, CSVFormat.EXCEL.withHeader()))
        {
            for (CSVRecord record : parser)
            {
                Calories calories = new Calories(record);
                this.calories.put(calories.getDate(), calories);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public int getCaloriesIn(Date date)
    {
        if (calories.get(date) != null)
            return calories.get(date)
                    .getCaloriesIn();
        return 0;
    }

    private class Calories extends LogLine
    {
        private int caloriesIn;

        public Calories(CSVRecord record)
        {
            super(record);
        }

        @Override
        protected void parseCSVRecord(CSVRecord record)
        {
            NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
            try
            {
                caloriesIn = format.parse(record.get("Calories In"))
                        .intValue();
            } catch (Exception e)
            {

            }
        }

        public int getCaloriesIn()
        {
            return caloriesIn;
        }
    }
}
