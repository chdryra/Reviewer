/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import android.view.MenuItem;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.NodeRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewCollection;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiBookmarks<T extends GvData> extends MenuActionItemBasic<T>{
    private static final int CODE = RequestCodeGenerator.getCode(MaiBookmarks.class);

    private final UiLauncher mLauncher;
    private final NodeRepo mRepo;
    private final FactoryReviewView mFactory;

    public MaiBookmarks(UiLauncher launcher, NodeRepo repo, FactoryReviewView factory) {
        mLauncher = launcher;
        mFactory = factory;
        mRepo = repo;
    }

    @Override
    public void doAction(MenuItem item) {
        AuthorId authorId = getApp().getAccounts().getUserSession().getAuthorId();
        ReviewCollection bookmarks = mRepo.getCollectionForAuthor(authorId, Strings.Playlists.BOOKMARKS);
        ReviewNode node = mRepo.getMetaReview(bookmarks, authorId,  bookmarks.getName());
        mLauncher.launch(mFactory.newListView(node, null), new UiLauncherArgs(CODE));
    }
}
