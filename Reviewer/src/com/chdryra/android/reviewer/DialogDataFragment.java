package com.chdryra.android.reviewer;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogDataFragment extends DialogBasicFragment {

	private ControllerReviewNode mController;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mController = Controller.getControllerFor(getArguments().getString(FragmentReviewOptions.REVIEW_ID));
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_data, null);
		
		LinearLayout dataLinearLayout = (LinearLayout)v.findViewById(R.id.data_linear_layout);
		boolean dark = true;
		LinkedHashMap<String, String> factsMap = mController.getFacts();
		for(Entry<String, String> entry: factsMap.entrySet()) {
			View datumRow = getSherlockActivity().getLayoutInflater().inflate(R.layout.datum_linear_row, null);
			TextView label = (TextView)datumRow.findViewById(R.id.datum_label_text_view);
			TextView value = (TextView)datumRow.findViewById(R.id.datum_value_text_view);
			value.setGravity(Gravity.RIGHT);
			
			label.setText(entry.getKey() + ": ");
			value.setText(entry.getValue());
		
			datumRow.setBackgroundResource(dark == true? android.R.drawable.divider_horizontal_bright: android.R.drawable.divider_horizontal_dark);
			dataLinearLayout.addView(datumRow);
			
			dark = !dark;
		}
		
		return buildDialog(v);
	}
	
	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.data_activity_title);
	}
	
	@Override
	protected void deleteData() {
		mController.deleteFacts();
	}
}
