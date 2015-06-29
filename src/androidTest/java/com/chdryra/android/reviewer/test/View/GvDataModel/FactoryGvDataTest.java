/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 February, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

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
    public void testNewList() {
        assertNotNull(FactoryGvData.newDataList(GvImageList.GvImage.TYPE));
        assertNotNull(FactoryGvData.newDataList(GvTagList.GvTag.TYPE));
        assertNotNull(FactoryGvData.newDataList(GvChildList.GvChildReview.TYPE));
        assertNotNull(FactoryGvData.newDataList(GvCommentList.GvComment.TYPE));
        assertNotNull(FactoryGvData.newDataList(GvLocationList.GvLocation.TYPE));
        assertNotNull(FactoryGvData.newDataList(GvUrlList.GvUrl.TYPE));
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
