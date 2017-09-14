package br.com.jgeniselli.catalogacaolem.common.service;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

import br.com.jgeniselli.catalogacaolem.BuildConfig;
import br.com.jgeniselli.catalogacaolem.common.HttpBasicAuthenticatorInterceptor;
import br.com.jgeniselli.catalogacaolem.common.models.Ant;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.common.models.DataUpdateVisit;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;

/**
 * Created by jgeniselli on 14/09/17.
 */

@Rest(rootUrl = BuildConfig.SERVER_URL, converters = { MappingJackson2HttpMessageConverter.class }, interceptors = { HttpBasicAuthenticatorInterceptor.class })
public interface NestSyncRestClient extends RestClientErrorHandling {

    @Post("/nestsByCities")
    List<AntNest> nestsByCities(@Body List<Long> citiesIds);

    @Post("/addNewNest")
    ModelResponse addNewNest(@Body AntNest nest);


    @Post("/addNewDataUpdate")
    ModelResponse addNewDataUpdate(@Body DataUpdateVisit dataUpdateVisit);

    @Post("/addAnts")
    List<ModelResponse> addAnts(@Body List<Ant> ants);

    @Post("/addPhotos")
    List<ModelResponse> addPhotos(@Body List<PhotoModel> photos);
}
