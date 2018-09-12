package edmt.dev.androidcollapsingtoolbar;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by hamza on 13/11/17.
 */

class Inscriptioneleve  extends Activity {
    NotificationCompat.Builder notification;
    Spinner spinner;
    TextView Nom;
    TextView Prenom;
    TextView Mail;
    TextView Date;
    TextView Password;
    Button sinscrire;
    Bitmap bitmap;
    String[] exempleListe = {"...","primaire","college","lycée"};
    //TextView Password2;
    TextView Adress;
    TextView Numero;
    RadioGroup Gender;
    RadioButton Male;
    RadioButton Female;
    Button image;
    int uniqueId =1234;
    RadioButton selectedRadioButton;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static String url_create_product = "https://2020succes.000webhostapp.com/create_etudient.php";
    String sniveau,snom,sprenom,smail,sdate,simage,sgender,spassword,sadress,snum;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface3);
        spinner = (Spinner) findViewById(R.id.spinner);
        notification= new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);



        ArrayAdapter adapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item,exempleListe);
         spinner.setAdapter(adapter);
        sinscrire =(Button) findViewById(R.id.button5);
        Nom = (TextView) findViewById(R.id.b1);
        Male = (RadioButton) findViewById(R.id.b10);
        Female=(RadioButton) findViewById(R.id.b9);
        Prenom = (TextView) findViewById(R.id.b2);
        Mail = (TextView) findViewById(R.id.b3);
        Date = (TextView) findViewById(R.id.b4);
        Password = (TextView) findViewById(R.id.b5);
       // Password2 = (TextView) findViewById(R.id.a9);
        Adress = (TextView) findViewById(R.id.b6);
        Numero = (TextView) findViewById(R.id.b7);
        Gender = (RadioGroup) findViewById(R.id.b8);
        image=(Button)findViewById(R.id.image1);

        sinscrire.setOnClickListener((View v) -> {
            selectedRadioButton = (RadioButton)findViewById(Gender.getCheckedRadioButtonId());
            if (v.getId() == R.id.button5) {
                if (Gender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_SHORT).show();
                } else if(!isValidEmailAddress(Mail.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please correct mail", Toast.LENGTH_SHORT).show();
                }else{
                    new code().execute();



                }

            }
        });
        image.setOnClickListener((View v) -> {

            Intent intent = new Intent();

            intent.setType("image/*");

            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);


        });


    }
    private class code extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;
        public JSONObject json;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Inscriptioneleve.this);
            pDialog.setMessage("Inscription en cours ...");
            pDialog.setCancelable(false);
            pDialog.show();
            simage=encodeTobase64(bitmap);


        }
        HashMap<String, String> postDataParams;
        String response;
        int success;



        @Override
        protected String doInBackground(String... strings) {

            sniveau = spinner.getSelectedItem().toString();
            snom = Nom.getText().toString();
            sprenom = Prenom.getText().toString();
            smail = Mail.getText().toString();
            sdate = Date.getText().toString();

            sgender =".";
            spassword = Password.getText().toString();
            //String password2 = Password2.getText().toString();
            sadress = Adress.getText().toString();
            snum = Numero.getText().toString();
            if(selectedRadioButton.getId()==Male.getId()){
                sgender= "Male";
            }else{
                sgender="Female";
            }



            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mail", smail));
            params.add(new BasicNameValuePair("prenom",sprenom ));
            params.add(new BasicNameValuePair("nom", snom));
            params.add(new BasicNameValuePair("date_de_naissance", sdate));
            params.add(new BasicNameValuePair("password", spassword));
            params.add(new BasicNameValuePair("tel", snum));
            params.add(new BasicNameValuePair("localisation", sadress));
            params.add(new BasicNameValuePair("gender", sgender));
            //params.add(new BasicNameValuePair("matiere", matiere));
            params.add(new BasicNameValuePair("image", simage));
            params.add(new BasicNameValuePair("niveau", sniveau));
            //params.add(new BasicNameValuePair("prix", pri));
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);
            Log.d("Create Response", json.toString());
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), login.class);
                    startActivity(i);

                    // closing this screen

                } else {
                    Toast.makeText(Inscriptioneleve.this, "Coucou !", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }





            return null;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            notification.setSmallIcon(R.drawable.chat1);
            notification.setContentText("Bonjour Monsieur "+Nom.getText().toString()+" votre inscription est bien faite ");
            notification.setTicker("20/20: new notification !!!");
            notification.setWhen(System.currentTimeMillis());
            notification.setContentTitle("20/20 : Inscription!!!!");
            notification.setVibrate(new long[] { 1000, 1000});
            notification.setSound(Uri.parse("uri://sadfasdfasdf.mp3"));
            Intent intent = new Intent(Inscriptioneleve.this,login.class);
            PendingIntent pending =  PendingIntent.getActivity(Inscriptioneleve.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pending);
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(uniqueId,notification.build());
            // successfully created product
            Intent i = new Intent(Inscriptioneleve.this, login.class);
            startActivity(i);
            pDialog.dismiss();


        }
    }


    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }
    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                //imageView.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


}