/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 May, 2015
 */

package com.chdryra.android.reviewer.test.Controller;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Controller.MdGvConverter;
import com.chdryra.android.reviewer.Controller.ReviewNodeDataAdapter;
import com.chdryra.android.reviewer.Model.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewUser;
import com.chdryra.android.reviewer.Model.TagsManager;
import com.chdryra.android.reviewer.Model.VisitorRatingAverageOfChildren;
import com.chdryra.android.reviewer.View.GvChildList;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvFactList;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvLocationList;
import com.chdryra.android.reviewer.View.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 06/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewNodeDataAdapterTest extends AndroidTestCase {
    private ReviewNode        mNode;
    private ReviewNodeDataAdapter mAdapter;
    private GvTagList             mTags;

    @SmallTest
    public void testGetSubject() {
        assertEquals(mNode.getSubject().get(), mAdapter.getSubject());
    }

    @SmallTest
    public void testGetRating() {
        assertEquals(mNode.getRating().get(), mAdapter.getRating());
    }

    @SmallTest
    public void testGetAverageRating() {
        assertEquals(mNode.getRating().get(), mAdapter.getAverageRating());

        ReviewNode notAverage = ReviewMocker.newReviewNode(false);
        ReviewNodeDataAdapter notAverageAdapter = new ReviewNodeDataAdapter(getContext(),
                notAverage);
        VisitorRatingAverageOfChildren visitor = new VisitorRatingAverageOfChildren();
        notAverage.acceptVisitor(visitor);
        float rating = visitor.getRating();
        assertEquals(rating, notAverageAdapter.getAverageRating());
        assertFalse(notAverageAdapter.getAverageRating() == notAverage.getRating().get());
    }

    @SmallTest
    public void testGetGridData() {
        GvDataList data = mAdapter.getGridData();
        assertEquals(6, data.size());

        GvDataList cell = (GvDataList) data.getItem(0);
        GvTagList tags = (GvTagList) cell;
        assertEquals(mTags, tags);

        cell = (GvDataList) data.getItem(1);
        GvChildList criteria = (GvChildList) cell;
        assertEquals(MdGvConverter.convertChildren(mNode), criteria);

        cell = (GvDataList) data.getItem(2);
        GvImageList images = (GvImageList) cell;
        assertEquals(MdGvConverter.convert(mNode.getImages()), images);

        cell = (GvDataList) data.getItem(3);
        GvCommentList comments = (GvCommentList) cell;
        assertEquals(MdGvConverter.convert(mNode.getComments()), comments);

        cell = (GvDataList) data.getItem(4);
        GvLocationList locations = (GvLocationList) cell;
        assertEquals(MdGvConverter.convert(mNode.getLocations()), locations);

        cell = (GvDataList) data.getItem(5);
        GvFactList facts = (GvFactList) cell;
        assertEquals(MdGvConverter.convert(mNode.getFacts()), facts);
    }

    @SmallTest
    public void testGetCovers() {
        assertEquals(MdGvConverter.convert(mNode.getImages().getCovers()), mAdapter.getCovers());
    }

    @SmallTest
    public void testExpandable() {
        ReviewUser review = (ReviewUser) FactoryReview.createReviewUser(mNode.getAuthor(), new Date
                (), mNode.getSubject().get(), mNode.getRating().get(), mNode.getComments(), mNode
                .getImages(), new GvFactList(), new GvLocationList());
        ReviewNode node = FactoryReview.createReviewNode(review);
        ReviewNodeDataAdapter adapter = new ReviewNodeDataAdapter(getContext(), node);

        GvDataList data = adapter.getGridData();
        for (int i = 0; i < data.size(); ++i) {
            GvDataList item = (GvDataList) data.getItem(i);
            if (item.getGvDataType() == GvCommentList.TYPE
                    || item.getGvDataType() == GvImageList.TYPE) {
                assertTrue(adapter.isExpandable(item));
                assertNotNull(adapter.expandItem(item));

            } else {
                assertFalse(adapter.isExpandable(item));
                assertNull(adapter.expandItem(item));
            }
        }
    }

    @Override
    protected void setUp() throws Exception {
        mNode = ReviewMocker.newReviewNode(true);
        mTags = GvDataMocker.newTagList(3);
        TagsManager.tag(mNode.getId(), mTags.toStringArray());
        mAdapter = new ReviewNodeDataAdapter(getContext(), mNode);
    }

    @Override
    protected void tearDown() throws Exception {
        TagsManager.ReviewTagCollection tags = TagsManager.getTags(mNode.getId());
        for (TagsManager.ReviewTag tag : tags) {
            TagsManager.untag(mNode.getId(), tag);
        }
    }
}
