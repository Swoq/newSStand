package com.swoqe.newSStand.util;

import com.swoqe.newSStand.controllers.AddPublicationServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Tools {
    private final static Logger logger = LogManager.getLogger(Tools.class);
    public static Map<String, BigDecimal> toHashMap(String[] periods, String[] prices){
        Map<String, BigDecimal> periodsWithPrices = new HashMap<>();
        try {
            for (int i = 0; i < periods.length; i++) {
                BigDecimal n = BigDecimal.valueOf(Double.parseDouble(prices[i]));
                periodsWithPrices.put(periods[i], n);
            }
        }catch (NumberFormatException e){
            logger.error(e);
        }
        return periodsWithPrices;
    }

    public static File partToFile(Part filePart, ServletContext context, String UPLOAD_DIRECTORY){
        String[] splitFileName = filePart.getSubmittedFileName().split("\\.");
        String extension = splitFileName[splitFileName.length-1];
        final String fileName = UUID.randomUUID() + "." + extension;
        String path = context.getRealPath("") + File.separator + UPLOAD_DIRECTORY + File.separator + fileName;
        File file = new File(path);

        try (OutputStream out = new FileOutputStream(file);
             InputStream fileContent = filePart.getInputStream()){

            byte[] buffer = new byte[8 * 1024];
            int read;
            while ((read = fileContent.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            logger.info("New file created in path: {} ", path);
        } catch (IOException | NullPointerException e){
            logger.error(e);
        }
        return file;
    }

}
