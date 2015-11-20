package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.RatingBarAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.SubjectAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation
        .BannerButtonAdd;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GridItemEdit;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.MenuDataEdit;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation
        .RatingBarEdit;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.SubjectEdit;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsDefault<T extends GvData> {
    private Context mContext;
    private GvDataType<T> mDataType;
    private ConfigDataUi mConfig;
    private FactoryLaunchableUi mLaunchableFactory;
    private FactoryGvData mDataFactory;
    private GvDataPacker<T> mPacker;

    public FactoryEditActionsDefault(Context context,
                                     GvDataType<T> dataType,
                                     ConfigDataUi config,
                                     FactoryLaunchableUi launchableFactory,
                                     FactoryGvData dataFactory,
                                     GvDataPacker<T> packer) {
        mContext = context;
        mDataType = dataType;
        mConfig = config;
        mLaunchableFactory = launchableFactory;
        mDataFactory = dataFactory;
        mPacker = packer;
    }

    public ReviewViewActions<T> newActions() {
        return new ReviewViewActions<>(newSubjectEdit(), newRatingBarEdit(), newBannerButtonAdd(),
                newGridItemEdit(), newMenuEdit());
    }

    protected Context getContext() {
        return mContext;
    }

    protected GvDataType<T> getDataType() {
        return mDataType;
    }

    protected LaunchableConfig<T> getAdderConfig() {
        return mConfig.getAdderConfig(mDataType);
    }

    public ConfigDataUi getConfig() {
        return mConfig;
    }

    protected LaunchableConfig<T> getEditorConfig() {
        return mConfig.getEditorConfig(mDataType);
    }

    protected FactoryGvData getDataFactory() {
        return mDataFactory;
    }

    protected FactoryLaunchableUi getLaunchableFactory() {
        return mLaunchableFactory;
    }

    protected GvDataPacker<T> getPacker() {
        return mPacker;
    }

    protected SubjectAction<T> newSubjectEdit() {
        return new SubjectEdit<>();
    }

    protected RatingBarAction<T> newRatingBarEdit() {
        return new RatingBarEdit<>();
    }

    protected BannerButtonAction<T> newBannerButtonAdd() {
        return new BannerButtonAdd<>(getAdderConfig(), getBannerButtonTitle(), mDataType,
                mDataFactory, mPacker, mLaunchableFactory);
    }

    protected GridItemAction<T> newGridItemEdit() {
        return new GridItemEdit<>(getEditorConfig(), mLaunchableFactory, mPacker);
    }

    protected MenuAction<T> newMenuEdit() {
        return new MenuDataEdit<>(mDataType);
    }

    protected String getBannerButtonTitle() {
        String title = mContext.getResources().getString(R.string.button_add);
        title += " " + mDataType.getDataName();
        return title;
    }
}
