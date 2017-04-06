package edu.uw.inputlistdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        Button searchButton = (Button) findViewById(R.id.btnSearch);
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.v(TAG, "You clicked again!!");
//            }
//        });

        //model
//        String[] data = new String[99];
//        for(int i=99; i>0; i--) {
//            data[99-i] = i+ " bottles of beer on the wall";
//        }
        ArrayList<String> data = new ArrayList<String>();



        //view
        //See XML

        //controller
        ListView listView = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter<String>(this, R.layout.list_item,
                                                                R.id.txtItem, data);
        listView.setAdapter(adapter);

    }

    public void handleButtonSearch(View v) {
        EditText searchQuery = (EditText)findViewById(R.id.txtSearch);
        String text = searchQuery.getText().toString();

//        Log.v(TAG, "You searched for " + text);

        MovieDownloadTask myTask = new MovieDownloadTask();
        myTask.execute(text);
    }

    public class MovieDownloadTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            String[] res = MovieDownloader.downloadMovieData(params[0]);

            for(String movie : res) {
                Log.v(TAG, movie);
            }

            return res;
        }

        @Override
        protected void onPostExecute(String[] movies) {
            super.onPostExecute(movies);

            if(movies != null) {
                adapter.clear();
//                adapter.addAll(movies);
                for(String movie : movies) {
                    adapter.add(movie);
                }
            }
        }
    }

}
