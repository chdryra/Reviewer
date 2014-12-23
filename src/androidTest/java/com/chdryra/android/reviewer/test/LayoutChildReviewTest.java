/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.ActivityFeed;
import com.chdryra.android.reviewer.ConfigGvDataAddEditDisplay;
import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.LayoutChildReview;
import com.chdryra.android.reviewer.RatingFormatter;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.RatingMocker;
import com.chdryra.android.testutils.RandomStringGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutChildReviewTest extends
        ActivityInstrumentationTestCase2<ActivityFeed> {
    private LayoutChildReview mLayout;

    public LayoutChildReviewTest() {
        super(ActivityFeed.class);
    }

    @Override
    public void setUp() throws Exception {
        mLayout = new LayoutChildReview(new ConfigGvDataAddEditDisplay.AddChild());
    }

    @SmallTest
    public void testGetDialogTitleOnAdd() {
        GvChildrenList.GvChildReview review = GvDataMocker.newChild();

        String title = mLayout.getTitleOnAdd(review);
        assertNotNull(title);
        assertTrue(title.contains(review.getSubject()));
        String ratingToFirstDecimal = RatingFormatter.roundToSignificant(review.getRating(), 2);
        assertTrue(title.contains(ratingToFirstDecimal));
    }

    @SmallTest
    public void testGetDeleteConfirmDialogTitle() {
        GvChildrenList.GvChildReview review = GvDataMocker.newChild();
        String deleteConfirm = mLayout.getDeleteConfirmDialogTitle(review);
        assertNotNull(deleteConfirm);
        assertTrue(deleteConfirm.contains(review.getSubject()));
        assertTrue(deleteConfirm.contains(RatingFormatter.twoSignificantDigits(review.getRating()
        )));
    }

    @SmallTest
    public void testCreateGvDataFromViews() {
        View v = inflate();
        EditText subjectET = (EditText) v.findViewById(LayoutChildReview.SUBJECT);
        RatingBar ratingbar = (RatingBar) v.findViewById(LayoutChildReview.RATING);
        assertNotNull(subjectET);
        assertNotNull(ratingbar);

        String subject = RandomStringGenerator.nextWord();
        float rating = RatingMocker.nextRating();

        subjectET.setText(subject);
        ratingbar.setRating(rating);

        GvChildrenList.GvChildReview reviewOut = mLayout.createGvDataFromViews();
        assertNotNull(reviewOut);
        assertEquals(subject, reviewOut.getSubject());
        assertEquals(rating, reviewOut.getRating());
    }

    @SmallTest
    public void testUpdateViews() {
        View v = inflate();
        EditText subjectET = (EditText) v.findViewById(LayoutChildReview.SUBJECT);
        RatingBar ratingbar = (RatingBar) v.findViewById(LayoutChildReview.RATING);

        GvChildrenList.GvChildReview reviewIn = GvDataMocker.newChild();
        assertFalse(subjectET.getText().toString().trim().equals(reviewIn.getSubject()));

        mLayout.updateViews(reviewIn);

        assertTrue(subjectET.getText().toString().trim().equals(reviewIn.getSubject()));
        assertTrue(ratingbar.getRating() == reviewIn.getRating());
    }

    @SmallTest
    public void testGetViewHolder() {
        assertNotNull(mLayout.getViewHolder());
    }

    @SmallTest
    public void testGetEditTextForKeyboardAction() {
        View v = inflate();
        EditText subjectET = (EditText) v.findViewById(LayoutChildReview.SUBJECT);
        assertEquals(subjectET, mLayout.getEditTextForKeyboardAction());
    }

    private View inflate() {
        mLayout.getViewHolder().inflate(getInstrumentation().getTargetContext());
        return mLayout.getViewHolder().getView();
    }
}
