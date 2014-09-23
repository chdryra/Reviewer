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

public abstract class FragmentReviewGridAddEditDoneDouble<TLeft extends GVData, TRight extends GVData>
		extends FragmentReviewGridDouble<TLeft, TRight> {

	public final static int DATA_ADD_LEFT = 10;
	public final static int DATA_EDIT_LEFT = 11;
	public final static int DATA_ADD_RIGHT = 20;
	public final static int DATA_EDIT_RIGHT = 21;

	protected abstract void addDataLeft(int resultCode, Intent data);
	protected abstract void editDataLeft(int resultCode, Intent data);
	protected abstract void addDataRight(int resultCode, Intent data);
	protected abstract void editDataRight(int resultCode, Intent data);

	@SuppressWarnings("unchecked")
	protected GVReviewDataList<TLeft> setAndInitDataLeft(GVType dataType) {
		// Not sure how to make sure this setup is type safe
		GVReviewDataList<TLeft> data = (GVReviewDataList<TLeft>) getController()
				.getData(dataType);
		setGridViewDataLeft(data);
		return data;
	}

	@SuppressWarnings("unchecked")
	protected GVReviewDataList<TRight> setAndInitDataRight(GVType dataType) {
		// Not sure how to make sure this setup is type safe
		GVReviewDataList<TRight> data = (GVReviewDataList<TRight>) getController()
				.getData(dataType);
		setGridViewDataRight(data);
		return data;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		switch (requestCode) {
		case DATA_ADD_LEFT:
			addDataLeft(resultCode, data);
			break;
		case DATA_EDIT_LEFT:
			editDataLeft(resultCode, data);
			break;
		case DATA_ADD_RIGHT:
			addDataRight(resultCode, data);
			break;
		case DATA_EDIT_RIGHT:
			editDataRight(resultCode, data);
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}

		updateUI();
	}
}
