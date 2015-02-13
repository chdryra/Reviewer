/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 11 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.ControllerReviewEditable;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.GvUrlList;
import com.chdryra.android.reviewer.MdCommentList;
import com.chdryra.android.reviewer.MdDataList;
import com.chdryra.android.reviewer.MdFactList;
import com.chdryra.android.reviewer.MdGvConverter;
import com.chdryra.android.reviewer.MdImageList;
import com.chdryra.android.reviewer.MdLocationList;
import com.chdryra.android.reviewer.MdUrlList;
import com.chdryra.android.reviewer.ReviewEditable;
import com.chdryra.android.testutils.RandomStringGenerator;

import junit.framework.Assert;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 11/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ControllerEditableTester extends ControllerTester<ReviewEditable> {
    ControllerReviewEditable     mController;
    ReviewEditable               mReview;
    MdDataMocker<ReviewEditable> mDataMocker;

    public ControllerEditableTester(ControllerReviewEditable controller, ReviewEditable review) {
        super(controller, review);
        mController = controller;
        mReview = getReview();
    }

    public void testSetSubject() {
        String subject = RandomStringGenerator.nextSentence();
        mController.setSubject(subject);
        Assert.assertEquals(subject, mController.getSubject());
        Assert.assertEquals(mReview.getSubject().get(), mController.getSubject());
    }

    public void testSetRating() {
        Random rand = new Random();
        float rating = rand.nextFloat() * 5;
        mController.setRating(rating);
        Assert.assertEquals(rating, mController.getRating());
        Assert.assertEquals(mReview.getRating().get(), mController.getRating());
    }

    public void testSetData() {
        MdGvEquality.check((MdCommentList) testSetAndGetData(GvDataList.GvType.COMMENTS),
                (GvCommentList) mController.getData(GvDataList.GvType.COMMENTS));

        MdGvEquality.check((MdFactList) testSetAndGetData(GvDataList.GvType.FACTS),
                (GvFactList) mController.getData(GvDataList.GvType.FACTS));

        MdGvEquality.check((MdImageList) testSetAndGetData(GvDataList.GvType.IMAGES),
                (GvImageList) mController.getData(GvDataList.GvType.IMAGES));

        MdGvEquality.check((MdLocationList) testSetAndGetData(GvDataList.GvType.LOCATIONS),
                (GvLocationList) mController.getData(GvDataList.GvType.LOCATIONS));

        MdGvEquality.check((MdUrlList) testSetAndGetData(GvDataList.GvType.URLS),
                (GvUrlList) mController.getData(GvDataList.GvType.URLS));
    }

    private MdDataList testSetAndGetData(GvDataList.GvType dataType) {
        //No data to begin with
        GvDataList controllerData = mController.getData(dataType);
        MdDataList reviewData = ReviewGetSetDelete.getData(dataType, mReview);
        Assert.assertNotNull(controllerData);
        Assert.assertNotNull(reviewData);
        Assert.assertEquals(0, controllerData.size());
        Assert.assertEquals(0, reviewData.size());

        //Get mock data and check there is some
        MdDataList mockData = getDataMocker().getData(dataType, NUMDATA);
        Assert.assertNotNull(mockData);
        Assert.assertTrue(mockData.size() > 0);

        //Convert it to GvData and check same size
        GvDataList setData = MdGvConverter.convert(dataType, mockData);
        Assert.assertNotNull(setData);
        Assert.assertEquals(mockData.size(), setData.size());

        //Set data
        mController.setData(setData);

        //Re-get data from controller and review an check same size
        controllerData = mController.getData(dataType);
        reviewData = ReviewGetSetDelete.getData(dataType, mReview);
        Assert.assertNotNull(controllerData);
        Assert.assertNotNull(reviewData);
        Assert.assertEquals(mockData.size(), controllerData.size());
        Assert.assertEquals(mockData.size(), reviewData.size());

        //Check mock data = review data, and controller data = set data
        for (int i = 0; i < mockData.size(); ++i) {
            Assert.assertEquals(mockData.getItem(i), reviewData.getItem(i));
            Assert.assertEquals(setData.getItem(i), controllerData.getItem(i));
        }

        return mockData;
    }
}
