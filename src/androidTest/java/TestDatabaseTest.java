/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Application.ApplicationLaunch;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackRepositoryMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;

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
    private ReviewsRepository mTestRepo;
    private ReviewsRepositoryMutable mRepo;
    private Context mContext;

    @Before
    @Override
    public void setUp() {
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mContext = getInstrumentation().getTargetContext();
        ApplicationLaunch.launchIfNecessary(mContext, ApplicationLaunch.LaunchState.TEST);
        mRepo = (ReviewsRepositoryMutable) ApplicationInstance.getInstance(mContext).getUsersFeed();
        deleteDatabaseIfNecessary();
        mTestRepo = TestReviews.getReviews(getInstrumentation(),mRepo.getTagsManager());
        mTestRepo.getReviews(new CallbackRepository() {
            @Override
            public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {

            }

            @Override
            public void onFetchedFromRepo(Collection<Review> reviews, CallbackMessage result) {
                populateRepository(reviews);
            }
        });
    }

    @Test
    public void testDatabase() {
        mTestRepo.getReviews(new CallbackRepository() {
            @Override
            public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {

            }

            @Override
            public void onFetchedFromRepo(final Collection<Review> testReviews, CallbackMessage result) {
                mRepo.getReviews(new CallbackRepository() {
                    @Override
                    public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {

                    }

                    @Override
                    public void onFetchedFromRepo(Collection<Review> reviews, CallbackMessage result) {
                        assertEquals(testReviews.size(), reviews.size());
                        Iterator<Review> testReviewsIt = testReviews.iterator();
                        for (Review review : reviews) {
                            Review testReview = testReviewsIt.next();
                            assertThat(review.getReviewId(), is(testReview.getReviewId()));
                            assertThat(review.getSubject(), is(testReview.getSubject()));
                            assertThat(review.getRating(), is(testReview.getRating()));
                            assertThat(review.getAuthor(), is(testReview.getAuthor()));
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
            mRepo.addReview(review, new CallbackRepositoryMutable() {
                @Override
                public void onAddedCallback(Review review, CallbackMessage result) {
                    assertThat(mContext.getDatabasePath(DB_NAME).exists(), is(true));
                }

                @Override
                public void onRemovedCallback(ReviewId reviewId, CallbackMessage result) {

                }
            });
        }
    }

    private void deleteDatabaseIfNecessary() {
        File db = mContext.getDatabasePath(DB_NAME);
        if (db.exists()) mContext.deleteDatabase(DB_NAME);
    }
}
