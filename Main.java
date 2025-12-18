
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
            Scanner sc = null;
            String filename = "../" + months[monthIndex] + ".txt";

            try {
                sc = new Scanner(Paths.get(filename));


                if (sc.hasNextLine()) {
                    sc.nextLine();
                }

                int dayIndex = 0;
                int commIndex = 0;

                while (sc.hasNextLine() && dayIndex < DAYS) {

                    String line = sc.nextLine();

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
                if (sc != null) {
                    sc.close();
                }
            }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {

        if (month < 0 || month >= MONTHS) {
            return "INVALID_MONTH";
        }

        int maxProfit = 0;
        String mostProfitableComm = "";

        for (int commIndex = 0; commIndex < COMMS; commIndex++) {
            int currentProfit = 0;

            for (int dayIndex = 0; dayIndex < DAYS; dayIndex++) {
                currentProfit += marketData[month][dayIndex][commIndex];
            }

            if (currentProfit > maxProfit) {
                maxProfit = currentProfit;
                mostProfitableComm = commodities[commIndex];
            }
        }
        return mostProfitableComm + " " + maxProfit;
    }


    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS || day < 0 || day >= DAYS) {
            return -99999;
        }
        int totalProfit = 0;
        for(int commIndex = 0 ; commIndex < COMMS ; commIndex++ ){
            totalProfit += marketData[month][day][commIndex];
        }
        return totalProfit;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        if (from < 1 || from > DAYS || to < 1 || to > DAYS ||  from > to){
            return -99999;
        }
        int commodityIndex = -1;
        for (int commIndex = 0; commIndex < COMMS; commIndex++) {
            if (commodity.equals(commodities[commIndex])) {
                commodityIndex = commIndex;
                break;
            }

        }
        if (commodityIndex == -1) // index bulunamamış
        { return -99999; }
        int totalProfit = 0;
        for(int monthIndex = 0; monthIndex < MONTHS ; monthIndex++){
            for (int dayIndex = from ; dayIndex <= to ; dayIndex++){
                totalProfit += marketData[monthIndex][dayIndex-1][commodityIndex];

            }
        }

        return totalProfit;
    }

    public static int bestDayOfMonth(int month) {
        if(month < 1 || month > MONTHS){
            return -1;
        }
        int bestDay = 0;
        int totalProfit = 0;

        for (int dayIndex = 0 ; dayIndex < DAYS; dayIndex++){
            int currentProfit= 0;
            for (int commIndex = 0 ; commIndex < COMMS ; commIndex++) {
                currentProfit += marketData[month - 1][dayIndex][commIndex];
            }
            if (currentProfit > totalProfit ){
                totalProfit = currentProfit;
                bestDay= dayIndex+1;
            }
        }
        return bestDay;
    }
    
    public static String bestMonthForCommodity(String comm) {

        int commidityIndex = -1;
        int currentProfit = Integer.MIN_VALUE;
        int bestMonth= 0;

        for(int commIndex = 0 ; commIndex < COMMS ; commIndex++){
            if (comm.equals(commodities[commIndex])) {
                commidityIndex = commIndex;
                break;
            }
        }
        if (commidityIndex == -1){
            return "INVALID_COMMODITY";
        }

        for (int monthIndex = 0; monthIndex < MONTHS ; monthIndex++){
            int totalProfit = 0;
            for (int dayIndex = 0; dayIndex < DAYS; dayIndex++) {
                totalProfit += marketData[monthIndex][dayIndex][commidityIndex];
            }
            if(totalProfit > currentProfit){
                currentProfit = totalProfit;
                bestMonth = monthIndex;
            }
        }
        return months[bestMonth];
    }

    public static int consecutiveLossDays(String comm) {
        int commidityIndex = -1;
        int currentStreak = 0;
        int maxStreak = 0;

        for(int commIndex = 0 ; commIndex < COMMS ; commIndex++){
            if (comm.equals(commodities[commIndex])) {
                commidityIndex = commIndex;
                break;
            }
        }
        if (commidityIndex == -1){
            return -1;
        }
        for (int monthIndex = 0; monthIndex < MONTHS ; monthIndex++) {
            for (int dayIndex = 0; dayIndex < DAYS; dayIndex++) {
                if(marketData[monthIndex][dayIndex][commidityIndex] < 0){
                    currentStreak++;
                } else {
                    if (currentStreak > maxStreak) {
                        maxStreak = currentStreak;
                    }
                    currentStreak = 0;
                }
                // else bloğu çalışmayabilirse
                if (currentStreak > maxStreak) {
                    maxStreak = currentStreak;
                }
            }

        }
        return maxStreak;
    }



    public static int daysAboveThreshold(String comm, int threshold) {
        int commoddityIndex = -1;
        int totalProfit = 0;
        int daysCounter = 0;

        for(int commIndex = 0 ; commIndex < COMMS ; commIndex++){//
            if (comm.equals(commodities[commIndex])){
                commoddityIndex = commIndex;
            }
        }
        if (commoddityIndex == -1){
            return -1;
        }

        for (int monthIndex = 0; monthIndex < MONTHS ; monthIndex++) {
            for (int dayIndex = 0; dayIndex < DAYS; dayIndex++) {
                totalProfit =  marketData[monthIndex][dayIndex][commoddityIndex];
                 if(totalProfit > threshold){
                     daysCounter++;
                 }
            }
        }
        return daysCounter;
    }

    public static int biggestDailySwing(int month) {
        if (month < 0 || month >= MONTHS) {
            return -99999;
        }

        int profitOfPreviousDay = 0;
        int profitSwing = 0;
        int maxSwing = 0;
        for (int dayIndex = 0; dayIndex < DAYS; dayIndex++) {
            int profitOfDay = 0;

            for (int commodityIndex = 0; commodityIndex < COMMS; commodityIndex++) {
                profitOfDay += marketData[month][dayIndex][commodityIndex];
            }

            if (dayIndex > 0){
              profitSwing =  Math.abs(profitOfDay - profitOfPreviousDay);

                if (profitSwing > maxSwing){
                    maxSwing = profitSwing;
                }
            }
            profitOfPreviousDay = profitOfDay;
        }
        return maxSwing;


    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
       int c1Index = 0;
       int c2Index = 0;
       boolean isC1Found = false;
       boolean isC2Found = false;
        for (int comm1Index = 0 ; comm1Index < COMMS ; comm1Index++) {

            if (c1.equals(commodities[comm1Index])) {
                c1Index = comm1Index;
                isC1Found = true;
                }
            }

        if (!isC1Found){
            return "INVALID_COMMODITY";
        }

        for (int comm2Index = 0 ; comm2Index < COMMS ; comm2Index++) {
            if (c2.equals(commodities[comm2Index])) {
                c2Index = comm2Index;
                isC2Found = true;
                }
        }
        if(!isC2Found){
            return "INVALID_COMMODITY";
        }


        if (c1Index == c2Index){
            return "EQUAL";
        }

        int c1TotalProfit = 0;
        int c2TotalProfit = 0;
        for (int monthIndex = 0; monthIndex < MONTHS ; monthIndex++){
            for (int dayIndex = 0; dayIndex < DAYS ; dayIndex++){
                c1TotalProfit += marketData[monthIndex][dayIndex][c1Index];
                c2TotalProfit += marketData[monthIndex][dayIndex][c2Index];
            }
        }
        if(c1TotalProfit > c2TotalProfit){
            return c1 + " is better by " + String.valueOf(c1TotalProfit);
        } else {
            return c2 + " is better by " + String.valueOf(c2TotalProfit);

        }
    }
    
    public static String bestWeekOfMonth(int month) {
        int[] week = new int[4];
        int bestWeek = week[0];
        String weekName = "Week-1";

        if (month < 0 || month >= MONTHS) {
            return "INVALID_MONTH";
        }

        int totalProfit = 0;


        for (int dayIndex = 0; dayIndex < MONTHS; dayIndex++) {
            totalProfit += totalProfitOnDay(month, dayIndex);

            int weekIndex = dayIndex / 7;

            if (weekIndex < 4) {
                week[weekIndex] += totalProfit;
            }
        }
            for (int i = 1; i < week.length; i++) {
                if (week[i] > bestWeek) {
                    bestWeek = week[i];
                    weekName = "Week-" + (i + 1);
                }


        }


        return "Best week is " + weekName + " with " + String.valueOf(bestWeek);
    }



    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
        System.out.println("--------------");
        System.out.println(mostProfitableCommodityInMonth(2));
        System.out.println("dddd");
        System.out.println(totalProfitOnDay(1 , 1));
        System.out.println(commodityProfitInRange("Silver",1, 2));
        System.out.println(bestDayOfMonth(10));
        System.out.println(bestMonthForCommodity("Silver"));
        System.out.println(consecutiveLossDays("Oil"));
        System.out.println(daysAboveThreshold("Gold" , 10000));
        System.out.println(biggestDailySwing(2));
        System.out.println(compareTwoCommodities("Oil" , "Gold"));
        System.out.println(bestWeekOfMonth(8));
}}
