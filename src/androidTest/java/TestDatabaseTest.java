/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.RepoResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepoMutable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 28/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(AndroidJUnit4.class)
public class TestDatabaseTest extends InstrumentationTestCase {
    private final String DB_NAME = "TestReviewer.db";
    private ReviewsRepo mTestRepo;
    private ReviewsRepoMutable mRepo;
    private Context mContext;

    @Before
    @Override
    public void setUp() {
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mContext = getInstrumentation().getTargetContext();
        ApplicationInstance instance = AppInstanceAndroid.getInstance();
        mRepo = (ReviewsRepoMutable) instance.getReviews(instance.getUserSession().getAuthorId());
        deleteDatabaseIfNecessary();
        mTestRepo = TestReviews.getReviews(getInstrumentation(),mRepo.getTagsManager());
        mTestRepo.getRepository(new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                Collection<Review> reviews = result.getReviews();
                populateRepository(reviews);
            }
        });
    }

    @Test
    public void testDatabase() {
        mTestRepo.getRepository(new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                final Collection<Review> testReviews = result.getReviews();
                mRepo.getRepository(new RepoCallback() {
                    @Override
                    public void onRepoCallback(RepoResult result) {
                        Collection<Review> reviews = result.getReviews();
                        assertEquals(testReviews.size(), reviews.size());
                        Iterator<Review> testReviewsIt = testReviews.iterator();
                        for (Review review : reviews) {
                            Review testReview = testReviewsIt.next();
                            assertThat(review.getReviewId(), is(testReview.getReviewId()));
                            assertThat(review.getSubject(), is(testReview.getSubject()));
                            assertThat(review.getRating(), is(testReview.getRating()));
                            assertThat(review.getAuthorId(), is(testReview.getAuthorId()));
                            assertThat(review.getPublishDate(), is(testReview.getPublishDate()));
                            assertThat(review.getComments().size(), is(testReview.getComments().size()));
                            assertThat(review.getFacts().size(), is(testReview.getFacts().size()));
                            assertThat(review.getLocations().size(), is(testReview.getLocations().size()));
                            assertThat(review.getImages().size(), is(testReview.getImages().size()));
                        }
                    }
                });
            }
        });
    }

    private void populateRepository(Collection<Review> reviews) {
        deleteDatabaseIfNecessary();
        for (Review review : reviews) {
            mRepo.addReview(review, new ReviewsRepoMutable.Callback() {
                @Override
                public void onAddedToRepo(RepoResult result) {
                    assertThat(mContext.getDatabasePath(DB_NAME).exists(), is(true));
                }

                @Override
                public void onRemovedFromRepo(RepoResult result) {

                }
            });
        }
    }

    private void deleteDatabaseIfNecessary() {
        File db = mContext.getDatabasePath(DB_NAME);
        if (db.exists()) mContext.deleteDatabase(DB_NAME);
    }
}
