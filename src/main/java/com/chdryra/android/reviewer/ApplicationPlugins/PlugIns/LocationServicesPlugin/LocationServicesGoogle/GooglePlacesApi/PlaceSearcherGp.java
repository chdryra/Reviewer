/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.LocationServicesGoogle.GooglePlacesApi;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetails;
import com.chdryra.android.reviewer.LocationServices.Interfaces.PlaceSearcher;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PlaceSearcherGp extends GoogleLocationServiceBasic implements
        PlaceSearcher, ResultCallback<AutocompletePredictionBuffer>{
    private PlaceSearcherListener mListener;
    private LatLng mLatLng;
    private String mQuery;

    public PlaceSearcherGp(GoogleApiClient client) {
        super(client);
    }

    @Override
    public void searchQuery(String query, LatLng nearLatLng, PlaceSearcherListener listener) {
        mQuery = query;
        mLatLng = nearLatLng;
        mListener = listener;
        connectAndDoRequest();
    }

    @Override
    protected void onNotConnected() {
        mListener.onSearchResultsFound(new ArrayList<LocationDetails>());
    }

    @Override
    public void onNotPermissioned() {
        mListener.onNotPermissioned();
    }

    @Override
    protected void doRequestOnConnected() {
        LatLngBounds bounds = LatLngBounds.builder().include(mLatLng).build();
        AutocompleteFilter filter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE)
                .build();

        Places.GeoDataApi
                .getAutocompletePredictions(getClient(), mQuery, bounds, filter)
                .setResultCallback(this);
    }

    @Override
    public void onResult(@NonNull AutocompletePredictionBuffer predictions) {
        ArrayList<AutocompletePrediction> idable = new ArrayList<>();
        int numPredictions = 0;
        for(AutocompletePrediction prediction : predictions) {
            if(prediction.getPlaceId() != null) {
                idable.add(prediction.freeze());
                numPredictions++;
            }
        }

        predictions.release();

        DetailsCallback callback = new DetailsCallback(numPredictions);
        for(AutocompletePrediction prediction : idable) {
            Places.GeoDataApi.getPlaceById(getClient(), prediction.getPlaceId()).setResultCallback(callback);
        }
    }

    private class DetailsCallback implements ResultCallback<PlaceBuffer> {
        private ArrayList<LocationDetails> mDetails;
        private int mNumDetailsToExpect = 0;

        public DetailsCallback(int numDetailsToExpect) {
            mDetails = new ArrayList<>();
            mNumDetailsToExpect = numDetailsToExpect;
        }

        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (places.getStatus().isSuccess() && places.getCount() > 0) {
                mDetails.add(new LocationDetailsGoogle(places.get(0).freeze()));
                places.release();
            }

            mNumDetailsToExpect--;
            if(mNumDetailsToExpect == 0) {
                mListener.onSearchResultsFound(mDetails);
                disconnect();
            }
        }
    }
}
