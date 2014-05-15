package com.chdryra.android.reviewer;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class FragmentReviewChildren extends FragmentReviewGrid {
	private static final String DIALOG_CHILD_TAG = "ChildDialog";
	
	public final static int REVIEW_REQUEST = 60;
	public final static int REVIEW_EDIT = 61;
		
	private ControllerReviewNode mController;
	private ControllerReviewNodeCollection mCollectionController;

	private ArrayList<String> mReviewTitles = new ArrayList<String>();
	
	private boolean mTotalRatingIsAverage = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mController = Controller.unpack(getActivity().getIntent().getExtras());		
		mCollectionController = mController.getCollectionController();
		for(String id : mCollectionController.getIDs())
			mReviewTitles.add(mCollectionController.getTitle(id));
		
		mTotalRatingIsAverage = mController.isReviewRatingAverage();
		setDeleteWhatTitle(getResources().getString(R.string.activity_title_children));
	}
	
	@Override
	protected void initUI() {
		initSubjectTextUI();
		initRatingBarUI();
		initAddReviewUI();
		initReviewCollectionGridViewUI();
	}
	
	@Override
	protected void updateUI() {
		updateRatingBarUI();
		updateReviewCollectionGridViewUI();
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
				String ratingType = mTotalRatingIsAverage? "average" : "user";
				Toast.makeText(getActivity(), "Rating is " + ratingType, Toast.LENGTH_SHORT).show();
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
	
	private void initReviewCollectionGridViewUI() {
		getGridView().setAdapter(new GridViewCellAdapter(getActivity(), 
				mCollectionController.getGridViewData(), 
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
	
	private void updateReviewCollectionGridViewUI() {
		((GridViewCellAdapter)getGridView().getAdapter()).notifyDataSetChanged();
	}
	
	private void showChildDialog() {
		showDialog(new DialogChildAddFragment(), REVIEW_REQUEST, DIALOG_CHILD_TAG, Controller.pack(mController));
	}

	private void showChildDialog(ControllerReviewNode childController) {
		showDialog(new DialogChildEditFragment(), REVIEW_EDIT, DIALOG_CHILD_TAG, Controller.pack(childController));
	}
	
	private void showDialog(SherlockDialogFragment dialog, int requestCode, String tag, Bundle args) {
		dialog.setTargetFragment(FragmentReviewChildren.this, requestCode);
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), tag);
	}
	
	private void setTotalRatingIsAverage(boolean isAverage) {
		mController.setReviewRatingAverage(isAverage);
		updateRatingBarUI();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REVIEW_EDIT:
			switch(ActivityResultCode.get(resultCode)) {
			case DELETE:
				ControllerReviewNode childController = Controller.unpack(data.getExtras());
				mController.removeChild(childController.getID());
				if(mCollectionController.size() == 0)
					setTotalRatingIsAverage(false);
			break;
			default:
				break;
			}
			
		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}

		updateUI();				
	}

	@Override
	protected void onDeleteSelected() {
		mCollectionController.removeAll();		
		setTotalRatingIsAverage(false);
	}

	@Override
	protected boolean hasDataToDelete() {
		return mCollectionController.size() > 0;
	}
}
