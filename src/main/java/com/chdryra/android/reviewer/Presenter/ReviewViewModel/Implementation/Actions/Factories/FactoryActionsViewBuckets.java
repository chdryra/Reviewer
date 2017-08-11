/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Collections.CollectionIdable;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ButtonViewer;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.NamedReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.CommandsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvBucket;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewBuckets extends FactoryActionsViewData<GvBucket> {
    private final ReviewNode mNode;
    private final CollectionIdable<String, NamedReviewView<?>> mContextViews;
    private final CommandsList mContextCommands;
    private final FactoryCommands mFactoryCommands;

    public FactoryActionsViewBuckets(ViewDataParameters<GvBucket> parameters, ReviewNode node,
                                     CollectionIdable<String, NamedReviewView<?>> contextViews,
                                     CommandsList contextCommands, FactoryCommands factoryCommands) {
        super(parameters);
        mNode = node;
        mContextViews = contextViews;
        mContextCommands = contextCommands;
        mFactoryCommands = factoryCommands;
    }

    @Override
    public GridItemAction<GvBucket> newGridItem() {
        return new GridItemLauncher<>(getLauncher(), getViewFactory());
    }

    @Override
    public ButtonAction<GvBucket> newBannerButton() {
        return new ButtonActionNone<>();
    }

    @Nullable
    @Override
    public ButtonAction<GvBucket> newContextButton() {
        String title = Strings.Buttons.DISTRIBUTION;
        ButtonViewer<GvBucket> button = new ButtonViewer<>(title, title, 0, newSelector(), mContextViews);
        if(mNode != null) {
            for(Command command : mContextCommands)
                button.addOption(command);
        }

        return button;
    }


    private OptionsSelector newSelector() {
        return mFactoryCommands.newOptionsSelector();
    }
}
