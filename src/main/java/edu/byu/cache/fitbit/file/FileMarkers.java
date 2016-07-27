package edu.byu.cache.fitbit.file;

/**
 * Created by cstaheli on 7/20/2016.
 */
public class FileMarkers
{
    private final int start;
    private int end;

    public FileMarkers(int start, int end)
    {
        this.start = start;
        this.end = end;
    }

    public FileMarkers(int start)
    {
        this(start, start);
    }

    public void end(int end)
    {
        this.end = end;
    }

    public int start()
    {
        return start;
    }

    public int end()
    {
        return end;
    }
}
