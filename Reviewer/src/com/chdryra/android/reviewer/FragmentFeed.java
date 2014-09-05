package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;
import com.chdryra.android.reviewer.GVReviewOverviewList.GVReviewOverview;

public class FragmentFeed extends FragmentReviewGrid<GVReviewOverview> {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setGridViewData(Administrator.get(getActivity()).getFeed());
		setGridCellDimension(CellDimension.FULL, CellDimension.FULL);
		setBackgroundImageAlpha(0);
		setController(null);
		setDisplayHomeAsUp(false);
	}

	
	@Override
	protected void initSubjectUI() {
		super.initSubjectUI();
		getSubjectView().setHint(R.string.search_hint);
	}

	@Override
	protected void initRatingBarUI() {
		getTotalRatingBar().setVisibility(View.GONE);
	}

	@Override
	protected void initBannerButtonUI() {
		getBannerButton().setVisibility(View.GONE);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updateUI();
	}
	
	@Override
	protected void updateGridDataUI() {
		setGridViewData(Administrator.get(getActivity()).getFeed());
		((GridViewCellAdapter)getGridView().getAdapter()).setData(getGridData());
	}

	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
	
	}
	
	@Override
	protected void onGridItemLongClick(AdapterView<?> parent, View v, int position, long id) {
	
	}
	
	@Override
	protected void onDoneSelected() {
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_feed, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_new_review:
			requestNewReviewIntent();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void requestNewReviewIntent() {
		Intent i = new Intent(getActivity(), ActivityReviewBuild.class);
		startActivity(i);
	}	
}
