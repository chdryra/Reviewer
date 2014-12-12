/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;

import com.chdryra.android.reviewer.DialogHolder;
import com.chdryra.android.reviewer.GvDataUi;
import com.chdryra.android.reviewer.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogHolderTest extends AndroidTestCase {
    private static final int    LAYOUT   = com.chdryra.android.reviewer.R.layout.dialog_holder_test;
    private static final int[]  VIEWIDS  = {com.chdryra.android.reviewer.R.id.edit_text1,
            com.chdryra.android.reviewer.R.id.edit_text2};
    private static final String TAG_INIT = "Initialise";
    private static final String TAG_UPD  = "Update";
    private static final String TAG_RET  = "Return";

    private DialogHolder<GvTagList.GvTag> mDialogHolder;
    private String                        mTag;

    @SmallTest
    public void testInflateAndGetView() {
        assertNull(mDialogHolder.getView());
        mDialogHolder.inflate(getContext());
        View v = mDialogHolder.getView();
        assertNotNull(v);
        for (int viewId : VIEWIDS) {
            assertNotNull(v.findViewById(viewId));
        }
    }

    @SmallTest
    public void testInitialiseView() {
        assertNull(mTag);
        mDialogHolder.initialiseView(new GvTagList.GvTag(TAG_INIT));
        assertEquals(TAG_INIT, mTag);
    }

    @SmallTest
    public void testUpdateView() {
        assertNull(mTag);
        mDialogHolder.updateView(new GvTagList.GvTag(TAG_UPD));
        assertEquals(TAG_UPD, mTag);
    }

    @SmallTest
    public void testGetGvData() {
        assertEquals(TAG_RET, mDialogHolder.getGvData().get());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mDialogHolder = getDialogHolder();
    }

    private DialogHolder<GvTagList.GvTag> getDialogHolder() {
        return new DialogHolder<GvTagList.GvTag>(LAYOUT, VIEWIDS) {
            @Override
            protected GvDataUi<GvTagList.GvTag> getGvDataUi() {
                return getGvUi();
            }
        };
    }

    private GvDataUi<GvTagList.GvTag> getGvUi() {
        return new GvDataUi<GvTagList.GvTag>() {
            @Override
            public void initialiseView(GvTagList.GvTag data) {
                mTag = data.get();
            }

            @Override
            public void updateView(GvTagList.GvTag data) {
                mTag = data.get();
            }

            @Override
            public GvTagList.GvTag getGvData() {
                return new GvTagList.GvTag(TAG_RET);
            }
        };
    }
}
