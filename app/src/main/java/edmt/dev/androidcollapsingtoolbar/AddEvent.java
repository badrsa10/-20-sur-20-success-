package edmt.dev.androidcollapsingtoolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;

import java.util.HashMap;

public class AddEvent extends AppCompatActivity {

    EditText location,jour,details,heur,minute,time;
    private String url_login = "https://2020succes.000webhostapp.com/addevent.php";
    Button button1;
    String mail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);


        button1 = (Button) findViewById(R.id.btnEventCreate);
        location = (EditText) findViewById(R.id.etEventLocation);
        jour = (EditText) findViewById(R.id.etEventTitle);
        details = (EditText) findViewById(R.id.etEventDetails);
        heur = (EditText) findViewById(R.id.etDurationHours);
        minute = (EditText) findViewById(R.id.etDurationMinutes);
        time = (EditText) findViewById(R.id.etEventTime);


        button1.setOnClickListener((View v) -> {
            (new UpdateCalendar()).execute();
            Intent intent = new Intent(AddEvent.this,ScrollableTabsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("mail",mail);
            bundle.putString("mail",mail);
            bundle.putString("prenonnom",getIntent().getExtras().getString("prenonnom"));
            bundle.putString("tel",getIntent().getExtras().getString("tel"));
            bundle.putString("niveau",getIntent().getExtras().getString("niveau"));
            bundle.putString("matiere",getIntent().getExtras().getString("matiere"));
            bundle.putString("prix",getIntent().getExtras().getString("prix"));
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    class UpdateCalendar extends AsyncTask<String,Void,Void>{

        String day = jour.getText().toString().toLowerCase();
        String temps = time.getText().toString()+"h";
        String duree = heur.getText().toString()+"h"+minute.getText().toString()+"min";
        String localisation = location.getText().toString();
        String detail = details.getText().toString();

        HashMap<String, String> postDataParams;
        public JSONArray json;
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mail = getIntent().getExtras().getString("mail");
        }

        @Override
        protected Void doInBackground(String... strings) {

            postDataParams=new HashMap<String, String>();
            postDataParams.put("mail",mail);
            postDataParams.put("detail",detail);
            postDataParams.put("localisation",localisation);
            postDataParams.put("duree",duree);
            postDataParams.put("time",temps);
            postDataParams.put("day",day);
            //postDataParams.put("day",strings[1]);
            HTTPURLConnection service = new HTTPURLConnection();
            response = service.ServerData(url_login,postDataParams);

            return null;
        }
    }
}