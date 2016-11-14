/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ReviewCommentsEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ReviewDataEditorDefault;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewDataEditor {
    private final UiConfig mConfig;
    private final FactoryGvData mDataFactory;
    private final FactoryCommands mCommandsFactory;
    private final FactoryReviewViewParams mParamsFactory;

    public FactoryReviewDataEditor(UiConfig config,
                                   FactoryGvData dataFactory,
                                   FactoryCommands commandsFactory,
                                   FactoryReviewViewParams paramsFactory) {
        mConfig = config;
        mDataFactory = dataFactory;
        mCommandsFactory = commandsFactory;
        mParamsFactory = paramsFactory;
    }

    public <T extends GvDataParcelable> ReviewDataEditor<T> newEditor(DataBuilderAdapter<T> adapter,
                                                                      UiLauncher launcher,
                                                                      ImageChooser imageChooser) {
        GvDataType<T> type = adapter.getGvDataType();

        FactoryEditActions actionsFactory
                = new FactoryEditActions(mConfig, mDataFactory, launcher, mCommandsFactory, imageChooser);

        ReviewViewActions<T> actions = actionsFactory.newActions(type);
        ReviewViewParams params = mParamsFactory.newEditorParams(type);

        //TODO make type safe
        ReviewDataEditor editor;
        if(type.equals(GvComment.TYPE)) {
            editor = new ReviewCommentsEditor((DataBuilderAdapter<GvComment>) adapter,
                    (ReviewViewActions<GvComment>) actions, params);
        } else {
            editor = new ReviewDataEditorDefault<>(adapter, actions, params);
        }

        //TODO make type safe
        return (ReviewDataEditor<T>) editor;
    }
}
