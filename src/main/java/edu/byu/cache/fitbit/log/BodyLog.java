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

public class BodyLog extends FitbitLog
{
    private final Map<Date, Body> body;

    public BodyLog(List<String> log)
    {
        this(linesToStringStripTitle(log));
    }

    private BodyLog(String log)
    {
        this();
        try (CSVParser parser = CSVParser.parse(log, CSVFormat.EXCEL.withHeader()))
        {
            for (CSVRecord record : parser)
            {
                Body body = new Body(record);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private BodyLog()
    {
        body = new HashMap<>();
    }

    private class Body extends LogLine
    {
        private double weight, bmi, fatPercentage, running;

        public Body(CSVRecord record)
        {
            super(record);
        }

        public double getWeight()
        {
            return weight;
        }

        public double getBmi()
        {
            return bmi;
        }

        public double getFatPercentage()
        {
            return fatPercentage;
        }

        public double getRunning()
        {
            return running;
        }

        @Override
        protected void parseCSVRecord(CSVRecord record)
        {
            weight = parseDouble(record.get("Weight"));
            bmi = parseDouble(record.get("BMI"));
            fatPercentage = parseDouble(record.get("Fat"));
            running = parseDouble(record.get("Running"));
        }
    }
}
