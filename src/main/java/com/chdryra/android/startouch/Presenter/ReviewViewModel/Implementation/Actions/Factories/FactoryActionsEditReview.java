/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.FinishEditsButton;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.View.Configs.Interfaces.UiConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsEditReview<GC extends GvDataList<? extends GvDataParcelable>> extends FactoryActionsBuildReview<GC> {
    private final PublishAction mPublishAction;

    public FactoryActionsEditReview(GvDataType<GC> dataType,
                                    UiConfig config,
                                    FactoryCommands factoryCommands,
                                    ReviewEditor.EditMode defaultEditMode,
                                    LocationClient locationClient,
                                    PublishAction publishAction) {
        super(dataType, config, factoryCommands, defaultEditMode, locationClient);
        mPublishAction = publishAction;
    }

    @Nullable
    @Override
    public ButtonAction<GC> newContextButton() {
        return new FinishEditsButton<>(mPublishAction);
    }

    @NonNull
    @Override
    protected String getMenuTitle() {
        return Strings.Screens.EDIT;
    }
}
