/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;


import android.view.MenuItem;

import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewCollection;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewNodeRepo;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiBookmarks<T extends GvData> extends MenuActionItemBasic<T> {
    private static final int CODE = RequestCodeGenerator.getCode(MaiBookmarks.class);

    private final UiLauncher mLauncher;
    private final ReviewNodeRepo mRepo;
    private final FactoryReviewView mFactory;

    public MaiBookmarks(UiLauncher launcher, ReviewNodeRepo repo, FactoryReviewView factory) {
        mLauncher = launcher;
        mFactory = factory;
        mRepo = repo;
    }

    @Override
    public void doAction(MenuItem item) {
        AuthorId authorId = getApp().getAccounts().getUserSession().getAuthorId();
        ReviewCollection bookmarks = mRepo.getCollectionForAuthor(authorId, Strings.Playlists
                .BOOKMARKS);
        ReviewNode node = mRepo.getMetaReview(bookmarks, authorId, bookmarks.getName());
        mLauncher.launch(mFactory.newListView(node, null), new UiLauncherArgs(CODE));
    }
}
