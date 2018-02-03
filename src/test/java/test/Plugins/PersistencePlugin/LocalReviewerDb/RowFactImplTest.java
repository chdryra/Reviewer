/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.LocalReviewerDb;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.RowFactImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.ArrayList;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowFactImplTest extends RowTableBasicTest<RowFact, RowFactImpl> {

    public static final int INDEX = 314;

    public RowFactImplTest() {
        super(RowFact.FACT_ID.getName(), 5);
    }

    @Test
    public void constructionWithRowValuesAndGetters() {
        String reviewId = RandomReviewId.nextIdString();
        String factId = reviewId + ":f" + INDEX;
        String label = RandomString.nextWord();
        String value = RandomString.nextWord();
        boolean isUrl = RAND.nextBoolean();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowFact.FACT_ID, factId);
        values.put(RowFact.REVIEW_ID, reviewId);
        values.put(RowFact.LABEL, label);
        values.put(RowFact.VALUE, value);
        values.put(RowFact.IS_URL, isUrl);

        RowFactImpl row = new RowFactImpl(values);

        assertThat(row.hasData(new DataValidator()), is(true));
        assertThat(row.getRowId(), is(factId));
        assertThat(row.getReviewId().toString(), is(reviewId));
        assertThat(row.getLabel(), is(label));
        assertThat(row.getValue(), is(value));
        assertThat(row.isUrl(), is(isUrl));
    }

    @Test
    public void constructionWithDataFactAndGetters() {
        DataFact fact = new DatumFact(RandomReviewId.nextReviewId(),
                RandomString.nextWord(), RandomString.nextWord());

        RowFactImpl row = new RowFactImpl(fact, INDEX);

        assertThat(row.hasData(new DataValidator()), is(true));
        checkAgainstReference(row, fact);
    }

    @Test
    public void constructionWithInvalidFactIdMakesRowFactInvalid() {
        RowFactImpl reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowFact.FACT_ID, "");
        values.put(RowFact.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowFact.LABEL, reference.getLabel());
        values.put(RowFact.VALUE, reference.getValue());
        values.put(RowFact.IS_URL, reference.isUrl());

        RowFactImpl row = new RowFactImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithInvalidReviewIdMakesRowFactInvalid() {
        RowFact reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowFact.FACT_ID, reference.getRowId());
        values.put(RowFact.REVIEW_ID, "");
        values.put(RowFact.LABEL, reference.getLabel());
        values.put(RowFact.VALUE, reference.getValue());
        values.put(RowFact.IS_URL, reference.isUrl());

        RowFactImpl row = new RowFactImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithNullIsUrlMakesRowFactInvalid() {
        RowFact reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowFact.FACT_ID, reference.getRowId());
        values.put(RowFact.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowFact.LABEL, reference.getLabel());
        values.put(RowFact.VALUE, reference.getValue());
        values.put(RowFact.IS_URL, null);

        RowFactImpl row = new RowFactImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithInvalidIs_urlMakesRowFactInvalid() {
        DataFact fact = new DatumFact(RandomReviewId.nextReviewId(),
                RandomString.nextWord(), "");

        RowFactImpl row = new RowFactImpl(fact, INDEX);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void iteratorReturnsDataInOrder() {
        RowFactImpl row = newRow();

        ArrayList<RowEntry<RowFact, ?>> entries = getRowEntries(row);

        assertThat(entries.size(), is(5));

        checkEntry(entries.get(0), RowFact.FACT_ID, getRowId(row));
        checkEntry(entries.get(1), RowFact.REVIEW_ID, row.getReviewId().toString());
        checkEntry(entries.get(2), RowFact.LABEL, row.getLabel());
        checkEntry(entries.get(3), RowFact.VALUE, row.getValue());
        checkEntry(entries.get(4), RowFact.IS_URL, row.isUrl());
    }

    @NonNull
    @Override
    protected RowFactImpl newRow() {
        String reviewId = RandomReviewId.nextIdString();
        String factId = reviewId + ":f" + INDEX;
        String label = RandomString.nextWord();
        String value = RandomString.nextWord();
        boolean isUrl = RAND.nextBoolean();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowFact.FACT_ID, factId);
        values.put(RowFact.REVIEW_ID, reviewId);
        values.put(RowFact.LABEL, label);
        values.put(RowFact.VALUE, value);
        values.put(RowFact.IS_URL, isUrl);

        return new RowFactImpl(values);
    }

    @Override
    protected String getRowId(RowFactImpl row) {
        return rowId(row);
    }

    private void checkAgainstReference(RowFactImpl row, DataFact reference) {
        assertThat(row.getRowId(), is(rowId(reference)));
        assertThat(row.getReviewId(), is(reference.getReviewId()));
        assertThat(row.getLabel(), is(reference.getLabel()));
        assertThat(row.getValue(), is(reference.getValue()));
        assertThat(row.isUrl(), is(reference.isUrl()));
    }

    private String rowId(DataFact fact) {
        return fact.getReviewId().toString() + ":f" + String.valueOf(INDEX);
    }
}
