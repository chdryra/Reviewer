package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.GVCriterionList.GVCriterion;

public class FragmentReviewChildren extends FragmentReviewGridAddEditDone<GVCriterion> {
	public static final String CHILD_SUBJECT = "com.chdryra.android.reviewer.child_subject";
	public static final String CHILD_RATING = "com.chdryra.android.reviewer.child_rating";
	
	private GVCriterionList mReviewData;
	private boolean mTotalRatingIsAverage = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mReviewData = getController().getCollectionController().getGridViewiableData();
		mTotalRatingIsAverage = getController().isReviewRatingAverage();

		setDeleteWhatTitle(getResources().getString(R.string.activity_title_children));
		
		setGridViewData(mReviewData);
		setGridCellLayout(R.layout.grid_cell_review);
		setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
	
		setBannerButtonText(getResources().getString(R.string.button_add_criteria));
		
		setIsEditable(true);
	}
	
	@Override
	protected void onBannerButtonClick() {
		DialogShower.show(new DialogChildAddFragment(), FragmentReviewChildren.this, DATA_ADD, DATA_ADD_TAG, Controller.pack(getController()));
	}
	
	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position,
			long id) {
		GVCriterion reviewData = (GVCriterion)parent.getItemAtPosition(position);
		Bundle args = new Bundle();
		args.putString(CHILD_SUBJECT, reviewData.getSubject());
		args.putFloat(CHILD_RATING, reviewData.getRating());
		DialogShower.show(new DialogChildEditFragment(), FragmentReviewChildren.this, DATA_EDIT, DATA_EDIT_TAG, args);
	}
	
	@Override
	protected void addData(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case ADD:
			String subject = (String)data.getSerializableExtra(DialogChildAddFragment.SUBJECT);
			float rating = (float)data.getFloatExtra(DialogChildAddFragment.RATING, 0);
			if(subject != null && subject.length() > 0 && !mReviewData.contains(subject))
				mReviewData.add(subject, rating);
			break;
		default:
			return;
		}
	}
	
	@Override
	protected void editData(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case DONE:
			String oldSubject = (String)data.getSerializableExtra(DialogChildEditFragment.SUBJECT_OLD);
			String newSubject = (String)data.getSerializableExtra(DialogChildEditFragment.SUBJECT);
			float newRating = data.getFloatExtra(DialogChildEditFragment.RATING, 0);
			if(!mReviewData.contains(newSubject)) {
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
		getController().getChildrenController().removeAll();
		for(GVCriterion reviewData : mReviewData)
			getController().getChildrenController().addChild(reviewData.getSubject(), reviewData.getRating());
	}
	
	@Override
	protected void onDeleteSelected() {
		super.onDeleteSelected();		
		setTotalRatingIsAverage(false);
	}

	private void setTotalRatingIsAverage(boolean isAverage) {
		getController().setReviewRatingAverage(isAverage);
		updateRatingBarUI();
	}
}
