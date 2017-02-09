package com.example.user.evnt;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;

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
