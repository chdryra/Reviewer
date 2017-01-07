/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Algorithms.DataSorting.ComparatorCollection;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuOptionsItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.BannerButtonAuthorReviews;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.BannerButtonSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.BannerButtonSorter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemConfigLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuViewDataDefault;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.RatingBarCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsSelectAndExecute;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.OptionsSelector;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewData<T extends GvData> extends FactoryActionsNone<T> {
    private final ReviewNode mNode;
    private final FactoryReviewView mFactoryView;
    private final FactoryCommands mFactoryCommands;
    private final UiLauncher mLauncher;
    private final ReviewStamp mStamp;
    private final AuthorsRepository mRepo;
    private final ComparatorCollection<? super T> mComparators;

    private Command mBannerLongClick;
    private LaunchableConfig mGridItemConfig;
    private String mButtonTitle;

    public FactoryActionsViewData(ViewDataParameters<T> parameters) {
        super(parameters.getDataType());
        mNode = parameters.getNode();
        mButtonTitle = parameters.getButtonTitle();
        mFactoryView = parameters.getFactoryView();
        mFactoryCommands = parameters.getFactoryCommands();
        mLauncher = parameters.getLauncher();
        mStamp = parameters.getStamp();
        mRepo = parameters.getRepo();
        mComparators = parameters.getComparators();
        mBannerLongClick = parameters.getBannerLongClick();
        mGridItemConfig = parameters.getGridItemConfig();
    }

    protected UiLauncher getLauncher() {
        return mLauncher;
    }

    @NonNull
    protected BannerButtonSelector<T> newBannerButtonSelector(String buttonTitle, OptionsSelector selector) {
        return new BannerButtonSorter<>(buttonTitle, selector, mComparators);
    }

    @Override
    public MenuAction<T> newMenu() {
        return new MenuViewDataDefault<>(getDataType().getDataName(), newOptionsMenuItem());
    }

    @Override
    public RatingBarAction<T> newRatingBar() {
        if(hasStamp()) {
            return new RatingBarCommand<>(new Command() {
                @Override
                public void execute() {
                    mLauncher.getReviewLauncher().launchAsList(mStamp);
                }
            }, null);
        } else {
            return new RatingBarExpandGrid<>(mLauncher, mFactoryView);
        }
    }

    @Override
    public BannerButtonAction<T> newBannerButton() {
        if(hasStamp()) {
            return new BannerButtonAuthorReviews<>(mLauncher.getReviewLauncher(), mStamp, mRepo);
        } else {
            BannerButtonSelector<T> selector
                    = newBannerButtonSelector(mButtonTitle, mFactoryCommands.newOptionsSelector());
            if(mNode != null) selector.addOption(mFactoryCommands.newLaunchFormattedCommand(mNode));
            selector.addLongClick(mBannerLongClick);
            return selector;
        }
    }

    @Override
    public GridItemAction<T> newGridItem() {
        return new GridItemConfigLauncher<>(mLauncher, mFactoryView, mGridItemConfig);
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
        OptionsSelectAndExecute command
                = mFactoryCommands.newReviewOptionsSelector(mStamp.getDataAuthorId());
        return new MaiOptionsCommand<>(command);
    }

    boolean hasStamp() {
        return mStamp.isValid();
    }
}
