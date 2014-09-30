/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.chdryra.android.reviewer.GVUrlList.GVUrl;

import java.net.URL;

public class FragmentReviewUrls extends FragmentReviewGridAddEditDone<GVUrl> {
    private final static String URL = FragmentReviewUrlBrowser.URL;

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
        requestBrowserIntent(DATA_EDIT, ((GVUrl) parent.getItemAtPosition(position)).getUrl());
    }

    private void requestBrowserIntent(int requestCode, URL url) {
        Intent i = new Intent(getActivity(), ActivityReviewUrlBrowser.class);
        i.putExtra(URL, url);
        startActivityForResult(i, requestCode);
    }

    @Override
    protected void addData(int resultCode, Intent data) {
        switch (ActivityResultCode.get(resultCode)) {
            case DONE:
                URL url = (URL) data.getSerializableExtra(FragmentReviewUrlBrowser.URL);
                if (url != null && !mUrls.contains(url)) {
                    mUrls.add(url);
                }
                break;
            default:
        }
    }

    @Override
    protected void editData(int resultCode, Intent data) {
        switch (ActivityResultCode.get(resultCode)) {
            case DONE:
                URL oldUrl = (URL) data.getSerializableExtra(FragmentReviewUrlBrowser.URL_OLD);
                URL newUrl = (URL) data.getSerializableExtra(FragmentReviewUrlBrowser.URL);
                mUrls.remove(oldUrl);
                mUrls.add(newUrl);
                break;
            case DELETE:
                URL toDelete = (URL) data.getSerializableExtra(FragmentReviewUrlBrowser.URL_OLD);
                mUrls.remove(toDelete);
                break;
            default:
        }
    }
}
