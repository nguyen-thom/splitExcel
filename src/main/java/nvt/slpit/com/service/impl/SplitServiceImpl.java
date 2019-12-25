package nvt.slpit.com.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nvt.slpit.com.entity.Company;
import nvt.slpit.com.entity.CompanyTarget;
import nvt.slpit.com.entity.Invoice;
import nvt.slpit.com.entity.InvoiceInputEntity;
import nvt.slpit.com.service.ExcelService;
import nvt.slpit.com.service.FileService;
import nvt.slpit.com.service.SplitExcelService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class SplitServiceImpl extends SwingWorker<Integer, String> implements SplitExcelService {

    private String input;
    private String folderOutput;
    private String templateFile;
    private int startRow;
    private int startIndex;
    private JTextArea jTextArea;


    @Override
    public int splitExcelFile(String input, String folderOutput, String templateFile, int startRow, int startIndex) {
        List<String> resultStringPath = new ArrayList<>();
        File file = new File(input);
        if (!file.exists()) {
            publish("input not exist: " + input);
        }
        File fileTemplateOutput = new File(templateFile);
        if (!fileTemplateOutput.exists() || fileTemplateOutput.isDirectory()) {
            publish("template not exist: " + templateFile);
        }
        ExcelService excelService = new ExcelServiceImpl();
        FileService fileService = new FileServiceImpl();
        try {

            File outputDir = new File(folderOutput);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            //FileUtils.cleanDirectory(outputDir);
            Map<String, List<InvoiceInputEntity>> dataInput = excelService.readExcelFile(file, startRow, startIndex);

            Map<String, CompanyTarget> companyTargetMap = new HashMap<>();
            convertCompany(dataInput, companyTargetMap);
            Map<String, CompanyTarget> sorted = companyTargetMap
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                            LinkedHashMap::new));

            for (Map.Entry<String, CompanyTarget> listEntry : sorted.entrySet()) {
                CompanyTarget companyTargetInput = listEntry.getValue();
                String ctyCode = listEntry.getKey();

                //create file name
                String name = companyTargetInput.getName();
                String fileName = ctyCode.concat("_");
                //fileName = fileName.replace("//", "-");
                fileName = covertStringToURL(fileName);

                //create file from template
                File fileOutput = FileUtils.getFile(outputDir, fileName + ".xlsx");
                fileService.copy(fileTemplateOutput, fileOutput);
                publish(fileOutput.getPath());
                //resultStringPath.add(fileOutput.getPath());

                //write data to output file
                excelService.insertDataToTemplate(companyTargetInput, fileOutput);
            }
            return dataInput.size();
        } catch (Exception ex) {
            log.error("exception :", ex);
            publish(getStackTrace(ex));
            //resultStringPath.add(getStackTrace(ex));
        }
        return 0;
    }

    private void convertCompany(Map<String, List<InvoiceInputEntity>> dataInput, Map<String, CompanyTarget> companyTargetMap) {
        CompanyTarget companyTarget;//create CompanyTarget
        for (Map.Entry<String, List<InvoiceInputEntity>> listEntry : dataInput.entrySet()) {
            String mst = listEntry.getKey();
            List<InvoiceInputEntity> listData = listEntry.getValue();
            if (listData.isEmpty()) {
                continue;
            }
            long totalValue;
            if (companyTargetMap.containsKey(listEntry.getKey())) {
                companyTarget = companyTargetMap.get(mst);
                totalValue = companyTarget.getTotalValue();
            } else {
                InvoiceInputEntity firstItem = listData.get(0);
                companyTarget = new CompanyTarget();
                Company company = new Company();
                //TODO refactor code
                company.setName(firstItem.getSaleCompanyName());
                company.setTaxCode(firstItem.getSaleCompanyCode());
                companyTarget.setName(firstItem.getCompanyName());
                companyTarget.setTaxCode(firstItem.getTaxCode());
                companyTarget.setManagerDepartment(firstItem.getCoQuanQuanLy());
                companyTarget.setCode(firstItem.getCode());
                companyTarget.setCompany(company);

                companyTargetMap.put(mst, companyTarget);
                totalValue = 0L;
            }
            List<Invoice> invoiceList = companyTarget.getInvoiceList();

            for (InvoiceInputEntity item : listData) {
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

    private String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    @Override
    protected Integer doInBackground() throws Exception {
        return splitExcelFile(input, folderOutput, templateFile, startRow, startIndex);
    }

    @Override
    protected void process(List<String> chunks) {
        super.process(chunks);
        for (String chunk : chunks) {
            jTextArea.append(chunk);
            jTextArea.append(StringUtils.CR);
            jTextArea.append(StringUtils.LF);
        }
    }
}
