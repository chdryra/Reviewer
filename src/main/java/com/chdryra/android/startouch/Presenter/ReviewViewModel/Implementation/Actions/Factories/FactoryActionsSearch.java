/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemLaunchAuthor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemLauncher;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuActionNone;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.SubjectFilter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsSearch<T extends GvData> extends FactoryActionsNone<T> {
    private final UiLauncher mLauncher;
    private final FactoryReviewView mFactory;

    public FactoryActionsSearch(GvDataType<T> dataType, UiLauncher launcher, FactoryReviewView
            factory) {
        super(dataType);
        mLauncher = launcher;
        mFactory = factory;
    }

    FactoryReviewView getReviewViewFactory() {
        return mFactory;
    }

    UiLauncher getLauncher() {
        return mLauncher;
    }

    @Override
    public SubjectAction<T> newSubject() {
        return newSubjectBannerFilter();
    }

    public SubjectFilter<T> newSubjectBannerFilter() {
        return new SubjectFilter<>(Strings.Buttons.AUTHORS, Strings.Progress.SEARCHING);
    }

    @Override
    public ButtonAction<T> newBannerButton() {
        return newSubjectBannerFilter();
    }

    @Override
    public GridItemAction<T> newGridItem() {
        return new GridItemLauncher<>(mLauncher, mFactory);
    }

    @Override
    public MenuAction<T> newMenu() {
        return new MenuActionNone<>(Strings.Screens.SEARCH);
    }

    public static class Author extends FactoryActionsSearch<GvAuthor> {
        public Author(UiLauncher launcher, FactoryReviewView factory) {
            super(GvAuthor.TYPE, launcher, factory);
        }

        @Override
        public GridItemAction<GvAuthor> newGridItem() {
            return new GridItemLaunchAuthor(getLauncher(), getReviewViewFactory());
        }
    }
}