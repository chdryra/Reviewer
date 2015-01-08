/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogCancelActionDoneFragment;
import com.chdryra.android.mygenerallibrary.LocationClientConnector;
import com.chdryra.android.mygenerallibrary.LocationClientConnector.Locatable;
import com.chdryra.android.mygenerallibrary.PlaceAutoCompleteSuggester;
import com.chdryra.android.mygenerallibrary.PlaceSuggester;
import com.chdryra.android.mygenerallibrary.StringFilterAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Dialog for adding location name: populates with suggestions found near current location. Comes
 * up with autocomplete suggestions as user types name.
 */
public class DialogFragmentLocation extends DialogCancelActionDoneFragment implements Locatable,
        LaunchableIU2, PlaceSuggester.SuggestionsListener {
    public static final  ActionType RESULT_MAP         = ActionType.OTHER;
    private static final int        NUMBER_SUGGESTIONS = 10;
    private static final String     SEARCHING          = "searching nearby...";
    private static final String     NO_LOCATION        = "no suggestions found...";

    private ControllerReviewEditable mController;
    private ClearableEditText        mNameEditText;
    private ListView                 mLocationNameSuggestions;
    private LatLng                   mLatLng;
    private LocationClientConnector  mLocationClient;

    private PlaceAutoCompleteSuggester mAutoCompleter;
    private StringFilterAdapter        mAdapter;

    @Override
    public void onStop() {
        super.onStop();
        mLocationClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.disconnect();
    }

    @Override
    public void onLocated(LatLng latLng) {
        mLatLng = latLng;
        setSuggestionsAdapter();
    }

    @Override
    public void onLocationClientConnected(LatLng latLng) {
        mLatLng = latLng;
        setSuggestionsAdapter();
    }

    @Override
    public void launch(LauncherIU2 launcher) {
        launcher.launch(this);
    }

    @Override
    public void onSuggestionsFound(ArrayList<String> addresses) {
        if (addresses.size() == 0) addresses.add(NO_LOCATION);

        mAdapter = new StringFilterAdapter(getActivity(), addresses, mAutoCompleter);
        mLocationNameSuggestions.setAdapter(mAdapter);
    }

    @Override
    protected View createDialogUI() {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_location, null);
        mNameEditText = (ClearableEditText) v.findViewById(R.id.location_edit_text);

        if (mController.hasData(GvDataList.GvType.LOCATIONS)) {
            GvLocationList.GvLocation location = (GvLocationList.GvLocation) mController.getData
                    (GvDataList.GvType.LOCATIONS).getItem(0);
            mLatLng = location.getLatLng();
            mNameEditText.setText(location.getName());
        } else if (mController.hasData(GvDataList.GvType.IMAGES)) {
            GvImageList.GvImage image = (GvImageList.GvImage) mController.getData(GvDataList
                    .GvType.IMAGES).getItem(0);
            LatLng latLng = image.getLatLng();
            if (latLng != null) {
                mLatLng = latLng;
            }
        }

        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mAdapter != null) mAdapter.filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        setKeyboardDoDoneOnEditText(mNameEditText);

        mLocationNameSuggestions = (ListView) v.findViewById(R.id.suggestions_list_view);
        mLocationNameSuggestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String locationName = (String) parent.getAdapter().getItem(position);
                mNameEditText.setText(locationName);
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mController = (ControllerReviewEditable) Administrator.get(getActivity()).unpack
                (getArguments());
        mLocationClient = new LocationClientConnector(getActivity(), this);
        mLocationClient.connect();

        setActionButtonAction(RESULT_MAP);
        setActionButtonText(getResources().getString(R.string.button_map));
        dismissDialogOnActionClick();
    }

    @Override
    protected void onActionButtonClick() {
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.NEW, createGVData(),
                createNewReturnData());
    }

    @Override
    protected void onDoneButtonClick() {
        GvLocationList.GvLocation location = createGVData();
        if (location.isValidForDisplay()) {
            GvLocationList locations = new GvLocationList();
            locations.add(location);
            mController.setData(locations);
        }
    }

    GvLocationList.GvLocation createGVData() {
        return new GvLocationList.GvLocation(mLatLng, mNameEditText.getText().toString().trim());
    }

    private void setSuggestionsAdapter() {
        //A bit hacky....
        ArrayList<String> message = new ArrayList<String>();
        message.add(SEARCHING);

        mAutoCompleter = new PlaceAutoCompleteSuggester(mLatLng);
        mAdapter = new StringFilterAdapter(getActivity(), message, mAutoCompleter);
        mLocationNameSuggestions.setAdapter(mAdapter);

        PlaceSuggester suggester = new PlaceSuggester(getActivity(), mLatLng, this);
        suggester.getSuggestions(NUMBER_SUGGESTIONS);
    }
}
