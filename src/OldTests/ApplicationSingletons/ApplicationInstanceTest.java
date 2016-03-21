/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 December, 2014
 */

package com.chdryra.android.reviewer.test.ApplicationSingletons;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.ReviewerDb.Interfaces.ReviewerPersistence;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation
        .MdIdableCollection;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeed;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatformList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.TestDatabase;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationInstanceTest extends InstrumentationTestCase {
    private ApplicationInstance mAdmin;

    @SmallTest
    public void testGetImageChooser() {
        Context context = getInstrumentation().getTargetContext();
        if (mAdmin.getReviewBuilderAdapter() == null) {
            assertNull(ApplicationInstance.getImageChooser(context));
        }
        mAdmin.newReviewBuilder();
        assertNotNull(ApplicationInstance.getImageChooser(context));
    }

    @SmallTest
    public void testGetAuthor() {
        assertNotNull(mAdmin.getAuthor());
    }

    @SmallTest
    public void testGetReviewBuilder() {
        ReviewBuilderAdapter builder = mAdmin.newReviewBuilder();
        assertNotNull(builder);
        assertEquals(builder, mAdmin.getReviewBuilderAdapter());
    }

    @SmallTest
    public void testNewReviewBuilder() {
        assertNotNull(mAdmin.newReviewBuilder());
    }

    @SmallTest
    @UiThreadTest
    public void testPublishReviewBuilder() {
        ReviewsFeed repo = mAdmin.getReviewsRepository();
        assertNotNull(repo);
        int numReviews = repo.getReviews().size();
        ReviewerPersistence db = TestDatabase.getDatabase(getInstrumentation());
        assertEquals(numReviews, db.getReviews().size());

        ReviewBuilderAdapter builder = mAdmin.newReviewBuilder();
        assertNotNull(builder);
        builder.setSubject(RandomString.nextWord());
        GvTagList tags = GvDataMocker.newTagList(3, false);
        DataBuilderAdapter<GvTag> tagBuilder
                = builder.getDataBuilderAdapter(GvTag.TYPE);
        for (GvTag tag : tags) {
            tagBuilder.add(tag);
        }
        tagBuilder.publishData();
        mAdmin.executeReviewBuilder();

        MdIdableCollection<Review> reviews = repo.getReviews();
        int newSize = reviews.size();
        assertEquals(numReviews + 1, newSize);
        assertNull(mAdmin.getReviewBuilderAdapter());

        MdIdableCollection<Review> fromDb = db.getReviews();
        assertEquals(numReviews + 1, fromDb.size());
        Review mostRecent = reviews.getItem(newSize - 1);
        assertTrue(fromDb.containsId(mostRecent.getMdReviewId()));
    }


    @SmallTest
    public void testGetSocialPlatformList() {
        GvSocialPlatformList list = mAdmin.getSocialPlatformList();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAdmin = ApplicationInstance.getInstance(getInstrumentation().getTargetContext());
    }
}
