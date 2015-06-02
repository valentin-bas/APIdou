package com.example.raveh.apidoubridge;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ItemSettingsActivity extends FragmentActivity
{

    private ApidouListener _apidouInUse;
    private ApidouManager _manager;
    private List<ApidouListener> _redirectionTargets = new ArrayList<ApidouListener>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apidou_item_settings);
        TextView title = (TextView)findViewById(R.id.titleNameTextView);
        _manager = ApidouManager.getInstance();
        UUID uuid = (UUID)getIntent().getSerializableExtra(MainActivity.ITEM_NAME_MESSAGE);
        _apidouInUse = _manager.getApidouWithUUID(uuid);
        title.setText(_apidouInUse.getName());
        ApidouListener voidApidou = new ApidouListener();
        voidApidou.setOverrideName("");
        _redirectionTargets.add(voidApidou);
        _redirectionTargets.addAll(_manager.getApidous());
        _redirectionTargets.remove(_apidouInUse);
        ItemArrayAdapter adapter = new ItemArrayAdapter(this, android.R.layout.simple_spinner_item, _redirectionTargets, ItemArrayAdapter.ItemType.Spinner);
        Spinner spinner = (Spinner)findViewById(R.id.eventsRedirectionSpinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(_redirectionItemSelected);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_apidou_item_settings, menu);
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
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //---------------Click listeners----------------------------------------------------------------

    private AdapterView.OnItemSelectedListener _redirectionItemSelected = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            if (position != 0)
                _apidouInUse.setRedirectionListener(_redirectionTargets.get(position));
            else
                _apidouInUse.setRedirectionListener(null);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    };
}
