package test.DataAlgorithms;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalAuthor;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Factories.NullData;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumUserId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;

import test.TestUtils.RandomAuthor;
import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalAuthorTest {
    private static final int NUM = 10;
    private CanonicalAuthor mCanonical;
    private String mAuthorName;
    private UserId mUserId;

    @Before
    public void setUp() {
        mCanonical = new CanonicalAuthor(new ComparitorAuthor());
        mAuthorName = RandomString.nextWord();
        mUserId = new DatumUserId(RandomString.nextWord());
    }

    @Test
    public void ifNoDataThenReturnsNullAuthor() {
        ReviewId reviewId = RandomReviewId.nextReviewId();
        IdableList<DataAuthorReview> authors = new IdableDataList<>(reviewId);

        DataAuthorReview canonical = mCanonical.getCanonical(authors);

        assertThat(canonical, is(NullData.nullAuthor(reviewId)));
    }

    @Test
    public void ifMoreThanOneAuthorReferencedInListThenReturnsInvalidAuthor() {
        ReviewId reviewId = RandomReviewId.nextReviewId();
        IdableList<DataAuthorReview> authors = getReferenceAuthors(reviewId);
        authors.add(RandomAuthor.nextAuthorReview());

        DataAuthorReview canonical = mCanonical.getCanonical(authors);

        assertThat(canonical, is(NullData.nullAuthor(reviewId)));
    }

    @Test
    public void ifAllAuthorsTheSameInListThenReturnsAppropriateAuthor() {
        ReviewId reviewId = RandomReviewId.nextReviewId();
        IdableList<DataAuthorReview> authors = getReferenceAuthors(reviewId);

        DataAuthorReview canonical = mCanonical.getCanonical(authors);

        assertThat(canonical.getReviewId().toString(), is(reviewId.toString()));
        assertThat(canonical.getName(), is(mAuthorName));
        assertThat(canonical.getUserId(), is(mUserId));
    }

    @NonNull
    private IdableList<DataAuthorReview> getReferenceAuthors(ReviewId reviewId) {
        IdableList<DataAuthorReview> authors = new IdableDataList<>(reviewId);
        for(int i = 0; i < NUM; ++i) {
            authors.add(getReferenceAuthor());
        }
        return authors;
    }

    private DatumAuthorReview getReferenceAuthor() {
        return new DatumAuthorReview(RandomReviewId.nextReviewId(), mAuthorName, mUserId);
    }
}
