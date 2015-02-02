/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ActivityViewReview;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.ControllerReview;
import com.chdryra.android.reviewer.ControllerReviewEditable;
import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;
import com.robotium.solo.Solo;

/**
 * Created by: Rizwan Choudrey
 * On: 02/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditTagsTest extends ActivityInstrumentationTestCase2<ActivityViewReview> {
    private static final int               DELETE = 1;
    private static final int               DONE   = 2;
    private final static GvDataList.GvType TYPE   = GvDataList.GvType.TAGS;
    private Activity         mActivity;
    private ControllerReview mController;
    private Solo             mSolo;

    public ActivityEditTagsTest() {
        super(ActivityViewReview.class);
    }

    @SmallTest
    public void testActivityLaunches() {
        assertTrue(mSolo.searchText(TYPE.getDatumString()));
        mSolo.searchText(TYPE.getDataString());
        mSolo.searchText("add " + TYPE.getDatumString());
    }

    @SmallTest
    public void testSubjectRatingChange() {
        String reviewSubject = mController.getSubject();
        String currentSubject = mSolo.getEditText(0).getText().toString();
        assertEquals(reviewSubject, currentSubject);

        final GvChildrenList.GvChildReview child = GvDataMocker.newChild();

        mSolo.clearEditText(0);
        mSolo.enterText(mSolo.getEditText(0), child.getSubject());
        mSolo.setProgressBar(0, (int) (child.getRating() * 2f));

        mSolo.pressMenuItem(DONE);
        assertEquals(mController.getSubject(), child.getSubject());
        assertEquals(mController.getRating(), child.getRating());
    }

    @SmallTest
    public void testBannerButtonClick() {
        GvDataList currentData = mController.getData(TYPE);
        assertEquals(0, currentData.size());

        GvTagList.GvTag tag1 = (GvTagList.GvTag) GvDataMocker.getDatum(TYPE);
        GvTagList.GvTag tag2 = (GvTagList.GvTag) GvDataMocker.getDatum(TYPE);
        GvTagList.GvTag tag3 = (GvTagList.GvTag) GvDataMocker.getDatum(TYPE);

        assertFalse(mSolo.searchText(tag1.get()));
        assertFalse(mSolo.searchText(tag2.get()));
        assertFalse(mSolo.searchText(tag3.get()));

        assertFalse(mSolo.searchButton("Cancel"));
        assertFalse(mSolo.searchButton("Add"));
        assertFalse(mSolo.searchButton("Done"));
        mSolo.clickOnButton("Add " + TYPE.getDatumString());
        assertTrue(mSolo.searchButton("Cancel"));
        assertTrue(mSolo.searchButton("Add"));
        assertTrue(mSolo.searchButton("Done"));

        mSolo.enterText(mSolo.getEditText(0), tag1.get());
        mSolo.clickOnButton("Add");
        mSolo.enterText(mSolo.getEditText(0), tag2.get());
        mSolo.clickOnButton("Add");
        mSolo.enterText(mSolo.getEditText(0), tag3.get());
        mSolo.clickOnButton("Done");

        assertFalse(mSolo.searchButton("Cancel"));
        assertFalse(mSolo.searchButton("Add"));
        assertFalse(mSolo.searchButton("Done"));

        assertTrue(mSolo.searchText(tag1.get()));
        assertTrue(mSolo.searchText(tag2.get()));
        assertTrue(mSolo.searchText(tag3.get()));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent i = new Intent();
        ActivityViewReview.packParameters(TYPE, true, i);
        mController = new ControllerReviewEditable(ReviewMocker.newReviewEditable());
        Administrator admin = Administrator.get(getInstrumentation().getTargetContext());
        admin.pack(mController, i);
        setActivityIntent(i);
        mActivity = getActivity();
        mSolo = new Solo(getInstrumentation(), mActivity);
    }
}
