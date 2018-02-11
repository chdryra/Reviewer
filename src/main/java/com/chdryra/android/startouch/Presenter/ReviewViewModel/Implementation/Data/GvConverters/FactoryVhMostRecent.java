/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.CacheUtils.InMemoryCache;
import com.chdryra.android.corelibrary.CacheUtils.QueueCache;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryLaunchCommands;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ReviewSelector;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.SelectorMostRecent;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhReviewAbstract;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ViewHolderFactory;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryVhMostRecent implements ViewHolderFactory<VhNode> {
    private static final int CACHE_MAX = 50;

    private final AuthorsRepo mRepository;
    private final FactoryLaunchCommands mCommandsfactory;

    private CacheVhNode mCache;

    public FactoryVhMostRecent(AuthorsRepo repository, FactoryLaunchCommands commandsfactory) {
        mRepository = repository;
        mCommandsfactory = commandsfactory;
        mCache = new CacheVhNode(this.<AuthorName>newCache(), this.<ProfileImage>newCache(),
                this.<DataSubject>newCache(), this.<DataRating>newCache(),
                this.<DataDate>newCache(), this.<Bitmap>newCache(),
                this.<IdableList<DataTag>>newCache(), this.<IdableList<DataComment>>newCache(),
                this.<IdableList<DataLocation>>newCache());
    }

    @NonNull
    private <T> QueueCache<T> newCache() {
        return new QueueCache<>(new InMemoryCache<T>(), CACHE_MAX);
    }

    @Override
    public VhNode newViewHolder() {
        return new VhReviewAbstract(mRepository,
                mCommandsfactory.getOptionsFactory(),
                mCommandsfactory.newLaunchProfileCommand(),
                new ReviewSelector(new SelectorMostRecent()), mCache);
    }
}
