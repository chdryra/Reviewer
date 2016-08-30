/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.LocalReviewerDb;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.TableTransactor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.RowAuthorImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.RowCommentImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.RowCriterionImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.RowEntryImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.RowFactImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.RowLocationImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.RowReviewImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.TableAuthors;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.TableComments;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Implementation.TableCriteria;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.TableFacts;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.TableImages;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.TableLocations;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.TableReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.TableRowList;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.TableTags;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewerDbReadable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowCriterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import test.TestUtils.DataEquivalence;
import test.TestUtils.RandomReview;
import test.TestUtils.RandomReviewData;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

/**
 * Created by: Rizwan Choudrey
 * On: 29/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class ReviewDbTest {

    @Mock
    private ReviewerDbReadable mDb;
    @Mock
    private TableTransactor mTransactor;
    private FactoryReviewNode mFactory;

    @Before
    public void setUp() {
        mFactory = new FactoryReviewNode();
        when(mDb.getImagesTable()).thenReturn(mock(TableImages.class));
        when(mDb.getImagesTable().getRowClass()).thenReturn(RowImage.class);

        when(mDb.getFactsTable()).thenReturn(mock(TableFacts.class));
        when(mDb.getFactsTable().getRowClass()).thenReturn(RowFact.class);

        when(mDb.getLocationsTable()).thenReturn(mock(TableLocations.class));
        when(mDb.getLocationsTable().getRowClass()).thenReturn(RowLocation.class);

        when(mDb.getCriteriaTable()).thenReturn(mock(TableCriteria.class));
        when(mDb.getCriteriaTable().getRowClass()).thenReturn(RowCriterion.class);

        when(mDb.getCommentsTable()).thenReturn(mock(TableComments.class));
        when(mDb.getCommentsTable().getRowClass()).thenReturn(RowComment.class);

        when(mDb.getReviewsTable()).thenReturn(mock(TableReviews.class));
        when(mDb.getReviewsTable().getRowClass()).thenReturn(RowReview.class);

        when(mDb.getAuthorsTable()).thenReturn(mock(TableAuthors.class));
        when(mDb.getAuthorsTable().getRowClass()).thenReturn(RowAuthor.class);

        when(mDb.getTagsTable()).thenReturn(mock(TableTags.class));
        when(mDb.getTagsTable().getRowClass()).thenReturn(RowTag.class);

        when(mDb.beginReadTransaction()).thenReturn(mTransactor);
    }

    @Test
    public void checkRowValueGetters() {
        RowReview row = newRow();
        ReviewDb reviewDb = new ReviewDb(row, mDb, mFactory);

        ReviewId reviewId = row.getReviewId();
        assertThat(reviewDb.getReviewId(), is(reviewId));
        assertThat(reviewDb.getSubject().getReviewId(), is(reviewId));
        assertThat(reviewDb.getSubject().getSubject(), is(row.getSubject()));
        assertThat(reviewDb.getRating().getReviewId(), is(reviewId));
        assertThat(reviewDb.getRating().getRating(), is(row.getRating()));
        assertThat(reviewDb.getRating().getRatingWeight(), is(row.getRatingWeight()));
        assertThat(reviewDb.getPublishDate().getReviewId(), is(reviewId));
        assertThat(reviewDb.getPublishDate().getTime(), is(row.getTime()));
        assertThat(reviewDb.isRatingAverageOfCriteria(), is(row.isRatingIsAverage()));
    }

    @Test
    public void getAuthorLoadsAuthorFromAuthorsTable() {
        RowReview row = newRow();
        ReviewDb reviewDb = new ReviewDb(row, mDb, mFactory);

        NamedAuthor author = new DatumAuthor(row.getReviewId(),
                RandomString.nextWord(), new AuthorIdParcelable(row.getAuthorId()));
        RowAuthor rowAuthor = new RowAuthorImpl(author);

        DbTable<RowAuthor> authorsTable = mDb.getAuthorsTable();
        RowEntry<RowAuthor, String> authorClause
                = asClause(RowAuthor.class, RowAuthor.AUTHOR_ID, row.getAuthorId());
        when(mDb.getUniqueRowWhere(authorsTable, authorClause, mTransactor)).thenReturn(rowAuthor);

        DataAuthor authorOut = reviewDb.getAuthorId();

        verify(mDb).getUniqueRowWhere(authorsTable, authorClause, mTransactor);
        assertThat(authorOut, is(author));
    }

    @Test
    public void getCriteriaLoadsCriterriaFromCriteriaTable() {
        Review review = RandomReview.nextReview();

        TableRowList<RowCriterion> dataRows = new TableRowList<>();
        dataRows.add(new RowCriterionImpl(RandomReviewData.nextCriterion(review.getReviewId()), 1));
        dataRows.add(new RowCriterionImpl(RandomReviewData.nextCriterion(review.getReviewId()), 2));
        dataRows.add(new RowCriterionImpl(RandomReviewData.nextCriterion(review.getReviewId()), 3));

        DbTable<RowCriterion> dataTable = mDb.getCriteriaTable();

        RowReview row = newRow(review);
        ReviewDb reviewDb = new ReviewDb(row, mDb, mFactory);

        RowEntry<RowCriterion, String> dataClause = setUpDbReturn(dataRows, dataTable, RowFact
                .REVIEW_ID, row);

        IdableList<? extends DataCriterion> data = reviewDb.getCriteria();

        verify(mDb).getRowsWhere(dataTable, dataClause, mTransactor);
        assertThat(data.getReviewId(), is(review.getReviewId()));
        assertThat(data.size(), is(3));
        DataEquivalence.checkEquivalence(data.getItem(0), dataRows.getItem(0));
        DataEquivalence.checkEquivalence(data.getItem(1), dataRows.getItem(1));
        DataEquivalence.checkEquivalence(data.getItem(2), dataRows.getItem(2));
    }

    @Test
    public void getFactsLoadsFactsFromFactsTable() {
        Review review = RandomReview.nextReview();

        TableRowList<RowFact> dataRows = new TableRowList<>();
        dataRows.add(new RowFactImpl(RandomReviewData.nextFact(review.getReviewId()), 1));
        dataRows.add(new RowFactImpl(RandomReviewData.nextFact(review.getReviewId()), 2));
        dataRows.add(new RowFactImpl(RandomReviewData.nextFact(review.getReviewId()), 3));

        DbTable<RowFact> dataTable = mDb.getFactsTable();

        RowReview row = newRow(review);
        ReviewDb reviewDb = new ReviewDb(row, mDb, mFactory);
        
        RowEntry<RowFact, String> dataClause = setUpDbReturn(dataRows, dataTable, RowFact
                .REVIEW_ID, row);

        IdableList<? extends DataFact> data = reviewDb.getFacts();

        verify(mDb).getRowsWhere(dataTable, dataClause, mTransactor);
        assertThat(data.getReviewId(), is(review.getReviewId()));
        assertThat(data.size(), is(3));
        DataEquivalence.checkEquivalence(data.getItem(0), dataRows.getItem(0));
        DataEquivalence.checkEquivalence(data.getItem(1), dataRows.getItem(1));
        DataEquivalence.checkEquivalence(data.getItem(2), dataRows.getItem(2));
    }

    @Test
    public void getCommentsLoadsCommentsFromCommentsTable() {
        Review review = RandomReview.nextReview();

        TableRowList<RowComment> dataRows = new TableRowList<>();
        dataRows.add(new RowCommentImpl(RandomReviewData.nextComment(review.getReviewId()), 1));
        dataRows.add(new RowCommentImpl(RandomReviewData.nextComment(review.getReviewId()), 2));
        dataRows.add(new RowCommentImpl(RandomReviewData.nextComment(review.getReviewId()), 3));

        DbTable<RowComment> dataTable = mDb.getCommentsTable();

        RowReview row = newRow(review);
        ReviewDb reviewDb = new ReviewDb(row, mDb, mFactory);

        RowEntry<RowComment, String> dataClause = setUpDbReturn(dataRows, dataTable, RowComment.REVIEW_ID, row);

        IdableList<? extends DataComment> data = reviewDb.getComments();

        verify(mDb).getRowsWhere(dataTable, dataClause, mTransactor);
        assertThat(data.getReviewId(), is(review.getReviewId()));
        assertThat(data.size(), is(3));
        DataEquivalence.checkEquivalence(data.getItem(0), dataRows.getItem(0));
        DataEquivalence.checkEquivalence(data.getItem(1), dataRows.getItem(1));
        DataEquivalence.checkEquivalence(data.getItem(2), dataRows.getItem(2));
    }

    @Test
    public void getLocationsLoadsLocationsFromLocationsTable() {
        Review review = RandomReview.nextReview();

        TableRowList<RowLocation> dataRows = new TableRowList<>();
        dataRows.add(new RowLocationImpl(RandomReviewData.nextLocation(review.getReviewId()), 1));
        dataRows.add(new RowLocationImpl(RandomReviewData.nextLocation(review.getReviewId()), 2));
        dataRows.add(new RowLocationImpl(RandomReviewData.nextLocation(review.getReviewId()), 3));

        DbTable<RowLocation> dataTable = mDb.getLocationsTable();

        RowReview row = newRow(review);
        ReviewDb reviewDb = new ReviewDb(row, mDb, mFactory);

        RowEntry<RowLocation, String> dataClause = setUpDbReturn(dataRows, dataTable, RowLocation.REVIEW_ID, row);

        IdableList<? extends DataLocation> data = reviewDb.getLocations();

        verify(mDb).getRowsWhere(dataTable, dataClause, mTransactor);
        assertThat(data.getReviewId(), is(review.getReviewId()));
        assertThat(data.size(), is(3));
        DataEquivalence.checkEquivalence(data.getItem(0), dataRows.getItem(0));
        DataEquivalence.checkEquivalence(data.getItem(1), dataRows.getItem(1));
        DataEquivalence.checkEquivalence(data.getItem(2), dataRows.getItem(2));
    }

    @Test
    public void getImagesLoadsImagesFromImagesTable() {
        //TODO revisit test if Android mocks Bitmaps
        Review review = RandomReview.nextReview();

        TableRowList<RowImage> dataRows = new TableRowList<>();

        DbTable<RowImage> dataTable = mDb.getImagesTable();

        RowReview row = newRow(review);
        ReviewDb reviewDb = new ReviewDb(row, mDb, mFactory);

        RowEntry<RowImage, String> dataClause = setUpDbReturn(dataRows, dataTable, RowImage.REVIEW_ID, row);

        IdableList<? extends DataImage> data = reviewDb.getImages();

        verify(mDb).getRowsWhere(dataTable, dataClause, mTransactor);
        assertThat(data.getReviewId(), is(review.getReviewId()));
        assertThat(data.size(), is(0));
    }

    @Test
    public void getCoversLoadsCImagesFromImagesTableWithCoverClause() {
        //TODO revisit test if Android mocks Bitmaps
        Review review = RandomReview.nextReview();

        TableRowList<RowImage> dataRows = new TableRowList<>();

        DbTable<RowImage> dataTable = mDb.getImagesTable();

        RowReview row = newRow(review);
        ReviewDb reviewDb = new ReviewDb(row, mDb, mFactory);

        RowEntry<RowImage, Boolean> coverClause
                = asClause(dataTable.getRowClass(), RowImage.IS_COVER, true);
        when(mDb.getRowsWhere(dataTable, coverClause, mTransactor)).thenReturn(dataRows);

        IdableList<? extends DataImage> data = reviewDb.getCovers();

        verify(mDb).getRowsWhere(dataTable, coverClause, mTransactor);
        assertThat(data.getReviewId(), is(review.getReviewId()));
        assertThat(data.size(), is(0));
    }

    @NonNull
    private <T extends DbTableRow> RowEntry<T, String> setUpDbReturn(TableRowList<T> dataRows,
                                                                     DbTable<T> dataTable,
                                                                     ColumnInfo<String> idCol,
                                                                     RowReview row) {
        RowEntry<T, String> dataClause
                = asClause(dataTable.getRowClass(), idCol, row.getReviewId().toString());
        when(mDb.getRowsWhere(dataTable, dataClause, mTransactor)).thenReturn(dataRows);
        
        return dataClause;
    }

    private RowReview newRow(Review review) {
        return new RowReviewImpl(review);
    }

    private RowReview newRow() {
        return newRow(RandomReview.nextReview());
    }

    private <DbRow extends DbTableRow, T> RowEntry<DbRow, T> asClause(Class<DbRow> rowClass,
                                                                      ColumnInfo<T> column,
                                                                      T value) {
        return new RowEntryImpl<>(rowClass, column, value);
    }
}
