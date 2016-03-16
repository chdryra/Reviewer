/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.PersistenceReviewerDb;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Model.Implementation.TagsModel.ItemTagImpl;
import com.chdryra.android.reviewer.Model.Implementation.TagsModel.ItemTagList;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.ReviewInserterImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableAuthors;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableComments;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableFacts;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableImages;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableLocations;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableReviews;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableRowList;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.TableTags;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb.Interfaces.RowEntry;


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
public class ReviewInserterImplTest {
    @Mock
    private ReviewerDb mDb;
    private FactoryDbTableRow mRowFactory;
    private ReviewInserterImpl mInserter;
    private Transactor mTransactor;
    private ItemTagCollection mTags = new ItemTagList();
    private TagsMan mTagsManager;

    @Before
    public void setUp() {
        mRowFactory = new FactoryReviewerDbTableRow();
        mInserter = new ReviewInserterImpl(mRowFactory);
        mTransactor = new Transactor();
        when(mDb.getImagesTable()).thenReturn(mock(TableImages.class));
        when(mDb.getImagesTable().getRowClass()).thenReturn(RowImage.class);

        when(mDb.getFactsTable()).thenReturn(mock(TableFacts.class));
        when(mDb.getFactsTable().getRowClass()).thenReturn(RowFact.class);

        when(mDb.getLocationsTable()).thenReturn(mock(TableLocations.class));
        when(mDb.getLocationsTable().getRowClass()).thenReturn(RowLocation.class);

        when(mDb.getCommentsTable()).thenReturn(mock(TableComments.class));
        when(mDb.getCommentsTable().getRowClass()).thenReturn(RowComment.class);

        when(mDb.getReviewsTable()).thenReturn(mock(TableReviews.class));
        when(mDb.getReviewsTable().getRowClass()).thenReturn(RowReview.class);

        when(mDb.getAuthorsTable()).thenReturn(mock(TableAuthors.class));
        when(mDb.getAuthorsTable().getRowClass()).thenReturn(RowAuthor.class);

        when(mDb.getTagsTable()).thenReturn(mock(TableTags.class));
        when(mDb.getTagsTable().getRowClass()).thenReturn(RowTag.class);

        mTagsManager = new TagsMan();
    }

    @Test
    public void addReviewCallsTransactorInsertWithReviewTableAndRowsForReviewAndCriteria() {
        Review review = newReview();

        ArrayList<RowReview> rows = new ArrayList<>();
        rows.add(asRow(RowReview.class, review));
        rows.addAll(getExpectedRows(review.getCriteria(), RowReview.class, false));

        checkInsertCalled(review, mDb.getReviewsTable(), TableReviews.NAME, rows);
    }

    @Test
    public void addReviewCallsTransactorInsertWithCommentsTableWithCorrectRows() {
        Review review = newReview();
        ArrayList<RowComment> rows = getExpectedRows(review.getComments(), RowComment.class, true);
        checkInsertCalled(review, mDb.getCommentsTable(), TableComments.NAME, rows);
    }

    @Test
    public void addReviewCallsTransactorInsertWithFactsTableWithCorrectRows() {
        Review review = newReview();
        ArrayList<RowFact> rows = getExpectedRows(review.getFacts(), RowFact.class, true);
        checkInsertCalled(review, mDb.getFactsTable(), TableFacts.NAME, rows);
    }

    @Test
    public void addReviewCallsTransactorInsertWithLocationsTableWithCorrectRows() {
        Review review = newReview();
        ArrayList<RowLocation> rows = getExpectedRows(review.getLocations(), RowLocation.class, true);
        checkInsertCalled(review, mDb.getLocationsTable(), TableLocations.NAME, rows);
    }

    @Test
    public void addReviewCallsTransactorInsertOrReplaceWithImagesTableWithCorrectRows() {
        Review review = newReview();
        ArrayList<RowImage> rows = getExpectedRows(review.getImages(), RowImage.class, true);
        checkInsertCalled(review, mDb.getImagesTable(), TableImages.NAME, rows);
    }

    @Test
    public void addReviewCallsTransactorInsertOrReplaceWithTagsTableWithCorrectRows() {
        Review review = newReview();
        setTags(review);
        ArrayList<RowTag> rows = getExpectedRows(mTags, RowTag.class, false);
        checkInsertOrReplaceCalled(review, mDb.getTagsTable(), TableTags.NAME, rows);
    }

    @Test
    public void addReviewDoesNotCallTransactorInsertWhenAuthorAlreadyInTable() {
        Review review = newReview();
        mTransactor.mIsAuthorIdInTable = true;
        checkInsertNotCalled(review, mDb.getAuthorsTable(), TableAuthors.NAME);
    }

