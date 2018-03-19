/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.DoneEditingButton;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.SubjectEditTags;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.TagAdjuster;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryLaunchCommands;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.startouch.View.Configs.Interfaces.UiConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsEditTags extends FactoryActionsEditData<GvTag> {
    private static final GvDataType<GvTag> TYPE = GvTag.TYPE;
    private final TagAdjuster mTagAdjuster;

    public FactoryActionsEditTags(UiConfig config,
                                  FactoryGvData dataFactory,
                                  FactoryLaunchCommands commandsFactory,
                                  TagAdjuster tagAdjuster) {
        super(TYPE, config, dataFactory, commandsFactory);
        mTagAdjuster = tagAdjuster;
    }

    @Override
    public SubjectAction<GvTag> newSubject() {
        return new SubjectEditTags(mTagAdjuster);
    }

    @Nullable
    @Override
    public ButtonAction<GvTag> newContextButton() {
        return new DoneEditingButton<>(false);
    }
}
