/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.view.WindowManager;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

public abstract class FragmentReviewGridAddEditDone<T extends GVData>  extends FragmentReviewGrid<T> {
	final static String DATA_ADD_TAG = "DataAddTag";
	final static String DATA_EDIT_TAG = "DataEditTag";
	
	final static int DATA_ADD = 10;
	final static int DATA_EDIT = 11;

	protected abstract void addData(int resultCode, Intent data);
	protected abstract void editData(int resultCode, Intent data);
	
	@SuppressWarnings("unchecked")
    GVReviewDataList<T> setAndInitData(GVType dataType) {
		//Not sure how to make sure this setup is type safe
		GVReviewDataList<T> data = (GVReviewDataList<T>) getController().getData(dataType);
		setGridViewData(data);
		return data;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
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
