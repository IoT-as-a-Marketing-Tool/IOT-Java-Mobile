package com.example.iot_java_mobile;

import androidx.appcompat.app.AppCompatActivity;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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