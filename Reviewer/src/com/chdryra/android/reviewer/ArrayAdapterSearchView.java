package com.chdryra.android.reviewer;

import android.content.Context;
import android.support.v4.widget.CursorAdapter;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView.OnEditorActionListener;

import com.actionbarsherlock.R;
import com.actionbarsherlock.widget.SearchView;

public class ArrayAdapterSearchView extends SearchView {
	private SearchView.SearchAutoComplete mSearchAutoComplete;

	public ArrayAdapterSearchView(Context context) {
	    super(context);
	    initialize();
	}

	public ArrayAdapterSearchView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    initialize();
	}

	public void initialize() {
	    mSearchAutoComplete = (SearchAutoComplete) findViewById(R.id.abs__search_src_text);
	    this.setAdapter(null);
	    this.setOnItemClickListener(null);
	}

	@Override
	public void setSuggestionsAdapter(CursorAdapter adapter) {
	    // don't let anyone touch this
	}

	public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
	    mSearchAutoComplete.setOnItemClickListener(listener);
	}

	public void setAdapter(ArrayAdapter<?> adapter) {
	    mSearchAutoComplete.setAdapter(adapter);
	}
	
	public void setText(String text) { 
		mSearchAutoComplete.setText(text); 
	}

	public String getText() {
		return mSearchAutoComplete.getText().toString();
	}

	public void setOnEditorActionListener(
			OnEditorActionListener onEditorActionListener) {
		mSearchAutoComplete.setOnEditorActionListener(onEditorActionListener);
		
	} 
}
