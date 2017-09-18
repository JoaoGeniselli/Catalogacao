package br.com.jgeniselli.catalogacaolem.pendenciesSync;

import java.util.List;

import br.com.jgeniselli.catalogacaolem.common.models.Ant;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.common.models.DataUpdateVisit;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;
import br.com.jgeniselli.catalogacaolem.common.service.ModelResponse;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by jgeniselli on 17/09/17.
 */

public interface NestsSyncService {

    @POST("/nestsByCities")
    Call<List<AntNest>> nestsByCities(List<Long> citiesIds);

    @POST("/addNewNest")
    Call<ModelResponse> addNewNest(AntNest nest);


    @POST("/addNewDataUpdate")
    Call<ModelResponse> addNewDataUpdate(DataUpdateVisit dataUpdateVisit);

    @POST("/addAnts")
    Call<List<ModelResponse>> addAnts(List<Ant> ants);

    @POST("/addPhotos")
    Call<List<ModelResponse>> addPhotos(List<PhotoModel> photos);
}
