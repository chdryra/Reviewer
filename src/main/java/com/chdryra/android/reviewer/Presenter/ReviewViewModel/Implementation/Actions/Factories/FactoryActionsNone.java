/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.RatingBarActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsNone<T extends GvData> implements FactoryReviewViewActions<T> {
    private GvDataType<T> mDataType;
    private FactoryCommands mCommandsFactory;

    public FactoryActionsNone(GvDataType<T> dataType) {
        mDataType = dataType;
        mCommandsFactory = new FactoryCommands();
    }

    protected GvDataType<T> getDataType() {
        return mDataType;
    }

    @Override
    public MenuAction<T> newMenu() {
        return newMenu(null);
    }

    @Override
    public SubjectAction<T> newSubject() {
        return new SubjectActionNone<>();
    }

    @Override
    public RatingBarAction<T> newRatingBar() {
        return new RatingBarActionNone<>();
    }

    @Override
    public BannerButtonAction<T> newBannerButton() {
        return new BannerButtonActionNone<>();
    }

    @Override
    public GridItemAction<T> newGridItem() {
        return new GridItemActionNone<>();
    }

    @Nullable
    @Override
    public ContextualButtonAction<T> newContextButton() {
        return null;
    }

    MenuAction<T> newMenu(@Nullable String title) {
        return new MenuActionNone<>(title);
    }

    LaunchOptionsCommand newOptionsCommand(LaunchableConfig optionsUi) {
        return mCommandsFactory.newLaunchOptionsCommand(optionsUi);
    }
}
