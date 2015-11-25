/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 February, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvTag;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvUrl;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 16/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGvDataTest extends TestCase {

    @SmallTest
    public void testNewList() {
        assertNotNull(FactoryGvData.newDataList(GvImage.TYPE));
        assertNotNull(FactoryGvData.newDataList(GvTag.TYPE));
        assertNotNull(FactoryGvData.newDataList(GvCriterion.TYPE));
        assertNotNull(FactoryGvData.newDataList(GvComment.TYPE));
        assertNotNull(FactoryGvData.newDataList(GvLocation.TYPE));
        assertNotNull(FactoryGvData.newDataList(GvUrl.TYPE));
    }

    @SmallTest
    public void testNewNull() {
        assertFalse(FactoryGvData.newNull(GvImage.class).isValidForDisplay());
        assertFalse(FactoryGvData.newNull(GvTag.class).isValidForDisplay());
        assertFalse(FactoryGvData.newNull(GvCriterion.class)
                .isValidForDisplay());
        assertFalse(FactoryGvData.newNull(GvComment.class).isValidForDisplay());
        assertFalse(FactoryGvData.newNull(GvLocation.class).isValidForDisplay());
        assertFalse(FactoryGvData.newNull(GvUrl.class).isValidForDisplay());
    }
}
