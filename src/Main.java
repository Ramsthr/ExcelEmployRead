import java.util.*;

public class Main {
    public static void main(String[] args) {
        ExcelMap excelMap = new ExcelMap("src/Assignment_Timecard.xlsx");
        ShiftLedger shiftLedger = new ShiftLedger(excelMap);
        int uptime = 14;
        int consecutiveDays = 7;
        int maxHours = 10;
        int minHours = 1;
        List<ShiftRecord> recordListOfUptime = shiftLedger.getRecordsWithUptime(uptime);
        List<ShiftRecord> recordListOfConsecutiveDays = shiftLedger.getRecordsWithConsecutiveShiftDays(consecutiveDays);
        List<ShiftRecord> recordListOfHoursBetweenShift = shiftLedger.getRecordsWithHoursBetweenShiftsApproach1(maxHours, minHours);
        List<ShiftRecord> recordListOfHoursBetweenShiftApr2 = shiftLedger.getRecordsWithHoursBetweenShiftsApproach2(maxHours, minHours);
        shiftLedger.prints(recordListOfConsecutiveDays, "who has worked for" + consecutiveDays + " consecutive days");
        shiftLedger.prints(recordListOfHoursBetweenShift, "who have less than " + maxHours + " hours of time between shifts but greater than " + minHours + " hour by Approach1");
        shiftLedger.prints(recordListOfHoursBetweenShiftApr2, "who have less than " + maxHours + " hours of time between shifts but greater than " + minHours + " hour by Approach2");
        shiftLedger.prints(recordListOfUptime, "The uptime greater than " + uptime + " hours");
    }

}