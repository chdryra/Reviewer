/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 08/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RepositoryCollection<Key> implements ReferencesRepository {
    private Map<Key, ReferencesRepository> mRepos;
    private List<ReviewsSubscriber> mSubscribers;

    public RepositoryCollection() {
        mRepos = new HashMap<>();
        mSubscribers = new ArrayList<>();
    }

    public void add(Key id, ReferencesRepository repo) {
        if(!mRepos.containsKey(id)) {
            mRepos.put(id, repo);
            for(ReviewsSubscriber subscriber : mSubscribers) {
                repo.subscribe(subscriber);
            }
        } else {
            remove(id);
            add(id, repo);
        }
    }

    public void remove(Key id) {
        if(mRepos.containsKey(id)) {
            ReferencesRepository removed = mRepos.remove(id);
            for(ReviewsSubscriber subscriber : mSubscribers) {
                removed.unsubscribe(subscriber);
            }
        }
    }

    public Set<Key> getKeys() {
        return mRepos.keySet();
    }

    @Override
    public void subscribe(ReviewsSubscriber subscriber) {
        if(!mSubscribers.contains(subscriber)) {
            mSubscribers.add(subscriber);
            for(ReferencesRepository repo : mRepos.values()) {
                repo.subscribe(subscriber);
            }
        }
    }

    @Override
    public void unsubscribe(ReviewsSubscriber subscriber) {
        if(mSubscribers.contains(subscriber)) {
            mSubscribers.remove(subscriber);
            for(ReferencesRepository repo : mRepos.values()) {
                repo.unsubscribe(subscriber);
            }
        }
    }

    @Override
    public void getReference(ReviewId reviewId, RepositoryCallback callback) {
        new ReferenceFinder(reviewId, callback).execute();
    }

    private class ReferenceFinder {
        private ReviewId mId;
        private RepositoryCallback mCallback;
        private int mNumRepos;
        private int mNumReturned;
        private boolean mDone;

        public ReferenceFinder(ReviewId id, RepositoryCallback callback) {
            mId = id;
            mCallback = callback;
        }

        private void execute() {
            mNumRepos = mRepos.size();
            mNumReturned = 0;
            mDone = false;
            for(ReferencesRepository repo : mRepos.values()) {
                if(mDone) break;
                repo.getReference(mId, new RepositoryCallback() {
                    @Override
                    public void onRepositoryCallback(RepositoryResult result) {
                        parseResult(result);
                    }
                });
            }
        }

        private void parseResult(RepositoryResult result) {
            mNumReturned++;
            ReviewReference reference = result.getReference();
            if(result.isReference() && reference != null) {
                doCallback(reference);
            } else if(mNumReturned >= mNumRepos) {
                doCallback(null);
            }
        }

        private void doCallback(@Nullable ReviewReference reference) {
            mDone = true;
            RepositoryResult result;
            if(reference != null) {
                result= new RepositoryResult(reference);
            } else {
                result = new RepositoryResult(CallbackMessage.error("Reference not found"));
            }
            mCallback.onRepositoryCallback(result);
        }
    }
}
