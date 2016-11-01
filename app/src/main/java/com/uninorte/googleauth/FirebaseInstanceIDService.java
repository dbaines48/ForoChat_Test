package com.uninorte.googleauth;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


/**
 * Created by dbain on 31/10/2016.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {


    private String TAG = "TAG DE MUESTRA";
    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, token);

        System.out.println("TOKEN ES " + token);
        registerToken(token);
    }

    private void registerToken(String token){
        /*OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token",token)
                .build();

        Request request = new Request.Builder()
                .url("http://190.144.171.172/g2movil/register.php") //Poner nuestra propia URL de metodo
                .post(body)
                .build();

        try {
            String response = client.newCall(request).execute().body().string();
            Log.d("RESPUESTA_HP_PHP", response);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try{
            URL url = new URL("http://190.144.171.172/g2movil/register.php");

            String data = URLEncoder.encode("Token", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8");
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
            Log.d(TAG, text);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
