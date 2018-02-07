package com.example.android.forzahomeassignmentteams.Utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Eleftherios Ch. on 7/2/2018.
 * Software Developer
 * lefterisxris@gmail.com
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
     * @return: The response as a String. "" empty if any error exists.
     * @throws IOException
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            /*// Check if any error http code exists.
            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_CREATED || urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK)
                return "";

            // Set timeout on 5 seconds.
            urlConnection.setConnectTimeout(5000);*/

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            }
            else {
                return null;
            }
        }
        catch (SocketTimeoutException e){
            System.out.println("Timeout Error.");
            return "";
        }
        finally {
            urlConnection.disconnect();
        }
    }

}
