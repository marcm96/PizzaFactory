package com.pfac.marcm.pizzafactory;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by marcm on 16/12/2017.
 */

public class PizzaFactory extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
