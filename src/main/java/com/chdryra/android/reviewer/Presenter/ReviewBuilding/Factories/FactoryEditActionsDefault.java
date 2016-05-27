/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

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
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MenuDataEdit;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.RatingBarDataEdit;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.SubjectDataEdit;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsDefault<T extends GvData> {
    private Context mContext;
    private GvDataType<T> mDataType;
    private ConfigUi mConfig;
    private UiLauncher mLaunchableFactory;
    private FactoryGvData mDataFactory;
    private ParcelablePacker<T> mPacker;

    public FactoryEditActionsDefault(Context context,
                                     GvDataType<T> dataType,
                                     ConfigUi config,
                                     UiLauncher launchableFactory,
                                     FactoryGvData dataFactory,
                                     ParcelablePacker<T> packer) {
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

    public ConfigUi getConfig() {
        return mConfig;
    }

    protected LaunchableConfig getEditorConfig() {
        return mConfig.getEditorConfig(mDataType.getDatumName());
    }

    protected FactoryGvData getDataFactory() {
        return mDataFactory;
    }

    protected UiLauncher getLaunchableFactory() {
        return mLaunchableFactory;
    }

    protected ParcelablePacker<T> getPacker() {
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
