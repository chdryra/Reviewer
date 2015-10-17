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
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ParcelableTester;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
import com.chdryra.android.testutils.RandomString;

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
        ParcelableTester.testParcelable(GvDataMocker.newReviewOverview(null));
        ParcelableTester.testParcelable(GvDataMocker.newReviewOverview(RandomReviewId
                .nextGvReviewId()));
        ParcelableTester.testParcelable(GvDataMocker.newReviewList(2, false));
        ParcelableTester.testParcelable(GvDataMocker.newReviewList(2, true));
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
        String tag1 = RandomString.nextWord();

        String id2 = review2.getId();
        Author author2 = review2.getAuthor();
        Date publishDate2 = review2.getPublishDate();
        String subject2 = review2.getSubject();
        float rating2 = review2.getRating();
        Bitmap image2 = review2.getCoverImage();
        String headline2 = review2.getHeadline();
        String locationName2 = review2.getLocationString();
        String tag2 = RandomString.nextWord();

        assertEquals(0, mList.size());
        ArrayList<String> locations = new ArrayList<>();
        locations.add(locationName1);
        ArrayList<String> tags = new ArrayList<>();
        tags.add(tag1);
        mList.add(new GvReviewOverviewList.GvReviewOverview(id1, author1, publishDate1, subject1,
                rating1, image1, headline1, locations, tags));

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
        tags.clear();
        tags.add(tag2);
        mList.add(new GvReviewOverviewList.GvReviewOverview(id2, author2, publishDate2, subject2,
                rating2, image2, headline2, locations, tags));
        assertEquals(2, mList.size());
        mList.add(new GvReviewOverviewList.GvReviewOverview(id1, author2, publishDate2, subject2,
                rating2, image2, headline2, locations, tags));
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
        String tag2 = RandomString.nextWord();
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
        ArrayList<String> tags = new ArrayList<>();
        tags.add(tag2);
        GvReviewOverviewList.GvReviewOverview reviewSameId = new GvReviewOverviewList
                .GvReviewOverview(id1, author2, publishDate2, subject2, rating2, image2, headline2,
                locations, tags);
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
        String tag1 = RandomString.nextWord();

        String id2 = review2.getId();
        Author author2 = review2.getAuthor();
        Date date2 = review2.getPublishDate();
        String subject2 = review2.getSubject();
        float rating2 = review2.getRating();
        String headline2 = review2.getHeadline();
        String location2 = review2.getLocationString();
        String tag2 = RandomString.nextWord();

        ArrayList<String> locations1 = new ArrayList<>();
        ArrayList<String> locations2 = new ArrayList<>();
        ArrayList<String> tags1 = new ArrayList<>();
        ArrayList<String> tags2 = new ArrayList<>();

        locations1.add(location1);
        locations2.add(location1);
        locations2.add(location2);
        tags1.add(tag1);
        tags2.add(tag1);
        tags2.add(tag2);

        GvReviewOverviewList.GvReviewOverview gvReview = new GvReviewOverviewList
                .GvReviewOverview(id1, author1, date1, subject1, rating1, image1, headline1,
                locations1, tags1);
        GvReviewOverviewList.GvReviewOverview gvReviewEquals = new GvReviewOverviewList
                .GvReviewOverview(id1, author1, date1, subject1, rating1, image1, headline1,
                locations1, tags1);
        GvReviewOverviewList.GvReviewOverview gvReviewEquals2 = new GvReviewOverviewList
                .GvReviewOverview(gvReview);

        GvReviewOverviewList.GvReviewOverview gvReviewNotEquals1 = new GvReviewOverviewList
                .GvReviewOverview(id2, author1, date1, subject1, rating1, image1, headline1,
                locations1, tags1);
        GvReviewOverviewList.GvReviewOverview gvReviewNotEquals2 = new GvReviewOverviewList
                .GvReviewOverview(id1, author1, date1, subject2, rating1, image1, headline1,
                locations1, tags1);
        GvReviewOverviewList.GvReviewOverview gvReviewNotEquals3 = new GvReviewOverviewList
                .GvReviewOverview(id1, author1, date1, subject1, rating2, image1, headline1,
                locations1, tags1);
        GvReviewOverviewList.GvReviewOverview gvReviewNotEquals5 = new GvReviewOverviewList
                .GvReviewOverview(id1, author1, date1, subject1, rating1, image1, headline2,
                locations1, tags1);
        GvReviewOverviewList.GvReviewOverview gvReviewNotEquals6 = new GvReviewOverviewList
                .GvReviewOverview(id1, author1, date1, subject1, rating1, image1, headline1,
                locations2, tags1);
        GvReviewOverviewList.GvReviewOverview gvReviewNotEquals7 = new GvReviewOverviewList
                .GvReviewOverview(id1, author1, date1, subject1, rating1, image1, headline1,
                locations1, tags2);
        GvReviewOverviewList.GvReviewOverview gvReviewNotEquals8 = new GvReviewOverviewList
                .GvReviewOverview(id1, author2, date1, subject1, rating1, image1, headline1,
                locations1, tags1);
        GvReviewOverviewList.GvReviewOverview gvReviewNotEquals9 = new GvReviewOverviewList
                .GvReviewOverview(id1, author1, date2,subject1, rating1, image1, headline1,
                locations1, tags1);

        GvReviewOverviewList.GvReviewOverview gvReviewNull = new GvReviewOverviewList
                .GvReviewOverview();
        GvReviewOverviewList.GvReviewOverview gvReviewNoId = new GvReviewOverviewList
                .GvReviewOverview("", author1, date1, subject1, rating1, image1, headline1,
                locations1, tags1);
        GvReviewOverviewList.GvReviewOverview gvReviewNoAuthor = new GvReviewOverviewList
                .GvReviewOverview(id1, Author.NULL_AUTHOR, date1, subject1, rating1, image1,
                headline1, locations1, tags1);
        GvReviewOverviewList.GvReviewOverview gvReviewNoDate = new GvReviewOverviewList
                .GvReviewOverview(id1, author1, null, subject1, rating1, image1, headline1,
                locations1, tags1);
        GvReviewOverviewList.GvReviewOverview gvReviewNoSubject = new GvReviewOverviewList
                .GvReviewOverview(id1, author1, date1, "", rating1, image1, headline1, locations1,
                tags1);
        GvReviewOverviewList.GvReviewOverview gvReviewNoTags = new GvReviewOverviewList
                .GvReviewOverview(id1, author1, date1, "", rating1, image1, headline1, locations1,
                new ArrayList<String>());
        GvReviewOverviewList.GvReviewOverview gvReviewNullTags = new GvReviewOverviewList
                .GvReviewOverview(id1, author1, date1, "", rating1, image1, headline1, locations1,
                null);
        GvReviewOverviewList.GvReviewOverview gvReviewNotEmpty = new GvReviewOverviewList
                .GvReviewOverview(id1, author1, date1, subject1, 0f, null, null, null, tags1);

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
        assertFalse(gvReview.equals(gvReviewNotEquals9));

        assertFalse(gvReviewNull.isValidForDisplay());
        assertFalse(gvReviewNoId.isValidForDisplay());
        assertFalse(gvReviewNoAuthor.isValidForDisplay());
        assertFalse(gvReviewNoDate.isValidForDisplay());
        assertFalse(gvReviewNoSubject.isValidForDisplay());
        assertFalse(gvReviewNoTags.isValidForDisplay());
        assertFalse(gvReviewNullTags.isValidForDisplay());
        assertTrue(gvReviewNotEmpty.isValidForDisplay());
    }

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvReviewOverviewList.GvReviewOverview.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testEquals() {
        mList.addList(GvDataMocker.newReviewList(NUM, false));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvCriterionList.GvCriterion.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvTagList.GvTag.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocationList.GvLocation.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvCommentList.GvComment.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFactList.GvFact.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImageList.GvImage.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrlList.GvUrl.TYPE, NUM)));

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

    //Overridden
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvReviewOverviewList();
    }
}
