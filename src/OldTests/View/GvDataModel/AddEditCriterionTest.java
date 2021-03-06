/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 December, 2014
 */

package com.chdryra.android.startouch.test.View.GvDataModel;

import android.widget.EditText;
import android.widget.RatingBar;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Implementation.AddEditCriterion;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Plugin.UiAndroid;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditCriterionTest extends AddEditLayoutTest<GvCriterion> {
    private RatingBar mRatingBar;

    //Constructors
    public AddEditCriterionTest() {
        super(GvCriterion.TYPE,
                new AddEditCriterion(new UiAndroid.DefaultLaunchables.AddCriterion()));
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mEditText = (EditText) getView(AddEditCriterion.SUBJECT);
        mRatingBar = (RatingBar) getView(AddEditCriterion.RATING);
        assertNotNull(mEditText);
        assertNotNull(mRatingBar);
    }

    //Overridden
    @Override
    protected void enterData(GvCriterion child) {
        mEditText.setText(child.getSubject());
        mRatingBar.setRating(child.getRating());
    }

    @Override
    protected void checkViewAndDataEquivalence(GvCriterion datum, boolean result) {
        assertEquals(result, mEditText.getText().toString().trim().equals(datum.getSubject()));
        if (result) assertTrue(mRatingBar.getRating() == datum.getRating());
    }
}
