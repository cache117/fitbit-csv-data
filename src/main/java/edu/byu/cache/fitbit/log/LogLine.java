package edu.byu.cache.fitbit.log;

import org.apache.commons.csv.CSVRecord;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cstaheli on 7/27/2016.
 */
public abstract class LogLine
{
    private final Date date;

    public LogLine(CSVRecord record)
    {
        date = parseDate(record);
        parseCSVRecord(record);
    }

    protected Date parseDate(CSVRecord record)
    {
        Date date;
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        try
        {
            date = format.parse(record.get("Date"));
        } catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
        return date;
    }

    protected abstract void parseCSVRecord(CSVRecord record);

    public Date getDate()
    {
        return date;
    }
}
