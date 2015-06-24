package edu.itba.hci.chopi.activity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import edu.itba.hci.chopi.R;

/**
 * Created by kevinkraus on 20/11/14.
 */
public class NoResultsActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_no_results);
        setTitle(getString(R.string.no_hay_resultados));
        String s = getString(R.string.no_results_string);
        TextView t = (TextView) this.findViewById(R.id.no_results);
        t.setText(s);
        setDrawer();


    }
}
