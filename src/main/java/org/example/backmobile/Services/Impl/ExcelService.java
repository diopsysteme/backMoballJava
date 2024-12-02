//package org.example.backmobile.Services.Impl;
//
//import org.SchoolApp.Services.Interfaces.ExcelIService;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class ExcelService implements ExcelIService {
//    @Override
//    public List<Map<String, String>> importExcelData(MultipartFile file) throws IOException {
//        List<Map<String, String>> data = new ArrayList<>();
//
//        try (InputStream inputStream = file.getInputStream();
//             Workbook workbook = new XSSFWorkbook(inputStream)) {
//
//            Sheet sheet = workbook.getSheetAt(0);
//            Row headerRow = sheet.getRow(0); // First row as header
//
//            // Get the column headers
//            List<String> headers = new ArrayList<>();
//            for (Cell headerCell : headerRow) {
//                headers.add(headerCell.getStringCellValue());
//            }
//
//            // Iterate over the remaining rows (after the header)
//            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
//                Row row = sheet.getRow(i);
//                Map<String, String> rowData = new HashMap<>();
//
//                for (int j = 0; j < headers.size(); j++) {
//                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//                    String cellValue;
//
//                    // Get cell value based on its type
//                    switch (cell.getCellType()) {
//                        case STRING:
//                            cellValue = cell.getStringCellValue();
//                            break;
//                        case NUMERIC:
//                            cellValue = String.valueOf(cell.getNumericCellValue());
//                            break;
//                        case BOOLEAN:
//                            cellValue = String.valueOf(cell.getBooleanCellValue());
//                            break;
//                        default:
//                            cellValue = "";
//                            break;
//                    }
//
//                    // Associate each cell with its corresponding header
//                    rowData.put(headers.get(j), cellValue);
//                }
//
//                data.add(rowData); // Add the map to the list
//            }
//        }
//
//        return data;
//    }
//}