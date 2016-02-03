package wener.net.fraseado;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import wener.net.fraseado.model.Phrase;

public class MainActivity extends AppCompatActivity {

    private SavePhraseTask mSaveTask = null;

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
        if(mSaveTask != null) {
            // Another request of save already was thrown
            return;
        } else {
            // continue
        }

        // Reset errors.
        phraseView.setError(null);

        String phrase = phraseView.getText().toString();

        if(!isValid(phrase)) {
            phraseView.requestFocus();
        } else {
            mSaveTask = new SavePhraseTask(this.getApplicationContext(), phrase);
            mSaveTask.execute((Void) null);
        }

        Log.i(toString(), "Phrase are: " + phrase);
    }

    /**
     * Validate phrase
     *
     * @param phrase
     * @return
     */
    private boolean isValid(String phrase) {
        boolean isValid = false;

        if(TextUtils.isEmpty(phrase)) {
            phraseView.setError("Is invalid!");
        } else {
            isValid = true;
        }

        return isValid;
    }

    @Override
    public String toString() {
        return MainActivity.class.getSimpleName();
    }
}
