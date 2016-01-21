package test.Plugins.ReviewerDb;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.RowEntry;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb
        .Implementation.ColumnInfo;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.RowReviewImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowReview;
import com.chdryra.android.testutils.RandomString;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import test.TestUtils.RandomReview;
import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowReviewImplTest {
    private static final int NUM = 10;
    @Rule
    public ExpectedException mExpectedException = ExpectedException.none();

    @Test
    public void constructionWithRowValuesAndGetters() {
        RowReview reference = getRowToIterateOver();
        String parentId = RandomString.nextWord();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowReview.PARENT_ID, parentId);
        values.put(RowReview.USER_ID, reference.getAuthorId());
        values.put(RowReview.PUBLISH_DATE, reference.getPublishDate());
        values.put(RowReview.SUBJECT, reference.getSubject());
        values.put(RowReview.RATING, reference.getRating());
        values.put(RowReview.RATING_WEIGHT, reference.getRatingWeight());
        values.put(RowReview.IS_AVERAGE, reference.isRatingIsAverage());

        RowReviewImpl row = new RowReviewImpl(values);

        assertThat(row.hasData(new DataValidator()), is(true));

        assertThat(row.getReviewId(), is(reference.getReviewId()));
        assertThat(row.getParentId(), is(parentId));
        assertThat(row.getAuthorId(), is(reference.getAuthorId()));
        assertThat(row.getPublishDate(), is(reference.getPublishDate()));
        assertThat(row.getSubject(), is(reference.getSubject()));
        assertThat(row.getRating(), is(reference.getRating()));
        assertThat(row.getRatingWeight(), is(reference.getRatingWeight()));
        assertThat(row.isRatingIsAverage(), is(reference.isRatingIsAverage()));
    }

    @Test
    public void constructionWithCriterionAndGetters() {
        Review reference = RandomReview.nextReview();
        ReviewId parentId = RandomReviewId.nextReviewId();
        DatumCriterionReview criterion = new DatumCriterionReview(parentId, reference);

        RowReviewImpl row = new RowReviewImpl(criterion);

        assertThat(row.hasData(new DataValidator()), is(true));
        assertThat(row.getReviewId(), is(reference.getReviewId()));
        assertThat(row.getParentId(), is(parentId.toString()));
        assertThat(row.getAuthorId(), is(reference.getAuthor().getUserId().toString()));
        assertThat(row.getPublishDate(), is(reference.getPublishDate().getTime()));
        assertThat(row.getSubject(), is(reference.getSubject().getSubject()));
        assertThat(row.getRating(), is(reference.getRating().getRating()));
        assertThat(row.getRatingWeight(), is(reference.getRating().getRatingWeight()));
        assertThat(row.isRatingIsAverage(), is(reference.isRatingAverageOfCriteria()));
    }

    @Test
    public void constructionWithReviewAndGetters() {
        Review review = RandomReview.nextReview();

        RowReviewImpl row = new RowReviewImpl(review);

        assertThat(row.hasData(new DataValidator()), is(true));
        assertThat(row.getReviewId(), is(review.getReviewId()));
        assertThat(row.getParentId(), is(nullValue()));
        assertThat(row.getAuthorId(), is(review.getAuthor().getUserId().toString()));
        assertThat(row.getPublishDate(), is(review.getPublishDate().getTime()));
        assertThat(row.getSubject(), is(review.getSubject().getSubject()));
        assertThat(row.getRating(), is(review.getRating().getRating()));
        assertThat(row.getRatingWeight(), is(review.getRating().getRatingWeight()));
        assertThat(row.isRatingIsAverage(), is(review.isRatingAverageOfCriteria()));
    }

    @Test
    public void constructionWithInvalidReviewIdMakesRowReviewInvalid() {
        RowReview reference = getRowToIterateOver();
        String parentId = RandomString.nextWord();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, "");
        values.put(RowReview.PARENT_ID, parentId);
        values.put(RowReview.USER_ID, reference.getAuthorId());
        values.put(RowReview.PUBLISH_DATE, reference.getPublishDate());
        values.put(RowReview.SUBJECT, reference.getSubject());
        values.put(RowReview.RATING, reference.getRating());
        values.put(RowReview.RATING_WEIGHT, reference.getRatingWeight());
        values.put(RowReview.IS_AVERAGE, reference.isRatingIsAverage());

        RowReviewImpl row = new RowReviewImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithInvalidAuthorIdMakesRowReviewInvalid() {
        RowReview reference = getRowToIterateOver();
        String parentId = RandomString.nextWord();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowReview.PARENT_ID, parentId);
        values.put(RowReview.USER_ID, "");
        values.put(RowReview.PUBLISH_DATE, reference.getPublishDate());
        values.put(RowReview.SUBJECT, reference.getSubject());
        values.put(RowReview.RATING, reference.getRating());
        values.put(RowReview.RATING_WEIGHT, reference.getRatingWeight());
        values.put(RowReview.IS_AVERAGE, reference.isRatingIsAverage());

        RowReviewImpl row = new RowReviewImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithInvalidSubjectMakesRowReviewInvalid() {
        RowReview reference = getRowToIterateOver();
        String parentId = RandomString.nextWord();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowReview.PARENT_ID, parentId);
        values.put(RowReview.USER_ID, reference.getAuthorId());
        values.put(RowReview.PUBLISH_DATE, reference.getPublishDate());
        values.put(RowReview.SUBJECT, "");
        values.put(RowReview.RATING, reference.getRating());
        values.put(RowReview.RATING_WEIGHT, reference.getRatingWeight());
        values.put(RowReview.IS_AVERAGE, reference.isRatingIsAverage());

        RowReviewImpl row = new RowReviewImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithInvalidRatingWeightMakesRowReviewInvalid() {
        RowReview reference = getRowToIterateOver();
        String parentId = RandomString.nextWord();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowReview.PARENT_ID, parentId);
        values.put(RowReview.USER_ID, reference.getAuthorId());
        values.put(RowReview.PUBLISH_DATE, reference.getPublishDate());
        values.put(RowReview.SUBJECT, reference.getSubject());
        values.put(RowReview.RATING, reference.getRating());
        values.put(RowReview.RATING_WEIGHT, 0);
        values.put(RowReview.IS_AVERAGE, reference.isRatingIsAverage());

        RowReviewImpl row = new RowReviewImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void getRowIdColumnNameReturnsReviewIdColumnName() {
        Review review = RandomReview.nextReview();
        RowReviewImpl row = new RowReviewImpl(review);
        assertThat(row.getRowIdColumnName(), is(RowReview.REVIEW_ID.getName()));
    }

    @Test
    public void iteratorIsSize8() {
        RowReviewImpl row = getRowToIterateOver();
        Iterator<RowEntry<?>> it = row.iterator();
        int i = 0;
        while (it.hasNext()) {
            it.next();
            ++i;
        }
        assertThat(i, is(8));
    }

    @Test
    public void iteratorThrowsNoElementExceptionAfterTooManyNexts() {
        mExpectedException.expect(NoSuchElementException.class);
        RowReviewImpl row = getRowToIterateOver();
        Iterator<RowEntry<?>> it = row.iterator();
        while (it.hasNext()) it.next();
        it.next();
    }

    @Test
    public void iteratorThrowsUnsupportedOperationExceptionOnRemove() {
        mExpectedException.expect(UnsupportedOperationException.class);
        RowReviewImpl row = getRowToIterateOver();
        Iterator<RowEntry<?>> it = row.iterator();
        it.remove();
    }

    @Test
    public void iteratorReturnsDataInOrder() {
        RowReviewImpl row = getRowToIterateOver();

        ArrayList<RowEntry<?>> entries = new ArrayList<>();
        for (RowEntry<?> entry : row) {
            entries.add(entry);
        }

        assertThat(entries.size(), is(8));

        checkEntry(entries.get(0), RowReview.REVIEW_ID, row.getReviewId().toString());
        checkEntry(entries.get(1), RowReview.PARENT_ID, row.getParentId());
        checkEntry(entries.get(2), RowReview.USER_ID, row.getAuthorId());
        checkEntry(entries.get(3), RowReview.PUBLISH_DATE, row.getPublishDate());
        checkEntry(entries.get(4), RowReview.SUBJECT, row.getSubject());
        checkEntry(entries.get(5), RowReview.RATING, row.getRating());
        checkEntry(entries.get(6), RowReview.RATING_WEIGHT, row.getRatingWeight());
        checkEntry(entries.get(7), RowReview.IS_AVERAGE, row.isRatingIsAverage());
    }

    private <T> void checkEntry(RowEntry<?> entry, ColumnInfo<T> column, T value) {
        assertThat(entry.getColumnName(), is(column.getName()));
        assertThat(entry.getEntryType().equals(column.getType()), is(true));
        assertThat((T) entry.getValue(), is(value));
    }

    @NonNull
    private RowReviewImpl getRowToIterateOver() {
        Review reference = RandomReview.nextReview();
        ReviewId parentId = RandomReviewId.nextReviewId();

        return new RowReviewImpl(new DatumCriterionReview(parentId, reference));
    }
}
