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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.RowCriterionImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.ArrayList;

import test.TestUtils.RandomRating;
import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowCriterionImplTest extends RowTableBasicTest<RowCriterion, RowCriterionImpl> {
    public static final int INDEX = 314;

    public RowCriterionImplTest() {
        super(RowCriterion.CRITERION_ID.getName(), 4);
    }

    @Test
    public void constructionWithRowValuesAndGetters() {
        String reviewId = RandomReviewId.nextIdString();
        String criterionId = reviewId + ":cr" + INDEX;
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowCriterion.CRITERION_ID, criterionId);
        values.put(RowCriterion.REVIEW_ID, reviewId);
        values.put(RowCriterion.SUBJECT, subject);
        values.put(RowCriterion.RATING, rating);

        RowCriterionImpl row = new RowCriterionImpl(values);

        assertThat(row.hasData(new DataValidator()), is(true));
        assertThat(row.getRowId(), is(criterionId));
        assertThat(row.getReviewId().toString(), is(reviewId));
        assertThat(row.getSubject(), is(subject));
        assertThat(row.getRating(), is(rating));
    }

    @Test
    public void constructionWithDataCriterionAndGetters() {
        DataCriterion criterion = new DatumCriterion(RandomReviewId.nextReviewId(),
                RandomString.nextWord(), RandomRating.nextRating());

        RowCriterionImpl row = new RowCriterionImpl(criterion, INDEX);

        assertThat(row.hasData(new DataValidator()), is(true));
        checkAgainstReference(row, criterion);
    }

    @Test
    public void constructionWithInvalidCriterionIdMakesRowCriterionInvalid() {
        RowCriterionImpl reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowCriterion.CRITERION_ID, "");
        values.put(RowCriterion.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowCriterion.SUBJECT, reference.getSubject());
        values.put(RowCriterion.RATING, reference.getRating());

        RowCriterionImpl row = new RowCriterionImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithInvalidReviewIdMakesRowCriterionInvalid() {
        RowCriterion reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowCriterion.CRITERION_ID, reference.getRowId());
        values.put(RowCriterion.REVIEW_ID, "");
        values.put(RowCriterion.SUBJECT, reference.getSubject());
        values.put(RowCriterion.RATING, reference.getRating());

        RowCriterionImpl row = new RowCriterionImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithNullIsHeadlineMakesRowCriterionInvalid() {
        RowCriterion reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowCriterion.CRITERION_ID, reference.getRowId());
        values.put(RowCriterion.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowCriterion.SUBJECT, reference.getSubject());
        values.put(RowCriterion.RATING, null);

        RowCriterionImpl row = new RowCriterionImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithInvalidCriterionMakesRowCriterionInvalid() {
        DataCriterion criterion = new DatumCriterion(RandomReviewId.nextReviewId(),
                "", RandomRating.nextRating());

        RowCriterionImpl row = new RowCriterionImpl(criterion, INDEX);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void iteratorReturnsDataInOrder() {
        RowCriterionImpl row = newRow();

        ArrayList<RowEntry<RowCriterion, ?>> entries = getRowEntries(row);

        assertThat(entries.size(), is(4));

        checkEntry(entries.get(0), RowCriterion.CRITERION_ID, getRowId(row));
        checkEntry(entries.get(1), RowCriterion.REVIEW_ID, row.getReviewId().toString());
        checkEntry(entries.get(2), RowCriterion.SUBJECT, row.getSubject());
        checkEntry(entries.get(3), RowCriterion.RATING, row.getRating());
    }

    @NonNull
    @Override
    protected RowCriterionImpl newRow() {
        String reviewId = RandomReviewId.nextIdString();
        String criterionId = reviewId + ":cr" + INDEX;
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowCriterion.CRITERION_ID, criterionId);
        values.put(RowCriterion.REVIEW_ID, reviewId);
        values.put(RowCriterion.SUBJECT, subject);
        values.put(RowCriterion.RATING, rating);

        return new RowCriterionImpl(values);
    }

    @Override
    protected String getRowId(RowCriterionImpl row) {
        return rowId(row);
    }

    private void checkAgainstReference(RowCriterionImpl row, DataCriterion reference) {
        assertThat(row.getRowId(), is(rowId(reference)));
        assertThat(row.getReviewId(), is(reference.getReviewId()));
        assertThat(row.getSubject(), is(reference.getSubject()));
        assertThat(row.getRating(), is(reference.getRating()));
    }

    private String rowId(DataCriterion criterion) {
        return criterion.getReviewId().toString() + ":cr" + String.valueOf(INDEX);
    }
}
