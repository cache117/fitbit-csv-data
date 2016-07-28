package edu.byu.cache.fitbit.log;

import org.apache.commons.csv.CSVRecord;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FoodLogs extends FitbitLog
{
    private FoodLogs()
    {

    }

    public FoodLogs(List<String> logs)
    {
        this();
    }

    private class FoodLog extends LogLine
    {
        private final List<String> breakfast;
        private final List<String> lunch;
        private final List<String> dinner;
        private int calories, gramsFat, gramsFiber, gramsCarbs, gramsSodium, gramsProtein, FluidicOuncesWater;

        public FoodLog(CSVRecord record)
        {
            super(record);
            breakfast = new ArrayList<>();
            lunch = new ArrayList<>();
            dinner = new ArrayList<>();
        }

        @Override
        protected Date parseDate(CSVRecord record)
        {
            try
            {
                DateFormat format = new SimpleDateFormat("yyyymmdd", Locale.ENGLISH);
                return format.parse(record.get(0)
                        .substring(9));
            } catch (ParseException e)
            {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void parseCSVRecord(CSVRecord record)
        {

        }
    }
}
