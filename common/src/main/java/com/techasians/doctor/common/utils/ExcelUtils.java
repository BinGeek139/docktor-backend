package com.techasians.doctor.common.utils;

import com.google.common.collect.Iterators;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class ExcelUtils {

    private static final String HIDDEN_SHEET = "hidden";

    public static XSSFSheet createSheet(XSSFWorkbook workbook, XSSFSheet sheet, CellStyle cellStyle, Map<Long, Object> map, Long rowStart) {
        DataValidationHelper validationHelper = new XSSFDataValidationHelper(sheet);
        // set value for template Row
        Row templateRow;
        if (rowStart == null) {
            rowStart = 1L;
        } else {
            rowStart++;
        }
        templateRow = sheet.createRow(rowStart.intValue());
        sheet.createRow(rowStart.intValue() + 1);
        sheet.createRow(rowStart.intValue() + 2);
        for (Map.Entry<Long, Object> entry : map.entrySet()) {
            Long indexCell = entry.getKey();
            Object value = entry.getValue();
            Cell cell = templateRow.createCell(indexCell.intValue());
            if (value instanceof Collection) {
                String[] insert = new String[((List) value).size()];
                ((List) value).toArray(insert);
                cell.setCellValue(insert[0]);
                cell.setCellStyle(cellStyle);
                //create dropdown list
                // - create hidden value
                XSSFSheet hidden = workbook.getSheet(HIDDEN_SHEET);
                if (hidden == null) {
                    hidden = workbook.createSheet(HIDDEN_SHEET);
                }
                for (int i = 0; i < insert.length; i++) {
                    Row row = Objects.isNull(hidden.getRow(i)) ? hidden.createRow(i) : hidden.getRow(i);
                    row.createCell(indexCell.intValue()).setCellValue(insert[i]);
                }
                // - create drop down list
                Name namedCell = workbook.getName(HIDDEN_SHEET + indexCell.intValue());
                if (namedCell == null) {
                    namedCell = workbook.createName();
                    namedCell.setNameName(HIDDEN_SHEET + indexCell.intValue());
                }
                String columnLetter = CellReference.convertNumToColString(indexCell.intValue());
                namedCell.setRefersToFormula(HIDDEN_SHEET + "!$" + columnLetter + "$1:$" + columnLetter + "$" + insert.length);
                XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) validationHelper.createFormulaListConstraint(HIDDEN_SHEET + indexCell.intValue());
                CellRangeAddressList addressList = new CellRangeAddressList(rowStart.intValue(), rowStart.intValue(), indexCell.intValue(), indexCell.intValue());
                XSSFDataValidation validation = (XSSFDataValidation) validationHelper.createValidation(
                        dvConstraint, addressList);
                sheet.addValidationData(validation);
                validation.setShowErrorBox(true);
                validation.setSuppressDropDownArrow(true);
            } else {
                cell.setCellValue(value.toString() + " ");
                cell.setCellStyle(cellStyle);
            }
        }

        return sheet;
    }

    public static XSSFSheet alterSheet(XSSFWorkbook workbook, XSSFSheet sheet, Map<Long, Object> map, Long rowStart) {
        DataValidationHelper validationHelper = new XSSFDataValidationHelper(sheet);
        // set value for template Row
        if (rowStart == null) {
            rowStart = 1l;
        }
        Iterator<Row> rowIterator = sheet.iterator();
        XSSFSheet hidden = workbook.getSheet(HIDDEN_SHEET);
        if (hidden == null) {
            hidden = workbook.createSheet(HIDDEN_SHEET);
        }
        int size = Iterators.size(rowIterator);
        //for (Long indexCell : map.keySet()) {
        for (Map.Entry<Long, Object> entry : map.entrySet()) {
            Long indexCell = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Collection) {
                String[] insert = new String[((List) value).size()];
                ((List) value).toArray(insert);
                //create dropdown list
                // - create hidden value
                for (int i = 0; i < insert.length; i++) {
                    Row row = Objects.isNull(hidden.getRow(i)) ? hidden.createRow(i) : hidden.getRow(i);
                    row.createCell(indexCell.intValue()).setCellValue(insert[i]);
                }
                // - create drop down list
                Name namedCell = workbook.getName(HIDDEN_SHEET + indexCell.intValue());
                if (namedCell == null) {
                    namedCell = workbook.createName();
                    namedCell.setNameName(HIDDEN_SHEET + indexCell.intValue());
                }
                String columnLetter = CellReference.convertNumToColString(indexCell.intValue());
                namedCell.setRefersToFormula(HIDDEN_SHEET + "!$" + columnLetter + "$1:$" + columnLetter + "$" + insert.length);
                XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) validationHelper.createFormulaListConstraint(HIDDEN_SHEET + indexCell.intValue());
                CellRangeAddressList addressList = new CellRangeAddressList(rowStart.intValue(), size, indexCell.intValue(), indexCell.intValue());
                XSSFDataValidation validation = (XSSFDataValidation) validationHelper.createValidation(
                        dvConstraint, addressList);
                sheet.addValidationData(validation);
                validation.setShowErrorBox(true);
                validation.setSuppressDropDownArrow(true);
            }
        }
        return sheet;
    }

    public static CellStyle getFirstStyleHeader(Object wb) {
        //title font header
        Font fHeader = null;
        CellStyle csHeaderFrs = null;
        if (wb instanceof XSSFWorkbook) {
            fHeader = ((XSSFWorkbook) wb).createFont();
            csHeaderFrs = ((XSSFWorkbook) wb).createCellStyle();
        } else if (wb instanceof HSSFWorkbook) {
            fHeader = ((HSSFWorkbook) wb).createFont();
            csHeaderFrs = ((HSSFWorkbook) wb).createCellStyle();
        }
        if (fHeader != null) {
            fHeader.setFontHeightInPoints((short) 16);
            fHeader.setColor(IndexedColors.WHITE.index);
            fHeader.setBold(true);
            // set first style header
            csHeaderFrs.setFont(fHeader);
        }
        if (csHeaderFrs != null) {
            csHeaderFrs.setAlignment(HorizontalAlignment.CENTER);
            csHeaderFrs.setBorderBottom(BorderStyle.THIN);
            csHeaderFrs.setBorderLeft(BorderStyle.MEDIUM);
            csHeaderFrs.setBorderRight(BorderStyle.MEDIUM);
            csHeaderFrs.setBorderTop(BorderStyle.MEDIUM);
            csHeaderFrs.setBottomBorderColor((short) 18);
            csHeaderFrs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            csHeaderFrs.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
            csHeaderFrs.setWrapText(true);
        }
        return csHeaderFrs;
    }

    public static CellStyle getSecondStyleHeader(Object wb) {
        Font fHeader = null;
        CellStyle csHeaderLst = null;
        if (wb instanceof XSSFWorkbook) {
            fHeader = ((XSSFWorkbook) wb).createFont();
            csHeaderLst = ((XSSFWorkbook) wb).createCellStyle();
        } else if (wb instanceof HSSFWorkbook) {
            fHeader = ((HSSFWorkbook) wb).createFont();
            csHeaderLst = ((HSSFWorkbook) wb).createCellStyle();
        }
        if (fHeader != null) {
            fHeader.setFontHeightInPoints((short) 14);
            fHeader.setColor(IndexedColors.WHITE.index);
            fHeader.setBold(true);
            csHeaderLst.setFont(fHeader);
        }
        if (csHeaderLst != null) {
            csHeaderLst.setAlignment(HorizontalAlignment.CENTER);
            csHeaderLst.setBorderBottom(BorderStyle.THICK);
            csHeaderLst.setBorderLeft(BorderStyle.MEDIUM);
            csHeaderLst.setBorderRight(BorderStyle.MEDIUM);
            csHeaderLst.setBorderTop(BorderStyle.MEDIUM);
            csHeaderLst.setBottomBorderColor((short) 18);
            csHeaderLst.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            csHeaderLst.setFillForegroundColor(IndexedColors.SEA_GREEN.index);
            csHeaderLst.setWrapText(true);
        }
        return csHeaderLst;
    }

    public static Row createRow(Sheet sheet, int line) {
        Row xRow = sheet.getRow(line);
        if (xRow == null) {
            xRow = sheet.createRow(line);
        }
        return xRow;
    }

    public static void createCell(Row row, int column, String value, CellStyle style) {
        Cell xCell = row.getCell(column);
        if (xCell == null) {
            xCell = row.createCell(column);
        }
        xCell.setCellValue(value);
        if (style != null) {
            xCell.setCellStyle(style);
        }
    }


    public static Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

        Font headerFont = wb.createFont();
        headerFont.setBold(true);
        headerFont.setFontName("Times New Roman");
        headerFont.setFontHeight((short) 360);

        Font subHeaderFont = wb.createFont();
        subHeaderFont.setBold(true);
        subHeaderFont.setFontName("Times New Roman");
        subHeaderFont.setFontHeight((short) 280);

        Font cellTitleFont = wb.createFont();
        cellTitleFont.setBold(true);
        cellTitleFont.setFontName("Times New Roman");
        cellTitleFont.setFontHeight((short) 200);

        Font cellDataFont = wb.createFont();
        cellDataFont.setBold(true);
        cellDataFont.setFontName("Times New Roman");
        cellDataFont.setFontHeight((short) 200);

        Font cellDataErrorFont = wb.createFont();
        cellDataErrorFont.setBold(true);
        cellDataErrorFont.setColor(Font.COLOR_RED);
        cellDataErrorFont.setFontName("Times New Roman");
        cellDataErrorFont.setFontHeight((short) 200);

        CellStyle styleHeader = wb.createCellStyle();
        styleHeader.setAlignment(HorizontalAlignment.CENTER);
        styleHeader.setFont(headerFont);
        styleHeader.setWrapText(true);
        styles.put("header", styleHeader);

        CellStyle subHeaderLeft = wb.createCellStyle();
        subHeaderLeft.setFont(subHeaderFont);
        subHeaderLeft.setWrapText(true);
        styles.put("subHeaderLeft", subHeaderLeft);

        CellStyle subHeaderCenter = wb.createCellStyle();
        subHeaderCenter.setFont(subHeaderFont);
        subHeaderCenter.setAlignment(HorizontalAlignment.CENTER);
        subHeaderCenter.setWrapText(true);
        styles.put("subHeaderCenter", subHeaderCenter);

        CellStyle cellBoldCenter = wb.createCellStyle();
        cellBoldCenter.setFont(cellTitleFont);
        cellBoldCenter.setAlignment(HorizontalAlignment.CENTER);
        styles.put("cellBoldCenter", cellBoldCenter);

        CellStyle cellBoldLeft = wb.createCellStyle();
        cellBoldLeft.setFont(cellTitleFont);
        cellBoldLeft.setAlignment(HorizontalAlignment.LEFT);
        styles.put("cellBoldLeft", cellBoldLeft);

        CellStyle cellBoldRight = wb.createCellStyle();
        cellBoldRight.setFont(cellTitleFont);
        cellBoldRight.setAlignment(HorizontalAlignment.RIGHT);
        styles.put("cellBoldRight", cellBoldRight);

        CellStyle cellColTitle = createBorderedStyle(wb);
        cellColTitle.setFont(cellTitleFont);
        cellColTitle.setAlignment(HorizontalAlignment.LEFT);
        cellColTitle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        cellColTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellColTitle.setWrapText(true);
        styles.put("cellColumnTitle", cellColTitle);

        CellStyle cellDataLeft = createBorderedStyle(wb);
        cellDataLeft.setFont(cellDataFont);
        cellDataLeft.setAlignment(HorizontalAlignment.LEFT);
        cellDataLeft.setWrapText(true);
