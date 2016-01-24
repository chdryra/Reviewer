/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.PersistenceReviewerDb;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.ReviewDeleterImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.RowEntryImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.RowReviewImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableComments;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableFacts;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableImages;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableLocations;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableReviews;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Implementation.TableRowList;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.RowEntry;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import test.TestUtils.RandomReview;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDeleterImplTest {
    @Mock
    private FactoryDbTableRow mRowFactory;
    @Mock
    private ReviewerDb mDb;
    private ReviewDeleterImpl mDeleter;
    private TableTransactor mTransactor;

    @Before
    public void setUp() {
        mDeleter = new ReviewDeleterImpl(mRowFactory);
        when(mDb.getImagesTable()).thenReturn(mock(TableImages.class));
        when(mDb.getFactsTable()).thenReturn(mock(TableFacts.class));
        when(mDb.getLocationsTable()).thenReturn(mock(TableLocations.class));
        when(mDb.getCommentsTable()).thenReturn(mock(TableComments.class));
        when(mDb.getReviewsTable()).thenReturn(mock(TableReviews.class));
    }
    @Test
    public void deleteImagesCallsTransactorWithCorrectClause() {
        RowReview review = newRowReview();

        RowEntry<?> expected = asClause(RowImage.REVIEW_ID, review.getRowId());

        mDeleter.deleteReviewFromDb(review, mDb, mTransactor);

        verify(mDb.getImagesTable());
        verify(mTransactor).deleteRowsWhere(mDb.getImagesTable(), expected);
    }

    private RowReview newRowReview() {
        return new RowReviewImpl(RandomReview.nextReview());
    }

    private <T> RowEntry<?> asClause(ColumnInfo<T> col, T entry) {
        return new RowEntryImpl<>(col, entry);
    }

    private class Transactor implements TableTransactor {

        @Override
        public void beginTransaction() {
            fail();
        }

        @Override
        public void endTransaction() {
            fail();
        }

        @Override
        public <DbRow extends DbTableRow> TableRowList<DbRow> loadTable(DbTable<DbRow> table,
                                                                        FactoryDbTableRow
                                                                                rowFactory) {
            fail();
            return null;
        }

        @Override
        public <DbRow extends DbTableRow, Type> TableRowList<DbRow> getRowsWhere(DbTable<DbRow> table,
                                                                                 RowEntry<Type> clause,
                                                                                 FactoryDbTableRow rowFactory) {
            TableRowList<DbRow> dbRows = new TableRowList<>();
            if(!table.equals(mDb.getReviewsTable())) {
                return dbRows;
            }
            if(clause.getColumnName().equals(RowReview.PARENT_ID.getName())) {
            }
            return null;
        }

        @Override
        public <Type> void deleteRowsWhere(DbTable<?> table, RowEntry<Type> clause) {

        }

        @Override
        public <DbRow extends DbTableRow> boolean insertRow(DbRow row, DbTable<DbRow> table) {
            fail();
            return false;
        }

        @Override
        public <DbRow extends DbTableRow> void insertOrReplaceRow(DbRow row, DbTable<DbRow> table) {
            fail();
        }

        @Override
        public boolean isIdInTable(String id, DbColumnDefinition idCol, DbTable<?> table) {
            fail();
            return false;
        }
    }
}
