package edmt.dev.androidcollapsingtoolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class login extends Activity{
    Bitmap be;
    ImageView photo ;
    Button inscription;
    Button login;
    String prenom="";
    String tmail="";
    String nom ="";
    String tel ="";
    String niveau="";
    String matiere="";
    String image="";
    String prix="";
    JSONParser jsonParser = new JSONParser();
    EditText mail,password;
    private static final String TAG_SUCCESS = "success";
    private String url_login = "https://2020succes.000webhostapp.com/login_etudiant.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface1);

        mail=(EditText) findViewById(R.id.editText);
        password=(EditText) findViewById(R.id.editText2);
        login= (Button) findViewById(R.id.button14);

        photo = (ImageView) findViewById(R.id.imageView2);
        photo.setImageDrawable(getResources().getDrawable(R.drawable.logoprincipal));
        inscription = (Button) findViewById(R.id.button);
        inscription.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Coucou !", Toast.LENGTH_SHORT).show();

                Intent appel = new Intent(login.this, Inscription.class);
                startActivity(appel);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                new code().execute(mail.getText().toString(),password.getText().toString());

            }
        });




        }
    private class code extends AsyncTask<String, String, String> {
        String response = "";
        ProgressDialog pDialog;
        HashMap<String, String> postDataParams;
        public JSONObject json;

        String email = mail.getText().toString();
        String epassword=password.getText().toString();

        int success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(login.this);
            pDialog.setMessage("Connexion en cours ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            postDataParams=new HashMap<String, String>();

            postDataParams.put("mail",strings[0]);
            postDataParams.put("password",strings[1]);
           // Log.e("tag mail","mail:"+email);

            HTTPURLConnection service = new HTTPURLConnection();
            response = service.ServerData(url_login,postDataParams);

            try {
                Log.e("tag mail","mail:"+strings[0]+" "+strings[1]);
                Log.e("RESP","RESP:"+response);
                json = (new JSONObject(response)).getJSONObject("0");


                //Get Values from JSONobject

               success =json.getInt(TAG_SUCCESS);

               /* if(success == 1) {
                    Intent e = new Intent(login.this,Acceuil.class);

                    //e.putExtra("mail",tmail);
                    //e.putExtra("prenom",prenom);

                    startActivity(e);

                    Toast.makeText(getApplicationContext(), "Connexion succefully..!", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Verifier votre connexion internet et ressayer..!", Toast.LENGTH_LONG).show();
                }*/
                 tmail = json.getString("mail");
                 prenom = json.getString("prenom");
                 nom = json.getString("nom");
                 niveau=json.getString("niveau");
                 tel=json.getString("tel");
                 matiere=json.getString("matiere");
                 prix = json.getString("prix");




                Log.e("Tag","success ::"+tmail+prenom);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


protected void onPostExecute(String result) {


            if(success == 1) {
                Intent e = new Intent(login.this,Student.class);

              Bundle mybundle = new Bundle();
              String pre = prenom+" "+nom;
              mybundle.putString("prenonnom",pre);
              mybundle.putString("niveau", niveau);
              mybundle.putString("tel",tel);
              mybundle.putString("mail",tmail);

                e.putExtras(mybundle);
                startActivityForResult(e,101);

                //Toast.makeText(getApplicationContext(), "Connexion succefully..!", Toast.LENGTH_LONG).show();
            }else if(success==2) {

                Intent e = new Intent(login.this,Teacher.class);

                Bundle mybundle = new Bundle();
                String pre = prenom+" "+nom;
                mybundle.putString("prenonnom",pre);
                mybundle.putString("niveau", niveau);
                mybundle.putString("tel",tel);
                mybundle.putString("mail",tmail);
                mybundle.putString("matiere",matiere);
                mybundle.putString("prix",prix);



                e.putExtras(mybundle);
                startActivityForResult(e,101);
            }else {
                Toast.makeText(getApplicationContext(), "Verifier votre connexion internet et ressayer..!", Toast.LENGTH_LONG).show();
            }
    pDialog.dismiss();
        }



    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }



    }

