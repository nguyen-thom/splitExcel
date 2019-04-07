package nvt.slpit.com.service;

import java.io.File;
import java.io.IOException;

public interface FileService {
    
    void copy(File source, File destination) throws IOException;
}
