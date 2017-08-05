package br.com.jgeniselli.catalogacaolem.login;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.HashMap;

import br.com.jgeniselli.catalogacaolem.BuildConfig;
import br.com.jgeniselli.catalogacaolem.common.HttpBasicAuthenticatorInterceptor;
import br.com.jgeniselli.catalogacaolem.common.location.RestCountryModel;

/**
 * Created by joaog on 05/08/2017.
 */

@Rest(rootUrl = BuildConfig.SERVER_URL, converters = { MappingJackson2HttpMessageConverter.class }, interceptors = { HttpBasicAuthenticatorInterceptor.class })
public interface SessionRestClient {

    @Post("/validateUser")
    ResponseEntity validateUser(@Body User user);

    @Get("/locationContent")
    RestCountryModel locationContent();
}
