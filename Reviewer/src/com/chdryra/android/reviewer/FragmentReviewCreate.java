package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RatingBar;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.chdryra.android.myandroidwidgets.ClearableEditText;

public class FragmentReviewCreate  extends SherlockFragment {

	public final static int TAG_REQUEST = 10;
	public final static int TAG_EDIT = 11;
	public final static int REVIEW_EDIT = 20;
	
	private ControllerReviewNode mController;
	
	private ClearableEditText mSubjectEditText;
	private RatingBar mTotalRatingBar;
	private GridView mTagsGridView;

	int mMaxButtonWidth;
	int mMaxButtonHeight;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		mController = Controller.unpack(getActivity().getIntent().getExtras());
		if(mController == null)
			mController = Controller.addNewReviewInProgress();

		setHasOptionsMenu(true);		
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_review_create, container, false);			
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//***Get all view objects***//
		mSubjectEditText = (ClearableEditText)v.findViewById(R.id.review_subject_edit_text);
		mTotalRatingBar = (RatingBar)v.findViewById(R.id.total_rating_bar);
		mTagsGridView = (GridView)v.findViewById(R.id.tags_gridview);
		
		//***Get display metrics for tag display size***//
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getSherlockActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		mMaxButtonWidth = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels) / 2;				
		mMaxButtonHeight = mMaxButtonWidth / 2;

		initUI();
		updateUI();
		
		return v;
	}
	
	private void initUI() {
		initSubjectUI();
		initRatingBarUI();
		initTagsUI();
	}
	
	private void initSubjectUI() {
		mSubjectEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString().length() > 0)
					mController.setTitle(s.toString());
			}
		});
		
	}
	
	private void initRatingBarUI() {
		mTotalRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
					mController.setRating(rating);
			}
		});
	}

	private void initTagsUI(){
	};
	
	private void updateUI() {
		updateSubjectText();
		updateRatingBar();
		updateTags();
	}

	private void updateSubjectText() {
		mSubjectEditText.setText(mController.getTitle());
	}
	
	private void updateRatingBar() {
		mTotalRatingBar.setRating(mController.getRating());
	}
	
	private void updateTags() {
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_delete_done, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_done:
			Intent i = new Intent(getActivity(), ActivityReviewEdit.class);
			Controller.pack(mController, i);
			startActivity(i);
			return true;
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getSherlockActivity()) != null) {
				Intent j = NavUtils.getParentActivityIntent(getSherlockActivity());
				NavUtils.navigateUpTo(getActivity(), j);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
