/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ListItemBinder;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ListReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefAuthorList;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 08/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedRepository implements ReferencesRepository {
    private RefAuthorList mAuthorsList;
    private ReviewsRepository mMasterRepo;
    private RepositoryCollection<AuthorId> mRepos;
    private boolean mInitialised = false;
    private List<ReviewsSubscriber> mSubscribers;
    private Binder mBinder;

    public FeedRepository(RefAuthorList authorsList, ReviewsRepository masterRepo) {
        mAuthorsList = authorsList;
        mMasterRepo = masterRepo;
        mRepos = new RepositoryCollection<>();
        mSubscribers = new ArrayList<>();
    }

    @Override
    public void subscribe(ReviewsSubscriber subscriber) {
        mRepos.subscribe(subscriber);
        if (!mInitialised) initialise();
        if (!mSubscribers.contains(subscriber)) mSubscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(ReviewsSubscriber subscriber) {
        mRepos.unsubscribe(subscriber);
        if (mSubscribers.contains(subscriber)) mSubscribers.remove(subscriber);
        if (mSubscribers.size() == 0) mAuthorsList.unbindFromItems(mBinder);
    }

    @Override
    public void getReference(ReviewId reviewId, RepositoryCallback callback) {
        //TODO Not the best implementation....
        if (!mInitialised) initialise();
        mRepos.getReference(reviewId, callback);
    }

    private void initialise() {
        if(!mInitialised) {
            mInitialised = true;
            mBinder = new Binder();
            mAuthorsList.bindToItems(mBinder);
        }
    }

    private class Binder implements ListItemBinder<AuthorId> {
        @Override
        public void onItemAdded(AuthorId value) {
            mRepos.add(value, mMasterRepo.getLatestForAuthor(value));
        }

        @Override
        public void onItemRemoved(AuthorId value) {
            mRepos.remove(value);
        }

        @Override
        public void onListChanged(Collection<AuthorId> newItems) {
            Set<AuthorId> toRemove = new HashSet<>(mRepos.getKeys());
            Set<AuthorId> toAdd = new HashSet<>(newItems);
            toRemove.removeAll(new HashSet<>(newItems));
            toAdd.removeAll(new HashSet<>(mRepos.getKeys()));
            for (AuthorId toRemoveId : toRemove) {
                mRepos.remove(toRemoveId);
            }
            for (AuthorId toAddId : toAdd) {
                mRepos.add(toAddId, mMasterRepo.getLatestForAuthor(toAddId));
            }
        }

        @Override
        public void onInvalidated(ListReference<AuthorId, ?> reference) {
            for (AuthorId toRemoveId : mRepos.getKeys()) {
                mRepos.remove(toRemoveId);
            }
        }
    }
}
