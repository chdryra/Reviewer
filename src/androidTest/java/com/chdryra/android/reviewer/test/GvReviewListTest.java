/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.graphics.Bitmap;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.GvReviewList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvReviewListTest extends TestCase {
    private GvReviewList mList;

    @SmallTest
    public void testAdd() {
        GvReviewList.GvReviewOverview review1 = GvDataMocker.newReviewOverview();
        GvReviewList.GvReviewOverview review2 = GvDataMocker.newReviewOverview();

        String id1 = review1.getId();
        String author1 = review1.getAuthor();
        Date publishDate1 = review1.getPublishDate();
        String subject1 = review1.getSubject();
        float rating1 = review1.getRating();
        Bitmap image1 = review1.getCoverImage();
        String headline1 = review1.getHeadline();
        String locationName1 = review1.getLocationName();

        String id2 = review2.getId();
        String author2 = review2.getAuthor();
        Date publishDate2 = review2.getPublishDate();
        String subject2 = review2.getSubject();
        float rating2 = review2.getRating();
        Bitmap image2 = review2.getCoverImage();
        String headline2 = review2.getHeadline();
        String locationName2 = review2.getLocationName();

        assertEquals(0, mList.size());
        mList.add(id1, author1, publishDate1, subject1, rating1, image1, headline1, locationName1);
        assertEquals(1, mList.size());
        GvReviewList.GvReviewOverview review = mList.getItem(0);
        assertEquals(id1, review.getId());
        assertEquals(subject1, review.getSubject());
        assertEquals(rating1, review.getRating());
        assertTrue(image1.sameAs(review.getCoverImage()));
        assertEquals(headline1, review.getHeadline());
        assertEquals(locationName1, review.getLocationName());
        assertEquals(author1, review.getAuthor());
        assertEquals(publishDate1, review.getPublishDate());

        mList.add(id2, author2, publishDate2, subject2, rating2, image2, headline2, locationName2);
        assertEquals(2, mList.size());
        mList.add(id1, author2, publishDate2, subject2, rating2, image2, headline2, locationName2);
        assertEquals(2, mList.size());
    }

    @SmallTest
    public void testContains() {
        GvReviewList.GvReviewOverview review1 = GvDataMocker.newReviewOverview();
        GvReviewList.GvReviewOverview review2 = GvDataMocker.newReviewOverview();

        String id1 = review1.getId();
        String subject2 = review2.getSubject();
        float rating2 = review2.getRating();
        Bitmap image2 = review2.getCoverImage();
        String headline2 = review2.getHeadline();
        String locationName2 = review2.getLocationName();
        String author2 = review2.getAuthor();
        Date publishDate2 = review2.getPublishDate();

        assertFalse(mList.contains(review1));
        mList.add(review1);
        assertTrue(mList.contains(review1));

        assertFalse(mList.contains(review2));
        mList.add(review2);
        assertTrue(mList.contains(review2));

        GvReviewList.GvReviewOverview reviewSameId = new GvReviewList.GvReviewOverview(id1,
                author2, publishDate2, subject2, rating2, image2, headline2, locationName2);
        assertTrue(mList.contains(reviewSameId));

        mList.remove(review1);
        assertFalse(mList.contains(review1));
    }

    @SmallTest
    public void testComparator() {
        for (int i = 0; i < 50; ++i) {
            mList.add(GvDataMocker.newReviewOverview());
        }

        assertEquals(50, mList.size());

        mList.sort();
        GvReviewList.GvReviewOverview prev = mList.getItem(0);
        for (int i = 1; i < mList.size(); ++i) {
            mList.getItem(i);
            GvReviewList.GvReviewOverview next = mList.getItem(i);
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
        GvReviewList.GvReviewOverview review1 = GvDataMocker.newReviewOverview();
        GvReviewList.GvReviewOverview review2 = GvDataMocker.newReviewOverview();

        String id1 = review1.getId();
        String author1 = review1.getAuthor();
        Date date1 = review1.getPublishDate();
        String subject1 = review1.getSubject();
        float rating1 = review1.getRating();
        Bitmap image1 = review1.getCoverImage();
        String headline1 = review1.getHeadline();
        String location1 = review1.getLocationName();

        String id2 = review2.getId();
        String author2 = review2.getAuthor();
        Date date2 = review2.getPublishDate();
        String subject2 = review2.getSubject();
        float rating2 = review2.getRating();
        Bitmap image2 = review2.getCoverImage();
        String headline2 = review2.getHeadline();
        String location2 = review2.getLocationName();


        GvReviewList.GvReviewOverview gvReview = new GvReviewList.GvReviewOverview(id1, author1,
                date1, subject1, rating1, image1, headline1, location1);
        GvReviewList.GvReviewOverview gvReviewEquals = new GvReviewList.GvReviewOverview(id1,
                author1, date1, subject1, rating1, image1, headline1, location1);

        GvReviewList.GvReviewOverview gvReviewNotEquals1 = new GvReviewList.GvReviewOverview(id2,
                author1, date1,
                subject1, rating1, image1, headline1, location1);
        GvReviewList.GvReviewOverview gvReviewNotEquals2 = new GvReviewList.GvReviewOverview(id1,
                author1, date1,
                subject2, rating1, image1, headline1, location1);
        GvReviewList.GvReviewOverview gvReviewNotEquals3 = new GvReviewList.GvReviewOverview(id1,
                author1, date1,
                subject1, rating2, image1, headline1, location1);
        GvReviewList.GvReviewOverview gvReviewNotEquals4 = new GvReviewList.GvReviewOverview(id1,
                author1, date1,
                subject1, rating1, image2, headline1, location1);
        GvReviewList.GvReviewOverview gvReviewNotEquals5 = new GvReviewList.GvReviewOverview(id1,
                author1, date1,
                subject1, rating1, image1, headline2, location1);
        GvReviewList.GvReviewOverview gvReviewNotEquals6 = new GvReviewList.GvReviewOverview(id1,
                author1, date1,
                subject1, rating1, image1, headline1, location2);
        GvReviewList.GvReviewOverview gvReviewNotEquals7 = new GvReviewList.GvReviewOverview(id1,
                author2, date1,
                subject1, rating1, image1, headline1, location1);
        GvReviewList.GvReviewOverview gvReviewNotEquals8 = new GvReviewList.GvReviewOverview(id1,
                author1, date2,
                subject1, rating1, image1, headline1, location1);


        GvReviewList.GvReviewOverview gvReviewNull = new GvReviewList.GvReviewOverview();
        GvReviewList.GvReviewOverview gvReviewNoId = new GvReviewList.GvReviewOverview("",
                author1, date1,
                subject1, rating1, image1, headline1, location1);
        GvReviewList.GvReviewOverview gvReviewNoAuthor = new GvReviewList.GvReviewOverview(id1,
                "", date1,
                subject1, rating1, image1, headline1, location1);
        GvReviewList.GvReviewOverview gvReviewNoDate = new GvReviewList.GvReviewOverview(id1,
                author1,
                null, subject1, rating1, image1, headline1, location1);
        GvReviewList.GvReviewOverview gvReviewNoSubject = new GvReviewList.GvReviewOverview(id1,
                author1, date1, "", rating1, image1, headline1, location1);
        GvReviewList.GvReviewOverview gvReviewNotEmpty = new GvReviewList.GvReviewOverview(id1,
                author1, date1, subject1, 0f, null, null, null);

        assertNotNull(gvReview.newViewHolder());
        assertTrue(gvReview.isValidForDisplay());

        assertEquals(id1, gvReview.getId());
        assertEquals(author1, gvReview.getAuthor());
        assertEquals(date1, gvReview.getPublishDate());
        assertTrue(gvReview.equals(gvReviewEquals));
        assertEquals(subject1, gvReview.getSubject());
        assertEquals(rating1, gvReview.getRating());
        assertEquals(image1, gvReview.getCoverImage());
        assertEquals(headline1, gvReview.getHeadline());
        assertEquals(location1, gvReview.getLocationName());

        assertTrue(gvReview.equals(gvReviewEquals));
        assertFalse(gvReview.equals(gvReviewNotEquals1));
        assertFalse(gvReview.equals(gvReviewNotEquals2));
        assertFalse(gvReview.equals(gvReviewNotEquals3));
        assertFalse(gvReview.equals(gvReviewNotEquals4));
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

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvReviewList();
    }
}
