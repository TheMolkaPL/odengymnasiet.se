package se.odengymnasiet;

/**
 * Prevention from application self shutting down. Just make this thread sleep
 * forever...
 */
public class SleepForeverThread extends Thread {

    public SleepForeverThread() {
        super("Sleep Forever Thread");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
