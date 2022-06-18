package com.example.iot_java_mobile.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iot_java_mobile.Domain.Customer;
import com.example.iot_java_mobile.Domain.Profile;
import com.example.iot_java_mobile.Domain.SessionManager;
import com.example.iot_java_mobile.R;
import com.example.iot_java_mobile.Services.APIClient;
import com.example.iot_java_mobile.Services.APIInterface;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerSignUp extends AppCompatActivity{

    EditText birthdate;
    private Button finishButton;
    SessionManager session;
    CountryCodePicker ccp;
    Spinner spinnerGenders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up);
        birthdate=(EditText) findViewById(R.id.customer_birthday);
        spinnerGenders=findViewById(R.id.spinner_genders);

        ccp = (CountryCodePicker) findViewById(R.id.countryCode_picker);
        ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(this, R.array.genders, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        spinnerGenders.setAdapter(adapter);

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar= Calendar.getInstance();

                DatePickerDialog datePickerDialog= new DatePickerDialog(CustomerSignUp.this,android.R.style.Theme_Holo_InputMethod,new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int month, int day) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH,month);
                        myCalendar.set(Calendar.DAY_OF_MONTH,day);
                        Log.e("Don", "SUCCESSSSS " + myCalendar.getTime());
                        updateLabel(myCalendar);
                    }
                },myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Select the date");
                datePickerDialog.show();

            }
        });
        session = new SessionManager(getApplicationContext());
        finishButton= findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String gender= spinnerGenders.getSelectedItem().toString().toLowerCase(Locale.ROOT);
                String birthday= birthdate.getText().toString();
                String country= ccp.getSelectedCountryEnglishName();
                Profile profile = new Profile(gender,birthday,country);
                Toast.makeText(CustomerSignUp.this,  profile.toString(),Toast.LENGTH_LONG).show();

               createProfile(profile);
            }
        });
    }
    private void updateLabel(Calendar myCalendar){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        Log.e("Don", "SUCCESSSSS " + dateFormat.format(myCalendar.getTime()));

        birthdate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void createProfile(Profile profile) {

        APIInterface apiInterface = APIClient.authClient();
        String token= "Bearer "+ session.getAuthToken();
        Log.e("Don", "Res " + token );

        apiInterface.createProfile(profile,token).enqueue(
                new Callback<Object>() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful() && response.body()!= null){
                            String res=new Gson().toJson(response.body());
                            Gson gson=new Gson();
                            Log.e("Don", "Res " + res );

                            try{

                                session.createCustomer(res);
                                Log.e("Don", "Session " +  session.getCustomerDetails());
                            }catch (Exception e){
                                Log.e("Don", "here " + e.getMessage());


                            }







                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();

                        }else{
                            Log.e("Don", String.valueOf(response));
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(CustomerSignUp.this, "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
                        Log.e("Don", t.getMessage());
                    }
                }
        );


    }


}