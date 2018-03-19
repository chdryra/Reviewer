/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.TagAdjuster;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ReviewViewActions;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryLaunchCommands;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.startouch.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActions {
    private final Map<GvDataType<?>, FactoryActionsEditData<?>> mFactoriesMap;
    private final UiConfig mConfig;
    private final FactoryGvData mDataFactory;
    private final FactoryLaunchCommands mCommandsFactory;

    public FactoryEditActions(UiConfig config,
                              FactoryGvData dataFactory,
                              UiLauncher launcher,
                              FactoryLaunchCommands commandsFactory,
                              ImageChooser imageChooser) {
        mConfig = config;
        mDataFactory = dataFactory;
        mCommandsFactory = commandsFactory;
        mFactoriesMap = new HashMap<>();

        addFactory(GvComment.TYPE, new FactoryActionsCommentsEdit(mConfig, mDataFactory,
                mCommandsFactory));

        addFactory(GvCriterion.TYPE, new FactoryActionsCriteriaEdit(mConfig, mDataFactory,
                mCommandsFactory));

        addFactory(GvFact.TYPE, new FactoryActionsFactsEdit(mConfig, mDataFactory,
                mCommandsFactory));

        addFactory(GvLocation.TYPE, new FactoryActionsEditLocations(mConfig, mDataFactory,
                mCommandsFactory));

        addFactory(GvImage.TYPE,
                new FactoryActionsEditImages(mConfig, mDataFactory, launcher, mCommandsFactory,
                        imageChooser));

        addFactory(GvTag.TYPE,
                new FactoryActionsEditTags(mConfig, mDataFactory, mCommandsFactory, new
                        TagAdjuster()));
    }

    public <T extends GvDataParcelable> ReviewViewActions<T> newActions(GvDataType<T> dataType) {
        return new ReviewViewActions<>(getFactory(dataType));
    }

    private <T extends GvDataParcelable> FactoryActionsEditData<T> getFactory(GvDataType<T>
                                                                                      dataType) {
        //TODO make type safe
        FactoryActionsEditData<T> factory = (FactoryActionsEditData<T>) mFactoriesMap.get(dataType);
        if (factory == null) {
            factory = new FactoryActionsEditData<>(dataType, mConfig, mDataFactory,
                    mCommandsFactory);
        }

        return factory;
    }

    private <T extends GvDataParcelable> void addFactory(GvDataType<T> dataType,
                                                         FactoryActionsEditData<T> factory) {
        mFactoriesMap.put(dataType, factory);
    }
}
