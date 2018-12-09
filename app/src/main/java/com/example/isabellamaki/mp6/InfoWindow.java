package com.example.isabellamaki.mp6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InfoWindow implements GoogleMap.InfoWindowAdapter {
    private final View mMoreInfo;
    private Context mContext;

    public InfoWindow(Context context) {
        mContext = context;
        mMoreInfo = LayoutInflater.from(context).inflate(R.layout.info_window, null);
    }

    private void rendowWindowText(Marker marker, View view){
        String title = marker.getTitle();
        TextView textView = (TextView) view.findViewById(R.id.title);

        if (!title.equals("")) {
            textView.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView snippetText = (TextView) view.findViewById(R.id.snippet);

        if (!snippetText.equals("")) {
            snippetText.setText(snippet);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mMoreInfo);
        return mMoreInfo;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mMoreInfo);
        return mMoreInfo;
    }
}
