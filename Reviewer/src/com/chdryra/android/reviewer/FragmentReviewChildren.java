package com.chdryra.android.reviewer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class FragmentReviewChildren extends FragmentReviewGrid {
	private final static String DIALOG_CHILD_TAG = "ChildDialog";

	public final static int REVIEW_REQUEST = 60;
	public final static int REVIEW_EDIT = 61;
		
	private ControllerReviewNode mController;
	private ControllerReviewNodeCollection mReviewsController;

	private ArrayList<String> mReviewTitles = new ArrayList<String>();
	
	private boolean mTotalRatingIsAverage = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mController = Controller.unpack(getActivity().getIntent().getExtras());		
		mReviewsController = mController.getCollectionController();
		for(String id : mReviewsController.getIDs())
			mReviewTitles.add(mReviewsController.getTitle(id));
		
		mTotalRatingIsAverage = mController.isReviewRatingAverage();
	}
	
	@Override
	protected void initUI() {
		initSubjectTextUI();
		initRatingBarUI();
		initAddReviewUI();
		initReviewsGridViewUI();
	}
	
	@Override
	protected void updateUI() {
		updateRatingBarUI();
	}

	private void initSubjectTextUI() {
		getSubjectView().setText(mController.getTitle());
	}
	
	private void initRatingBarUI() {
		getTotalRatingBar().setIsIndicator(true);
		setTotalRatingIsAverage(mController.isReviewRatingAverage());
		getTotalRatingBar().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTotalRatingIsAverage = !mTotalRatingIsAverage;
				setTotalRatingIsAverage(mTotalRatingIsAverage);
			}
		});
		
	}
	
	private void initAddReviewUI() {
		getAddDataButton().setText(getResources().getString(R.string.button_add_criteria));
		getAddDataButton().setTextColor(getSubjectView().getTextColors().getDefaultColor());
		getAddDataButton().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showChildDialog();
			}
		});		
	}
	
	private void initReviewsGridViewUI() {
		getGridView().setAdapter(new GridViewCellAdapter(getActivity(), 
				mReviewsController.getGridViewData(), 
				R.layout.grid_view_cell_review, 
				getGridCellWidth(), getGridCellHeight(), 
				getSubjectView().getTextColors().getDefaultColor()));
		
		getGridView().setColumnWidth(getGridCellWidth());
		getGridView().setNumColumns(getNumberColumns());
		
		getGridView().setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            showChildDialog((ControllerReviewNode)parent.getItemAtPosition(position));
	        }
	    });	
	}
		
	private void updateRatingBarUI() {	
		getTotalRatingBar().setRating(mController.getRating());
	}
	
	private void showChildDialog() {
		showDialog(new DialogChildAddFragment(), REVIEW_REQUEST, DIALOG_CHILD_TAG, Controller.pack(mController));
	}

	private void showChildDialog(ControllerReviewNode childController) {
		showDialog(new DialogChildAddFragment(), REVIEW_REQUEST, DIALOG_CHILD_TAG, Controller.pack(mController));
	}
	
	private void showDialog(SherlockDialogFragment dialog, int requestCode, String tag, Bundle args) {
		dialog.setTargetFragment(FragmentReviewChildren.this, requestCode);
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), tag);
	}
	
	private void setTotalRatingIsAverage(boolean isAverage) {
		mController.setReviewRatingAverage(isAverage);
		String ratingType = isAverage? "average" : "user";
		Toast.makeText(getActivity(), "Rating is " + ratingType, Toast.LENGTH_SHORT).show();
		updateRatingBarUI();
	}
		
	private void deleteChild(String childID) {
		mReviewTitles.remove(mReviewsController.getTitle(childID));
		mReviewsController.remove(childID);
		if(mReviewsController.size() == 0)
			setTotalRatingIsAverage(false);
		
		updateUI();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		String childID = data.getStringExtra(DialogChildTitleEditFragment.REVIEW_ID);
		switch (requestCode) {
		case REVIEW_EDIT:
			switch (resultCode) {
			case Activity.RESULT_OK:
				String childName = mReviewsController.getTitle(childID);
				String oldName = data.getStringExtra(DialogChildTitleEditFragment.OLD_NAME);
				
				if(childName.equals(oldName))
					break;
				
				if(mReviewTitles.contains(childName)) {
					String newName = childName;
					int i = 1;
					while(mReviewTitles.contains(newName))
						newName = childName + "_" + String.valueOf(i++);
					Toast.makeText(getSherlockActivity(), "Criterion: " + childName + " already exists, changing name to " + newName, Toast.LENGTH_SHORT).show();
					mReviewsController.setTitle(childID, newName);
				}
				
				mReviewTitles.remove(oldName);
				mReviewTitles.add(mReviewsController.getTitle(childID));
				break;
			
			case DialogChildTitleEditFragment.RESULT_DELETE:
				deleteChild(childID);
				break;
			
			case Activity.RESULT_CANCELED:
				return;
				
			default:
				break;
			}
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}

		updateUI();				
	}

	@Override
	protected void deleteData() {
		mReviewsController.removeAll();		
	}

	@Override
	protected boolean hasData() {
		return mReviewsController.size() > 0;
	}
	
	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.activity_title_children);
	}
}
