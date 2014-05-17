package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.GVStrings;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;

public class FragmentReviewProsCons extends FragmentReviewGridDouble {
	public static final String PRO = "com.chdryra.android.reviewer.pro";
	public static final String CON = "com.chdryra.android.reviewer.con";
	public static final String PROCON_HINT = "com.chdryra.android.reviewer.pro_con_hint";
	public static final String DIALOG_PROCON_ADD_TAG = "ProConAddDialog";
	public static final String DIALOG_PROCON_EDIT_TAG = "ProConEditDialog";

	public static final int PROS_ADD = 10;
	public static final int PRO_EDIT = 11;
	public static final int CONS_ADD = 20;
	public static final int CON_EDIT = 21;
	
	private GVStrings mPros; 
	private GVStrings mCons;
	
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
		DialogShower.show(new DialogProConAddFragment(), FragmentReviewProsCons.this, PROS_ADD, DIALOG_PROCON_ADD_TAG, Controller.pack(getController()));
	}
	
	@Override
	protected void onBannerButtonClickRight() {
		DialogShower.show(new DialogProConAddFragment(), FragmentReviewProsCons.this, CONS_ADD, DIALOG_PROCON_ADD_TAG, Controller.pack(getController()));
	}
	
	@Override
	protected void onGridItemClickLeft(AdapterView<?> parent, View v, int position, long id) {
		Bundle args = Controller.pack(getController());
		String pro = (String)parent.getItemAtPosition(position);
		args.putString(PRO, pro);
		DialogShower.show(new DialogProConEditFragment(), FragmentReviewProsCons.this, PRO_EDIT, DIALOG_PROCON_EDIT_TAG, args);
	}

	@Override
	protected void onGridItemClickRight(AdapterView<?> parent, View v, int position, long id) {
		Bundle args = Controller.pack(getController());
		String con = (String)parent.getItemAtPosition(position);
		args.putString(CON, con);
		DialogShower.show(new DialogProConEditFragment(), FragmentReviewProsCons.this, CON_EDIT, DIALOG_PROCON_EDIT_TAG, args);
	}
	
	@Override
	protected void onDoneSelected() {
		getController().setProsCons(mPros, mCons);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
		switch(requestCode) {
			case PRO_EDIT:
				updateProCon(resultCode, data, mPros);
				break;
			case CON_EDIT:
				updateProCon(resultCode, data, mCons);
				break;
			default:
				super.onActivityResult(requestCode, resultCode, data);
				break;
		}
		
		updateUI();				
	}

	private void updateProCon(int resultCode, Intent data, GVStrings proCons) {
		switch(ActivityResultCode.get(resultCode)) {
			case DONE:
				String oldPro = (String)data.getSerializableExtra(DialogProConEditFragment.PROCON_OLD);
				String newPro = (String)data.getSerializableExtra(DialogProConEditFragment.PROCON);
				proCons.remove(oldPro);
				proCons.add(newPro);
				break;
			case DELETE:
				String toDelete = (String)data.getSerializableExtra(DialogProConEditFragment.PROCON_OLD);
				proCons.remove(toDelete);
				break;
			default:
				return;
		}
	}
	
	@Override
	protected void onDeleteSelected() {
		getController().deleteProsCons();
		mPros.removeAll();
		mCons.removeAll();
	}

	@Override
	protected boolean hasDataToDelete() {
		return getController().hasProsCons();
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
}
