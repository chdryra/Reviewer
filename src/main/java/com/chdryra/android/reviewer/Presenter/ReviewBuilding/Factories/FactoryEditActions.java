/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActions {
    private final Map<GvDataType<?>, FactoryEditActionsDefault<?>> mFactoriesMap;
    private final UiConfig mConfig;
    private final FactoryGvData mDataFactory;

    public FactoryEditActions(UiConfig config,
                              FactoryGvData dataFactory,
                              UiLauncher launcher,
                              ImageChooser imageChooser) {
        mConfig = config;
        mDataFactory = dataFactory;
        mFactoriesMap = new HashMap<>();

        addFactory(GvComment.TYPE,
                new FactoryEditActionsComments(mConfig, mDataFactory,
                        new ParcelablePacker<GvComment>()));

        addFactory(GvCriterion.TYPE,
                new FactoryEditActionsCriteria(mConfig, mDataFactory,
                        new ParcelablePacker<GvCriterion>()));

        addFactory(GvFact.TYPE,
                new FactoryEditActionsFacts(mConfig, mDataFactory,
                        new ParcelablePacker<GvFact>()));

        addFactory(GvImage.TYPE,
                new FactoryEditActionsImages(mConfig, mDataFactory,
                        new ParcelablePacker<GvImage>(), launcher, imageChooser));

        addFactory(GvLocation.TYPE,
                new FactoryEditActionsLocations(mConfig, mDataFactory,
                        new ParcelablePacker<GvLocation>()));

        addFactory(GvTag.TYPE,
                new FactoryEditActionsTags(mConfig, mDataFactory,
                        new ParcelablePacker<GvTag>()));
    }

    public <T extends GvDataParcelable> ReviewViewActions<T> newActions(GvDataType<T> dataType) {
        return getFactory(dataType).newActions();
    }

    private <T extends GvDataParcelable> FactoryEditActionsDefault<T> getFactory(GvDataType<T> dataType) {
        //TODO make type safe
        FactoryEditActionsDefault<T> factory = (FactoryEditActionsDefault<T>) mFactoriesMap.get(dataType);
        if(factory == null) {
            factory = new FactoryEditActionsDefault<>(dataType, mConfig, mDataFactory,
                    new ParcelablePacker<T>());
        }

        return factory;
    }

    private <T extends GvDataParcelable> void addFactory(GvDataType<T> dataType, FactoryEditActionsDefault<T> factory) {
        mFactoriesMap.put(dataType, factory);
    }
}
