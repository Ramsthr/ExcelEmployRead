import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelMap {


    private final String inputFile;
    private final Sheet sheet;

    /**
     * constructor of {@link ExcelMap} which takes parameter as string path of Excel file  and fetch sheet from it.
     * @param inputFile inputFile as String
     */
    ExcelMap(String inputFile){
        this.inputFile = inputFile;
        this.sheet = sheetData();
    }



    /**
     * returns sheet from user input Excel file
     * @return returns {@link Sheet} sheet
     */
    private Sheet sheetData()  {
        Sheet sheet = null;
        try {
            File inputWorkbook = new File(inputFile);
            FileInputStream file = new FileInputStream(inputWorkbook);
            Workbook workbook;
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheetAt(0);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheet;
    }

    /**
     * returns uptime in minute
     * @param row row number
     * @return  int value of total time in minute
     */
    private int upTimeCalculate( Row row) {
        String uptime = row.getCell(4).toString();
        int h = 0;
        int m = 0;
        if (!uptime.isEmpty()) {
            if (uptime.charAt(1) == ':') {
                h = Integer.parseInt(uptime.substring(0, 1)) * 60;
                m = Integer.parseInt(uptime.substring(2));
            } else {
                h = Integer.parseInt(uptime.substring(0, 2)) * 60;
                m = Integer.parseInt(uptime.substring(3));
            }
        }
        return h + m;
    }

    /**
     * Extracts date from cell and returns
     * @param row row position
     * @param cellNumber cell number where to fetch date
     * @return {@link Date}
     */
    private Date timeGet(Row row, int cellNumber) {
        final Cell cell = row.getCell(cellNumber);
        Date date = null;
        if ((null != cell) && (CellType.NUMERIC == cell.getCellType())) {
            date = cell.getDateCellValue();
        }
        return date;
    }

    /**
     * Converts excel to {@link Map} of  and return it
     * @return Map of key Integer and value is List of {@link ShiftRecord}
     */
    public Map<Integer, List<ShiftRecord>> toMap() {
        Map<Integer, List<ShiftRecord>> map = null;
        if (sheet != null) {
            boolean startLineSkip = true;
            map = new HashMap<>();
            for (Row row : sheet) {
                if (startLineSkip) {
                    startLineSkip = false;
                    continue;
                }
                final String fileIdString = row.getCell(8).toString();
                final String positionId = row.getCell(0).toString();
                if (null != fileIdString && null != positionId) {
                    final int fileNameId = Integer.parseInt(fileIdString);
                    ShiftRecord shiftRecord = new ShiftRecord.Builder()
                            .setName(row.getCell(7).toString())
                            .setUptime(upTimeCalculate(row))
                            .setInTime(timeGet(row, 2))
                            .setOutTime(timeGet(row, 3))
                            .setPayCycleStart(timeGet(row, 5))
                            .setPayCycleEnd(timeGet(row, 6))
                            .build(positionId, fileIdString);
                    List<ShiftRecord> shiftRecordList = map.get(fileNameId);
                    if (null == shiftRecordList) {
                        shiftRecordList = new ArrayList<>();
                    }
                    shiftRecordList.add(shiftRecord);
                    map.put(fileNameId, shiftRecordList);
                }
            }
        }
        return map;
    }
}
