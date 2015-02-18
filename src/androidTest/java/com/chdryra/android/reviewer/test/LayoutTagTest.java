/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.reviewer.ConfigGvDataAddEdit;
import com.chdryra.android.reviewer.GvTagList;
import com.chdryra.android.reviewer.LayoutTag;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 23/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutTagTest extends AndroidTestCase {
    private LayoutTag mLayout;

    @Override
    public void setUp() throws Exception {
        mLayout = new LayoutTag(new ConfigGvDataAddEdit.AddTag());
    }

    @SmallTest
    public void testGetDialogTitleOnAdd() {
        GvTagList.GvTag tag = GvDataMocker.newTag();

        String title = mLayout.getTitleOnAdd(tag);
        assertNotNull(title);
        assertTrue(title.contains(tag.get()));
    }

    @SmallTest
    public void testGetDeleteConfirmDialogTitle() {
        GvTagList.GvTag tag = GvDataMocker.newTag();
        String deleteConfirm = mLayout.getDeleteConfirmDialogTitle(tag);
        assertNotNull(deleteConfirm);
        assertTrue(deleteConfirm.contains(tag.get()));
    }

    @SmallTest
    public void testCreateGvDataFromViews() {
        View v = inflate();
        EditText tagET = (EditText) v.findViewById(LayoutTag.TAG);
        assertNotNull(tagET);

        String tag = RandomString.nextWord();

        tagET.setText(tag);

        GvTagList.GvTag tagOut = mLayout.createGvDataFromViews();
        assertNotNull(tagOut);
        assertEquals(tag, tagOut.get());
    }

    @SmallTest
    public void testUpdateViews() {
        View v = inflate();
        EditText tagET = (EditText) v.findViewById(LayoutTag.TAG);
        assertNotNull(tagET);

        GvTagList.GvTag tagIn = GvDataMocker.newTag();
        assertFalse(tagET.getText().toString().trim().equals(tagIn.get()));

        mLayout.updateViews(tagIn);

        assertTrue(tagET.getText().toString().trim().equals(tagIn.get()));
    }

    @SmallTest
    public void testGetViewHolder() {
        assertNotNull(mLayout.getViewHolder());
    }

    @SmallTest
    public void testGetEditTextForKeyboardAction() {
        View v = inflate();
        EditText tagET = (EditText) v.findViewById(LayoutTag.TAG);
        assertEquals(tagET, mLayout.getEditTextForKeyboardAction());
    }

    private View inflate() {
        mLayout.getViewHolder().inflate(getContext());
        return mLayout.getViewHolder().getView();
    }
}
