package com.chdryra.android.reviewer;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.IntentObjectHolder;

public class DialogDataFragment extends DialogBasicFragment {

	private UserReview mUserReview;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_data, null);
		LinearLayout dataLinearLayout = (LinearLayout)v.findViewById(R.id.data_linear_layout);
		
		mUserReview = (UserReview)IntentObjectHolder.getObject(FragmentReviewOptions.REVIEW_OBJECT);
		boolean dark = true;
		for(Datum datum: mUserReview.getFacts()) {
			View datumRow = getSherlockActivity().getLayoutInflater().inflate(R.layout.datum_linear_row, null);
			TextView label = (TextView)datumRow.findViewById(R.id.datum_label_text_view);
			TextView value = (TextView)datumRow.findViewById(R.id.datum_value_text_view);
			value.setGravity(Gravity.RIGHT);
			
			label.setText(datum.getLabel() + ": ");
			value.setText(datum.getValue());
		
			datumRow.setBackgroundResource(dark == true? android.R.drawable.divider_horizontal_bright: android.R.drawable.divider_horizontal_dark);
			dataLinearLayout.addView(datumRow);
			
			dark = !dark;
		}
		
		setDeleteConfirmation(getResources().getString(R.string.data_activity_title));
	
		return buildDialog(v);
	}
}
