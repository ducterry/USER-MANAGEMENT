package com.ndangducbn.ducterrybase.utils;

import com.ndangducbn.ducterrybase.enums.ETypeCellFormat;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.MediaType;

import javax.servlet.ServletContext;

public class FileUtil {
    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        String mineType = servletContext.getMimeType(fileName);
        try {
            return MediaType.parseMediaType(mineType);
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    public static CellStyle cellStyle(Workbook workbook, ETypeCellFormat type){
        CellStyle cellStyle = workbook.createCellStyle();
        borderStyle(cellStyle);
        Font fontStyle = fontStyle(workbook);
        DataFormat dataFormat = workbook.createDataFormat();
        /*
            - Xử lý Font
         */
        cellStyle.setFont(fontStyle);
        cellStyle.setDataFormat(dataFormat.getFormat("@"));
        cellStyle.setWrapText(true);
        switch (type){
            case TYPE_DATE:
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                break;
            case TYPE_TEXT:
                cellStyle.setAlignment(HorizontalAlignment.LEFT);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                break;
            case TYPE_NUMBER:
                cellStyle.setAlignment(HorizontalAlignment.RIGHT);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                break;
        }
        return cellStyle;
    }

    public static void borderStyle(CellStyle cellStyle) {
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
    }

    public static Font fontStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        return font;
    }

    public static void createCell(Row row, Integer i, String value, CellStyle cellStyle) {
        Cell cell = row.createCell(i);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }
}
