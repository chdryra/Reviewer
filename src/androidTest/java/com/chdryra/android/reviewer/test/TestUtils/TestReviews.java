/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 April, 2015
 */

package com.chdryra.android.reviewer.test.TestUtils;

import android.app.Instrumentation;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.webkit.URLUtil;

import com.chdryra.android.mygenerallibrary.ImageHelper;
import com.chdryra.android.mygenerallibrary.TextUtils;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilder;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.Model.UserData.UserId;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TestReviews {
    private static Author AUTHOR = new Author("Riz", UserId.generateId());
    private static TestReviews     sReviews;
    private        Instrumentation mInstr;
    private ReviewIdableList<ReviewNode> mNodes;

    private TestReviews(Instrumentation instr) {
        mInstr = instr;
        mNodes = new ReviewIdableList<>();
    }

    private static TestReviews get(Instrumentation instr) {
        if (sReviews == null) sReviews = new TestReviews(instr);
        return sReviews;
    }

    public static ReviewIdableList<ReviewNode> getReviews(Instrumentation instr) {
        TestReviews reviews = get(instr);
        ReviewIdableList<ReviewNode> nodes = reviews.mNodes;
        if (nodes.size() == 0) {
            nodes.add(reviews.getReviewNode(reviews.getReview1()));
            nodes.add(reviews.getReviewNode(reviews.getReview2()));
            reviews.mNodes = nodes;
        }

        return nodes;
    }

    private ReviewNode getReviewNode(TestReview review) {
        ReviewBuilder builder = new ReviewBuilder(mInstr.getTargetContext(), AUTHOR);
        builder.setSubject(review.mSubject);
        builder.setRating(review.mRating);
        builder.setRatingIsAverage(review.mIsRatingAverage);
        ReviewBuilder.DataBuilder b = builder.getDataBuilder(GvCommentList.GvComment.TYPE);
        for (String comment : review.mComments) {
            b.add(new GvCommentList.GvComment(comment));
        }
        b.setData();
        b = builder.getDataBuilder(GvFactList.GvFact.TYPE);
        for (Fact fact : review.mFacts) {
            GvFactList.GvFact f = new GvFactList.GvFact(fact.mLabel, fact.mValue);
            if (fact.mIsUrl) {
                try {
                    f = new GvUrlList.GvUrl(fact.mLabel, new URL(fact.mValue));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            b.add(f);
        }
        b.setData();
        b = builder.getDataBuilder(GvLocationList.GvLocation.TYPE);
        for (Location Location : review.mLocations) {
            b.add(new GvLocationList.GvLocation(Location.mLatLng, Location.mName));
        }
        b.setData();
        b = builder.getDataBuilder(GvImageList.GvImage.TYPE);
        for (Image image : review.mImages) {
            b.add(new GvImageList.GvImage(image.mBitmap, image.mDate, null, image.mCaption, image
                    .mIsCover));
        }
        b.setData();
        b = builder.getDataBuilder(GvChildList.GvChildReview.TYPE);
        for (Criterion child : review.mCriteria) {
            b.add(new GvChildList.GvChildReview(child.mSubject, child.mRating));
        }
        b.setData();
        b = builder.getDataBuilder(GvTagList.GvTag.TYPE);
        for (String tag : review.mTags) {
            b.add(new GvTagList.GvTag(tag));
        }
        b.setData();

        return builder.publish(PublishDate.then(review.mPublishDate.getTime()));
    }

    private TestReview getReview1() {
        TestReview review = new TestReview();
        review.mSubject = "Tayyabs";
        review.mRating = 4f; //irrelevant as will be average of criteria
        review.mIsRatingAverage = true;
        review.mTags.add("Restaurant");
        review.mTags.add("Pakistani");
        review.mTags.add("London");
        review.mCriteria.add(new Criterion("Food", 4f));
        review.mCriteria.add(new Criterion("Service", 2f));
        review.mCriteria.add(new Criterion("Value", 4.5f));
        review.mComments.add("Good food but variable service. Very good value though.");
        review.mComments.add("Drinks are BYO.");
        review.mComments.add("Be prepared to queue at peak times.");
        review.mLocations.add(new Location("Tayyabs", 51.517972, -0.063291));
        review.mFacts.add(new Fact("Starter", "5"));
        review.mFacts.add(new Fact("Main", "8"));
        review.mFacts.add(new Fact("Desert", "5"));
        review.mFacts.add(new Fact("Drinks", "BYO"));
        review.mFacts.add(new Fact("Link", "http://www.tayyabs.co.uk/"));

        Bitmap image = loadBitmap("tayyabs-14.jpg");
        Assert.assertNotNull(image);
        GregorianCalendar cal = new GregorianCalendar(2015, 1, 25, 19, 15);
        review.mImages.add(new Image(image, "Lovely lamb chops!", cal.getTime(), true));
        image = loadBitmap("tayyabs.jpg");
        Assert.assertNotNull(image);
        cal = new GregorianCalendar(2015, 1, 25, 19, 0);
        review.mImages.add(new Image(image, "Frontage", cal.getTime(), false));

        cal = new GregorianCalendar(2015, 1, 25, 19, 30);
        review.mPublishDate = cal.getTime();

        return review;
    }

    private TestReview getReview2() {
        TestReview review = new TestReview();
        review.mSubject = "The Weekend";
        review.mRating = 5f;
        review.mIsRatingAverage = false;
        review.mTags.add("Reading");
        review.mTags.add("Mum");
        review.mTags.add("Kew Gardens");
        review.mTags.add("Baby");
        review.mCriteria.add(new Criterion("Friday", 4f));
        review.mCriteria.add(new Criterion("Saturday", 3.5f));
        review.mCriteria.add(new Criterion("Sunday", 4f));
        review.mComments.add("Mum made curry which was awesome! Had coconut cake for dessert. " +
                "Also great but ate too much.");
        review.mComments.add("Saturday went to Kew Gardens to see the blossom. A little late for " +
                "blossom and lots of place overhead. However was fun to hang out with mum and she" +
                " enjoyed it.");
        review.mComments.add("Sunday went to look at cars and cots in preparation for spot. " +
                "Exciting. For mum anyway...");
        review.mLocations.add(new Location("Home", 51.453149, -1.058555));
        review.mLocations.add(new Location("Kew Gardens", 51.478914, -0.295557));
        review.mLocations.add(new Location("Car contacts", 51.460987, -1.038010));
        review.mLocations.add(new Location("ToysRUs", 51.456697, -0.960154));
        review.mFacts.add(new Fact("Friday dinner", "Curry and coconut & chocolate cake"));
        review.mFacts.add(new Fact("Kew lunch", "Fish pie"));
        review.mFacts.add(new Fact("Car", "Skoda Octavia Estate"));
        review.mFacts.add(new Fact("Car price", "4495"));
        review.mFacts.add(new Fact("Cot", "Sleigh Cot in antique"));
        review.mFacts.add(new Fact("Cot price", "199 plus mattress"));

        Bitmap image = loadBitmap("Kew.jpg");
        Assert.assertNotNull(image);
        GregorianCalendar cal = new GregorianCalendar(2015, 4, 25, 14, 15);
        review.mImages.add(new Image(image, "Selfie in Kew!", cal.getTime(), true));

        image = loadBitmap("Car.jpeg");
        Assert.assertNotNull(image);
        cal = new GregorianCalendar(2015, 4, 26, 13, 0);
        review.mImages.add(new Image(image, "Skoda Octavia", cal.getTime(), false));

        image = loadBitmap("Cot.jpeg");
        Assert.assertNotNull(image);
        cal = new GregorianCalendar(2015, 4, 26, 14, 15);
        review.mImages.add(new Image(image, "Cot", cal.getTime(), false));

        cal = new GregorianCalendar(2015, 4, 26, 14, 30);
        review.mPublishDate = cal.getTime();

        return review;
    }

    private Bitmap loadBitmap(String fileName) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            Context test = mInstr.getContext();
            Context target = mInstr.getTargetContext();
            is = test.getAssets().open(fileName);
            int width = (int) target.getResources().getDimension(R.dimen.imageMaxWidth);
            int height = (int) target.getResources().getDimension(R.dimen.imageMaxHeight);
            bitmap = BitmapFactory.decodeStream(is);
            bitmap = ImageHelper.rescalePreservingAspectRatio(bitmap, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }

    public class TestReview {
        private String  mSubject;
        private float   mRating;
        private boolean mIsRatingAverage;
        private ArrayList<String>    mTags      = new ArrayList<>();
        private ArrayList<Criterion> mCriteria  = new ArrayList<>();
        private ArrayList<String>    mComments  = new ArrayList<>();
        private ArrayList<Location>  mLocations = new ArrayList<>();
        private ArrayList<Fact>      mFacts     = new ArrayList<>();
        private ArrayList<Image>     mImages    = new ArrayList<>();
        private Date mPublishDate;
    }

    private class Criterion {
        private String mSubject;
        private float  mRating;

        private Criterion(String subject, float rating) {
            mSubject = subject;
            mRating = rating;
        }
    }

    private class Location {
        private String mName;
        private LatLng mLatLng;

        private Location(String name, double lat, double lng) {
            mName = name;
            mLatLng = new LatLng(lat, lng);
        }
    }

    private class Fact {
        private String mLabel;
        private String mValue;
        private boolean mIsUrl = false;

        public Fact(String label, String value) {
            mLabel = label;
            mValue = value;
            ArrayList<String> urls = TextUtils.getLinks(value);
            if (urls.size() > 0) {
                mValue = URLUtil.guessUrl(urls.get(0).toLowerCase());
                mIsUrl = true;
            }
        }
    }

    private class Image {
        private Bitmap  mBitmap;
        private String  mCaption;
        private Date    mDate;
        private boolean mIsCover;

        public Image(Bitmap bitmap, String caption, Date date, boolean isCover) {
            mBitmap = bitmap;
            mCaption = caption;
            mDate = date;
            mIsCover = isCover;
        }
    }
}
