package edmt.dev.androidcollapsingtoolbar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class Show_Teacher extends FragmentActivity implements OnMapReadyCallback {
    static final int REQUEST_LOCATION = 1;
    FloatingActionButton calendar;

    TextView localisationteaher;
    Double latti_teacher,longi_teacher;
    LocationManager locationManager;
    Button suggestion;
    private GoogleMap mMap;
    TextView prenom ,niveau,tel,matiere,prix;
    Bitmap be;
    ImageView photo;
    String image = "";
    String mail="";
    private static final String TAG_SUCCESS = "success";
    private String url_login = "https://2020succes.000webhostapp.com/listprofil.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher);
        //Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        /*setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        //jjjj


        ///////
        suggestion = (Button) findViewById(R.id.suggestion);




        //declaration
        photo=(ImageView)findViewById(R.id.images);

        prenom =(TextView)findViewById(R.id.prenom);
        tel= (TextView)findViewById(R.id.tele);
        niveau=(TextView)findViewById(R.id.niveau1);
        matiere=(TextView)findViewById(R.id.matiere);
        prix=(TextView)findViewById(R.id.prix);
        Intent e= getIntent();
        Bundle mybi =e.getExtras();
        prenom.setText(mybi.getString("prenonnom"));
        tel.setText(mybi.getString("tel"));
        niveau.setText(mybi.getString("niveau"));
        matiere.setText(mybi.getString("matiere"));
        mail = mybi.getString("mail");
        prix.setText(mybi.getString("prix"));
        new code().execute(mail);


        //vars

        calendar = findViewById(R.id.calendar);
        calendar.setOnClickListener((View v) -> {
            Intent intent = new Intent(Show_Teacher.this,showscroll.class);
            Bundle bundle = new Bundle();
            bundle.putString("mail",mail);
            intent.putExtras(bundle);
            startActivityForResult(intent,101);
        });
    }



    void getLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null){
                //latti_teacher = location.getLatitude();
                //longi_teacher = location.getLongitude();
                String addresse = getCompleteAddressString(latti_teacher,longi_teacher);
                localisationteaher.setText(addresse);
            } else {
                //Toast.makeText(MainActivity.this, "Enable your GPS please", LENGTH_SHORT).show();

            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                //for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                strReturnedAddress.append(returnedAddress.getAddressLine(0));
                //}
                strAdd = strReturnedAddress.toString();
                Log.w("My Current address", strReturnedAddress.toString());
            } else {
                Log.w("My Current address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My address", "Canont get Address!");
        }
        return strAdd;
    }

    //Map

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in your location and move the camera
        LatLng locallocation = new LatLng(latti_teacher, longi_teacher);
        mMap.addMarker(new MarkerOptions().position(locallocation).title("Your current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(locallocation));
        //Zoom
        mMap.setMinZoomPreference(14.0f);
        mMap.setMaxZoomPreference(28.0f);
    }






    private class code extends AsyncTask<String, String, String> {
        String response = "";
        ProgressDialog pDialog;
        HashMap<String, String> postDataParams;
        public JSONObject json;




        int success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Show_Teacher.this);
            pDialog.setMessage("Chargement du profil ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            postDataParams=new HashMap<String, String>();

            //postDataParams.put("latitude",strings[1]);
            //postDataParams.put("longitude",strings[2]);
            postDataParams.put("mail",strings[0]);

            // Log.e("tag mail","mail:"+email);

            HTTPURLConnection service = new HTTPURLConnection();

            response = service.ServerData(url_login,postDataParams);

            try {
                Log.e("tag mail","mail:"+strings[0]);
                Log.e("RESP","RESP:"+response);
                json = (new JSONObject(response)).getJSONObject("0");


                //Get Values from JSONobject

                //success =json.getInt(TAG_SUCCESS);

               /* if(success == 1) {
                    Intent e = new Intent(login.this,Acceuil.class);

                    //e.putExtra("mail",tmail);
                    //e.putExtra("prenom",prenom);

                    startActivity(e);

                    Toast.makeText(getApplicationContext(), "Connexion succefully..!", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Verifier votre connexion internet et ressayer..!", Toast.LENGTH_LONG).show();
                }*/


                image = json.getString("image");
                be = decodeFromBase64ToBitmap(image);
                latti_teacher = Double.parseDouble(json.getString("latitude"));
                longi_teacher = Double.parseDouble(json.getString("longitude"));





            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String result) {


                photo.setImageBitmap(be);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapviewteacher);
            mapFragment.getMapAsync(Show_Teacher.this);
            ///////
            suggestion = (Button) findViewById(R.id.suggestion);

            //vars

            localisationteaher = (TextView) findViewById(R.id.localisationteacher);


            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            getLocation();
            pDialog.dismiss();
        }
    }









    private Bitmap decodeFromBase64ToBitmap(String encodedImage)

    {

        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);

        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;

    }

}