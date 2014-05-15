package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogDeleteCancelDoneFragment;

public class DialogURLFragment extends DialogDeleteCancelDoneFragment {
	public static final ActivityResultCode RESULT_BROWSE = ActivityResultCode.OTHER;
	
	private static final String TAG = "DialogURLFragment";
	
	private ControllerReviewNode mController;
	private ClearableEditText mURLEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getArguments());
		
		setMiddleButtonAction(ActionType.OTHER);
		setMiddleButtonText(getResources().getString(R.string.button_browse_text));
		setDismissDialogOnMiddleClick(true);

		setDialogTitle(getResources().getString(R.string.dialog_URL_title));
		setDeleteConfirmation(true);
		setDeleteWhatTitle(getResources().getString(R.string.dialog_URL_title));
	}
	
	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_url, null);
		
		mURLEditText = (ClearableEditText)v.findViewById(R.id.url_edit_text);
		if(mController.hasURL())
			mURLEditText.setText(mController.getURLString());
	
		setKeyboardIMEDoDone(mURLEditText);
		
		return v;
	}
	
	@Override
	protected void onDoneButtonClick() {
		String urlText = mURLEditText.getText().toString();
		if(urlText.length() > 0) {			
			try {
				mController.setURL(urlText);
			} catch (Exception e) {
				Log.e(TAG, "Malformed URL", e);
				Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_bad_url), Toast.LENGTH_SHORT).show();
				return;
			}
		}
		super.onDoneButtonClick();
	}
		
	@Override
	protected void onDeleteButtonClick() {
		mController.deleteURL();
	}
	
	@Override
	protected boolean hasDataToDelete() {
		return mController.hasURL();
	}
}
