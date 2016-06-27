/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSize;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 27/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewReferenceWrapper extends ReviewReferenceBasic {
    private static final CallbackMessage OK = CallbackMessage.ok();
    private Review mReview;
    private TagsManager mTagsManager;
    private ReviewNode mNode;

    public ReviewReferenceWrapper(Review review,
                                  TagsManager tagsManager,
                                  FactoryReviews reviewsFactory,
                                  FactoryBinders bindersFactory) {
        super(bindersFactory.newBindersManager());
        mReview = review;
        mTagsManager = tagsManager;
        mNode = reviewsFactory.createLeafNode(this);
    }

    @Override
    public ReviewId getReviewId() {
        return mReview.getReviewId();
    }

    @Override
    public DataSubject getSubject() {
        return mReview.getSubject();
    }

    @Override
    public DataRating getRating() {
        return mReview.getRating();
    }

    @Override
    public DataAuthorReview getAuthor() {
        return mReview.getAuthor();
    }

    @Override
    public DataDateReview getPublishDate() {
        return mReview.getPublishDate();
    }

    @Override
    public ReviewNode asNode() {
        return mNode;
    }

    @Override
    public void getCovers(CoversCallback callback) {
        IdableList<DataImage> covers = new IdableDataList<>(getReviewId());
        covers.add(mReview.getCover());
        callback.onCovers(covers, OK);
    }

    @Override
    public void getTags(TagsCallback callback) {
        ItemTagCollection tagCollection = mTagsManager.getTags(getReviewId().toString());
        IdableList<DataTag> tags = new IdableDataList<>(getReviewId());
        for (ItemTag tag : tagCollection) {
            tags.add(new DatumTag(getReviewId(), tag.getTag()));
        }
        callback.onTags(tags, OK);
    }

    @Override
    public void getCriteria(CriteriaCallback callback) {
        IdableList<DataCriterion> criteria = new IdableDataList<>(getReviewId());
        criteria.addAll(mReview.getCriteria());
        callback.onCriteria(criteria, OK);
    }

    @Override
    public void getImages(ImagesCallback callback) {
        IdableList<DataImage> Images = new IdableDataList<>(getReviewId());
        Images.addAll(mReview.getImages());
        callback.onImages(Images, OK);
    }

    @Override
    public void getComments(CommentsCallback callback) {
        IdableList<DataComment> Comments = new IdableDataList<>(getReviewId());
        Comments.addAll(mReview.getComments());
        callback.onComments(Comments, OK);
    }

    @Override
    public void getLocations(LocationsCallback callback) {
        IdableList<DataLocation> Locations = new IdableDataList<>(getReviewId());
        Locations.addAll(mReview.getLocations());
        callback.onLocations(Locations, OK);
    }

    @Override
    public void getFacts(FactsCallback callback) {
        IdableList<DataFact> Facts = new IdableDataList<>(getReviewId());
        Facts.addAll(mReview.getFacts());
        callback.onFacts(Facts, OK);
    }

    @Override
    public void getTagsSize(TagsSizeCallback callback) {
        ItemTagCollection tagCollection = mTagsManager.getTags(getReviewId().toString());
        callback.onNumTags(newSize(tagCollection.size()), OK);
    }

    @Override
    public void getCriteriaSize(CriteriaSizeCallback callback) {
        callback.onNumCriteria(newSize(mReview.getCriteria().size()), OK);
    }

    @Override
    public void getImagesSize(ImagesSizeCallback callback) {
        callback.onNumImages(newSize(mReview.getImages().size()), OK);
    }

    @Override
    public void getCommentsSize(CommentsSizeCallback callback) {
        callback.onNumComments(newSize(mReview.getComments().size()), OK);
    }

    @Override
    public void getLocationsSize(LocationsSizeCallback callback) {
        callback.onNumLocations(newSize(mReview.getLocations().size()), OK);
    }

    @Override
    public void getFactsSize(FactsSizeCallback callback) {
        callback.onNumFacts(newSize(mReview.getFacts().size()), OK);
    }

    @Override
    public void dereference(DereferenceCallback callback) {
        callback.onDereferenced(mReview, OK);
    }

    @Override
    public boolean isValid() {
        return mReview != null;
    }

    private DataSize newSize(int size) {
        return new DatumSize(getReviewId(), size);
    }
}
