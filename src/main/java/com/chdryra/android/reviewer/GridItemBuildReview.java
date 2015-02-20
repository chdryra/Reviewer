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
public class GridItemBuildReview extends ViewReviewAction.GridItemAction {
    private static final String TAG = "GridItemBuildUiListener";
    private BuildListener           mListener;
    private LatLng                  mLatLng;
    private ImageChooser            mImageChooser;
    private LocationClientConnector mLocationClient;

    public GridItemBuildReview() {
        mListener = new BuildListener() {
        };
        registerActionListener(mListener, TAG);
    }

    @Override
    public void onUnsetViewReview() {
        super.onUnsetViewReview();
        mLocationClient.disconnect();
    }

    @Override
    public void onSetViewReview() {
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
            GvLocationList list = new GvLocationList();
            list.add(location);
            getBuilder().getDataBuilder(GvDataList.GvType.LOCATIONS).setData(list);
        }
    }

    private void startMapActivity(GvLocationList.GvLocation location) {
        Bundle args = new Bundle();
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, location, args);

        LaunchableUi mapUi = ConfigGvDataUi.getLaunchable(ActivityEditLocationMap.class);
        LauncherUi.launch(mapUi, mListener, getLocationMRequestCode(), null, args);
    }

    private void startActivity(ConfigGvDataUi.Config config) {
        GvDataList.GvType dataType = config.getGvType();
        boolean isEdit = !(dataType == GvDataList.GvType.SHARE);

        Intent i = new Intent(getActivity(), ActivityViewReview.class);
        ActivityViewReview.packParameters(dataType, isEdit, i);
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
        args.putBoolean(DialogFragmentGvDataAdd.QUICK_SET, true);

        ConfigGvDataUi.LaunchableConfig adderConfig = config.getAdderConfig();
        LaunchableUi ui;
        if (dataType == GvDataList.GvType.LOCATIONS) {
            ui = ConfigGvDataUi.getLaunchable(DialogFragmentLocation.class);
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

        args.putParcelable(DialogFragmentLocation.LATLNG, latLng);
        args.putBoolean(DialogFragmentLocation.FROM_IMAGE, fromImage);
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
            DialogFragmentLocation.DialogFragmentLocationListener {

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
            GvImageList images = new GvImageList();
            images.add(image);
            getBuilder().getDataBuilder(GvDataList.GvType.IMAGES).setData(images);
            getViewReview().updateUi();
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

            getViewReview().updateUi();
        }
    }
}
