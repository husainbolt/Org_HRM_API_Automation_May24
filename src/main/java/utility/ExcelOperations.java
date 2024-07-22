package utility;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelOperations {

    public static Object[][] excelReadOperation(String workbook, String sheetName) throws IOException {

        File file = new File(workbook);
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = xssfWorkbook.getSheet(sheetName);
        int totalRows = sheet.getLastRowNum();
        int totalCols = sheet.getRow(0).getLastCellNum();
        System.out.println(totalRows);
        System.out.println(totalCols);
        Object[][] data = new Object[totalRows][totalCols];
        for(int rowIndex = 0; rowIndex<totalRows; rowIndex++){
            for(int colIndex = 0; colIndex < totalCols; colIndex++){
                try {
                    Cell cell = sheet.getRow(rowIndex + 1).getCell(colIndex);
                    if (CellType.STRING == cell.getCellType())
                        data[rowIndex][colIndex] = cell.getStringCellValue();
                    else if (CellType.NUMERIC == cell.getCellType())
                        data[rowIndex][colIndex] = cell.getNumericCellValue();
                    else if (CellType.BOOLEAN == cell.getCellType())
                        data[rowIndex][colIndex] = cell.getBooleanCellValue();
                }
                catch(NullPointerException ne){
                    data[rowIndex][colIndex] = "";
                }
            System.out.print(data[rowIndex][colIndex] + "---");
            }
            System.out.println();
        }
        return data;
    }
}
