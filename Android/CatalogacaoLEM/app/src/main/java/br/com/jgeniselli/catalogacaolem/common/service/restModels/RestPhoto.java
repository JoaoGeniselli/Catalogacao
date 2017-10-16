package br.com.jgeniselli.catalogacaolem.common.service.restModels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import br.com.jgeniselli.catalogacaolem.common.form.model.ChooseFileManager;
import br.com.jgeniselli.catalogacaolem.common.models.Ant;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;
import br.com.jgeniselli.catalogacaolem.login.User;

/**
 * Created by jgeniselli on 20/09/17.
 */

public class RestPhoto {

    private long antId;
    private long nestId;
    private long dataUpdateId;
    private String image;
    private String description;

    public RestPhoto(PhotoModel photoModel, ChooseFileManager fileManager, Context context) {
        if (photoModel.getAnt() != null) {
            this.antId = photoModel.getAnt().getId();
        } else if (photoModel.getDataUpdateVisit() != null) {
            this.dataUpdateId = photoModel.getDataUpdateVisit().getDataUpdateId();
        } else if (photoModel.getAntNest() != null) {
            this.nestId = photoModel.getAntNest().getNestId();
        }


        byte[] byteArray = new byte[0];
        try {
            byteArray = FileUtils.readFileToByteArray(new File(photoModel.getFilePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String encodedImage = fileManager.encodeToBase64(context, photoModel.getFileURI());
        this.image = encodedImage;
        this.description = photoModel.getDescription();
    }
}
