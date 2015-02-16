/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.FactoryGvData;
import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.GvTagList;
import com.chdryra.android.reviewer.GvUrlList;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 16/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGvDataTest extends TestCase {

    @SmallTest
    public void testGvType() {
        assertEquals(GvDataList.GvType.IMAGES, FactoryGvData.gvType(GvImageList.class));
        assertEquals(GvDataList.GvType.TAGS, FactoryGvData.gvType(GvTagList.class));
        assertEquals(GvDataList.GvType.CHILDREN, FactoryGvData.gvType(GvChildrenList.class));
        assertEquals(GvDataList.GvType.COMMENTS, FactoryGvData.gvType(GvCommentList.class));
        assertEquals(GvDataList.GvType.LOCATIONS, FactoryGvData.gvType(GvLocationList.class));
        assertEquals(GvDataList.GvType.URLS, FactoryGvData.gvType(GvUrlList.class));
    }

    @SmallTest
    public void testNewList() {
        assertTrue(FactoryGvData.newList(GvImageList.class) instanceof GvImageList);
        assertTrue(FactoryGvData.newList(GvTagList.class) instanceof GvTagList);
        assertTrue(FactoryGvData.newList(GvChildrenList.class) instanceof GvChildrenList);
        assertTrue(FactoryGvData.newList(GvCommentList.class) instanceof GvCommentList);
        assertTrue(FactoryGvData.newList(GvLocationList.class) instanceof GvLocationList);
        assertTrue(FactoryGvData.newList(GvUrlList.class) instanceof GvUrlList);
    }

    @SmallTest
    public void testNewNull() {
        assertFalse(FactoryGvData.newNull(GvImageList.GvImage.class).isValidForDisplay());
        assertFalse(FactoryGvData.newNull(GvTagList.GvTag.class).isValidForDisplay());
        assertFalse(FactoryGvData.newNull(GvChildrenList.GvChildReview.class).isValidForDisplay());
        assertFalse(FactoryGvData.newNull(GvCommentList.GvComment.class).isValidForDisplay());
        assertFalse(FactoryGvData.newNull(GvLocationList.GvLocation.class).isValidForDisplay());
        assertFalse(FactoryGvData.newNull(GvUrlList.GvUrl.class).isValidForDisplay());
    }
}
