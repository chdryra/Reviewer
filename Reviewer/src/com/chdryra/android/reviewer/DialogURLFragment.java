package com.chdryra.android.reviewer;

import java.net.URL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogDeleteCancelDoneFragment;
import com.chdryra.android.mygenerallibrary.RandomTextUtils;

public class DialogURLFragment extends DialogDeleteCancelDoneFragment {
	public static final ActionType RESULT_BROWSE = ActionType.OTHER;
	public static final String URL = FragmentReviewURLBrowser.URL;
	public static final String URL_OLD = FragmentReviewURLBrowser.URL_OLD;
	
	private static final String TAG = "DialogURLFragment";
	
	private URL mUrl;
	private ClearableEditText mURLEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mUrl = getArguments().getParcelable(URL);
		
		setMiddleButtonAction(RESULT_BROWSE);
		setMiddleButtonText(getResources().getString(R.string.button_browse_text));
		setDismissDialogOnMiddleClick(true);

		setDialogTitle(getResources().getString(R.string.dialog_url_title));
		setDeleteConfirmation(true);
		setDeleteWhatTitle(getResources().getString(R.string.dialog_url_title));
	}
	
	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_url, null);
		
		mURLEditText = (ClearableEditText)v.findViewById(R.id.url_edit_text);
		if(mUrl != null)
			mURLEditText.setText(RandomTextUtils.toShortenedStringURL(mUrl));
	
		setKeyboardIMEDoDone(mURLEditText);
		
		return v;
	}
	
	@Override
	protected void onDoneButtonClick() {
		String urlString = mURLEditText.getText().toString();
		if(urlString.length() > 0) {			
			try {
				URL url = new URL(URLUtil.guessUrl(urlString));
				Intent i = getNewReturnData();
				i.putExtra(URL, url);
				i.putExtra(URL_OLD, mUrl);
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
		getNewReturnData().putExtra(URL_OLD, mUrl);
	}
	
	@Override
	protected boolean hasDataToDelete() {
		return mUrl != null;
	}
}
