package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogDeleteCancelDoneFragment;

public class DialogProConEditFragment extends DialogDeleteCancelDoneFragment {
	public static final String PROCON = "com.chdryra.android.reviewer.pro_con";
	public static final String PRO_MODE = "com.chdryra.android.reviewer.pro_mode";
	public static final String OLD = "com.chdryra.android.reviewer.procon_old";
	
	private ClearableEditText mProCon;
	private String mOldProCon;
	private boolean mProMode = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setDeleteConfirmation(false);
		mOldProCon = getArguments().getString(FragmentReviewProsCons.PROCON);
		mProMode = getArguments().getBoolean(FragmentReviewProsCons.PRO_MODE);
		String title = mProMode? getResources().getString(R.string.dialog_edit_pro_title) : getResources().getString(R.string.dialog_edit_con_title); 
		setDialogTitle(title);
	}
	
	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_procon, null);
		String proConHint = mProMode? getResources().getString(R.string.edit_text_pro_hint) : getResources().getString(R.string.edit_text_con_hint);
		mProCon = (ClearableEditText)v.findViewById(R.id.procon_edit_text);
		mProCon.setText(mOldProCon);		
		mProCon.setHint(proConHint);
		setKeyboardIMEDoDone(mProCon);
		return v;
	}

	@Override
	protected void onDoneButtonClick() {
		Intent i = getNewReturnData();
		i.putExtra(OLD, mOldProCon);
		i.putExtra(PROCON, mProCon.getText().toString());
		i.putExtra(PRO_MODE, mProMode);
	}
	
	@Override
	protected void onDeleteButtonClick() {
		Intent i = getNewReturnData();
		i.putExtra(OLD, mOldProCon);
		i.putExtra(PRO_MODE, mProMode);
	}

	@Override
	protected boolean hasDataToDelete() {
		return true;
	}	
}
