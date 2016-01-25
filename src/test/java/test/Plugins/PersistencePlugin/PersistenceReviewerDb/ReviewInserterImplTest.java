/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.PersistenceReviewerDb;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.Model.Implementation.TagsModel.ItemTagList;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.ReviewInserterImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableAuthors;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableComments;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableFacts;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableImages;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableLocations;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableReviews;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableRowList;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableTags;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.RowEntry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import test.TestUtils.RandomReview;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class ReviewInserterImplTest {
    @Mock
    private FactoryDbTableRow mRowFactory;
    @Mock
    private ReviewerDb mDb;
    private ReviewInserterImpl mInserter;
    @Mock
    private Transactor mTransactor;
    private TableRowList<RowReview> mWhereRows = new TableRowList<>();
    private TagsMan mManager;
    private ItemTagCollection mTags = new ItemTagList();

    @Before
    public void setUp() {
        mInserter = new ReviewInserterImpl(mRowFactory);
        mManager = new TagsMan();
        mTransactor = new Transactor();
        when(mDb.getImagesTable()).thenReturn(mock(TableImages.class));
        when(mDb.getFactsTable()).thenReturn(mock(TableFacts.class));
        when(mDb.getLocationsTable()).thenReturn(mock(TableLocations.class));
        when(mDb.getCommentsTable()).thenReturn(mock(TableComments.class));
        when(mDb.getReviewsTable()).thenReturn(mock(TableReviews.class));
        when(mDb.getAuthorsTable()).thenReturn(mock(TableAuthors.class));
        when(mDb.getTagsTable()).thenReturn(mock(TableTags.class));
        when(mDb.getTagsManager()).thenReturn(mManager);
    }

    @Test
    public void insertReviewCallsTransactorWithCorrectTableAndRows() {
        Review review = newReview();

        ArrayList<DbTableRow> rows = new ArrayList<>();
        rows.add(asRow(RowReview.class, review));
        for(DataCriterionReview criterion : review.getCriteria()) {
            rows.add(asRow(RowReview.class, criterion));
        }

        checkInsertCalled(review, mDb.getReviewsTable(), TableReviews.NAME, rows);
    }

    private void checkCaptures(ArrayList<DbTableRow> rows) {
        checkNumberCaptured(rows.size());
        for (int i = 0; i < rows.size(); ++i) {
            assertThat(mTransactor.mCapturedRows.get(i).equals(rows.get(i)), is(true));
        }
    }

    private void checkInsertCalled(Review review, DbTable<?> table,
                                   String tableName, DbTableRow row) {
        ArrayList<DbTableRow> rows = new ArrayList<>();
        rows.add(row);
        checkInsertCalled(review, table, tableName, rows);
    }

    private void checkInsertCalled(Review review, DbTable<?> table,
                                   String tableName, ArrayList<DbTableRow> rows) {
        setToCapture(table, tableName);

        checkNumberCaptured(0);

        mInserter.addReviewToDb(review, mDb, mTransactor);

        checkNumberCaptured(rows.size());
        checkCaptures(rows);
    }

    private void checkNumberCaptured(int num) {
        assertThat(mTransactor.mTablesCaptured, is(num));
        assertThat(mTransactor.mCapturedRows.size(), is(num));
    }

    private void setToCapture(DbTable<?> table, String tableName) {
        when(table.getName()).thenReturn(tableName);
        mTransactor.mCaptureTableName = tableName;
    }

    private Review newReview() {
        return RandomReview.nextReview();
    }

    private <T extends DbTableRow, D> DbTableRow asRow(Class<T> rowClass, D entry) {
        return mRowFactory.newRow(rowClass, entry);
    }

    private <T extends DbTableRow, D> DbTableRow asRow(Class<T> rowClass, D entry, int index) {
        return mRowFactory.newRow(rowClass, entry, index);
    }

    private class Transactor implements TableTransactor {
        private String mCaptureTableName;
        private int mTablesCaptured = 0;
        private ArrayList<DbTableRow> mCapturedRows = new ArrayList<>();
        private boolean mInsertOrReplaceCalled = false;
        private boolean mIsAuthorIdInTable = false;

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
                                                                                 RowEntry<Type>
                                                                                         clause,
                                                                                 FactoryDbTableRow rowFactory) {
            fail();
            return null;
        }

        @Override
        public <Type> void deleteRowsWhere(DbTable<?> table, RowEntry<Type> clause) {
            fail();
        }

        @Override
        public <DbRow extends DbTableRow> boolean insertRow(DbRow row, DbTable<DbRow> table) {
            if (table.getName() != null && table.getName().equals(mCaptureTableName)) {
                mTablesCaptured++;
                mCapturedRows.add(row);
            }
            return true;
        }

        @Override
        public <DbRow extends DbTableRow> void insertOrReplaceRow(DbRow row, DbTable<DbRow> table) {
            if (table.getName() != null && table.getName().equals(mCaptureTableName)) {
                mInsertOrReplaceCalled = true;
                mTablesCaptured++;
                mCapturedRows.add(row);
            }
        }

        @Override
        public boolean isIdInTable(String id, DbColumnDefinition idCol, DbTable<?> table) {
            return mIsAuthorIdInTable;
        }
    }

    private class TagsMan implements TagsManager {
        @Override
        public void tagItem(String id, String tag) {
            fail();
        }

        @Override
        public void tagItem(String id, ArrayList<String> tags) {
            fail();
        }

        @Override
        public boolean untagItem(String id, ItemTag tag) {
            fail();
            return false;
        }

        @Override
        public ItemTagCollection getTags() {
            fail();
            return null;
        }

        @Override
        public ItemTagCollection getTags(String id) {
            return mTags;
        }
    }
}
