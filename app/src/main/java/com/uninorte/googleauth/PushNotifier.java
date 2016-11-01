package com.uninorte.googleauth;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by dbain on 01/11/2016.
 */

public class PushNotifier extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        /*POST PARA PUSH NOTIFICATIONS*/

        String post = params[0];
        try{
            URL url = new URL("http://190.144.171.172/g2movil/push_notifications.php");

            String my_token = FirebaseInstanceId.getInstance().getToken();
            //ACA SE COLOCA LA CADENA CON EL PARAMETRO
            String data = URLEncoder.encode("Message", "UTF-8") + "=" + URLEncoder.encode(post, "UTF-8");
            data += "&" + URLEncoder.encode("Forum", "UTF-8") + "=" + URLEncoder.encode("Foro General" , "UTF-8");
            data += "&" + URLEncoder.encode("Token", "UTF-8") + "=" + URLEncoder.encode(my_token, "UTF-8");

            BufferedReader reader = null;
            String text = "";

            //SEND POST DATA REQUEST
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            //GET THE SERVER RESPONSE
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            //READ SERVER RESPONSE
            while((line=reader.readLine()) != null){
                sb.append(line + "\n");
            }
            text = sb.toString();
            Log.d("PUSH_RESULT", text);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*END POST PARA PUSH NOTIFICATIONS*/
        return "";
    }
}
