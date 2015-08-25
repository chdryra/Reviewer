/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.graphics.Bitmap;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.GvDataParcelableTester;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvReviewOverviewListTest extends TestCase {
    private static final int NUM = 50;
    private GvReviewOverviewList mList;

    @SmallTest
    public void testParcelable() {
        GvDataParcelableTester.testParcelable(GvDataMocker.newReviewOverview(null));
        GvDataParcelableTester.testParcelable(GvDataMocker.newReviewOverview(RandomReviewId
                .nextGvReviewId()));
        GvDataParcelableTester.testParcelable(GvDataMocker.newReviewList(2, false));
        GvDataParcelableTester.testParcelable(GvDataMocker.newReviewList(2, true));
    }

    @SmallTest
    public void testAdd() {
        GvReviewOverviewList.GvReviewOverview review1 = GvDataMocker.newReviewOverview(null);
        GvReviewOverviewList.GvReviewOverview review2 = GvDataMocker.newReviewOverview(null);

        String id1 = review1.getId();
        Author author1 = review1.getAuthor();
        Date publishDate1 = review1.getPublishDate();
        String subject1 = review1.getSubject();
        float rating1 = review1.getRating();
        Bitmap image1 = review1.getCoverImage();
        String headline1 = review1.getHeadline();
        String locationName1 = review1.getLocationString();

        String id2 = review2.getId();
        Author author2 = review2.getAuthor();
        Date publishDate2 = review2.getPublishDate();
        String subject2 = review2.getSubject();
        float rating2 = review2.getRating();
        Bitmap image2 = review2.getCoverImage();
        String headline2 = review2.getHeadline();
        String locationName2 = review2.getLocationString();

        assertEquals(0, mList.size());
        ArrayList<String> locations = new ArrayList<>();
        locations.add(locationName1);
        mList.add(new GvReviewOverviewList.GvReviewOverview(id1, author1, publishDate1, subject1,
                rating1, image1, headline1, locations));
        assertEquals(1, mList.size());
        GvReviewOverviewList.GvReviewOverview review = mList.getItem(0);
        assertEquals(id1, review.getId());
        assertEquals(subject1, review.getSubject());
        assertEquals(rating1, review.getRating());
        assertTrue(image1.sameAs(review.getCoverImage()));
        assertEquals(headline1, review.getHeadline());
        assertEquals(locationName1, review.getLocationString());
        assertEquals(author1, review.getAuthor());
        assertEquals(publishDate1, review.getPublishDate());

        locations.clear();
        locations.add(locationName2);
        mList.add(new GvReviewOverviewList.GvReviewOverview(id2, author2, publishDate2, subject2,
                rating2, image2, headline2, locations));
        assertEquals(2, mList.size());
        mList.add(new GvReviewOverviewList.GvReviewOverview(id1, author2, publishDate2, subject2,
                rating2, image2, headline2, locations));
        assertEquals(2, mList.size());
    }

    @SmallTest
    public void testContains() {
        GvReviewOverviewList.GvReviewOverview review1 = GvDataMocker.newReviewOverview(null);
        GvReviewOverviewList.GvReviewOverview review2 = GvDataMocker.newReviewOverview(null);

        String id1 = review1.getId();
        String subject2 = review2.getSubject();
        float rating2 = review2.getRating();
        Bitmap image2 = review2.getCoverImage();
        String headline2 = review2.getHeadline();
        String locationName2 = review2.getLocationString();
        Author author2 = review2.getAuthor();
        Date publishDate2 = review2.getPublishDate();

        assertFalse(mList.contains(review1));
        mList.add(review1);
        assertTrue(mList.contains(review1));

        assertFalse(mList.contains(review2));
        mList.add(review2);
        assertTrue(mList.contains(review2));

        ArrayList<String> locations = new ArrayList<>();
        locations.add(locationName2);
        GvReviewOverviewList.GvReviewOverview reviewSameId = new GvReviewOverviewList
                .GvReviewOverview(id1,
                author2, publishDate2, subject2, rating2, image2, headline2, locations);
        assertTrue(mList.contains(reviewSameId));

        mList.remove(review1);
        assertFalse(mList.contains(review1));
    }

    @SmallTest
    public void testComparator() {
        for (int i = 0; i < 50; ++i) {
            mList.add(GvDataMocker.newReviewOverview(null));
        }

        assertEquals(50, mList.size());

        mList.sort();
        GvReviewOverviewList.GvReviewOverview prev = mList.getItem(0);
        for (int i = 1; i < mList.size(); ++i) {
            mList.getItem(i);
            GvReviewOverviewList.GvReviewOverview next = mList.getItem(i);
            Date prevDate = prev.getPublishDate();
            Date nextDate = next.getPublishDate();
            if (prevDate != nextDate) {
                assertTrue(prevDate.after(nextDate));
            }

            prev = next;
        }
    }

    @SmallTest
    public void testGvReviewOverview() {
        GvReviewOverviewList.GvReviewOverview review1 = GvDataMocker.newReviewOverview(null);
        GvReviewOverviewList.GvReviewOverview review2 = GvDataMocker.newReviewOverview(null);

        String id1 = review1.getId();
        Author author1 = review1.getAuthor();
        Date date1 = review1.getPublishDate();
        String subject1 = review1.getSubject();
        float rating1 = review1.getRating();
        Bitmap image1 = review1.getCoverImage();
        String headline1 = review1.getHeadline();
        String location1 = review1.getLocationString();

        String id2 = review2.getId();
        Author author2 = review2.getAuthor();
        Date date2 = review2.getPublishDate();
        String subject2 = review2.getSubject();
        float rating2 = review2.getRating();
        String headline2 = review2.getHeadline();
        String location2 = review2.getLocationString();

        ArrayList<String> locations = new ArrayList<>();
        ArrayList<String> locations2 = new ArrayList<>();
        locations.add(location1);
        locations2.add(location1);
        locations2.add(location2);
        GvReviewOverviewList.GvReviewOverview gvReview = new GvReviewOverviewList
                .GvReviewOverview(id1, author1,
                date1, subject1, rating1, image1, headline1, locations);
        GvReviewOverviewList.GvReviewOverview gvReviewEquals = new GvReviewOverviewList
                .GvReviewOverview(id1,
                author1, date1, subject1, rating1, image1, headline1, locations);
        GvReviewOverviewList.GvReviewOverview gvReviewEquals2 = new GvReviewOverviewList
                .GvReviewOverview(gvReview);

        GvReviewOverviewList.GvReviewOverview gvReviewNotEquals1 = new GvReviewOverviewList
                .GvReviewOverview(id2,
                author1, date1,
                subject1, rating1, image1, headline1, locations);
        GvReviewOverviewList.GvReviewOverview gvReviewNotEquals2 = new GvReviewOverviewList
                .GvReviewOverview(id1,
                author1, date1,
                subject2, rating1, image1, headline1, locations);
        GvReviewOverviewList.GvReviewOverview gvReviewNotEquals3 = new GvReviewOverviewList
                .GvReviewOverview(id1,
                author1, date1,
                subject1, rating2, image1, headline1, locations);
        GvReviewOverviewList.GvReviewOverview gvReviewNotEquals5 = new GvReviewOverviewList
                .GvReviewOverview(id1,
                author1, date1,
                subject1, rating1, image1, headline2, locations);
        GvReviewOverviewList.GvReviewOverview gvReviewNotEquals6 = new GvReviewOverviewList
                .GvReviewOverview(id1,
                author1, date1,
                subject1, rating1, image1, headline1, locations2);
        GvReviewOverviewList.GvReviewOverview gvReviewNotEquals7 = new GvReviewOverviewList
                .GvReviewOverview(id1,
                author2, date1,
                subject1, rating1, image1, headline1, locations);
        GvReviewOverviewList.GvReviewOverview gvReviewNotEquals8 = new GvReviewOverviewList
                .GvReviewOverview(id1,
                author1, date2,
                subject1, rating1, image1, headline1, locations);

        GvReviewOverviewList.GvReviewOverview gvReviewNull = new GvReviewOverviewList
                .GvReviewOverview();
        GvReviewOverviewList.GvReviewOverview gvReviewNoId = new GvReviewOverviewList
                .GvReviewOverview("",
                author1, date1,
                subject1, rating1, image1, headline1, locations);
        GvReviewOverviewList.GvReviewOverview gvReviewNoAuthor = new GvReviewOverviewList
                .GvReviewOverview(id1,
                Author.NULL_AUTHOR, date1,
                subject1, rating1, image1, headline1, locations);
        GvReviewOverviewList.GvReviewOverview gvReviewNoDate = new GvReviewOverviewList
                .GvReviewOverview(id1,
                author1,
                null, subject1, rating1, image1, headline1, locations);
        GvReviewOverviewList.GvReviewOverview gvReviewNoSubject = new GvReviewOverviewList
                .GvReviewOverview(id1,
                author1, date1, "", rating1, image1, headline1, locations);
        GvReviewOverviewList.GvReviewOverview gvReviewNotEmpty = new GvReviewOverviewList
                .GvReviewOverview(id1,
                author1, date1, subject1, 0f, null, null, null);

        assertNotNull(gvReview.getViewHolder());
        assertTrue(gvReview.isValidForDisplay());

        assertEquals(id1, gvReview.getId());
        assertEquals(author1, gvReview.getAuthor());
        assertEquals(date1, gvReview.getPublishDate());
        assertTrue(gvReview.equals(gvReviewEquals));
        assertEquals(subject1, gvReview.getSubject());
        assertEquals(rating1, gvReview.getRating());
        assertEquals(image1, gvReview.getCoverImage());
        assertEquals(headline1, gvReview.getHeadline());
        assertEquals(location1, gvReview.getLocationString());

        assertTrue(gvReview.equals(gvReviewEquals));
        assertTrue(gvReview.equals(gvReviewEquals2));
        assertFalse(gvReview.equals(gvReviewNotEquals1));
        assertFalse(gvReview.equals(gvReviewNotEquals2));
        assertFalse(gvReview.equals(gvReviewNotEquals3));
        assertFalse(gvReview.equals(gvReviewNotEquals5));
        assertFalse(gvReview.equals(gvReviewNotEquals6));
        assertFalse(gvReview.equals(gvReviewNotEquals7));
        assertFalse(gvReview.equals(gvReviewNotEquals8));

        assertFalse(gvReviewNull.isValidForDisplay());
        assertFalse(gvReviewNoId.isValidForDisplay());
        assertFalse(gvReviewNoAuthor.isValidForDisplay());
        assertFalse(gvReviewNoDate.isValidForDisplay());
        assertFalse(gvReviewNoSubject.isValidForDisplay());
        assertTrue(gvReviewNotEmpty.isValidForDisplay());
    }

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvReviewOverviewList.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testEquals() {
        mList.addList(GvDataMocker.newReviewList(NUM, false));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvChildReviewList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvTagList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocationList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvCommentList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFactList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImageList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrlList.TYPE, NUM)));

        GvReviewOverviewList list = new GvReviewOverviewList();
        GvReviewOverviewList list2 = new GvReviewOverviewList(mList);
        assertEquals(0, list.size());
        for (int i = 0; i < mList.size(); ++i) {
            assertFalse(mList.equals(list));
            list.add(mList.getItem(i));
        }

        assertTrue(mList.equals(list));
        assertTrue(mList.equals(list2));
        list.addList(mList);
        list2.addList(mList);
        assertFalse(mList.equals(list));
        assertFalse(mList.equals(list2));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvReviewOverviewList();
    }
}
