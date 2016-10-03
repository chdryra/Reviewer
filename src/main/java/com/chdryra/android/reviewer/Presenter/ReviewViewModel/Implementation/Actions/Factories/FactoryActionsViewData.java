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
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ContextButtonStamp;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemConfigLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiSplitCommentRefs;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewData<T extends GvData> extends FactoryActionsNone<T> {
    private FactoryReviewView mFactory;
    private ReviewStamp mStamp;
    private LaunchableConfig mConfig;
    private AuthorsRepository mRepo;

    public FactoryActionsViewData(GvDataType<T> dataType,
                                  FactoryReviewView factory,
                                  LaunchableConfig config,
                                  ReviewStamp stamp,
                                  AuthorsRepository repo) {
        super(dataType);
        mFactory = factory;
        mStamp = stamp;
        mRepo = repo;
        mConfig = config;
    }

    protected FactoryReviewView getFactory() {
        return mFactory;
    }

    public LaunchableConfig getConfig() {
        return mConfig;
    }

    @Override
    public MenuAction<T> newMenu() {
        return newMenu(getDataType().getDataName());
    }

    @Override
    public RatingBarAction<T> newRatingBar() {
        return new RatingBarExpandGrid<>(mFactory);
    }

    @Override
    public GridItemAction<T> newGridItem() {
        return new GridItemConfigLauncher<>(mConfig, mFactory,
                new ParcelablePacker<GvDataParcelable>());
    }

    @Nullable
    @Override
    public ContextualButtonAction<T> newContextButton() {
        return mStamp.isValid() ? new ContextButtonStamp<T>(mConfig.getLauncher().newReviewLauncher(), mStamp, mRepo) : null;
    }

    /**
     * Created by: Rizwan Choudrey
     * On: 27/09/2016
     * Email: rizwan.choudrey@gmail.com
     */
    public static class Comments extends FactoryActionsViewData<GvComment.Reference> {
        public Comments(FactoryReviewView factory,
                        LaunchableConfig config,
                        ReviewStamp stamp,
                        AuthorsRepository repo) {
            super(GvComment.Reference.TYPE, factory,config, stamp, repo);
        }

        @Override
        public GridItemLauncher<GvComment.Reference> newGridItem() {
            return new GridItemComments(getConfig(), getFactory(),
                    new ParcelablePacker<GvDataParcelable>());
        }

        @Override
        public MenuAction<GvComment.Reference> newMenu() {
            return new MenuComments(new MaiSplitCommentRefs());
        }
    }
}
