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
 * Dialog for adding location name: populates with suggestions found near location provided.
 * Finds current location if none provided. Comes up with autocomplete suggestions as user types
 * name.
 */
public class DialogFragmentLocation extends DialogCancelActionDoneFragment implements Locatable,
        LaunchableUi, PlaceSuggester.SuggestionsListener {
    public static final String     LATLNG     = "com.chdryra.android.reviewer.latlng";
    public static final String     FROM_IMAGE = "com.chdryra.android.reviewer.from_image";
    public static final ActionType RESULT_MAP = ActionType.OTHER;

    private static final int    NUMBER_SUGGESTIONS = 10;
    private static final String SEARCHING_NEARBY   = "searching near here...";
    private static final String SEARCHING_IMAGE    = "searching near image...";
    private static final String NO_LOCATION        = "no suggestions found...";

    private DialogFragmentLocationListener mListener;
    private ClearableEditText              mNameEditText;
    private ListView                       mLocationNameSuggestions;
    private LatLng                         mLatLng;
    private LocationClientConnector        mLocationClient;

    private PlaceAutoCompleteSuggester mAutoCompleter;
    private StringFilterAdapter        mAdapter;

    private boolean mLatLngFromImage = false;

    public interface DialogFragmentLocationListener {
        public void onLocationChosen(GvLocationList.GvLocation location);

        public void onMapRequested(GvLocationList.GvLocation location);
    }

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
        setLatLngHere(latLng);
    }

    @Override
    public void onLocationClientConnected(LatLng latLng) {
        setLatLngHere(latLng);
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }

    @Override
    public void onSuggestionsFound(ArrayList<String> addresses) {
        if (addresses.size() == 0) addresses.add(NO_LOCATION);

        mAdapter = new StringFilterAdapter(getActivity(), addresses, mAutoCompleter);
        mLocationNameSuggestions.setAdapter(mAdapter);
    }

    @Override
    protected View createDialogUi() {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_location, null);

        mNameEditText = (ClearableEditText) v.findViewById(R.id.location_edit_text);
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

        if (mLatLng != null) {
            if (mLatLngFromImage) {
                mNameEditText.setHint(R.string.edit_text_name_image_location_hint);
                setSuggestionsAdapter(SEARCHING_IMAGE);
            } else {
                setSuggestionsAdapter(SEARCHING_NEARBY);
            }
        }

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mListener = (DialogFragmentLocationListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getTargetFragment().toString() + " must implement " +
                    "DialogFragmentLocationListener");
        }

        mLocationClient = new LocationClientConnector(getActivity(), this);
        LatLng latLng = getArguments().getParcelable(LATLNG);
        if (latLng != null) {
            mLatLng = latLng;
            mLatLngFromImage = getArguments().getBoolean(FROM_IMAGE);
        } else {
            mLocationClient.connect();
        }

        setActionButtonAction(RESULT_MAP);
        setActionButtonText(getResources().getString(R.string.button_map));
        dismissDialogOnActionClick();
    }

    @Override
    protected void onActionButtonClick() {
        mListener.onMapRequested(createGVData());
    }

    @Override
    protected void onDoneButtonClick() {
        mListener.onLocationChosen(createGVData());
    }

    GvLocationList.GvLocation createGVData() {
        return new GvLocationList.GvLocation(mLatLng, mNameEditText.getText().toString().trim());
    }

    private void setLatLngHere(LatLng latLng) {
        mLatLng = latLng;
        mLatLngFromImage = false;
        setSuggestionsAdapter(SEARCHING_NEARBY);
    }

    private void setSuggestionsAdapter(String searching) {
        ArrayList<String> message = new ArrayList<String>();
        message.add(searching); //A bit hacky....

        //Initial suggestions
        PlaceSuggester suggester = new PlaceSuggester(getActivity(), mLatLng, this);
        suggester.getSuggestions(NUMBER_SUGGESTIONS);

        //Autocomplete suggestions
        mAutoCompleter = new PlaceAutoCompleteSuggester(mLatLng);
        mAdapter = new StringFilterAdapter(getActivity(), message, mAutoCompleter);
        mLocationNameSuggestions.setAdapter(mAdapter);
    }
}
