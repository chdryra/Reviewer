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

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewOverview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewOverviewList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
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
        GvReviewOverview review1 = GvDataMocker.newReviewOverview(null);
        GvReviewOverview review2 = GvDataMocker.newReviewOverview(null);

        String id1 = review1.getId();
        DatumAuthor author1 = review1.getAuthor();
        Date publishDate1 = review1.getPublishDate();
        String subject1 = review1.getSubject();
        float rating1 = review1.getRating();
        Bitmap image1 = review1.getCoverImage();
        String headline1 = review1.getHeadline();
        String locationName1 = review1.getLocationString();
        String tag1 = RandomString.nextWord();

        String id2 = review2.getId();
        DatumAuthor author2 = review2.getAuthor();
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
        mList.add(new GvReviewOverview(id1, author1, publishDate1, subject1,
                rating1, image1, headline1, locations, tags));

        assertEquals(1, mList.size());
        GvReviewOverview review = mList.getItem(0);
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
        mList.add(new GvReviewOverview(id2, author2, publishDate2, subject2,
                rating2, image2, headline2, locations, tags));
        assertEquals(2, mList.size());
        mList.add(new GvReviewOverview(id1, author2, publishDate2, subject2,
                rating2, image2, headline2, locations, tags));
        assertEquals(2, mList.size());
    }

    @SmallTest
    public void testContains() {
        GvReviewOverview review1 = GvDataMocker.newReviewOverview(null);
        GvReviewOverview review2 = GvDataMocker.newReviewOverview(null);

        String id1 = review1.getId();
        String subject2 = review2.getSubject();
        float rating2 = review2.getRating();
        Bitmap image2 = review2.getCoverImage();
        String headline2 = review2.getHeadline();
        String locationName2 = review2.getLocationString();
        String tag2 = RandomString.nextWord();
        DatumAuthor author2 = review2.getAuthor();
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
        GvReviewOverview reviewSameId = new GvReviewOverview(id1, author2, publishDate2, subject2, rating2, image2, headline2,
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
        GvReviewOverview prev = mList.getItem(0);
        for (int i = 1; i < mList.size(); ++i) {
            mList.getItem(i);
            GvReviewOverview next = mList.getItem(i);
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
        GvReviewOverview review1 = GvDataMocker.newReviewOverview(null);
        GvReviewOverview review2 = GvDataMocker.newReviewOverview(null);

        String id1 = review1.getId();
        DatumAuthor author1 = review1.getAuthor();
        Date date1 = review1.getPublishDate();
        String subject1 = review1.getSubject();
        float rating1 = review1.getRating();
        Bitmap image1 = review1.getCoverImage();
        String headline1 = review1.getHeadline();
        String location1 = review1.getLocationString();
        String tag1 = RandomString.nextWord();

        String id2 = review2.getId();
        DatumAuthor author2 = review2.getAuthor();
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

        GvReviewOverview gvReview = new GvReviewOverview(id1, author1, date1, subject1, rating1, image1, headline1,
                locations1, tags1);
        GvReviewOverview gvReviewEquals = new GvReviewOverview(id1, author1, date1, subject1, rating1, image1, headline1,
                locations1, tags1);
        GvReviewOverview gvReviewEquals2 = new GvReviewOverview(gvReview);

        GvReviewOverview gvReviewNotEquals1 = new GvReviewOverview(id2, author1, date1, subject1, rating1, image1, headline1,
                locations1, tags1);
        GvReviewOverview gvReviewNotEquals2 = new GvReviewOverview(id1, author1, date1, subject2, rating1, image1, headline1,
                locations1, tags1);
        GvReviewOverview gvReviewNotEquals3 = new GvReviewOverview(id1, author1, date1, subject1, rating2, image1, headline1,
                locations1, tags1);
        GvReviewOverview gvReviewNotEquals5 = new GvReviewOverview(id1, author1, date1, subject1, rating1, image1, headline2,
                locations1, tags1);
        GvReviewOverview gvReviewNotEquals6 = new GvReviewOverview(id1, author1, date1, subject1, rating1, image1, headline1,
                locations2, tags1);
        GvReviewOverview gvReviewNotEquals7 = new GvReviewOverview(id1, author1, date1, subject1, rating1, image1, headline1,
                locations1, tags2);
        GvReviewOverview gvReviewNotEquals8 = new GvReviewOverview(id1, author2, date1, subject1, rating1, image1, headline1,
                locations1, tags1);
        GvReviewOverview gvReviewNotEquals9 = new GvReviewOverview(id1, author1, date2,subject1, rating1, image1, headline1,
                locations1, tags1);

        GvReviewOverview gvReviewNull = new GvReviewOverview();
        GvReviewOverview gvReviewNoId = new GvReviewOverview("", author1, date1, subject1, rating1, image1, headline1,
                locations1, tags1);
        GvReviewOverview gvReviewNoAuthor = new GvReviewOverview(id1, DatumAuthor.NULL_AUTHOR, date1, subject1, rating1, image1,
                headline1, locations1, tags1);
        GvReviewOverview gvReviewNoDate = new GvReviewOverview(id1, author1, null, subject1, rating1, image1, headline1,
                locations1, tags1);
        GvReviewOverview gvReviewNoSubject = new GvReviewOverview(id1, author1, date1, "", rating1, image1, headline1, locations1,
                tags1);
        GvReviewOverview gvReviewNoTags = new GvReviewOverview(id1, author1, date1, "", rating1, image1, headline1, locations1,
                new ArrayList<String>());
        GvReviewOverview gvReviewNullTags = new GvReviewOverview(id1, author1, date1, "", rating1, image1, headline1, locations1,
                null);
        GvReviewOverview gvReviewNotEmpty = new GvReviewOverview(id1, author1, date1, subject1, 0f, null, null, null, tags1);

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
        assertEquals(GvReviewOverview.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testEquals() {
        mList.addAll(GvDataMocker.newReviewList(NUM, false));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvCriterion.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvTag.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocation.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvComment.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFact.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImage.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrl.TYPE, NUM)));

        GvReviewOverviewList list = new GvReviewOverviewList();
        GvReviewOverviewList list2 = new GvReviewOverviewList(mList);
        assertEquals(0, list.size());
        for (int i = 0; i < mList.size(); ++i) {
            assertFalse(mList.equals(list));
            list.add(mList.getItem(i));
        }

        assertTrue(mList.equals(list));
        assertTrue(mList.equals(list2));
        list.addAll(mList);
        list2.addAll(mList);
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
