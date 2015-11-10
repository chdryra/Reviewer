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

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdIdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvSocialPlatformList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.TestDatabase;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AdministratorTest extends InstrumentationTestCase {
    private Administrator mAdmin;

    @SmallTest
    public void testGetImageChooser() {
        Context context = getInstrumentation().getTargetContext();
        if (mAdmin.getReviewBuilderAdapter() == null) {
            assertNull(Administrator.getImageChooser(context));
        }
        mAdmin.newReviewBuilder();
        assertNotNull(Administrator.getImageChooser(context));
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
        ReviewsRepository repo = mAdmin.getReviewsRepository();
        assertNotNull(repo);
        int numReviews = repo.getReviews().size();
        ReviewerDb db = TestDatabase.getDatabase(getInstrumentation());
        assertEquals(numReviews, db.loadReviewsFromDb().size());

        ReviewBuilderAdapter builder = mAdmin.newReviewBuilder();
        assertNotNull(builder);
        builder.setSubject(RandomString.nextWord());
        GvTagList tags = GvDataMocker.newTagList(3, false);
        ReviewBuilderAdapter.DataBuilderAdapter<GvTagList.GvTag> tagBuilder
                = builder.getDataBuilder(GvTagList.GvTag.TYPE);
        for (GvTagList.GvTag tag : tags) {
            tagBuilder.add(tag);
        }
        tagBuilder.setData();
        mAdmin.publishReviewBuilder();

        MdIdableCollection<Review> reviews = repo.getReviews();
        int newSize = reviews.size();
        assertEquals(numReviews + 1, newSize);
        assertNull(mAdmin.getReviewBuilderAdapter());

        MdIdableCollection<Review> fromDb = db.loadReviewsFromDb();
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
        mAdmin = Administrator.getInstance(getInstrumentation().getTargetContext());
    }
}
