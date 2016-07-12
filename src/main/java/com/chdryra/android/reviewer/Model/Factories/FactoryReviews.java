/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumRating;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.NullAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdAuthor;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdComment;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdCriterion;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdDate;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdFact;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdImage;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdLocation;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdRating;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdSubject;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.NullNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewInfo;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewMetaSnapshot;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewReferenceWrapper;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewUser;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewMaker;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.AuthorsStamp;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewTreeRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for creating Reviews and ReviewNodes.
 * <p/>
 * <p>
 * A convenient place to
 * put constructors so as to minimise the use of constructors in multiple places.
 * </p>
 */
public class FactoryReviews implements ReviewMaker {
    private static Review sNullReview;
    private AuthorsStamp mAuthorsStamp;
    private FactoryReviewNode mNodeFactory;
    private ConverterMd mConverter;
    private DataValidator mValidator;

    public FactoryReviews(ConverterMd converter,
                          DataValidator validator,
                          FactoryBinders binderFactory,
                          FactoryVisitorReviewNode visitorFactory,
                          FactoryNodeTraverser traverserFactory) {
        mNodeFactory = new FactoryReviewNode(this, binderFactory, visitorFactory, traverserFactory);
        mConverter = converter;
        mValidator = validator;
        mAuthorsStamp = new AuthorsStamp(NullAuthor.AUTHOR);
    }

    public FactoryReviewNode getNodeFactory() {
        return mNodeFactory;
    }

    @Nullable
    public DataAuthor getAuthor() {
        return mAuthorsStamp != null ? mAuthorsStamp.getAuthor() : null;
    }

    public Review getNullReview() {
        if (sNullReview == null) sNullReview = createUserReview("NULL_REVIEW", 0f);
        return sNullReview;
    }

    public ReviewNode getNullNode() {
        return new NullNode();
    }

    public void setAuthorsStamp(AuthorsStamp authorsStamp) {
        mAuthorsStamp = authorsStamp;
    }

    public Review createUserReview(String subject, float rating,
                                   Iterable<? extends DataCriterion> criteria,
                                   Iterable<? extends DataComment> comments,
                                   Iterable<? extends DataImage> images,
                                   Iterable<? extends DataFact> facts,
                                   Iterable<? extends DataLocation> locations,
                                   boolean ratingIsAverage) {
        return newReviewUser(subject, rating, criteria, comments,
                images, facts, locations, ratingIsAverage);
    }

    public ReviewNodeComponent createMetaTree(ReviewReference review) {
        return mNodeFactory.createMetaTree(review);
    }

    public ReviewNodeComponent createMetaTree(Iterable<ReviewReference> reviews, String subject) {
        return mNodeFactory.createMetaTree(createUserReview(subject, 0f), reviews);
    }

    public ReviewNodeComponent createLeafNode(ReviewReference reference) {
        return mNodeFactory.createLeafNode(reference);
    }

    public Review createMetaReview(DataReviewInfo info, List<Review> reviews) {
        return new ReviewMetaSnapshot(info, reviews);
    }

    public ReviewTreeRepo createMetaReview(ReferencesRepository repo, String title) {
        ReviewStamp stamp = newStamp();
        DataReviewInfo info = new ReviewInfo(stamp, new DatumSubject(stamp, title),
                new DatumRating(stamp, 0f, 1), new DatumAuthorReview(stamp, stamp.getAuthor()),
                new DatumDateReview(stamp, stamp.getDate().getTime()));
        return new ReviewTreeRepo(info, repo, getNodeFactory());
    }

    public ReviewReference asReference(Review review, TagsManager manager) {
        return new ReviewReferenceWrapper(review, manager, this, getNodeFactory().getBinderFactory());
    }

    @Override
    public Review makeReview(ReviewDataHolder reviewData) {
        if (!reviewData.isValid(mValidator)) return sNullReview;

        return newReviewUser(new MdReviewId(reviewData.getReviewId()), reviewData.getAuthor(),
                reviewData.getPublishDate(), reviewData.getSubject(), reviewData.getRating(),
                reviewData.getCriteria(), reviewData.getComments(), reviewData.getImages(),
                reviewData.getFacts(), reviewData.getLocations());
    }

    /********************************************************/
    //private methods
    private Review createUserReview(String subject, float rating) {
        return newReviewUser(subject, rating,
                new ArrayList<MdCriterion>(),
                new ArrayList<MdComment>(),
                new ArrayList<MdImage>(),
                new ArrayList<MdFact>(),
                new ArrayList<MdLocation>(), false);
    }

    private Review newReviewUser(String subject, float rating,
                                 Iterable<? extends DataCriterion> criteria,
                                 Iterable<? extends DataComment> comments,
                                 Iterable<? extends DataImage> images,
                                 Iterable<? extends DataFact> facts,
                                 Iterable<? extends DataLocation> locations,
                                 boolean ratingIsAverage) {
        if (mAuthorsStamp == null) throw new IllegalStateException("No author stamp!");

        ReviewStamp stamp = newStamp();
        DataAuthor author = stamp.getAuthor();
        DataDate date = stamp.getDate();

        MdReviewId id = new MdReviewId(stamp);

        if (ratingIsAverage) rating = getAverageRating(criteria);

        return newReviewUser(id, author, date, subject, rating,
                criteria,
                comments,
                images,
                facts,
                locations);
    }

    private ReviewStamp newStamp() {
        return mAuthorsStamp.newStamp();
    }

    private float getAverageRating(Iterable<? extends DataCriterion> criteria) {
        float rating;
        double average = 0;
        int size = 0;
        for (DataCriterion criterion : criteria) {
            average += criterion.getRating();
            size++;
        }
        rating = (float) average / (float) size;
        return rating;
    }

    private Review newReviewUser(MdReviewId id,
                                 DataAuthor author,
                                 DataDate publishDate,
                                 String subject,
                                 float rating,
                                 Iterable<? extends DataCriterion> criteria,
                                 Iterable<? extends DataComment> comments,
                                 Iterable<? extends DataImage> images,
                                 Iterable<? extends DataFact> facts,
                                 Iterable<? extends DataLocation> locations) {
        MdAuthor mdAuthor = new MdAuthor(id, author.getName(), author.getAuthorId());
        MdDate mdDate = new MdDate(id, publishDate.getTime());
        MdSubject mdSubject = new MdSubject(id, subject);
        MdRating mdRating = new MdRating(id, rating, 1);
        MdDataList<MdComment> mdComments = mConverter.toMdCommentList(comments, id);
        MdDataList<MdImage> mdImages = mConverter.toMdImageList(images, id);
        MdDataList<MdFact> mdFacts = mConverter.toMdFactList(facts, id);
        MdDataList<MdLocation> mdLocations = mConverter.toMdLocationList(locations, id);
        MdDataList<MdCriterion> mdCriteria = mConverter.toMdCriterionList(criteria, id);

        return new ReviewUser(id, mdAuthor, mdDate, mdSubject, mdRating, mdComments,
                mdImages, mdFacts, mdLocations, mdCriteria);
    }
}
