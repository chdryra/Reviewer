/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.LocalReviewerDb;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.RowReviewImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;

import org.junit.Test;

import java.util.ArrayList;

import test.TestUtils.RandomReview;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowReviewImplTest extends RowTableBasicTest<RowReview, RowReviewImpl>{

    public RowReviewImplTest() {
        super(RowReview.REVIEW_ID.getName(), 7);
    }

    @Test
    public void constructionWithRowValuesAndGetters() {
        RowReview reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowReview.AUTHOR_ID, reference.getAuthorId());
        values.put(RowReview.PUBLISH_DATE, reference.getPublishDate());
        values.put(RowReview.SUBJECT, reference.getSubject());
        values.put(RowReview.RATING, reference.getRating());
        values.put(RowReview.RATING_WEIGHT, reference.getRatingWeight());
        values.put(RowReview.IS_AVERAGE, reference.isRatingIsAverage());

        RowReviewImpl row = new RowReviewImpl(values);

        assertThat(row.hasData(new DataValidator()), is(true));

        assertThat(row.getReviewId(), is(reference.getReviewId()));
        assertThat(row.getAuthorId(), is(reference.getAuthorId()));
        assertThat(row.getPublishDate(), is(reference.getPublishDate()));
        assertThat(row.getSubject(), is(reference.getSubject()));
        assertThat(row.getRating(), is(reference.getRating()));
        assertThat(row.getRatingWeight(), is(reference.getRatingWeight()));
        assertThat(row.isRatingIsAverage(), is(reference.isRatingIsAverage()));
    }

    @Test
    public void constructionWithReviewAndGetters() {
        Review review = RandomReview.nextReview();

        RowReviewImpl row = new RowReviewImpl(review);

        assertThat(row.hasData(new DataValidator()), is(true));
        assertThat(row.getReviewId(), is(review.getReviewId()));
        assertThat(row.getAuthorId(), is(review.getAuthorId().getAuthorId().toString()));
        assertThat(row.getPublishDate(), is(review.getPublishDate().getTime()));
        assertThat(row.getSubject(), is(review.getSubject().getSubject()));
        assertThat(row.getRating(), is(review.getRating().getRating()));
        assertThat(row.getRatingWeight(), is(review.getRating().getRatingWeight()));
        assertThat(row.isRatingIsAverage(), is(review.isRatingAverageOfCriteria()));
    }

    @Test
    public void constructionWithInvalidReviewIdMakesRowReviewInvalid() {
        RowReview reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, "");
        values.put(RowReview.AUTHOR_ID, reference.getAuthorId());
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
        RowReview reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowReview.AUTHOR_ID, "");
        values.put(RowReview.PUBLISH_DATE, reference.getPublishDate());
        values.put(RowReview.SUBJECT, reference.getSubject());
        values.put(RowReview.RATING, reference.getRating());
        values.put(RowReview.RATING_WEIGHT, reference.getRatingWeight());
        values.put(RowReview.IS_AVERAGE, reference.isRatingIsAverage());

        RowReviewImpl row = new RowReviewImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithNullDateMakesRowReviewInvalid() {
        RowReview reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowReview.AUTHOR_ID, reference.getAuthorId());
        values.put(RowReview.PUBLISH_DATE, null);
        values.put(RowReview.SUBJECT, reference.getSubject());
        values.put(RowReview.RATING, reference.getRating());
        values.put(RowReview.RATING_WEIGHT, reference.getRatingWeight());
        values.put(RowReview.IS_AVERAGE, reference.isRatingIsAverage());

        RowReviewImpl row = new RowReviewImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithNullSubjectMakesRowReviewInvalid() {
        RowReview reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowReview.AUTHOR_ID, reference.getAuthorId());
        values.put(RowReview.PUBLISH_DATE, reference.getPublishDate());
        values.put(RowReview.SUBJECT, null);
        values.put(RowReview.RATING, reference.getRating());
        values.put(RowReview.RATING_WEIGHT, reference.getRatingWeight());
        values.put(RowReview.IS_AVERAGE, reference.isRatingIsAverage());

        RowReviewImpl row = new RowReviewImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithZeroLengthSubjectMakesRowReviewInvalid() {
        RowReview reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowReview.AUTHOR_ID, reference.getAuthorId());
        values.put(RowReview.PUBLISH_DATE, reference.getPublishDate());
        values.put(RowReview.SUBJECT, "");
        values.put(RowReview.RATING, reference.getRating());
        values.put(RowReview.RATING_WEIGHT, reference.getRatingWeight());
        values.put(RowReview.IS_AVERAGE, reference.isRatingIsAverage());

        RowReviewImpl row = new RowReviewImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithNullRatingMakesRowReviewInvalid() {
        RowReview reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowReview.AUTHOR_ID, reference.getAuthorId());
        values.put(RowReview.PUBLISH_DATE, reference.getPublishDate());
        values.put(RowReview.SUBJECT, reference.getSubject());
        values.put(RowReview.RATING, null);
        values.put(RowReview.RATING_WEIGHT, reference.getRatingWeight());
        values.put(RowReview.IS_AVERAGE, reference.isRatingIsAverage());

        RowReviewImpl row = new RowReviewImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithNegativeRatingMakesRowReviewInvalid() {
        RowReview reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowReview.AUTHOR_ID, reference.getAuthorId());
        values.put(RowReview.PUBLISH_DATE, reference.getPublishDate());
        values.put(RowReview.SUBJECT, reference.getSubject());
        values.put(RowReview.RATING, -1f);
        values.put(RowReview.RATING_WEIGHT, reference.getRatingWeight());
        values.put(RowReview.IS_AVERAGE, reference.isRatingIsAverage());

        RowReviewImpl row = new RowReviewImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithZeroRatingWeightMakesRowReviewInvalid() {
        RowReview reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowReview.AUTHOR_ID, reference.getAuthorId());
        values.put(RowReview.PUBLISH_DATE, reference.getPublishDate());
        values.put(RowReview.SUBJECT, reference.getSubject());
        values.put(RowReview.RATING, reference.getRating());
        values.put(RowReview.RATING_WEIGHT, 0);
        values.put(RowReview.IS_AVERAGE, reference.isRatingIsAverage());

        RowReviewImpl row = new RowReviewImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithNullRatingWeightMakesRowReviewInvalid() {
        RowReview reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowReview.AUTHOR_ID, reference.getAuthorId());
        values.put(RowReview.PUBLISH_DATE, reference.getPublishDate());
        values.put(RowReview.SUBJECT, reference.getSubject());
        values.put(RowReview.RATING, reference.getRating());
        values.put(RowReview.RATING_WEIGHT, null);
        values.put(RowReview.IS_AVERAGE, reference.isRatingIsAverage());

        RowReviewImpl row = new RowReviewImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithNullIsAverageWeightMakesRowReviewInvalid() {
        RowReview reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowReview.AUTHOR_ID, reference.getAuthorId());
        values.put(RowReview.PUBLISH_DATE, reference.getPublishDate());
        values.put(RowReview.SUBJECT, reference.getSubject());
        values.put(RowReview.RATING, reference.getRating());
        values.put(RowReview.RATING_WEIGHT, reference.getRatingWeight());
        values.put(RowReview.IS_AVERAGE, null);

        RowReviewImpl row = new RowReviewImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithInvalidRatingWeightMakesRowReviewInvalid() {
        RowReview reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowReview.AUTHOR_ID, reference.getAuthorId());
        values.put(RowReview.PUBLISH_DATE, reference.getPublishDate());
        values.put(RowReview.SUBJECT, reference.getSubject());
        values.put(RowReview.RATING, reference.getRating());
        values.put(RowReview.RATING_WEIGHT, 0);
        values.put(RowReview.IS_AVERAGE, reference.isRatingIsAverage());

        RowReviewImpl row = new RowReviewImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void iteratorReturnsDataInOrder() {
        RowReviewImpl row = newRow();

        ArrayList<RowEntry<RowReview, ?>> entries = getRowEntries(row);

        assertThat(entries.size(), is(7));

        checkEntry(entries.get(0), RowReview.REVIEW_ID, row.getReviewId().toString());
        checkEntry(entries.get(1), RowReview.AUTHOR_ID, row.getAuthorId());
        checkEntry(entries.get(2), RowReview.PUBLISH_DATE, row.getPublishDate());
        checkEntry(entries.get(3), RowReview.SUBJECT, row.getSubject());
        checkEntry(entries.get(4), RowReview.RATING, row.getRating());
        checkEntry(entries.get(5), RowReview.RATING_WEIGHT, row.getRatingWeight());
        checkEntry(entries.get(6), RowReview.IS_AVERAGE, row.isRatingIsAverage());
    }

    @NonNull
    @Override
    protected RowReviewImpl newRow() {
        return new RowReviewImpl(RandomReview.nextReview());
    }

    @Override
    protected String getRowId(RowReviewImpl row) {
        return row.getReviewId().toString();
    }
}
