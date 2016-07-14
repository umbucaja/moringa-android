package umbucaja.moringa.adapter;

import android.content.Context;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.SearchView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import umbucaja.moringa.R;

/**
 * Created by jordaoesa on 21/06/2016.
 */
public class SearchViewAdapter extends SearchView {

    private SearchView.SearchAutoComplete mSearchAutoComplete;

    public SearchViewAdapter(Context context) {
        super(context);
        initialize();
    }

    public void initialize() {
        mSearchAutoComplete = (SearchAutoComplete) findViewById(android.support.v7.appcompat.R.id.search_src_text);
        mSearchAutoComplete.setThreshold(1);
        mSearchAutoComplete.setHintTextColor(getResources().getColor(R.color.background_color));
        //mSearchAutoComplete.setBackground(getResources().getDrawable(R.color.background_color));

        mSearchAutoComplete.setDropDownBackgroundDrawable(getResources().getDrawable(android.support.v7.appcompat.R.drawable.abc_popup_background_mtrl_mult));
        this.setAdapter(null);
        this.setOnItemClickListener(null);
    }

    @Override
    public void setSuggestionsAdapter(CursorAdapter adapter) {}

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mSearchAutoComplete.setOnItemClickListener(listener);
    }

    public void setAdapter(ArrayAdapter<?> adapter) {
        mSearchAutoComplete.setAdapter(adapter);
    }

    public void setText(String text) {
        mSearchAutoComplete.setText(text);
    }
}
