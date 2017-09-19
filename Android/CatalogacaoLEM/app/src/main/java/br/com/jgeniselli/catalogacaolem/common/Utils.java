package br.com.jgeniselli.catalogacaolem.common;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by jgeniselli on 19/09/17.
 */

public class Utils {

    public static void showAlert(String title, String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setNeutralButton("OK", null);
        builder.create().show();
    }

}
