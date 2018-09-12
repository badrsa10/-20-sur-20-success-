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
import java.util.List;

/**
 * Created by hamza on 13/11/17.
 */

class Inscriptionprof extends Activity {
    //Database helper = new Database(this);
    NotificationCompat.Builder notification;
    int uniqueId =1234;

    Spinner spinner;
    Spinner spinner1;
    RadioButton selectedRadioButton;
    RadioButton Male;
    RadioButton Female;
    TextView Nom;
    TextView Prenom;
    TextView Mail;
    TextView Date;
    TextView Password;
    Button sinscrire,Image;
    TextView Password2;
    TextView Adress;
    TextView Numero;
    TextView prix;
    RadioGroup Gender;
    String convert;
    Bitmap bitmap;
    String[] exempleListe = {"...","primaire","college","lycée"};
    String[] exempleListe1 = {"...","education islamique","français","anglais","physique","maths","science","arabe","Histoire geo","phylosophie"};
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static String url_create_product = "https://2020succes.000webhostapp.com/create_professeur.php";
    String image;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.interface4);
        notification= new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        Image=(Button)findViewById(R.id.upld);
        sinscrire = (Button) findViewById(R.id.button5);
        Nom = (TextView) findViewById(R.id.a1);
        Prenom = (TextView) findViewById(R.id.a2);
        Mail = (TextView) findViewById(R.id.a3);
        Date = (TextView) findViewById(R.id.a4);
        Password = (TextView) findViewById(R.id.a5);
        Password2 = (TextView) findViewById(R.id.a9);
        Adress = (TextView) findViewById(R.id.a6);
        Numero = (TextView) findViewById(R.id.a7);
        prix = (TextView) findViewById(R.id.prix);
        Gender = (RadioGroup) findViewById(R.id.a8);
        Male = (RadioButton) findViewById(R.id.a10);
        Female=(RadioButton) findViewById(R.id.a11);

        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner1 = (Spinner) findViewById(R.id.spinner2);



        ArrayAdapter adapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item,exempleListe);
        spinner.setAdapter(adapter);
        ArrayAdapter adapter1 = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item,exempleListe1);
        spinner1.setAdapter(adapter1);
        Image.setOnClickListener((View v) -> {

            Intent intent = new Intent();

            intent.setType("image/*");

            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);


        });


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


    }

    private class code extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Inscriptionprof.this);
            pDialog.setMessage("Inscription en cours ...");
            pDialog.setCancelable(false);
            pDialog.show();
            image=encodeTobase64(bitmap);
        }

        @Override
        protected String doInBackground(String... strings) {
            String niveau = spinner.getSelectedItem().toString();
            String matiere = spinner1.getSelectedItem().toString();



            String nom = Nom.getText().toString();
            String prenom = Prenom.getText().toString();
            String mail = Mail.getText().toString();
            String date = Date.getText().toString();
            String password = Password.getText().toString();
            String password2 = Password2.getText().toString();
            String adress = Adress.getText().toString();
            String num = Numero.getText().toString();
            String pri = prix.getText().toString();
            String gender =".";
            if(selectedRadioButton.getId()==Male.getId()){
                gender= "Male";
            }else{
                gender="Female";
            }



            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mail", mail));
            params.add(new BasicNameValuePair("prenom",prenom ));
            params.add(new BasicNameValuePair("nom", nom));
            params.add(new BasicNameValuePair("date_de_naissance", date));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("tel", num));
            params.add(new BasicNameValuePair("localisation", adress));
            params.add(new BasicNameValuePair("gender", gender));
            params.add(new BasicNameValuePair("matiere", matiere));
            params.add(new BasicNameValuePair("image", image));
            params.add(new BasicNameValuePair("niveau", niveau));
            params.add(new BasicNameValuePair("prix", pri));
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
                    Toast.makeText(Inscriptionprof.this, "Coucou !", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(Inscriptionprof.this,login.class);
            PendingIntent pending =  PendingIntent.getActivity(Inscriptionprof.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pending);
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(uniqueId,notification.build());
            Intent i = new Intent(Inscriptionprof.this, login.class);
            startActivity(i);
            pDialog.dismiss();
        }
    }

//            String nom = Nom.getText().toString();
//            String prenom = Prenom.getText().toString();
//            String mail = Mail.getText().toString();
//            String date = Date.getText().toString();
//            String password = Password.getText().toString();
//            String password2 = Password2.getText().toString();
//            String adress = Adress.getText().toString();
//            String num = Numero.getText().toString();
//            String gender="";
//
//
//            if(Gender.getCheckedRadioButtonId()==-1)
//            {
//                Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                // get selected radio button from radioGroup
//                int selectedId = Gender.getCheckedRadioButtonId();
//                // find the radiobutton by returned id
//                RadioButton selectedRadioButton = (RadioButton)findViewById(selectedId);
//                gender = selectedRadioButton.getText().toString();
//
//            }
//            if (!password.equals(password2)){
//                Toast.makeText(getApplicationContext(),"confirm password erreur",Toast.LENGTH_LONG).show();
//
//            }else {
//                helper.insert1(nom,prenom,mail,date,password,adress,num,gender);
//
//            }
//
//        }
//
//        });
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

