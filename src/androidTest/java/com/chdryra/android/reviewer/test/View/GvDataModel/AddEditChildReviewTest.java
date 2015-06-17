/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 December, 2014
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.widget.EditText;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.View.Configs.ConfigGvDataAddEditView;
import com.chdryra.android.reviewer.View.Dialogs.AddEditChildReview;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildList;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditChildReviewTest extends AddEditLayoutTest<GvChildList.GvChildReview> {
    private RatingBar mRatingBar;

    public AddEditChildReviewTest() {
        super(GvChildList.TYPE, new AddEditChildReview(new ConfigGvDataAddEditView.AddChild()));
    }

    @Override
    protected void enterData(GvChildList.GvChildReview child) {
        mEditText.setText(child.getSubject());
        mRatingBar.setRating(child.getRating());
    }

    @Override
    protected void checkViewAndDataEquivalence(GvChildList.GvChildReview datum, boolean result) {
        assertEquals(result, mEditText.getText().toString().trim().equals(datum.getSubject()));
        if (result) assertTrue(mRatingBar.getRating() == datum.getRating());
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mEditText = (EditText) getView(AddEditChildReview.SUBJECT);
        mRatingBar = (RatingBar) getView(AddEditChildReview.RATING);
        assertNotNull(mEditText);
        assertNotNull(mRatingBar);
    }
}
