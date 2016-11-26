/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BannerButtonAddImage;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemEditImage;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsEditImages extends FactoryActionsEditData<GvImage> {
    private static final GvDataType<GvImage> TYPE = GvImage.TYPE;
    private final UiLauncher mLauncher;
    private final ImageChooser mImageChooser;

    public FactoryActionsEditImages(UiConfig config,
                                    FactoryGvData dataFactory,
                                    UiLauncher launcher,
                                    FactoryCommands commandsFactory,
                                    ImageChooser imageChooser) {
        super(TYPE, config, dataFactory, launcher.getReviewLauncher(), commandsFactory);
        mLauncher = launcher;
        mImageChooser = imageChooser;
    }

    @Override
    public BannerButtonAction<GvImage> newBannerButton() {
        return new BannerButtonAddImage(getAdderConfig(), getBannerButtonTitle(),
                getDataFactory().newDataList(TYPE), getPacker(), mLauncher, mImageChooser);
    }

    @Override
    public GridItemAction<GvImage> newGridItem() {
        return new GridItemEditImage(getEditorConfig(), getPacker());
    }

}
