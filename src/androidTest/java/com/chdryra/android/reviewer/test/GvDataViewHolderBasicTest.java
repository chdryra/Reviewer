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

import com.chdryra.android.reviewer.GvDataView;
import com.chdryra.android.reviewer.GvDataViewHolderBasic;
import com.chdryra.android.reviewer.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataViewHolderBasicTest extends AndroidTestCase {
    private static final int    LAYOUT   = com.chdryra.android.reviewer.R.layout.dialog_holder_test;
    private static final int[]  VIEWIDS  = {com.chdryra.android.reviewer.R.id.edit_text1,
            com.chdryra.android.reviewer.R.id.edit_text2};
    private static final String TAG_INIT = "Initialise";
    private static final String TAG_UPD  = "Update";
    private static final String TAG_RET  = "Return";

    private GvDataViewHolderBasic<GvTagList.GvTag> mGvDataUiHolderImpl;
    private String                                 mTag;

    @SmallTest
    public void testInflateAndGetView() {
        assertNull(mGvDataUiHolderImpl.getView());
        mGvDataUiHolderImpl.inflate(getContext());
        View v = mGvDataUiHolderImpl.getView();
        assertNotNull(v);
        for (int viewId : VIEWIDS) {
            assertNotNull(v.findViewById(viewId));
        }
    }

    @SmallTest
    public void testInitialiseView() {
        assertNull(mTag);
        mGvDataUiHolderImpl.initialiseView(new GvTagList.GvTag(TAG_INIT));
        assertEquals(TAG_INIT, mTag);
    }

    @SmallTest
    public void testUpdateView() {
        assertNull(mTag);
        mGvDataUiHolderImpl.updateView(new GvTagList.GvTag(TAG_UPD));
        assertEquals(TAG_UPD, mTag);
    }

    @SmallTest
    public void testGetGvData() {
        assertEquals(TAG_RET, mGvDataUiHolderImpl.getGvData().get());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mGvDataUiHolderImpl = getGvDataUiHolderImpl();
    }

    private GvDataViewHolderBasic<GvTagList.GvTag> getGvDataUiHolderImpl() {
        return new GvDataViewHolderBasic<GvTagList.GvTag>(LAYOUT, VIEWIDS) {
            @Override
            protected GvDataView<GvTagList.GvTag> getGvDataView() {
                return getGvUi();
            }
        };
    }

    private GvDataView<GvTagList.GvTag> getGvUi() {
        return new GvDataView<GvTagList.GvTag>() {
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
