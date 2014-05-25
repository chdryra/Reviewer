package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogAddCancelDoneFragment;
import com.chdryra.android.mygenerallibrary.GVStrings;

public class DialogProConAddFragment extends DialogAddCancelDoneFragment{
	private GVStrings mProCons;
	private ClearableEditText mProConEditText;
	private boolean mProMode = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ControllerReviewNode controller = Controller.unpack(getArguments());
		mProMode = getArguments().getBoolean(FragmentReviewProsCons.PRO_MODE);
		mProCons = mProMode? controller.getPros() : controller.getCons();
		String title = mProMode? getResources().getString(R.string.dialog_add_pros_title) : getResources().getString(R.string.dialog_add_cons_title); 
		setDialogTitle(title);
		setAddOnDone(true);
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
		
		if(mProCons.contains(procon))
			Toast.makeText(getActivity(), mProMode? R.string.toast_has_pro : R.string.toast_has_con, Toast.LENGTH_SHORT).show();
		else {
			mProCons.add(procon);
			Intent i = getNewReturnData();
			i.putExtra(FragmentReviewProsCons.PROCON, procon);
			i.putExtra(FragmentReviewProsCons.PRO_MODE, mProMode);
			mProConEditText.setText(null);
			String label = mProMode? "Pro" : "Con";
			getDialog().setTitle("Added " + label + ": " + procon);
		}
	}
}