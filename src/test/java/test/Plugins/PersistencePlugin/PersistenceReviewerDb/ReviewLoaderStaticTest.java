
/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.PersistenceReviewerDb;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDbPlugin
        .Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Implementation.ReviewLoaderStatic;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Implementation.RowEntryImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Implementation.TableAuthors;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Implementation.TableComments;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Implementation.TableFacts;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Implementation.TableImages;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Implementation.TableLocations;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Implementation.TableReviews;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Implementation.TableRowList;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Implementation.TableTags;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Interfaces.ReviewRecreater;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Interfaces.ReviewerDbReadable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb
        .Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb
        .Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb
        .Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb
        .Interfaces.RowEntry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import test.TestUtils.DataEquivalence;
import test.TestUtils.RandomReview;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Created by: Rizwan Choudrey
 * On: 26/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class ReviewLoaderStaticTest {
    private ReviewLoaderStatic mLoader;
    private Recreater mRecreater;
    private FactoryDbTableRow mRowFactory;
    private Review mReview;
    private Db mDb;

    @Mock
    private TableTransactor mTransactor;

    @Before
    public void setUp() {
        mRowFactory = new FactoryReviewerDbTableRow();
        mRecreater = new Recreater();
        mLoader = new ReviewLoaderStatic(mRecreater, new DataValidator());
        mReview = RandomReview.nextReview();

        mDb = spy(new Db());

        setTableMocks();
    }

    @Test
    public void loadReviewCallsDbGetRowsWhereForCommentsWithReviewIdColEqualsIdValClause() {
        RowReview review = newRowReview();
        checkLoadClassDb(review, TableComments.NAME,
                asClause(RowComment.class, RowComment.REVIEW_ID, review.getReviewId().toString()));
    }

    @Test
    public void loadReviewCallsDbGetRowsWhereForFactsWithReviewIdColEqualsIdValClause() {
        RowReview review = newRowReview();
        checkLoadClassDb(review, TableFacts.NAME,
                asClause(RowFact.class, RowFact.REVIEW_ID, review.getReviewId().toString()));
    }

    @Test
    public void loadReviewCallsDbGetRowsWhereForImagesWithReviewIdColEqualsIdValClause() {
        RowReview review = newRowReview();
        checkLoadClassDb(review, TableImages.NAME,
                asClause(RowImage.class, RowImage.REVIEW_ID, review.getReviewId().toString()));
    }

    @Test
    public void loadReviewCallsDbGetRowsWhereForLocationsWithReviewIdColEqualsIdValClause() {
        RowReview review = newRowReview();
        checkLoadClassDb(review, TableLocations.NAME, asClause(RowLocation.class,
                RowLocation.REVIEW_ID, review.getReviewId().toString()));
    }

    @Test
    public void loadReviewCallsDbLoadReviewsWhereForCriteriaWithParentIdColEqualsIdValClause() {
        RowReview review = newRowReview();
        checkLoadClassDb(review, TableReviews.NAME,
                asClause(RowReview.class, RowReview.PARENT_ID, review.getReviewId().toString()));
    }

    @Test
    public void loadReviewCallsDbGetUniqueWhereForAuthorWithUserIdColEqualsIdValClause() {
        RowReview review = newRowReview();
        checkLoadClassDb(review, TableAuthors.NAME,
                asClause(RowAuthor.class, RowAuthor.USER_ID, review.getAuthorId()));
    }

    @Test
    public void recreateReviewIsPassedCorrectReviewDataHolder() {
        RowReview review = newRowReview();
        mLoader.loadReview(review, mDb, mTransactor);
        checkReviewEquivalence(mRecreater.mHolder);
    }

    private void checkReviewEquivalence(ReviewDataHolder data) {
        assertThat(data.getAuthor().getUserId(), is(mReview.getAuthor().getUserId()));
        assertThat(data.getAuthor().getName(), is(mReview.getAuthor().getName()));
        assertThat(data.getSubject(), is(mReview.getSubject().getSubject()));
        assertThat(data.getRating(), is(mReview.getRating().getRating()));
        assertThat(data.getRatingWeight(), is(mReview.getRating().getRatingWeight()));
        assertThat(data.getPublishDate().getTime(), is(mReview.getPublishDate().getTime()));

        IdableList<? extends DataComment> reviewComments = mReview.getComments();
        int i = 0;
        for (DataComment comment : data.getComments()) {
            DataEquivalence.checkEquivalence(comment, reviewComments.getItem(i++));
        }
        assertThat(i, is(reviewComments.size()));

        IdableList<? extends DataFact> reviewFacts = mReview.getFacts();
        i = 0;
        for (DataFact fact : data.getFacts()) {
            DataEquivalence.checkEquivalence(fact, reviewFacts.getItem(i++));
        }
        assertThat(i, is(reviewFacts.size()));

        IdableList<? extends DataLocation> reviewLocations = mReview.getLocations();
        i = 0;
        for (DataLocation location : data.getLocations()) {
            DataEquivalence.checkEquivalence(location, reviewLocations.getItem(i++));
        }
        assertThat(i, is(reviewLocations.size()));

        IdableList<? extends DataImage> reviewImages = mReview.getImages();
        i = 0;
        for (DataImage image : data.getImages()) {
            DataEquivalence.checkEquivalence(image, reviewImages.getItem(i++));
        }
        assertThat(i, is(reviewImages.size()));
    }

    private void setTableMocks() {
        TableImages images = mock(TableImages.class);
        when(mDb.getImagesTable()).thenReturn(images);
        when(images.getName()).thenReturn(TableImages.NAME);
        when(images.getRowClass()).thenReturn(RowImage.class);

        TableFacts facts = mock(TableFacts.class);
        when(mDb.getFactsTable()).thenReturn(facts);
        when(facts.getName()).thenReturn(TableFacts.NAME);
        when(facts.getRowClass()).thenReturn(RowFact.class);

        TableLocations locations = mock(TableLocations.class);
        when(mDb.getLocationsTable()).thenReturn(locations);
        when(locations.getName()).thenReturn(TableLocations.NAME);
        when(locations.getRowClass()).thenReturn(RowLocation.class);

        TableComments comments = mock(TableComments.class);
        when(mDb.getCommentsTable()).thenReturn(comments);
        when(comments.getName()).thenReturn(TableComments.NAME);
        when(comments.getRowClass()).thenReturn(RowComment.class);

        TableReviews reviews = mock(TableReviews.class);
        when(mDb.getReviewsTable()).thenReturn(reviews);
        when(reviews.getName()).thenReturn(TableReviews.NAME);
        when(reviews.getRowClass()).thenReturn(RowReview.class);

        TableAuthors authors = mock(TableAuthors.class);
        when(mDb.getAuthorsTable()).thenReturn(authors);
        when(authors.getName()).thenReturn(TableAuthors.NAME);
        when(authors.getRowClass()).thenReturn(RowAuthor.class);

        TableTags tags = mock(TableTags.class);
        when(mDb.getTagsTable()).thenReturn(tags);
        when(tags.getName()).thenReturn(TableTags.NAME);
        when(tags.getRowClass()).thenReturn(RowTag.class);
    }

    private <DbRow extends DbTableRow> RowEntry<DbRow, ?> asClause(Class<DbRow> rowClass,
                                                                   ColumnInfo<String> column,
                                                                   String value) {
        return new RowEntryImpl<>(rowClass, column, value);
    }

    private <DbRow extends DbTableRow> void checkLoadClassDb(RowReview review,
                                                             String tableName,
                                                             RowEntry<DbRow, ?> clause) {
        mDb.mCaptureTableName = tableName;

        assertThat(mDb.mTableCaptured, is(false));
        assertThat(mDb.mCapturedClause, is(nullValue()));

        mLoader.loadReview(review, mDb, mTransactor);

        assertThat(mDb.mTableCaptured, is(true));
        assertThat(mDb.mCapturedClause.equals(clause), is(true));
    }

    private RowReview newRowReview() {
        return mRowFactory.newRow(RowReview.class, mReview);
    }

    private class Recreater implements ReviewRecreater {
        private ReviewDataHolder mHolder;

        @Override
        public Review recreateReview(ReviewDataHolder review) {
            mHolder = review;
            return RandomReview.nextReview();
        }
    }

    private class Db implements ReviewerDbReadable {
        private String mCaptureTableName;
        private boolean mTableCaptured = false;
        private RowEntry<?, ?> mCapturedClause;

        @Override
        public <DbRow extends DbTableRow, Type> ArrayList<Review>
        loadReviewsWhere(DbTable<DbRow> table, RowEntry<DbRow, Type> clause,
                         TableTransactor transactor) {
            if (table.getName().equals(TableReviews.NAME)) doCapture(table, clause);
            ArrayList<Review> crits = new ArrayList<>();
            for (DataCriterionReview criterion : mReview.getCriteria()) {
                crits.add(criterion.getReview());
            }
            return crits;
        }

        @Override
        public <DbRow extends DbTableRow, Type> DbRow getUniqueRowWhere(DbTable<DbRow> table,
                                                                        RowEntry<DbRow, Type> clause,
                                                                        TableTransactor transactor) {

            if (table.getName().equals(TableAuthors.NAME)) doCapture(table, clause);

            return mRowFactory.newRow(table.getRowClass(), mReview.getAuthor());
        }

        @Override
        public <DbRow extends DbTableRow> TableRowList<DbRow> loadTable(DbTable<DbRow> table, TableTransactor transactor) {
            fail();
            return null;
        }

        @Override
        public <DbRow extends DbTableRow, Type> TableRowList<DbRow>
        getRowsWhere(DbTable<DbRow> table, RowEntry<DbRow, Type> clause,
                     TableTransactor transactor) {
            String name = table.getName();
            if (!name.equals(TableAuthors.NAME) && !name.equals(TableAuthors.NAME)) {
                doCapture(table, clause);
            }

            TableRowList<DbRow> tableRowList = new TableRowList<>();
            Iterable<? extends HasReviewId> data = null;
            switch (name) {
                case TableComments.NAME:
                    data = mReview.getComments();
                    break;
                case TableFacts.NAME:
                    data = mReview.getFacts();
                    break;
                case TableImages.NAME:
                    data = mReview.getImages();
                    break;
                case TableLocations.NAME:
                    data = mReview.getLocations();
                    break;
                default:
                    fail();
                    break;
            }

            for (HasReviewId datum : data) {
                tableRowList.add(mRowFactory.newRow(table.getRowClass(), datum, 123));
            }

            return tableRowList;
        }

        @Override
        public TableTransactor beginReadTransaction() {
            fail();
            return null;
        }

        @Override
        public void endTransaction(TableTransactor db) {
            fail();
        }

        @Override
        public DbTable<RowReview> getReviewsTable() {
            return null;
        }

        @Override
        public DbTable<RowComment> getCommentsTable() {
            return null;
        }

        @Override
        public DbTable<RowFact> getFactsTable() {
            return null;
        }

        @Override
        public DbTable<RowLocation> getLocationsTable() {
            return null;
        }

        @Override
        public DbTable<RowImage> getImagesTable() {
            return null;
        }

        @Override
        public DbTable<RowAuthor> getAuthorsTable() {
            return null;
        }

        @Override
        public DbTable<RowTag> getTagsTable() {
            return null;
        }

        @Override
        public ArrayList<DbTable<? extends DbTableRow>> getTables() {
            return null;
        }

        @Override
        public ArrayList<String> getTableNames() {
            return null;
        }

        private <DbRow extends DbTableRow, Type> void doCapture(DbTable<DbRow> table, RowEntry
                <DbRow, Type> clause) {
            if (table.getName() != null && table.getName().equals(mCaptureTableName)) {
                mTableCaptured = true;
                mCapturedClause = clause;
            }
        }
    }
}
