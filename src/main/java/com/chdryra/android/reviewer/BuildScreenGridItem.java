/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 28 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.LocationClientConnector;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 28/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenGridItem extends ReviewViewAction.GridItemAction {
    private static final String TAG = "GridItemBuildUiListener";
    private BuildListener           mListener;
    private LatLng                  mLatLng;
    private ImageChooser            mImageChooser;
    private LocationClientConnector mLocationClient;

    public BuildScreenGridItem() {
        mListener = new BuildListener() {
        };
        registerActionListener(mListener, TAG);
    }

    @Override
    public void onUnattachReviewView() {
        super.onUnattachReviewView();
        mLocationClient.disconnect();
    }

    @Override
    public void onAttachReviewView() {
        mImageChooser = Administrator.getImageChooser(getActivity());
        mLocationClient = new LocationClientConnector(getActivity(), mListener);
        mLocationClient.connect();
    }

    @Override
    public void onGridItemClick(GvDataList.GvData item, View v) {
        executeIntent((GvBuildReviewList.GvBuildReview) item, true);
    }

    @Override
    public void onGridItemLongClick(GvDataList.GvData item, View v) {
        executeIntent((GvBuildReviewList.GvBuildReview) item, false);
    }

    private void executeIntent(GvBuildReviewList.GvBuildReview gridCell, boolean quickDialog) {
        if (quickDialog && gridCell.getDataSize() == 0) {
            showQuickDialog(gridCell.getConfig());
        } else {
            startActivity(gridCell.getConfig());
        }
    }

    private ReviewBuilder getBuilder() {
        return (ReviewBuilder) getAdapter();
    }

    private void addLocation(GvLocationList.GvLocation location) {
        if (location.isValidForDisplay()) {
            ReviewBuilder.DataBuilder builder = getBuilder().getDataBuilder(GvDataList.GvType
                    .LOCATIONS);
            //TODO make type safe
            builder.add(location);
            builder.setData();
        }
    }

    private void startMapActivity(GvLocationList.GvLocation location) {
        Bundle args = new Bundle();
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, location, args);

        LaunchableUi mapUi = ConfigGvDataUi.getLaunchable(ActivityEditLocationMap.class);
        LauncherUi.launch(mapUi, mListener, getLocationMRequestCode(), null, args);
    }

    private void startActivity(ConfigGvDataUi.Config config) {
        Intent i = new Intent(getActivity(), ActivityReviewView.class);
        Administrator admin = Administrator.get(getActivity());
        admin.packView(FactoryReviewView.newEditScreen(getActivity(), config.getGvType()), i);

        mListener.startActivity(i);
    }

    private void showQuickDialog(ConfigGvDataUi.Config config) {
        GvDataList.GvType dataType = config.getGvType();

        if (dataType == GvDataList.GvType.IMAGES) {
            mListener.startActivityForResult(mImageChooser.getChooserIntents(),
                    getImageRequestCode());
            return;
        }

        Bundle args = new Bundle();
        args.putBoolean(DialogAddGvData.QUICK_SET, true);

        ConfigGvDataUi.LaunchableConfig adderConfig = config.getAdderConfig();
        LaunchableUi ui;
        if (dataType == GvDataList.GvType.LOCATIONS) {
            ui = ConfigGvDataUi.getLaunchable(DialogLocation.class);
            packLatLng(args);
        } else {
            ui = adderConfig.getLaunchable();
        }

        LauncherUi.launch(ui, mListener, adderConfig.getRequestCode(), adderConfig.getTag(),
                args);
    }

    private void packLatLng(Bundle args) {
        LatLng latLng = mLatLng;
        boolean fromImage = false;

        GvImageList images = (GvImageList) getBuilder().getDataBuilder(GvDataList.GvType.IMAGES)
                .getGridData();
        if (images.size() > 0) {
            LatLng coverLatLng = images.getCovers().getItem(0).getLatLng();
            if (coverLatLng != null) {
                latLng = coverLatLng;
                fromImage = true;
            }
        }

        args.putParcelable(DialogLocation.LATLNG, latLng);
        args.putBoolean(DialogLocation.FROM_IMAGE, fromImage);
    }

    private int getImageRequestCode() {
        return ConfigGvDataUi.getConfig(GvDataList.GvType.IMAGES).getAdderConfig()
                .getRequestCode();
    }

    private int getLocationMRequestCode() {
        return ConfigGvDataUi.getConfig(GvDataList.GvType.LOCATIONS).getEditorConfig()
                .getRequestCode();
    }

    private abstract class BuildListener extends Fragment implements
            ImageChooser.ImageChooserListener,
            LocationClientConnector.Locatable,
            DialogLocation.DialogFragmentLocationListener {

        @Override
        public void onLocated(LatLng latLng) {
            mLatLng = latLng;
        }

        @Override
        public void onLocationClientConnected(LatLng latLng) {
            mLatLng = latLng;
        }

        @Override
        public void onLocationChosen(GvLocationList.GvLocation location) {
            addLocation(location);
        }

        @Override
        public void onMapRequested(GvLocationList.GvLocation location) {
            startMapActivity(location);
        }

        @Override
        public void onImageChosen(GvImageList.GvImage image) {
            image.setIsCover(true);
            ReviewBuilder.DataBuilder builder = getBuilder().getDataBuilder(GvDataList.GvType
                    .IMAGES);
            builder.add(image);
            builder.setData();
            getReviewView().updateUi();
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            ActivityResultCode result = ActivityResultCode.get(resultCode);

            boolean imageRequested = requestCode == getImageRequestCode();
            boolean mapRequested = requestCode == getLocationMRequestCode();

            if (imageRequested && mImageChooser.chosenImageExists(result, data)) {
                mImageChooser.getChosenImage(this);
            } else if (mapRequested && result.equals(ActivityResultCode.DONE)) {
                addLocation((GvLocationList.GvLocation) GvDataPacker.unpackItem(GvDataPacker
                        .CurrentNewDatum.NEW, data));
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }

            getReviewView().updateUi();
        }
    }
}
