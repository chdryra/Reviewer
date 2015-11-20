package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ImageChooser;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;

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
    private FactoryLaunchableUi mLaunchableFactory;
    private FactoryGvData mDataFactory;

    public FactoryEditActions(Context context,
                              ConfigDataUi config,
                              FactoryLaunchableUi launchableFactory,
                              FactoryGvData dataFactory,
                              ImageChooser imageChooser) {
        mContext = context;
        mConfig = config;
        mLaunchableFactory = launchableFactory;
        mDataFactory = dataFactory;
        mFactoriesMap = new HashMap<>();

        addFactory(GvCommentList.GvComment.TYPE,
                new FactoryEditActionsComments(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new GvDataPacker<GvCommentList.GvComment>()));

        addFactory(GvCriterionList.GvCriterion.TYPE,
                new FactoryEditActionsCriteria(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new GvDataPacker<GvCriterionList.GvCriterion>()));

        addFactory(GvFactList.GvFact.TYPE,
                new FactoryEditActionsFacts(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new GvDataPacker<GvFactList.GvFact>()));

        addFactory(GvImageList.GvImage.TYPE,
                new FactoryEditActionsImages(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new GvDataPacker<GvImageList.GvImage>(),
                        imageChooser));

        addFactory(GvLocationList.GvLocation.TYPE,
                new FactoryEditActionsLocations(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new GvDataPacker<GvLocationList.GvLocation>()));

        addFactory(GvTagList.GvTag.TYPE,
                new FactoryEditActionsTags(mContext, mConfig, mLaunchableFactory, mDataFactory,
                        new GvDataPacker<GvTagList.GvTag>()));
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
