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

import com.chdryra.android.reviewer.ApplicationSingletons.ReviewViewPacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvConverter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters
        .ConverterMd;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverters;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.PublishDate;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation
        .MdIdableCollection;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel.ReviewsSourceAuthored;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel
        .StaticReviewsRepository;
import com.chdryra.android.reviewer.Model.Implementation.TagsModel.TagsManagerImpl;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeed;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepository;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryConfiguredGridUi;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuildersGridUi;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.AuthorsStamp;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryChildListView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGridDataViewer;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataAggregator;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverviewList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.FeedScreen;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryFileIncrementor;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityReviewView;
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
        GvReviewOverview oldReview = (GvReviewOverview)
                getGridItem(0);
        for (int i = 0; i < NUM; ++i) {
            GvReviewOverview review = (GvReviewOverview) getGridItem(i);
            int j = list.size() - i - 1;
            assertEquals(list.getItem(j).getSubject(), review.getSubject());
            assertEquals(list.getItem(j).getRating(), review.getRating());
            assertNotNull(review.getPublishDate());
            if (i > 0) {
                GvDate oldDate = oldReview.getPublishDate();
                GvDate newDate = review.getPublishDate();
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
        FactoryReviewNode nodeFactory = new FactoryReviewNode();
        TagsManager tagsManager = new TagsManagerImpl();
        DataConverters converters = new FactoryGvConverter(tagsManager).newDataConverters();
        ConverterGv converterGv = converters.getGvConverter();
        ConverterMd converterMd = converters.getMdConverter();
        FactoryReviews reviewFactory = new FactoryReviews(nodeFactory, converterMd);
        FactoryChildListView builder = new FactoryChildListView();
        FactoryGridDataViewer viewerFactory = new FactoryGridDataViewer();
        GvDataAggregator aggregator = new GvDataAggregator();
        ReviewsFeed feed = createFeed(converterGv, tagsManager, reviewFactory);
        FactoryReviewViewAdapter adapterFactory = new FactoryReviewViewAdapter(builder, viewerFactory, aggregator, feed, converterGv);
        FeedScreen screen = new FeedScreen(new GridItemFeedScreen());
        ReviewView feedScreen = screen.createView(feed, publishDate, reviewFactory, converterGv, builder, adapterFactory, new MenuFeedScreen());
        mAdapter = feedScreen.getAdapter();

        Intent i = new Intent();
        ReviewViewPacker.packView(getActivity(), feedScreen, i);
        setActivityIntent(i);
        mActivity = getActivity();

        mSolo = new Solo(getInstrumentation(), mActivity);
        SoloUtils.pretouchScreen(mActivity, mSolo);
    }

    private ReviewsFeed createFeed(ConverterGv converterGv, TagsManager tagsManager, FactoryReviews reviewFactory) {
        DatumAuthor author = RandomAuthor.nextAuthor();
        MdIdableCollection<Review> reviews = new MdIdableCollection<>();
        FactoryDataBuilder dataBuilderFactory = new FactoryDataBuilder(converterGv);
        DataValidator validator = new DataValidator();
        FactoryDataBuildersGridUi gridCellFactory = new FactoryDataBuildersGridUi();
        FactoryConfiguredGridUi gridUiFactory = new FactoryConfiguredGridUi(gridCellFactory);
        FactoryFileIncrementor incrementorFactory = new FactoryFileIncrementor(FILE_DIR_EXT, "ActivityFeedTest", "test", validator);
        FactoryImageChooser chooserFactory = new FactoryImageChooser();
        AuthorsStamp publisherFactory = new AuthorsStamp(author);
        for (int i = 0; i < NUM; ++i) {
            ReviewBuilder builder = new ReviewBuilder(converterGv, tagsManager, reviewFactory, dataBuilderFactory, validator);
            ReviewBuilderAdapter adapter = new ReviewBuilderAdapter(getActivity(),
                    builder, gridUiFactory.newReviewBuilderAdapterGridUi(), validator, incrementorFactory, chooserFactory);

            adapter.setSubject(RandomString.nextWord());
            adapter.setRating(RandomRating.nextRating());

            DataBuilderAdapter<GvTag> dataBuilderAdapter =
                    adapter.getDataBuilderAdapter(GvTag.TYPE);
            GvTagList tags = GvDataMocker.newTagList(NUM, false);
            for (int j = 0; j < tags.size(); ++j) {
                dataBuilderAdapter.add(tags.getItem(j));
            }
            dataBuilderAdapter.publishData();

            reviews.add(adapter.publishReview());
        }

        ReviewsRepository provider = new StaticReviewsRepository(reviews, tagsManager);
        return new ReviewsSourceAuthored(provider, publisherFactory, reviewFactory, , );
    }
}
