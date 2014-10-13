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

    @Override
    public GVType getGVType() {
        return GVType.URLS;
    }

    @Override
    protected void addData(int resultCode, Intent data) {
        switch (ActivityResultCode.get(resultCode)) {
            case DONE:
                URL url = (URL) data.getSerializableExtra(FragmentReviewURLBrowser.URL);
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
                URL oldUrl = (URL) data.getSerializableExtra(FragmentReviewURLBrowser.URL_OLD);
                URL newUrl = (URL) data.getSerializableExtra(FragmentReviewURLBrowser.URL);
                mUrls.remove(oldUrl);
                mUrls.add(newUrl);
                break;
            case DELETE:
                URL toDelete = (URL) data.getSerializableExtra(FragmentReviewURLBrowser.URL_OLD);
                mUrls.remove(toDelete);
                break;
            default:
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
