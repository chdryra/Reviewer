/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import android.graphics.Bitmap;

import com.chdryra.android.mygenerallibrary.CacheUtils.QueueCache;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 07/02/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class CacheVhReviewSelected implements CacheVhNode{
    private final QueueCache<IdableList<DataComment>> mComments;
    private final QueueCache<IdableList<DataLocation>> mLocations;
    private final QueueCache<IdableList<DataTag>> mTags;
    private final QueueCache<NamedAuthor> mAuthors;
    private final QueueCache<Bitmap> mCovers;

    public List<VhNode> mObservers;

    public CacheVhReviewSelected(QueueCache<IdableList<DataComment>> headlines, 
                                 QueueCache<IdableList<DataLocation>> locations, 
                                 QueueCache<IdableList<DataTag>> tags, 
                                 QueueCache<NamedAuthor> authors, 
                                 QueueCache<Bitmap> covers) {
        mComments = headlines;
        mLocations = locations;
        mTags = tags;
        mAuthors = authors;
        mCovers = covers;
        mObservers = new ArrayList<>();
    }

    @Override
    public void registerObserver(VhNode node) {
        if(!mObservers.contains(node)) mObservers.add(node);
    }

    @Override
    public void unregisterObserver(VhNode node) {
        if(mObservers.contains(node)) mObservers.remove(node);
    }

    @Override
    public void deleteCache(ReviewId reviewId) {
        removeAuthor(reviewId);
        removeComments(reviewId);
        removeCover(reviewId);
        removeLocations(reviewId);
        removeTags(reviewId);

        for(VhNode node : mObservers) {
            node.refresh(reviewId);
        }
    }

    public void addComments(IdableList<DataComment> comments) {
        mComments.add(comments.getReviewId().toString(), comments);
    }

    public IdableList<DataComment> getComments(ReviewId reviewId) {
        return mComments.get(reviewId.toString());
    }

    public void removeComments(ReviewId reviewId) {
        mComments.remove(reviewId.toString());
    }

    public boolean containsComments(ReviewId reviewId) {
        return mComments.containsId(reviewId.toString());
    }

    public void addAuthor(ReviewId reviewId, NamedAuthor author) {
        mAuthors.add(reviewId.toString(), author);
    }

    public NamedAuthor getAuthor(ReviewId reviewId) {
        return mAuthors.get(reviewId.toString());
    }

    public void removeAuthor(ReviewId reviewId) {
        mAuthors.remove(reviewId.toString());
    }

    public boolean containsAuthor(ReviewId reviewId) {
        return mAuthors.containsId(reviewId.toString());
    }

    public void addCover(ReviewId reviewId, Bitmap bitmap) {
        mCovers.add(reviewId.toString(), bitmap);
    }

    public Bitmap getCover(ReviewId reviewId) {
        return mCovers.get(reviewId.toString());
    }

    public void removeCover(ReviewId reviewId) {
        mCovers.remove(reviewId.toString());
    }

    public boolean containsCover(ReviewId reviewId) {
        return mCovers.containsId(reviewId.toString());
    }

    public void addLocations(IdableList<DataLocation> locations) {
        mLocations.add(locations.getReviewId().toString(), locations);
    }

    public IdableList<DataLocation> getLocations(ReviewId reviewId) {
        return mLocations.get(reviewId.toString());
    }

    public void removeLocations(ReviewId reviewId) {
        mLocations.remove(reviewId.toString());
    }

    public boolean containsLocations(ReviewId reviewId) {
        return mLocations.containsId(reviewId.toString());
    }

    public void addTags(IdableList<DataTag> tags) {
        mTags.add(tags.getReviewId().toString(), tags);
    }

    public IdableList<DataTag> getTags(ReviewId reviewId) {
        return mTags.get(reviewId.toString());
    }

    public void removeTags(ReviewId reviewId) {
        mTags.remove(reviewId.toString());
    }

    public boolean containsTags(ReviewId reviewId) {
        return mTags.containsId(reviewId.toString());
    }
}
