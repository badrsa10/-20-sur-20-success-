package edmt.dev.androidcollapsingtoolbar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v4.view.GestureDetectorCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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


public class Student extends FragmentActivity implements OnMapReadyCallback {
    static final int REQUEST_LOCATION = 1;


    private GestureDetectorCompat gestureDetectorCompat;

    TextView localisationstudent;
    Double latti_student,longi_student;
    LocationManager locationManager;
    FloatingActionButton modifier;
    FloatingActionButton search;
    FloatingActionButton logout;
    private GoogleMap mMap;
    TextView prenom ,niveau,tel;
    Bitmap be;
    ImageView photo;
    String image = "";
    String mail="";
    int tag =2;
    private static final String TAG_SUCCESS = "success";
    private String url_login = "https://2020succes.000webhostapp.com/image.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student);
        /*Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        //jjjj

        logout=findViewById(R.id.logout);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapviewstudent);
        mapFragment.getMapAsync(this);
        ///
        search = (FloatingActionButton) findViewById(R.id.search);
        search.setOnClickListener((View v) -> {
            Intent intent = new Intent(Student.this,Search_Settings.class);
            Bundle bundle = new Bundle();
            bundle.putDouble("latitude",latti_student);
            bundle.putDouble("longitude",longi_student);
            intent.putExtras(bundle);
            startActivityForResult(intent,101);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        });

        //vars

        localisationstudent = (TextView) findViewById(R.id.localisationstudent);


        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getLocation();


        //declaration

        prenom =(TextView)findViewById(R.id.nom);
        tel= (TextView)findViewById(R.id.tel);
        niveau=(TextView)findViewById(R.id.niveau);
        Intent e= getIntent();
        Bundle mybi =e.getExtras();
        prenom.setText(mybi.getString("prenonnom"));
        tel.setText(mybi.getString("tel"));
        niveau.setText(mybi.getString("niveau"));
        mail= mybi.getString("mail");
        photo=(ImageView)findViewById(R.id.images1);
        SharedPreferences sh = getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("mail",mail);
        editor.putString("prenom",mybi.getString("prenonnom"));
        editor.putString("tel",mybi.getString("tel"));
        editor.putString("niveau",mybi.getString("niveau"));
        editor.putInt("tag",tag);
        editor.commit();
        new codes().execute(mail,String.valueOf(latti_student),String.valueOf(longi_student));
        logout.setOnClickListener(v -> {

            editor.clear();
            editor.commit();
            startActivity(new Intent(Student.this,login.class));


        });



    }

    void getLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null){
                latti_student = location.getLatitude();
                longi_student = location.getLongitude();
                String addresse = getCompleteAddressString(latti_student,longi_student);
                localisationstudent.setText(addresse);
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
        LatLng locallocation = new LatLng(latti_student, longi_student);
        mMap.addMarker(new MarkerOptions().position(locallocation).title("Your current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(locallocation));
        //Zoom
        mMap.setMinZoomPreference(14.0f);
        mMap.setMaxZoomPreference(28.0f);
    }
    private class codes extends AsyncTask<String, String, String> {
        String response = "";
        ProgressDialog pDialog;
        HashMap<String, String> postDataParams;
        public JSONObject json;




        int success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Student.this);
            pDialog.setMessage("Chargement du profil ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            postDataParams=new HashMap<String, String>();
            postDataParams.put("latitude",strings[1]);
            postDataParams.put("longitude",strings[2]);
            postDataParams.put("mail",strings[0]);

            // Log.e("tag mail","mail:"+email);

            HTTPURLConnection service = new HTTPURLConnection();

            response = service.ServerData(url_login,postDataParams);

            try {
                Log.e("tag mail","mail:"+strings[0]);
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


                image = json.getString("image");
                be = decodeFromBase64ToBitmap(image);





            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String result) {


            if(success == 1) {

                photo.setImageBitmap(be);

            }
            pDialog.dismiss();
        }
    }
    private Bitmap decodeFromBase64ToBitmap(String encodedImage)

    {

        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);

        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}