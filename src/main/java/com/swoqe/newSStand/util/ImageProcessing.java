package com.swoqe.newSStand.util;

import com.swoqe.newSStand.model.services.PeriodicalPublicationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageProcessing {
    final static Logger logger = LogManager.getLogger(ImageProcessing.class);

    public static File bytesToImage(byte[] byteImg, String fileName){
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(byteImg, 0, byteImg.length);
        } catch (IOException e) {
            logger.error(e);
        }
        return file;
    }
}
