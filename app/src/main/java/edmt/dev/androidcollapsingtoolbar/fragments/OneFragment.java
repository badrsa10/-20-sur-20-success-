package edmt.dev.androidcollapsingtoolbar.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import edmt.dev.androidcollapsingtoolbar.Event;
import edmt.dev.androidcollapsingtoolbar.HTTPURLConnection;
import edmt.dev.androidcollapsingtoolbar.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class OneFragment extends Fragment {


    ListView listView;
    TextView dure,time,localisation,details;
    //private String url_login = "https://2020succes.000webhostapp.com/deletevent.php";

    ArrayList<Event> lundi;
    String mail;



    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //listView.setAdapter(new EventAdaptor());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_one, container, false);
        Gson gson = new Gson();
        SharedPreferences sh = this.getActivity().getSharedPreferences("Sh",Context.MODE_PRIVATE);
        String slundi = sh.getString("lundi",null);



        listView = view.findViewById(R.id.list_lundi);
        if (slundi != null){
            Type type = new TypeToken<List<Event>>() {}.getType();
            lundi = gson.fromJson(slundi, type);
            //Log.e("CHECK",lundi.get(0).getDetails());
            listView.setAdapter(new EventAdaptor());
        }


        return view;
    }

   class EventAdaptor extends BaseAdapter{

        @Override
        public int getCount() {
            return lundi.size();
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

            convertView = getActivity().getLayoutInflater().inflate(R.layout.event,null);

            dure = convertView.findViewById(R.id.tvEventdetailsDate);
            time = convertView.findViewById(R.id.tvEventdetailsTime);
            localisation = convertView.findViewById(R.id.tvEventdetailsLocation);
            details = convertView.findViewById(R.id.tvEventdetailsBody);

            dure.setText(lundi.get(position).getDuration());
            time.setText(lundi.get(position).getTime());
            localisation.setText(lundi.get(position).getLocation());
            details.setText(lundi.get(position).getDetails());



            return convertView;
        }
    }

/*    class DeleteEventAsync extends AsyncTask<String,Void,Void>{
        String dure,time,localisation,details;
        HashMap<String, String> postDataParams;
        public JSONArray json;
        String response = "";
        @Override
        protected Void doInBackground(String... strings) {
            mail = strings[0];
            dure = strings[1];
            time = strings[2];
            localisation = strings[3];
            details = strings[4];

            postDataParams=new HashMap<String, String>();
            postDataParams.put("mail",mail);
            postDataParams.put("detail",details);
            postDataParams.put("localisation",localisation);
            postDataParams.put("duree",dure);
            postDataParams.put("time",time);
            postDataParams.put("day",new String("lundi"));
            //postDataParams.put("day",strings[1]);
            HTTPURLConnection service = new HTTPURLConnection();
            response = service.ServerData(url_login,postDataParams);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }*/



}
