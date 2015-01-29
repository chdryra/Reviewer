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
public class GridItemBuildUi extends GridItemEdit {
    private final BuildListener mListener;
    private       LatLng        mLatLng;
    private       ImageChooser  mImageChooser;

    public GridItemBuildUi(ControllerReviewEditable controller) {
        super(controller, GvDataList.GvType.REVIEWS);
        mListener = new BuildListener() {
        };
    }

    @Override
    public void onSetReviewView() {
        super.onSetReviewView();
        mImageChooser = Administrator.getImageChooser(getActivity());
        LocationClientConnector client = new LocationClientConnector(getActivity(), mListener);
        client.connect();
    }

    @Override
    protected Fragment getNewListener() {
        return mListener;
    }

    @Override
    public void onGridItemClick(GvDataList.GvData item, View v) {
        executeIntent((GvBuildUiList.GvBuildUi) item, true);
    }

    @Override
    public void onGridItemLongClick(GvDataList.GvData item, View v) {
        executeIntent((GvBuildUiList.GvBuildUi) item, false);
    }

    private void executeIntent(GvBuildUiList.GvBuildUi gridCell, boolean quickDialog) {
        if (quickDialog && getController().getData(gridCell.getGvType()).size() == 0) {
            showQuickDialog(gridCell.getConfig());
        } else {
            startActivity(gridCell.getConfig());
        }
    }

    private ControllerReviewEditable getEditableController() {
        return (ControllerReviewEditable) getController();
    }

    private void addLocation(GvLocationList.GvLocation location) {
        if (location.isValidForDisplay()) {
            GvLocationList list = new GvLocationList();
            list.add(location);
            getEditableController().setData(list);
        }
    }

    private void startMapActivity(GvLocationList.GvLocation location) {
        Bundle args = new Bundle();
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, location, args);

        LaunchableUi mapUi = ConfigGvDataUi.getLaunchable(ActivityEditLocationMap.class);
        LauncherUi.launch(mapUi, getListener(), getLocationMRequestCode(), null, args);
    }

    private void startActivity(ConfigGvDataUi.Config config) {
        GvDataList.GvType dataType = config.getGvType();
        boolean isEdit = !(dataType == GvDataList.GvType.SOCIAL);

        Intent i = new Intent(getActivity(), ActivityViewReview.class);
        ActivityViewReview.packParameters(dataType, isEdit, i);
        Administrator.get(getActivity()).pack(getController(), i);
        getListener().startActivity(i);
    }

    private void showQuickDialog(ConfigGvDataUi.Config config) {
        if (config.getGvType() == GvDataList.GvType.IMAGES) {
            getListener().startActivityForResult(mImageChooser.getChooserIntents(),
                    getImageRequestCode());
        }

        Bundle args = Administrator.get(getActivity()).pack(getController());
        args.putBoolean(DialogFragmentGvDataAdd.QUICK_SET, true);

        ConfigGvDataUi.LaunchableConfig adderConfig = config.getAdderConfig();

        LaunchableUi ui;
        if (adderConfig.getGVType() == GvDataList.GvType.LOCATIONS) {
            ui = ConfigGvDataUi.getLaunchable(DialogFragmentLocation.class);
            packLatLng(args);
        } else {
            ui = adderConfig.getLaunchable();
        }

        LauncherUi.launch(ui, getListener(), adderConfig.getRequestCode(), adderConfig.getTag(),
                args);
    }

    private void packLatLng(Bundle args) {
        ControllerReview controller = getController();
        LatLng latLng = mLatLng;
        boolean fromImage = false;
        if (controller.hasData(GvDataList.GvType.IMAGES)) {
            GvImageList images = (GvImageList) controller.getData(GvDataList.GvType.IMAGES);
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
            getEditableController().setData(images);
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
