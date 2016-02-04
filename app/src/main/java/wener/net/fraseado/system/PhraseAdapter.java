package wener.net.fraseado.system;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import wener.net.fraseado.R;
import wener.net.fraseado.dao.PhraseDAO;
import wener.net.fraseado.model.Phrase;

public class PhraseAdapter extends ArrayAdapter<Phrase> {

    public PhraseAdapter(Context context, ArrayList<Phrase> phrases) {
        super(context, R.layout.activity_listview, phrases);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Phrase phrase = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview, parent, false);
        }

        setTexts(phrase, convertView);

        return convertView;
    }

    private void setTexts(Phrase phrase, View convertView) {
        TextView phraseContent = (TextView) convertView.findViewById(R.id.phraseContent);
        TextView phraseDate = (TextView) convertView.findViewById(R.id.phraseDate);
        TextView phraseID = null;

        phraseContent.setText(phrase.getContent());
        
        String timeAgo = getTimePass(phrase.getInitialDate());
        
        phraseDate.setText(timeAgo);
    }

    private String getTimePass(Date initialDate) {
        long now = System.currentTimeMillis();

        String relativeTime = DateUtils.getRelativeTimeSpanString(initialDate.getTime(), now,
                DateUtils.SECOND_IN_MILLIS).toString();

        Log.d(this.getClass().getSimpleName(), "Posted on " + relativeTime);

        return relativeTime;
    }
}
