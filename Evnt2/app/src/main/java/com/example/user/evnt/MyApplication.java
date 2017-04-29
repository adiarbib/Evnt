package com.example.user.evnt;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * This class is meant to setup the connection
 * between this app and the Parse server.
 */

public class MyApplication extends Application
{
    public static final String APPLICATION_ID = "a2IxzIPAzFaelatZwzP038qFhKIdLIuAc4tGsLET";
    public static final String CLIENT_KEY = "HJwXRtCY87SyafpzsnJOapBdQOknRjwqBNzRRgCG";
    public static final String SERVER = "https://parseapi.back4app.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        initParse();
    }

    /**
     * The method initiates the Parse with known constants
     * from the Parse website.
     */

    public void initParse() {
        ParseObject.registerSubclass(MyEvent.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(new Parse.Configuration.Builder(this)
                    .applicationId(APPLICATION_ID)
                   .clientKey(CLIENT_KEY)
                   .server(SERVER)
                    .build()
            );
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseUser.enableAutomaticUser();
    }
}