//        cellDataLeft.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("cellDataLeft", cellDataLeft);

        CellStyle cellDataRight = createBorderedStyle(wb);
        cellDataRight.setFont(cellDataFont);
        cellDataRight.setWrapText(true);
        cellDataRight.setAlignment(HorizontalAlignment.CENTER);
//        cellDataRight.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("cellDataRight", cellDataRight);

        CellStyle cellDataCenter = createBorderedStyle(wb);
        cellDataCenter.setFont(cellDataFont);
        cellDataCenter.setAlignment(HorizontalAlignment.CENTER);
        cellDataCenter.setVerticalAlignment(VerticalAlignment.CENTER);
        cellDataCenter.setWrapText(true);
//        cellDataCenter.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("cellDataCenter", cellDataCenter);

        CellStyle hlink_style = createBorderedStyle(wb);
        Font hlink_font = wb.createFont();
        hlink_font.setUnderline(Font.U_SINGLE);
        hlink_font.setColor(IndexedColors.BLUE.getIndex());
        hlink_style.setWrapText(true);
        hlink_style.setFont(hlink_font);

        styles.put("cellHyperLinkLeft", hlink_style);

        CellStyle cellDataError = createBorderedStyle(wb);
        cellDataError.setFont(cellDataErrorFont);
        cellDataError.setAlignment(HorizontalAlignment.LEFT);
        cellDataError.setWrapText(true);
