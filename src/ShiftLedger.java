import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ShiftLedger {
    Map<Integer, List<ShiftRecord>> shiftRecordsMap;

    /**
     * Constructor of {@link ShiftLedger}
     * @param excelMap creates map for processing from Excel
     */
    public ShiftLedger(@NotNull final ExcelMap excelMap) {
        this.shiftRecordsMap = excelMap.toMap();
    }

    /**
     * The uptime greater than uptime hours
     * @param uptime that records which uptime greater than uptime
     * @return returns List of {@link  ShiftRecord}
     */
    @Nullable
    public List<ShiftRecord> getRecordsWithUptime(final int uptime) {
        int upTimeInMin = uptime * 60;
        List<ShiftRecord> recordsWithUptime = new ArrayList<>();
        if (!this.shiftRecordsMap.isEmpty()) {
            for (List<ShiftRecord> shiftRecordList : this.shiftRecordsMap.values()) {
                for (ShiftRecord shiftRecord : shiftRecordList) {
                    if (shiftRecord.getUptime() >= upTimeInMin) {
                        recordsWithUptime.add(shiftRecord);
                        break;
                    }
                }
            }
        } else {
            System.out.println("map is empty");
        }
        return recordsWithUptime;
    }

    /**
     * return ShiftRecord List whose  worked for consecutive days
     * @param consecutiveDays consecutive Days
     * @return returns List of {@link ShiftRecord}
     */
    @Nullable
    public List<ShiftRecord> getRecordsWithConsecutiveShiftDays(final int consecutiveDays) {
        List<ShiftRecord> consecutiveDaysData = new ArrayList<>();
        if (!this.shiftRecordsMap.isEmpty()) {
            for (List<ShiftRecord> shiftRecordList : this.shiftRecordsMap.values()) {
                int consecutivedays = 0;
                int previousdate = 0;
                int currentconsdays = 0;
                for (ShiftRecord shiftRecord : shiftRecordList) {
                    Date startDate = shiftRecord.getInTime();
                    if (null != startDate) {
                        int start = startDate.getDate();
                        if (previousdate + 1 == start) {
                            currentconsdays++;
                            consecutivedays = Math.max(consecutivedays, currentconsdays);
                        } else if (previousdate != start) {
                            currentconsdays = 1;
                        }
                        previousdate = start;
                    }
                }
                if (consecutivedays >= consecutiveDays) {
                    consecutiveDaysData.add(shiftRecordList.get(0));
                }
            }
        } else {
            System.out.println("map is empty");
        }
        return consecutiveDaysData;
    }

    /**
     * who have less than maxHours hours of time between shifts but greater than minHours hour by Approach1
     *If any FileData that contains time between shift difference is greater than maxHours and time between shift difference is lesser than minHours is considerable than it brakes from list of {@link ShiftRecord}
     * @param maxHours maxHours
     * @param minHours minHours
     * @return returns List of {@link ShiftRecord }
     */
    @Nullable
    public List<ShiftRecord> getRecordsWithHoursBetweenShiftsApproach1(final int maxHours, final int minHours) {
        List<ShiftRecord> timeBetweenShiftApr1 = new ArrayList<>();
        if (!this.shiftRecordsMap.isEmpty()) {
            for (List<ShiftRecord> shiftRecordList : this.shiftRecordsMap.values()) {
                Date endpreviousdate = null;
                for (ShiftRecord shiftRecord : shiftRecordList) {
                    Date startDate = shiftRecord.getInTime();
                    Date endDate = shiftRecord.getOutTime();
                    if (null != startDate) {
                        if (endpreviousdate != null) {
                            long diff = startDate.getTime() - endpreviousdate.getTime();
                            diff = diff / 3600000;
                            if (diff < maxHours && diff > minHours) {
                                timeBetweenShiftApr1.add(shiftRecord);
                                break;
                            }
                        }
                        if (endDate != null) {
                            endpreviousdate = endDate;
                        }
                    }
                }
            }
        } else {
            System.out.println("map is empty");
        }
        return timeBetweenShiftApr1;
    }

    /**
     * who have less than maxHours hours of time between shifts but greater than minHours hour by Approach2
     *If any FileData that contains time between shift difference is greater than maxHours and time between shift difference is lesser than minHours is not considerable
     * @param maxHours maxHours
     * @param minHours minHours
     * @return returns List of {@link ShiftRecord }
     */
    @Nullable
    public List<ShiftRecord> getRecordsWithHoursBetweenShiftsApproach2(final int maxHours, final int minHours) {
        List<ShiftRecord> timeBetweenShiftApr2 = new ArrayList<>();
        if (!this.shiftRecordsMap.isEmpty()) {
            for (List<ShiftRecord> shiftRecordList : this.shiftRecordsMap.values()) {
                Date endpreviousdate = null;
                Long diffMin = Long.MAX_VALUE;
                Long diffMax = Long.MIN_VALUE;
                for (ShiftRecord shiftRecord : shiftRecordList) {
                    Date startDate = shiftRecord.getInTime();
                    Date endDate = shiftRecord.getOutTime();
                    if (null != startDate) {
                        if (endpreviousdate != null) {
                            long diff = startDate.getTime() - endpreviousdate.getTime();
                            diff = diff / 3600000;
                            diffMin = Math.min(diffMin, diff);
                            diffMax = Math.max(diffMax, diff);
                        }
                        if (endDate != null) {
                            endpreviousdate = endDate;
                        }
                    }
                }
                if (Long.MIN_VALUE != diffMax && Long.MAX_VALUE != diffMin) {
                    if (diffMax < maxHours && diffMin > minHours) {
                        timeBetweenShiftApr2.add(shiftRecordList.get(0));
                    }
                }
            }
        } else {
            System.out.println("map is empty");
        }
        return timeBetweenShiftApr2;
    }

    /**
     * prints data in console from List of {@link ShiftRecord}
     * @param shiftRecords List of {@link ShiftRecord}
     * @param string       String for message
     */
    public void prints(List<ShiftRecord> shiftRecords, String string) {
        for (ShiftRecord shiftRecord : shiftRecords) {
            System.out.println(string + " :- " + shiftRecord.getName());
        }
    }
}
