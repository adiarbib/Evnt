package com.example.user.evnt;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by User on 01/12/2016.
 */
public class MyApplication extends Application
{

    public static final String APPLICATION_ID = "a2IxzIPAzFaelatZwzP038qFhKIdLIuAc4tGsLET";
    public static final String CLIENT_KEY = "HJwXRtCY87SyafpzsnJOapBdQOknRjwqBNzRRgCG";
    public static final String SERVER = "https://parseapi.back4app.com";

    @Override
    public void onCreate() {
        super.onCreate();
        initParse();
    }

    public void initParse() {
        ParseObject.registerSubclass(MyEvent.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(APPLICATION_ID)
                .clientKey(CLIENT_KEY)
                .server(SERVER)
                .build()
        );
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
