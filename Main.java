

// Main.java — Students version
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};
    static int[][][] marketData = new int[MONTHS][DAYS][COMMS];

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {

        for (int monthIndex = 0; monthIndex < MONTHS; monthIndex++) {

            Scanner scanner = null;
            String filename = months[monthIndex] + ".txt";

            try {
                scanner = new Scanner(Paths.get(filename));


                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                int dayIndex = 0;
                int commIndex = 0;

                while (scanner.hasNextLine() && dayIndex < DAYS) {

                    String line = scanner.nextLine();

                    String[] data = line.split(",");


                    if (data.length >= 3) {

                        int profit = Integer.parseInt(data[2].trim());

                        marketData[monthIndex][dayIndex][commIndex] = profit;
                    }

                    commIndex++;
                    if (commIndex >= COMMS) {
                        commIndex = 0;
                        dayIndex++;
                    }
                }

            } catch (IOException e) {
                System.out.println("ERROR: Dosya okuma hatası: " + filename);
                e.printStackTrace();
            }
            finally {
                if (scanner != null) {
                    scanner.close();
                }
            }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        return "DUMMY"; 
    }

    public static int totalProfitOnDay(int month, int day) {
        return 1234;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) { 
        return 1234; 
    }
    
    public static String bestMonthForCommodity(String comm) { 
        return "DUMMY"; 
    }

    public static int consecutiveLossDays(String comm) { 
        return 1234; 
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        return 1234; 
    }

    public static int biggestDailySwing(int month) { 
        return 1234; 
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
        return "DUMMY is better by 1234"; 
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
        System.out.println(marketData[2][0][2]);
        // AY GÜN KAR    }
}}