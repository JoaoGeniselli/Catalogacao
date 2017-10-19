/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller.site;

import br.com.jgeniselli.catalogacaoWS.util.DriveUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jgeniselli
 */
@Controller
public class MediaController extends BaseSiteController {
    
    @Value("${server.imagesPath}")
    private String imagesPath;
    
    @RequestMapping("/image/{imageId}")
    public void photo(HttpServletResponse response, @PathVariable String imageId) throws IOException {
        
        response.setContentType("image/png");
        InputStream in = DriveUtils.retrieveImage(imageId);
        IOUtils.copy(in, response.getOutputStream());

//        response.setContentType("image/jpeg");
//        File imgPath = new File(imagesPath + imageId + ".png");
//        InputStream in = new FileInputStream(imgPath);
//        IOUtils.copy(in, response.getOutputStream());
    }
    
}
