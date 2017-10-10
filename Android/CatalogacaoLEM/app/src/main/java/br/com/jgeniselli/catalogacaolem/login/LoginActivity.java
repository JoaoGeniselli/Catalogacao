package br.com.jgeniselli.catalogacaolem.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.MyPreferences_;
import br.com.jgeniselli.catalogacaolem.common.Utils;
import br.com.jgeniselli.catalogacaolem.common.service.ServiceCallback;
import br.com.jgeniselli.catalogacaolem.main.MainActivity_;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @Pref
    MyPreferences_ prefs;

    @ViewById(R.id.username_field)
    public EditText usernameField;

    @ViewById(R.id.password_field)
    public EditText passwordField;

    @ViewById(R.id.enter_btn)
    public Button sendButton;

    @RestService
    SessionRestClient restClient;

    @Bean
    SessionController sessionController;

    @Click(R.id.enter_btn)
    public void onSendButtonClick() {
        signUp();
    }

    public void signUp() {
        String userId = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        if (userId.length() == 0 || password.length() == 0) {
            usernameField.setError("Insira seu nome de usuário");
            passwordField.setError("Insira sua senha");
        } else {
            validateUser(userId, password);
        }
    }

    @Background
    void validateUser(String userId, String password) {
        String cryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        final User user = new User(userId, cryptedPassword);
        sessionController.validateUser(user, new ServiceCallback<HashMap>() {
            @Override
            public void onFinish(HashMap response, Error error) {
                if (error != null) {
                    showAlert(error.getMessage());
                } else {
                    String username = (String) response.get("username");
                    String token = (String) response.get("token");

                    user.setName(username);
                    user.setToken(token);
                    updateSharedUser(user);

                    redirectToStart();
                }
            }
        });
    }

    @UiThread
    public void updateSharedUser(User user) {
        User.setSharedUser(user, this);
    }

    @UiThread
    public void showAlert(String message) {
        Utils.showAlert("Atenção", message, LoginActivity.this);
    }

    @UiThread
    public void redirectToStart() {
        StartActivity_.intent(this).start();
    }

    @Override
    public void onBackPressed() {

    }
}
