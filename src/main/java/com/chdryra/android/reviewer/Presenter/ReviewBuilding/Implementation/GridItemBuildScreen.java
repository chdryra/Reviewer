/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation.AddLocation;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.AdderConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemBuildScreen<GC extends GvDataList<? extends GvDataParcelable>> extends ReviewEditorActionBasic<GC>
        implements GridItemAction<GC>, LocationClient.Locatable {
    private LocationClient mLocationClient;
    private ImageChooser mImageChooser;
    private LatLng mLatLng;

    @Override
    public void onGridItemClick(GC item, int position, View v) {
        executeIntent(item, true);
    }

    @Override
    public void onGridItemLongClick(GC item, int position, View v) {
        executeIntent(item, false);
    }

    @Override
    public void onAttachReviewView() {
        if(mImageChooser == null) mImageChooser = getEditor().getImageChooser();
        if(mLocationClient == null) mLocationClient = getApp().newLocationClient(this);
        mLocationClient.connect();
    }

    @Override
    public void onDetachReviewView() {
        mLocationClient.disconnect();
    }

    @Override
    public void onLocated(Location location) {
        mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onConnected(Location location) {
        onLocated(location);
    }

    private void executeIntent(GvDataList<? extends GvDataParcelable> gridCell, boolean quickDialog) {
        GvDataType<? extends GvDataParcelable> type = gridCell.getGvDataType();
        if (quickDialog && !gridCell.hasData()) {
            launchQuickSetAdder(type);
        } else {
            getApp().launchEditScreen(type);
        }
    }

    private void launchQuickSetAdder(GvDataType<? extends GvData> type) {
        if (type.equals(GvImage.TYPE)) {
            getApp().launchImageChooser(mImageChooser, getImageRequestCode());
        } else {
            showQuickSetLaunchable(getAdderConfig(type));
        }
    }

    private int getImageRequestCode() {
        return getAdderConfig(GvImage.TYPE).getRequestCode();
    }

    private <T extends GvData> LaunchableConfig getAdderConfig(GvDataType<T> dataType) {
        return getApp().getConfigUi().getAdder(dataType.getDatumName());
    }

    private void showQuickSetLaunchable(LaunchableConfig adderConfig) {
        Bundle args = new Bundle();
        args.putBoolean(AdderConfig.QUICK_SET, true);
        packLatLng(args);
        getApp().getUiLauncher().launch(adderConfig, RequestCodeGenerator.getCode(adderConfig.getTag
                ()), args);
    }

    private void packLatLng(Bundle args) {
        LatLng latLng = mLatLng;
        boolean fromImage = false;

        GvImage cover = getEditor().getCover();
        LatLng coverLatLng = cover.getLatLng();
        if (coverLatLng != null) {
            latLng = coverLatLng;
            fromImage = true;
        }

        args.putParcelable(AddLocation.LATLNG, latLng);
        args.putBoolean(AddLocation.FROM_IMAGE, fromImage);
    }
}
