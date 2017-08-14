package br.com.jgeniselli.catalogacaolem.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.MyPreferences_;
import br.com.jgeniselli.catalogacaolem.main.MainActivity_;

@EActivity
public class LoginActivity extends AppCompatActivity {

    @Pref
    MyPreferences_ prefs;

    @ViewById(R.id.username_field)
    public EditText usernameField;
    public EditText passwordField;

    @RestService
    SessionRestClient restClient;

    public Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = (EditText) findViewById(R.id.username_field);
        passwordField = (EditText) findViewById(R.id.password_field);
        sendButton = (Button)findViewById(R.id.enter_btn);

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
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
        User user = new User(userId, password);
        try {
            ResponseEntity<HashMap> response = restClient.validateUser(user);

            if (response.getStatusCode() == HttpStatus.OK) {

                HashMap responseBody = (HashMap) response.getBody();

                String username = (String) responseBody.get("username");
                String token = (String) responseBody.get("token");

                prefs.name().put(username);
                prefs.token().put(token);

                redirectToMain();
            } else if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                HashMap responseBody = (HashMap) response.getBody();
                String message = (String) responseBody.get("msg");

                showErrorAlert(message);
            }
        } catch (RestClientException e) {
            showErrorAlert(getString(R.string.default_login_rest_error));
        }
    }

    @UiThread
    void showErrorAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(R.string.default_login_rest_error);
        builder.setCancelable(true);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    @UiThread
    void redirectToMain() {
        Intent toMain = new Intent(this, MainActivity_.class);
        startActivity(toMain);
    }
}
