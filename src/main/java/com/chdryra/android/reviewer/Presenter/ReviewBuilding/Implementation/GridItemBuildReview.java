/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.reviewer.Application.Interfaces.EditorSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation.AddLocation;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Utils.ParcelablePacker;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemBuildReview<GC extends GvDataList<? extends GvDataParcelable>> extends
        ReviewEditorActionBasic<GC>
        implements GridItemAction<GC>, LocationClient.Locatable, ImageChooser.ImageChooserListener,
        ActivityResultListener {
    private final UiConfig mConfig;
    private final UiLauncher mLauncher;
    private final LocationClient mLocationClient;

    private ImageChooser mImageChooser;
    private LatLng mLatLng;
    private ReviewEditor.EditMode mUiType;

    public GridItemBuildReview(UiConfig config, UiLauncher launcher, ReviewEditor.EditMode
            uiType, LocationClient locationClient) {
        mConfig = config;
        mLauncher = launcher;
        mUiType = uiType;
        mLocationClient = locationClient;
    }

    public void setView(ReviewEditor.EditMode uiType) {
        mUiType = uiType;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(mImageChooser == null) return;

        boolean correctCode = requestCode == getImageRequestCode();
        boolean isOk = ActivityResultCode.OK.equals(resultCode);
        boolean exists = mImageChooser.chosenImageExists(ActivityResultCode.get(resultCode), data);

        if (correctCode && isOk && exists) mImageChooser.getChosenImage(this);
    }

    @Override
    public void onGridItemClick(GC item, int position, View v) {
        executeIntent(item, true);
    }

    @Override
    public void onGridItemLongClick(GC item, int position, View v) {
        executeIntent(item, isQuickReview());
    }

    @Override
    public void onChosenImage(GvImage image) {
        getEditor().setCover(image);
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        mLocationClient.connect(this);
    }

    @Override
    public void onDetachReviewView() {
        super.onDetachReviewView();
        mLocationClient.disconnect();
    }

    @Override
    public void onLocated(Location location, CallbackMessage message) {
        if (message.isOk()) {
            mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        }
    }

    @Override
    public void onConnected(Location location, CallbackMessage message) {
        onLocated(location, message);
    }

    private boolean isQuickReview() {
        return mUiType == ReviewEditor.EditMode.QUICK;
    }

    private int getImageRequestCode() {
        return mConfig.getAdder(GvImage.TYPE.getDatumName()).getDefaultRequestCode();
    }

    private void executeIntent(GvDataList<? extends GvDataParcelable> gridCell, boolean
            quickDialog) {
        GvDataType<? extends GvDataParcelable> type = gridCell.getGvDataType();
        if (quickDialog && !gridCell.hasData()) {
            launchQuickSetAdder(type);
        } else if (isQuickReview(type) && gridCell.size() == 1) {
            launchQuickSetEditor(gridCell.get(0));
        } else {
            mLauncher.launchEditDataUi(type);
        }
    }

    private boolean isQuickReview(GvDataType<?> dataType) {
        for(GvDataType<?> non : EditorSuite.NON_QUICK) {
            if(dataType.equals(non)) return false;
        }
        return isQuickReview();
    }

    private <T extends GvDataParcelable> void launchQuickSetEditor(T datum) {
        showQuickSetLaunchable(mConfig.getEditor(datum.getGvDataType().getDatumName()), datum,
                false);
    }

    private void launchQuickSetAdder(GvDataType<? extends GvData> type) {
        if (type.equals(GvImage.TYPE)) {
            if (mImageChooser == null) mImageChooser = getEditor().newImageChooser();
            mLauncher.launchImageChooser(mImageChooser, getImageRequestCode());
        } else {
            showQuickSetLaunchable(mConfig.getAdder(type.getDatumName()), null, type.equals
                    (GvLocation.TYPE));
        }
    }

    private <T extends GvDataParcelable> void showQuickSetLaunchable(LaunchableConfig config,
                                                                     @Nullable T datum, boolean
                                                                             packLatLng) {
        Bundle args = packData(datum, packLatLng);
        config.launch(new UiLauncherArgs(config.getDefaultRequestCode()).setBundle(args));
    }

    @NonNull
    private <T extends GvDataParcelable> Bundle packData(@Nullable T datum, boolean packLatLng) {
        Bundle args = new Bundle();
        args.putBoolean(EditorSuite.QUICK_ADD, true);

        if (isQuickReview()) args.putBoolean(EditorSuite.QUICK_REVIEW, true);
        if (datum != null) {
            ParcelablePacker<T> packer = new ParcelablePacker<>();
            packer.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, datum, args);
        }
        if (packLatLng) packLatLng(args);

        return args;
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
