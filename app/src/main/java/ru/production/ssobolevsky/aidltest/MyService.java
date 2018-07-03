package ru.production.ssobolevsky.aidltest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IMyAidlInterface.Stub() {
            @Override
            public void setData(String text) throws RemoteException {
                SharedPreferences preferences = getSharedPreferences(Config.PREFS, Context.MODE_PRIVATE);
                preferences.edit()
                        .putString(Config.TEXT, text)
                        .apply();
            }

            @Override
            public String getData() throws RemoteException {
                SharedPreferences preferences = getSharedPreferences(Config.PREFS, Context.MODE_PRIVATE);
                return preferences.getString(Config.TEXT, "DEFAULT");
            }
        };
    }
}
