package edu.byu.cache.fitbit.log;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static CaloriesLog create(String log)
    {
        return null;
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
            caloriesIn = Integer.parseInt(record.get("Calories In"));
        }

        public int getCaloriesIn()
        {
            return caloriesIn;
        }
    }
}
