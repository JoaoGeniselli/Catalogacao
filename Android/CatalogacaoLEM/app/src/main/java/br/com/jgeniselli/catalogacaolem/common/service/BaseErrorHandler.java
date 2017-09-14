package br.com.jgeniselli.catalogacaolem.common.service;

import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.api.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;

/**
 * Created by jgeniselli on 14/09/17.
 */

@EBean
public class BaseErrorHandler  implements RestErrorHandler {

    @Override
    public void onRestClientExceptionThrown(NestedRuntimeException e) {

    }
}
