/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogAddCancelDoneFragment;
import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

public abstract class DialogAddReviewDataFragment extends DialogAddCancelDoneFragment {
	public static final String QUICK_SET = "com.chdryra.android.reviewer.dialog_quick_mode";
	
	private ControllerReviewNode mController;
	private boolean mQuickSet = false;
	private GVReviewDataList<? extends GVData> mData;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mQuickSet = getArguments().getBoolean(QUICK_SET);
		mController = Administrator.get(getActivity()).unpack(getArguments());
		setAddOnDone(true);
	}
	
	protected void setQuickSet(boolean quickSet) {
		mQuickSet = quickSet;
	}
	
	protected boolean isQuickSet() {
		return mQuickSet;
	}
	
	protected ControllerReviewNode getController() {
		return mController;
	}
	
	protected GVReviewDataList<? extends GVData> setAndInitData(GVType dataType) {
		mData = mController.getData(dataType);
		return mData;
	}
	
	@Override
	protected void onDoneButtonClick() {
		if(isQuickSet())
			mController.setData(mData);
	}
	
	@Override
	protected abstract View createDialogUI();
}
