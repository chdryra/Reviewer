/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.GVLocationList.GVLocation;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * UI Fragment: review locations. Each grid cell shows a location name.
 * <p/>
 * <p>
 * FragmentReviewGrid functionality:
 * <ul>
 * <li>Subject: disabled</li>
 * <li>RatingBar: disabled</li>
 * <li>Banner button: launches ActivityReviewLocationMap showing current location</li>
 * <li>Grid cell click: launches ActivityReviewLocationMap showing clicked location</li>
 * <li>Grid cell long click: same as click</li>
 * </ul>
 * </p>
 *
 * @see com.chdryra.android.reviewer.ActivityReviewLocations
 * @see com.chdryra.android.reviewer.ActivityReviewLocationMap
 */
public class FragmentReviewLocations extends FragmentReviewGridAddEdit<GVLocation> {
    public static final String SUBJECT = "com.chdryra.android.reviewer.subject";

    public FragmentReviewLocations() {
        mDataType = GVType.LOCATIONS;
        setResultCode(Action.ADD, ActivityResultCode.DONE);
    }

    @Override
    protected void onBannerButtonClick() {
        requestMapIntent(getRequestCodeAdd(), null);
    }

    @Override
    protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
        GVLocation location = (GVLocation) parent.getItemAtPosition(position);
        requestMapIntent(getRequestCodeEdit(), location);
    }

    private void requestMapIntent(int requestCode, GVLocation location) {
        Intent i = new Intent(getActivity(), ActivityReviewLocationMap.class);
        i.putExtra(SUBJECT, getController().getSubject());
        getInputHandler().pack(InputHandlerReviewData.CurrentNewDatum.CURRENT, location, i);
        startActivityForResult(i, requestCode);
    }
}
