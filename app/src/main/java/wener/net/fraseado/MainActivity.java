package wener.net.fraseado;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText phraseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phraseView = (EditText) findViewById(R.id.editText);

        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(this.toString(), "Trying saving...");
                savePhrase();
            }
        });
    }

    /**
     * Try to save phrase through PhraseDAO
     */
    private void savePhrase() {
        
    }

    @Override
    public String toString() {
        return MainActivity.class.getSimpleName();
    }
}
