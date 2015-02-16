/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.MdGvConverter;
import com.chdryra.android.reviewer.RCollectionReview;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.ReviewAdapter;
import com.chdryra.android.reviewer.ReviewNode;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
//Has to extend AndroidTestCase as fails in batch test with TestCase....
public class ReviewAdapterTest extends TestCase {
    private Review                mReview;
    private ReviewAdapter<Review> mAdapter;

    @SmallTest
    public void testGetId() {
        assertEquals(mReview.getId().toString(), mAdapter.getId());
    }

    @SmallTest
    public void testGetSubject() {
        assertEquals(mReview.getSubject().get(), mAdapter.getSubject());
    }

    @SmallTest
    public void testGetRating() {
        assertEquals(mReview.getRating().get(), mAdapter.getRating());
    }

    @SmallTest
    public void testGetAuthor() {
        assertEquals(mReview.getAuthor(), mAdapter.getAuthor());
    }

    @SmallTest
    public void testGetPublishDate() {
        assertEquals(mReview.getPublishDate(), mAdapter.getPublishDate());
    }

    @SmallTest
    public void testHasData() {
        assertEquals(mReview.hasComments(), mAdapter.hasData(GvDataList.GvType.COMMENTS));
        assertEquals(mReview.hasFacts(), mAdapter.hasData(GvDataList.GvType.FACTS));
        assertEquals(mReview.hasImages(), mAdapter.hasData(GvDataList.GvType.IMAGES));
        assertEquals(mReview.hasLocations(), mAdapter.hasData(GvDataList.GvType.LOCATIONS));
        assertEquals(mReview.hasUrls(), mAdapter.hasData(GvDataList.GvType.URLS));
    }

    @SmallTest
    public void testGetData() {
        assertEquals(MdGvConverter.convert(mReview.getComments()), mAdapter.getData(GvDataList
                .GvType.COMMENTS));
        assertEquals(MdGvConverter.convert(mReview.getFacts()), mAdapter.getData(GvDataList
                .GvType.FACTS));
        assertEquals(MdGvConverter.convert(mReview.getImages()), mAdapter.getData(GvDataList
                .GvType.IMAGES));
        assertEquals(MdGvConverter.convert(mReview.getLocations()), mAdapter.getData(GvDataList
                .GvType.LOCATIONS));
        assertEquals(MdGvConverter.convert(mReview.getUrls()), mAdapter.getData(GvDataList
                .GvType.URLS));
    }

    @SmallTest
    public void testGetChildren() {
        GvChildrenList children = (GvChildrenList) mAdapter.getData(GvDataList.GvType.CHILDREN);
        RCollectionReview<ReviewNode> childNodes = mReview.getReviewNode().getChildren();
        assertEquals(childNodes.size(), children.size());

        for (int i = 0; i < childNodes.size(); ++i) {
            assertEquals(childNodes.getItem(i).getSubject().get(), children.getItem(i).getSubject
                    ());
            assertEquals(childNodes.getItem(i).getRating().get(), children.getItem(i).getRating());
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mReview = ReviewMocker.newReview();
        mAdapter = new ReviewAdapter<>(mReview);
    }
}
