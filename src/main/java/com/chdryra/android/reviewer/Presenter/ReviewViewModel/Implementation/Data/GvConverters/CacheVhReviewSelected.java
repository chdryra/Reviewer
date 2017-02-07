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
    }

    public boolean addComments(IdableList<DataComment> comments) {
        ReviewId reviewId = comments.getReviewId();
        boolean newComments = !containsComments(reviewId) || !getComments(reviewId).equals(comments);
        if(newComments) mComments.add(comments.getReviewId().toString(), comments);
        return newComments;
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

    public boolean addAuthor(ReviewId reviewId, NamedAuthor author) {
        boolean newAuthor = false;
        if(mAuthors.containsId(reviewId.toString())) {
            NamedAuthor current = getAuthor(reviewId);
            newAuthor = !current.getName().equals(author.getName())
                    || !current.getAuthorId().toString().equals(author.getAuthorId().toString());
        }
        if(newAuthor) mAuthors.add(reviewId.toString(), author);
        return newAuthor;
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

    public boolean addCover(ReviewId reviewId, Bitmap bitmap) {
        boolean newCover = !containsCover(reviewId) || !getCover(reviewId).equals(bitmap);
        if(newCover) mCovers.add(reviewId.toString(), bitmap);
        return newCover;
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

    public boolean addLocations(IdableList<DataLocation> locations) {
        ReviewId reviewId = locations.getReviewId();
        boolean newLocations = !containsLocations(reviewId) || getLocations(reviewId).equals(locations);
        if(newLocations) mLocations.add(reviewId.toString(), locations);
        return newLocations;
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

    public boolean addTags(IdableList<DataTag> tags) {
        ReviewId reviewId = tags.getReviewId();
        boolean newTags = !containsTags(reviewId) || !getTags(reviewId).equals(tags);
        if(newTags) mTags.add(reviewId.toString(), tags);
        return newTags;
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
