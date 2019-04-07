package nvt.slpit.com.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import nvt.slpit.com.service.FileService;

import java.io.*;

@Slf4j
public class FileServiceImpl implements FileService {
    
    public void copy(File source, File destination) throws IOException {
        if (source == null) {
            throw new NullPointerException("Null Source");
        }
        if (destination == null) {
            throw new NullPointerException("Null Destination");
        }
        if (source.isDirectory()) {
            copyDirectory(source, destination);
        } else {
            copyFile(source, destination);
        }
    }
    
    public void copyDirectory(File source, File destination) throws IOException {
        copyDirectory(source, destination, null);
    }
    
    public void copyDirectory(File source, File destination, FileFilter filter) throws IOException {
        File nextDirectory = new File(destination, source.getName());
        if (!nextDirectory.exists() && !nextDirectory.mkdirs()) {// create the directory if necessary...
            Object[] filler = {nextDirectory.getAbsolutePath()};
            String message = "Dir Copy Failed";
            throw new IOException(message);
        }
        File[] files = source.listFiles();
        for (int n = 0; n < files.length; ++n) {// and then all the items below the directory...
            if (filter == null || filter.accept(files[n])) {
                if (files[n].isDirectory()) {
                    copyDirectory(files[n], nextDirectory, filter);
                } else {
                    copyFile(files[n], nextDirectory);
                }
            }
        }
    }
    
    public void copyFile(File source, File destination) throws IOException {
        // what we really want to do is create a file with the same name in that dir
        if (destination.isDirectory()) {
            destination = new File(destination, source.getName());
        }
        
        FileInputStream input = new FileInputStream(source);
        copyFile(input, destination);
    }
    
    public static void copyFile(InputStream input, File destination) throws IOException {
        OutputStream output = null;
        try {
            log.info("destination:" + destination.getPath());
            output = new FileOutputStream(destination);
            byte[] buffer = new byte[1024];
            int bytesRead = input.read(buffer);
            while (bytesRead >= 0) {
                output.write(buffer, 0, bytesRead);
                bytesRead = input.read(buffer);
            }
        } catch (Exception e) {
            log.error("exception ", e);
            //
        } finally {
            input.close();
            output.close();
        }
        input = null;
        output = null;
    }
}
