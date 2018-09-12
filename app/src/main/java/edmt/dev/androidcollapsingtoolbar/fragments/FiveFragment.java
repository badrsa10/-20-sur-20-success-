package edmt.dev.androidcollapsingtoolbar.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import edmt.dev.androidcollapsingtoolbar.Event;
import edmt.dev.androidcollapsingtoolbar.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class FiveFragment extends Fragment {

    ListView listView;
    TextView dure,time,localisation,details;
    ArrayList<Event> vendredi;

    public FiveFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //(new CalendarAsync()).execute(mail,day);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_five, container, false);
        Gson gson = new Gson();
        SharedPreferences sh = this.getActivity().getSharedPreferences("Sh", Context.MODE_PRIVATE);
        String slundi = sh.getString("vendredi",null);


        listView = view.findViewById(R.id.list_vendredi);
        if (slundi != null){
            Type type = new TypeToken<List<Event>>() {}.getType();
            vendredi = gson.fromJson(slundi, type);
            //Log.e("CHECK",vendredi.get(0).getDetails());
            listView.setAdapter(new EventAdaptor());
        }
        return view;    }

    class EventAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return vendredi.size();
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

            dure.setText(vendredi.get(position).getDuration());
            time.setText(vendredi.get(position).getTime());
            localisation.setText(vendredi.get(position).getLocation());
            details.setText(vendredi.get(position).getDetails());



            return convertView;
        }
    }



}
