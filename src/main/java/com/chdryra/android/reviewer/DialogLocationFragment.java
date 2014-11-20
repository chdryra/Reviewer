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
import com.chdryra.android.mygenerallibrary.AutoCompleteAdapter;
import com.chdryra.android.mygenerallibrary.DialogCancelActionDoneFragment;
import com.chdryra.android.mygenerallibrary.LocationClientConnector;
import com.chdryra.android.mygenerallibrary.LocationClientConnector.Locatable;
import com.chdryra.android.mygenerallibrary.PlaceAutoCompleteSuggester;
import com.chdryra.android.mygenerallibrary.PlaceSuggester;
import com.chdryra.android.reviewer.GVImageList.GVImage;
import com.chdryra.android.reviewer.GVLocationList.GVLocation;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Dialog for adding location name: populates with suggestions found near current location. Comes
 * up with autocomplete suggestions as user types name.
 */
public class DialogLocationFragment extends DialogCancelActionDoneFragment implements Locatable,
        LaunchableUI, PlaceSuggester.FetchCompleteListener {
    public static final ActionType RESULT_MAP = ActionType.OTHER;
    private static final int NUMBER_SUGGESTIONS = 10;
    private static final String SEARCHING   = "searching nearby...";
    private static final String NO_LOCATION = "no suggestions found...";

    private ControllerReviewEditable mController;
    private ClearableEditText        mNameEditText;
    private ListView                 mLocationNameSuggestions;
    private LatLng                   mLatLng;
    private LocationClientConnector  mLocationClient;

    private PlaceAutoCompleteSuggester mAutoCompleter;
    private AutoCompleteAdapter        mAdapter;

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
    public void launch(LauncherUI launcher) {
        launcher.launch(this);
    }

    @Override
    public void onAddressesFound(ArrayList<String> addresses) {
        if (addresses.size() == 0) addresses.add(NO_LOCATION);

        mAdapter = new AutoCompleteAdapter(getActivity(), addresses, mAutoCompleter);
        mLocationNameSuggestions.setAdapter(mAdapter);
    }

    @Override
    protected View createDialogUI() {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_location, null);
        mNameEditText = (ClearableEditText) v.findViewById(R.id.location_edit_text);

        if (mController.hasData(GVType.LOCATIONS)) {
            GVLocation location = (GVLocation) mController.getData(GVType.LOCATIONS).getItem(0);
            mLatLng = location.getLatLng();
            mNameEditText.setText(location.getName());
        } else if (mController.hasData(GVType.IMAGES)) {
            GVImage image = (GVImage) mController.getData(GVType.IMAGES).getItem(0);
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
                if (mAdapter != null) {
                    mAdapter.findSuggestions(s);
                }
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
        InputHandlerReviewData<GVLocation> handler = new InputHandlerReviewData<GVLocation>
                (GVType.LOCATIONS);
        handler.pack(InputHandlerReviewData.CurrentNewDatum.NEW, createGVData(),
                createNewReturnData());
    }

    @Override
    protected void onDoneButtonClick() {
        GVLocation location = createGVData();
        if (location.isValidForDisplay()) {
            GVLocationList locations = new GVLocationList();
            locations.add(location);
            mController.setData(locations);
        }
    }

    GVLocation createGVData() {
        return new GVLocation(mLatLng, mNameEditText.getText().toString().trim());
    }

    private void setSuggestionsAdapter() {
        //A bit hacky....
        ArrayList<String> message = new ArrayList<String>();
        message.add(SEARCHING);

        mAutoCompleter = new PlaceAutoCompleteSuggester(mLatLng);
        mAdapter = new AutoCompleteAdapter(getActivity(), message, mAutoCompleter);
        mLocationNameSuggestions.setAdapter(mAdapter);

        PlaceSuggester suggester = new PlaceSuggester(getActivity(), mLatLng, this);
        suggester.fetch(NUMBER_SUGGESTIONS);
    }
}
