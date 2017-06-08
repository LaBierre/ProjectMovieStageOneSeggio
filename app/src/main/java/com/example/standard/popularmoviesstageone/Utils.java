package com.example.standard.popularmoviesstageone;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vince on 04.06.2017.
 */

public class Utils {

    private static final String LOG_TAG = Utils.class.getName();

    public Utils() {
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(Context context, String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(Context context, URL url) throws IOException {

        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod(context.getResources().getString(R.string.get_http));
            urlConnection.connect();

            /*
            *  If the request was successful (response code 200),
            *  then read the input stream and parse the response.
            */
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(context, inputStream);
            } else {
                Log.e(LOG_TAG, context.getResources().getString(R.string.error_resp_code) + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, context.getResources().getString(R.string.io_exeption), e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                /*
                * Closing the input stream could throw an IOException, which is why
                * the makeHttpRequest(URL url) method signature specifies than an IOException
                * could be thrown.
                */
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(Context context, InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                    Charset.forName(context.getResources().getString(R.string.utf_8)));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Query the USGS dataset and return a list of {@link Movie} objects.
     */
    public static List<Movie> fetchMovieData(Context context, String requestUrl) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create URL object
        URL url = createUrl(context, requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(context, url);
        } catch (IOException e) {
            Log.e(LOG_TAG, context.getResources().getString(R.string.io_exeption_http), e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Movie}s
        List<Movie> movies = extractFeatureFromJson(context, jsonResponse);

        // Return the list of {@link Book}s
        return movies;
    }

    /**
     * Return a list of {@link Movie} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Movie> extractFeatureFromJson(Context context, String movieJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding movies to
        List<Movie> movies = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(movieJSON);
            JSONArray movieArray = baseJsonResponse.getJSONArray(context.getResources().getString(R.string.results_json));

            //For each movie in the movieArray create an @link Movie Object
            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject currentMovie = movieArray.getJSONObject(i);

                String title;
                if (currentMovie.has(context.getResources().getString(R.string.utils_title))) {
                    title = currentMovie.getString(context.getResources().getString(R.string.utils_title));
                } else {
                    title = "";
                }

                // Extract the value for the key "poster_path"
                String poster;
                if (currentMovie.has(context.getResources().getString(R.string.utils_poster))) {
                    poster = currentMovie.getString(context.getResources().getString(R.string.utils_poster));
                } else {
                    poster = "";
                }

                // Extract the value for the key "vote_avarage"
                double rating;
                if (currentMovie.has(context.getResources().getString(R.string.utils_rating))) {
                    rating = currentMovie.getDouble(context.getResources().getString(R.string.utils_rating));
                } else {
                    rating = 0;
                }

                // Extract the value for the key "overview"
                String overview;
                if (currentMovie.has(context.getResources().getString(R.string.utils_overview))) {
                    overview = currentMovie.getString(context.getResources().getString(R.string.utils_overview));
                } else {
                    overview = "";
                }

                // Extract the value for the key "release_date"
                String date;
                if (currentMovie.has(context.getResources().getString(R.string.utils_date))) {
                    date = currentMovie.getString(context.getResources().getString(R.string.utils_date));
                } else {
                    date = "";
                }

                Movie movie = new Movie(poster, title, overview, date, rating);
                movies.add(movie);
            }

        } catch (JSONException e) {
            /*
            * If an error is thrown when executing any of the above statements in the "try" block,
            * catch the exception here, so the app doesn't crash. Print a log message
            * with the message from the exception.
            */
            Log.e(LOG_TAG, context.getResources().getString(R.string.io_exepion_three), e);
        }
        return movies;
    }
}
