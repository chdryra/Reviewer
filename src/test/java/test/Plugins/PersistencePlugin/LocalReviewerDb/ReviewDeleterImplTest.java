/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.LocalReviewerDb;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.TableTransactor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ReviewDeleterImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.RowEntryImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.RowReviewImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableAuthors;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableComments;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableCriteria;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableFacts;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableImages;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableLocations;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableReviews;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableRowList;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableTags;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowComment;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowCriterion;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.corelibrary.TagsModel.Implementation.ItemTagImpl;
import com.chdryra.android.corelibrary.TagsModel.Implementation.ItemTagList;
import com.chdryra.android.corelibrary.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.corelibrary.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import test.TestUtils.RandomReview;
import test.TestUtils.RandomReviewId;

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
public class ReviewDeleterImplTest {
    @Mock
    private FactoryDbTableRow mRowFactory;
    @Mock
    private ReviewerDb mDb;
    private TagsMan mTagsManager;
    private ReviewDeleterImpl mDeleter;
    @Mock
    private Transactor mTransactor;
    private TableRowList<RowReview> mWhereRows = new TableRowList<>();
    private ItemTagCollection mTags = new ItemTagList();

    @Before
    public void setUp() {
        mDeleter = new ReviewDeleterImpl(mRowFactory);
        mTransactor = new Transactor();
        mTagsManager = new TagsMan();
        when(mDb.getImagesTable()).thenReturn(mock(TableImages.class));
        when(mDb.getFactsTable()).thenReturn(mock(TableFacts.class));
        when(mDb.getLocationsTable()).thenReturn(mock(TableLocations.class));
        when(mDb.getCriteriaTable()).thenReturn(mock(TableCriteria.class));
        when(mDb.getCommentsTable()).thenReturn(mock(TableComments.class));
        when(mDb.getReviewsTable()).thenReturn(mock(TableReviews.class));
        when(mDb.getAuthorsTable()).thenReturn(mock(TableAuthors.class));
        when(mDb.getTagsTable()).thenReturn(mock(TableTags.class));
    }

    @Test
    public void deleteImagesCallsTransactorWithCorrectTableAndClause() {
        RowReview review = newRowReview();
        checkDeleteCalled(review, mDb.getImagesTable(), TableImages.NAME,
                asClause(RowImage.class, RowImage.REVIEW_ID, review.getRowId()));
    }

    @Test
    public void deleteFactsCallsTransactorWithCorrectTableAndClause() {
        RowReview review = newRowReview();
        checkDeleteCalled(review, mDb.getFactsTable(), TableFacts.NAME,
                asClause(RowFact.class, RowFact.REVIEW_ID, review.getRowId()));
    }

    @Test
    public void deleteCommentsCallsTransactorWithCorrectTableAndClause() {
        RowReview review = newRowReview();
        checkDeleteCalled(review, mDb.getCommentsTable(), TableComments.NAME,
                asClause(RowComment.class, RowComment.REVIEW_ID, review.getRowId()));
    }

    @Test
    public void deleteLocationCallsTransactorWithCorrectTableAndClause() {
        RowReview review = newRowReview();
        checkDeleteCalled(review, mDb.getLocationsTable(), TableLocations.NAME,
                asClause(RowLocation.class, RowLocation.REVIEW_ID, review.getRowId()));
    }

    @Test
    public void deleteReviewCallsTransactorWithCorrectTableAndClause() {
        RowReview review = newRowReview();
        checkDeleteCalled(review, mDb.getReviewsTable(), TableReviews.NAME,
                asClause(RowReview.class, RowReview.REVIEW_ID, review.getRowId()));
    }

    @Test
    public void deleteCriteriaCallsTransactorWithCorrectTableAndClause() {
        RowReview review = newRowReview();
        checkDeleteCalled(review, mDb.getCriteriaTable(), TableCriteria.NAME,
                asClause(RowCriterion.class, RowCriterion.REVIEW_ID, review.getRowId()));
    }

    @Test
    public void deleteTagsDoesNotCallTransactorIfTagStillTagsAnotherReview() {
        RowReview review = newRowReview();
        ItemTagImpl tag = new ItemTagImpl(RandomString.nextWord(), review.getRowId());
        tag.addItemId(RandomReviewId.nextIdString());
        mTags.add(tag);

        checkDeleteNotCalled(review, mDb.getTagsTable(), TableTags.NAME);
    }

    @Test
    public void deleteTagsDoesCallTransactorOnTagsNoLongerTaggingAnotherReview() {
        RowReview review = newRowReview();
        ItemTagImpl tag1 = new ItemTagImpl(RandomString.nextWord(), review.getRowId());
        ItemTagImpl tag2 = new ItemTagImpl(RandomString.nextWord(), review.getRowId());
        ItemTagImpl tag3 = new ItemTagImpl(RandomString.nextWord(), review.getRowId());
        tag3.addItemId(RandomReviewId.nextIdString());
        mTags.add(tag1);
        mTags.add(tag2);
        mTags.add(tag3);

        ArrayList<RowEntry<RowTag, ?>> clauses = new ArrayList<>();
        clauses.add(asClause(RowTag.class, RowTag.TAG, tag1.getTag()));
        clauses.add(asClause(RowTag.class, RowTag.TAG, tag2.getTag()));
        checkDeleteCalled(review, mDb.getTagsTable(), TableTags.NAME, clauses);
    }

