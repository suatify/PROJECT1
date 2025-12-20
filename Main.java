
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
            String filename = months[monthIndex] + ".txt";

            try {
                sc = new Scanner(Paths.get("Data_Files" , filename));

                if (sc.hasNextLine()) {
                    sc.nextLine(); // başlığı atla
                }

                while (sc.hasNextLine()) {

                    String line = sc.nextLine();
                    if (line.trim().isEmpty()) {
                        continue;
                        // trim satır baş-son'unu temizler.
                        // Temizledikten sonra hala boşsa (demek ki satır boş) o satırı atlar.
                    }

                    String[] data = line.split(",");

                    if (data.length >= 3) {
                        try {
                            int dayFromFile = Integer.parseInt(data[0].trim());
                            String commodityName = data[1].trim();
                            int profit = Integer.parseInt(data[2].trim());

                            int commIndex = -1;
                            for (int i = 0; i < COMMS; i++) {
                                if (commodities[i].equals(commodityName)) {
                                    commIndex = i;
                                    break;
                                }
                            }
                            if (commIndex != -1 && dayFromFile >= 1 && dayFromFile <= DAYS) {
                                marketData[monthIndex][dayFromFile - 1][commIndex] = profit;
                            }

                        } catch (NumberFormatException nfe) {
                        }
                    }
                }
            } catch (IOException e) {

            }
            finally {
                // if olmazsa: dosya bulunmamış olsa dahi kapatmaya çalışır. (sc.close).
                // E olmayan bir şey nası kapansın. hata verir. bunu engelliyoruz
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

        int maxProfit = Integer.MIN_VALUE;
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
        if (month < 0 || month >= MONTHS || day <= 0 || day > DAYS) {
            return -99999;
        }
        int totalProfit = 0;
        for(int commIndex = 0 ; commIndex < COMMS ; commIndex++ ){
            totalProfit += marketData[month][(day-1)][commIndex];
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
                break;}

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
        if(month < 0 || month >= MONTHS){
            return -1;
        }
        int bestDay = 1;
        int maxProfit = totalProfitOnDay(month, 1);

        for (int dayIndex = 2 ; dayIndex <= DAYS; dayIndex++){ // totalProfitOnDay günleri 1-28 olarak alıyo ya ondan dolayı 2'den başladı. 1. günü max kabul edip ikiye geçiyoz

                int currentProfit = totalProfitOnDay(month, dayIndex);

            if (currentProfit > maxProfit ){
                maxProfit = currentProfit;
                bestDay= dayIndex;
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

         for(int monthIndex = 0 ; monthIndex < MONTHS ; monthIndex++) {
            int monthlyProfit= 0;
            for (int dayIndex = 0; dayIndex < DAYS; dayIndex++) {
                monthlyProfit += marketData[monthIndex][dayIndex][commidityIndex];
            }
            if(monthlyProfit > currentProfit){
                currentProfit = monthlyProfit;
                bestMonth = monthIndex;
            }
        }

        return months[bestMonth];
    }
    public static int consecutiveLossDays(String comm) {
        int commIndex = -1;
        for(int i = 0 ; i < COMMS ; i++){
            if (commodities[i].equals(comm)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex == -1) return -1;

        int currentStreak = 0;
        int maxStreak = 0;
        for (int monthIndex = 0; monthIndex < MONTHS ; monthIndex++) {
            for (int dayIndex = 0; dayIndex < DAYS; dayIndex++) {
                if(marketData[monthIndex][dayIndex][commIndex] < 0) {
                    currentStreak++;
                    if (currentStreak > maxStreak) {
                        maxStreak = currentStreak;
                    }
                }else {
                    currentStreak = 0;
                }
            }
        }
        return maxStreak;
    }
    public static int daysAboveThreshold(String comm, int threshold) {

        int commodityIndex = -1;
        for(int commIndex = 0 ; commIndex < COMMS ; commIndex++){//
            if (comm.equals(commodities[commIndex])){
                commodityIndex = commIndex;
                break;
            }
        }
        if (commodityIndex == -1) return -1;

        int daysCounter = 0;
        for (int monthIndex = 0; monthIndex < MONTHS ; monthIndex++) {
            for (int dayIndex = 0; dayIndex < DAYS; dayIndex++) {
                if(marketData[monthIndex][dayIndex][commodityIndex] > threshold){
                     daysCounter++;
                 }
            }
        }
        return daysCounter;
    }

    public static int biggestDailySwing(int month) {
        if (month < 0 || month >= MONTHS) return -99999;


        int maxSwing = 0;
        for (int dayIndex = 1; dayIndex < DAYS; dayIndex++) {

               int todayProfit = totalProfitOnDay(month, dayIndex+1);
               int profitOfPreviousDay = totalProfitOnDay(month, dayIndex);
               int profitSwing =  Math.abs(todayProfit - profitOfPreviousDay);

                if (profitSwing > maxSwing){
                    maxSwing = profitSwing;
                }
        }
        return maxSwing;


    }
    
    public static String compareTwoCommodities(String c1, String c2) {
        int c1Index = -1;
        int c2Index = -1;

        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(c1)) {
                c1Index = i;
            }
            if (commodities[i].equals(c2)) {
                c2Index = i;
            }
        }

        if (c1Index == -1 || c2Index == -1) {
            return "INVALID_COMMODITY";
        }
        int c1Profit = 0;
        int c2Profit = 0;

        for (int monthIndex = 0; monthIndex < MONTHS; monthIndex++) {
            for (int dayIndex = 0; dayIndex < DAYS; dayIndex++) {
                c1Profit += marketData[monthIndex][dayIndex][c1Index];
                c2Profit += marketData[monthIndex][dayIndex][c2Index];
            }
        }
        if (c1Profit > c2Profit) {
            return c1 + " is better by " + (c1Profit - c2Profit);
        } else if (c2Profit > c1Profit) {
            return c2 + " is better by " + (c2Profit - c1Profit);
        } else {
            return "Equal";
        }
    }

    public static String bestWeekOfMonth(int month) {

        if ((month < 0) || (month >= 12)) {return "INVALID_MONTH";}

        int[] week = new int[4];
        for (int dayIndex = 1; dayIndex <= DAYS; dayIndex++) {
            int weekIndex = (dayIndex - 1) / 7;
            week[weekIndex] += totalProfitOnDay(month, dayIndex);
        }

        int bestIndex = 0;
        int currentBestWeek = Integer.MIN_VALUE;
            for(int weekIndex = 0 ; weekIndex < week.length ; weekIndex++){
                if (week[weekIndex] > currentBestWeek){
                    currentBestWeek = week[weekIndex];
                    bestIndex = weekIndex;
                }
            }
            return "Week " + String.valueOf(bestIndex+1);
    }



    public static void main(String[] args) {
        loadData();
       
    }
}
