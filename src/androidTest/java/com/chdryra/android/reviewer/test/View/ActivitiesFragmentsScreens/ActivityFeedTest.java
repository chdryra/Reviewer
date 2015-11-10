/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 December, 2014
 */

package com.chdryra.android.reviewer.test.View.ActivitiesFragmentsScreens;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import android.widget.GridView;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.ReviewViewPacker;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdIdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsProvider;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.ReviewsProviderModel.StaticReviewsProvider;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.Screens.FeedScreen;
import com.chdryra.android.reviewer.View.Screens.ReviewView;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.RandomAuthor;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.reviewer.test.TestUtils.SoloUtils;
import com.chdryra.android.testutils.RandomString;
import com.robotium.solo.Solo;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityFeedTest extends
        ActivityInstrumentationTestCase2<ActivityReviewView> {
    private static final int NEWREVIEW = R.id.menu_item_new_review;
    private static final int NUM = 5;
    private static final int TIMEOUT = 10000;
    protected ReviewViewAdapter mAdapter;
    protected Activity mActivity;
    protected Solo mSolo;

    //Constructors
    public ActivityFeedTest() {
        super(ActivityReviewView.class);
    }

    @SmallTest
    public void testFeed() {
        GvReviewOverviewList list = (GvReviewOverviewList) mAdapter.getGridData();
        assertEquals(NUM, getGridSize());
        GvReviewOverviewList.GvReviewOverview oldReview = (GvReviewOverviewList.GvReviewOverview)
                getGridItem(0);
        for (int i = 0; i < NUM; ++i) {
            GvReviewOverviewList.GvReviewOverview review = (GvReviewOverviewList
                    .GvReviewOverview) getGridItem(i);
            int j = list.size() - i - 1;
            assertEquals(list.getItem(j).getSubject(), review.getSubject());
            assertEquals(list.getItem(j).getRating(), review.getRating());
            assertNotNull(review.getPublishDate());
            if (i > 0) {
                assertTrue(oldReview.getPublishDate().after(review.getPublishDate()));
            }
            oldReview = review;
        }
    }

    @SmallTest
    public void testMenuNewReview() {
        Log.i("ActivityFeedTest", "Enter testMenuNewReview");
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor
                (ActivityReviewView.class.getName(), null, false);

//        SoloUtils.pretouchScreen(mActivity, mSolo);
//        getInstrumentation().waitForIdleSync();
        Log.i("ActivityFeedTest", "Clicking...");
        mSolo.clickOnActionBarItem(NEWREVIEW);
        Log.i("ActivityFeedTest", "Clicked");
        getInstrumentation().waitForIdleSync();
        Log.i("ActivityFeedTest", "Getting activity");
        ActivityReviewView buildActivity = (ActivityReviewView) monitor.waitForActivityWithTimeout
                (TIMEOUT);
        assertNotNull(buildActivity);
        assertEquals(ActivityReviewView.class, buildActivity.getClass());
    }

    //private methods
    private GridView getGridView() {
        ArrayList views = mSolo.getCurrentViews(GridView.class);
        assertEquals(1, views.size());
        return (GridView) views.get(0);
    }

    private int getGridSize() {
        return getGridView().getAdapter().getCount();
    }

    private GvData getGridItem(int position) {
        return (GvData) getGridView().getItemAtPosition(position);
    }

    //Overridden
    @Override
    protected void setUp() {
        Context context = getInstrumentation().getTargetContext();

        ReviewView feedScreen = new FeedScreen(context, createFeed()).createView();
        mAdapter = feedScreen.getAdapter();

        Intent i = new Intent();
        ReviewViewPacker.packView(getActivity(), feedScreen, i);
        setActivityIntent(i);
        mActivity = getActivity();

        mSolo = new Solo(getInstrumentation(), mActivity);
        SoloUtils.pretouchScreen(mActivity, mSolo);
    }

    private ReviewsRepository createFeed() {
        Author author = RandomAuthor.nextAuthor();
        TagsManager tagsManager = new TagsManager();
        MdIdableCollection<Review> reviews = new MdIdableCollection<>();
        for (int i = 0; i < NUM; ++i) {
            ReviewBuilder builder = new ReviewBuilder(getActivity(), author, tagsManager);
            ReviewBuilderAdapter adapter = new ReviewBuilderAdapter(builder);

            adapter.setSubject(RandomString.nextWord());
            adapter.setRating(RandomRating.nextRating());

            ReviewBuilderAdapter.DataBuilderAdapter<GvTagList.GvTag> dataBuilderAdapter =
                    adapter.getDataBuilder(GvTagList.GvTag.TYPE);
            GvTagList tags = GvDataMocker.newTagList(NUM, false);
            for (int j = 0; j < tags.size(); ++j) {
                dataBuilderAdapter.add(tags.getItem(j));
            }
            dataBuilderAdapter.setData();

            reviews.add(adapter.publish());
        }

        ReviewsProvider provider = new StaticReviewsProvider(reviews, tagsManager);
        return new ReviewsRepository(provider, author);
    }
}
