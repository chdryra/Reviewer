package com.chdryra.android.reviewer;

import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolderBasic;

public class VHDualStringView extends ViewHolderBasic {
	private static final int LAYOUT = R.layout.grid_cell_dual_string;
	private static final int UPPER = R.id.upper_text_view;
	private static final int LOWER = R.id.lower_text_view;

	private int mUpperID = UPPER;
	private int mLowerID = LOWER;
	protected TextView mUpper;
	protected TextView mLower;
	
	public VHDualStringView() {
		super(LAYOUT);
	}
	
	public VHDualStringView(int layoutID, int upperTextViewID, int lowerTextViewID) {
		super(layoutID);
		mUpperID = upperTextViewID;
		mLowerID = lowerTextViewID;
	}
	
	@Override
	protected void initViewsToUpdate() {
		mUpper = (TextView)getView(mUpperID);
		mLower = (TextView)getView(mLowerID);
	}
	
	@Override
	public void updateView(GVData data) {
		GVDualString dual = (GVDualString)data;
		if(dual != null) {
			mUpper.setText(dual.getUpper());
			mLower.setText(dual.getLower());
		}
	}
}
