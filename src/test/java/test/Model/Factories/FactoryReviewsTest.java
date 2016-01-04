package test.Model.Factories;

import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Factories.FactoryMdConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewPublisher;
import com.chdryra.android.testutils.RandomString;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import test.TestUtils.DataEquivalence;
import test.Model.ReviewsModel.Utils.MdDataMocker;
import test.TestUtils.RandomAuthor;
import test.TestUtils.RandomRating;
import test.TestUtils.RandomReview;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;
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
        mFactory = new FactoryReviews(new FactoryReviewPublisher(mAuthor), new FactoryReviewNode(),
                converter);
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
        ArrayList<Review> criteria = new ArrayList<>();
        for(int i = 0; i < NUM; ++i) {
            criteria.add(RandomReview.nextReview());
        }

        Review review = mFactory.createUserReview(subject,
                rating, comments, images, facts, locations, criteria, false);

        checkSubject(subject, review);
        checkRating(rating, review);
        checkAuthor(review);
        checkPublishDate(review);
        checkTreeRepresentation(review);

        assertThat(review.isRatingAverageOfCriteria(), is(false));

        checkCriteria(criteria, review);
        checkComments(comments, review);
        checkFacts(facts, review);
        checkImages(images, review);
        checkLocations(locations, review);
    }

    private void checkLocations(Iterable<? extends DataLocation> locations, Review review) {
        IdableList<? extends DataLocation> reviewLocations = review.getLocations();
        assertThat(reviewLocations.getReviewId(), is(review.getReviewId()));
        int i = 0;
        for(DataLocation location : locations) {
            assertThat(reviewLocations.size(), greaterThan(i));
            DataEquivalence.checkEquivalence(location, reviewLocations.getItem(i++));
        }
        assertThat(reviewLocations.size(), is(i));
    }

    private void checkImages(Iterable<? extends DataImage> images, Review review) {
        IdableList<? extends DataImage> reviewImages = review.getImages();
        assertThat(reviewImages.getReviewId(), is(review.getReviewId()));
        int i = 0;
        for(DataImage image : images) {
            assertThat(reviewImages.size(), greaterThan(i));
            DataEquivalence.checkEquivalence(image, reviewImages.getItem(i++));
        }
        assertThat(reviewImages.size(), is(i));
    }

    private void checkFacts(Iterable<? extends DataFact> facts, Review review) {
        IdableList<? extends DataFact> reviewFacts = review.getFacts();
        assertThat(reviewFacts.getReviewId(), is(review.getReviewId()));
        int i = 0;
        for(DataFact fact : facts) {
            assertThat(reviewFacts.size(), greaterThan(i));
            DataEquivalence.checkEquivalence(fact, reviewFacts.getItem(i++));
        }
        assertThat(reviewFacts.size(), is(i));
    }
    
    private void checkComments(Iterable<? extends DataComment> comments, Review review) {
        IdableList<? extends DataComment> reviewComments = review.getComments();
        assertThat(reviewComments.getReviewId(), is(review.getReviewId()));
        int i = 0;
        for(DataComment comment : comments) {
            assertThat(reviewComments.size(), greaterThan(i));
            DataEquivalence.checkEquivalence(comment, reviewComments.getItem(i++));
        }
        assertThat(reviewComments.size(), is(i));
    }

    private void checkCriteria(ArrayList<Review> criteria, Review review) {
        IdableList<? extends DataCriterionReview> reviewCriteria = review.getCriteria();
        assertThat(reviewCriteria.getReviewId(), is(review.getReviewId()));
        assertThat(reviewCriteria.size(), is(criteria.size()));
        for(int i = 0; i < criteria.size(); ++i) {
            DataEquivalence.checkEquivalence(review.getReviewId(), criteria.get(i),
                    reviewCriteria.getItem(i));
        }
    }

    private void checkTreeRepresentation(Review review) {
        ReviewNode node = review.getTreeRepresentation();
        assertThat(node, not(nullValue()));
        assertThat(node.getReviewId(), is(review.getReviewId()));
        assertThat(node.getReview(), is(review));
        assertThat(node.getParent(), is(Matchers.nullValue()));
        assertThat(node.getChildren().size(), is(0));
    }

    private void checkPublishDate(Review review) {
        assertThat(review.getPublishDate().getReviewId(), is(review.getReviewId()));
        assertThat(review.getPublishDate().getTime(), lessThanOrEqualTo(new Date().getTime()));
    }

    private void checkAuthor(Review review) {
        assertThat(review.getAuthor().getReviewId(), is(review.getReviewId()));
        assertThat(review.getAuthor().getName(), is(mAuthor.getName()));
        assertThat(review.getAuthor().getUserId(), is(mAuthor.getUserId()));
    }

    private void checkRating(float rating, Review review) {
        assertThat(review.getRating().getRating(), is(rating));
        assertThat(review.getRating().getReviewId(), is(review.getReviewId()));
    }

    private void checkSubject(String subject, Review review) {
        assertThat(review.getSubject().getSubject(), is(subject));
        assertThat(review.getSubject().getReviewId(), is(review.getReviewId()));
    }
}
