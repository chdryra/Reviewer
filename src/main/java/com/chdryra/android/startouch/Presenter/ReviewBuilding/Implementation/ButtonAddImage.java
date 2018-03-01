/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.content.Intent;
import android.view.View;

import com.chdryra.android.corelibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ButtonAddImage extends ButtonAdd<GvImage>
        implements ImageChooser.ImageChooserListener {
    private final ImageChooser mImageChooser;
    private final UiLauncher mLauncher;

    public ButtonAddImage(LaunchableConfig adderConfig,
                          DataReference<String> title,
                          GvDataList<GvImage> emptyImageList,
                          ParcelablePacker<GvImage> dataPacker,
                          UiLauncher launcher,
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
