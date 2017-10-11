package br.com.jgeniselli.catalogacaolem.pendenciesSync;


import java.util.HashMap;
import java.util.List;

import br.com.jgeniselli.catalogacaolem.common.models.Ant;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.common.models.DataUpdateVisit;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;
import br.com.jgeniselli.catalogacaolem.common.service.ModelResponse;
import br.com.jgeniselli.catalogacaolem.common.service.restModels.RestAnt;
import br.com.jgeniselli.catalogacaolem.common.service.restModels.RestAntListRequest;
import br.com.jgeniselli.catalogacaolem.common.service.restModels.RestAntNest;
import br.com.jgeniselli.catalogacaolem.common.service.restModels.RestDataUpdateVisit;
import br.com.jgeniselli.catalogacaolem.common.service.restModels.RestPhoto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by jgeniselli on 17/09/17.
 */

public interface NestsSyncService {

    @POST("nestsByCities")
    Call<List<AntNest>> nestsByCities(@Body HashMap citiesIds);

    @POST("addNewNest")
    Call<ModelResponse> addNewNest(@Body RestAntNest nest);

    @POST("addDataUpdate")
    Call<ModelResponse> addNewDataUpdate(@Body RestDataUpdateVisit dataUpdateVisit);

    @POST("addAnts")
    Call<List<ModelResponse>> addAnts(@Body RestAntListRequest ants);

    @POST("addPhotos")
    Call<List<ModelResponse>> addPhotos(@Body List<RestPhoto> photos);
}
