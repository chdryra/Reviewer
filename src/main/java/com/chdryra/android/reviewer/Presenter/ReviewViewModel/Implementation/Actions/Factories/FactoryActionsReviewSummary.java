/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ContextButtonStamp;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiReviewOptions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuReviewOptions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher.ReviewLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsReviewSummary extends FactoryActionsNone<GvSize.Reference> {
    private FactoryReviewView mFactory;
    private ReviewLauncher mLauncher;
    private LaunchableUi mOptionsUi;
    private ReviewStamp mStamp;
    private AuthorsRepository mRepo;

    public FactoryActionsReviewSummary(FactoryReviewView factory,
                                ReviewLauncher launcher,
                                LaunchableUi optionsUi,
                                ReviewStamp stamp,
                                AuthorsRepository repo) {
        super(GvSize.Reference.TYPE);
        mFactory = factory;
        mLauncher = launcher;
        mOptionsUi = optionsUi;
        mStamp = stamp;
        mRepo = repo;
    }

    @Override
    public MenuAction<GvSize.Reference> newMenu() {
        String title = Strings.Screens.SUMMARY;
        MenuAction<GvSize.Reference> menu = new MenuActionNone<>(title);
        if (mStamp.isValid()) {
            MaiReviewOptions<GvSize.Reference> mai
                    = new MaiReviewOptions<>(newOptionsCommand(mOptionsUi), mStamp.getDataAuthorId());
            menu = new MenuReviewOptions<>(mai, title);
        }

        return menu;
    }

    @Override
    public RatingBarAction<GvSize.Reference> newRatingBar() {
        return new RatingBarExpandGrid<>(mFactory);
    }

    @Override
    public GridItemAction<GvSize.Reference> newGridItem() {
        return new GridItemLauncher<>(mFactory);
    }

    @Nullable
    @Override
    public ContextualButtonAction<GvSize.Reference> newContextButton() {
        return mStamp.isValid() ?
                new ContextButtonStamp<GvSize.Reference>(mLauncher, mStamp, mRepo) : null;
    }
}
