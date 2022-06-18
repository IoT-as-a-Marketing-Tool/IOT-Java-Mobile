package com.example.iot_java_mobile.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_java_mobile.Domain.LoginUser;
import com.example.iot_java_mobile.Domain.SessionManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.QuickRule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.mobsandgeeks.saripaar.annotation.Url;
import com.example.iot_java_mobile.Domain.User;
import com.example.iot_java_mobile.R;
import com.example.iot_java_mobile.Services.APIClient;
import com.example.iot_java_mobile.Services.APIInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPage extends AppCompatActivity implements Validator.ValidationListener {


    @NotEmpty
    @Password
    private EditText password;


    @Email
    private EditText email;


    private Button loginButton;
    SessionManager session;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        session = new SessionManager(getApplicationContext());
        initView();
        validator = new Validator(this);
        validator.setValidationListener(this);

    }
    private void initView() {

        password = findViewById(R.id.login_password);
        email= findViewById(R.id.login_email);

        loginButton = (Button) findViewById(R.id.btn_login);
        TextView toLogin =  findViewById(R.id.btn_login_signup);
        toLogin.setText("Sign Up");
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validator.validate();

            }
        });
        toLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, SignUp.class);
                startActivity(intent);


            }
        });
    }
    private void loginUser(LoginUser u) {
        APIInterface apiInterface = APIClient.authClient();


        apiInterface.loginUser(u).enqueue(
                new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful() && response.body()!= null){

                            String res=new Gson().toJson(response.body());
                            Gson gson=new Gson();
                            User u=gson.fromJson(res,User.class);

                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(new Gson().toJson(response.body()));
                                String token =jsonObject.getString("access_token");;
                                session.createLoginSession(u, token);
                                getCustomer();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            Log.e("Don", "SUCCESSSSS " + u);


                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();

                        }

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(LoginPage.this, "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
                        Log.e("Don", t.getMessage());
                    }
                }
        );


    }

    private void getCustomer() {
        APIInterface apiInterface = APIClient.authClient();

        String token= "Bearer "+ session.getAuthToken();

        apiInterface.getCustomer(token).enqueue(
                new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful() && response.body()!= null){
                            String res=new Gson().toJson(response.body());
                            session.createCustomer(res);

                            Log.e("Don", "SUCCESSSSS " + res);



                        }


                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(LoginPage.this, "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
                        Log.e("Don", t.getMessage());
                    }
                }
        );


    }
    @Override
    public void onValidationSucceeded() {
        LoginUser u = new LoginUser(email.getText().toString(),password.getText().toString());

        loginUser(u);

    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

}