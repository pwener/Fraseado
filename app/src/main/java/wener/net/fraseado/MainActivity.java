package wener.net.fraseado;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wener.net.fraseado.dao.PhraseDAO;
import wener.net.fraseado.model.Phrase;

/**
 * Main page is where insert and show phrases
 *
 */
public class MainActivity extends AppCompatActivity {

    private SavePhraseTask mSaveTask = null;

    private EditText phraseView;
    private View progressSaveView;
    private ListView listPhraseView;

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

        // @UNUSED
        progressSaveView = findViewById(R.id.save_progress);

        List<String> phrases = loadPhrases();

        // Creating adapter to list into view
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, phrases);

        listPhraseView = (ListView) findViewById(R.id.listPhraseView);
        listPhraseView.setAdapter(adapter);
    }

    /**
     * Select all phrases in database
     *
     * @return ArrayList<String> with content of phrases
     */
    private List<String> loadPhrases() {
        PhraseDAO phraseDAO = new PhraseDAO(this.getApplication());

        List<Phrase> phrases = phraseDAO.selectAll();
        List<String> phrasesContent = new ArrayList<String>();

        for(Phrase p : phrases) {
            phrasesContent.add(p.getContent());
        }

        Log.d(toString(), "Load " + phrasesContent.size() + " phrases...");

        return phrasesContent;
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

        Log.i(toString(), "Phrase are: " + phrase);

        if(!isValid(phrase)) {
            phraseView.requestFocus();
        } else {
            mSaveTask = new SavePhraseTask(this.getApplicationContext(), phrase);
            mSaveTask.execute((Void) null);
        }
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


    private class SavePhraseTask extends AsyncTask<Void, Void, Boolean> {

        private final String phraseContent;
        private final Context context;

        SavePhraseTask(Context context, String phrase) {
            this.phraseContent = phrase;
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            PhraseDAO phraseDAO = new PhraseDAO(context);

            Phrase phrase = new Phrase();
            phrase.setContent(phraseContent);

            phraseDAO.insert(phrase);

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            finish();
            reloadActivity();
        }

        /**
         * Reload current activity.
         */
        private void reloadActivity() {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MainActivity.class);

            startActivity(intent);
        }
    }
}
