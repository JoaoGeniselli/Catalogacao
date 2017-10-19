/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.util;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;



/**
 *
 * @author jgeniselli
 */
public class ImageUtil {
    
    @Value("${spring.profiles.active}")
    private String environment;
    
    public static void saveImageFromBase64(
            String imageString, 
            String filepath) throws IOException {
        
        // create a buffered image
        BufferedImage image;
        byte[] imageByte;

        imageByte = Base64.getMimeDecoder().decode(imageString);

        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageByte)) {
            image = ImageIO.read(bis);
            bis.close();
        }

        // write the image to a file
        File outputfile = new File(filepath);
        ImageIO.write(image, "png", outputfile);
        
//        DriveUtils.insertImage(imageByte, filepath, "", filepath);
    }
    
    public static com.google.api.services.drive.model.File saveBase64ImageToDrive(
            String imageString, 
            String filepath) throws IOException {
        
        // create a buffered image
        BufferedImage image;
        byte[] imageByte;

        imageByte = Base64.getMimeDecoder().decode(imageString);

//        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageByte)) {
//            image = ImageIO.read(bis);
//            bis.close();
//        }
//
//        // write the image to a file
//        File outputfile = new File(filepath);
//        ImageIO.write(image, "png", outputfile);
        
        return DriveUtils.insertImage(imageByte, filepath, "", filepath);
    }
    
    public static void saveMultipartFile(
            MultipartFile multipartFile,
            String filepath) throws IOException {
        
        File outputfile = new File(filepath);
        
        multipartFile.transferTo(outputfile);
    }
    
    public static String generateImageFilename(String prefix, String sufix) {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String filename = timeStamp;
        if (prefix != null) {
            filename = String.format("%s%s", prefix, filename);
        }
        if (sufix != null) {
            filename = String.format("%s%s", filename, sufix);
        }
        return filename + ".png";
    }
    
    public void uploadImageToE3() {
        
    }
}
