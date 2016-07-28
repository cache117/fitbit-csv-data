package edu.byu.cache.fitbit.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.byu.cache.fitbit.log.ActivitiesLog;
import edu.byu.cache.fitbit.log.FoodLogs;
import edu.byu.cache.fitbit.log.SleepLog;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cstaheli on 7/28/2016.
 */
public class HealthyMePrinter
{
    /*
    Format:
        {
          "rows": [
            {
              "date_str": "2016-07-01",
              "sleep_7_or_more_hours": true,
              "activity_minutes": "0",
              "water_5_or_more_cups": true,
              "fruit_veg_4_or_more_servings": true,
              "physical_activity_description": "walking"
            }
          ]
        }
     */
    public static String printJson(ActivitiesLog activitiesLog, SleepLog sleepLog, FoodLogs foodLogs, Date startDate, Date endDate)
    {
        JsonArray rows = new JsonArray();

        LocalDate start = getDate(startDate);
        LocalDate end = getDate(endDate);
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1))
        {
            JsonObject row = new JsonObject();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
            String dateString = date.format(formatter);
            row.addProperty("date_str", dateString);

            boolean sleptLongEnough = (sleepLog.getTimeInBed(getDate(date)) > (7 * 60));
            row.addProperty("sleep_7_or_more_hours", sleptLongEnough);

            int activityMinutes = (activitiesLog.getMinutesVeryActive(getDate(date)) + activitiesLog.getMinutesFairlyActive(getDate(date)));
            row.addProperty("activity_minutes", activityMinutes);

            int steps = activitiesLog.getSteps(getDate(date));
            row.addProperty("steps", steps);

            boolean drankEnoughWater = false; //TODO this will be incorrect
            row.addProperty("water_5_or_more_cups", drankEnoughWater);

            boolean ateEnoughFruitsAndVegies = false; //TODO this will be incorrect
            row.addProperty("fruit_veg_4_or_more_servings", ateEnoughFruitsAndVegies);

            String activityDescription = "Daily Exercise and walking"; //Generic
            row.addProperty("physical_activity_description", activityDescription);

            rows.add(row);
        }
        JsonObject logs = new JsonObject();
        logs.add("rows", rows);
        return logs.toString();
    }

    private static Date getDate(LocalDate date)
    {
        return Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault())));
    }

    private static LocalDate getDate(Date date)
    {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
