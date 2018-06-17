/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.usage.UsageEvents;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    public static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=1&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        //Hard coded earthquakes
        {/*earthquakes.add(new Earthquake("7.1","San Francisco","Feb 22, 2016" ));
        earthquakes.add(new Earthquake("8.9","Tokyo","Aug 23, 2009" ));
        earthquakes.add(new Earthquake("1.6","Paris","Mar 1, 2012" ));
        earthquakes.add(new Earthquake("4.2","Mumbai","Jan 12, 2013" ));
        earthquakes.add(new Earthquake("3.8","California","Nov 23, 2006" ));
        earthquakes.add(new Earthquake("8.2","Berlin","Jul 17, 2010" ));
        earthquakes.add(new Earthquake("6.4","Sydney","Dec 31, 2001" ));*/}

        QuakeTask task = new QuakeTask();
        task.execute();


    }

    public void updateUi(List<Earthquake> earthquakes){

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        EarthquakeAdapter adapter = new EarthquakeAdapter(
                this,earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
    }

    private class QuakeTask extends AsyncTask<String, Void, List<Earthquake>>{



        @Override
        protected List<Earthquake> doInBackground(String... urls) {

            // pass on query url to QueryUtils and get list of earthquakes
            List<Earthquake> earthquakes = QueryUtils.extractEarthquakes(USGS_REQUEST_URL);


            return earthquakes;
        }


        @Override
        protected void onPostExecute(List<Earthquake> earthquakes) {
            if(earthquakes==null)
                return;
            else
                updateUi(earthquakes);
        }
    }
}
