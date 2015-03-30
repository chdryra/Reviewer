/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 December, 2014
 */

package com.chdryra.android.reviewer.test.View;

import android.test.suitebuilder.annotation.SmallTest;
import android.widget.EditText;

import com.chdryra.android.reviewer.View.ConfigGvDataAddEdit;
import com.chdryra.android.reviewer.View.GvFactList;
import com.chdryra.android.reviewer.View.GvUrlList;
import com.chdryra.android.reviewer.View.LayoutFact;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 23/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutFactTest extends GvDataEditLayoutTest<GvFactList.GvFact> {
    private static final String BBC     = "BBC";
    private static final String BBC_URL = "http://www.bbc.co.uk/";

    private EditText mLabel;
    private boolean mUrlData = false;

    public LayoutFactTest() {
        super(GvFactList.TYPE, new LayoutFact(new ConfigGvDataAddEdit.AddFact()));
    }

    @SmallTest
    public void testCreateGvUrlFromViews() {
        testCreateGvDatumFromViews(true);
    }

    @Override
    protected void enterData(GvFactList.GvFact datum) {
        mLabel.setText(datum.getLabel());
        mEditText.setText(datum.getValue());
    }

    @Override
    protected void checkViewAndDataEquivalence(GvFactList.GvFact datum, boolean result) {
        assertEquals(result, mLabel.getText().toString().trim().equals(datum.getLabel()));
        assertEquals(result, mEditText.getText().toString().trim().equals(datum.getValue()));
    }

    @Override
    public void testCreateGvDataFromViews() {
        testCreateGvDatumFromViews(false);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mLabel = (EditText) getView(LayoutFact.LABEL);
        mEditText = (EditText) getView(LayoutFact.VALUE);
        assertNotNull(mLabel);
        assertNotNull(mEditText);
    }

    @Override
    protected GvFactList.GvFact newData() {
        if (mUrlData) {
            try {
                return new GvUrlList.GvUrl(BBC, new URL(BBC_URL));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                fail("Couldn't create URL data");
            }
        }

        return super.newData();
    }

    private void testCreateGvDatumFromViews(boolean isUrl) {
        mUrlData = isUrl;
        GvFactList.GvFact datum = newData();
        enterData(datum);
        GvFactList.GvFact fromLayout = mLayout.createGvData();
        assertEquals(isUrl, fromLayout.isUrl());
        assertEquals(datum, mLayout.createGvData());
    }
}
