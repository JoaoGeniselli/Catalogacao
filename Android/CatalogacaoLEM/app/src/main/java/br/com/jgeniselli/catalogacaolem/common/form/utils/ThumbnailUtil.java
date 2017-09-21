package br.com.jgeniselli.catalogacaolem.common.form.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import java.io.InputStream;

import br.com.jgeniselli.catalogacaolem.common.form.model.ChooseFileManager;


/**
 * Created by jgeniselli on 15/09/2017.
 */

public class ThumbnailUtil {

    private static final int WIDTH = 72;
    private static final int HEIGHT = 72;
    private static final int ROUND = 8;
    private static String[] imagesExtensionsAllow = new String[]{"jpg", "png", "gif", "jpeg"};



    public static RoundedBitmapDrawable getThumbailFromUri(Context context, Uri uri) {
        if (isFileImage(context, uri)) {
            try {
                InputStream is = context.getContentResolver().openInputStream(uri);
                Bitmap thumnail = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeStream(is), WIDTH, HEIGHT);
                RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(context.getResources(), thumnail);
                dr.setCornerRadius(ROUND);
                return dr;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static boolean isFileImage(Context context, Uri uri) {
        ChooseFileManager chooseFileManager = new ChooseFileManager();
        String fileName = chooseFileManager.getNameFile(context, uri);

        for (String extension : imagesExtensionsAllow) {
            if (fileName.toLowerCase().endsWith(extension))
                return true;
        }
        return false;
    }
}
