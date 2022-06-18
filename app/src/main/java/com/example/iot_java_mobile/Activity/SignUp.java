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

import com.example.iot_java_mobile.Domain.SessionManager;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.example.iot_java_mobile.Domain.User;
import com.example.iot_java_mobile.R;
import com.example.iot_java_mobile.Services.APIClient;
import com.example.iot_java_mobile.Services.APIInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUp extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    @Length(min = 3, max = 10)
    private EditText username;

    @NotEmpty
    @Password
    @Pattern(regex = "^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$")
    private EditText password;

    @NotEmpty
    private EditText first_name;
    @NotEmpty
    private EditText last_name;
    @NotEmpty
    @Email
    private EditText email;


    private Button signUpButton;
    SessionManager session;


    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
        validator = new Validator(this);
        session = new SessionManager(getApplicationContext());

        validator.setValidationListener(this);

    }
    private void initView() {
        username = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        first_name = findViewById(R.id.first_name);
        email= findViewById(R.id.email);
        last_name = findViewById(R.id.last_name);
        password = findViewById(R.id.password);

        signUpButton = (Button) findViewById(R.id.btn_register);
        TextView toLogin =  findViewById(R.id.btn_login_signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validator.validate();

            }
        });
        toLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, LoginPage.class);
                startActivity(intent);


            }
        });
    }
    private void registerUser(User u) {

        APIInterface apiInterface = APIClient.authClient();

        apiInterface.createUser(u).enqueue(
                new Callback<Object>() {
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
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            Log.e("Don", "SUCCESSSSS " + u);


                            Intent i = new Intent(getApplicationContext(), CustomerSignUp.class);
                            startActivity(i);
                            finish();

                        }else{
                            Log.e("Don", String.valueOf(response));
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(SignUp.this, "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
                        Log.e("Don", t.getMessage());
                    }
                }
        );


    }
    @Override
    public void onValidationSucceeded() {
        User u = new User(username.getText().toString(), email.getText().toString(),first_name.getText().toString() ,last_name.getText().toString(),3,password.getText().toString());

        registerUser(u);

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