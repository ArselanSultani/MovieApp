package com.example.arsa.movieapp;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private final String LOG_TAG2 = MainActivityFragment.class.getSimpleName();
    GridView gridView;
    ImgAdapter imgAdapter;
    private ArrayList<String> film_Adapt = new ArrayList<>();
    String movieJsonString = null;


    public MainActivityFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gridview);
        FetchFilmData fetchFilmData = new FetchFilmData();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String pref_str = prefs.getString(getString(R.string.pref_sort_key),
                                        getString(R.string.pref_sort_pop));

        fetchFilmData.execute(pref_str);

        return  rootView;


    }


    public class FetchFilmData extends AsyncTask<String, Void, String[]> {
        private final String LOG_TAG = FetchFilmData.class.getSimpleName();
        //private ProgressDialog dialog;


        @Override
        protected void onPreExecute(){

        }

        @Override
        protected void onProgressUpdate(Void... values){

        }


        @Override
        protected void onPostExecute(String[] results) {

            String first_path = "http://image.tmdb.org/t/p/w500/";
            if(results!=null){
                film_Adapt.clear();
                for(String s: results){
                    String full_path_poster = first_path + s;
                    film_Adapt.add(full_path_poster);
                }
            }

            imgAdapter = new ImgAdapter(getActivity().getApplicationContext(), film_Adapt);
            gridView.setAdapter(imgAdapter);

        }


        @Override
        protected String[] doInBackground(String... params) {

            if(params.length == 0){
                return null;
            }
            HttpURLConnection urlConnection= null;
            BufferedReader bReader = null;




            try {

                final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
                final String MOVIE_SORT_BY = "sort_by";
                final String APPID_PARAM = "api_key";
                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(MOVIE_SORT_BY, params[0])
                        .appendQueryParameter(APPID_PARAM, BuildConfig.MOVIE_API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Built_Uri: " + builtUri.toString());



                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(inputStream == null){
                    return null;
                }

                bReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = bReader.readLine()) != null){
                    buffer.append(line+"\n");
                }

                if (buffer.length() == 0){
                    return null;
                }

                movieJsonString = buffer.toString();
            } catch (IOException e){
                Log.e(LOG_TAG,"Error while connecting ", e);
                return null;
            } finally {
                if(urlConnection != null) {
                    urlConnection.disconnect();
                }

                if(bReader != null){
                    try {
                        bReader.close();
                    } catch (final IOException e){
                        Log.e(LOG_TAG, "ERROR bReader not closing");
                    }
                }
            }

            try {
                return gettingInfoFromJSON(movieJsonString);
            } catch (JSONException e){
                Log.e(LOG_TAG, "Error in gettingInfoFromJSON function", e);
                e.printStackTrace();
            }

            return null;
        }

        public String[] gettingInfoFromJSON(String movieData) throws JSONException{
            final String OWN_LIST = "results";
            final String OWN_POSTER_PATH="poster_path";

            JSONObject movieJson = new JSONObject(movieData);
            JSONArray movieJSONArray = movieJson.getJSONArray(OWN_LIST);

            String[] result_poster_path = new String[movieJSONArray.length()];

            for(int i = 0; i< movieJSONArray.length(); i++){
                JSONObject this_Film_Object = movieJSONArray.getJSONObject(i);


                //JSONObject filmObject = this_Film_Object.getJSONArray(OWN_LIST).getJSONObject(0);
                String poster_path = this_Film_Object.getString(OWN_POSTER_PATH);

                result_poster_path[i] = poster_path;
            }


            return result_poster_path;
        }

    }



}
