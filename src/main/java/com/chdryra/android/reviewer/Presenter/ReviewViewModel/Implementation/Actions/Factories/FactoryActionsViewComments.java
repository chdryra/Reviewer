/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiSplitCommentRefs;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuViewComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Utils.ParcelablePacker;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewComments extends FactoryActionsViewData<GvComment.Reference> {
    public FactoryActionsViewComments(FactoryReviewView factoryView,
                                      FactoryCommands factoryCommands,
                                      ReviewStamp stamp, AuthorsRepository repo, UiLauncher launcher,

                                      LaunchableConfig optionsConfig, LaunchableConfig
                                              gridItemConfig,
                                      @Nullable ReviewNode node) {
        super(GvComment.Reference.TYPE, factoryView, factoryCommands, stamp, repo, launcher, optionsConfig, gridItemConfig,

                node);
    }

    @Override
    public GridItemLauncher<GvComment.Reference> newGridItem() {
        return new GridItemComments(getLauncher(), getGridItemConfig(), getViewFactory(),
                new ParcelablePacker<GvDataParcelable>());
    }

    @Override
    public MenuAction<GvComment.Reference> newMenu() {
        return new MenuViewComments(newOptionsMenuItem(), new MaiSplitCommentRefs());
    }
}
