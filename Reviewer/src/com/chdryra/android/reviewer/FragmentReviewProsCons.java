package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.GVStringList;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;

public class FragmentReviewProsCons extends FragmentReviewGridDouble {
	public static final String PROCON = "com.chdryra.android.reviewer.pro_con";
	public static final String PRO_MODE = "com.chdryra.android.reviewer.pro_mode";
	public static final String PROCON_HINT = "com.chdryra.android.reviewer.pro_con_hint";
	
	public static final String DATA_ADD_TAG = "ProConAddDialog";
	public static final String DATA_EDIT_TAG = "ProConEditDialog";

	public static final int DATA_ADD = 10;
	public static final int DATA_EDIT = 20;
	
	private GVStringList mPros; 
	private GVStringList mCons;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mPros = getController().getPros();
		mCons = getController().getCons();
		
		setDeleteWhatTitle(getResources().getString(R.string.activity_title_procon));
		setBannerButtonTextLeft(getResources().getString(R.string.button_add_pros));
		setBannerButtonTextRight(getResources().getString(R.string.button_add_cons));
		setIsEditable(true);
	}
		
	@Override
	protected void onBannerButtonClickLeft() {
		Bundle args = Controller.pack(getController());
		args.putBoolean(PRO_MODE, true);
		DialogShower.show(new DialogProConAddFragment(), FragmentReviewProsCons.this, DATA_ADD, DATA_ADD_TAG, args);
	}
	
	@Override
	protected void onBannerButtonClickRight() {
		Bundle args = Controller.pack(getController());
		args.putBoolean(PRO_MODE, false);
		DialogShower.show(new DialogProConAddFragment(), FragmentReviewProsCons.this, DATA_ADD, DATA_ADD_TAG, args);
	}
	
	@Override
	protected void onGridItemClickLeft(AdapterView<?> parent, View v, int position, long id) {
		Bundle args = Controller.pack(getController());
		String pro = (String)parent.getItemAtPosition(position);
		args.putBoolean(PRO_MODE, true);
		args.putString(PROCON, pro);
		DialogShower.show(new DialogProConEditFragment(), FragmentReviewProsCons.this, DATA_EDIT, DATA_EDIT_TAG, args);
	}

	@Override
	protected void onGridItemClickRight(AdapterView<?> parent, View v, int position, long id) {
		Bundle args = Controller.pack(getController());
		String con = (String)parent.getItemAtPosition(position);
		args.putBoolean(PRO_MODE, false);
		args.putString(PROCON, con);
		DialogShower.show(new DialogProConEditFragment(), FragmentReviewProsCons.this, DATA_EDIT, DATA_EDIT_TAG, args);
	}
	
	@Override
	protected GridViewCellAdapter getGridViewCellAdapterLeft() {
		return new GridViewCellAdapter(getActivity(), 
				mPros, 
				R.layout.grid_cell_pro, 
				getGridCellWidth(), getGridCellHeight());
	}

	@Override
	protected GridViewCellAdapter getGridViewCellAdapterRight() {
		return new GridViewCellAdapter(getActivity(), 
				mCons, 
				R.layout.grid_cell_con, 
				getGridCellWidth(), getGridCellHeight());
	}
	
	@Override
	protected void onDoneSelected() {
		getController().setProsCons(mPros, mCons);
	}
		
	@Override
	protected void onDeleteSelected() {
		mPros.removeAll();
		mCons.removeAll();
	}

	@Override
	protected boolean hasDataToDelete() {
		return mPros.size() > 0 || mCons.size() > 0;
	}

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

	private void addData(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case ADD:
			if(data.getBooleanExtra(PRO_MODE, true))
				mPros.add((String)data.getSerializableExtra(PROCON));
			else
				mCons.add((String)data.getSerializableExtra(PROCON));
			break;
		default:
			return;
		}
	}
	
	private void editData(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case DONE:
			GVStringList procons = data.getBooleanExtra(PRO_MODE, true)? mPros : mCons;
			procons.remove((String)data.getSerializableExtra(DialogProConEditFragment.OLD));
			procons.add((String)data.getSerializableExtra(PROCON));
			break;
		case DELETE:
			if(data.getBooleanExtra(PRO_MODE, true))
				mPros.remove((String)data.getSerializableExtra(DialogProConEditFragment.OLD));
			else
				mCons.remove((String)data.getSerializableExtra(DialogProConEditFragment.OLD));
			break;
		default:
			return;
		}
	}
}
