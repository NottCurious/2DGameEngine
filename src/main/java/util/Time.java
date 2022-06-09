package util;

public class Time {
    // Static variables are initialized at application startup.
    public static float timeStarted = System.nanoTime();

    public static float getTime() { return (float)((System.nanoTime() - Time.timeStarted) * 1E-9); }
}
