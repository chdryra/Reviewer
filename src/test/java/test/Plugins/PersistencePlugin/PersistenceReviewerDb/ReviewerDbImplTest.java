/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.PersistenceReviewerDb;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Implementation.TagsModel.TagsManagerImpl;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.ContractorDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Factories.FactoryReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.ReviewTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.ReviewerDbImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.RowEntryImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.RowReviewImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableRowList;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.RowEntry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

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
    private TagsManager mTagsManager;
    @Mock
    private ContractorDb<ReviewerDbContract> mContractor;
    @Mock
    private TableTransactor mTransactor;
    private ReviewerDbImpl mDb;
    private FactoryReviewerDbTableRow mRowFactory;
    private DataValidator mValidator;
    private ReviewerDbContract mContract;

    @Before
    public void setUp() {
        when(mContractor.getReadableTransactor()).thenReturn(mTransactor);
        when(mContractor.getWriteableTransactor()).thenReturn(mTransactor);
        mContract = setupContract();
        when(mContractor.getContract()).thenReturn(mContract);

        mRowFactory = new FactoryReviewerDbTableRow();
        mValidator = new DataValidator();
        mTagsManager = new TagsManagerImpl();
        mDb = new ReviewerDbImpl(mContractor, mReviewTransactor, mRowFactory, mTagsManager,
                mValidator);
    }

    @Test
    public void getTagsManager() {
        assertThat(mDb.getTagsManager(), is(mTagsManager));
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

        assertThat(mDb.addReviewToDb(review, mTransactor), is(false));
        verifyZeroInteractions(mReviewTransactor);
    }

    @Test
    public void addReviewToDbWhenReviewNotInDbCallsReviewTransactorAddReviewToDbAndReturnsTrue() {
        Review review = RandomReview.nextReview();

        when(mTransactor.isIdInTable(eq(review.getReviewId().toString()),
                any(DbColumnDefinition.class),
                any(DbTable.class))).thenReturn(false);

        assertThat(mDb.addReviewToDb(review, mTransactor), is(true));
        verify(mReviewTransactor).addReviewToDb(review, mDb, mTransactor);
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

        assertThat(mDb.deleteReviewFromDb(id, mTransactor), is(false));
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

        assertThat(mDb.deleteReviewFromDb(id, mTransactor), is(true));
        verify(mReviewTransactor).deleteReviewFromDb(row, mDb, mTransactor);
    }

    @Test
    public void loadReviewsWhereCallsReviewTransactorWithPassedClauseIfReviewTableClauses() {
        Review review = RandomReview.nextReview();
        RowEntry<RowReview, String> clause
                = asClause(RowReview.class, RowReview.REVIEW_ID, review.getReviewId().toString());

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

        when(mTransactor.getRowsWhere(mDb.getReviewsTable(),clause, mRowFactory)).thenReturn(ret);
        when(mReviewTransactor.loadReview(row1, mDb, mTransactor)).thenReturn(review1);
        when(mReviewTransactor.loadReview(row2, mDb, mTransactor)).thenReturn(review2);
        when(mReviewTransactor.loadReview(row3, mDb, mTransactor)).thenReturn(review3);

        Collection<Review> reviews = mDb.loadReviewsWhere(mDb.getReviewsTable(), clause,
                mTransactor);

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

    private <DbRow extends DbTableRow, T> RowEntry<DbRow, T> asClause(Class<DbRow> rowClass,
                                                                      ColumnInfo<T> column, T value) {
        return new RowEntryImpl<>(rowClass, column, value);
    }

    @NonNull
    private ReviewerDbContract setupContract() {
        FactoryReviewerDbContract factory = new FactoryReviewerDbContract(new FactoryDbColumnDef(), new FactoryForeignKeyConstraint());
        return factory.newContract();
    }
}
