package edu.byu.cache.fitbit.main;

import edu.byu.cache.fitbit.file.FitbitCSVFile;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cstaheli on 7/28/2016.
 */
public class Main
{
    public static void main(String... args)
    {
        try
        {
            if (args.length > 2)
            {
                FitbitCSVFile file = new FitbitCSVFile(args[0]);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date start = format.parse(args[1]);
                Date end = format.parse(args[2]);

                String json = file.toJson(FitbitCSVFile.JsonType.HEALTHY_ME, start, end);
                String outputFile = "import.json";
                if (args.length > 3)
                    outputFile = args[3] + outputFile;
                PrintWriter writer = new PrintWriter(outputFile);
                writer.println(json);
                writer.close();
                System.out.println("Healthy-Me compatible import.json file created");
            } else
            {
                System.out.println("USAGE: java -jar <jar-file> <fitbit-csv-file> <start-date: yyyy-MM-dd> <end-date: yyyy-MM-dd> [<json-output-directory>]");
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("Invalid file(s)");
        } catch (ParseException e)
        {
            System.out.println("Invalid date format. Please use \"yyyy-MM-dd\"");
        }
    }
}
