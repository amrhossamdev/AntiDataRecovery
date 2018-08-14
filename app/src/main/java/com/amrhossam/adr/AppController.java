package com.amrhossam.adr;

import android.app.Application;

import com.amrhossam.adr.anti_datarecovery.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/segoeuil.tff")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


    }


}
