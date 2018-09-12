package edmt.dev.androidcollapsingtoolbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by badr on 28/11/17.
 */

public class Search_Settings extends AppCompatActivity {

    GestureDetectorCompat gestureDetectorCompat;

    Spinner spinner,spinner1;
    //String niveaux;
    //String matieres;
    String[] niveau = {"...","primaire","college","lycée"};
    String[] matiere = {"...","education islamique","français","anglais","physique","maths","science","arabe","Histoire geo","phylosophie"};

    SeekBar seekBar;
    TextView distanceText;
    EditText prixx;
    int progresss = 10;

    Button search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_settings);

        search = (Button) findViewById(R.id.search);

        spinner = (Spinner) findViewById(R.id.spinner_niveau);
        spinner1 = (Spinner) findViewById(R.id.spinner_matiere);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,niveau);
        ArrayAdapter adapter1 = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,matiere);

        spinner.setAdapter(adapter);
        spinner1.setAdapter(adapter1);

        gestureDetectorCompat = new GestureDetectorCompat(this ,new LearnGesture());


        seekBar = (SeekBar) findViewById(R.id.seekBar);
        distanceText = (TextView) findViewById(R.id.distanceText);

        seekBar.setMax(30);
        seekBar.setProgress(progresss);

        distanceText.setText(progresss+"Km");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progresss = progress;
                distanceText.setText(progresss+"Km");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        prixx = (EditText) findViewById(R.id.prix);


        search.setOnClickListener((View v) ->{
            Integer prix =  Integer. parseInt(String.valueOf(prixx.getText()));
            String niveaux = spinner.getSelectedItem().toString();
            String matieres = spinner1.getSelectedItem().toString();
            Bundle bundle = getIntent().getExtras();
            Double lati = bundle.getDouble("latitude");
            Double longi = bundle.getDouble("longitude");
            Intent intent = new Intent(Search_Settings.this,Search_Result.class);
            Bundle bd = new Bundle();
            bd.putDouble("distance",progresss);
            bd.putDouble("latitude",lati);
            bd.putDouble("longitude",longi);
            bd.putString("niveau",niveaux);
            bd.putString("matiere",matieres);
            bd.putInt("prix",prix);
            intent.putExtras(bd);
            Log.e("Intent","Intent"+niveaux+" "+matieres+" "+progresss+" "+prix);
            startActivityForResult(intent,101);
        });





    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetectorCompat.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e2.getX() > e1.getX()){
                startActivity(new Intent(Search_Settings.this,Student.class));
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
            return true;
        }
    }
}
