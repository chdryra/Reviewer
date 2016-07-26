/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.MetaBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.MetaReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ValueBinder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 21/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReferenceBinder implements DataReviewInfo {
    private static final CallbackMessage OK = CallbackMessage.ok();
    private MetaReference mReference;
    private DataBinder mDataBinder;
    private DataSizeBinder mSizeBinder;
    private Map<Class<?>, ValueBinder<?>> mBinders;

    public interface DataBinder extends
            ReviewReference.CoverCallback,
            ReviewReference.TagsCallback,
            ReviewReference.CriteriaCallback,
            ReviewReference.ImagesCallback,
            ReviewReference.CommentsCallback,
            ReviewReference.LocationsCallback,
            ReviewReference.FactsCallback,
            MetaReference.ReviewsCallback,
            MetaReference.AuthorsCallback,
            MetaReference.SubjectsCallback,
            MetaReference.DatesCallback {
    }

    public interface DataSizeBinder extends
            ReviewReference.TagsSizeCallback,
            ReviewReference.CriteriaSizeCallback,
            ReviewReference.ImagesSizeCallback,
            ReviewReference.CommentsSizeCallback,
            ReviewReference.LocationsSizeCallback,
            ReviewReference.FactsSizeCallback,
            MetaReference.ReviewsSizeCallback,
            MetaReference.AuthorsSizeCallback,
            MetaReference.SubjectsSizeCallback,
            MetaReference.DatesSizeCallback {
    }

    public ReferenceBinder(MetaReference reference, DataSizeBinder sizeBinder, @Nullable
    DataBinder dataBinder) {
        mReference = reference;
        mSizeBinder = sizeBinder;
        mDataBinder = dataBinder;
        mBinders = new HashMap<>();
    }

    public ReviewNode getReviewNode() {
        return mReference.asNode();
    }

    public void bindToCovers() {
        if (toBind(Cover.class)) mReference.bind(addBinder(new Cover()));
    }

    public void unbindFromCovers() {
        if (isBound(Cover.class)) mReference.unbind(getBinder(Cover.class));
    }

    public void bindToTags() {
        if (toBind(Tags.class)) mReference.bind(addBinder(new Tags()));
    }

    public void unbindFromTags() {
        if (isBound(Tags.class)) mReference.unbind(getBinder(Tags.class));
    }

    public void bindToCriteria() {
        if (toBind(Criteria.class)) mReference.bind(addBinder(new Criteria()));
    }

    public void unbindFromCriteria() {
        if (isBound(Criteria.class)) mReference.unbind(getBinder(Criteria.class));
    }

    public void bindToComments() {
        if (toBind(Comments.class)) mReference.bind(addBinder(new Comments()));
    }

    public void unbindFromComments() {
        if (isBound(Comments.class)) mReference.unbind(getBinder(Comments.class));
    }

    public void bindToImages() {
        if (toBind(Images.class)) mReference.bind(addBinder(new Images()));
    }

    public void unbindFromImages() {
        if (isBound(Images.class)) mReference.unbind(getBinder(Images.class));
    }

    public void bindToLocations() {
        if (toBind(Locations.class)) mReference.bind(addBinder(new Locations()));
    }

    public void unbindFromLocations() {
        if (isBound(Locations.class)) mReference.unbind(getBinder(Locations.class));
    }

    public void bindToFacts() {
        if (toBind(Facts.class)) mReference.bind(addBinder(new Facts()));
    }

    public void unbindFromFacts() {
        if (isBound(Facts.class)) mReference.unbind(getBinder(Facts.class));
    }

    public void bindToReviews() {
        if (toBind(Reviews.class)) mReference.bind(addBinder(new Reviews()));
    }

    public void unbindFromReviews() {
        if (isBound(Reviews.class)) mReference.unbind(getBinder(Reviews.class));
    }

    public void bindToAuthors() {
        if (toBind(Authors.class)) mReference.bind(addBinder(new Authors()));
    }

    public void unbindFromAuthors() {
        if (isBound(Authors.class)) mReference.unbind(getBinder(Authors.class));
    }

    public void bindToSubjects() {
        if (toBind(Subjects.class)) mReference.bind(addBinder(new Subjects()));
    }

    public void unbindFromSubjects() {
        if (isBound(Subjects.class)) mReference.unbind(getBinder(Subjects.class));
    }

    public void bindToDates() {
        if (toBind(Dates.class)) mReference.bind(addBinder(new Dates()));
    }

    public void unbindFromDates() {
        if (isBound(Dates.class)) mReference.unbind(getBinder(Dates.class));
    }

    public void bindToNumTags() {
        if (!isBound(TagsSize.class)) mReference.bindToTags(addBinder(new TagsSize()));
    }

    public void unbindFromNumTags() {
        if (isBound(TagsSize.class)) mReference.unbindFromTags(getBinder(TagsSize.class));
    }

    public void bindToNumCriteria() {
        if (!isBound(CriteriaSize.class)) mReference.bindToCriteria(addBinder(new CriteriaSize()));
    }

    public void unbindFromNumCriteria() {
        if (isBound(CriteriaSize.class))
            mReference.unbindFromCriteria(getBinder(CriteriaSize.class));
    }

    public void bindToNumComments() {
        if (!isBound(CommentsSize.class)) mReference.bindToComments(addBinder(new CommentsSize()));
    }

    public void unbindFromNumComments() {
        if (isBound(CommentsSize.class))
            mReference.unbindFromComments(getBinder(CommentsSize.class));
    }

    public void bindToNumImages() {
        if (!isBound(ImagesSize.class)) mReference.bindToImages(addBinder(new ImagesSize()));
    }

    public void unbindFromNumImages() {
        if (isBound(ImagesSize.class)) mReference.unbindFromImages(getBinder(ImagesSize.class));
    }

    public void bindToNumLocations() {
        if (!isBound(LocationsSize.class))
            mReference.bindToLocations(addBinder(new LocationsSize()));
    }

    public void unbindFromNumLocations() {
        if (isBound(LocationsSize.class))
            mReference.unbindFromLocations(getBinder(LocationsSize.class));
    }

    public void bindToNumFacts() {
        if (!isBound(FactsSize.class)) mReference.bindToFacts(addBinder(new FactsSize()));
    }

    public void unbindFromNumFacts() {
        if (isBound(FactsSize.class)) mReference.unbindFromFacts(getBinder(FactsSize.class));
    }

    public void bindToNumReviews() {
        if (!isBound(ReviewsSize.class)) mReference.bindToReviews(addBinder(new ReviewsSize()));
    }

    public void unbindFromNumReviews() {
        if (isBound(ReviewsSize.class)) mReference.unbindFromReviews(getBinder(ReviewsSize.class));
    }

    public void bindToNumAuthors() {
        if (!isBound(AuthorsSize.class)) mReference.bindToAuthors(addBinder(new AuthorsSize()));
    }

    public void unbindFromNumAuthors() {
        if (isBound(AuthorsSize.class)) mReference.unbindFromAuthors(getBinder(AuthorsSize.class));
    }

    public void bindToNumSubjects() {
        if (!isBound(SubjectsSize.class)) mReference.bindToSubjects(addBinder(new SubjectsSize()));
    }

    public void unbindFromNumSubjects() {
        if (isBound(SubjectsSize.class))
            mReference.unbindFromSubjects(getBinder(SubjectsSize.class));
    }

    public void bindToNumDates() {
        if (!isBound(DatesSize.class)) mReference.bindToDates(addBinder(new DatesSize()));
    }

    public void unbindFromNumDates() {
        if (isBound(DatesSize.class)) mReference.unbindFromDates(getBinder(DatesSize.class));
    }

    @Override
    public DataSubject getSubject() {
        return mReference.getSubject();
    }

    @Override
    public DataRating getRating() {
        return mReference.getRating();
    }

    @Override
    public DataDate getPublishDate() {
        return mReference.getPublishDate();
    }

    @Override
    public DataAuthorId getAuthorId() {
        return mReference.getAuthorId();
    }

    @Override
    public ReviewId getReviewId() {
        return mReference.getReviewId();
    }

    private <T extends ValueBinder> boolean toBind(Class<T> binderClass) {
        return mDataBinder != null && !isBound(binderClass);
    }

    private <T extends ValueBinder> T getBinder(Class<T> binderClass) {
        return (T) mBinders.get(binderClass);
    }

    private <T extends ValueBinder> boolean isBound(Class<T> binderClass) {
        return mBinders.get(binderClass) != null;
    }

    private <T extends ValueBinder> T addBinder(T binder) {
        mBinders.put(binder.getClass(), binder);
        return binder;
    }

    private class Cover implements ReferenceBinders.CoverBinder {
        @Override
        public void onValue(DataImage value) {
            if (mDataBinder != null) mDataBinder.onCover(value, OK);
        }
    }

    private class Tags implements MetaBinders.TagsBinder {
        @Override
        public void onValue(final IdableList<? extends DataTag> value) {
            if (mDataBinder != null) mDataBinder.onTags(value, OK);
        }
    }

    private class Criteria implements MetaBinders.CriteriaBinder {
        @Override
        public void onValue(final IdableList<? extends DataCriterion> value) {
            if (mDataBinder != null) mDataBinder.onCriteria(value, OK);
        }
    }

    private class Images implements MetaBinders.ImagesBinder {
        @Override
        public void onValue(final IdableList<? extends DataImage> value) {
            if (mDataBinder != null) mDataBinder.onImages(value, OK);
        }
    }

    private class Comments implements MetaBinders.CommentsBinder {
        @Override
        public void onValue(final IdableList<? extends DataComment> value) {
            if (mDataBinder != null) mDataBinder.onComments(value, OK);
        }
    }

    private class Locations implements MetaBinders.LocationsBinder {
        @Override
        public void onValue(final IdableList<? extends DataLocation> value) {
            if (mDataBinder != null) mDataBinder.onLocations(value, OK);
        }
    }

    private class Facts implements MetaBinders.FactsBinder {
        @Override
        public void onValue(final IdableList<? extends DataFact> value) {
            if (mDataBinder != null) mDataBinder.onFacts(value, OK);
        }
    }

    private class TagsSize implements MetaBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            mSizeBinder.onNumTags(size, OK);
        }
    }

    private class CriteriaSize implements MetaBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            mSizeBinder.onNumCriteria(size, OK);
        }
    }

    private class ImagesSize implements MetaBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            mSizeBinder.onNumImages(size, OK);
        }
    }

    private class CommentsSize implements MetaBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            mSizeBinder.onNumComments(size, OK);
        }
    }

    private class LocationsSize implements MetaBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            mSizeBinder.onNumLocations(size, OK);
        }
    }

    private class FactsSize implements MetaBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            mSizeBinder.onNumFacts(size, OK);
        }
    }

    private class Reviews implements MetaBinders.ReviewsBinder {
        @Override
        public void onValue(final IdableList<ReviewReference> value) {
            if (mDataBinder != null) mDataBinder.onReviews(value, OK);
        }
    }

    private class Authors implements MetaBinders.AuthorsBinder {
        @Override
        public void onValue(final IdableList<? extends DataAuthorId> value) {
            if (mDataBinder != null) mDataBinder.onAuthors(value, OK);
        }
    }

    private class Subjects implements MetaBinders.SubjectsBinder {
        @Override
        public void onValue(final IdableList<? extends DataSubject> value) {
            if (mDataBinder != null) mDataBinder.onSubjects(value, OK);
        }
    }

    private class Dates implements MetaBinders.DatesBinder {
        @Override
        public void onValue(final IdableList<? extends DataDate> value) {
            if (mDataBinder != null) mDataBinder.onDates(value, OK);
        }
    }

    private class ReviewsSize implements ReferenceBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            mSizeBinder.onNumReviews(size, OK);
        }
    }

    private class AuthorsSize implements ReferenceBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            mSizeBinder.onNumAuthors(size, OK);
        }
    }

    private class SubjectsSize implements ReferenceBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            mSizeBinder.onNumSubjects(size, OK);
        }
    }

    private class DatesSize implements ReferenceBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            mSizeBinder.onNumDates(size, OK);
        }
    }
}
