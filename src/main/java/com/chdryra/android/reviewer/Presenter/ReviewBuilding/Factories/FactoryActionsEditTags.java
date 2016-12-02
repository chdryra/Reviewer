/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.SubjectEditTags;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.TagAdjuster;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;

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
                                  FactoryCommands commandsFactory,
                                  TagAdjuster tagAdjuster) {
        super(TYPE, config, dataFactory, commandsFactory);
        mTagAdjuster = tagAdjuster;
    }

    @Override
    public SubjectAction<GvTag> newSubject() {
        return new SubjectEditTags(mTagAdjuster);
    }
}