//        cellDataLeft.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("cellDataError", cellDataError);
//        CellStyle numStyle = wb.createCellStyle();
//        XSSFDataFormat format = wb.createDataFormat();
//        numStyle.setDataFormat(format.getFormat(javafx.scene.input.DataFormat.));

        return styles;
    }

    public static CellStyle createBorderedStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }

    public static CellRangeAddress createCellReangeAddress(int col1, int row1, int col2, int row2, Sheet sheet, CellStyle cellStyle) {
        Row row = null;
        Cell cell = null;
        if (cellStyle != null) {
            for (int i = row1; i <= row2; i++) {
                for (int j = col1; j <= col2; j++) {
                    row = sheet.getRow(i);
                    if (row == null) {
                        row = sheet.createRow(i);
                    }
                    cell = row.getCell(j);
                    if (cell == null) {
                        cell = row.createCell(j);
                    }
                    cell.setCellStyle(cellStyle);
                }
            }
        }
        return new CellRangeAddress(row1, row2, col1, col2);
    }

    public static Cell createCell(Sheet sheet, int col, int iRowIndex, Object value, CellStyle cellStyle) {
        Row row = null;
        Cell cell = null;
        try {
            row = sheet.getRow(iRowIndex);
            if (row == null) {
                row = sheet.createRow(iRowIndex);
            }
            cell = row.getCell(col);
            if (cell == null) {
                cell = row.createCell(col);
            }
            if (value instanceof BigDecimal) {
                cell.setCellValue(((BigDecimal) value).doubleValue());
            } else if (value instanceof Float) {
                cell.setCellValue((Float) value);
            } else if (value instanceof Double) {
                cell.setCellValue((Double) value);
            } else if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            } else if (value instanceof Long) {
                cell.setCellValue((Long) value);
            } else {
                cell.setCellValue(value == null ? "" : value.toString());
            }

            cell.setCellStyle(cellStyle);

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return cell;
    }

    public static Cell createHyperLinkCell(Sheet sheet, int col, int iRowIndex, String value, String linkStr, CellStyle cellStyle) {
        Row row = null;
        Cell cell = null;
        try {
            row = sheet.getRow(iRowIndex);
            if (row == null) {
                row = sheet.createRow(iRowIndex);
            }
            cell = row.getCell(col);
            if (cell == null) {
                cell = row.createCell(col);
            }
            Hyperlink link = sheet.getWorkbook().getCreationHelper().createHyperlink(HyperlinkType.URL);
            link.setAddress(linkStr == null ? "" : linkStr);
            cell.setHyperlink(link);
            cell.setCellValue(value);
            cell.setCellStyle(cellStyle);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return cell;
    }

    public static int fillTitle(String title, Sheet sheet, Map<String, CellStyle> mapStyle, int rowIndex, String[] header, Date fromDate, Date toDate) {
        int rowIndexTemp = rowIndex;
        sheet.setDisplayGridlines(true);
        sheet.setPrintGridlines(false);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setAutobreaks(true);
        printSetup.setFitHeight((short) 1);
        printSetup.setFitWidth((short) 1);
        final SimpleDateFormat sdfDateTemp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        ExcelUtils.createCell(sheet, 0, rowIndexTemp, title, mapStyle.get("header"));
        sheet.addMergedRegion(ExcelUtils.createCellReangeAddress(0, rowIndexTemp, 10, rowIndexTemp, sheet, mapStyle.get("header")));
        rowIndexTemp++;
        ExcelUtils.createCell(sheet, 0, rowIndexTemp, "(" + sdfDateTemp.format(fromDate) + " --> " + sdfDateTemp.format(toDate) + ")", mapStyle.get("subHeaderCenter"));
        sheet.addMergedRegion(ExcelUtils.createCellReangeAddress(0, rowIndexTemp, 10, rowIndexTemp, sheet, mapStyle.get("subHeaderCenter")));

        rowIndexTemp++;
        rowIndexTemp++;
        ExcelUtils.createCell(sheet, 0, rowIndexTemp, "STT", mapStyle.get("cellColumnTitle"));
//        sheet.addMergedRegion(ExcelUtils.createCellReangeAddress(0, rowIndexTemp, 0, rowIndexTemp + 1, sheet, mapStyle.get("cellColumnTitle")));
        sheet.setColumnWidth(0, 5 * 256);

        int col = 1;
        for (String head : header) {
            ExcelUtils.createCell(sheet, col, rowIndexTemp, head, mapStyle.get("cellColumnTitle"));
//            sheet.addMergedRegion(ExcelUtils.createCellReangeAddress(col, rowIndexTemp, col, rowIndexTemp + 1, sheet, mapStyle.get("cellColumnTitle")));
            sheet.setColumnWidth(col, 20 * 256);
            col++;
        }
        rowIndexTemp++;
//        rowIndexTemp++;

        return rowIndexTemp;
    }

    public static int fillData(Sheet sheet, Map<String, CellStyle> mapStyle, List<String> listResult, String[] header, int rowIndex) {
        int rowIndexTemp = rowIndex;
        for (int stt = 1; stt < listResult.size(); stt++) {
            String[] value = listResult.get(stt).split("\\|\\|");
            int col = 0;
            ExcelUtils.createCell(sheet, col, rowIndexTemp, stt, mapStyle.get("cellDataCenter"));
            col++;
            for (int i = 0; i < header.length; i++) {
                String s = "";
                if (value.length > i) {
                    s = value[i];
                }

                ExcelUtils.createCell(sheet, col, rowIndexTemp, s, mapStyle.get("cellDataLeft"));
                col++;
            }
            rowIndexTemp++;
        }
        return rowIndexTemp;
    }


    public static int fillData(Sheet sheet, Map<String, CellStyle> mapStyle, Map<Long, HashMap<String, String>> listResult, String[] headerCode, int rowIndex) {
        int rowIndexTemp = rowIndex;
        int stt = 1;
        for (Map<String, String> value : listResult.values()) {
            int col = 0;
            ExcelUtils.createCell(sheet, col, rowIndexTemp, stt, mapStyle.get("cellDataCenter"));
            col++;
            for (String h : headerCode) {
                String s = value.get(h);
                ExcelUtils.createCell(sheet, col, rowIndexTemp, s, mapStyle.get("cellDataLeft"));
                col++;
            }
            rowIndexTemp++;
            stt++;
        }
        return rowIndexTemp;
    }


    public static String getCellStringValue(XSSFCell cell) {
        CellType type = cell.getCellType();
        if (type == CellType.STRING) {
            return cell.getStringCellValue();
        }
        if (type == CellType.BLANK) {
            return "";
        }
        if (type == CellType.BOOLEAN) {
            return cell.getBooleanCellValue() + "";
        }
        if (type == CellType.NUMERIC) {
            return cell.getRawValue();
        }
        if (type == CellType.FORMULA) {
            return cell.getStringCellValue();
        }
        return cell.getRawValue();
    }

    public static String getCellStringValue(Cell cell, FormulaEvaluator formulaEval) {
        CellType type = cell.getCellType();
        DataFormatter df = new DataFormatter();
        if (type == CellType.FORMULA) {
            return df.formatCellValue(cell, formulaEval);
        }
        return df.formatCellValue(cell);
    }
}
