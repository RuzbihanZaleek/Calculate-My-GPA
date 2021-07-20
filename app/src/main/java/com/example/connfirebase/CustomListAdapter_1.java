package com.example.connfirebase;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter_1 extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] result;
    private final String[] subPra;

    public CustomListAdapter_1(Activity context, String[] result, String[] subPra) {
        super(context, R.layout.forcast_list, result);
        this.context = context;
        this.result = result;
        this.subPra = subPra;
    }

    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.forcast_list, null, true);
        TextView sCode = (TextView) rowView.findViewById(R.id.forcastList);
        TextView sName = (TextView) rowView.findViewById(R.id.prasantage);

        sCode.setText(result[position]);
        sName.setText(subPra[position]);
        return rowView;
    }
}


