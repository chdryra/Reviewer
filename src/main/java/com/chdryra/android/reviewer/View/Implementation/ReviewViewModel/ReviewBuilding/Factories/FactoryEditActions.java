package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ImageChooser;
import com.chdryra.android.reviewer.View.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvFact;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTag;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;

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
    private ConfigDataUi mConfig;
    private LaunchableUiLauncher mLaunchableFactory;
    private FactoryGvData mDataFactory;

    public FactoryEditActions(Context context,
                              ConfigDataUi config,
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
                        new GvDataPacker<GvComment>()));

        addFactory(GvCriterion.TYPE,
                new FactoryEditActionsCriteria(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new GvDataPacker<GvCriterion>()));

        addFactory(GvFact.TYPE,
                new FactoryEditActionsFacts(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new GvDataPacker<GvFact>()));

        addFactory(GvImage.TYPE,
                new FactoryEditActionsImages(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new GvDataPacker<GvImage>(),
                        imageChooser));

        addFactory(GvLocation.TYPE,
                new FactoryEditActionsLocations(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new GvDataPacker<GvLocation>()));

        addFactory(GvTag.TYPE,
                new FactoryEditActionsTags(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new GvDataPacker<GvTag>()));
    }

    public <T extends GvData> ReviewViewActions<T> newActions(GvDataType<T> dataType) {
        return getFactory(dataType).newActions();
    }

    private <T extends GvData> FactoryEditActionsDefault<T> getFactory(GvDataType<T> dataType) {
        //TODO make type safe
        FactoryEditActionsDefault<T> factory = (FactoryEditActionsDefault<T>) mFactoriesMap.get(dataType);
        if(factory == null) {
            factory = new FactoryEditActionsDefault<>(mContext, dataType, mConfig,
                    mLaunchableFactory, mDataFactory, new GvDataPacker<T>());
        }

        return factory;
    }

    private <T extends GvData> void addFactory(GvDataType<T> dataType, FactoryEditActionsDefault<T> factory) {
        mFactoriesMap.put(dataType, factory);
    }
}
