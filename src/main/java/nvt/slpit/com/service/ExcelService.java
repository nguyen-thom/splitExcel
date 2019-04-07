package nvt.slpit.com.service;

import nvt.slpit.com.entity.CompanyTarget;
import nvt.slpit.com.entity.InvoiceInputEntity;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface ExcelService {
    
    void createFileFromTemplate(String path, String target) throws Exception;
    
    Map<String, List<InvoiceInputEntity>> readExcelFile(File fullPath) throws Exception;
    
    void insertDataToTemplate(CompanyTarget data, File file) throws Exception;
    
    String getCellValueAsString(Cell cell);
    
    //void copyRow(HSSFWorkbook workbook, HSSFSheet worksheet, int sourceRowNum, int destinationRowNum);
     
     
    
}
