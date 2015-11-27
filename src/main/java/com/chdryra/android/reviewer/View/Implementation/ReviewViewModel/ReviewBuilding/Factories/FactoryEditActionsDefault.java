package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.RatingBarAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.SubjectAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation
        .BannerButtonAdd;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GridItemDataEdit;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.MenuDataEdit;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.RatingBarDataEdit;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.SubjectDataEdit;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsDefault<T extends GvData> {
    private Context mContext;
    private GvDataType<T> mDataType;
    private ConfigDataUi mConfig;
    private LaunchableUiLauncher mLaunchableFactory;
    private FactoryGvData mDataFactory;
    private GvDataPacker<T> mPacker;

    public FactoryEditActionsDefault(Context context,
                                     GvDataType<T> dataType,
                                     ConfigDataUi config,
                                     LaunchableUiLauncher launchableFactory,
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

    protected LaunchableConfig getAdderConfig() {
        return mConfig.getAdderConfig(mDataType.getDatumName());
    }

    public ConfigDataUi getConfig() {
        return mConfig;
    }

    protected LaunchableConfig getEditorConfig() {
        return mConfig.getEditorConfig(mDataType.getDatumName());
    }

    protected FactoryGvData getDataFactory() {
        return mDataFactory;
    }

    protected LaunchableUiLauncher getLaunchableFactory() {
        return mLaunchableFactory;
    }

    protected GvDataPacker<T> getPacker() {
        return mPacker;
    }

    protected SubjectAction<T> newSubjectEdit() {
        return new SubjectDataEdit<>();
    }

    protected RatingBarAction<T> newRatingBarEdit() {
        return new RatingBarDataEdit<>();
    }

    protected BannerButtonAction<T> newBannerButtonAdd() {
        return new BannerButtonAdd<>(getAdderConfig(), getBannerButtonTitle(), mDataType,
                mDataFactory, mPacker, mLaunchableFactory);
    }

    protected GridItemAction<T> newGridItemEdit() {
        return new GridItemDataEdit<>(getEditorConfig(), mLaunchableFactory, mPacker);
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
