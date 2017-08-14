/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Comparators.ComparatorCollection;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuOptionsItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ActionsParameters;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ButtonAuthorReviews;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ButtonSelectorWithDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ButtonSorter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemConfigLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuViewDataDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.CommandList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsSelectAndExecute;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsSelector;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.ReviewOptionsSelector;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewData<T extends GvData> extends FactoryActionsNone<T> {
    private final FactoryReviewView mFactoryView;
    private final FactoryCommands mFactoryCommands;
    private final UiLauncher mLauncher;
    private final ReviewStamp mStamp;
    private final AuthorsRepository mRepo;
    private final ComparatorCollection<? super T> mComparators;

    private LaunchableConfig mGridItemConfig;
    private CommandList mContextCommands;

    public FactoryActionsViewData(ActionsParameters<T> parameters) {
        super(parameters.getDataType());
        mFactoryView = parameters.getFactoryView();
        mFactoryCommands = parameters.getFactoryCommands();
        mLauncher = parameters.getLauncher();
        mStamp = parameters.getStamp();
        mRepo = parameters.getRepo();
        mContextCommands = parameters.getContextCommands();
        mComparators = parameters.getComparators();
        mGridItemConfig = parameters.getGridItemConfig();
    }

    protected UiLauncher getLauncher() {
        return mLauncher;
    }

    protected OptionsSelector newSelector() {
        return getCommandsFactory().newOptionsSelector();
    }

    @Override
    public MenuAction<T> newMenu() {
        return new MenuViewDataDefault<>(getDataType().getDataName(), newOptionsMenuItem());
    }

    @Override
    public RatingBarAction<T> newRatingBar() {
        return new RatingBarExpandGrid<>(mLauncher, mFactoryView);
    }

    @Override
    public ButtonAction<T> newBannerButton() {
        if (hasStamp()) {
            return new ButtonAuthorReviews<>(mLauncher.getReviewLauncher(), mStamp, mRepo);
        } else if(mComparators != null){
            return new ButtonSorter<>(newSelector(), mComparators);
        } else {
            return super.newBannerButton();
        }
    }

    @Nullable
    @Override
    public ButtonAction<T> newContextButton() {
        return mContextCommands != null ? new ButtonSelectorWithDefault<T>(newSelector(),
                mContextCommands, mContextCommands.getListName()) : super.newContextButton();
    }

    @Override
    public GridItemAction<T> newGridItem() {
        return mGridItemConfig != null ?
                new GridItemConfigLauncher<T>(mLauncher, mFactoryView, mGridItemConfig) :
                new GridItemLauncher<T>(getLauncher(), getViewFactory());
    }

    FactoryReviewView getViewFactory() {
        return mFactoryView;
    }

    FactoryCommands getCommandsFactory() {
        return mFactoryCommands;
    }

    LaunchableConfig getGridItemConfig() {
        return mGridItemConfig;
    }

    @NonNull
    MenuOptionsItem<T> newOptionsMenuItem() {
        OptionsSelectAndExecute command;
        if(hasStamp()) {
            command = mFactoryCommands.newReviewOptionsSelector(ReviewOptionsSelector.OptionsType.ALL, mStamp.getDataAuthorId());
        } else {
            command = mFactoryCommands.newReviewOptionsSelector(ReviewOptionsSelector.OptionsType.ALL);
        }
        return new MaiOptionsCommand<>(command);
    }

    boolean hasStamp() {
        return mStamp != null && mStamp.isValid();
    }
}
