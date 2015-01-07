/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ConfigGvDataAddEditDisplay;
import com.chdryra.android.reviewer.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogFragmentGvDataEditTagTest extends DialogFragmentGvDataEditTest<GvTagList.GvTag> {

    public DialogFragmentGvDataEditTagTest() {
        super(ConfigGvDataAddEditDisplay.EditTag.class);
    }

    @Override
    protected GvTagList.GvTag editData() {
        GvTagList.GvTag tag = GvDataMocker.newTag();
        mSolo.clearEditText(mSolo.getEditText(0));
        mSolo.enterText(mSolo.getEditText(0), tag.get());
        assertEquals(getDataShown(), tag);

        return tag;
    }

    @Override
    protected GvTagList.GvTag getDataShown() {
        return new GvTagList.GvTag(mSolo.getEditText(0).getText().toString());
    }

    @SmallTest
    @Override
    public void testCancelButtonNoEdit() {
        super.testCancelButtonNoEdit();
    }

    @SmallTest
    @Override
    public void testCancelButtonWithEdit() {
        super.testCancelButtonWithEdit();
    }
}
