package com.example.raveh.apidoubridge;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Raveh on 31/05/2015.
 */
public class ItemArrayAdapter extends ArrayAdapter<ApidouListener>
{
    private final Context context;
    private final List<ApidouListener> values;
    private ItemType _selectedItemType;

    public enum ItemType
    {
        ListView,
        Spinner,
    }

    public ItemArrayAdapter(Context context, int layout, List<ApidouListener> values, ItemType itemType)
    {
        super(context, layout, values);
        this.context = context;
        this.values = values;
        _selectedItemType = itemType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (_selectedItemType == ItemType.ListView)
        {
            View listView = inflater.inflate(R.layout.apidou_list_layout, parent, false);
            TextView textView = (TextView) listView.findViewById(R.id.apidouListTextView);
            textView.setText(values.get(position).getName());
            //ImageView imageView = (ImageView) listView.findViewById(R.id.icon);
            //imageView.setImageResource(R.drawable.no);
            return listView;
        }
        else if ((_selectedItemType == ItemType.Spinner))
        {
            // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
            TextView label = new TextView(context);
            label.setTextColor(Color.BLACK);
            label.setText(values.get(position).getName());
            return label;
        }
        return null;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        if ((_selectedItemType == ItemType.Spinner))
        {
            TextView label = new TextView(context);
            label.setTextSize(20.f);
            label.setTextColor(Color.BLACK);
            label.setText(values.get(position).getName());
            return label;
        }
        return null;
    }
}
