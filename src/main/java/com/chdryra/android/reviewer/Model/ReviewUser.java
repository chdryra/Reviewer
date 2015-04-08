/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 February, 2015
 */

package com.chdryra.android.reviewer.Model;

import com.chdryra.android.reviewer.Controller.DataComment;
import com.chdryra.android.reviewer.Controller.DataFact;
import com.chdryra.android.reviewer.Controller.DataImage;
import com.chdryra.android.reviewer.Controller.DataLocation;
import com.chdryra.android.reviewer.Controller.MdGvConverter;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * The fundamental {@link Review} implementation that holds the
 * review data. The review in leaf nodes in a tree.
 */
public class ReviewUser implements Review {
    private final ReviewNode mNode;

    private final ReviewId  mId;
    private final Author    mAuthor;
    private final Date      mPublishDate;
    private final MdSubject mSubject;
    private final MdRating  mRating;

    private final MdCommentList  mComments;
    private final MdImageList    mImages;
    private final MdFactList     mFacts;
    private final MdLocationList mLocations;

    public ReviewUser(ReviewId id, Author author, Date publishDate, String subject, float rating,
            Iterable<? extends DataComment> comments,
            Iterable<? extends DataImage> images,
            Iterable<? extends DataFact> facts,
            Iterable<? extends DataLocation> locations) {
        mId = id;
        mAuthor = author;
        mPublishDate = publishDate;
        mSubject = new MdSubject(subject, mId);
        mRating = new MdRating(rating, mId);

        mComments = MdGvConverter.toMdCommentList(comments, mId);
        mImages = MdGvConverter.toMdImageList(images, mId);
        mFacts = MdGvConverter.toMdFactList(facts, mId);
        mLocations = MdGvConverter.toMdLocationList(locations, mId);

        mNode = FactoryReview.createReviewNode(this);
    }

    public ReviewUser(ReviewId id, Author author, Date publishDate, String subject, float rating) {
        mId = id;
        mAuthor = author;
        mPublishDate = publishDate;
        mSubject = new MdSubject(subject, mId);
        mRating = new MdRating(rating, mId);

        mComments = new MdCommentList(mId);
        mImages = new MdImageList(mId);
        mFacts = new MdFactList(mId);
        mLocations = new MdLocationList(mId);

        mNode = FactoryReview.createReviewNode(this);
    }

    @Override
    public ReviewId getId() {
        return mId;
    }

    @Override
    public MdSubject getSubject() {
        return mSubject;
    }

    @Override
    public MdRating getRating() {
        return mRating;
    }

    @Override
    public Author getAuthor() {
        return mAuthor;
    }

    @Override
    public Date getPublishDate() {
        return mPublishDate;
    }

    @Override
    public MdCommentList getComments() {
        return mComments;
    }

    @Override
    public boolean hasComments() {
        return mComments.size() > 0;
    }

    @Override
    public MdFactList getFacts() {
        return mFacts;
    }

    @Override
    public boolean hasFacts() {
        return mFacts.size() > 0;
    }

    @Override
    public MdImageList getImages() {
        return mImages;
    }

    @Override
    public boolean hasImages() {
        return mImages.size() > 0;
    }

    @Override
    public MdLocationList getLocations() {
        return mLocations;
    }

    @Override
    public boolean hasLocations() {
        return mLocations.size() > 0;
    }
}