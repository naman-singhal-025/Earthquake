package com.naman.earthquake;

import android.content.Context;

import android.content.AsyncTaskLoader;

import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<list_item>> {

    /** Query URL */
    private String mUrl="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

    /**
     * Constructs a new {@link EarthquakeLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public EarthquakeLoader(Context context, String url) {
        super(context);
//        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    /**
     * This is on a background thread.
     */
    @Override
    public ArrayList<list_item> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        ArrayList<list_item> earthquakes = QueryUtils.extractEarthquakes(mUrl);
        return earthquakes;
    }

}
