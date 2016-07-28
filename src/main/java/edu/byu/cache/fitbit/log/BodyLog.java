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
                this.body.put(body.getDate(), body);
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

    public double getWeight(Date date)
    {
        if (body.get(date) != null)
            return body.get(date)
                    .getWeight();
        return 0;
    }

    public double getBmi(Date date)
    {
        if (body.get(date) != null)
            return body.get(date)
                    .getBmi();
        return 0;
    }

    public double getFatPercentage(Date date)
    {
        if (body.get(date) != null)
            return body.get(date)
                    .getFatPercentage();
        return 0;
    }

    public double getRunning(Date date)
    {
        if (body.get(date) != null)
            return body.get(date)
                    .getRunning();
        return 0;
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
