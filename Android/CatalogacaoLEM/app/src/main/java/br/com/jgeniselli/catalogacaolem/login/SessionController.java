package br.com.jgeniselli.catalogacaolem.login;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import br.com.jgeniselli.catalogacaolem.common.CatalogacaoLEMApplication;
import br.com.jgeniselli.catalogacaolem.common.location.RestCountryModel;
import br.com.jgeniselli.catalogacaolem.common.location.RestCountryToRealmAdapter;
import br.com.jgeniselli.catalogacaolem.common.service.ServiceCallback;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jgeniselli on 19/09/17.
 */

@EBean
public class SessionController {

    private SessionService sessionService;

    public SessionController() {
        Retrofit retrofit = CatalogacaoLEMApplication.getDefaultRetrofit();
        sessionService = retrofit.create(SessionService.class);
    }

    @Background
    public void validateUser(User user, ServiceCallback<HashMap> completion) {
        Call<HashMap> call = sessionService.validateUser(user);

        Response<HashMap> response = null;
        Error error = null;
        try {
            response = call.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
            error = new Error("Ocorreu um erro ao obter as informações do usuário.");
            response = null;
        } finally {
            if (response == null) {
                completion.onFinish(null, error);
            }

            if (response.code() == HttpStatus.OK.value()) {
                HashMap responseBody = (HashMap) response.body();
                if (completion != null) {
                    completion.onFinish(response.body(), null);
                }
            } else if (response.code() == HttpStatus.UNAUTHORIZED.value()) {
                String message = "Usuário ou senha incorretos";

                error = new Error(message);
                completion.onFinish(response.body(), error);
            }
        }
    }

    @Background
    public void requestLocationContent(ServiceCallback<String> callback) {
        Call<List<RestCountryModel>> call = sessionService.locationContent();

        List<RestCountryModel> countries = null;
        Error error = null;
        try {
            countries = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            error = new Error("Erro ao obter carga inicial de cidades");
            countries = null;
        } finally {
            if (countries == null) {
                callback.onFinish(null, error);
            } else {
                if (countries.size() > 0){
                    RestCountryToRealmAdapter.saveCountryModel(countries.get(0));
                }
                callback.onFinish("Carga de cidades completa!", null);
            }
        }
    }
}
