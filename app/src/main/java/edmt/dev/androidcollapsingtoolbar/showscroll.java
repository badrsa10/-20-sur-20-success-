package edmt.dev.androidcollapsingtoolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import edmt.dev.androidcollapsingtoolbar.fragments.FiveFragment;
import edmt.dev.androidcollapsingtoolbar.fragments.FourFragment;
import edmt.dev.androidcollapsingtoolbar.fragments.OneFragment;
import edmt.dev.androidcollapsingtoolbar.fragments.SevenFragment;
import edmt.dev.androidcollapsingtoolbar.fragments.SixFragment;
import edmt.dev.androidcollapsingtoolbar.fragments.ThreeFragment;
import edmt.dev.androidcollapsingtoolbar.fragments.TwoFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class showscroll extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    FloatingActionButton addEvent;


    ArrayList<Event> lundi = new ArrayList<>();
    ArrayList<Event> mardi = new ArrayList<>();
    ArrayList<Event> mercredi = new ArrayList<>();
    ArrayList<Event> jeudi = new ArrayList<>();
    ArrayList<Event> vendredi = new ArrayList<>();
    ArrayList<Event> samedi = new ArrayList<>();
    ArrayList<Event> dimanche = new ArrayList<>();
    String mail;
    private String url_login = "https://2020succes.000webhostapp.com/event.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollable_tabs);
        mail = getIntent().getExtras().getString("mail");
        (new CalendarAsync()).execute(mail);

        addEvent = (FloatingActionButton) findViewById(R.id.addEvent);
        addEvent.hide();






    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "Lundi");
        adapter.addFrag(new TwoFragment(), "Mardi");
        adapter.addFrag(new ThreeFragment(), "Mercredi");
        adapter.addFrag(new FourFragment(), "Jeudi");
        adapter.addFrag(new FiveFragment(), "Vendredi");
        adapter.addFrag(new SixFragment(), "Samedi");
        adapter.addFrag(new SevenFragment(), "Dimanche");



        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    class CalendarAsync extends AsyncTask<String,Void,Void> {

        HashMap<String, String> postDataParams;
        public JSONArray json;
        String response = "";
        int i=0;

        @Override
        protected Void doInBackground(String[] strings) {
            postDataParams=new HashMap<String, String>();
            postDataParams.put("mail",strings[0]);
            //postDataParams.put("day",strings[1]);
            HTTPURLConnection service = new HTTPURLConnection();
            response = service.ServerData(url_login,postDataParams);
            Log.e("LIST",response.toString());

            try {
                json = new JSONArray(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int i=0;
            while (i<json.length()){
                try {
                    JSONObject object = json.getJSONObject(i);
                    Event event = new Event();

                    switch (object.get("day").toString()){
                        case "lundi":
                            event.setDetails(object.get("detail").toString());
                            event.setTime(object.get("time").toString());
                            event.setLocation(object.get("localisation").toString());
                            event.setDuration(object.get("duree").toString());
                            lundi.add(event);
                            break;
                        case "mardi":
                            event.setDetails(object.get("detail").toString());
                            event.setTime(object.get("time").toString());
                            event.setLocation(object.get("localisation").toString());
                            event.setDuration(object.get("duree").toString());
                            mardi.add(event);
                            break;
                        case "mercredi":
                            event.setDetails(object.get("detail").toString());
                            event.setTime(object.get("time").toString());
                            event.setLocation(object.get("localisation").toString());
                            event.setDuration(object.get("duree").toString());
                            mercredi.add(event);
                            break;
                        case "jeudi":
                            event.setDetails(object.get("detail").toString());
                            event.setTime(object.get("time").toString());
                            event.setLocation(object.get("localisation").toString());
                            event.setDuration(object.get("duree").toString());
                            jeudi.add(event);
                            break;
                        case "vendredi":
                            event.setDetails(object.get("detail").toString());
                            event.setTime(object.get("time").toString());
                            event.setLocation(object.get("localisation").toString());
                            event.setDuration(object.get("duree").toString());
                            vendredi.add(event);
                            break;
                        case "samedi":
                            event.setDetails(object.get("detail").toString());
                            event.setTime(object.get("time").toString());
                            event.setLocation(object.get("localisation").toString());
                            event.setDuration(object.get("duree").toString());
                            samedi.add(event);
                            break;
                        case "dimanche":
                            event.setDetails(object.get("detail").toString());
                            event.setTime(object.get("time").toString());
                            event.setLocation(object.get("localisation").toString());
                            event.setDuration(object.get("duree").toString());
                            dimanche.add(event);
                            break;
                    }

                    i++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            String slundi = new  Gson().toJson(lundi,new TypeToken<List<Event>>(){}.getType());
            SharedPreferences sh = getSharedPreferences("Sh",MODE_PRIVATE);
            SharedPreferences.Editor editor = sh.edit();
            editor.putString("lundi",slundi);
            editor.commit();
            String smardi = new  Gson().toJson(mardi,new TypeToken<List<Event>>(){}.getType());

            editor.putString("mardi",smardi);
            editor.commit();
            String smercredi = new  Gson().toJson(mercredi,new TypeToken<List<Event>>(){}.getType());

            editor.putString("mercredi",smercredi);
            editor.commit();

            String sjeudi = new  Gson().toJson(jeudi,new TypeToken<List<Event>>(){}.getType());

            editor.putString("jeudi",sjeudi);
            editor.commit();

            String svendredi = new  Gson().toJson(vendredi,new TypeToken<List<Event>>(){}.getType());

            editor.putString("vendredi",svendredi);
            editor.commit();

            String ssamedi = new  Gson().toJson(samedi,new TypeToken<List<Event>>(){}.getType());

            editor.putString("samedi",ssamedi);
            editor.commit();

            String sdimanche = new  Gson().toJson(dimanche,new TypeToken<List<Event>>(){}.getType());

            editor.putString("dimanche",sdimanche);
            editor.commit();

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            viewPager = (ViewPager) findViewById(R.id.viewpager);
            setupViewPager(viewPager);

            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);

        }
    }

}