    @Test
    public void deleteAuthorDoesNotCallTransactorIfAuthorStillAuthorsAnotherReview() {
        RowReview review = newRowReview();
        mWhereRows.add(newRowReview());
        checkDeleteNotCalled(review, mDb.getAuthorsTable(), TableAuthors.NAME);
    }

    @Test
    public void deleteAuthorDoesCallTransactorIfAuthorNoLongerAuthorsAnotherReview() {
        RowReview review = newRowReview();
        checkDeleteCalled(review, mDb.getAuthorsTable(), TableAuthors.NAME,
                asClause(RowAuthor.class, RowAuthor.AUTHOR_ID, review.getAuthorId()));
    }

    private <DbRow extends DbTableRow> void checkCaptures(ArrayList<RowEntry<DbRow, ?>> clauses) {
        checkNumberCaptured(clauses.size());
        for (int i = 0; i < clauses.size(); ++i) {
            assertThat(mTransactor.mCapturedClauses.get(i).equals(clauses.get(i)), is(true));
        }
    }

    private void checkDeleteNotCalled(RowReview review, DbTable<?> table, String tableName) {
        setToCapture(table, tableName);

        checkNumberCaptured(0);

        mDeleter.deleteReviewFromDb(review, mTagsManager, mDb, mTransactor);

        checkNumberCaptured(0);
    }

    private <DbRow extends DbTableRow> void checkDeleteCalled(RowReview review,
                                                              DbTable<DbRow> table,
                                   String tableName, RowEntry<DbRow, ?> clause) {
        ArrayList<RowEntry<DbRow, ?>> clauses = new ArrayList<>();
        clauses.add(clause);
        checkDeleteCalled(review, table, tableName, clauses);
    }

    private <DbRow extends DbTableRow> void checkDeleteCalled(RowReview review, DbTable<DbRow> table,
                                   String tableName, ArrayList<RowEntry<DbRow, ?>> clauses) {
        setToCapture(table, tableName);

        checkNumberCaptured(0);

        mDeleter.deleteReviewFromDb(review, mTagsManager, mDb, mTransactor);

        checkNumberCaptured(clauses.size());
        checkCaptures(clauses);
    }

    private void checkNumberCaptured(int num) {
        assertThat(mTransactor.mTablesCaptured, is(num));
        assertThat(mTransactor.mCapturedClauses.size(), is(num));
    }

    private void setToCapture(DbTable<?> table, String tableName) {
        when(table.getName()).thenReturn(tableName);
        mTransactor.mCaptureTableName = tableName;
    }

    private RowReview newRowReview() {
        return new RowReviewImpl(RandomReview.nextReview());
    }

    private <DbRow extends DbTableRow, T> RowEntry<DbRow, T> asClause(Class<DbRow> rowClass,
                                                                      ColumnInfo<T> col, T entry) {
        return new RowEntryImpl<>(rowClass, col, entry);
    }

    private class Transactor implements TableTransactor {
        private String mCaptureTableName;
        private int mTablesCaptured = 0;
        private ArrayList<RowEntry<?, ?>> mCapturedClauses = new ArrayList<>();

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
                                                                                 RowEntry<DbRow, Type>
                                                                                         clause,
                                                                                 FactoryDbTableRow rowFactory) {
            TableRowList<DbRow> dbRows = new TableRowList<>();
            if (!table.equals(mDb.getReviewsTable())) {
                return dbRows;
            }

            if (clause.getColumnName().equals(RowReview.AUTHOR_ID.getName())) {
                return (TableRowList<DbRow>) mWhereRows;
            }

            return dbRows;
        }

        @Override
        public <DbRow extends DbTableRow, Type> int deleteRowsWhere(DbTable<DbRow> table,
                                                                     RowEntry<DbRow, Type> clause) {
            if (table.getName() != null && table.getName().equals(mCaptureTableName)) {
                mTablesCaptured++;
                mCapturedClauses.add(clause);
            }

            return 0;
        }

        @Override
        public <DbRow extends DbTableRow> boolean insertRow(DbRow row, DbTable<DbRow> table) {
            fail();
            return false;
        }

        @Override
        public <DbRow extends DbTableRow> boolean insertOrReplaceRow(DbRow row, DbTable<DbRow> table) {
            fail();
            return false;
        }

        @Override
        public boolean isIdInTable(String id, DbColumnDefinition idCol, DbTable<?> table) {
            fail();
            return false;
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
        public boolean tagsItem(String id, String tag) {
            fail();
            return false;
        }

        @Override
        public void clearTags(String id) {
            mTags = new ItemTagList();
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
