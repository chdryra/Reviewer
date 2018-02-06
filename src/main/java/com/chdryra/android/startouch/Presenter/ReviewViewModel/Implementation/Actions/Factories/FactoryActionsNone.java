/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ButtonActionNone;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemActionNone;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuActionNone;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.RatingBarActionNone;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.SubjectActionNone;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsNone<T extends GvData> implements FactoryActionsReviewView<T> {
    private GvDataType<T> mDataType;

    public FactoryActionsNone(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    protected GvDataType<T> getDataType() {
        return mDataType;
    }

    @Override
    public MenuAction<T> newMenu() {
        return newDefaultMenu("");
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
    public ButtonAction<T> newBannerButton() {
        return new ButtonActionNone<>();
    }

    @Override
    public GridItemAction<T> newGridItem() {
        return new GridItemActionNone<>();
    }

    @Nullable
    @Override
    public ButtonAction<T> newContextButton() {
        return null;
    }

    MenuAction<T> newDefaultMenu(String title) {
        return new MenuActionNone<>(title);
    }
}
