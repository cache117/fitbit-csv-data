package edu.byu.cache.fitbit.log;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class SleepLog extends FitbitLog
{
    private final Map<Date, Sleep> sleep;

    public SleepLog(List<String> log)
    {
        this(linesToStringStripTitle(log));
    }

    private SleepLog()
    {
        sleep = new HashMap<>();
    }

    private SleepLog(String log)
    {
        this();
        try (CSVParser parser = CSVParser.parse(log, CSVFormat.EXCEL.withHeader()))
        {
            for (CSVRecord record : parser)
            {
                Sleep sleep = new Sleep(record);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private class Sleep extends LogLine
    {
        private int minutesAsleep, minutesAwake, numberOfAwakenings, timeInBed;

        public Sleep(CSVRecord record)
        {
            super(record);
        }

        @Override
        protected void parseCSVRecord(CSVRecord record)
        {
            minutesAsleep = parseInt(record.get("Minutes Asleep"));
            minutesAwake = parseInt(record.get("Minutes Awake"));
            numberOfAwakenings = parseInt(record.get("Number of Awakenings"));
            timeInBed = parseInt(record.get("Time in Bed"));
        }

        public int getMinutesAsleep()
        {
            return minutesAsleep;
        }

        public int getMinutesAwake()
        {
            return minutesAwake;
        }

        public int getNumberOfAwakenings()
        {
            return numberOfAwakenings;
        }

        public int getTimeInBed()
        {
            return timeInBed;
        }
    }
}
