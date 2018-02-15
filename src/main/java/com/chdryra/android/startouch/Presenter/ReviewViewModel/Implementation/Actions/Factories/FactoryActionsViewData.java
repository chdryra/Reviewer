/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Comparators.ComparatorCollection;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuOptionsItem;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ActionsParameters;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ButtonAuthorReviews;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ButtonSelectorWithDefault;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ButtonSorter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemConfigLauncher;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemLauncher;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiOptionsCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuViewDataDefault;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.RatingBarExpandGrid;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryLaunchCommands;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryReviewOptions;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.CommandList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsSelectAndExecute;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsSelector;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.ReviewOptionsSelector;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewData<T extends GvData> extends FactoryActionsNone<T> {
    private final FactoryReviewView mFactoryView;
    private final FactoryLaunchCommands mFactoryLaunchCommands;
    private final UiLauncher mLauncher;
    private final ReviewStamp mStamp;
    private final AuthorsRepo mRepo;
    private final ComparatorCollection<? super T> mComparators;

    private LaunchableConfig mGridItemConfig;
    private CommandList mContextCommands;

    FactoryActionsViewData(ActionsParameters<T> parameters) {
        super(parameters.getDataType());
        mFactoryView = parameters.getFactoryView();
        mFactoryLaunchCommands = parameters.getFactoryLaunchCommands();
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

    private OptionsSelector newSelector() {
        return getCommandsFactory().getOptionsFactory().newOptionsSelector();
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

    FactoryLaunchCommands getCommandsFactory() {
        return mFactoryLaunchCommands;
    }

    LaunchableConfig getGridItemConfig() {
        return mGridItemConfig;
    }

    @NonNull
    MenuOptionsItem<T> newOptionsMenuItem() {
        OptionsSelectAndExecute command;
        FactoryReviewOptions factory = mFactoryLaunchCommands.getOptionsFactory();
        if(hasStamp()) {
            command = factory.newReviewOptionsSelector(ReviewOptionsSelector.SelectorType.ALL, mStamp.getDataAuthorId());
        } else {
            command = factory.newReviewOptionsSelector(ReviewOptionsSelector.SelectorType.ALL);
        }
        return new MaiOptionsCommand<>(command);
    }

    boolean hasStamp() {
        return mStamp != null && mStamp.isValid();
    }
}
