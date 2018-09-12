package edmt.dev.androidcollapsingtoolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Search_Result extends AppCompatActivity {
    ListView listView;
    TextView prenomnom,tel,niveau,matiere,adress,result,price;
    ImageView image,img1,img2;
    //Bitmap bitmap;
    //String sprenomnom,stel,sniveau,smatiere,slocation,simage;
    String mail;


    private static final String TAG_SUCCESS = "success";
    private String url_login = "https://2020succes.000webhostapp.com/download.php";
    Location location;
    List<Prof> list;
    List<Prof> l;
    String s = "result \n";
    Double distance,latitude,longitude;
    String niveaux,matieres;
    Integer prix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        //result = findViewById(R.id.result);
        //img1 = findViewById(R.id.img1);
        //img2 = findViewById(R.id.img2);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        distance = bd.getDouble("distance");
        niveaux = bd.getString("niveau");
        matieres = bd.getString("matiere");
        latitude = bd.getDouble("latitude");
        longitude = bd.getDouble("longitude");
        prix = bd.getInt("prix");



        listView = (ListView) findViewById(R.id.listView);

        list = new LinkedList<>();


        (new DownloadData()).execute(niveaux,matieres,String.valueOf(prix));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mail = list.get(position).getMail();
                Intent intent1 = new Intent(Search_Result.this,Show_Teacher.class);
                Bundle bundle = new Bundle();
                bundle.putString("mail",mail);
                String pre = list.get(position).getPrenom()+" "+list.get(position).getNom();
                bundle.putString("prenonnom",pre);
                bundle.putString("niveau", list.get(position).getNiveau());
                bundle.putString("tel",list.get(position).getTel());
                bundle.putString("matiere",list.get(position).getMatiere());
                bundle.putString("prix",list.get(position).getPrix());
                intent1.putExtras(bundle);
                startActivityForResult(intent1,101);
            }
        });


    }


    //-------------------------------------------------------------------------------------------------
    class ListAdaptor extends BaseAdapter{
        @Override
        public int getCount() {
            return l.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.flater,null);

            prenomnom = convertView.findViewById(R.id.prenomnom);

            niveau = convertView.findViewById(R.id.niveau);
            matiere = convertView.findViewById(R.id.matiere);
            adress = convertView.findViewById(R.id.adress);
            image = convertView.findViewById(R.id.image);
            price = convertView.findViewById(R.id.prix);

            final Location location = new Location("");
            location.setLatitude(latitude);
            location.setLongitude(longitude);

            Prof p = l.get(position);
            prenomnom.setText(p.getPrenom()+" "+p.getNom());

            niveau.setText(p.getNiveau());
            matiere.setText(p.getMatiere());
            adress.setText(Math.round(location.distanceTo(p.getLocation())/1000) +"Km");
            image.setImageBitmap(p.getImage());
            price.setText(p.getPrix());


            //(new FillingTask(prenomnom,tel,niveau,matiere,adress,image)).execute(position);

            return convertView;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /*class FillingTask extends AsyncTask<Integer,Void,Void>{

        TextView tprenomnom,ttel,tniveau,tmatiere,tadress;
        ImageView timage;

        public FillingTask(TextView prenomnom,TextView tel,TextView niveau,TextView matiere,TextView adress,ImageView image){
            this.tprenomnom = prenomnom;
            this.ttel = tel;
            this.tniveau = niveau;
            this.tmatiere = matiere;
            this.tadress = adress;
            this.timage = image;
        }


        @Override
        protected Void doInBackground(Integer... voids) {
            Prof p = l.get(voids[0]);
            prenomnom.setText(p.getPrenom()+" "+p.getNom());
            tel.setText(p.getTel());
            niveau.setText(p.getNiveau());
            matiere.setText(p.getMatiere());
            adress.setText(getCompleteAddressString(p.getLatitude(),p.getLongitude()));
            image.setImageBitmap(p.getImage());
            Log.e("Prof","Prof:"+p.getNom());
            return null;
        }
    }
*/




    //-------------------------------------------------------------------------------
    class DownloadData extends AsyncTask<String, Void, Void> {
        String response = "";
        //JSONObject js;
        JSONArray json;
        HashMap<String, String> postDataParams;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Search_Result.this);
            pDialog.setMessage("Chargement des resultats ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... voids) {
            postDataParams=new HashMap<String, String>();

            postDataParams.put("niveau",voids[0]);
            postDataParams.put("matiere",voids[1]);
            postDataParams.put("prix",voids[2]);
            Log.e("MSG: ",voids[0]+" "+voids[1]+" "+voids[2]);
            HTTPURLConnection service = new HTTPURLConnection();
            response = service.ServerData(url_login,postDataParams);

            try {
                json = new JSONArray(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int i=0;
            while (i<json.length()){
                try {
                    JSONObject object = json.getJSONObject(i);
                    Prof prof = new Prof();
                    prof.setNom(object.getString("nom"));
                    prof.setPrenom(object.getString("prenom"));
                    prof.setImage(decodeToImage(object.getString("image")));
                    prof.setTel(object.getString("tel"));
                    prof.setNiveau(object.getString("niveau"));
                    prof.setMatiere(object.getString("matiere"));
                    prof.setLatitude(Double.parseDouble(object.getString("latitude")));
                    prof.setLongitude(Double.parseDouble(object.getString("longitude")));
                    prof.setPrix(object.getString("prix"));
                    prof.setMail(object.getString("mail"));



                    list.add(prof);
                    i++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            final Location location = new Location("");
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            //l = list.stream().filter((Prof p) -> ((location.distanceTo(p.getLocation()))/1000)<20 ).collect(Collectors.toList());
            l=new LinkedList<>();
            for (int i = 0; i < list.size(); i++){
                Prof prof = list.get(i);
                if (((location.distanceTo(prof.getLocation()))/1000)<distance) l.add(prof);
                //String string = prof.getPrenom()+" "+prof.getNom()+" "+prof.getLatitude().getClass()+" "+prof.getLongitude()+"\n";
                //s += string;
            }
            listView.setAdapter(new ListAdaptor());

            /*for (int i = 0; i < list.size(); i++){
                Prof prof = list.get(i);
                String string = prof.getPrenom()+" "+prof.getNom()+" "+prof.getLatitude().getClass()+" "+prof.getLongitude()+"\n";
                s += string;
            }

            s+="FILTER \n";

            for (int i = 0; i < l.size(); i++){
                Prof prof = l.get(i);
                String string = prof.getPrenom()+" "+prof.getNom()+" "+prof.getLatitude().getClass()+" "+prof.getLongitude()+"\n";
                s += string;
            }

            result.setText(s);
            img1.setImageBitmap(l.get(0).getImage());
            img2.setImageBitmap(l.get(1).getImage());*/
            pDialog.dismiss();
        }

    }


    //-------------------------------------------------------------------------------------------------------
    public static Bitmap decodeToImage(String imageString) {

            byte[] imageByte = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap bm = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            return bm;

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



}
