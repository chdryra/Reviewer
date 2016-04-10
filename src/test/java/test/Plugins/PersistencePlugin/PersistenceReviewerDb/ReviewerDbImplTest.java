/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.PersistenceReviewerDb;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;
import com.chdryra.android.reviewer.Model.TagsModel.Implementation.ItemTagImpl;
import com.chdryra.android.reviewer.Model.TagsModel.Implementation.TagsManagerImpl;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDbPlugin.Api.ContractorDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDbPlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Factories.FactoryReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.ReviewTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.ReviewerDbImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.RowAuthorImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.RowEntryImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.RowFactImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.RowReviewImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.RowTagImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.TableRowList;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;

import test.TestUtils.RandomAuthor;
import test.TestUtils.RandomReview;
import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.*;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class ReviewerDbImplTest {
    @Rule
    public ExpectedException mExpectedException = ExpectedException.none();
    @Mock
    private ReviewTransactor mReviewTransactor;
    @Mock
    private ContractorDb<ReviewerDbContract> mContractor;
    @Mock
    private TableTransactor mTransactor;

    private TagsManager mTagsManager;
    private ReviewerDbImpl mDb;
    private FactoryReviewerDbTableRow mRowFactory;
    private DataValidator mValidator;

    @Before
    public void setUp() {
        when(mContractor.getReadableTransactor()).thenReturn(mTransactor);
        when(mContractor.getWriteableTransactor()).thenReturn(mTransactor);
        ReviewerDbContract contract = new FactoryReviewerDbContract().newContract();
        when(mContractor.getContract()).thenReturn(contract);

        mRowFactory = new FactoryReviewerDbTableRow();
        mValidator = new DataValidator();
        mTagsManager = new TagsManagerImpl();
        mDb = new ReviewerDbImpl(mContractor, mReviewTransactor, mRowFactory, mValidator);
    }

    @Test
    public void beginWriteTransaction() {
        assertThat(mDb.beginWriteTransaction(), is(mTransactor));
        verify(mTransactor).beginTransaction();
    }

    @Test
    public void beginReadTransaction() {
        assertThat(mDb.beginReadTransaction(), is(mTransactor));
        verify(mTransactor).beginTransaction();
    }

    @Test
    public void endTransactionForReadableTransaction() {
        TableTransactor reader = mDb.beginReadTransaction();
        mDb.endTransaction(reader);
        verify(mTransactor).endTransaction();
    }

    @Test
    public void endTransactionForWriteableTransaction() {
        TableTransactor writer = mDb.beginWriteTransaction();
        mDb.endTransaction(writer);
        verify(mTransactor).endTransaction();
    }

    @Test
    public void addReviewToDbWhenReviewAlreadyInDbDoesNotCallReviewTransactorAndReturnsFalse() {
        Review review = RandomReview.nextReview();

        when(mTransactor.isIdInTable(eq(review.getReviewId().toString()),
                any(DbColumnDefinition.class),
                any(DbTable.class))).thenReturn(true);

        assertThat(mDb.addReviewToDb(review, mTagsManager, mTransactor), is(false));
        verifyZeroInteractions(mReviewTransactor);
    }

    @Test
    public void addReviewToDbWhenReviewNotInDbCallsReviewTransactorAddReviewToDbAndReturnsTrue() {
        Review review = RandomReview.nextReview();

        when(mTransactor.isIdInTable(eq(review.getReviewId().toString()),
                any(DbColumnDefinition.class),
                any(DbTable.class))).thenReturn(false);

        assertThat(mDb.addReviewToDb(review, mTagsManager, mTransactor), is(true));
        verify(mReviewTransactor).addReviewToDb(review, mTagsManager, mDb, mTransactor);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void deleteReviewFromDbForUnrecognisedReviewIdDoesNotCallReviewTransactorReturnsFalse() {
        DbTable<RowReview> table = mDb.getReviewsTable();

        ReviewId id = RandomReviewId.nextReviewId();
        RowEntry<RowReview, String> clause
                = new RowEntryImpl<>(RowReview.class, RowReview.REVIEW_ID, id.toString());

        when(mTransactor.getRowsWhere(table, clause, mRowFactory)).thenReturn(new
                TableRowList<RowReview>());

        assertThat(mDb.deleteReviewFromDb(id, mTagsManager, mTransactor), is(false));
        verifyZeroInteractions(mReviewTransactor);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void deleteReviewFromDbForRecognisedReviewIdDoesCallReviewTransactorReturnsTrue() {
        DbTable<RowReview> table = mDb.getReviewsTable();

        Review review = RandomReview.nextReview();
        ReviewId id = review.getReviewId();
        RowEntry<RowReview, String> clause
                = asClause(RowReview.class, RowReview.REVIEW_ID, id.toString());

        TableRowList<RowReview> ret = new TableRowList<>();
        RowReviewImpl row = new RowReviewImpl(review);
        ret.add(row);
        when(mTransactor.getRowsWhere(table, clause, mRowFactory)).thenReturn(ret);

        assertThat(mDb.deleteReviewFromDb(id, mTagsManager, mTransactor), is(true));
        verify(mReviewTransactor).deleteReviewFromDb(row, mTagsManager, mDb, mTransactor);
    }

    @Test
    public void loadReviewsWhereCallsReviewTransactorWithAppropriateReviewRows() {
        UserId id = RandomAuthor.nextAuthor().getUserId();
        RowEntry<RowReview, String> clause = asClause(RowReview.class, RowReview.USER_ID,
                id.toString());

        TableRowList<RowReview> ret = new TableRowList<>();
        Review review1 = RandomReview.nextReview();
        Review review2 = RandomReview.nextReview();
        Review review3 = RandomReview.nextReview();
        RowReviewImpl row1 = new RowReviewImpl(review1);
        RowReviewImpl row2 = new RowReviewImpl(review2);
        RowReviewImpl row3 = new RowReviewImpl(review3);
        ret.add(row1);
        ret.add(row2);
        ret.add(row3);

        when(mTransactor.getRowsWhere(mDb.getReviewsTable(), clause, mRowFactory)).thenReturn(ret);
        returnReviewForRow(review1, row1);
        returnReviewForRow(review2, row2);
        returnReviewForRow(review3, row3);

        mDb.loadReviewsWhere(mDb.getReviewsTable(), clause, mTransactor);

        verify(mReviewTransactor, atLeastOnce()).loadReview(row1, mDb, mTransactor);
        verify(mReviewTransactor, atLeastOnce()).loadReview(row2, mDb, mTransactor);
        verify(mReviewTransactor, atLeastOnce()).loadReview(row3, mDb, mTransactor);
    }

    @Test
    public void loadReviewsWhereCallsTransactorWithInputClauseIfReviewsTableClause() {
        UserId id = RandomAuthor.nextAuthor().getUserId();
        RowEntry<RowReview, String> clauseIn = asClause(RowReview.class, RowReview.USER_ID,
                id.toString());

        Review review = RandomReview.nextReview();
        TableRowList<RowReview> rowForClauseIn = new TableRowList<>();
        RowReviewImpl row = new RowReviewImpl(review);
        rowForClauseIn.add(row);

        when(mTransactor.getRowsWhere(mDb.getReviewsTable(), clauseIn, mRowFactory))
                .thenReturn(rowForClauseIn);
        returnReviewForRow(review, row);

        mDb.loadReviewsWhere(mDb.getReviewsTable(), clauseIn, mTransactor);

        verify(mTransactor).getRowsWhere(mDb.getReviewsTable(), clauseIn, mRowFactory);
        verify(mReviewTransactor).loadReview(row, mDb, mTransactor);
    }

    @Test
    public void loadReviewsWhereCallsTransactorWithAppropriateAuthorClauseIfAuthorTableClauses() {
        DataAuthor author = RandomAuthor.nextAuthor();
        String name = author.getName();
        String userId = author.getUserId().toString();

        DbTable<RowAuthor> clauseTable = mDb.getAuthorsTable();
        RowEntry<RowAuthor, String> clauseIn
                = asClause(RowAuthor.class, RowAuthor.AUTHOR_NAME, name);

        returnRowForClause(clauseTable, clauseIn, new RowAuthorImpl(author));

        Review review = RandomReview.nextReview();
        RowReview rowOut = new RowReviewImpl(review);
        RowEntry<RowReview, String> clauseReview
                = asClause(RowReview.class, RowReview.USER_ID, userId);
        returnRowForClause(mDb.getReviewsTable(), clauseReview, rowOut);
        returnReviewForRow(review, rowOut);

        Collection<Review> reviews = mDb.loadReviewsWhere(clauseTable, clauseIn, mTransactor);

        verify(mTransactor).getRowsWhere(mDb.getReviewsTable(), clauseReview, mRowFactory);
        verify(mReviewTransactor).loadReview(rowOut, mDb, mTransactor);
        assertThat(reviews.size(), is(1));
    }

    @Test
    public void loadReviewsWhereCallsTransactorWithAppropriateTagsClauseIfTagsTableClauses() {
        String tagString = RandomString.nextWord();
        DbTable<RowTag> clauseTable = mDb.getTagsTable();
        RowEntry<RowTag, String> clauseIn
                = asClause(RowTag.class, RowTag.TAG, tagString);

        Review review1 = RandomReview.nextReview();
        Review review2 = RandomReview.nextReview();
        Review review3 = RandomReview.nextReview();

        ItemTagImpl tag = new ItemTagImpl(tagString, review1.getReviewId().toString());
        tag.addItemId(review2.getReviewId().toString());
        tag.addItemId(review3.getReviewId().toString());
        returnRowForClause(clauseTable, clauseIn, new RowTagImpl(tag));

        RowReview rowOut1 = new RowReviewImpl(review1);
        RowReview rowOut2 = new RowReviewImpl(review1);
        RowReview rowOut3 = new RowReviewImpl(review1);
        RowEntry<RowReview, String> clauseReview1 = asClause(RowReview.class, RowReview
                .REVIEW_ID, review1.getReviewId().toString());
        RowEntry<RowReview, String> clauseReview2 = asClause(RowReview.class, RowReview
                .REVIEW_ID, review2.getReviewId().toString());
        RowEntry<RowReview, String> clauseReview3 = asClause(RowReview.class, RowReview
                .REVIEW_ID, review3.getReviewId().toString());

        DbTable<RowReview> reviewsTable = mDb.getReviewsTable();
        returnRowForClause(reviewsTable, clauseReview1, rowOut1);
        returnRowForClause(reviewsTable, clauseReview2, rowOut2);
        returnRowForClause(reviewsTable, clauseReview3, rowOut3);
        returnReviewForRow(review1, rowOut1);
        returnReviewForRow(review2, rowOut2);
        returnReviewForRow(review3, rowOut3);

        Collection<Review> reviews = mDb.loadReviewsWhere(clauseTable, clauseIn, mTransactor);

        verify(mTransactor, atLeastOnce()).getRowsWhere(reviewsTable, clauseReview1, mRowFactory);
        verify(mTransactor, atLeastOnce()).getRowsWhere(reviewsTable, clauseReview2, mRowFactory);
        verify(mTransactor, atLeastOnce()).getRowsWhere(reviewsTable, clauseReview3, mRowFactory);
        verify(mReviewTransactor, atLeastOnce()).loadReview(rowOut1, mDb, mTransactor);
        verify(mReviewTransactor, atLeastOnce()).loadReview(rowOut2, mDb, mTransactor);
        verify(mReviewTransactor, atLeastOnce()).loadReview(rowOut3, mDb, mTransactor);

        assertThat(reviews.size(), is(3));
    }

    @Test
    public void loadReviewsWhereCallsTransactorWithAppropriateDataClauseIfDataTableClauses() {
        //Same logic for any data type so just testing facts.
        String factLabel = RandomString.nextWord();
        DbTable<RowFact> clauseTable = mDb.getFactsTable();
        RowEntry<RowFact, String> clauseIn
                = asClause(RowFact.class, RowFact.LABEL, factLabel);

        Review review1 = RandomReview.nextReview();
        Review review2 = RandomReview.nextReview();
        Review review3 = RandomReview.nextReview();

        ArrayList<RowFact> returnRows = new ArrayList<>();
        returnRows.add(new RowFactImpl(new DatumFact(review1.getReviewId(), factLabel,
                RandomString.nextWord()), 1));
        returnRows.add(new RowFactImpl(new DatumFact(review2.getReviewId(), factLabel,
                RandomString.nextWord()), 2));
        returnRows.add(new RowFactImpl(new DatumFact(review3.getReviewId(), factLabel,
                RandomString.nextWord()), 3));
        returnRowsForClause(clauseTable, clauseIn, returnRows);

        RowReview rowOut1 = new RowReviewImpl(review1);
        RowReview rowOut2 = new RowReviewImpl(review2);
        RowReview rowOut3 = new RowReviewImpl(review3);
        RowEntry<RowReview, String> clauseReview1 = asClause(RowReview.class, RowReview
                .REVIEW_ID, review1.getReviewId().toString());
        RowEntry<RowReview, String> clauseReview2 = asClause(RowReview.class, RowReview
                .REVIEW_ID, review2.getReviewId().toString());
        RowEntry<RowReview, String> clauseReview3 = asClause(RowReview.class, RowReview
                .REVIEW_ID, review3.getReviewId().toString());

        DbTable<RowReview> reviewsTable = mDb.getReviewsTable();
        returnRowForClause(reviewsTable, clauseReview1, rowOut1);
        returnRowForClause(reviewsTable, clauseReview2, rowOut2);
        returnRowForClause(reviewsTable, clauseReview3, rowOut3);
        returnReviewForRow(review1, rowOut1);
        returnReviewForRow(review2, rowOut2);
        returnReviewForRow(review3, rowOut3);

        Collection<Review> reviews = mDb.loadReviewsWhere(clauseTable, clauseIn, mTransactor);

        verify(mTransactor, atLeastOnce()).getRowsWhere(reviewsTable, clauseReview1, mRowFactory);
        verify(mTransactor, atLeastOnce()).getRowsWhere(reviewsTable, clauseReview2, mRowFactory);
        verify(mTransactor, atLeastOnce()).getRowsWhere(reviewsTable, clauseReview3, mRowFactory);
        verify(mReviewTransactor, atLeastOnce()).loadReview(rowOut1, mDb, mTransactor);
        verify(mReviewTransactor, atLeastOnce()).loadReview(rowOut2, mDb, mTransactor);
        verify(mReviewTransactor, atLeastOnce()).loadReview(rowOut3, mDb, mTransactor);

        assertThat(reviews.size(), is(3));
    }

    @Test
    public void getUniqueRowWhereThrowsIllegalStateExceptionIfMoreThanOneRowFound() {
        mExpectedException.expect(IllegalStateException.class);
        DbTable table = mock(DbTable.class);
        RowEntry clause = mock(RowEntry.class);
        TableRowList ret = mock(TableRowList.class);
        when(ret.size()).thenReturn(2);
        when(mTransactor.getRowsWhere(table, clause, mRowFactory)).thenReturn(ret);

        mDb.getUniqueRowWhere(table, clause, mTransactor);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getUniqueRowWhereReturnsEmptyRowIfNoRowsFound() {
        DbTable<RowReview> table = mDb.getReviewsTable();
        RowEntry clause = mock(RowEntry.class);
        when(mTransactor.getRowsWhere(table, clause, mRowFactory)).thenReturn(new TableRowList<>());

        RowReview row = mDb.getUniqueRowWhere(table, clause, mTransactor);
        assertThat(row, not(nullValue()));
        assertThat(row.hasData(mValidator), is(false));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getUniqueRowWhereReturnsRowIfOneRowFound() {
        DbTable<RowReview> table = mDb.getReviewsTable();
        RowEntry clause = mock(RowEntry.class);

        TableRowList<RowReview> ret = new TableRowList<>();
        RowReview reviewRow = new RowReviewImpl(RandomReview.nextReview());
        ret.add(reviewRow);

        when(mTransactor.getRowsWhere(table, clause, mRowFactory)).thenReturn(ret);

        RowReview row = mDb.getUniqueRowWhere(table, clause, mTransactor);
        assertThat(row, is(reviewRow));
        assertThat(row.hasData(mValidator), is(true));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getRowsWhere() {
        DbTable<RowReview> table = mDb.getReviewsTable();
        RowEntry<RowReview, ?> clause = mock(RowEntry.class);

        TableRowList<RowReview> ret = new TableRowList<>();
        ret.add(new RowReviewImpl(RandomReview.nextReview()));
        ret.add(new RowReviewImpl(RandomReview.nextReview()));
        ret.add(new RowReviewImpl(RandomReview.nextReview()));

        when(mTransactor.getRowsWhere(table, clause, mRowFactory)).thenReturn(ret);

        TableRowList<RowReview> list = mDb.getRowsWhere(table, clause, mTransactor);

        verify(mTransactor).getRowsWhere(table, clause, mRowFactory);
        assertThat(list, is(ret));
    }

    @Test
    public void loadTable() {
        DbTable<RowReview> table = mDb.getReviewsTable();

        TableRowList<RowReview> reviews = new TableRowList<>();
        reviews.add(new RowReviewImpl(RandomReview.nextReview()));
        when(mTransactor.loadTable(table, mRowFactory)).thenReturn(reviews);

        TableRowList<RowReview> list = mDb.loadTable(table, mTransactor);

        verify(mTransactor).loadTable(table, mRowFactory);
        assertThat(list, is(reviews));
    }

    private void returnReviewForRow(Review review, RowReview rowOut) {
        when(mReviewTransactor.loadReview(rowOut, mDb, mTransactor)).thenReturn(review);
    }

    private <T extends DbTableRow> void returnRowForClause(DbTable<T> table, RowEntry<T, String>
            clauseIn, T row) {
        TableRowList<T> rowForClauseIn = new TableRowList<>();
        rowForClauseIn.add(row);
        when(mTransactor.getRowsWhere(table, clauseIn, mRowFactory)).thenReturn(rowForClauseIn);
    }

    private <T extends DbTableRow> void returnRowsForClause(DbTable<T> table, RowEntry<T, String>
            clauseIn, ArrayList<T> rows) {
        TableRowList<T> rowForClauseIn = new TableRowList<>();
        rowForClauseIn.addAll(rows);
        when(mTransactor.getRowsWhere(table, clauseIn, mRowFactory)).thenReturn(rowForClauseIn);
    }

    private <DbRow extends DbTableRow, T> RowEntry<DbRow, T> asClause(Class<DbRow> rowClass,
                                                                      ColumnInfo<T> column, T
                                                                              value) {
        return new RowEntryImpl<>(rowClass, column, value);
    }
}