    @Test
    public void addReviewCallsTransactorInsertWhenAuthorNotInTable() {
        Review review = newReview();
        mTransactor.mIsAuthorIdInTable = false;
        ArrayList<RowAuthor> authors = new ArrayList<>();
        authors.add(asRow(RowAuthor.class, review.getAuthor()));
        checkInsertCalled(review, mDb.getAuthorsTable(), TableAuthors.NAME, authors);
    }

    private void setTags(Review review) {
        ItemTagImpl tag1 = new ItemTagImpl(RandomString.nextWord(), review.getReviewId().toString());
        tag1.addItemId(RandomReviewId.nextIdString());
        tag1.addItemId(RandomReviewId.nextIdString());
        tag1.addItemId(RandomReviewId.nextIdString());
        ItemTagImpl tag2 = new ItemTagImpl(RandomString.nextWord(), review.getReviewId().toString());
        tag2.addItemId(RandomReviewId.nextIdString());
        tag2.addItemId(RandomReviewId.nextIdString());
        ItemTagImpl tag3 = new ItemTagImpl(RandomString.nextWord(), review.getReviewId().toString());
        tag3.addItemId(RandomReviewId.nextIdString());
        mTags.add(tag1);
        mTags.add(tag2);
        mTags.add(tag3);
    }

    @NonNull
    private <T extends DbTableRow, D> ArrayList<T> getExpectedRows(Iterable<D> data,
                                                                   Class<T> rowClass,
                                                                   boolean indexed) {
        ArrayList<T> rows = new ArrayList<>();
        int i = 1;
        for(D datum : data) {
            rows.add(indexed ? asRow(rowClass, datum, i++) : asRow(rowClass, datum));
        }
        return rows;
    }

    private <T extends DbTableRow> void checkCaptures(ArrayList<T> rows) {
        checkNumberCaptured(rows.size());
        for (int i = 0; i < rows.size(); ++i) {
            assertThat(mTransactor.mCapturedRows.get(i).equals(rows.get(i)), is(true));
        }
    }

    private <T extends DbTableRow> void checkInsertOrReplaceCalled(Review review, DbTable<T> table,
                                                          String tableName, ArrayList<T> rows) {
        checkInsert(review, table, tableName, rows);
        assertThat(mTransactor.mInsertOrReplaceCalled, is(true));
    }

    private <T extends DbTableRow> void checkInsertCalled(Review review, DbTable<T> table,
                                   String tableName, ArrayList<T> rows) {
        checkInsert(review, table, tableName, rows);
        assertThat(mTransactor.mInsertOrReplaceCalled, is(false));
    }

    private <T extends DbTableRow> void checkInsert(Review review, DbTable<T> table, String tableName, ArrayList<T> rows) {
        setToCapture(table, tableName);

        checkNumberCaptured(0);

        mInserter.addReviewToDb(review, mTagsManager, mDb, mTransactor);

        checkNumberCaptured(rows.size());
        checkCaptures(rows);
    }

    private <T extends DbTableRow> void checkInsertNotCalled(Review review, DbTable<T> table,
                                                          String tableName) {
        setToCapture(table, tableName);

        checkNumberCaptured(0);

        mInserter.addReviewToDb(review, mTagsManager, mDb, mTransactor);

        checkNumberCaptured(0);
        assertThat(mTransactor.mInsertOrReplaceCalled, is(false));
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

    private <T extends DbTableRow, D> T asRow(Class<T> rowClass, D entry) {
        return mRowFactory.newRow(rowClass, entry);
    }

    private <T extends DbTableRow, D> T asRow(Class<T> rowClass, D entry, int index) {
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
        public <DbRow extends DbTableRow, Type> TableRowList<DbRow>
        getRowsWhere(DbTable<DbRow> table, RowEntry<DbRow, Type> clause,
                     FactoryDbTableRow rowFactory) {
            fail();
            return null;
        }

        @Override
        public <DbRow extends DbTableRow, Type> int deleteRowsWhere(DbTable<DbRow> table,
                                                                     RowEntry<DbRow, Type> clause) {
            fail();
            return 0;
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
        public <DbRow extends DbTableRow> boolean insertOrReplaceRow(DbRow row, DbTable<DbRow> table) {
            if (table.getName() != null && table.getName().equals(mCaptureTableName)) {
                mInsertOrReplaceCalled = true;
                mTablesCaptured++;
                mCapturedRows.add(row);

                return true;
            }

            return false;
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
        public boolean tagsItem(String id, String tag) {
            fail();
            return false;
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
