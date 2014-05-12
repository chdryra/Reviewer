package com.chdryra.android.reviewer;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogFactsFragment extends DialogEditFragment {

	public static final int RESULT_ADD = 4;
	
	private ControllerReviewNode mController;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mController = Controller.unpack(getArguments());
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_facts, null);
		
		LinearLayout dataLinearLayout = (LinearLayout)v.findViewById(R.id.data_linear_layout);
		boolean dark = true;
		LinkedHashMap<String, String> factsMap = mController.getFacts();
		for(Entry<String, String> entry: factsMap.entrySet()) {
			View datumRow = getSherlockActivity().getLayoutInflater().inflate(R.layout.fact_linear_row, null);
			TextView label = (TextView)datumRow.findViewById(R.id.datum_label_text_view);
			TextView value = (TextView)datumRow.findViewById(R.id.datum_value_text_view);
			value.setGravity(Gravity.RIGHT);
			
			label.setText(entry.getKey() + ": ");
			value.setText(entry.getValue());
		
			datumRow.setBackgroundResource(dark == true? android.R.drawable.divider_horizontal_bright: android.R.drawable.divider_horizontal_dark);
			dataLinearLayout.addView(datumRow);
			
			dark = !dark;
		}
		
		return buildDialog(v, getResources().getString(R.string.activity_title_facts));
	}
	
	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.activity_title_facts);
	}
	
	@Override
	protected void deleteData() {
		mController.deleteFacts();
	}

	@Override
	protected boolean hasData() {
		return mController.hasFacts();
	}

	@Override
	protected AlertDialog buildDialog(View v, String title) {
		AlertDialog dialog = new AlertDialog.Builder(getActivity()).
				setView(v).
				setPositiveButton(R.string.dialog_button_edit_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_OK);
					}
				}).
				setNeutralButton(R.string.dialog_button_add_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(RESULT_ADD);
					}
				}).
				setNegativeButton(R.string.dialog_button_delete_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(RESULT_DELETE);
					}
				}).
				create(); 
		if(title != null)
			dialog.setTitle(title);
		
		return dialog;
	}
}
