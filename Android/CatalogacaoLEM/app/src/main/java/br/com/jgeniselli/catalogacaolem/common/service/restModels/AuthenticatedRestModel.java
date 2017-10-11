package br.com.jgeniselli.catalogacaolem.common.service.restModels;

import android.content.Context;

import java.io.Serializable;

import br.com.jgeniselli.catalogacaolem.login.User;

/**
 * Created by jgeniselli on 11/10/17.
 */

public class AuthenticatedRestModel implements Serializable {
    private String token;

    public AuthenticatedRestModel(Context context) {
        User user = User.shared(context);
        token = user.getToken();
    }
    public String getToken() {
        return token;
    }

}
