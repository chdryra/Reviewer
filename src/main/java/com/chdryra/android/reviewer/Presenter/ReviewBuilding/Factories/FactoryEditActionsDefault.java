package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BannerButtonAdd;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemDataEdit;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MenuDataEdit;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.RatingBarDataEdit;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.SubjectDataEdit;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Configs.ConfigDataUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

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
        return new BannerButtonAdd<>(getAdderConfig(), mLaunchableFactory, getBannerButtonTitle(),
                mDataFactory.newDataList(mDataType), mPacker);
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
