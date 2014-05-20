package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;
import com.chdryra.android.reviewer.GVReviewSubjectRatings.GVReviewSubjectRating;

public class FragmentReviewChildren extends FragmentReviewGrid {
	private static final String DIALOG_CHILD_ADD_TAG = "ChildAddDialog";
	private static final String DIALOG_CHILD_EDIT_TAG = "ChildEditDialog";
	public static final String CHILD_SUBJECT = "com.chdryra.android.reviewer.child_subject";
	public static final String CHILD_RATING = "com.chdryra.android.reviewer.child_rating";
	
	public final static int CHILD_ADD = 60;
	public final static int CHILD_EDIT = 61;
		
	private GVReviewSubjectRatings mReviewData;
	private boolean mTotalRatingIsAverage = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mReviewData = getController().getCollectionController().getGridViewiableData();
		mTotalRatingIsAverage = getController().isReviewRatingAverage();

		setDeleteWhatTitle(getResources().getString(R.string.activity_title_children));
		setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
		setBannerButtonText(getResources().getString(R.string.button_add_criteria));
		setIsEditable(true);
	}
	
	@Override
	protected void onBannerButtonClick() {
		DialogShower.show(new DialogChildAddFragment(), FragmentReviewChildren.this, CHILD_ADD, DIALOG_CHILD_ADD_TAG, Controller.pack(getController()));
	}
	
	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position,
			long id) {
		GVReviewSubjectRating reviewData = (GVReviewSubjectRating)parent.getItemAtPosition(position);
		Bundle args = new Bundle();
		args.putString(CHILD_SUBJECT, reviewData.getSubject());
		args.putFloat(CHILD_RATING, reviewData.getRating());
		DialogShower.show(new DialogChildEditFragment(), FragmentReviewChildren.this, CHILD_EDIT, DIALOG_CHILD_EDIT_TAG, args);
	}
	
	@Override
	protected GridViewCellAdapter getGridViewCellAdapter() {
		return new GridViewCellAdapter(getActivity(), 
				mReviewData, 
				R.layout.grid_cell_review, 
				getGridCellWidth(), getGridCellHeight());
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
		switch(requestCode) {
		case CHILD_ADD:
			addChild(resultCode, data);
			break;
		case CHILD_EDIT:
			editChild(resultCode, data);
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}
		
		updateUI();				
	}

	private void addChild(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case ADD:
			String subject = (String)data.getSerializableExtra(DialogChildAddFragment.SUBJECT);
			float rating = (float)data.getFloatExtra(DialogChildAddFragment.RATING, 0);
			if(subject != null && subject.length() > 0 && !mReviewData.hasSubject(subject))
				mReviewData.add(subject, rating);
			break;
		default:
			return;
		}
	}
	
	private void editChild(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case DONE:
			String oldSubject = (String)data.getSerializableExtra(DialogChildEditFragment.SUBJECT_OLD);
			String newSubject = (String)data.getSerializableExtra(DialogChildEditFragment.SUBJECT);
			float newRating = data.getFloatExtra(DialogChildEditFragment.RATING, 0);
			if(!mReviewData.hasSubject(newSubject)) {
				mReviewData.remove(oldSubject);
				mReviewData.add(newSubject, newRating);
			}
			break;
		case DELETE:
			String toDelete = (String)data.getSerializableExtra(DialogChildEditFragment.SUBJECT_OLD);
			mReviewData.remove(toDelete);
			if(mReviewData.size() == 0)
				setTotalRatingIsAverage(false);
			break;
		default:
			return;
		}
	}

	@Override
	protected void onDoneSelected() {
		for(GVReviewSubjectRating reviewData : mReviewData)
			getController().getChildrenController().addChild(reviewData.getSubject(), reviewData.getRating());
	}
	
	@Override
	protected void onDeleteSelected() {
		mReviewData.removeAll();		
		setTotalRatingIsAverage(false);
	}

	@Override
	protected boolean hasDataToDelete() {
		return mReviewData.size() > 0;
	}

	private void setTotalRatingIsAverage(boolean isAverage) {
		getController().setReviewRatingAverage(isAverage);
		updateRatingBarUI();
	}
}
