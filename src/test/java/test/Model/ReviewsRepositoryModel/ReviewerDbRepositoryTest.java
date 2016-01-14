package test.Model.ReviewsRepositoryModel;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel.ReviewerDbRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

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
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private ReviewerDb mDb;
    private ReviewerDbRepository mRepo;

    @Before
    public void setup() {
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
        TableTransactor mockDb = mockReadTransaction();
        mRepo.getReview(id);
        verify(mDb).loadReviewsWhere(mockDb, RowReview.COLUMN_REVIEW_ID, id.toString());
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
        TableTransactor mockDb = mockReadTransaction();
        mRepo.getReviews();
        verify(mDb).loadReviewsWhere(mockDb, RowReview.COLUMN_PARENT_ID, null);
    }

    @Test
    public void getReviewsReturnsResultOfLoadReviewsFromDbWhere() {
        Review review = RandomReview.nextReview();
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(review);
        reviews.add(review);

        TableTransactor mockDb = mockReadTransaction();
        when(mDb.loadReviewsWhere(mockDb, RowReview.COLUMN_PARENT_ID, null)).thenReturn
                (reviews);
        assertThat(mRepo.getReviews(), is(reviews));
    }

    @Test
    public void removeReviewRemovesReviewToDb() {
        TableTransactor mockDb = mockWriteTransaction();
        ReviewId id = RandomReviewId.nextReviewId();
        mRepo.removeReview(id);
        verify(mDb).deleteReviewFromDb(id.toString(), mockDb);
    }

    @Test
    public void removeReviewCallsObserversOnSuccessfulRemovingOfReview() {
        ReviewsRepositoryObserver observer1 = mock(ReviewsRepositoryObserver.class);
        ReviewsRepositoryObserver observer2 = mock(ReviewsRepositoryObserver.class);
        mRepo.registerObserver(observer1);
        mRepo.registerObserver(observer2);

        ReviewId id = RandomReviewId.nextReviewId();
        TableTransactor mockDb = mockWriteTransaction();
        when(mDb.deleteReviewFromDb(id.toString(), mockDb)).thenReturn(true);

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
        when(mDb.deleteReviewFromDb(id.toString(), mockDb)).thenReturn(false);

        mRepo.removeReview(id);

        verifyZeroInteractions(observer1, observer2);
    }

    private void mockLoadFromDb(ReviewId id, ArrayList<Review> reviews) {
        TableTransactor mockDb = mockReadTransaction();
        when(mDb.loadReviewsWhere(mockDb, RowReview.COLUMN_REVIEW_ID, id.toString()))
                .thenReturn(reviews);
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
}
