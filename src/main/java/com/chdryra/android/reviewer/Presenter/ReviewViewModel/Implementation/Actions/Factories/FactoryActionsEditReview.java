/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ConfirmEditsButton;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsEditReview<GC extends GvDataList<? extends GvDataParcelable>> extends FactoryActionsBuild<GC> {
    private final PublishAction mPublishAction;

    public FactoryActionsEditReview(GvDataType<GC> dataType,
                                    UiConfig config,
                                    UiLauncher launcher,
                                    FactoryCommands factoryCommands,
                                    ReviewEditor.EditMode defaultEditMode,
                                    LocationClient locationClient,
                                    PublishAction publishAction) {
        super(dataType, config, launcher, factoryCommands, defaultEditMode, locationClient);
        mPublishAction = publishAction;
    }

    @Nullable
    @Override
    public ButtonAction<GC> newContextButton() {
        return new ConfirmEditsButton<>(mPublishAction);
    }

    @NonNull
    @Override
    protected String getMenuTitle() {
        return Strings.Screens.EDIT;
    }
}
