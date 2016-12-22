/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BannerButtonReviewBuild;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenShareButton;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemBuildReview;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MaiAverageRating;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MaiPreviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MaiUpEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MenuReviewBuild;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.RatingEditBuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.SubjectEditBuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsBuild<GC extends GvDataList<? extends GvDataParcelable>> extends FactoryActionsNone<GC> {
    private final UiConfig mConfig;
    private final UiLauncher mLauncher;
    private final FactoryCommands mFactoryCommands;
    private final LocationClient mLocationClient;
    private final ReviewEditor.EditMode mDefaultEditMode;

    public FactoryActionsBuild(GvDataType<GC> dataType,
                               UiConfig config,
                               UiLauncher launcher,
                               FactoryCommands factoryCommands,
                               ReviewEditor.EditMode defaultEditMode,
                               LocationClient locationClient) {
        super(dataType);
        mConfig = config;
        mLauncher = launcher;
        mFactoryCommands = factoryCommands;
        mLocationClient = locationClient;
        mDefaultEditMode = defaultEditMode;
    }

    @Override
    public SubjectAction<GC> newSubject() {
        return new SubjectEditBuildScreen<>();
    }

    @Override
    public RatingBarAction<GC> newRatingBar() {
        return new RatingEditBuildScreen<>();
    }

    @Override
    public BannerButtonAction<GC> newBannerButton() {
        return new BannerButtonReviewBuild<>(mDefaultEditMode);
    }

    @Override
    public GridItemAction<GC> newGridItem() {
        return new GridItemBuildReview<>(mConfig, mLauncher, mDefaultEditMode, mLocationClient);
    }

    @Override
    public MenuAction<GC> newMenu() {
        LaunchBespokeViewCommand command = mFactoryCommands.newLaunchFormattedCommand(null);
        return new MenuReviewBuild<>(new MaiUpEditor<GC>(),
                new MaiPreviewEditor<GC>(command), new MaiAverageRating<GC>());
    }

    @Nullable
    @Override
    public ContextualButtonAction<GC> newContextButton() {
        return new BuildScreenShareButton<>(mConfig.getPublish());
    }
}
