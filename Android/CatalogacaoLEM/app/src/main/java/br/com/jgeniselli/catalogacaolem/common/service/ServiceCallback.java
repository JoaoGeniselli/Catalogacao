package br.com.jgeniselli.catalogacaolem.common.service;

import org.springframework.http.ResponseEntity;

/**
 * Created by jgeniselli on 14/09/17.
 */

public interface ServiceCallback<T> {

    void onFinish(T response, Error error);
}
