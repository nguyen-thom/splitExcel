package nvt.slpit.com.service;

import lombok.extern.slf4j.Slf4j;
import nvt.slpit.com.entity.Company;
import nvt.slpit.com.entity.CompanyTarget;
import nvt.slpit.com.entity.Invoice;
import nvt.slpit.com.entity.InvoiceInputEntity;
import nvt.slpit.com.serviceImpl.ExcelServiceImpl;
import nvt.slpit.com.serviceImpl.FileServiceImpl;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SliptService {
    
   public void sliptExcelFile(String input, String folderOutput){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("input_data/XMHD.xlsx").getFile());
        ExcelService excelService = new ExcelServiceImpl();
        FileService fileService = new FileServiceImpl();
       try{
            File fileTemplateOutput = new File(classLoader.getResource("template_output/template_gia_nguyen.xlsx").getFile());
            File outputDir =  new File(folderOutput);
            FileUtils.cleanDirectory(outputDir);
            Map<String, List<InvoiceInputEntity>> dataInput = excelService.readExcelFile(file);
           
            Map<String, CompanyTarget> companyTargetMap = new HashMap<>();
            convertCompany(dataInput, companyTargetMap);
    
            for(Map.Entry<String,CompanyTarget> listEntry  : companyTargetMap.entrySet()){
                CompanyTarget companyTargetInput = listEntry.getValue();
                String ctyCode = listEntry.getKey();
                
                //create file name
                String name = companyTargetInput.getName();
                String fileName = ctyCode;
                
                //create file from template
                String fullPath = folderOutput + fileName +  ".xlsx";
                log.info("fullPath output:"+ fullPath);
                File fileOutput = new File(fullPath);
                fileService.copy(fileTemplateOutput,fileOutput);
                
                //write data to output file
                excelService.insertDataToTemplate(companyTargetInput,fileOutput);
            }
        }catch (Exception ex){
            log.error("exception :", ex);
        }
        
    }
    
    private void convertCompany(Map<String, List<InvoiceInputEntity>> dataInput, Map<String, CompanyTarget> companyTargetMap) {
        CompanyTarget companyTarget;//create CompanyTarget
        for(Map.Entry<String,List<InvoiceInputEntity>> listEntry : dataInput.entrySet()){
            String mst = listEntry.getKey();
            List<InvoiceInputEntity> listData = listEntry.getValue();
            System.out.println("List Data: " + listData);
            if(listData.isEmpty()){
                continue;
            }
            long totalValue = 0L;
            if(companyTargetMap.containsKey(listEntry.getKey())){
                companyTarget = companyTargetMap.get(mst);
                totalValue = companyTarget.getTotalValue();
            }else{
                InvoiceInputEntity firstItem = listData.get(0);
                companyTarget = new CompanyTarget();
                Company company = new Company();
                //TODO refactor code
                company.setName("Công ty TNHH Đồi Thông Xanh");
                company.setTaxCode("303137349");
                companyTarget.setName(firstItem.getCompanyName());
                companyTarget.setTaxCode(firstItem.getTaxCode());
                companyTarget.setCompany(company);
                companyTargetMap.put(mst,companyTarget);
                totalValue = 0L;
            }
            List<Invoice> invoiceList = companyTarget.getInvoiceList();
            
            for(InvoiceInputEntity item : listData){
                Invoice invoice = new Invoice();
                invoice.setSign(item.getSign());
                invoice.setInvoiceNumber(item.getInvoiceNumber());
                invoice.setInvoiceDate(item.getInvoiceDate());
                invoice.setTaxValue(item.getTaxValue());
                totalValue = totalValue + Long.parseLong(invoice.getTaxValue());
                invoiceList.add(invoice);
            }
            companyTarget.setTotalValue(totalValue);
        }
    }
}
