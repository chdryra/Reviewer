/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdConverter;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewDataHolderImpl;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewDataHolder;

import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.AuthorsStamp;
import com.chdryra.android.testutils.RandomString;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import test.Model.ReviewsModel.Utils.MdDataMocker;
import test.TestUtils.DataEquivalence;
import test.TestUtils.RandomAuthor;
import test.TestUtils.RandomRating;
import test.TestUtils.RandomReview;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

/**
 * Created by: Rizwan Choudrey
 * On: 04/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsTest {
    private static final int NUM = 3;
    private DataAuthor mAuthor;
    private FactoryReviews mFactory;
    private MdDataMocker mDataMocker;

    @Before
    public void setup() {
        mAuthor = RandomAuthor.nextAuthor();
        ConverterMd converter = new FactoryMdConverter().newMdConverter();
        mFactory = new FactoryReviews(new FactoryReviewNode(), converter, new DataValidator());
        mFactory.setAuthorsStamp(new AuthorsStamp(mAuthor));
        mDataMocker = new MdDataMocker();
    }

    @Test
    public void getAuthorReturnsExpectedAuthor() {
        assertThat(mFactory.getAuthor(), is(mAuthor));
    }

    @Test
    public void createUserReviewWithRatingNotAverageOfCriteriaCreatesReviewWithExpectedData() {
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();
        Iterable<? extends DataComment> comments = mDataMocker.newCommentList(NUM);
        Iterable<? extends DataImage> images = new ArrayList<>(); //Android can't mock bitmaps
        Iterable<? extends DataFact> facts = mDataMocker.newFactList(NUM);
        Iterable<? extends DataLocation> locations = mDataMocker.newLocationList(NUM);
        Iterable<? extends DataCriterion> criteria = mDataMocker.newCriterionList(NUM);

        Review review = mFactory.createUserReview(subject,
                rating, criteria, comments, images, facts, locations, false);

        checkRating(rating, review);

        checkSubject(subject, review);
        checkAuthor(mAuthor, review);
        checkPublishDate(review);
        checkTreeRepresentationIsSingleNode(review);

        checkCriteria(criteria, review);
        checkComments(comments, review);
        checkFacts(facts, review);
        checkImages(images, review);
        checkLocations(locations, review);
    }

    @Test
    public void createUserReviewWithRatingAverageOfCriteriaCreatesReviewWithExpectedData() {
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();
        Iterable<? extends DataComment> comments = mDataMocker.newCommentList(NUM);
        Iterable<? extends DataImage> images = new ArrayList<>(); //Android can't mock bitmaps
        Iterable<? extends DataFact> facts = mDataMocker.newFactList(NUM);
        Iterable<? extends DataLocation> locations = mDataMocker.newLocationList(NUM);
        Iterable<? extends DataCriterion> criteria = mDataMocker.newCriterionList(NUM);
        float averageRating = 0f;
        for(DataCriterion criterion : criteria) {
            averageRating += criterion.getRating() / NUM;
        }

        Review review = mFactory.createUserReview(subject,
                rating, criteria, comments, images, facts, locations, true);

        assertThat(review.isRatingAverageOfCriteria(), is(true));
        assertThat(review.getRating().getReviewId(), is(review.getReviewId()));
        assertThat((double) review.getRating().getRating(), closeTo(averageRating, 0.001));

        checkSubject(subject, review);
        checkAuthor(mAuthor, review);
        checkPublishDate(review);
        checkTreeRepresentationIsSingleNode(review);

        checkCriteria(criteria, review);
        checkComments(comments, review);
        checkFacts(facts, review);
        checkImages(images, review);
        checkLocations(locations, review);
    }

    @Test
    public void createMetaReviewOfOneReviewIsNodeWithOneChildWrappingReview() {
        Review review = RandomReview.nextReview();

        ReviewNode meta = mFactory.createMetaTree(review);

        assertThat(meta.getReviewId(), not(review.getReviewId()));
        assertThat(meta.getParent(), is(nullValue()));
        assertThat(meta.getChildren().size(), is(1));
        assertThat(meta.isRatingAverageOfChildren(), is(true));
        checkAuthor(mAuthor, meta);
        checkNodeAgainstReview(review, meta);
        
        ReviewNode child = meta.getChildren().getItem(0);
        assertThat(child.getReviewId(), is(review.getReviewId()));
        assertThat(child.getReference(), is(review));
        assertThat(child.getChildren().size(), is(0));
        checkNodeAgainstReview(review, child);
    }

    @Test
    public void createMetaReviewWithMultipleReviewsIsNodeWithMultipleChildrenWrappingEachReview() {
        ArrayList<Review> reviews = new ArrayList<>();
        float averageRating = 0f;
        for(int i = 0; i < NUM; ++i) {
            Review review = RandomReview.nextReview();
            reviews.add(review);
            averageRating += review.getRating().getRating() / NUM;
        }

        String subject = RandomString.nextWord();
        ReviewNode meta = mFactory.createMetaTree(reviews, subject);

        checkAuthor(mAuthor, meta);
        checkSubject(subject, meta);
        checkPublishDate(meta);
        assertThat(meta.isRatingAverageOfChildren(), is(true));
        assertThat((double)meta.getRating().getRating(), closeTo(averageRating, 0.001));
        assertThat(meta.getParent(), is(nullValue()));
        assertThat(meta.getChildren().size(), is(reviews.size()));

        for(int i = 0; i < reviews.size(); ++i) {
            Review review = reviews.get(i);
            ReviewNode child = meta.getChildren().getItem(i);
            assertThat(child.getReviewId(), is(review.getReviewId()));
            assertThat(child.getReference(), is(review));
            assertThat(child.getChildren().size(), is(0));
            checkNodeAgainstReview(review, child);
        }
    }

    @Test
    public void createMetaReviewMutableWithMultipleReviewsIsNodeWithMultipleChildrenWrappingEachReview() {
        ArrayList<Review> reviews = new ArrayList<>();
        float averageRating = 0f;
        for(int i = 0; i < NUM; ++i) {
            Review review = RandomReview.nextReview();
            reviews.add(review);
            averageRating += review.getRating().getRating() / NUM;
        }

        String subject = RandomString.nextWord();
        ReviewNodeComponent meta = mFactory.createMetaReferenceMutable(reviews, subject);

        checkAuthor(mAuthor, meta);
        checkSubject(subject, meta);
        checkPublishDate(meta);
        assertThat(meta.isRatingAverageOfChildren(), is(true));
        assertThat((double)meta.getRating().getRating(), closeTo(averageRating, 0.001));
        assertThat(meta.getParent(), is(nullValue()));
        assertThat(meta.getChildren().size(), is(reviews.size()));

        for(int i = 0; i < reviews.size(); ++i) {
            Review review = reviews.get(i);
            ReviewNode child = meta.getChildren().getItem(i);
            assertThat(child.getReviewId(), is(review.getReviewId()));
            assertThat(child.getReference(), is(review));
            assertThat(child.getChildren().size(), is(0));
            checkNodeAgainstReview(review, child);
        }
    }

    @Test
    public void createReviewNodeComponentNotAverageOfChildrenHasExpectedAttributes() {
        Review review = RandomReview.nextReview();

        ReviewNodeComponent node = mFactory.createReviewNodeComponent(review, false);

        assertThat(node.getReviewId(), is(review.getReviewId()));
        assertThat(node.isRatingAverageOfChildren(), is(false));
        assertThat(node.getParent(), is(nullValue()));
        assertThat(node.getChildren().size(), is(0));

        checkAuthor(review.getAuthor(), node);
        checkNodeAgainstReview(review, node);

        Review review2 = RandomReview.nextReview();
        ReviewNodeComponent child = mFactory.createReviewNodeComponent(review2, false);
        node.addChild(child);
        assertThat(node.getChildren().size(), is(1));
        checkRating(review.getRating().getRating(), node);
    }

    @Test
    public void createReviewNodeComponentAverageOfChildrenHasRatingDependentOnChildren() {
        Review review = RandomReview.nextReview();

        ReviewNodeComponent node = mFactory.createReviewNodeComponent(review, true);

        assertThat(node.getReviewId(), is(review.getReviewId()));
        assertThat(node.isRatingAverageOfChildren(), is(true));
        assertThat(node.getParent(), is(nullValue()));
        assertThat(node.getChildren().size(), is(0));

        checkRating(0f, node);

        Review review2 = RandomReview.nextReview();
        ReviewNodeComponent child = mFactory.createReviewNodeComponent(review2, false);
        node.addChild(child);
        assertThat(node.getChildren().size(), is(1));
        checkRating(review2.getRating().getRating(), node);
    }

    @Test
    public void recreateReview() {
        Review review = RandomReview.nextReview();

        ReviewDataHolder holder = new ReviewDataHolderImpl(review.getReviewId(), review.getAuthor(),
                review.getPublishDate(), review.getSubject().getSubject(),
                review.getRating().getRating(), review.getRating().getRatingWeight(),
                review.getComments(), review.getImages(),
                review.getFacts(), review.getLocations(), review.getCriteria(),
                review.isRatingAverageOfCriteria());

        Review recreation = mFactory.makeReview(holder);

        assertThat(recreation.getReviewId(), is(review.getReviewId()));
        assertThat(recreation.isRatingAverageOfCriteria(), is(review.isRatingAverageOfCriteria()));
        checkAuthor(review.getAuthor(), recreation);
        checkPublishDate(review.getPublishDate(), recreation);
        checkSubject(review.getSubject(), recreation);
        checkRating(review.getRating(), recreation);
        checkComments(review.getComments(), recreation);
        checkCriteria(review.getCriteria(), recreation);
        checkFacts(review.getFacts(), recreation);
        checkImages(review.getImages(), recreation);
        checkLocations(review.getLocations(), recreation);
        checkTreeRepresentationIsSingleNode(recreation);
    }

    private void checkNodeAgainstReview(Review review, ReviewNode node) {
        checkSubject(review.getSubject().getSubject(), node);
        checkRating(review.getRating().getRating(), node);
        checkPublishDate(node);
        assertThat(node.getTreeRepresentation(), is(node));

        checkCriteria(review.getCriteria(), node, review.getReviewId());
        checkComments(review.getComments(), node, review.getReviewId());
        checkFacts(review.getFacts(), node, review.getReviewId());
        checkImages(review.getImages(), node, review.getReviewId());
        checkLocations(review.getLocations(), node, review.getReviewId());
    }

    private void checkLocations(Iterable<? extends DataLocation> locations, Review review) {
        checkLocations(locations, review, review.getReviewId());
    }

    private void checkLocations(Iterable<? extends DataLocation> locations, Review review, ReviewId reviewId) {
        IdableList<? extends DataLocation> reviewLocations = review.getLocations();
        assertThat(reviewLocations.getReviewId(), is(review.getReviewId()));
        int i = 0;
        for(DataLocation location : locations) {
            assertThat(reviewLocations.size(), greaterThan(i));
            DataEquivalence.checkEquivalence(location, reviewLocations.getItem(i++), reviewId);
        }
        assertThat(reviewLocations.size(), is(i));
    }
    private void checkImages(Iterable<? extends DataImage> images, Review review) {
        checkImages(images, review, review.getReviewId());
    }

    private void checkImages(Iterable<? extends DataImage> images, Review review, ReviewId reviewId) {
        IdableList<? extends DataImage> reviewImages = review.getImages();
        assertThat(reviewImages.getReviewId(), is(review.getReviewId()));
        int i = 0;
        for(DataImage image : images) {
            assertThat(reviewImages.size(), greaterThan(i));
            DataEquivalence.checkEquivalence(image, reviewImages.getItem(i++), reviewId);
        }
        assertThat(reviewImages.size(), is(i));
    }

    private void checkFacts(Iterable<? extends DataFact> facts, Review review) {
        checkFacts(facts, review, review.getReviewId());
    }

    private void checkFacts(Iterable<? extends DataFact> facts, Review review, ReviewId reviewId) {
        IdableList<? extends DataFact> reviewFacts = review.getFacts();
        assertThat(reviewFacts.getReviewId(), is(review.getReviewId()));
        int i = 0;
        for(DataFact fact : facts) {
            assertThat(reviewFacts.size(), greaterThan(i));
            DataEquivalence.checkEquivalence(fact, reviewFacts.getItem(i++), reviewId);
        }
        assertThat(reviewFacts.size(), is(i));
    }

    private void checkComments(Iterable<? extends DataComment> comments, Review review) {
        checkComments(comments, review, review.getReviewId());
    }

    private void checkComments(Iterable<? extends DataComment> comments, Review review, ReviewId reviewId) {
        IdableList<? extends DataComment> reviewComments = review.getComments();
        assertThat(reviewComments.getReviewId(), is(review.getReviewId()));
        int i = 0;
        for(DataComment comment : comments) {
            assertThat(reviewComments.size(), greaterThan(i));
            DataEquivalence.checkEquivalence(comment, reviewComments.getItem(i++), reviewId);
        }
        assertThat(reviewComments.size(), is(i));
    }

    private void checkCriteria(Iterable<? extends DataCriterion> criteria, Review review) {
        checkCriteria(criteria, review, review.getReviewId());
    }

    private void checkCriteria(Iterable<? extends DataCriterion> criteria, Review review, ReviewId reviewId) {
        IdableList<? extends DataCriterion> reviewCriteria = review.getCriteria();
        assertThat(reviewCriteria.getReviewId(), is(review.getReviewId()));
        int i = 0;
        for(DataCriterion criterion : criteria) {
            assertThat(reviewCriteria.size(), greaterThan(i));
            DataEquivalence.checkEquivalence(criterion, reviewCriteria.getItem(i++), reviewId);
        }
        assertThat(reviewCriteria.size(), is(i));
    }
    
    private void checkCriteria(ArrayList<Review> criteria, Review review) {
        IdableList<? extends DataCriterion> reviewCriteria = review.getCriteria();
        assertThat(reviewCriteria.getReviewId(), is(review.getReviewId()));
        assertThat(reviewCriteria.size(), is(criteria.size()));
        for(int i = 0; i < criteria.size(); ++i) {
            DataEquivalence.checkEquivalence(review.getReviewId(), criteria.get(i),
                    reviewCriteria.getItem(i));
        }
    }

    private void checkTreeRepresentationIsSingleNode(Review review) {
        ReviewNode node = review.getTreeRepresentation();
        assertThat(node, not(nullValue()));
        assertThat(node.getReviewId(), is(review.getReviewId()));
        assertThat(node.getReference(), is(review));
        assertThat(node.getParent(), is(Matchers.nullValue()));
        assertThat(node.getChildren().size(), is(0));
    }

    private void checkPublishDate(DataDateReview date, Review review) {
        assertThat(review.getPublishDate(), is(date));
    }

    private void checkPublishDate(Review review) {
        assertThat(review.getPublishDate().getReviewId(), is(review.getReviewId()));
        assertThat(review.getPublishDate().getTime(), lessThanOrEqualTo(new Date().getTime()));
    }

    private void checkAuthor(DataAuthor author, Review review) {
        assertThat(review.getAuthor().getReviewId(), is(review.getReviewId()));
        assertThat(review.getAuthor().getName(), is(author.getName()));
        assertThat(review.getAuthor().getAuthorId(), is(author.getAuthorId()));
    }

    private void checkRating(float rating, Review review) {
        assertThat(review.getRating().getRating(), is(rating));
        assertThat(review.getRating().getReviewId(), is(review.getReviewId()));
    }

    private void checkRating(DataRating rating, Review review) {
        assertThat(review.getRating(), is(rating));
    }

    private void checkSubject(DataSubject subject, Review review) {
        assertThat(review.getSubject(), is(subject));
    }

    private void checkSubject(String subject, Review review) {
        assertThat(review.getSubject().getSubject(), is(subject));
        assertThat(review.getSubject().getReviewId(), is(review.getReviewId()));
    }
}
