package nvt.slpit.com;
import nvt.slpit.com.service.SliptService;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;

public class Application {
    
    public static void main(String[] args) {
        //read file from resource
        SliptService sliptService = new SliptService();
        sliptService.sliptExcelFile("mua_vao.xlsx", "/Volumes/DATA/nguyen.thom/projects/splitExcel/output/");
    }
    
}
