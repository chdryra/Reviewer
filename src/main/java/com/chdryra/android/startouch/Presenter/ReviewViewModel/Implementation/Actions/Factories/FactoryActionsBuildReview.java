/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.LocationUtils.LocationClient;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.BuildScreenShare;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.BuildScreenEditMode;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.BuildScreenDataEdit;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.MaiAverageRating;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.MaiPreviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.MaiUpEditor;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.BuildScreenMenu;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.BuildScreenRatingEdit;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.BuildScreenSubjectEdit;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryLaunchCommands;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.View.Configs.Interfaces.UiConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsBuildReview<GC extends GvDataList<? extends GvDataParcelable>> extends FactoryActionsNone<GC> {
    private final UiConfig mConfig;
    private final FactoryLaunchCommands mFactoryLaunchCommands;
    private final LocationClient mLocationClient;
    private final ReviewEditor.EditMode mDefaultEditMode;

    public FactoryActionsBuildReview(GvDataType<GC> dataType,
                                     UiConfig config,
                                     FactoryLaunchCommands factoryLaunchCommands,
                                     ReviewEditor.EditMode defaultEditMode,
                                     LocationClient locationClient) {
        super(dataType);
        mConfig = config;
        mFactoryLaunchCommands = factoryLaunchCommands;
        mLocationClient = locationClient;
        mDefaultEditMode = defaultEditMode;
    }

    @Override
    public SubjectAction<GC> newSubject() {
        return new BuildScreenSubjectEdit<>();
    }

    @Override
    public RatingBarAction<GC> newRatingBar() {
        return new BuildScreenRatingEdit<>();
    }

    @Override
    public ButtonAction<GC> newBannerButton() {
        return new BuildScreenEditMode<>(mDefaultEditMode);
    }

    @Override
    public GridItemAction<GC> newGridItem() {
        return new BuildScreenDataEdit<>(mConfig, mConfig.getUiLauncher(), mDefaultEditMode, mLocationClient);
    }

    @Override
    public MenuAction<GC> newMenu() {
        LaunchBespokeViewCommand command = mFactoryLaunchCommands.newLaunchPagedCommand(null);
        return new BuildScreenMenu<>(getMenuTitle(), new MaiUpEditor<GC>(),
                new MaiPreviewEditor<GC>(command), new MaiAverageRating<GC>());
    }

    @NonNull
    protected String getMenuTitle() {
        return Strings.Screens.CREATE;
    }

    @Nullable
    @Override
    public ButtonAction<GC> newContextButton() {
        return new BuildScreenShare<>(mConfig.getPublish());
    }
}
