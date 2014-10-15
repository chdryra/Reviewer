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
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.chdryra.android.reviewer.GVUrlList.GVUrl;

import java.net.URL;

/**
 * UI Fragment: URLs (currently disabled). Each grid cell shows a URL.
 * <p/>
 * <p>
 * FragmentReviewGrid functionality:
 * <ul>
 * <li>Subject: disabled</li>
 * <li>RatingBar: disabled</li>
 * <li>Banner button: launches ActivityReviewURLBrowser with Google home</li>
 * <li>Grid cell click: launches ActivityReviewURLBrowser with selected link</li>
 * <li>Grid cell long click: same as click</li>
 * </ul>
 * </p>
 *
 * @see com.chdryra.android.reviewer.ActivityReviewURLs
 * @see com.chdryra.android.reviewer.ActivityReviewURLBrowser
 */
public class FragmentReviewURLs extends FragmentReviewGridAddEdit<GVUrl> {
    private final static String URL = FragmentReviewURLBrowser.URL;

    private GVUrlList mUrls;

    public FragmentReviewURLs() {
        super(GVType.URLS);
    }

    @Override
    protected void doDatumAdd(Intent data) {
        URL url = (URL) data.getSerializableExtra(FragmentReviewURLBrowser.URL);
        if (url != null && !mUrls.contains(url)) {
            mUrls.add(url);
        }
    }

    @Override
    protected void doDatumDelete(Intent data) {
        mUrls.remove((URL) data.getSerializableExtra(FragmentReviewURLBrowser.URL_OLD));
    }

    @Override
    protected void doDatumEdit(Intent data) {
        URL oldUrl = (URL) data.getSerializableExtra(FragmentReviewURLBrowser.URL_OLD);
        URL newUrl = (URL) data.getSerializableExtra(FragmentReviewURLBrowser.URL);
        if (!oldUrl.equals(newUrl) && mUrls.contains(newUrl)) {
            Toast.makeText(getActivity(), getResources().getString(R.string.toast_has_url),
                    Toast.LENGTH_SHORT).show();
        } else {
            mUrls.remove(oldUrl);
            mUrls.add(newUrl);
        }
    }

    @Override
    protected Bundle packGridCellData(GVUrl data, Bundle args) {
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrls = (GVUrlList) getGridData();
        setResultCode(Action.ADD, ActivityResultCode.DONE);
    }

    @Override
    protected void onBannerButtonClick() {
        requestBrowserIntent(getRequestCodeAdd(), null);
    }

    @Override
    protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
        requestBrowserIntent(getRequestCodeEdit(), ((GVUrl) parent.getItemAtPosition
                (position))
                .getUrl());
    }

    private void requestBrowserIntent(int requestCode, URL url) {
        Intent i = new Intent(getActivity(), ActivityReviewURLBrowser.class);
        i.putExtra(URL, url);
        startActivityForResult(i, requestCode);
    }
}
