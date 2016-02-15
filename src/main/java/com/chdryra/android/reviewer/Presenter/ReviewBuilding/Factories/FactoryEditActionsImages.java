/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BannerButtonAddImage;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemDataEditImage;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsImages extends FactoryEditActionsDefault<GvImage> {
    private static final GvDataType<GvImage> TYPE = GvImage.TYPE;
    private ImageChooser mImageChooser;

    public FactoryEditActionsImages(Context context, ConfigUi config, LaunchableUiLauncher launchableFactory,
                                    FactoryGvData dataFactory,
                                    ParcelablePacker<GvImage> packer,
                                    ImageChooser imageChooser) {
        super(context, TYPE, config, launchableFactory, dataFactory, packer);
        mImageChooser = imageChooser;
    }

    @Override
    protected BannerButtonAction<GvImage> newBannerButtonAdd() {
        return new BannerButtonAddImage(getAdderConfig(), getLaunchableFactory(),
                getBannerButtonTitle(), getDataFactory().newDataList(TYPE), getPacker(),
                mImageChooser);
    }

    @Override
    protected GridItemAction<GvImage> newGridItemEdit() {
        return new GridItemDataEditImage(getEditorConfig(), getLaunchableFactory(), getPacker());
    }

}
