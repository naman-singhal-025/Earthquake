package com.naman.earthquake;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private static final String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson";

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;
    /**
     * Adapter for the list of earthquakes
     */
    private EarthquakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Initialise progress dialog
        ProgressDialog dialog = new ProgressDialog(this);

        //Set message
        dialog.setMessage("Please Wait...");

        //Set non cancellable
        dialog.setCancelable(true);

        //show progress dialog
        dialog.show();

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Check condition
                if(response!=null){
                    //when response is not null
                    //Dismiss progress dialog
                    dialog.dismiss();

                    ArrayList<list_item> earthquakes = QueryUtils.extractEarthquakes(response);
                    Collections.sort(earthquakes,list_item.list_itemComparator);
                    // Find a reference to the {@link ListView} in the layout
                    ListView earthquakeListView = (ListView) findViewById(R.id.list);

                    // Create a new {@link ArrayAdapter} of earthquakes
                    EarthquakeAdapter adapter = new EarthquakeAdapter(
                            MainActivity.this, earthquakes);

                    // Set the adapter on the {@link ListView}
                    // so the list can be populated in the user interface
                    earthquakeListView.setAdapter(adapter);

                    earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            // Find the current earthquake that was clicked on
                            list_item current = adapter.getItem(position);

                            // Convert the String URL into a URI object (to pass into the Intent constructor)
                            Uri earthquakeUri = Uri.parse(current.getUrl());

                            // Create a new intent to view the earthquake URI
                            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                            // Send the intent to launch a new activity
                            startActivity(websiteIntent);
                        }
                    });

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Display toast
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        //Initialise request Queue

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new EarthquakeAdapter(this, new ArrayList<>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);
        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                list_item currentEarthquake = mAdapter.getItem(position);
//                // Convert the String URL into a URI object (to pass into the Intent constructor)
//                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
//                // Create a new intent to view the earthquake URI
//                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
//                // Send the intent to launch a new activity
//                startActivity(websiteIntent);
            }
        }
//        // Get a reference to the LoaderManager, in order to interact with loaders.
//        LoaderManager loaderManager = getLoaderManager();
//        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
//        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
//        // because this activity implements the LoaderCallbacks interface).
//        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
//    }
//
//    @Override
//    public Loader<ArrayList<list_item>> onCreateLoader(int id, Bundle args) {
//        // Create a new loader for the given URL
//        return new EarthquakeLoader(this, url);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<ArrayList<list_item>> loader, ArrayList<list_item> earthquakes) {
//
//        // Clear the adapter of previous earthquake data
////        mAdapter.clear();
//
//        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
//        // data set. This will trigger the ListView to update.
//        if (earthquakes != null && !earthquakes.isEmpty()) {
//            mAdapter.addAll(earthquakes);
//        }
//        else
//        {
//            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    @Override
//    public void onLoaderReset(Loader<ArrayList<list_item>> loader) {
//        // Loader reset, so we can clear out our existing data.
//        mAdapter.clear();
//    }
//}