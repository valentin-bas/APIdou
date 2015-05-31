package com.example.raveh.apidoubridge;

import android.content.Context;
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
public class ApidouItemArrayAdapter extends ArrayAdapter<String>
{
    private final Context context;
    private final List<String> values;

    public ApidouItemArrayAdapter(Context context, List<String> values) {
        super(context, R.layout.apidou_list_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listView = inflater.inflate(R.layout.apidou_list_layout, parent, false);
        TextView textView = (TextView) listView.findViewById(R.id.apidouListTextView);
        textView.setText(values.get(position));
        String s = values.get(position);
        //ImageView imageView = (ImageView) listView.findViewById(R.id.icon);
        //imageView.setImageResource(R.drawable.no);

        return listView;
    }
}
