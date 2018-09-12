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


public class FourFragment extends Fragment {

    ListView listView;
    TextView dure,time,localisation,details;
    ArrayList<Event> jeudi;

    public FourFragment() {
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
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        Gson gson = new Gson();
        SharedPreferences sh = this.getActivity().getSharedPreferences("Sh", Context.MODE_PRIVATE);
        String slundi = sh.getString("jeudi",null);


        listView = view.findViewById(R.id.list_jeudi);
        if (slundi != null){
            Type type = new TypeToken<List<Event>>() {}.getType();
            jeudi = gson.fromJson(slundi, type);
            //Log.e("CHECK",jeudi.get(0).getDetails());
            listView.setAdapter(new EventAdaptor());
        }
        return view;    }

    class EventAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return jeudi.size();
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

            dure.setText(jeudi.get(position).getDuration());
            time.setText(jeudi.get(position).getTime());
            localisation.setText(jeudi.get(position).getLocation());
            details.setText(jeudi.get(position).getDetails());



            return convertView;
        }
    }



}
