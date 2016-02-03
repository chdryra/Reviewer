/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.content.Intent;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BannerButtonAddImage extends BannerButtonAdd<GvImage>
        implements ImageChooser.ImageChooserListener {
    private static final String LAUNCH_TAG = "ImageChooser";
    private ImageChooser mImageChooser;

    public BannerButtonAddImage(LaunchableConfig adderConfig,
                                LaunchableUiLauncher launchableFactory, String title,
                                GvDataList<GvImage> emptyImageList,
                                GvDataPacker<GvImage> dataPacker,
                                ImageChooser imageChooser) {
        super(adderConfig, launchableFactory, title, emptyImageList, dataPacker);
        mImageChooser = imageChooser;
    }

    @Override
    public void onClick(View v) {
        setLaunchableRequestCode(LAUNCH_TAG);
        getActivity().startActivityForResult(mImageChooser.getChooserIntents(),
                getLaunchableRequestCode());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean correctCode = requestCode == getLaunchableRequestCode();
        boolean isOk = ActivityResultCode.OK.equals(resultCode);
        boolean exists = mImageChooser.chosenImageExists(ActivityResultCode.get(resultCode), data);

        if (correctCode && isOk && exists) mImageChooser.getChosenImage(this);
    }

    @Override
    public void onChosenImage(GvImage image) {
        if (getGridData().size() == 0) image.setIsCover(true);
        if (addData(image) && getGridData().size() == 1) setCover();
    }

    private void setCover() {
        GvImageList images = (GvImageList) getGridData();
        GvImage cover = images.getItem(0);
        cover.setIsCover(true);
        getReviewView().notifyObservers();
    }
}
