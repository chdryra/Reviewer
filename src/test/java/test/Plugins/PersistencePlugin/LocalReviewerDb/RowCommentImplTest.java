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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.RowCommentImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
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
public class RowCommentImplTest extends RowTableBasicTest<RowComment, RowCommentImpl> {

    public static final int INDEX = 314;

    public RowCommentImplTest() {
        super(RowComment.COMMENT_ID.getName(), 4);
    }

    @Test
    public void constructionWithRowValuesAndGetters() {
        String reviewId = RandomReviewId.nextIdString();
        String commentId = reviewId + ":c" + INDEX;
        String comment = RandomString.nextSentence();
        boolean isHeadline = RAND.nextBoolean();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowComment.COMMENT_ID, commentId);
        values.put(RowComment.REVIEW_ID, reviewId);
        values.put(RowComment.COMMENT, comment);
        values.put(RowComment.IS_HEADLINE, isHeadline);

        RowCommentImpl row = new RowCommentImpl(values);

        assertThat(row.hasData(new DataValidator()), is(true));
        assertThat(row.getRowId(), is(commentId));
        assertThat(row.getReviewId().toString(), is(reviewId));
        assertThat(row.getComment(), is(comment));
        assertThat(row.isHeadline(), is(isHeadline));
    }

    @Test
    public void constructionWithDataCommentAndGetters() {
        DataComment comment = new DatumComment(RandomReviewId.nextReviewId(),
                RandomString.nextSentence(), RAND.nextBoolean());

        RowCommentImpl row = new RowCommentImpl(comment, INDEX);

        assertThat(row.hasData(new DataValidator()), is(true));
        checkAgainstReference(row, comment);
    }

    @Test
    public void constructionWithInvalidCommentIdMakesRowCommentInvalid() {
        RowCommentImpl reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowComment.COMMENT_ID, "");
        values.put(RowComment.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowComment.COMMENT, reference.getComment());
        values.put(RowComment.IS_HEADLINE, reference.isHeadline());

        RowCommentImpl row = new RowCommentImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithInvalidReviewIdMakesRowCommentInvalid() {
        RowComment reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowComment.COMMENT_ID, reference.getRowId());
        values.put(RowComment.REVIEW_ID, "");
        values.put(RowComment.COMMENT, reference.getComment());
        values.put(RowComment.IS_HEADLINE, reference.isHeadline());

        RowCommentImpl row = new RowCommentImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithNullIsHeadlineMakesRowCommentInvalid() {
        RowComment reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowComment.COMMENT_ID, reference.getRowId());
        values.put(RowComment.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowComment.COMMENT, reference.getComment());
        values.put(RowComment.IS_HEADLINE, null);

        RowCommentImpl row = new RowCommentImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithInvalidCommentMakesRowCommentInvalid() {
        DataComment comment = new DatumComment(RandomReviewId.nextReviewId(),
                "", RAND.nextBoolean());

        RowCommentImpl row = new RowCommentImpl(comment, INDEX);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void iteratorReturnsDataInOrder() {
        RowCommentImpl row = newRow();

        ArrayList<RowEntry<RowComment, ?>> entries = getRowEntries(row);

        assertThat(entries.size(), is(4));

        checkEntry(entries.get(0), RowComment.COMMENT_ID, getRowId(row));
        checkEntry(entries.get(1), RowComment.REVIEW_ID, row.getReviewId().toString());
        checkEntry(entries.get(2), RowComment.COMMENT, row.getComment());
        checkEntry(entries.get(3), RowComment.IS_HEADLINE, row.isHeadline());
    }

    @NonNull
    @Override
    protected RowCommentImpl newRow() {
        String reviewId = RandomReviewId.nextIdString();
        String commentId = reviewId + ":c" + INDEX;
        String comment = RandomString.nextSentence();
        boolean isHeadline = RAND.nextBoolean();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowComment.COMMENT_ID, commentId);
        values.put(RowComment.REVIEW_ID, reviewId);
        values.put(RowComment.COMMENT, comment);
        values.put(RowComment.IS_HEADLINE, isHeadline);

        return new RowCommentImpl(values);
    }

    @Override
    protected String getRowId(RowCommentImpl row) {
        return rowId(row);
    }

    private void checkAgainstReference(RowCommentImpl row, DataComment reference) {
        assertThat(row.getRowId(), is(rowId(reference)));
        assertThat(row.getReviewId(), is(reference.getReviewId()));
        assertThat(row.getComment(), is(reference.getComment()));
        assertThat(row.isHeadline(), is(reference.isHeadline()));
    }

    private String rowId(DataComment comment) {
        return comment.getReviewId().toString() + ":c" + String.valueOf(INDEX);
    }
}
