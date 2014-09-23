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
import com.chdryra.android.reviewer.GVLocationList.GVLocation;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.google.android.gms.maps.model.LatLng;

public class FragmentReviewLocations  extends FragmentReviewGridAddEditDone<GVLocation> {
	public final static String LATLNG = FragmentReviewLocationMap.LATLNG;
	public final static String NAME = FragmentReviewLocationMap.NAME;
	public final static String SUBJECT = FragmentReviewLocationMap.SUBJECT;
	
	private GVLocationList mLocations;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		mLocations = (GVLocationList) setAndInitData(GVType.LOCATIONS);
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_locations_title));
		setBannerButtonText(getResources().getString(R.string.button_add_location));
	}

	@Override
	protected void onBannerButtonClick() {
		requestMapIntent(DATA_ADD, null, null);
	}

	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		GVLocation location = (GVLocation)parent.getItemAtPosition(position);
		requestMapIntent(DATA_EDIT, location.getLatLng(), location.getName());
	}

	private void requestMapIntent(int requestCode, LatLng latLng, String name) {
		Intent i = new Intent(getActivity(), ActivityReviewLocationMap.class);
		i.putExtra(LATLNG, latLng);
		i.putExtra(NAME, name);
		i.putExtra(SUBJECT, getController().getSubject());
		startActivityForResult(i, requestCode);
	}

	@Override
	protected void addData(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case DONE:
			LatLng latLng = data.getParcelableExtra(FragmentReviewLocationMap.LATLNG);
			String name = (String)data.getSerializableExtra(FragmentReviewLocationMap.NAME);
			if(latLng != null && name != null && !mLocations.contains(latLng, name))
				mLocations.add(latLng, name);			
			break;
		default:
			return;
		}
	}
	
	@Override
	protected void editData(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case DONE:
			LatLng oldLatLng = data.getParcelableExtra(FragmentReviewLocationMap.LATLNG_OLD);
			LatLng newLatLng = data.getParcelableExtra(FragmentReviewLocationMap.LATLNG);
			String oldName = (String)data.getSerializableExtra(FragmentReviewLocationMap.NAME_OLD);
			String newName = (String)data.getSerializableExtra(FragmentReviewLocationMap.NAME);
			mLocations.remove(oldLatLng, oldName);
			mLocations.add(newLatLng, newName);
			break;
		case DELETE:
			LatLng deleteLatLng = data.getParcelableExtra(FragmentReviewLocationMap.LATLNG_OLD);
			String deleteName = (String)data.getSerializableExtra(FragmentReviewLocationMap.NAME_OLD);
			mLocations.remove(deleteLatLng, deleteName);
			break;
		default:
			return;
		}
	}
}
