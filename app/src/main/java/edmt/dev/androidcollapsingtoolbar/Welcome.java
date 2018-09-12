package edmt.dev.androidcollapsingtoolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by badr on 01/02/18.
 */

public class Welcome extends AppCompatActivity {
    SharedPreferences sh;
    ProgressBar progressBar;
    int progress ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        (new WelcomeAsync()).execute();

    }

    class WelcomeAsync extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = 0;
            progressBar.setProgress(progress);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            while (progress < 100){
                try {
                    Thread.sleep(20);
                    progress++;
                    progressBar.setProgress(progress);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            sh = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
            String mail = sh.getString("mail",null);
            if (mail == null){
                startActivity(new Intent(Welcome.this,login.class));
            }else {
                if (sh.getInt("tag",0)==1){
                    Intent intent = new Intent(Welcome.this,Teacher.class);
                    Bundle mybd = new Bundle();
                    mybd.putString("mail",sh.getString("mail",null));
                    mybd.putString("prenonnom",sh.getString("prenom",null));
                    mybd.putString("tel",sh.getString("tel",null));
                    mybd.putString("niveau",sh.getString("niveau",null));
                    mybd.putString("prix",sh.getString("prix",null));
                    mybd.putString("matiere",sh.getString("matiere",null));
                    intent.putExtras(mybd);
                    startActivityForResult(intent,101);
                }else{
                    Intent intent = new Intent(Welcome.this,Student.class);
                    Bundle mybd = new Bundle();
                    mybd.putString("mail",sh.getString("mail",null));
                    mybd.putString("prenonnom",sh.getString("prenom",null));
                    mybd.putString("tel",sh.getString("tel",null));
                    mybd.putString("niveau",sh.getString("niveau",null));
                    intent.putExtras(mybd);
                    startActivityForResult(intent,101);

                }
            }

        }
    }
}
