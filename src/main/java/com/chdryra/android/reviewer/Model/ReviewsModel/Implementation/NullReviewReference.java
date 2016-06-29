/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;


import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumRating;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSize;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSubject;
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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 12/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullReviewReference extends ReviewReferenceBasic {
    private static final CallbackMessage OK = CallbackMessage.ok();

    public NullReviewReference() {
        super(new BindersManagerReference());
    }

    @Override
    public ReviewId getReviewId() {
        return new DatumReviewId();
    }

    @Override
    public DataSubject getSubject() {
        return new DatumSubject();
    }

    @Override
    public DataRating getRating() {
        return new DatumRating();
    }

    @Override
    public DataAuthorReview getAuthor() {
        return new DatumAuthorReview();
    }

    @Override
    public DataDateReview getPublishDate() {
        return new DatumDateReview();
    }

    @Override
    public ReviewNode asNode() {
        return new NodeLeaf(this, getBindersManager());
    }

    @Override
    public void getData(CoversCallback callback) {
        callback.onCovers(new IdableDataList<DataImage>(getReviewId()), OK);
    }

    @Override
    public void getData(TagsCallback callback) {
        callback.onTags(new IdableDataList<DataTag>(getReviewId()), OK);
    }

    @Override
    public void getData(CriteriaCallback callback) {
        callback.onCriteria(new IdableDataList<DataCriterion>(getReviewId()), OK);
    }

    @Override
    public void getData(ImagesCallback callback) {
        callback.onImages(new IdableDataList<DataImage>(getReviewId()), OK);
    }

    @Override
    public void getData(CommentsCallback callback) {
        callback.onComments(new IdableDataList<DataComment>(getReviewId()), OK);
    }

    @Override
    public void getData(LocationsCallback callback) {
        callback.onLocations(new IdableDataList<DataLocation>(getReviewId()), OK);
    }

    @Override
    public void getData(FactsCallback callback) {
        callback.onFacts(new IdableDataList<DataFact>(getReviewId()), OK);
    }

    private DataSize zero() {
        return new DatumSize(getReviewId(), 0);
    }

    @Override
    public void getSize(TagsSizeCallback callback) {
        callback.onNumTags(zero(), OK);
    }

    @Override
    public void getSize(CriteriaSizeCallback callback) {
        callback.onNumCriteria(zero(), OK);
    }

    @Override
    public void getSize(ImagesSizeCallback callback) {
        callback.onNumImages(zero(), OK);
    }

    @Override
    public void getSize(CommentsSizeCallback callback) {
        callback.onNumComments(zero(), OK);
    }

    @Override
    public void getSize(LocationsSizeCallback callback) {
        callback.onNumLocations(zero(), OK);
    }

    @Override
    public void getSize(FactsSizeCallback callback) {
        callback.onNumFacts(zero(), OK);
    }

    @Override
    public void dereference(DereferenceCallback callback) {
        callback.onDereferenced(null, CallbackMessage.error("Null reference"));
    }

    @Override
    public boolean isValidReference() {
        return false;
    }
}
