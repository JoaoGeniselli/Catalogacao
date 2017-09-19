package br.com.jgeniselli.catalogacaolem.login;

import java.util.HashMap;
import java.util.List;

import br.com.jgeniselli.catalogacaolem.common.location.RestCountryModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by jgeniselli on 19/09/17.
 */

public interface SessionService {

    @POST("validateUser")
    Call<HashMap> validateUser(@Body User user);

    @GET("locationContent")
    Call<List<RestCountryModel>> locationContent();
}
