# Clock Application

This Java program demonstrates a simple multithreaded clock application. It prints the current time to the console while continuously updating in the background, and listens for user input to gracefully terminate.

## Features
- Continuously displays the current time and date in the "HH:mm:ss dd-MM-yyyy" format.
- Uses multithreading to ensure that time updates and display happen concurrently.
- Utilizes ReentrantLock to ensure proper synchronization.
- Allows the user to terminate the application by pressing (inputting) the Enter key.
- Utilizes thread priorities to ensure the time display is prioritized.

- startClock() method starts all threads:
-timeUpdater thread updates the currentTime value using getCurrentTime() method.
-timePrinter thread retrieves the currentTime value and prints it to the console.
-inputListener thread listens for input from the user in the console, terminating the program if it detects empty input (Enter button has been pressed)


## Requirements
- Java 8 or later.

## Example Output Screenshot
![Clock Screenshot](images/output.png)

