package br.com.jgeniselli.catalogacaolem.common;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Created by joaog on 05/08/2017.
 */

public class HttpBasicAuthenticatorInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] data, ClientHttpRequestExecution execution) throws IOException {
        // do something
        System.out.println("Essa é a request:\n\n\n" + request.toString());

        return execution.execute(request, data);
    }
}

