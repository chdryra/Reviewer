package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

public class DialogProConAddFragment extends DialogAddReviewDataFragment{
	private GVProConList mProCons = new GVProConList();
	private ClearableEditText mProConEditText;
	private boolean mProMode = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mProMode = getArguments().getBoolean(FragmentReviewProsCons.PRO_MODE);
		mProCons = (GVProConList) (mProMode? getController().getData(GVType.PROS) : getController().getData(GVType.CONS));
		String title = mProMode? getResources().getString(R.string.dialog_add_pros_title) : getResources().getString(R.string.dialog_add_cons_title); 
		setDialogTitle(title);
		setQuickSet(false);
	}

	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_procon, null);
		mProConEditText = (ClearableEditText)v.findViewById(R.id.procon_edit_text);
		setKeyboardIMEDoAction(mProConEditText);
		return v;
	}

	@Override
	protected void OnAddButtonClick() {
		String procon = mProConEditText.getText().toString();
		if(procon == null || procon.length() == 0)
			return;
		
		if(mProCons.contains(procon, mProMode))
			Toast.makeText(getActivity(), mProMode? R.string.toast_has_pro : R.string.toast_has_con, Toast.LENGTH_SHORT).show();
		else {
			mProCons.add(procon, mProMode);
			Intent i = getNewReturnData();
			i.putExtra(FragmentReviewProsCons.PROCON, procon);
			i.putExtra(FragmentReviewProsCons.PRO_MODE, mProMode);
			mProConEditText.setText(null);
			String label = mProMode? "Pro" : "Con";
			getDialog().setTitle("Added " + label + ": " + procon);
		}
	}
}