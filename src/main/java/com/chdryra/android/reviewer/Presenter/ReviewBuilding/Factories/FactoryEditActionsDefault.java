/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BannerButtonAdd;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemEdit;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MenuEdit;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.RatingEdit;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.SubjectEdit;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
class FactoryEditActionsDefault<T extends GvDataParcelable> {
    private final GvDataType<T> mDataType;
    private final ConfigUi mConfig;
    private final FactoryGvData mDataFactory;
    private final ParcelablePacker<T> mPacker;

    public FactoryEditActionsDefault(GvDataType<T> dataType,
                                     ConfigUi config,
                                     FactoryGvData dataFactory,
                                     ParcelablePacker<T> packer) {
        mDataType = dataType;
        mConfig = config;
        mDataFactory = dataFactory;
        mPacker = packer;
    }

    public ReviewViewActions<T> newActions() {
        return new ReviewViewActions<>(newSubjectEdit(), newRatingBarEdit(), newBannerButtonAdd(),
                newGridItemEdit(), newMenuEdit());
    }

    protected GvDataType<T> getDataType() {
        return mDataType;
    }

    LaunchableConfig getAdderConfig() {
        return mConfig.getAdder(mDataType.getDatumName());
    }

    ConfigUi getConfig() {
        return mConfig;
    }

    LaunchableConfig getEditorConfig() {
        return mConfig.getEditor(mDataType.getDatumName());
    }

    FactoryGvData getDataFactory() {
        return mDataFactory;
    }

    ParcelablePacker<T> getPacker() {
        return mPacker;
    }

    SubjectAction<T> newSubjectEdit() {
        return new SubjectEdit<>();
    }

    private RatingBarAction<T> newRatingBarEdit() {
        return new RatingEdit<>();
    }

    BannerButtonAction<T> newBannerButtonAdd() {
        return new BannerButtonAdd<>(getAdderConfig(), getBannerButtonTitle(),
                mDataFactory.newDataList(mDataType), mPacker);
    }

    GridItemAction<T> newGridItemEdit() {
        return new GridItemEdit<>(getEditorConfig(), mPacker);
    }

    MenuAction<T> newMenuEdit() {
        return new MenuEdit<>(mDataType);
    }

    String getBannerButtonTitle() {
        String title = Strings.Buttons.ADD;
        title += " " + mDataType.getDataName();
        return title;
    }
}
