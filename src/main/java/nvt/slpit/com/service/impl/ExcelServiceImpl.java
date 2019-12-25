package nvt.slpit.com.service.impl;

import lombok.extern.slf4j.Slf4j;
import nvt.slpit.com.entity.Company;
import nvt.slpit.com.entity.CompanyTarget;
import nvt.slpit.com.entity.Invoice;
import nvt.slpit.com.entity.InvoiceInputEntity;
import nvt.slpit.com.service.ExcelService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelServiceImpl implements ExcelService {

    @Override
    public void createFileFromTemplate(String path, String target) throws Exception {
        Workbook wb = new XSSFWorkbook(OPCPackage.open("template.xlsx"));
        Sheet sheet = wb.getSheetAt(0);
        sheet.getRow(2).getCell(0).setCellValue("Changed value"); // For example
        FileOutputStream fileOut = new FileOutputStream("new.xls");
        wb.write(fileOut);
        fileOut.close();
    }

    @Override
    public Map<String, List<InvoiceInputEntity>> readExcelFile(File fullPath, int startRow, int startIndex) throws Exception {
        Workbook workbook = WorkbookFactory.create(fullPath);
        Sheet sheet = workbook.getSheetAt(0);

        InvoiceInputEntity invoiceInputEntity;
        Map<String, List<InvoiceInputEntity>> listMap = new HashMap<>();
        List<InvoiceInputEntity> listInvoices;
        String nguoiNopThue = sheet.getRow(4).getCell(5).getStringCellValue();
        String masothue = sheet.getRow(5).getCell(5).getStringCellValue();

        int index = 0;
        int code = startIndex;
        boolean isLastRow = false;
        while (!isLastRow) {
            //ma so cong ty
            Row row = sheet.getRow(startRow++);
            if (row == null) {
                isLastRow = true;
                continue;
            }
            Cell c6 = row.getCell(6);
            if (c6 == null) {
                isLastRow = true;
                continue;
            }
            String mstCty = getCellValueAsString(c6);
            log.info("masocty:" + mstCty + " At Row: " + startRow);
            if (StringUtils.isEmpty(mstCty)) {
                isLastRow = true;
                continue;

            }

            if (listMap.containsKey(mstCty)) {
                listInvoices = listMap.get(mstCty);
                code = listInvoices.get(0).getCode();
            } else {
                listInvoices = new ArrayList<>();
                listMap.put(mstCty, listInvoices);
                code = code + 1;
            }

            invoiceInputEntity = new InvoiceInputEntity();
            invoiceInputEntity.setIndex(++index);
            invoiceInputEntity.setTaxCode(mstCty);
            invoiceInputEntity.setCode(code);
            invoiceInputEntity.setSaleCompanyName(nguoiNopThue);
            invoiceInputEntity.setSaleCompanyCode(masothue);
            //column ky hieu
            Cell c2 = row.getCell(2);
            String sign = getCellValueAsString(c2);
            invoiceInputEntity.setSign(sign);

            //column so phat hanh
            Cell c3 = row.getCell(3);
            String number = getCellValueAsString(c3);
            invoiceInputEntity.setInvoiceNumber(number);

            //column num phat hanh
            Cell c4 = row.getCell(4);
            String date = getCellValueAsString(c4);
            invoiceInputEntity.setInvoiceDate(date);

            //column ten cong ty target
            Cell c5 = row.getCell(5);
            String companyName = getCellValueAsString(c5);
            invoiceInputEntity.setCompanyName(companyName);

            //gia tri chua dua vao thue
            Cell c10 = row.getCell(10);
            String taxValue = getCellValueAsString(c10);
            invoiceInputEntity.setTaxValue(taxValue);

            Cell c16 = row.getCell(15);
            String cqQuanLy = getCellValueAsString(c16);
            invoiceInputEntity.setCoQuanQuanLy(cqQuanLy);
            listInvoices.add(invoiceInputEntity);

        }
        return listMap;
    }

    @Override
    public void insertDataToTemplate(CompanyTarget data, File file) throws Exception {
        if (StringUtils.isEmpty(data.getTaxCode())) {
            log.info("taxCode is empty. so skip");
            return;
        }

        final Company company = data.getCompany();
        FileInputStream fsIP = new FileInputStream(file);
//Access the workbook
        XSSFWorkbook wb = new XSSFWorkbook(fsIP);
//Access the worksheet, so that we can update / modify it.
        XSSFSheet worksheet = wb.getSheetAt(0);
// declare a Cell object
        Cell cell = null;

        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        XSSFFont font = wb.createFont();
        font.setFontName("Times New Roman");
        style.setFont(font);

        cell = worksheet.getRow(2).getCell(2);
        cell.setCellValue(String.format("%d/XMHĐ-KTr1", data.getCode()));

        cell = worksheet.getRow(5).getCell(4);
        cell.setCellValue(data.getManagerDepartment());

        //set name for company
        cell = worksheet.getRow(7).getCell(3);
        cell.setCellValue(data.getName());

        cell = worksheet.getRow(7).getCell(9);
        cell.setCellValue(data.getTaxCode());


        List<Invoice> invoices = data.getInvoiceList();

        int startRow = 13;
        int rowNumber = startRow + 1;
        if (invoices.size() > 1) {
            worksheet.shiftRows(startRow + 1, worksheet.getLastRowNum(),
                    invoices.size() - 1,
                    false,
                    false);
            for (int i = 1; i < invoices.size(); ++i) {
                worksheet.copyRows(startRow, startRow, rowNumber++, new CellCopyPolicy());
            }
        }
        int index = 1;
        rowNumber = startRow;
        for (Invoice invoice : invoices) {
            XSSFRow row = worksheet.getRow(rowNumber++);
            row.setHeightInPoints((short) 23);
            insertData(index++, invoice, company, row);
        }
        //write total

        cell = worksheet.getRow(rowNumber).getCell(6);
        cell.setCellValue(data.getTotalValue());
        moveImage(worksheet, invoices.size() - 1, startRow);

        fsIP.close();
//Open FileOutputStream to write updates
        FileOutputStream output_file = new FileOutputStream(file);
        //write changes
        wb.write(output_file);
//close the stream
        output_file.close();

    }

    private void insertData(int index, Invoice invoice, Company company, final XSSFRow row) {
        Cell cell = null;
        if (row == null) {
            log.info("Row index " + index + " is null . skip");
            return;
        }
        cell = row.getCell(0);
        cell.setCellValue(index);


        cell = row.getCell(1);
        cell.setCellValue(invoice.getSign());


        cell = row.getCell(2);
        cell.setCellValue(invoice.getInvoiceNumber());


        cell = row.getCell(3);
        cell.setCellValue(invoice.getInvoiceDate());


        cell = row.getCell(4);
        cell.setCellValue(company.getName());


        cell = row.getCell(5);
        cell.setCellValue(company.getTaxCode());

        cell = row.getCell(6);
        cell.setCellValue(Long.parseLong(invoice.getTaxValue()));

    }

    public String getCellValueAsString(Cell cell) {
        String strCellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    strCellValue = cell.toString();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat(
                                "dd/MM/yyyy");
                        strCellValue = dateFormat.format(cell.getDateCellValue());
                    } else {
                        Double value = cell.getNumericCellValue();
                        Long longValue = value.longValue();
                        strCellValue = new String(longValue.toString());
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    strCellValue = new String(new Boolean(
                            cell.getBooleanCellValue()).toString());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    strCellValue = "";
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    switch (cell.getCachedFormulaResultType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            Double value = cell.getNumericCellValue();
                            Long longValue = value.longValue();
                            strCellValue = new String(longValue.toString());
                            break;
                        case Cell.CELL_TYPE_STRING:
                            strCellValue = cell.getRichStringCellValue() + "\"";
                            break;
                    }
            }
        }
        return strCellValue;
    }

    private void moveImage(XSSFSheet sheet, int insertedRowCount, int startRow) {
        XSSFDrawing drawings = sheet.createDrawingPatriarch();
        for (XSSFShape shape : drawings.getShapes()) {
            if (shape instanceof Picture) {
                XSSFPicture picture = (XSSFPicture) shape;
                XSSFClientAnchor anchor = picture.getClientAnchor();
                int row1 = anchor.getRow1();
                if (row1 > startRow && row1 <= sheet.getLastRowNum()) {
                    int row2 = anchor.getRow2();
                    anchor.setRow1(row1 + insertedRowCount);
                    anchor.setRow2(row2 + insertedRowCount);
                }
            }
        }
    }


    public void copyRow(XSSFWorkbook workbook, XSSFSheet worksheet, int sourceRowNum, int destinationRowNum) {
        // Get the source / new row
        XSSFRow newRow = worksheet.getRow(destinationRowNum);
        XSSFRow sourceRow = worksheet.getRow(sourceRowNum);
        log.info("sourceRow :" + sourceRowNum + "-destinationRow:" + destinationRowNum);

        // If the row exist in destination, push down all rows by 1 else create a new row
//        if (newRow != null) {
        worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
        log.info("shiftRow");
//        } else {
//            newRow = worksheet.createRow(destinationRowNum);
//            log.info("create New Row");
//        }

        // Loop through source columns to add to new row
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            // Grab a copy of the old/new cell
            XSSFCell oldCell = sourceRow.getCell(i);
            XSSFCell newCell = newRow.createCell(i);

            // If the old cell is null jump to next cell
            if (oldCell == null) {
                newCell = null;
                continue;
            }

            // Copy style from old cell and apply to new cell
            XSSFCellStyle newCellStyle = workbook.createCellStyle();
            newCellStyle.cloneStyleFrom(oldCell.getCellStyle());

            newCell.setCellStyle(newCellStyle);

            // If there is a cell comment, copy
            if (oldCell.getCellComment() != null) {
                newCell.setCellComment(oldCell.getCellComment());
            }

            // If there is a cell hyperlink, copy
            if (oldCell.getHyperlink() != null) {
                newCell.setHyperlink(oldCell.getHyperlink());
            }

            // Set the cell data type
            newCell.setCellType(oldCell.getCellType());

            // Set the cell data value
            switch (oldCell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    newCell.setCellValue(oldCell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    newCell.setCellValue(oldCell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_ERROR:
                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    newCell.setCellFormula(oldCell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    newCell.setCellValue(oldCell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                    newCell.setCellValue(oldCell.getRichStringCellValue());
                    break;
            }
        }

        // If there are are any merged regions in the source row, copy to new row
        for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
                        (newRow.getRowNum() +
                                (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()
                                )),
                        cellRangeAddress.getFirstColumn(),
                        cellRangeAddress.getLastColumn());
                worksheet.addMergedRegion(newCellRangeAddress);
            }
        }
    }
}

