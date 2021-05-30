package com.swoqe.newSStand.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageProcessing {
    final static Logger logger = LogManager.getLogger(ImageProcessing.class);

    public static File bytesToFile(byte[] byteImg, String fileName, String UPLOAD_DIRECTORY, String realPath){
        String path = realPath + File.separator + UPLOAD_DIRECTORY + File.separator + fileName;
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(byteImg, 0, byteImg.length);
        } catch (IOException e) {
            logger.error(e);
        }
        return file;
    }
}
