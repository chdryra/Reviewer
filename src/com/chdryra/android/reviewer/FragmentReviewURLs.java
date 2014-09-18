package com.chdryra.android.reviewer;

import java.net.URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.chdryra.android.reviewer.GVUrlList.GVUrl;

public class FragmentReviewURLs  extends FragmentReviewGridAddEditDone<GVUrl> {
	public final static String URL = FragmentReviewURLBrowser.URL;
	
	private GVUrlList mUrls;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		mUrls = (GVUrlList) setAndInitData(GVType.URLS);
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_urls_title));
		setBannerButtonText(getResources().getString(R.string.button_add_url));
	}

	@Override
	protected void onBannerButtonClick() {
		requestBrowserIntent(DATA_ADD, null);
	}
	
	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		requestBrowserIntent(DATA_EDIT, ((GVUrl)parent.getItemAtPosition(position)).getUrl());
	}

	private void requestBrowserIntent(int requestCode, URL url) {
		Intent i = new Intent(getActivity(), ActivityReviewURLBrowser.class);
		i.putExtra(URL, url);
		startActivityForResult(i, requestCode);
	}
	
	@Override
	protected void addData(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case DONE:
			URL url = (URL)data.getSerializableExtra(FragmentReviewURLBrowser.URL);
			if(url != null && !mUrls.contains(url))
				mUrls.add(url);
			break;
		default:
			return;
		}
	}
	
	@Override
	protected void editData(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case DONE:
			URL oldUrl = (URL)data.getSerializableExtra(FragmentReviewURLBrowser.URL_OLD);
			URL newUrl = (URL)data.getSerializableExtra(FragmentReviewURLBrowser.URL);
			mUrls.remove(oldUrl);
			mUrls.add(newUrl);
			break;
		case DELETE:
			URL toDelete = (URL)data.getSerializableExtra(FragmentReviewURLBrowser.URL_OLD);
			mUrls.remove(toDelete);
			break;
		default:
			return;
		}
	}
}
