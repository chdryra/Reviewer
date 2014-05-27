package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;

import android.content.Intent;
import android.view.WindowManager;

public abstract class FragmentReviewGridAddEditDone<T extends GVData>  extends FragmentReviewGrid<T> {
	protected final static String DATA_ADD_TAG = "DataAddTag";
	protected final static String DATA_EDIT_TAG = "DataEditTag";
	
	public final static int DATA_ADD = 10;
	public final static int DATA_EDIT = 11;

	protected abstract void addData(int resultCode, Intent data);
	protected abstract void editData(int resultCode, Intent data);
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
		switch(requestCode) {
		case DATA_ADD:
			addData(resultCode, data);
			break;
		case DATA_EDIT:
			editData(resultCode, data);
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}
		
		updateUI();				
	}
}
