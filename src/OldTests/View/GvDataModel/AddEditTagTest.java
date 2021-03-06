/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 December, 2014
 */

package com.chdryra.android.startouch.test.View.GvDataModel;

import android.widget.EditText;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Implementation.AddEditTag;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Plugin.UiAndroid;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

/**
 * Created by: Rizwan Choudrey
 * On: 23/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditTagTest extends AddEditLayoutTest<GvTag> {
    //Constructors
    public AddEditTagTest() {
        super(GvTag.TYPE, new AddEditTag(new UiAndroid.DefaultLaunchables.AddTag()));
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mEditText = (EditText) getView(AddEditTag.TAG);
        assertNotNull(mEditText);
    }

    //Overridden
    @Override
    protected void enterData(GvTag comment) {
        mEditText.setText(comment.getString());
    }

    @Override
    protected void checkViewAndDataEquivalence(GvTag datum, boolean result) {
        assertEquals(result, mEditText.getText().toString().trim().equals(datum.getString()));
    }
}
