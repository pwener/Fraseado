package wener.net.fraseado;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import wener.net.fraseado.dao.PhraseDAO;
import wener.net.fraseado.model.Phrase;

public class SavePhraseTask extends AsyncTask<Void, Void, Boolean> {

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
}
