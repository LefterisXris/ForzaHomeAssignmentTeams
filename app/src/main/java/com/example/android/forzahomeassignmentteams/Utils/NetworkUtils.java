package com.example.android.forzahomeassignmentteams.Utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Eleftherios Ch. on 5/2/2018.
 */

public class NetworkUtils {

    private final static String FULL_PATH_URL = "https://s3-eu-west-1.amazonaws.com/forza-assignment/android/teams.json"; // Teams URL.
    private static final String TAG = NetworkUtils.class.getSimpleName(); // For Logging.

    /**
     * Method that builds and returns the URL.
     * @return: the URL tha I need. Null if any error occurs.
     */
    public static URL buildUrl(){

        // Building the uri to feed the URL.
        Uri builtUri = Uri.parse(FULL_PATH_URL).buildUpon().build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.v(TAG, ("Error on URL creating... Message: " + e.getMessage()));
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * Method that creates a http request and gets the response.
     * @param url: The specific url that I want to visit.
     * @return: The response as a String.
     * @throws IOException
     */
    public static  String getResponseFromHttpUrl(URL url) throws IOException {

        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            }
            else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
