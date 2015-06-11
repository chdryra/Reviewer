/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 February, 2015
 */

package com.chdryra.android.reviewer.test.View;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 16/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGvDataTest extends TestCase {

    @SmallTest
    public void testGvType() {
        assertEquals(GvImageList.TYPE, FactoryGvData.gvType(GvImageList.class));
        assertEquals(GvTagList.TYPE, FactoryGvData.gvType(GvTagList.class));
        assertEquals(GvChildList.TYPE, FactoryGvData.gvType(GvChildList.class));
        assertEquals(GvCommentList.TYPE, FactoryGvData.gvType(GvCommentList.class));
        assertEquals(GvLocationList.TYPE, FactoryGvData.gvType(GvLocationList.class));
        assertEquals(GvUrlList.TYPE, FactoryGvData.gvType(GvUrlList.class));
    }

    @SmallTest
    public void testNewList() {
        assertNotNull(FactoryGvData.newList(GvImageList.class));
        assertNotNull(FactoryGvData.newList(GvTagList.class));
        assertNotNull(FactoryGvData.newList(GvChildList.class));
        assertNotNull(FactoryGvData.newList(GvCommentList.class));
        assertNotNull(FactoryGvData.newList(GvLocationList.class));
        assertNotNull(FactoryGvData.newList(GvUrlList.class));
    }

    @SmallTest
    public void testNewNull() {
        assertFalse(FactoryGvData.newNull(GvImageList.GvImage.class).isValidForDisplay());
        assertFalse(FactoryGvData.newNull(GvTagList.GvTag.class).isValidForDisplay());
        assertFalse(FactoryGvData.newNull(GvChildList.GvChildReview.class).isValidForDisplay());
        assertFalse(FactoryGvData.newNull(GvCommentList.GvComment.class).isValidForDisplay());
        assertFalse(FactoryGvData.newNull(GvLocationList.GvLocation.class).isValidForDisplay());
        assertFalse(FactoryGvData.newNull(GvUrlList.GvUrl.class).isValidForDisplay());
    }
}
