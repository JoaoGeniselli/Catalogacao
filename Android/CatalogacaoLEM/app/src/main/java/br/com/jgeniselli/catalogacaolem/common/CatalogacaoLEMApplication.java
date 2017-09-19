package br.com.jgeniselli.catalogacaolem.common;

import android.app.Application;
import android.content.Context;

import br.com.jgeniselli.catalogacaolem.BuildConfig;
import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by joaog on 05/08/2017.
 */

public class CatalogacaoLEMApplication extends Application {

    private static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }

    public static Retrofit getDefaultRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
