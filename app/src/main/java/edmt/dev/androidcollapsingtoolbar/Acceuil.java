package edmt.dev.androidcollapsingtoolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by hamza on 20/12/17.
 */

class Acceuil extends Activity{
    public TextView welcome;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil);
        welcome=(TextView)findViewById(R.id.textView40);

        Intent e = getIntent();
       String prenom = e.getStringExtra("prenom");
       welcome.setText(String.valueOf(prenom));

    }
}
