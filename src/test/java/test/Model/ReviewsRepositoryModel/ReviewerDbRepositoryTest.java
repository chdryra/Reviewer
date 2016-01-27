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
import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel.ReviewerDbRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.RowEntryImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableReviews;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowReview;

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
    public static final RowEntryImpl<String> REVIEW_CLAUSE = new RowEntryImpl<>(RowReview
            .PARENT_ID, null);
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private ReviewerDb mDb;
    @Mock
    private TableReviews mReviewsTable;

    private ReviewerDbRepository mRepo;

    @Before
    public void setup() {
        when(mDb.getReviewsTable()).thenReturn(mReviewsTable);
        mRepo = new ReviewerDbRepository(mDb);
    }

    @Test
    public void getTagsManagerReturnsDatabasesTagsManager() {
        TagsManager mockManager = mock(TagsManager.class);
        when(mDb.getTagsManager()).thenReturn(mockManager);
        assertThat(mRepo.getTagsManager(), is(mockManager));
    }

    @Test
    public void addReviewAddsReviewToDb() {
        TableTransactor mockDb = mockWriteTransaction();
        Review review = RandomReview.nextReview();
        mRepo.addReview(review);
        verify(mDb).addReviewToDb(review, mockDb);
    }

    @Test
    public void addReviewCallsObserversOnSuccessfulAddingOfReview() {
        ReviewsRepositoryObserver observer1 = mock(ReviewsRepositoryObserver.class);
        ReviewsRepositoryObserver observer2 = mock(ReviewsRepositoryObserver.class);
        mRepo.registerObserver(observer1);
        mRepo.registerObserver(observer2);

        Review review = RandomReview.nextReview();
        TableTransactor mockDb = mockWriteTransaction();
        when(mDb.addReviewToDb(review, mockDb)).thenReturn(true);

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
        when(mDb.addReviewToDb(review, mockDb)).thenReturn(false);

        mRepo.addReview(review);

        verifyZeroInteractions(observer1, observer2);
    }

    @Test
    public void getReviewCallsLoadReviewsFromDbWhere() {
        ReviewId id = RandomReviewId.nextReviewId();
        RowEntry<String> clause = asClause(RowReview.REVIEW_ID, id.toString());
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
        verify(mDb).deleteReviewFromDb(id, mockTransactor);
    }

    @Test
    public void removeReviewCallsObserversOnSuccessfulRemovingOfReview() {
        ReviewsRepositoryObserver observer1 = mock(ReviewsRepositoryObserver.class);
        ReviewsRepositoryObserver observer2 = mock(ReviewsRepositoryObserver.class);
        mRepo.registerObserver(observer1);
        mRepo.registerObserver(observer2);

        ReviewId id = RandomReviewId.nextReviewId();
        TableTransactor mockDb = mockWriteTransaction();
        when(mDb.deleteReviewFromDb(id, mockDb)).thenReturn(true);

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
        when(mDb.deleteReviewFromDb(id, mockDb)).thenReturn(false);

        mRepo.removeReview(id);

        verifyZeroInteractions(observer1, observer2);
    }

    private void mockLoadFromDb(ReviewId id, ArrayList<Review> reviews) {
        TableTransactor mockDb = mockReadTransaction();
        RowEntry<String> clause = asClause(RowReview.REVIEW_ID, id.toString());
        when(mDb.loadReviewsWhere(mReviewsTable, clause, mockDb)).thenReturn(reviews);
    }

    private TableTransactor mockReadTransaction() {
        TableTransactor mockDb = mock(TableTransactor.class);
        when(mDb.beginReadTransaction()).thenReturn(mockDb);
        return mockDb;
    }

    private TableTransactor mockWriteTransaction() {
        TableTransactor mockDb = mock(TableTransactor.class);
        when(mDb.beginWriteTransaction()).thenReturn(mockDb);
        return mockDb;
    }

    private <T> RowEntry<T> asClause(ColumnInfo<T> column, @Nullable T value) {
        return new RowEntryImpl<>(column, value);
    }
}
