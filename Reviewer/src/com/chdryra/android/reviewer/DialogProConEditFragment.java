package com.chdryra.android.reviewer;

import android.content.Intent;
import android.view.View;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogDeleteCancelDoneFragment;

public class DialogProConEditFragment extends DialogDeleteCancelDoneFragment {
	public static final String PROCON = "com.chdryra.android.reviewer.procon";
	public static final String PROCON_OLD = "com.chdryra.android.reviewer.procon_old";
	
	private ClearableEditText mProCon;
	private String mOldProCon;
	private boolean mProEdit = true;
	
	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_procon, null);
		mProCon = (ClearableEditText)v.findViewById(R.id.procon_edit_text);
		
		mProEdit = getTargetRequestCode() == FragmentReviewProsCons.PRO_EDIT;
		mOldProCon = mProEdit? getArguments().getString(FragmentReviewProsCons.PRO) : getArguments().getString(FragmentReviewProsCons.CON);
		String proConHint = mProEdit? getResources().getString(R.string.edit_text_pro_hint) : getResources().getString(R.string.edit_text_con_hint);
		mProCon.setText(mOldProCon);		
		mProCon.setHint(proConHint);

		setKeyboardIMEDoDone(mProCon);
		
		return v;
	}

	@Override
	protected void onDoneButtonClick() {
		Intent i = getReturnData();
		i.putExtra(PROCON_OLD, mOldProCon);
		i.putExtra(PROCON, mProCon.getText().toString());
		super.onDoneButtonClick();
	}
	
	@Override
	protected void onDeleteButtonClick() {
		getReturnData().putExtra(PROCON_OLD, mOldProCon);
		super.onDeleteButtonClick();
	}

	@Override
	protected boolean hasDataToDelete() {
		return false;
	}	
}
