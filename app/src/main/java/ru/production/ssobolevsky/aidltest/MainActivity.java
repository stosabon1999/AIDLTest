package ru.production.ssobolevsky.aidltest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements IActivityCallback {

    private IMyAidlInterface mIMyAidlInterface;

    private Toolbar mToolbar;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(R.id.fl_content, TextFragment.newInstance())
                    .commit();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIMyAidlInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_edit :
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fl_content, EditFragment.newInstance())
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MyService.class);
        intent.setAction(IMyAidlInterface.class.getName());
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mServiceConnection);
    }


    @Override
    public void setData(String text) {
        try {
            mIMyAidlInterface.setData(text);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getData() {
        try {
            return mIMyAidlInterface.getData();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
