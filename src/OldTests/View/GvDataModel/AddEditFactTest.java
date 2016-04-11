/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 December, 2014
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;
import android.widget.EditText;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Plugin.UiAndroid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation.AddEditFact;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 23/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditFactTest extends AddEditLayoutTest<GvFact> {
    private static final String BBC = "BBC";
    private static final String BBC_URL = "http://www.bbc.co.uk/";

    private EditText mLabel;
    private boolean mUrlData = false;

    //Constructors
    public AddEditFactTest() {
        super(GvFact.TYPE, new AddEditFact(new UiAndroid.DefaultLaunchables.AddFact()));
    }

    @SmallTest
    public void testCreateGvUrlFromViews() {
        testCreateGvDatumFromViews(true);
    }

    private void testCreateGvDatumFromViews(boolean isUrl) {
        mUrlData = isUrl;
        GvFact datum = newDatum();
        enterData(datum);
        GvFact fromLayout = mLayout.createGvData();
        assertEquals(isUrl, fromLayout.isUrl());
        assertEquals(datum, mLayout.createGvData());
    }

    //Overridden
    @Override
    protected void enterData(GvFact datum) {
        mLabel.setText(datum.getLabel());
        mEditText.setText(datum.getValue());
    }

    @Override
    protected void checkViewAndDataEquivalence(GvFact datum, boolean result) {
        assertEquals(result, mLabel.getText().toString().trim().equals(datum.getLabel()));
        assertEquals(result, mEditText.getText().toString().trim().equals(datum.getValue()));
    }

    @Override
    public void testCreateGvDataFromViews() {
        testCreateGvDatumFromViews(false);
    }

    @Override
    protected GvFact newDatum() {
        if (mUrlData) {
            try {
                return new GvUrl(BBC, new URL(BBC_URL));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                fail("Couldn't create URL data");
            }
        }

        return super.newDatum();
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mLabel = (EditText) getView(AddEditFact.LABEL);
        mEditText = (EditText) getView(AddEditFact.VALUE);
        assertNotNull(mLabel);
        assertNotNull(mEditText);
    }
}
