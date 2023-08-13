package com.example.epokamission;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MissionActivity extends AppCompatActivity {
    private ArrayList<LibelleEtNo> lesLibellesEtNo = new ArrayList<>();
    private Spinner sVille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        lesLibellesEtNo = new ArrayList<>();
        sVille = findViewById(R.id.sVille);
        String urlServiceWebVilles = "http://172.16.43.205/scripts-PHP/villes.php";

        getServerDataJSONVilles(urlServiceWebVilles);

    }

    public void envoyer (View v) {
        EditText etDebutMission = findViewById(R.id.etDebutMission);
        EditText etFinMission = findViewById(R.id.etFinMission);
        LibelleEtNo libelleEtNo = (LibelleEtNo) (sVille.getSelectedItem());
        int idCommune = libelleEtNo.no;
        String urlServiceWebMission = "http://172.16.43.205/scripts-PHP/mission.php?debut=" + etDebutMission.getText() + "&fin=" + etFinMission.getText() + "&numCommune=" + idCommune + "&numEmp=" + AuthentificationActivity.idEmploye;

        if (etDebutMission.getText().toString().equals("") || etFinMission.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Veuillez saisir toutes les informations liées à votre mission", Toast.LENGTH_LONG).show();
        }

        else if (getServerDataTexteBrutMission(urlServiceWebMission).contains("La mission a été créée avec succès")) {
            Toast.makeText(getApplicationContext(), "Mission créée avec succès", Toast.LENGTH_LONG).show();
        }

        else {
            Toast.makeText(getApplicationContext(), "à l'aide", Toast.LENGTH_LONG).show();
        }
    }

    private String getServerDataJSONVilles (String urlServiceWebVilles) {
        //Autoriser les accès réseaux sur le thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ch = "";
        InputStream is = null;
        try {
            //Appel du serveur
            URL url = new URL(urlServiceWebVilles);
            HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
            connexion.connect();
            is = connexion.getInputStream();

            //Lecture des données
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String ligne;

            // Lecture des communes ligne par ligne
            while ((ligne = br.readLine())!=null) {
                ch = ch + ligne + "\n";
            }
            // Supprime les communes en trop
            lesLibellesEtNo.clear();

            // Décodage JSON
            try {
                JSONArray jArray = new JSONArray(ch);
                ch = "";
                for (int i=0; i<jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    int id = jsonObject.getInt("idCommune");
                    String libelle = jsonObject.getString("comNom");
                    String cp = jsonObject.getString("comCP");
                    // Affichage de la collection des communes
                    lesLibellesEtNo.add(new LibelleEtNo(libelle, id, cp));
                }
                // Associer la liste au spinner

                ArrayAdapter<LibelleEtNo> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lesLibellesEtNo);
                sVille.setAdapter(adapter);

            } catch (Exception e) {
                Toast.makeText(this, "Erreur récupération des données : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }



        } catch (Exception e) {
            Toast.makeText(this, "Erreur récupération des données : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
        return(ch);

    }

    private String getServerDataTexteBrutMission (String urlServiceWebMission) {
        //Autoriser les accès réseaux sur le thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ch = "";
        InputStream is = null;
        try {
            //Appel du serveur
            URL url = new URL(urlServiceWebMission);
            HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
            connexion.connect();
            is = connexion.getInputStream();



            //Lecture des données
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String ligne;
            while ((ligne = br.readLine())!=null) {
                ch = ch + ligne + "\n";
            }


        } catch (Exception e) {
            Toast.makeText(this, "Erreur récupération des données : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
        return(ch);
    }


    
    /*private String recupVillesJSON(String urlServiceWeb) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        InputStream is = null;
        String ch = "";
        
        try {
            //Appel du serveur
            URL url = new URL(urlServiceWeb);
            HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
            connexion.connect();
            is = connexion.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String ligne;
            while ((ligne = br.readLine())!=null) {
                ch = ch + ligne + "\n";
            }

            try {
                JSONArray jsonArray = new JSONArray(ch);
                ch = "";
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String libelle = jsonObject.getString("libelle");
                    int id = jsonObject.getInt("no");
                    String cp = jsonObject.getString("codePostal");
                    ch = ch + libelle + id + cp;

                }
            }
            catch (Exception e) {
                Toast.makeText(this, "Erreur récupération des données : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }

        }
        
        catch (Exception e) {
            Toast.makeText(this, "Erreur récupération des données : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
        return(ch);
    }*/


}
