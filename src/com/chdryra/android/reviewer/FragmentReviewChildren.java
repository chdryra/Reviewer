package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.GVReviewSubjectRatingList.GVReviewSubjectRating;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

public class FragmentReviewChildren extends FragmentReviewGridAddEditDone<GVReviewSubjectRating> {
	public static final String CHILD_SUBJECT = "com.chdryra.android.reviewer.child_subject";
	public static final String CHILD_RATING = "com.chdryra.android.reviewer.child_rating";
	
	private GVReviewSubjectRatingList mReviewData;
	private boolean mTotalRatingIsAverage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mReviewData = (GVReviewSubjectRatingList) setAndInitData(GVType.CRITERIA);
		mTotalRatingIsAverage = getController().isReviewRatingAverage();
		setDeleteWhatTitle(getResources().getString(R.string.activity_title_children));		
		setBannerButtonText(getResources().getString(R.string.button_add_criteria));
		setIsEditable(true);
		setOnDoneActivity(ActivityReviewBuild.class);
	}
	
	protected void initRatingBarUI() {
		getTotalRatingBar().setIsIndicator(false);
		getTotalRatingBar().setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				getController().setRating(rating);
				if(fromUser)
					setTotalRatingIsAverage(false);
			}
		});
	}

	@Override
	protected void onBannerButtonClick() {
		DialogShower.show(new DialogChildAddFragment(), FragmentReviewChildren.this, DATA_ADD, DATA_ADD_TAG, Administrator.get(getActivity()).pack(getController()));
	}
	
	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		GVReviewSubjectRating reviewData = (GVReviewSubjectRating)parent.getItemAtPosition(position);
		Bundle args = new Bundle();
		args.putString(CHILD_SUBJECT, reviewData.getSubject());
		args.putFloat(CHILD_RATING, reviewData.getRating());
		DialogShower.show(new DialogChildEditFragment(), FragmentReviewChildren.this, DATA_EDIT, DATA_EDIT_TAG, args);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(mTotalRatingIsAverage)
			setAverageRating();
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
			if(oldSubject.equals(newSubject))
				mReviewData.set(oldSubject, newRating);
			else {
				if(!mReviewData.contains(newSubject)) {
					mReviewData.remove(oldSubject);
					mReviewData.add(newSubject, newRating);
				} else
					Toast.makeText(getActivity(), R.string.toast_exists_criterion, Toast.LENGTH_SHORT).show();
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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_review_children, menu);
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_item_average_rating) {
            setTotalRatingIsAverage(true);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
	}
		
	@Override
	protected void onDeleteSelected() {
		super.onDeleteSelected();		
		setTotalRatingIsAverage(false);
	}

	private void setTotalRatingIsAverage(boolean isAverage) {
		mTotalRatingIsAverage = isAverage;
		getController().setReviewRatingAverage(mTotalRatingIsAverage);
		if(mTotalRatingIsAverage)
			setAverageRating();
	}
	
	private void setAverageRating() {
		float rating = 0;
		for(GVReviewSubjectRating child : mReviewData)
			rating += child.getRating() / mReviewData.size();
		getTotalRatingBar().setRating(rating);
	}
}
