package nvt.slpit.com;
import nvt.slpit.com.service.SliptService;
import org.apache.commons.io.FilenameUtils;
import java.util.Scanner;

public class Application {
    
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        //read file from resource
        SliptService sliptService = new SliptService();
        System.out.print("Nhap duong dan cua file excel ban dau: ");
        String input = FilenameUtils.separatorsToSystem(sc.nextLine());
        System.out.print("Nhap duong dan thu muc can xuat ket qua (example: C://): ");
        String output = FilenameUtils.separatorsToSystem(sc.nextLine());
        System.out.print("Nhap duong dan cua file mau: ");
        String template = FilenameUtils.separatorsToSystem(sc.nextLine());
        System.out.print("Nhap so dong bat dau quet: ");
        int startRow = sc.nextInt();
        System.out.println("Input:" + input);
        System.out.println("Output:" + output);
        System.out.println("Template:" + template);
        sliptService.sliptExcelFile(input, output,template,startRow);
    }
    
}
