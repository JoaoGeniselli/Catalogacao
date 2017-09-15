package br.com.jgeniselli.catalogacaolem.common.form.model;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.webkit.MimeTypeMap;

import org.androidannotations.annotations.EBean;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import br.com.jgeniselli.catalogacaolem.R;

/**
 * Created by jgeniselli on 15/09/2017.
 */

@EBean
public class ChooseFileManager {

    private static final String FOLDER_APP = "CatalogacaoLEM";
    private static final String PICTURE_EXTENSION = ".jpg";
    private static final String MASK_DATE_IMAGE = "dd-MM-yyyy_HHmmss";
    private static final int maxSizeBytes = 20971520;

    private List<String> extensionsAllowed = Arrays.asList("jpg", "jpeg", "png");

    private Uri selectedFile;

    public Intent buildIntentFileOrTakePicture(Context context) {
        // Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + FOLDER_APP + File.separator);
        root.mkdirs();
        final File sdImageMainDirectory = new File(root, generateFileName());
        selectedFile = Uri.fromFile(sdImageMainDirectory);

        // Camera
        final List<Intent> cameraIntents = new ArrayList<>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = context.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);

        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, selectedFile);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("*/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, context.getString(R.string.select_file));

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        return chooserIntent;
    }

    private boolean isPictureFromCamera(Intent data) {
        final boolean isCamera;
        if (data == null) {
            isCamera = true;
        } else {
            final String action = data.getAction();
            if (action == null) {
                isCamera = false;
            } else {
                isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            }
        }
        return isCamera;
    }

    public Uri getSelectedFile(Intent data) {
        if (isPictureFromCamera(data)) {
            return selectedFile;
        } else {
            return data.getData();
        }
    }

    private String generateFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat(MASK_DATE_IMAGE);
        String timeStamp = sdf.format(new Date());
        return FOLDER_APP + timeStamp + PICTURE_EXTENSION;
    }

    public boolean isSupportedExtension(Context context, Uri uri) {
        String extension = FilenameUtils.getExtension(uri.getPath());
        ContentResolver cr = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = mime.getExtensionFromMimeType(cr.getType(uri));
        return extensionsAllowed.contains(type) || extensionsAllowed.contains(extension);
    }

    public String getExtensionFromFile(Context context, Uri uri) {
        String extension = FilenameUtils.getExtension(uri.getPath());
        if (extension.isEmpty()) {
            ContentResolver cr = context.getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String type = mime.getExtensionFromMimeType(cr.getType(uri));
            return type;
        } else {
            return extension;
        }

    }

    public boolean isSupportedSize(Context context, Uri uri) {
        try {
            int size = getSizeFromFile(context, uri);
            return size < maxSizeBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Integer getSizeFromFile(Context context, Uri uri) {
        try {
            ContentResolver cr = context.getContentResolver();
            InputStream is = cr.openInputStream(uri);
            return is.available();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isSupportedFile(Context context, Uri uri) {
        boolean supportedExtension = isSupportedExtension(context, uri);
        boolean supportedSize = isSupportedSize(context, uri);
        return supportedExtension && supportedSize;
    }

    public String encodeToBase64(Context context, Uri uri) {
        try {
            ContentResolver cr = context.getContentResolver();
            InputStream is = cr.openInputStream(uri);
            byte[] bytes = IOUtils.toByteArray(is);
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getNameFile(Context context, Uri uri) {
        String uriString = uri.toString();
        String displayName = null;
        File file = new File(uriString);

        if (uriString.startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        } else if (uriString.startsWith("file://")) {
            displayName = file.getName();
        }

        return displayName;
    }


}
