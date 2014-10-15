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
import com.chdryra.android.reviewer.GVLocationList.GVLocation;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.google.android.gms.maps.model.LatLng;

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
    private final static String LATLNG  = FragmentReviewLocationMap.LATLNG;
    private final static String NAME    = FragmentReviewLocationMap.NAME;
    private final static String SUBJECT = FragmentReviewLocationMap.SUBJECT;

    private GVLocationList mLocations;

    public FragmentReviewLocations() {
        super(GVType.LOCATIONS);
    }

    @Override
    protected void doDatumAdd(Intent data) {
        LatLng latLng = data.getParcelableExtra(FragmentReviewLocationMap.LATLNG);
        String name = (String) data.getSerializableExtra(FragmentReviewLocationMap.NAME);
        if (latLng != null && name != null && !mLocations.contains(latLng, name)) {
            mLocations.add(latLng, name);
        }
    }

    @Override
    protected void doDatumDelete(Intent data) {
        LatLng deleteLatLng = data.getParcelableExtra(FragmentReviewLocationMap.LATLNG_OLD);
        String deleteName = (String) data.getSerializableExtra(FragmentReviewLocationMap
                .NAME_OLD);
        mLocations.remove(deleteLatLng, deleteName);
    }

    @Override
    protected void doDatumEdit(Intent data) {
        LatLng oldLatLng = data.getParcelableExtra(FragmentReviewLocationMap.LATLNG_OLD);
        String oldName = (String) data.getSerializableExtra(FragmentReviewLocationMap.NAME_OLD);
        LatLng newLatLng = data.getParcelableExtra(FragmentReviewLocationMap.LATLNG);
        String newName = (String) data.getSerializableExtra(FragmentReviewLocationMap.NAME);
        if (!oldLatLng.equals(newLatLng) && mLocations.contains(newLatLng, newName)) {
            Toast.makeText(getActivity(), getResources().getString(R.string
                            .toast_has_location),
                    Toast.LENGTH_SHORT).show();
        } else {
            mLocations.remove(oldLatLng, oldName);
            mLocations.add(newLatLng, newName);
        }
    }

    @Override
    protected Bundle packGridCellData(GVLocation data, Bundle args) {
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocations = (GVLocationList) getGridData();
        setResultCode(Action.ADD, ActivityResultCode.DONE);
    }

    @Override
    protected void onBannerButtonClick() {
        requestMapIntent(getRequestCodeAdd(), null, null);
    }

    @Override
    protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
        GVLocation location = (GVLocation) parent.getItemAtPosition(position);
        requestMapIntent(getRequestCodeEdit(), location.getLatLng(),
                location.getName());
    }

    private void requestMapIntent(int requestCode, LatLng latLng, String name) {
        Intent i = new Intent(getActivity(), ActivityReviewLocationMap.class);
        i.putExtra(LATLNG, latLng);
        i.putExtra(NAME, name);
        i.putExtra(SUBJECT, getController().getSubject());
        startActivityForResult(i, requestCode);
    }
}
