package shelly.tripoto;


import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by chandresh.pancholi on 12/12/15.
 */
public class Http extends AsyncTask<String, Void, String> {

    private MainActivityCallback callback;


    public Http(MainActivityCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... arg) {
        String text = "";
        BufferedReader reader = null;
        String payload = arg[0];
        HttpURLConnection conn = null;
        OutputStreamWriter wr = null;
        System.out.println("Payload " + payload);
        try {
            URL url = new URL("http://www.tripoto.com/ApiPlacesNearby/getPlacesNearby");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(payload);
            wr.flush();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }
            text = sb.toString();
            wr.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (wr != null) {
                try {
                    wr.close();
                } catch (IOException e) {
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return text;
    }


    @Override
    protected void onPostExecute(String result) {
        callback.updateView(result);

    }
}