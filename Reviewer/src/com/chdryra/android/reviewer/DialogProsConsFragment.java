package com.chdryra.android.reviewer;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogProsConsFragment extends DialogBasicFragment {

	public static final int RESULT_ADD = 5;
	
	private ControllerReviewNode mController;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mController = Controller.unpack(getArguments());
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_proscons, null);
		
		LinearLayout prosLinearLayout = (LinearLayout)v.findViewById(R.id.pros_linear_layout);
		LinearLayout consLinearLayout = (LinearLayout)v.findViewById(R.id.cons_linear_layout);
		
		boolean dark = true;
		ArrayList<String> pros = mController.getPros();
		ArrayList<String> cons = mController.getCons();
		int max_size = Math.max(pros.size(), cons.size());
		for(int i = 0; i < max_size; ++i) {
			String pro = i < pros.size()? pros.get(i) : null;
			String con = i < cons.size()? cons.get(i) : null;
			
			View proRow = getSherlockActivity().getLayoutInflater().inflate(R.layout.procon_row, null);
			View conRow = getSherlockActivity().getLayoutInflater().inflate(R.layout.procon_row, null);
			
			TextView proTextView = (TextView)proRow.findViewById(R.id.procon_text_view);
			TextView conTextView = (TextView)conRow.findViewById(R.id.procon_text_view);
			
			proTextView.setText(pro);
			conTextView.setText(con);
			proTextView.setTextColor(getResources().getColor(R.color.Green));
			conTextView.setTextColor(getResources().getColor(R.color.Crimson));
			proTextView.setSelected(true);
			conTextView.setSelected(true);
			
			proTextView.setBackgroundResource(dark == true? android.R.drawable.divider_horizontal_bright: android.R.drawable.divider_horizontal_dark);
			dark = !dark;
			conTextView.setBackgroundResource(dark == true? android.R.drawable.divider_horizontal_bright: android.R.drawable.divider_horizontal_dark);
			
			prosLinearLayout.addView(proTextView);
			consLinearLayout.addView(conTextView);
		}
		
		return buildDialog(v, getResources().getString(R.string.procon_activity_title));
	}
	
	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.procon_activity_title);
	}
	
	@Override
	protected void deleteData() {
		mController.deleteProsCons();
	}

	@Override
	protected boolean hasData() {
		return mController.hasProsCons();
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
