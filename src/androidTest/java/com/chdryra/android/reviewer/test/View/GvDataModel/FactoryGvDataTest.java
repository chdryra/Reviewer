/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 February, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;

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
