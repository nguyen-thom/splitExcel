package nvt.slpit.com.service;

public interface SplitExcelService {

    int splitExcelFile(String input, String folderOutput, String templateFile, int startRow, int startIndex);
}
