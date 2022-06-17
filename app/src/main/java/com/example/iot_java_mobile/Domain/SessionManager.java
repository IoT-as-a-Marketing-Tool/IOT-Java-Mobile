package com.example.iot_java_mobile.Domain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.iot_java_mobile.Activity.LoginPage;
import com.example.iot_java_mobile.Activity.MainActivity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


public class SessionManager {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "IoTAuth";

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_USER = "user";

    public static final String KEY_TOKEN = "accessToken";
    public static final String KEY_PROFILE = "profile";


    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void createLoginSession(User u, String token){

        editor.putBoolean(IS_LOGIN, true);
        Gson gson = new Gson();
        String userJson = gson.toJson(u);
        editor.putString(KEY_USER, userJson);
        editor.putString(KEY_TOKEN, token);

        editor.commit();
    }
    public void createCustomer(String c){



        editor.putString(KEY_PROFILE, c);


        editor.commit();
    }
    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginPage.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }else {
            Intent i = new Intent(_context, MainActivity.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }

    }




    public User getUserDetails(){
        Gson gson = new Gson();
        String json = pref.getString(KEY_USER, "");
        User user = gson.fromJson(json, User.class);

        return user;
    }
    public Customer getCustomerDetails() throws JsonProcessingException {

        String json = pref.getString(KEY_PROFILE, "");
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);



        Customer customer = om.readValue(json, Customer.class);


        return customer;
    }
    public String getAuthToken(){
        String token = pref.getString(KEY_TOKEN, "");

        return token;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, LoginPage.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}