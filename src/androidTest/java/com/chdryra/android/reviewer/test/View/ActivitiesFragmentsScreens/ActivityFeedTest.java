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
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import android.widget.GridView;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.DataConverters;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Factories.FactoryDataConverters;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.PublishDate;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryConfiguredGridUi;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryDataBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryRvaGridUi;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewPublisher;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryGridDataViewer;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.ReviewViewPacker;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviewNodeComponent;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdIdableCollection;
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Models.TagsModel.Implementation.TagsManagerImpl;
import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProvider;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.ReviewsProviderModel.Implementation.ReviewsSource;
import com.chdryra.android.reviewer.ReviewsProviderModel.Implementation.StaticReviewsRepository;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityReviewView;
import com.chdryra.android.reviewer.View.GvDataAggregation.GvDataAggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.Screens.Builders.BuilderChildListScreen;
import com.chdryra.android.reviewer.View.Screens.FeedScreen;
import com.chdryra.android.reviewer.View.Screens.FeedScreenGridItem;
import com.chdryra.android.reviewer.View.Screens.FeedScreenMenu;
import com.chdryra.android.reviewer.View.Screens.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.Utils.FactoryFileIncrementor;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryImageChooser;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.RandomAuthor;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.reviewer.test.TestUtils.SoloUtils;
import com.chdryra.android.testutils.RandomString;
import com.robotium.solo.Solo;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityFeedTest extends
        ActivityInstrumentationTestCase2<ActivityReviewView> {
    private static final File FILE_DIR_EXT = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
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
                GvDateList.GvDate oldDate = oldReview.getPublishDate();
                GvDateList.GvDate newDate = review.getPublishDate();
                assertTrue(oldDate.getTime() > newDate.getTime());
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

        PublishDate publishDate = new PublishDate(new Date().getTime());
        FactoryReviewNodeComponent nodeFactory = new FactoryReviewNodeComponent();
        TagsManager tagsManager = new TagsManagerImpl();
        DataConverters converters = new FactoryDataConverters(tagsManager).newDataConverters();
        ConverterGv converterGv = converters.getGvConverter();
        ConverterMd converterMd = converters.getMdConverter();
        FactoryReviews reviewFactory = new FactoryReviews(nodeFactory, converterMd);
        BuilderChildListScreen builder = new BuilderChildListScreen();
        FactoryGridDataViewer viewerFactory = new FactoryGridDataViewer();
        GvDataAggregater aggregator = new GvDataAggregater();
        ReviewsProvider feed = createFeed(converterGv, tagsManager, reviewFactory);
        FactoryReviewViewAdapter adapterFactory = new FactoryReviewViewAdapter(builder, viewerFactory, aggregator, feed, converterGv);
        FeedScreen screen = new FeedScreen(new FeedScreenGridItem());
        ReviewView feedScreen = screen.createView(feed, publishDate, reviewFactory, converterGv, builder, adapterFactory, new FeedScreenMenu());
        mAdapter = feedScreen.getAdapter();

        Intent i = new Intent();
        ReviewViewPacker.packView(getActivity(), feedScreen, i);
        setActivityIntent(i);
        mActivity = getActivity();

        mSolo = new Solo(getInstrumentation(), mActivity);
        SoloUtils.pretouchScreen(mActivity, mSolo);
    }

    private ReviewsProvider createFeed(ConverterGv converterGv, TagsManager tagsManager, FactoryReviews reviewFactory) {
        Author author = RandomAuthor.nextAuthor();
        MdIdableCollection<Review> reviews = new MdIdableCollection<>();
        FactoryDataBuilder dataBuilderFactory = new FactoryDataBuilder(converterGv);
        DataValidator validator = new DataValidator();
        FactoryRvaGridUi gridCellFactory = new FactoryRvaGridUi();
        FactoryConfiguredGridUi gridUiFactory = new FactoryConfiguredGridUi(gridCellFactory);
        FactoryFileIncrementor incrementorFactory = new FactoryFileIncrementor(FILE_DIR_EXT, "ActivityFeedTest", "test", validator);
        FactoryImageChooser chooserFactory = new FactoryImageChooser();
        FactoryReviewPublisher publisherFactory = new FactoryReviewPublisher(author);
        for (int i = 0; i < NUM; ++i) {
            ReviewBuilder builder = new ReviewBuilder(converterGv, tagsManager, reviewFactory, dataBuilderFactory, validator);
            ReviewBuilderAdapter adapter = new ReviewBuilderAdapter(getActivity(),
                    builder, gridUiFactory.newReviewBuilderAdapterGridUi(), validator, incrementorFactory, chooserFactory);

            adapter.setSubject(RandomString.nextWord());
            adapter.setRating(RandomRating.nextRating());

            DataBuilderAdapter<GvTagList.GvTag> dataBuilderAdapter =
                    adapter.getDataBuilderAdapter(GvTagList.GvTag.TYPE);
            GvTagList tags = GvDataMocker.newTagList(NUM, false);
            for (int j = 0; j < tags.size(); ++j) {
                dataBuilderAdapter.add(tags.getItem(j));
            }
            dataBuilderAdapter.setData();

            reviews.add(adapter.publishReview());
        }

        ReviewsRepository provider = new StaticReviewsRepository(reviews, tagsManager);
        return new ReviewsSource(provider, publisherFactory, reviewFactory);
    }
}
