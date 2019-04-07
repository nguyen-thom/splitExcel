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
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class SliptService {
    
   public void sliptExcelFile(String input, String folderOutput, String templateFile, int startRow){
        File file = new File(input);
        if(!file.exists()){
            log.info("input not exist: " + input);
            return;
        }
       File fileTemplateOutput = new File(templateFile);
       if(!fileTemplateOutput.exists()){
           log.info("input template not exist: " + templateFile);
           return;
       }
        ExcelService excelService = new ExcelServiceImpl();
        FileService fileService = new FileServiceImpl();
       try{
           
            File outputDir =  new File(folderOutput);
            if(!outputDir.exists()){
                outputDir.mkdirs();
            }
            //FileUtils.cleanDirectory(outputDir);
            Map<String, List<InvoiceInputEntity>> dataInput = excelService.readExcelFile(file, startRow);
           
            Map<String, CompanyTarget> companyTargetMap = new HashMap<>();
            convertCompany(dataInput, companyTargetMap);
    
            for(Map.Entry<String,CompanyTarget> listEntry  : companyTargetMap.entrySet()){
                CompanyTarget companyTargetInput = listEntry.getValue();
                String ctyCode = listEntry.getKey();
                
                //create file name
                String name = companyTargetInput.getName();
                String fileName = ctyCode.concat("_").concat(name);
                fileName = covertStringToURL(fileName);
                
                //create file from template
                String fullPath = folderOutput + fileName +  ".xlsx";
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
            if(listData.isEmpty()){
                continue;
            }
            long totalValue;
            if(companyTargetMap.containsKey(listEntry.getKey())){
                companyTarget = companyTargetMap.get(mst);
                totalValue = companyTarget.getTotalValue();
            }else{
                InvoiceInputEntity firstItem = listData.get(0);
                companyTarget = new CompanyTarget();
                Company company = new Company();
                //TODO refactor code
                company.setName(firstItem.getSaleCompanyName());
                company.setTaxCode(firstItem.getSaleCompanyCode());
                companyTarget.setName(firstItem.getCompanyName());
                companyTarget.setTaxCode(firstItem.getTaxCode());
                companyTarget.setManagerDepartment(firstItem.getCoQuanQuanLy());
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
    public String covertStringToURL(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
        } catch (Exception e) {
           e.printStackTrace();
        }
        return "";
    }
}
