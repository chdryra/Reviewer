/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SizeReferencer;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CollectionBinder;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CollectionReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 08/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedRepo extends RepoCollection<AuthorId> implements ReviewsRepoReadable {
    private final AuthorListRef mFollowing;
    private final ReviewsRepo mMasterRepo;
    
    private boolean mInitialised = false;
    private Binder mBinder;

    public FeedRepo(AuthorId usersId,
                    AuthorListRef following,
                    ReviewsRepo masterRepo,
                    ReviewsRepoReadable initialFeed,
                    ReviewDereferencer dereferencer,
                    SizeReferencer sizeReferencer) {
        super(dereferencer, sizeReferencer);
        mFollowing = following;
        mMasterRepo = masterRepo;
        add(usersId, initialFeed);
    }

    @Override
    public void getReference(ReviewId reviewId, RepoCallback callback) {
        //TODO Not the best implementation....
        if (!mInitialised) initialise();
        super.getReference(reviewId, callback);
    }

    @Override
    public void getReview(ReviewId reviewId, RepoCallback callback) {
        if (!mInitialised) initialise();
        super.getReview(reviewId, callback);
    }

    @Override
    public void bindToItems(CollectionBinder<ReviewReference> binder) {
        super.bindToItems(binder);
        if(!mInitialised) initialise();
    }

    @Override
    public void unbindItemBinder(CollectionBinder<ReviewReference> binder) {
        super.unbindItemBinder(binder);
        if (getBinders().size() == 0) {
            mFollowing.unbindFromItems(mBinder);
            mInitialised = false;
        }
    }

    private void initialise() {
        if(!mInitialised) {
            mInitialised = true;
            mBinder = new Binder();
            mFollowing.bindToItems(mBinder);
        }
    }

    private class Binder implements CollectionBinder<AuthorId> {
        @Override
        public void onItemAdded(AuthorId item) {
            add(item, mMasterRepo.getReviewsByAuthor(item));
        }

        @Override
        public void onItemRemoved(AuthorId item) {
            remove(item);
        }

        @Override
        public void onCollectionChanged(Collection<AuthorId> newItems) {
            Set<AuthorId> toRemove = new HashSet<>(getKeys());
            Set<AuthorId> toAdd = new HashSet<>(newItems);
            toRemove.removeAll(new HashSet<>(newItems));
            toAdd.removeAll(new HashSet<>(getKeys()));
            for (AuthorId toRemoveId : toRemove) {
                remove(toRemoveId);
            }
            for (AuthorId toAddId : toAdd) {
                add(toAddId, mMasterRepo.getReviewsByAuthor(toAddId));
            }
        }

        @Override
        public void onInvalidated(CollectionReference<AuthorId, ?, ?> reference) {
            for (AuthorId toRemoveId : getKeys()) {
                remove(toRemoveId);
            }
        }
    }
}
