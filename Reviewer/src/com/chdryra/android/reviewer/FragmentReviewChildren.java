package com.chdryra.android.reviewer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;

public class FragmentReviewChildren extends FragmentReviewGrid {
	private static final String DIALOG_CHILD_ADD_TAG = "ChildAddDialog";
	private static final String DIALOG_CHILD_EDIT_TAG = "ChildEditDialog";
	
	public final static int CHILD_ADD = 60;
	public final static int CHILD_EDIT = 61;
		
	private ControllerReviewNodeCollection mCollectionController;

	private ArrayList<String> mReviewTitles = new ArrayList<String>();
	private LinkedHashMap<String, Float> mBackup = new LinkedHashMap<String, Float>();
	private boolean mTotalRatingIsAverage = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mCollectionController = getController().getCollectionController();
		for(String id : mCollectionController.getIDs()) {
			String title = mCollectionController.getTitle(id);
			mReviewTitles.add(title);
			mBackup.put(title, mCollectionController.getRating(id));
		}
		
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
		ControllerReviewNode childController = (ControllerReviewNode)parent.getItemAtPosition(position);
		DialogShower.show(new DialogChildEditFragment(), FragmentReviewChildren.this, CHILD_EDIT, DIALOG_CHILD_EDIT_TAG, Controller.pack(childController));
	}
	
	@Override
	protected GridViewCellAdapter getGridViewCellAdapter() {
		return new GridViewCellAdapter(getActivity(), 
				mCollectionController.getGridViewiableCollection(), 
				R.layout.grid_cell_review, 
				getGridCellWidth(), getGridCellHeight());
	}
		
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CHILD_EDIT:
			switch(ActivityResultCode.get(resultCode)) {
			case DELETE:
				ControllerReviewNode childController = Controller.unpack(data.getExtras());
				getController().removeChild(childController.getID());
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

	private void setTotalRatingIsAverage(boolean isAverage) {
		getController().setReviewRatingAverage(isAverage);
		updateRatingBarUI();
	}
	
	private void revertToBackup() {
		ControllerReviewNodeChildren controller = getController().getChildrenController();
		controller.removeAll();
		for (Map.Entry<String,Float> child : mBackup.entrySet())
			controller.addChild(child.getKey(), child.getValue());
	}
	
	@Override
	protected void onUpSelected() {
		revertToBackup();
		super.onUpSelected();
	}
}
