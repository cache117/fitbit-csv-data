package edu.byu.cache.fitbit.log;

import java.util.List;

/**
 * Created by cstaheli on 7/27/2016.
 */
public abstract class FitbitLog
{
    protected static String linesToString(List<String> lines)
    {
        StringBuilder builder = new StringBuilder();
        for (String line : lines)
        {
            builder.append(line)
                    .append("\n");
        }
        return builder.toString();
    }

    protected static String linesToStringStripTitle(List<String> lines)
    {
        return linesToString(lines.subList(1, lines.size()));
    }
}

