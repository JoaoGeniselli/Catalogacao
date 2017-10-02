/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.Rest;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author joaog
 */
public class RestPhoto {
    
    private Long antId;
    private Long nestId;
    private Long dataUpdateId;
    private String description;
    
    private String base64Photo;
    private MultipartFile imageFile;

    public RestPhoto() {
        
    }

    public Long getAntId() {
        return antId;
    }

    public void setAntId(Long antId) {
        this.antId = antId;
    }

    public Long getNestId() {
        return nestId;
    }

    public void setNestId(Long nestId) {
        this.nestId = nestId;
    }

    public Long getDataUpdateId() {
        return dataUpdateId;
    }

    public void setDataUpdateId(Long dataUpdateId) {
        this.dataUpdateId = dataUpdateId;
    }

    public String getBase64Photo() {
        return base64Photo;
    }

    public void setBase64Photo(String base64Photo) {
        this.base64Photo = base64Photo;
    }
    
    public Image decodedImage() throws IOException {
        if (base64Photo == null || base64Photo.length() == 0) {
            return null;
        }

        byte[] imageByte;

        imageByte = Base64.getDecoder().decode(base64Photo);

        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        BufferedImage image = ImageIO.read(bis);
        bis.close();

        // write the image to a file
        File outputfile = new File("image.png");
        ImageIO.write(image, "png", outputfile);

        return null;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
