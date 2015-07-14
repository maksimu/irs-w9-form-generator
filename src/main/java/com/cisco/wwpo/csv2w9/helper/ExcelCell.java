package com.cisco.wwpo.csv2w9.helper;


import com.google.common.primitives.Floats;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.DateFormatConverter;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by maksimustinov on 3/27/15.
 */
@Log4j
public class ExcelCell {

    private ExcelCell(){}



    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c <= row.getLastCellNum(); c++) {
            if(c >= 0) {
                Cell cell = row.getCell(c);
                if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                    return false;
            } else{
                // the row does not contain any cells
            }
        }
        return true;
    }

    public static Double getDoubleFromCell(Cell cell) {
        if (cell == null) {
            return null;
        }

        return Double.parseDouble(getStringFromCell(cell).trim());
    }

    public static Float getFloatFromCell(Cell cell) {
        if (cell == null) {
            return null;
        }

        String s = getStringFromCell(cell).trim();
        if(NumberUtils.isNumber(s)){
            return Float.parseFloat(s);
        } else {
            return null;
        }
    }

    public static Long getLongFromCell(Cell cell) throws ParseException {
        if (cell == null) {
            return null;
        }

        String longStr = getStringFromCell(cell).trim();
        Number number = NumberFormat.getNumberInstance().parse(longStr);
//        log.debug("String to be converted to Long: [" + longStr + "]");

        return number.longValue();
    }

    public static Integer getIntegerFromCell(Cell cell) throws ParseException {
        if (cell == null) {
            return null;
        }

        String longStr = getStringFromCell(cell).trim();
        Number number = NumberFormat.getNumberInstance().parse(longStr);

//        log.debug("String to be converted to Integer: [" + longStr + "]");

        return number.intValue();
    }

    public static Date getDateFromCell(Cell cell){

        if( cell == null) return null;

        Date d = null;

        String dateString = getStringFromCell(cell);

        Parser nantyParser = new Parser();
        List<DateGroup> groups = nantyParser.parse(dateString);
        for(DateGroup group:groups)  {
            d = group.getDates().get(0);
        }

        return d;
    }

    public static String getStringFromCell(Cell cell) {

        String str;

        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                str = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    DataFormatter formatter = new DataFormatter(Locale.US);
                    CellReference ref = new CellReference(cell);
                    str = formatter.formatCellValue(cell);
//                    str = cell.getStringCellValue().toString();
                } else {
                    str = NumberToTextConverter.toText(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                if (cell.getBooleanCellValue()) {
                    str = "true";
                } else {
                    str = "false";
                }

                break;
            case Cell.CELL_TYPE_FORMULA:
                str = cell.getCellFormula();
                break;
            default:
                str = "";
        }

        return str.trim();
    }
}
