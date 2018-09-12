package edmt.dev.androidcollapsingtoolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by hamza on 07/11/17.
 */

public class Inscription extends Activity {
    ImageButton inscriptionetudient;

    ImageButton inscriptionprof;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface2);

        inscriptionetudient = (ImageButton) findViewById(R.id.button2);
        inscriptionetudient.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Coucou !", Toast.LENGTH_SHORT).show();

                Intent appel = new Intent(Inscription.this, Inscriptioneleve.class);
                startActivity(appel);

            }
        });
        inscriptionprof= (ImageButton) findViewById(R.id.button3);
        inscriptionprof.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Coucou !", Toast.LENGTH_SHORT).show();

                Intent appel = new Intent(Inscription.this, Inscriptionprof.class);
                startActivity(appel);

            }
        });

    }
}