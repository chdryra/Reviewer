/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogDeleteCancelDoneFragment;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.chdryra.android.reviewer.GVUrlList.GVUrl;

public class DialogURLFragment extends DialogDeleteCancelDoneFragment {
	public static final ActionType RESULT_BROWSE = ActionType.OTHER;
	public static final String URL = FragmentReviewURLBrowser.URL;
	public static final String URL_OLD = FragmentReviewURLBrowser.URL_OLD;
	
	private static final String TAG = "DialogURLFragment";
	
	private ControllerReviewNode mController;
	private ClearableEditText mURLEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mController = Administrator.get(getActivity()).unpack(getArguments());
		
		setLeftButtonAction(RESULT_BROWSE);
		setLeftButtonText(getResources().getString(R.string.button_browse_text));
		setDismissDialogOnLeftClick(true);

		setDialogTitle(getResources().getString(R.string.dialog_url_title));
		setDeleteConfirmation(true);
		setDeleteWhatTitle(getResources().getString(R.string.dialog_url_title));
	}
	
	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_url, null);
		
		mURLEditText = (ClearableEditText)v.findViewById(R.id.url_edit_text);
		if(mController.getData(GVType.URLS).size() == 1)
			mURLEditText.setText(((GVUrl)mController.getData(GVType.URLS).getItem(0)).toShortenedString());
	
		setKeyboardIMEDoDone(mURLEditText);
		
		return v;
	}
	
	@Override
	protected void onDoneButtonClick() {
		String urlString = mURLEditText.getText().toString();
		if(urlString.length() > 0) {			
			try {
				GVUrlList singleURL = new GVUrlList();
				singleURL.add(URLUtil.guessUrl(urlString));
				mController.setData(singleURL);
			} catch (Exception e) {
				Log.e(TAG, "Malformed URL", e);
				Toast.makeText(getActivity(), getResources().getString(R.string.toast_bad_url), Toast.LENGTH_SHORT).show();
				return;
			}
		}
	}
		
	@Override
	protected void onDeleteButtonClick() {
		GVUrlList urls = (GVUrlList) mController.getData(GVType.URLS);
		if(urls.size() == 1)
			urls.remove(urls.getItem(0));
	}
	
	@Override
	protected boolean hasDataToDelete() {
		return mController.hasData(GVType.URLS);
	}
}
