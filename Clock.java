import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

class Clock {

    // initialize variable to hold the current date time value for both threads
    private static String currentTime;
    //lock object used to control synchronized thread access to currentTime variable 
    private static final ReentrantLock lock = new ReentrantLock();
    //boolean value to tell us whether the program should continue running or not
    private static volatile boolean running = true;
    
    // method to get the current time in desired format
    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss dd-MM-yyyy").format(new Date());
    }

    // method to start both of the clock related threads
    public static void startClock() {
        // thread which updates time
        Thread timeUpdater = new Thread(() -> {
            while (running) {
                lock.lock(); //aquires lock to ensure this thread has exclusive access to currentTime
                try {
                    currentTime = getCurrentTime();
                }
                catch (Exception e){
                    System.out.println("Exception encountered while retrieving current time: " + e);
                }
                finally {
                    lock.unlock(); //release the lock after thread has updated currentTime value
                }

                try {
                    Thread.sleep(500); // Update time every 1/2 second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); //handle thread interruptions
                }
            }
        });
        
        // thread which prints time to console
        Thread timePrinter = new Thread(() -> {
            while (running) {
                lock.lock(); //aquires lock to ensure this thread has exclusive access to currentTime
                try {
                    if (currentTime != null) {
                    System.out.println("Current Local Time: " + currentTime);
                    }
                }
                catch (Exception e) {
                    System.out.println("Exception encountered while printing current time: " + e);
                }
                finally {
                    lock.unlock(); //release the lock after currentTime value has been read
                }
                
                try {
                    Thread.sleep(1000); // Print time every second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); //handle thread interruptions
                }
            }
        });

        // thread to listen for user input to exit
        Thread inputListener = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Press 'Enter' at any point to terminate the program.");
            while (running) {
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    System.out.println("Terminating Clock Program...");
                    running = false;
                }
            }
            scanner.close();
        });
        
        // assign thread priorities
        timeUpdater.setPriority(Thread.MIN_PRIORITY); // Lower priority for thread that updates time
        timePrinter.setPriority(Thread.MAX_PRIORITY); // Higher priority for thread that prints time
        
        // Start the threads
        timeUpdater.start();
        timePrinter.start();
        inputListener.start();
    }

    // main method
    public static void main(String[] args) {
        try {
            System.out.println("Starting Clock...");
            startClock(); //start clock threads
        }
        catch (Exception e) {
            System.out.println("Exception encountered when starting application: " + e);
        }
        
    }
}