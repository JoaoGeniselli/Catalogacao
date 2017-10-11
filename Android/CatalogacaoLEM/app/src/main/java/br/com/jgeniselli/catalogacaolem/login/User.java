package br.com.jgeniselli.catalogacaolem.login;

import android.content.Context;
import android.content.SharedPreferences;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by joaog on 05/08/2017.
 */

public class User {

    private static User sharedUser;
    private static String userPrefsId = "993450_p";
    private static String pref_key_user_id = "user_id";
    private static String pref_key_user_token = "user_token";
    private static String pref_key_user_name = "user_name";
    private static String pref_key_user_register_id = "user_register_id";


    private String name;
    private String username;
    private String password;
    private String token;
    private long registerId;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String name, String username, String password, String token) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public long getRegisterId() {
        //TODO: REMOVER MOCK
        return 1;
        //return registerId;
    }

    public void setRegisterId(long registerId) {
        this.registerId = registerId;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static void setSharedUser(User user, Context context) {
        SharedPreferences sharedPref = context
                .getSharedPreferences(userPrefsId, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(pref_key_user_name, StringUtils.defaultIfEmpty(user.getName(), ""));
        editor.putString(pref_key_user_id, StringUtils.defaultIfEmpty(user.getUsername(), ""));
        editor.putString(pref_key_user_token, StringUtils.defaultIfEmpty(user.getToken(), ""));
        editor.putLong(pref_key_user_register_id, user.getRegisterId());
        editor.commit();
    }


    public static User shared(Context context) {
        if (sharedUser == null) {
            sharedUser = new User();
        }
        SharedPreferences sharedPref = context
                .getSharedPreferences(userPrefsId, Context.MODE_PRIVATE);

        sharedUser.username = sharedPref.getString(pref_key_user_id, "");
        sharedUser.name = sharedPref.getString(pref_key_user_name, "");
        sharedUser.token = sharedPref.getString(pref_key_user_token, "");
        sharedUser.registerId = sharedPref.getLong(pref_key_user_register_id, 0);

        return sharedUser;
    }
}

