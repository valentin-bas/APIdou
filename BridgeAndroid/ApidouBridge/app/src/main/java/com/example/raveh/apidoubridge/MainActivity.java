package com.example.raveh.apidoubridge;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity
{

    public final static String ITEM_NAME_MESSAGE = "com.example.raveh.apidoubridge.ITEMNAMEMESSAGE";

    private ApidouManager _manager;
    private TextView _debugTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _debugTextView = (TextView)findViewById(R.id.debugTextView);
        _debugTextView.setText("");
        _manager = new ApidouManager();
        _manager.init(this);

        Button searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(_searchClickListener);

        ListView itemList = ((ListView)findViewById(R.id.apidouListView));
        _manager.discoveredNamesList.add("test");
        itemList.setAdapter(new ApidouItemArrayAdapter(this, _manager.discoveredNamesList));
        itemList.setOnItemClickListener(_itemClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            debugMessage("settings clicked");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void debugMessage(String message)
    {
        _debugTextView.setText(message + "\n");
    }


    //---------------Click listeners----------------------------------------------------------------

    private View.OnClickListener _searchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            boolean hasBluetooth = bluetoothAdapter != null;
            if (hasBluetooth && bluetoothAdapter.isEnabled() == true)
                _manager.startDiscovery();
            else if (hasBluetooth)
                Toast.makeText(getApplicationContext(), "Enable your bluetooth first", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "Can't find any bluetooth adapter", Toast.LENGTH_SHORT).show();
        }
    };

    private AdapterView.OnItemClickListener _itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            debugMessage("click on item " + position);
            Intent intent = new Intent(MainActivity.this, ApidouItemSettingsActivity.class);
            intent.putExtra(ITEM_NAME_MESSAGE, _manager.discoveredNamesList.get(position));
            startActivity(intent);
        }
    };

}
