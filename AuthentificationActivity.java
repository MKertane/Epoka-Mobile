package com.example.epokamission;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthentificationActivity extends AppCompatActivity {
    public static String idEmploye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
    }

    public void valider (View v) {
        EditText etNumMDP = findViewById(R.id.etNumMDP);
        EditText etMDP = findViewById(R.id.etMDP);
        TextView tvResultat = findViewById(R.id.tvResultat);
        idEmploye = etNumMDP.getText().toString();


        if (etNumMDP.getText().toString().equals("") || etMDP.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Veuillez saisir toutes les informations liées à votre authentification", Toast.LENGTH_LONG).show();
        }
        else {
            String urlServiceWeb = "http://172.16.43.205/scripts-PHP/authentifier.php?id=" + etNumMDP.getText().toString() + "&motDePasse=" + etMDP.getText().toString();

            if (getServerDataTexteBrut(urlServiceWeb).contains("Bienvenue") ) {
                Toast.makeText(getApplicationContext(), "Bienvenue", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK + intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else {
                tvResultat.setText(getServerDataTexteBrut(urlServiceWeb));
                Toast.makeText(getApplicationContext(), "Identifiant et/ou mot de passe incorrect" + etNumMDP.getText() + etMDP.getText(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getServerDataTexteBrut (String urlServiceWeb) {
        //Autoriser les accès réseaux sur le thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        InputStream is = null;
        try {
            //Appel du serveur
            URL url = new URL(urlServiceWeb);
            HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
            connexion.connect();
            String ch = "";
            is = connexion.getInputStream();



            //Lecture des données
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String ligne;
            while ((ligne = br.readLine()) != null) {
                ch = ch + ligne;
            }
            return(ch);

        } catch (Exception e) {
            return e.getMessage();
        }
    }

}