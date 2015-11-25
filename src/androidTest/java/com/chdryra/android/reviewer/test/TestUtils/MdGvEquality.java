/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCommentList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdFactList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdImageList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdLocationList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdUrlList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvUrlList;

import junit.framework.Assert;

/**
 * Created by: Rizwan Choudrey
 * On: 10/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class MdGvEquality {
    //Static methods
    public static void check(MdCommentList mdData, GvCommentList gvData) {
        checkSizes(mdData, gvData);
        for (int i = 0; i < mdData.size(); ++i) {
            Assert.assertEquals(mdData.getItem(i).getComment(), gvData.getItem(i).getComment());
        }
    }

    public static void check(MdFactList mdData, GvFactList gvData) {
        checkSizes(mdData, gvData);
        for (int i = 0; i < mdData.size(); ++i) {
            Assert.assertEquals(mdData.getItem(i).getLabel(), gvData.getItem(i).getLabel());
            Assert.assertEquals(mdData.getItem(i).getValue(), gvData.getItem(i).getValue());
        }
    }


    public static void check(MdImageList mdData, GvImageList gvData) {
        checkSizes(mdData, gvData);
        for (int i = 0; i < mdData.size(); ++i) {
            Assert.assertNotNull(mdData.getItem(i).getBitmap());
            Assert.assertTrue(mdData.getItem(i).getBitmap().sameAs(gvData.getItem(i).getBitmap()));
            Assert.assertEquals(mdData.getItem(i).getCaption(), gvData.getItem(i).getCaption());
            Assert.assertEquals(mdData.getItem(i).isCover(), gvData.getItem(i).isCover());
        }
    }

    public static void check(MdLocationList mdData, GvLocationList gvData) {
        checkSizes(mdData, gvData);
        for (int i = 0; i < mdData.size(); ++i) {
            Assert.assertEquals(mdData.getItem(i).getLatLng(), gvData.getItem(i).getLatLng());
            Assert.assertEquals(mdData.getItem(i).getName(), gvData.getItem(i).getName());
        }
    }

    public static void check(MdUrlList mdData, GvUrlList gvData) {
        checkSizes(mdData, gvData);
        for (int i = 0; i < mdData.size(); ++i) {
            Assert.assertEquals(mdData.getItem(i).getUrl(), gvData.getItem(i).getUrl());
        }
    }

    public static void checkSizes(MdDataList mdData, GvDataList gvData) {
        Assert.assertNotNull(mdData);
        Assert.assertNotNull(gvData);
        Assert.assertEquals(mdData.size(), gvData.size());
    }
}
