package com.github.zikani03;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Simple Http(s) client based on {@link java.net.URLConnection}
 */
class HttpClient {

    static String get(String fromUrl) throws HttpException {
        URL url;
        HttpURLConnection urlConnection;

        try {
            url = new URL(fromUrl);
        } catch (MalformedURLException e) {
            throw new HttpException("Invalid URL given", e);
        }

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(15_000); // 15 seconds
            urlConnection.connect();
        } catch (IOException ioe) {
            throw  new HttpException("Failed to open connection to the given URL", ioe);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {

            StringBuilder sb = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException ioe) {
            throw new HttpException("Failed to read data from URL", ioe);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    static class HttpException extends Exception {
        HttpException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
