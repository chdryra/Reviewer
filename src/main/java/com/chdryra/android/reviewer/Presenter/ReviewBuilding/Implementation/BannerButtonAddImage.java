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

import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Utils.ParcelablePacker;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BannerButtonAddImage extends BannerButtonAdd<GvImage>
        implements ImageChooser.ImageChooserListener {
    private final ImageChooser mImageChooser;
    private final UiLauncher mLauncher;

    public BannerButtonAddImage(LaunchableConfig adderConfig, String title, GvDataList<GvImage> emptyImageList, ParcelablePacker<GvImage> dataPacker, UiLauncher launcher,

                                ImageChooser imageChooser) {
        super(adderConfig, title, emptyImageList, dataPacker);
        mLauncher = launcher;
        mImageChooser = imageChooser;
        setLaunchableRequestCode(adderConfig.getDefaultRequestCode());
    }

    @Override
    public void onClick(View v) {
        mLauncher.launchImageChooser(mImageChooser, getLaunchableRequestCode());
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
        if (getGridData().size() == 0) {
            image.setIsCover(true);
            if (addData(image)) getReviewView().updateCover();
        } else {
            addData(image);
        }
    }
}
