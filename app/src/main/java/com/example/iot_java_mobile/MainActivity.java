package com.example.iot_java_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText name;
    EditText password;
    Button register;
    EditText loginEmail;
    EditText loginPassword;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
        setupListeners();

        private void setupUI() {
            loginEmail = findViewById(R.id.et_email_login);
            loginPassword = findViewById(R.id.et_password);
            register = findViewById(R.id.btn_register);
            login = findViewById(R.id.btn_login);
        }
        private void setupListeners() {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkUsername();
                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }

        void checkUsername() {
            boolean isValid = true;
            if (isEmpty(loginEmail)) {
                loginEmail.setError("You must enter email to login!");
                isValid = false;
            } else {
                if (!isEmail(loginEmail)) {
                    loginEmail.setError("Enter valid email!");
                    isValid = false;
                }
            }

            if (isEmpty(loginPassword)) {
                loginPassword.setError("You must enter password to login!");
                isValid = false;
            } else {
                if (loginPassword.getText().toString().length() < 4) {
                    loginPassword.setError("Password must be at least 4 chars long!");
                    isValid = false;
                }
            }

            if (isValid) {
                String emailValue = loginEmail.getText().toString();
                String passwordValue = password.getText().toString();
                if (emailValue.equals("test@test.com") && passwordValue.equals("password1234")) {
                    Intent i = new Intent(LoginActivity.this, FirstActivity.class);
                    startActivity(i);
                    this.finish();
                } else {
                    Toast t = Toast.makeText(this, "Wrong email or password!", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        }

        email = findViewById(R.id.et_email);
        name = findViewById(R.id.et_name);
        password = findViewById(R.id.et_password);
        register = findViewById(R.id.btn_register);


        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                checkDataEntered();
            }
        });
        boolean isEmail(EditText text) {
            CharSequence email = text.getText().toString();
            return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        }

        boolean isEmpty(EditText text) {
            CharSequence str = text.getText().toString();
            return TextUtils.isEmpty(str);
        }



        void checkDataEntered() {
            if (isEmpty(name)) {
                Toast t = Toast.makeText(this, "You must enter first name to register!", Toast.LENGTH_SHORT);
                t.show();
            }

            if (isEmail(email) == false) {
                email.setError("Enter valid email!");
            }
        }
    }

}