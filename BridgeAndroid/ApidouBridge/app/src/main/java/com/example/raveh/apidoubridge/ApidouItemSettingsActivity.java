package com.example.raveh.apidoubridge;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ApidouItemSettingsActivity extends FragmentActivity {

    private String _apidouName;
    private ApidouManager _manager;
    private List<String> _redirectionTargets = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apidou_item_settings);
        TextView title = (TextView)findViewById(R.id.titleNameTextView);
        _apidouName = getIntent().getStringExtra(MainActivity.ITEM_NAME_MESSAGE);
        title.setText(_apidouName);
        _manager = ApidouManager.getInstance();
        _redirectionTargets.add("none");
        _redirectionTargets.addAll(_manager.discoveredNamesList);
        _redirectionTargets.remove(_apidouName);
        Spinner spinner = (Spinner)findViewById(R.id.eventsRedirectionSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, _redirectionTargets);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(_redirectionItemSelected);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_apidou_item_settings, menu);
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

    //---------------Click listeners----------------------------------------------------------------

    private AdapterView.OnItemSelectedListener _redirectionItemSelected = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            boolean sucess;
            if (position != 0)
                sucess = _manager.redirectEventsToApidou(_apidouName, _redirectionTargets.get(position));
            else
                sucess = _manager.clearRedirection(_apidouName);
            if (!sucess)
                Toast.makeText(getApplicationContext(), "Unable to redirect : Bad name", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
