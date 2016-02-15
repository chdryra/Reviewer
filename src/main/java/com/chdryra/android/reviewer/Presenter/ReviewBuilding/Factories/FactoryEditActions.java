/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActions {
    private Map<GvDataType<?>, FactoryEditActionsDefault<?>> mFactoriesMap;
    private Context mContext;
    private ConfigUi mConfig;
    private LaunchableUiLauncher mLaunchableFactory;
    private FactoryGvData mDataFactory;

    public FactoryEditActions(Context context,
                              ConfigUi config,
                              LaunchableUiLauncher launchableFactory,
                              FactoryGvData dataFactory,
                              ImageChooser imageChooser) {
        mContext = context;
        mConfig = config;
        mLaunchableFactory = launchableFactory;
        mDataFactory = dataFactory;
        mFactoriesMap = new HashMap<>();

        addFactory(GvComment.TYPE,
                new FactoryEditActionsComments(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new ParcelablePacker<GvComment>()));

        addFactory(GvCriterion.TYPE,
                new FactoryEditActionsCriteria(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new ParcelablePacker<GvCriterion>()));

        addFactory(GvFact.TYPE,
                new FactoryEditActionsFacts(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new ParcelablePacker<GvFact>()));

        addFactory(GvImage.TYPE,
                new FactoryEditActionsImages(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new ParcelablePacker<GvImage>(),
                        imageChooser));

        addFactory(GvLocation.TYPE,
                new FactoryEditActionsLocations(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new ParcelablePacker<GvLocation>()));

        addFactory(GvTag.TYPE,
                new FactoryEditActionsTags(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new ParcelablePacker<GvTag>()));
    }

    public <T extends GvData> ReviewViewActions<T> newActions(GvDataType<T> dataType) {
        return getFactory(dataType).newActions();
    }

    private <T extends GvData> FactoryEditActionsDefault<T> getFactory(GvDataType<T> dataType) {
        //TODO make type safe
        FactoryEditActionsDefault<T> factory = (FactoryEditActionsDefault<T>) mFactoriesMap.get(dataType);
        if(factory == null) {
            factory = new FactoryEditActionsDefault<>(mContext, dataType, mConfig,
                    mLaunchableFactory, mDataFactory, new ParcelablePacker<T>());
        }

        return factory;
    }

    private <T extends GvData> void addFactory(GvDataType<T> dataType, FactoryEditActionsDefault<T> factory) {
        mFactoriesMap.put(dataType, factory);
    }
}
