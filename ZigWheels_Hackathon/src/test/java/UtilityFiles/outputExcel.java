package UtilityFiles;
 
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;
 
public class outputExcel {
 
    static FileOutputStream file;
    static XSSFWorkbook workbook;
    static XSSFSheet sheet1;
    static XSSFSheet sheet2;

 
    public static void openExcel() throws FileNotFoundException {
        file = new FileOutputStream(System.getProperty("user.dir") + "\\TestData\\ZigWheelsExcel.xlsx");
        workbook = new XSSFWorkbook();
        sheet1 = workbook.createSheet("Bikes and Signin Data");
        sheet2 = workbook.createSheet("Cars Data");
   
    }
 
    public static void getBikeExcel(ArrayList<String>  upcomingBikes) throws Throwable {
        XSSFRow row1 = sheet1.createRow(0);
        row1.createCell(0).setCellValue("Bikes Under 4 lakhs");

        for (int i = 0; i < upcomingBikes.size(); i++)
        {
            XSSFRow row = sheet1.createRow(i + 1);
            row.createCell(0).setCellValue(upcomingBikes.get(i));
        }
    }
    
    public static void getUsedCar(List<WebElement> nameList) throws Throwable 
    {
	         
	 	XSSFRow row1 = sheet2.createRow(0);
        row1.createCell(0).setCellValue("Used Car Names:");

        for (int i = 0; i < nameList.size(); i++) 
        {
            XSSFRow row2 = sheet2.createRow(i + 1);
            row2.createCell(0).setCellValue(nameList.get(i).getText());
	    }    
    }

    public static void getSignInExcel(String errorMessage) throws IOException 
    {
        int lastRowNum = sheet1.getLastRowNum();
        XSSFRow row1 = sheet1.createRow(lastRowNum + 3);
        row1.createCell(0).setCellValue("Error Message");
 
        XSSFRow row2 = sheet1.createRow(lastRowNum + 4);
        row2.createCell(0).setCellValue(errorMessage);
    }
    
    public static void closeExcel() throws IOException {
        workbook.write(file);
        workbook.close();
        file.close();
        System.out.println("Excel File created");
    }
}
