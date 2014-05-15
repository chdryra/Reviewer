package com.chdryra.android.reviewer;

import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;

public abstract class FragmentReviewGrid extends FragmentReviewBasic{

public enum CellDimension{FULL, HALF, QUARTER}; 
	
	private TextView mSubjectView;
	private RatingBar mTotalRatingBar;
	private Button mAddDataButton;
	private GridView mGridView;

	int mMaxGridCellWidth;
	int mMaxGridCellHeight;
	
	int mCellWidthDivider = 1;
	int mCellHeightDivider = 1;

	protected abstract void initUI();
	protected abstract void updateUI();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_review_grid, container, false);			
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mSubjectView = (TextView)v.findViewById(R.id.review_subject_edit_text);
		mTotalRatingBar = (RatingBar)v.findViewById(R.id.total_rating_bar);
		mAddDataButton = (Button)v.findViewById(R.id.add_data_button);
		mGridView = (GridView)v.findViewById(R.id.data_gridview);
		
		//***Get display metrics for reviewTag display size***//
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		
		mMaxGridCellWidth = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels);				
		mMaxGridCellHeight = mMaxGridCellWidth;
		
		setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
		
		initUI();
		updateUI();
		
		return v;		
	}

	protected void setGridCellDimension(CellDimension width, CellDimension height) {
		mCellWidthDivider = 1;
		mCellHeightDivider = 1;
		
		if(width == CellDimension.HALF)
			mCellWidthDivider = 2;
		else if(width == CellDimension.QUARTER)
			mCellWidthDivider = 4;
		
		if(height == CellDimension.HALF)
			mCellHeightDivider = 2;
		else if(height == CellDimension.QUARTER)
			mCellHeightDivider = 4;
	}
	
	protected int getGridCellWidth() {
		return mMaxGridCellWidth / mCellWidthDivider;
	}

	protected int getGridCellHeight() {
		return mMaxGridCellHeight / mCellHeightDivider;
	}
	
	protected int getNumberColumns() {
		return mCellWidthDivider;
	}
	
	protected TextView getSubjectView() {
		return mSubjectView;
	}
	
	protected RatingBar getTotalRatingBar() {
		return mTotalRatingBar;
	}
	
	protected Button getAddDataButton() {
		return mAddDataButton;
	}
	
	protected GridView getGridView() {
		return mGridView;
	}
	
	@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			boolean ret = super.onOptionsItemSelected(item);
			updateUI();
			return ret;
		}
}
