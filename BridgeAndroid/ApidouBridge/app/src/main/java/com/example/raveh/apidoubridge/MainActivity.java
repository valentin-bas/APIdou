package com.example.raveh.apidoubridge;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.BeanManager;
import com.punchthrough.bean.sdk.message.Acceleration;
import com.punchthrough.bean.sdk.message.BatteryLevel;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.Callback;
import com.punchthrough.bean.sdk.message.ScratchBank;

public class MainActivity extends ActionBarActivity {

    private class MyDiscoveryListener implements BeanDiscoveryListener
    {
        public void onBeanDiscovered(Bean bean, int rssi)
        {
            TextView t = (TextView)findViewById(R.id.textview);
            t.append("New bean discovered ! Id : " + rssi + "\n");
            _bean = bean;
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    beanListener = new MyBeanListener();
                    _bean.connect(_context, beanListener);
                }
            });
        }

        public void onDiscoveryComplete()
        {
            TextView t = (TextView)findViewById(R.id.textview);
            t.append("Discovery complete !\n");
            if (_bean != null && _bean.isConnected())
                t.append("Connected" + "\n" );
            else
                t.append("Not connected" + "\n" );
        }
    }

    private class MyBeanListener implements BeanListener
    {
        public void onConnected()
        {
            TextView t = (TextView)findViewById(R.id.textview);
            t.append("Bean connected !\n");
            _bean.readAcceleration(_accelCallback);
        }

        public void onConnectionFailed()
        {
            TextView t = (TextView)findViewById(R.id.textview);
            t.append("Failed to connect on the bean !\n");
        }

        public void onDisconnected()
        {
            TextView t = (TextView)findViewById(R.id.textview);
            t.append("Bean disconnected !\n");
        }

        public void onSerialMessageReceived(byte[] data)
        {
            TextView t = (TextView)findViewById(R.id.textview);
            t.append("Bean message received !" + new String(data) +"\n");
        }

        public void onScratchValueChanged(ScratchBank bank, byte[] value)
        {
            TextView t = (TextView)findViewById(R.id.textview);
            t.append("Bean scratch value changed !"+ new String(value) +"\n");
        }

        public void onError(BeanError error)
        {
            TextView t = (TextView)findViewById(R.id.textview);
            t.append("Bean error !" +"\n");
        }
    }

    private MyDiscoveryListener discoveryListener;
    private MyBeanListener beanListener;
    private Context _context;
    private Bean _bean;

    private Callback<Acceleration> _accelCallback = new Callback<Acceleration>() {
        @Override
        public void onResult(Acceleration acceleration) {
            TextView t = (TextView)findViewById(R.id.textview);
            t.setText("Accel : " + acceleration.toString() + "\n");
            _bean.readAcceleration(_accelCallback);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        discoveryListener = new MyDiscoveryListener();
        TextView t = (TextView)findViewById(R.id.textview);
        t.setText("Start discovery !\n");
        _context = this;
        BeanManager.getInstance().startDiscovery(discoveryListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
