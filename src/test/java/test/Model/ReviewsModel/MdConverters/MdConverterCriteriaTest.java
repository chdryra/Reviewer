package test.Model.ReviewsModel.MdConverters;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCriterion;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters.MdConverterCriteria;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

import org.junit.Test;

import java.util.ArrayList;

import test.TestUtils.RandomReview;
import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 04/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterCriteriaTest extends MdConverterBasicTest<DataCriterionReview, MdCriterion> {
    private static final int NUM_REVIEWS = 3;

    public MdConverterCriteriaTest() {
        super(new MdConverterCriteria());
    }

    @Override
    protected DataCriterionReview newDatum() {
        return new Criterion();
    }

    @Override
    protected void checkDatumEquivalence(DataCriterionReview datum, MdCriterion mdDatum) {
        assertThat(mdDatum.getReviewId().toString(), is(datum.getReviewId().toString()));
        assertThat(mdDatum.getSubject(), is(datum.getSubject()));
        assertThat(mdDatum.getRating(), is(datum.getRating()));
        assertThat(mdDatum.getReview(), is(datum.getReview()));
    }

    @Test
    public void testConvertReviews() {
        ArrayList<Review> reviews = new ArrayList<>();
        for(int i = 0; i < NUM_REVIEWS; ++i) {
            reviews.add(RandomReview.nextReview());
        }
        MdConverterCriteria converter = (MdConverterCriteria) getConverter();
        ReviewId id = RandomReviewId.nextReviewId();
        MdDataList<MdCriterion> criteria = converter.convertReviews(reviews, id);

        assertThat(criteria.size(), is(NUM_REVIEWS));
        for(int i = 0; i < NUM_REVIEWS; ++i) {
            MdCriterion criterion = criteria.getItem(i);
            Review review = reviews.get(i);
            assertThat(criterion.getReviewId().toString(), is(id.toString()));
            assertThat(criterion.getSubject(), is(review.getSubject().getSubject()));
            assertThat(criterion.getRating(), is(review.getRating().getRating()));
            assertThat(criterion.getReview(), is(review));
        }
    }

    private static class Criterion implements DataCriterionReview {
        private Review mReview;

        public Criterion() {
            mReview = RandomReview.nextReview();
        }

        @Override
        public Review getReview() {
            return mReview;
        }

        @Override
        public String getSubject() {
            return mReview.getSubject().getSubject();
        }

        @Override
        public float getRating() {
            return mReview.getRating().getRating();
        }

        @Override
        public ReviewId getReviewId() {
            return mReview.getReviewId();
        }

        @Override
        public boolean hasData(DataValidator validator) {
            return true;
        }
    }
}
