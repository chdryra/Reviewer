/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Persistence;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.TableTransactor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ColumnInfo;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ReviewerDbRepo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.RowEntryImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.RowTagImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableRowList;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.TableTags;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.mygenerallibrary.TagsModel.Implementation.ItemTagImpl;
import com.chdryra.android.mygenerallibrary.TagsModel.Implementation.TagsManagerImpl;
import com.chdryra.android.mygenerallibrary.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.mygenerallibrary.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.mygenerallibrary.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Implementation.RepoResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepoWriteable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryObserver;
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
    private ReviewerDbRepo mRepo;
    @Mock
    private TableTransactor mTransactor;

    @Before
    public void setup() {
        when(mDb.getReviewsTable()).thenReturn(mReviewsTable);
        when(mDb.getTagsTable()).thenReturn(mTagsTable);
        mTagsManager = new TagsManagerImpl();
        mRepo = new ReviewerDbRepo(mDb, mTagsManager);
        mTags = new TableRowList<>();
    }

    @Test
    public void getTagsManagerReturnsDatabasesTagsManager() {
        assertThat(mRepo.getTagsManager(), is(mTagsManager));
    }

    @Test
    public void addReviewAddsReviewToDbAndDoesNotReturnError() {
        final TableTransactor mockDb = mockWriteTransaction();
        final Review review = RandomReview.nextReview();
        when(mDb.addReviewToDb(review, mTagsManager, mockDb)).thenReturn(true);
        mRepo.addReview(review, new ReviewsRepoWriteable.Callback() {
            @Override
            public void onAddedToRepo(RepoResult result) {
                Review reviewPassed = result.getReview();
                assertThat(reviewPassed, is(review));
                verify(mDb).addReviewToDb(review, mTagsManager, mockDb);
                assertThat(result.isError(), is(false));
            }

            @Override
            public void onRemovedFromRepo(RepoResult result) {

            }
        });
    }

    @Test
    public void addReviewReturnsErrorIfAddingUnsuccessful() {
        final TableTransactor mockDb = mockWriteTransaction();
        Review review = RandomReview.nextReview();
        when(mDb.addReviewToDb(review, mTagsManager, mockDb)).thenReturn(false);
        mRepo.addReview(review, new ReviewsRepoWriteable.Callback() {
            @Override
            public void onAddedToRepo(RepoResult result) {
                assertThat(result.isError(), is(true));
            }

            @Override
            public void onRemovedFromRepo(RepoResult result) {

            }
        });
    }

    @Test
    public void addReviewCallsObserversOnSuccessfulAddingOfReview() {
        final ReviewsRepositoryObserver observer1 = mock(ReviewsRepositoryObserver.class);
        final ReviewsRepositoryObserver observer2 = mock(ReviewsRepositoryObserver.class);
        mRepo.registerObserver(observer1);
        mRepo.registerObserver(observer2);

        final Review review = RandomReview.nextReview();
        TableTransactor mockDb = mockWriteTransaction();
        when(mDb.addReviewToDb(review, mTagsManager, mockDb)).thenReturn(true);

        mRepo.addReview(review, new ReviewsRepoWriteable.Callback() {
            @Override
            public void onAddedToRepo(RepoResult result) {
                verify(observer1).onReviewAdded(review);
                verify(observer2).onReviewAdded(review);
            }

            @Override
            public void onRemovedFromRepo(RepoResult result) {

            }
        });
    }

    @Test
    public void addReviewDoesNotCallsObserversAndReturnsErrorOnUnsuccessfulAddingOfReview() {
        final ReviewsRepositoryObserver observer1 = mock(ReviewsRepositoryObserver.class);
        final ReviewsRepositoryObserver observer2 = mock(ReviewsRepositoryObserver.class);
        mRepo.registerObserver(observer1);
        mRepo.registerObserver(observer2);

        Review review = RandomReview.nextReview();
        TableTransactor mockDb = mockWriteTransaction();
        when(mDb.addReviewToDb(review, mTagsManager, mockDb)).thenReturn(false);

        mRepo.addReview(review, new ReviewsRepoWriteable.Callback() {
            @Override
            public void onAddedToRepo(RepoResult result) {
                verifyZeroInteractions(observer1, observer2);
                assertThat(result.isError(),is(true));
            }

            @Override
            public void onRemovedFromRepo(RepoResult result) {

            }
        });
    }

    @Test
    public void getReviewCallsLoadReviewsFromDbWhere() {
        ReviewId id = RandomReviewId.nextReviewId();
        final RowEntry<RowReview, String> clause
                = asClause(RowReview.class, RowReview.REVIEW_ID, id.toString());
        final TableTransactor mockTransactor = mockReadTransaction();
        mRepo.getReview(id, new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                verify(mDb).loadReviewsWhere(mReviewsTable, clause, mockTransactor);
            }
        });
    }

    @Test
    public void getReviewReturnsNullAndErrorIfNoReviewInDb() {
        ReviewId id = RandomReviewId.nextReviewId();
        ArrayList<Review> reviews = new ArrayList<>();
        mockLoadFromDb(id, reviews);
        mRepo.getReview(id, new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                assertThat(result.isError(),is(true));
                assertThat(result.getReview(), is(nullValue()));
            }
        });
    }

    @Test
    public void getReviewReturnsReviewIfReviewInDb() {
        final Review review = RandomReview.nextReview();
        ReviewId id = review.getReviewId();
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(review);
        mockLoadFromDb(id, reviews);
        mRepo.getReview(id, new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                assertThat(result.getReview(), is(review));
                assertThat(result.isError(),is(false));
            }
        });
    }

    @Test
    public void getReviewLoadsTagsIfNecessary() {
        Review review = RandomReview.nextReview();
        final ReviewId id = review.getReviewId();

        String tagString = RandomString.nextWord();
        final ItemTagImpl tag = new ItemTagImpl(tagString, id.toString());
        mTags.add(new RowTagImpl(tag));

        assertThat(mTagsManager.getTags(id.toString()).size(), is(0));

        final TableTransactor transactor = mockReadTransaction();
        mRepo.getReview(id, new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                verify(mDb).loadTable(mDb.getTagsTable(), transactor);
                ItemTagCollection tags = mTagsManager.getTags(id.toString());
                assertThat(tags.size(), is(1));
                assertThat(tags.getItemTag(0), is((ItemTag) tag));
            }
        });
    }

    @Test
    public void getReviewLoadsTagsOnlyOnce() {
        Review review = RandomReview.nextReview();
        ReviewId id = review.getReviewId();

        String tagString = RandomString.nextWord();
        ItemTagImpl tag = new ItemTagImpl(tagString, id.toString());
        mTags.add(new RowTagImpl(tag));

        final TableTransactor transactor = mockReadTransaction();

        mRepo.getReview(id, new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                verify(mDb, atMost(1)).loadTable(mDb.getTagsTable(), transactor);
            }
        });

        mRepo.getReview(id, new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                verify(mDb, atMost(1)).loadTable(mDb.getTagsTable(), transactor);
            }
        });
    }

    @Test
    public void getReviewReturnsErrorIfMoreThanOneReviewFoundInDbWithId() {
        Review review = RandomReview.nextReview();
        ReviewId id = review.getReviewId();
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(review);
        reviews.add(review);

        mockLoadFromDb(id, reviews);

        mRepo.getReview(id, new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                assertThat(result.isError(), is(true));
                assertThat(result.getReview(), is(nullValue()));
            }
        });
    }

    @Test
    public void getReviewsCallsLoadReviewsFromDbWhere() {
        final TableTransactor mockTransactor = mockReadTransaction();
        mRepo.getReviews(new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                verify(mDb).loadReviews(mockTransactor);
            }
        });
    }

    @Test
    public void getReviewsReturnsResultOfLoadReviewsFromDbWhere() {
        Review review = RandomReview.nextReview();
        final Collection<Review> reviews = new ArrayList<>();
        reviews.add(review);
        reviews.add(review);

        TableTransactor mockDb = mockReadTransaction();
        when(mDb.loadReviews(mockDb)).thenReturn(reviews);
        mRepo.getReviews(new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                assertThat(result.isError(),is(false));
                assertThat(result.getReviews(), is(reviews));
            }
        });
    }

    @Test
    public void removeReviewRemovesReviewFromDbAndDoesNotReturnError() {
        final TableTransactor mockTransactor = mockWriteTransaction();
        final ReviewId id = RandomReviewId.nextReviewId();
        when(mDb.deleteReviewFromDb(id, mTagsManager, mockTransactor)).thenReturn(true);
        mRepo.removeReview(id, new ReviewsRepoWriteable.Callback() {
            @Override
            public void onAddedToRepo(RepoResult result) {

            }

            @Override
            public void onRemovedFromRepo(RepoResult result) {
                verify(mDb).deleteReviewFromDb(id, mTagsManager, mockTransactor);
                assertThat(result.isError(), is(false));
            }
        });
    }

    @Test
    public void removeReviewReturnsErrorIfProblemDeleteing() {
        final TableTransactor mockTransactor = mockWriteTransaction();
        final ReviewId id = RandomReviewId.nextReviewId();
        when(mDb.deleteReviewFromDb(id, mTagsManager, mockTransactor)).thenReturn(false);
        mRepo.removeReview(id, new ReviewsRepoWriteable.Callback() {
            @Override
            public void onAddedToRepo(RepoResult result) {

            }

            @Override
            public void onRemovedFromRepo(RepoResult result) {
                assertThat(result.isError(), is(true));
            }
        });
    }

    @Test
    public void removeReviewCallsObserversOnSuccessfulRemovingOfReview() {
        final ReviewsRepositoryObserver observer1 = mock(ReviewsRepositoryObserver.class);
        final ReviewsRepositoryObserver observer2 = mock(ReviewsRepositoryObserver.class);
        mRepo.registerObserver(observer1);
        mRepo.registerObserver(observer2);

        final ReviewId id = RandomReviewId.nextReviewId();
        TableTransactor mockDb = mockWriteTransaction();
        when(mDb.deleteReviewFromDb(id, mTagsManager, mockDb)).thenReturn(true);

        mRepo.removeReview(id, new ReviewsRepoWriteable.Callback() {
            @Override
            public void onAddedToRepo(RepoResult result) {

            }

            @Override
            public void onRemovedFromRepo(RepoResult result) {
                verify(observer1).onReviewRemoved(id);
                verify(observer2).onReviewRemoved(id);
            }
        });
    }

    @Test
    public void removeReviewDoesNotCallsObserversAndReturnsErrorOnUnsuccessfulRemovingOfReview() {
        final ReviewsRepositoryObserver observer1 = mock(ReviewsRepositoryObserver.class);
        final ReviewsRepositoryObserver observer2 = mock(ReviewsRepositoryObserver.class);
        mRepo.registerObserver(observer1);
        mRepo.registerObserver(observer2);

        ReviewId id = RandomReviewId.nextReviewId();
        TableTransactor mockDb = mockWriteTransaction();
        when(mDb.deleteReviewFromDb(id, mTagsManager, mockDb)).thenReturn(false);

        mRepo.removeReview(id, new ReviewsRepoWriteable.Callback() {
            @Override
            public void onAddedToRepo(RepoResult result) {

            }

            @Override
            public void onRemovedFromRepo(RepoResult result) {
                verifyZeroInteractions(observer1, observer2);
                assertThat(result.isError(),is(true));
            }
        });
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
