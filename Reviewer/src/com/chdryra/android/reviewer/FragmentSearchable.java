package com.chdryra.android.reviewer;

import android.app.ListFragment;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentSearchable extends ListFragment {

	private ListView mListView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_searchable, container, false);
		
		mListView = (ListView)v.findViewById(R.id.searchable_listview);
		
		handleSearch();
		return v;
	}
	
	private void handleSearch() {
	    Intent intent = getActivity().getIntent(); 
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      doSearch(query);
	    }
	}

	private void doSearch(String query) {
		Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
	}
}
