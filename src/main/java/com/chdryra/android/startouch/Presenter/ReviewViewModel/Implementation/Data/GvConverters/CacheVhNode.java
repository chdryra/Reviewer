/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import android.graphics.Bitmap;

import com.chdryra.android.corelibrary.CacheUtils.QueueCache;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 07/02/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class CacheVhNode {
    private final QueueCache<AuthorName> mAuthors;
    private final QueueCache<ProfileImage> mProfiles;
    private final QueueCache<DataSubject> mSubjects;
    private final QueueCache<DataRating> mRatings;
    private final QueueCache<IdableList<DataComment>> mComments;
    private final QueueCache<IdableList<DataLocation>> mLocations;
    private final QueueCache<IdableList<DataTag>> mTags;
    private final QueueCache<DataDate> mDates;
    private final QueueCache<Bitmap> mCovers;

    CacheVhNode(QueueCache<AuthorName> authors,
                QueueCache<ProfileImage> profiles, 
                QueueCache<DataSubject> subjects,
                QueueCache<DataRating> ratings,
                QueueCache<DataDate> dates,
                QueueCache<Bitmap> covers,
                QueueCache<IdableList<DataTag>> tags,
                QueueCache<IdableList<DataComment>> headlines,
                QueueCache<IdableList<DataLocation>> locations) {
        mProfiles = profiles;
        mSubjects = subjects;
        mRatings = ratings;
        mComments = headlines;
        mLocations = locations;
        mTags = tags;
        mAuthors = authors;
        mDates = dates;
        mCovers = covers;
    }

    public boolean addComments(IdableList<DataComment> comments) {
        ReviewId reviewId = comments.getReviewId();
        boolean newComments = !containsComments(reviewId) || !getComments(reviewId).equals
                (comments);
        mComments.add(comments.getReviewId().toString(), comments);
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

    public boolean addAuthor(ReviewId reviewId, AuthorName author) {
        boolean newAuthor = !containsAuthor(reviewId)
                || !mAuthors.get(reviewId.toString()).getName().equals(author.getName());
        mAuthors.add(reviewId.toString(), author);
        return newAuthor;
    }

    public AuthorName getAuthor(ReviewId reviewId) {
        return mAuthors.get(reviewId.toString());
    }

    public void removeAuthor(ReviewId reviewId) {
        mAuthors.remove(reviewId.toString());
    }

    public boolean containsAuthor(ReviewId reviewId) {
        return mAuthors.containsId(reviewId.toString());
    }

    public boolean addProfile(ProfileImage profile) {
        AuthorId authorId = profile.getAuthorId();
        boolean newProfile = !containsProfile(authorId)
                || !mProfiles.get(authorId.toString()).getBitmap().sameAs(profile.getBitmap());
        mProfiles.add(authorId.toString(), profile);
        return newProfile;
    }

    public ProfileImage getProfile(AuthorId authorId) {
        return mProfiles.get(authorId.toString());
    }

    public void removeProfile(AuthorId authorId) {
        mProfiles.remove(authorId.toString());
    }

    public boolean containsProfile(AuthorId authorId) {
        return mProfiles.containsId(authorId.toString());
    }

    public void addDate(DataDate date) {
        addData(date, mDates);
    }

    public DataDate getDate(ReviewId reviewId) {
        return mDates.get(reviewId.toString());
    }

    public boolean containsDate(ReviewId reviewId) {
        return mDates.containsId(reviewId.toString());
    }

    public void addSubject(DataSubject subject) {
        addData(subject, mSubjects);
    }

    public DataSubject getSubject(ReviewId reviewId) {
        return mSubjects.get(reviewId.toString());
    }

    public boolean containsSubject(ReviewId reviewId) {
        return mSubjects.containsId(reviewId.toString());
    }

    public void addRating(DataRating rating) {
        addData(rating, mRatings);
    }

    public DataRating getRating(ReviewId reviewId) {
        return mRatings.get(reviewId.toString());
    }

    public boolean containsRating(ReviewId reviewId) {
        return mRatings.containsId(reviewId.toString());
    }

    public void addCover(ReviewId reviewId, Bitmap bitmap) {
        boolean newCover = !containsCover(reviewId) || !getCover(reviewId).sameAs(bitmap);
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

    public boolean addLocations(IdableList<DataLocation> locations) {
        ReviewId reviewId = locations.getReviewId();
        boolean newLocations = !containsLocations(reviewId) || !getLocations(reviewId).equals
                (locations);
        mLocations.add(reviewId.toString(), locations);
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
        mTags.add(reviewId.toString(), tags);
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

    private <T extends HasReviewId> void addData(T datum, QueueCache<T> cache) {
        cache.add(datum.getReviewId().toString(), datum);
    }
}
