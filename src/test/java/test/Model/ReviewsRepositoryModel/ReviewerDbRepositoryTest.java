/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.ReviewsRepositoryModel;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.TagsModel.Implementation.ItemTagImpl;
import com.chdryra.android.reviewer.Model.TagsModel.Implementation.TagsManagerImpl;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.ReviewerDbRepository;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.RowEntryImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.RowTagImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.TableReviews;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.TableRowList;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.TableTags;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.RelationalDbPlugin.Api.TableTransactor;
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

import test.TestUtils.RandomReview;
import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

/**
 * Created by: Rizwan Choudrey
 * On: 21/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class ReviewerDbRepositoryTest {
    public static final RowEntryImpl<RowReview, String> REVIEW_CLAUSE
            = new RowEntryImpl<>(RowReview.class, RowReview.PARENT_ID, null);
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private ReviewerDb mDb;
    @Mock
    private TableReviews mReviewsTable;
    @Mock
    private TableTags mTagsTable;
    private TagsManager mTagsManager;
    private TableRowList<RowTag> mTags;
    private ReviewerDbRepository mRepo;
    @Mock
    private TableTransactor mTransactor;

    @Before
    public void setup() {
        when(mDb.getReviewsTable()).thenReturn(mReviewsTable);
        when(mDb.getTagsTable()).thenReturn(mTagsTable);
        mTagsManager = new TagsManagerImpl();
        mRepo = new ReviewerDbRepository(mDb, mTagsManager);
        mTags = new TableRowList<>();
    }

    @Test
    public void getTagsManagerReturnsDatabasesTagsManager() {
        assertThat(mRepo.getTagsManager(), is(mTagsManager));
    }

    @Test
    public void addReviewAddsReviewToDb() {
        TableTransactor mockDb = mockWriteTransaction();
        Review review = RandomReview.nextReview();
        mRepo.addReview(review);
        verify(mDb).addReviewToDb(review, mTagsManager, mockDb);
    }

    @Test
    public void addReviewCallsObserversOnSuccessfulAddingOfReview() {
        ReviewsRepositoryObserver observer1 = mock(ReviewsRepositoryObserver.class);
        ReviewsRepositoryObserver observer2 = mock(ReviewsRepositoryObserver.class);
        mRepo.registerObserver(observer1);
        mRepo.registerObserver(observer2);

        Review review = RandomReview.nextReview();
        TableTransactor mockDb = mockWriteTransaction();
        when(mDb.addReviewToDb(review, mTagsManager, mockDb)).thenReturn(true);

        mRepo.addReview(review);

        verify(observer1).onReviewAdded(review);
        verify(observer2).onReviewAdded(review);
    }

    @Test
    public void addReviewDoesNotCallsObserversOnUnsuccessfulAddingOfReview() {
        ReviewsRepositoryObserver observer1 = mock(ReviewsRepositoryObserver.class);
        ReviewsRepositoryObserver observer2 = mock(ReviewsRepositoryObserver.class);
        mRepo.registerObserver(observer1);
        mRepo.registerObserver(observer2);

        Review review = RandomReview.nextReview();
        TableTransactor mockDb = mockWriteTransaction();
        when(mDb.addReviewToDb(review, mTagsManager, mockDb)).thenReturn(false);

        mRepo.addReview(review);

        verifyZeroInteractions(observer1, observer2);
    }

    @Test
    public void getReviewCallsLoadReviewsFromDbWhere() {
        ReviewId id = RandomReviewId.nextReviewId();
        RowEntry<RowReview, String> clause
                = asClause(RowReview.class, RowReview.REVIEW_ID, id.toString());
        TableTransactor mockTransactor = mockReadTransaction();
        mRepo.getReview(id);
        verify(mDb).loadReviewsWhere(mReviewsTable, clause, mockTransactor);
    }

    @Test
    public void getReviewReturnsNullIfNoReviewInDb() {
        ReviewId id = RandomReviewId.nextReviewId();
        ArrayList<Review> reviews = new ArrayList<>();
        mockLoadFromDb(id, reviews);
        assertThat(mRepo.getReview(id), is(nullValue()));
    }

    @Test
    public void getReviewReturnsReviewIfReviewInDb() {
        Review review = RandomReview.nextReview();
        ReviewId id = review.getReviewId();
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(review);
        mockLoadFromDb(id, reviews);
        assertThat(mRepo.getReview(id), is(review));
    }

    @Test
    public void getReviewLoadsTagsIfNecessary() {
        Review review = RandomReview.nextReview();
        ReviewId id = review.getReviewId();

        String tagString = RandomString.nextWord();
        ItemTagImpl tag = new ItemTagImpl(tagString, id.toString());
        mTags.add(new RowTagImpl(tag));

        assertThat(mTagsManager.getTags(id.toString()).size(), is(0));
        TableTransactor transactor = mockReadTransaction();

        mRepo.getReview(id);

        verify(mDb).loadTable(mDb.getTagsTable(), transactor);
        ItemTagCollection tags = mTagsManager.getTags(id.toString());
        assertThat(tags.size(), is(1));
        assertThat(tags.getItemTag(0), is((ItemTag) tag));
    }

    @Test
    public void getReviewLoadsTagsOnlyOnce() {
        Review review = RandomReview.nextReview();
        ReviewId id = review.getReviewId();

        String tagString = RandomString.nextWord();
        ItemTagImpl tag = new ItemTagImpl(tagString, id.toString());
        mTags.add(new RowTagImpl(tag));

        TableTransactor transactor = mockReadTransaction();

        mRepo.getReview(id);
        mRepo.getReview(id);

        verify(mDb, atMost(1)).loadTable(mDb.getTagsTable(), transactor);
    }

    @Test
    public void getReviewThrowsExceptionIfMoreThanOneReviewFoundInDbWithId() {
        expectedException.expect(IllegalStateException.class);

        Review review = RandomReview.nextReview();
        ReviewId id = review.getReviewId();
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(review);
        reviews.add(review);

        mockLoadFromDb(id, reviews);

        mRepo.getReview(id);
    }

    @Test
    public void getReviewsCallsLoadReviewsFromDbWhere() {
        TableTransactor mockTransactor = mockReadTransaction();
        mRepo.getReviews();
        verify(mDb).loadReviewsWhere(mReviewsTable, REVIEW_CLAUSE, mockTransactor);
    }

    @Test
    public void getReviewsReturnsResultOfLoadReviewsFromDbWhere() {
        Review review = RandomReview.nextReview();
        Collection<Review> reviews = new ArrayList<>();
        reviews.add(review);
        reviews.add(review);

        TableTransactor mockDb = mockReadTransaction();
        when(mDb.loadReviewsWhere(mReviewsTable, REVIEW_CLAUSE, mockDb)).thenReturn(reviews);

        assertThat(mRepo.getReviews(), is(reviews));
    }

    @Test
    public void removeReviewRemovesReviewToDb() {
        TableTransactor mockTransactor = mockWriteTransaction();
        ReviewId id = RandomReviewId.nextReviewId();
        mRepo.removeReview(id);
        verify(mDb).deleteReviewFromDb(id, mTagsManager, mockTransactor);
    }

    @Test
    public void removeReviewCallsObserversOnSuccessfulRemovingOfReview() {
        ReviewsRepositoryObserver observer1 = mock(ReviewsRepositoryObserver.class);
        ReviewsRepositoryObserver observer2 = mock(ReviewsRepositoryObserver.class);
        mRepo.registerObserver(observer1);
        mRepo.registerObserver(observer2);

        ReviewId id = RandomReviewId.nextReviewId();
        TableTransactor mockDb = mockWriteTransaction();
        when(mDb.deleteReviewFromDb(id, mTagsManager, mockDb)).thenReturn(true);

        mRepo.removeReview(id);

        verify(observer1).onReviewRemoved(id);
        verify(observer2).onReviewRemoved(id);
    }

    @Test
    public void removeReviewDoesNotCallsObserversOnUnsuccessfulRemovingOfReview() {
        ReviewsRepositoryObserver observer1 = mock(ReviewsRepositoryObserver.class);
        ReviewsRepositoryObserver observer2 = mock(ReviewsRepositoryObserver.class);
        mRepo.registerObserver(observer1);
        mRepo.registerObserver(observer2);

        ReviewId id = RandomReviewId.nextReviewId();
        TableTransactor mockDb = mockWriteTransaction();
        when(mDb.deleteReviewFromDb(id, mTagsManager, mockDb)).thenReturn(false);

        mRepo.removeReview(id);

        verifyZeroInteractions(observer1, observer2);
    }

    private TableTransactor mockLoadFromDb(ReviewId id, ArrayList<Review> reviews) {
        TableTransactor mockDb = mockReadTransaction();
        RowEntry<RowReview, String> clause
                = asClause(RowReview.class, RowReview.REVIEW_ID, id.toString());
        when(mDb.loadReviewsWhere(mReviewsTable, clause, mockDb)).thenReturn(reviews);
        return mockDb;
    }

    private TableTransactor mockReadTransaction() {
        when(mDb.beginReadTransaction()).thenReturn(mTransactor);
        when(mDb.loadTable(mTagsTable, mTransactor)).thenReturn(mTags);
        return mTransactor;
    }

    private TableTransactor mockWriteTransaction() {
        when(mDb.beginWriteTransaction()).thenReturn(mTransactor);
        return mTransactor;
    }

    private <DbRow extends DbTableRow, T> RowEntry<DbRow, T> asClause(Class<DbRow>rowClass,
                                                                      ColumnInfo<T> column,
                                                                      @Nullable T value) {
        return new RowEntryImpl<>(rowClass, column, value);
    }
}
