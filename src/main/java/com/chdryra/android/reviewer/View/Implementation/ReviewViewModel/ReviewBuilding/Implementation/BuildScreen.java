/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.LocationClientConnector;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataList;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Implementation.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation.AddLocation;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Activities.ActivityEditData;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.View.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreen implements
        ImageChooser.ImageChooserListener,
        LocationClientConnector.Locatable,
        ReviewViewActions.ReviewViewAttachedObserver,
        GridItemClickObserved.ClickObserver {
    private ReviewEditor mEditor;
    private final ConfigDataUi mUiConfig;
    private final LaunchableUiLauncher mLaunchableFactory;

    private LocationClientConnector mLocationClient;
    private ImageChooser mImageChooser;
    private LatLng mLatLng;

//Constructors
    public BuildScreen(ReviewEditor<?> editor,
                       ConfigDataUi uiConfig,
                       LaunchableUiLauncher launchableFactory) {
        mEditor = editor;
        mUiConfig = uiConfig;
        mLaunchableFactory = launchableFactory;

        setGridItemObservation();
    }

    private void setGridItemObservation() {
        ReviewViewActions<?> actions = mEditor.getActions();
        actions.registerObserver(this);
        GridItemClickObserved<?> gridItem = (GridItemClickObserved<?>) actions.getGridItemAction();
        gridItem.registerObserver(this);
    }

    //public methods
    public ReviewEditor getEditor() {
        return mEditor;
    }

    public Activity getActivity() {
        return mEditor.getActivity();
    }

    @Override
    public void onGridItemClick(GvData item, int position, View v) {
        executeIntent((GvDataList<? extends GvData>) item, true);
    }

    @Override
    public void onGridItemLongClick(GvData item, int position, View v) {
        executeIntent((GvDataList<? extends GvData>) item, false);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ActivityResultCode result = ActivityResultCode.get(resultCode);
        boolean imageRequested = requestCode == getImageRequestCode();
        if (imageRequested && mImageChooser.chosenImageExists(result, data)) {
            mImageChooser.getChosenImage(this);
        }

        updateScreen();
    }

    //private methods
    private <T extends GvData> LaunchableConfig getAdderConfig(GvDataType<T> dataType) {
        return mUiConfig.getAdderConfig(dataType.getDatumName());
    }

    private int getImageRequestCode() {
        return getAdderConfig(GvImage.TYPE).getRequestCode();
    }

    private void setCover(GvImage image) {
        mEditor.setCover(image);
    }

    private void updateScreen() {
        mEditor.notifyBuilder();
    }

    private void setLatLng(LatLng latLng) {
        mLatLng = latLng;
    }

    //Overridden
    @Override
    public void onLocated(Location location) {
        setLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onLocationClientConnected(Location location) {
        onLocated(location);
    }

    @Override
    public void onChosenImage(GvImage image) {
        setCover(image);
    }

    public void executeIntent(GvDataList<? extends GvData> gridCell, boolean quickDialog) {
        GvDataType<? extends GvData> type = gridCell.getGvDataType();
        if (quickDialog && !gridCell.hasData()) {
            launchAdder(type);
        } else {
            ActivityEditData.start(getActivity(), type);
        }
    }

    private void launchAdder(GvDataType<? extends GvData> type) {
        if (type.equals(GvImage.TYPE)) {
            launchImageChooser();
        } else {
            showQuickDialog(getAdderConfig(type));
        }
    }

    private void launchImageChooser() {
        getActivity().startActivityForResult(mImageChooser.getChooserIntents(),
                getImageRequestCode());
    }

    private void showQuickDialog(LaunchableConfig adderConfig) {
        Bundle args = new Bundle();
        args.putBoolean(DialogGvDataAdd.QUICK_SET, true);
        packLatLng(args);
        mLaunchableFactory.launch(adderConfig, getActivity(), args);
    }

    private void packLatLng(Bundle args) {
        LatLng latLng = mLatLng;
        boolean fromImage = false;

        GvImage cover = mEditor.getCover();
        LatLng coverLatLng = cover.getLatLng();
        if (coverLatLng != null ) {
            latLng = coverLatLng;
            fromImage = true;
        }

        args.putParcelable(AddLocation.LATLNG, latLng);
        args.putBoolean(AddLocation.FROM_IMAGE, fromImage);
    }

    @Override
    public <T extends GvData> void onReviewViewAttached(ReviewView<T> reviewView) {
        mImageChooser = mEditor.getImageChooser();
        mLocationClient = new LocationClientConnector(getActivity(), BuildScreen.this);
        mLocationClient.connect();
    }
}
