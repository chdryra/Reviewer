package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;
import com.chdryra.android.reviewer.GVProConList.GVProCon;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

public class FragmentReviewProsCons extends FragmentReviewGridAddEditDoneDouble<GVProCon, GVProCon> {
	public static final String PROCON = "com.chdryra.android.reviewer.pro_con";
	public static final String PRO_MODE = "com.chdryra.android.reviewer.pro_mode";
	public static final String PROCON_HINT = "com.chdryra.android.reviewer.pro_con_hint";
	
	public static final String DATA_ADD_TAG = "ProConAddDialog";
	public static final String DATA_EDIT_TAG = "ProConEditDialog";

	private GVProConList mPros; 
	private GVProConList mCons;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPros = (GVProConList) setAndInitDataLeft(GVType.PROS);
		mCons = (GVProConList) setAndInitDataRight(GVType.CONS);
		setDeleteWhatTitle(getResources().getString(R.string.activity_title_procon));
		setBannerButtonTextLeft(getResources().getString(R.string.button_add_pros));
		setBannerButtonTextRight(getResources().getString(R.string.button_add_cons));
	}
		
	@Override
	protected void onBannerButtonClickLeft() {
		onBannerButtonClick(true);
	}
	
	@Override
	protected void onBannerButtonClickRight() {
		onBannerButtonClick(false);
	}
	
	private void onBannerButtonClick(boolean isPro) {
	}
	
	@Override
	protected void onGridItemClickLeft(AdapterView<?> parent, View v, int position, long id) {
		onGridItemClick(true, parent, v, position, id);
	}

	@Override
	protected void onGridItemClickRight(AdapterView<?> parent, View v, int position, long id) {
		onGridItemClick(false, parent, v, position, id);
	}
	
	private void onGridItemClick(boolean isPro, AdapterView<?> parent, View v, int position, long id) {
		
	}
	
	@Override
	protected GridViewCellAdapter getGridViewCellAdapterLeft() {
		return new GridViewCellAdapter(getActivity(), mPros, getGridCellWidth(), getGridCellHeight());
	}

	@Override
	protected GridViewCellAdapter getGridViewCellAdapterRight() {
		return new GridViewCellAdapter(getActivity(), mCons, getGridCellWidth(), getGridCellHeight());
	}
	
	@Override
	protected void onDoneSelected() {
		GVProConList proCons = new GVProConList();
		proCons.add(mPros);
		proCons.add(mCons);
		getController().setData(proCons);
	}

	@Override
	protected void addDataLeft(int resultCode, Intent data) {
		if(ActivityResultCode.get(resultCode).equals(ActivityResultCode.ADD))
			mPros.add((String)data.getSerializableExtra(PROCON), true);
	}
	
	@Override
	protected void addDataRight(int resultCode, Intent data) {
		if(ActivityResultCode.get(resultCode).equals(ActivityResultCode.ADD))
			mCons.add((String)data.getSerializableExtra(PROCON), false);
	}
	
	@Override
	protected void editDataLeft(int resultCode, Intent data) {
		editData(true, resultCode, data);
	}
	
	@Override
	protected void editDataRight(int resultCode, Intent data) {
		editData(false, resultCode, data);
	}
	
	private void editData(boolean isPro, int resultCode, Intent data) {
	}
}
