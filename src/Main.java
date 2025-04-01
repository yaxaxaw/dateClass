import java.util.*;

class Date implements Comparable<Date> {
    private int day, month, year;

    // Constructor
    public Date(int day, int month, int year) {
        if (isValidDate(day, month, year)) {
            this.day = day;
            this.month = month;
            this.year = year;
        } else {
            throw new IllegalArgumentException("Invalid date!");
        }
    }

    // Validate date
    private boolean isValidDate(int day, int month, int year) {
        if (month < 1 || month > 12 || day < 1) return false;
        int[] daysInMonth = {31, (isLeapYear(year) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return day <= daysInMonth[month - 1];
    }

    // Check for leap year
    private boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 100 != 0) {
            return true;
        } else {
            return year % 400 == 0;
        }
    }

    // Update date
    public void updateDate(int day, int month, int year) {
        if (isValidDate(day, month, year)) {
            this.day = day;
            this.month = month;
            this.year = year;
        } else {
            throw new IllegalArgumentException("Invalid date!");
        }
    }

    // Get day of week
    public String getDayOfWeek() {
        int m = month;
        int y = year;
        if (m < 3) {
            m += 12;
            y--;
        }

        int k = y % 100;
        int j = y / 100;
        int h = (day + 13*(m + 1)/5 + k + k/4 + j/4 + 5*j) % 7;

        String[] days = {"Saturday", "Sunday", "Monday", "Tuesday",
                "Wednesday", "Thursday", "Friday"};
        return days[h];
    }

    // Calculate difference in days between two dates
    public long calculateDifference(Date otherDate) {
        return Math.abs(toDays() - otherDate.toDays());
    }

    // Helper method to convert date to total days
    private int toDays() {
        int totalDays = day;

        // Add days from months in current year
        for (int m = 1; m < month; m++) {
            totalDays += getDaysInMonth(m, year);
        }

        // Add days from previous years
        for (int y = 1; y < year; y++) {
            totalDays += isLeapYear(y) ? 366 : 365;
        }

        return totalDays;
    }

    // Helper method to get days in a month
    private int getDaysInMonth(int month, int year) {
        switch (month) {
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                return isLeapYear(year) ? 29 : 28;
            default:
                return 31;
        }
    }

    // Print date in readable format
    public void printDate() {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        System.out.println(monthNames[month - 1] + " " + day + ", " + year);
    }

    // Implement Comparable for sorting
    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) return Integer.compare(this.year, other.year);
        if (this.month != other.month) return Integer.compare(this.month, other.month);
        return Integer.compare(this.day, other.day);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Date> dates = new ArrayList<>();

        System.out.print("Enter number of dates: ");
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            try {
                System.out.print("Enter day, month, year (separated by space): ");
                int day = scanner.nextInt();
                int month = scanner.nextInt();
                int year = scanner.nextInt();
                dates.add(new Date(day, month, year));
            } catch (Exception e) {
                System.out.println("Invalid input! Try again.");
                i--;
            }
        }

        System.out.println("\nOriginal Dates:");
        for (Date d : dates) d.printDate();

        Collections.sort(dates);
        System.out.println("\nSorted Dates:");
        for (Date d : dates) d.printDate();

        System.out.print("\nEnter first date (day month year): ");
        Date d1 = new Date(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());

        System.out.print("Enter second date (day month year): ");
        Date d2 = new Date(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());

        System.out.println("\nDay of the week: " + d1.getDayOfWeek());
        System.out.println("Difference in days: " + d1.calculateDifference(d2));
    }
}
