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
public class DialogLocation extends DialogCancelActionDoneFragment implements Locatable,
        LaunchableUi, PlaceSuggester.SuggestionsListener {
    public static final  String     LATLNG             = "com.chdryra.android.reviewer.latlng";
    public static final  String     FROM_IMAGE         = "com.chdryra.android.reviewer.from_image";
    public static final  ActionType RESULT_MAP         = ActionType.OTHER;
    private static final int        LAYOUT             = R.layout.dialog_location;
    private static final int        EDITTEXT           = R.id.location_edit_text;
    private static final int        NUMBER_SUGGESTIONS = 10;
    private static final int        SEARCHING_NEARBY   = R.string.edit_text_searching_near_here;
    private static final int        SEARCHING_IMAGE    = R.string.edit_text_searching_near_image;
    private static final int        NO_LOCATION        = R.string.edit_text_no_suggestions;

    private DialogFragmentLocationListener mListener;
    private ClearableEditText              mNameEditText;
    private ListView                       mLocationNameSuggestions;
    private LatLng                         mLatLng;
    private LocationClientConnector        mLocationClient;

    private PlaceAutoCompleteSuggester mAutoCompleter;
    private StringFilterAdapter        mFilter;

    private boolean mLatLngProvided = false;
    private boolean mFromImage      = false;

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
        onLatLngFound(latLng);
    }

    @Override
    public void onLocationClientConnected(LatLng latLng) {
        onLatLngFound(latLng);
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }

    @Override
    public void onSuggestionsFound(ArrayList<String> addresses) {
        if (addresses.size() == 0) addresses.add(getResources().getString(NO_LOCATION));
        setNewSuggestionsAdapter(addresses);
    }

    @Override
    protected View createDialogUi() {
        View v = getActivity().getLayoutInflater().inflate(LAYOUT, null);

        mNameEditText = (ClearableEditText) v.findViewById(EDITTEXT);
        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mFilter != null) mFilter.filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        setKeyboardDoDoneOnEditText(mNameEditText);
        if (mFromImage) mNameEditText.setHint(R.string.edit_text_name_image_location_hint);

        mLocationNameSuggestions = (ListView) v.findViewById(R.id.suggestions_list_view);
        mLocationNameSuggestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String locationName = (String) parent.getAdapter().getItem(position);
                mNameEditText.setText(locationName);
            }
        });

        if (mLatLngProvided) findPlaceSuggestions();

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListener = getTargetListener(DialogFragmentLocationListener.class);
        mLocationClient = new LocationClientConnector(getActivity(), this);
        LatLng latLng = getArguments().getParcelable(LATLNG);
        if (latLng != null) {
            mLatLng = latLng;
            mFromImage = getArguments().getBoolean(FROM_IMAGE);
            mLatLngProvided = true;
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
        GvLocationList.GvLocation location = createGVData();
        if (location.isValidForDisplay()) mListener.onLocationChosen(createGVData());
    }

    private GvLocationList.GvLocation createGVData() {
        return new GvLocationList.GvLocation(mLatLng, mNameEditText.getText().toString().trim());
    }

    private void onLatLngFound(LatLng latLng) {
        mLatLng = latLng;
        mFromImage = false;
        findPlaceSuggestions();
    }

    private void findPlaceSuggestions() {
        //Autocomplete suggestions
        mAutoCompleter = new PlaceAutoCompleteSuggester(mLatLng);

        //Initial suggestions
        PlaceSuggester suggester = new PlaceSuggester(getActivity(), mLatLng, this);
        suggester.getSuggestions(NUMBER_SUGGESTIONS);

        //Whilst initial suggestions are being found....
        ArrayList<String> message = new ArrayList<String>();
        message.add(getResources().getString(mFromImage ? SEARCHING_IMAGE : SEARCHING_NEARBY));
        setNewSuggestionsAdapter(message);
    }

    private void setNewSuggestionsAdapter(ArrayList<String> suggestions) {
        mFilter = new StringFilterAdapter(getActivity(), suggestions, mAutoCompleter);
        mLocationNameSuggestions.setAdapter(mFilter);
    }
}
