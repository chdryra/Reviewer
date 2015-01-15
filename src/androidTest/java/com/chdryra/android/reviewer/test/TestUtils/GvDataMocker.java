/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 11 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.GvReviewList;
import com.chdryra.android.reviewer.GvSocialPlatformList;
import com.chdryra.android.reviewer.GvTagList;
import com.chdryra.android.reviewer.GvUrlList;
import com.chdryra.android.reviewer.ReviewId;
import com.chdryra.android.testutils.BitmapMocker;
import com.chdryra.android.testutils.LatLngMocker;
import com.chdryra.android.testutils.RandomStringGenerator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 11/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataMocker {
    public static final  GvDataList.GvType[]   TYPES            = {GvDataList.GvType.COMMENTS,
            GvDataList
                    .GvType.FACTS, GvDataList.GvType.TAGS, GvDataList.GvType.LOCATIONS,
            GvDataList.GvType.URLS, GvDataList.GvType.CHILDREN, GvDataList.GvType.IMAGES,
            GvDataList.GvType.REVIEW};
    private static final RandomStringGenerator STRING_GENERATOR = new RandomStringGenerator();
    private static final Random                RAND             = new Random();

    //Just a convenient method even if it uses GvType.....
    public static GvDataList getData(GvDataList.GvType dataType, int size) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return newCommentList(size);
        } else if (dataType == GvDataList.GvType.FACTS) {
            return newFactList(size);
        } else if (dataType == GvDataList.GvType.IMAGES) {
            return newImageList(size);
        } else if (dataType == GvDataList.GvType.LOCATIONS) {
            return newLocationList(size);
        } else if (dataType == GvDataList.GvType.URLS) {
            return newUrlList(size);
        } else if (dataType == GvDataList.GvType.TAGS) {
            return newTagList(size);
        } else if (dataType == GvDataList.GvType.CHILDREN) {
            return newChildList(size);
        } else if (dataType == GvDataList.GvType.REVIEW) {
            return newReviewList(size);
        } else {
            return null;
        }
    }

    //Just a convenient method even if it uses GvType.....
    public static GvDataList.GvData getDatum(GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return newComment();
        } else if (dataType == GvDataList.GvType.FACTS) {
            return newFact();
        } else if (dataType == GvDataList.GvType.IMAGES) {
            return newImage();
        } else if (dataType == GvDataList.GvType.LOCATIONS) {
            return newLocation();
        } else if (dataType == GvDataList.GvType.URLS) {
            return newUrl();
        } else if (dataType == GvDataList.GvType.TAGS) {
            return newTag();
        } else if (dataType == GvDataList.GvType.CHILDREN) {
            return newChild();
        } else if (dataType == GvDataList.GvType.REVIEW) {
            return newReviewOverview();
        } else {
            return null;
        }
    }

    public static GvCommentList newCommentList(int size) {
        GvCommentList list = new GvCommentList();
        for (int i = 0; i < size; ++i) {
            list.add(newComment());
        }

        return list;
    }

    public static GvImageList newImageList(int size) {
        GvImageList list = new GvImageList();
        for (int i = 0; i < size; ++i) {
            list.add(newImage());
        }

        return list;
    }

    public static GvLocationList newLocationList(int size) {
        GvLocationList list = new GvLocationList();
        for (int i = 0; i < size; ++i) {
            list.add(newLocation());
        }

        return list;
    }

    public static GvFactList newFactList(int size) {
        GvFactList list = new GvFactList();
        for (int i = 0; i < size; ++i) {
            list.add(newFact());
        }

        return list;
    }

    public static GvUrlList newUrlList(int size) {
        GvUrlList list = new GvUrlList();
        for (int i = 0; i < size; ++i) {
            list.add(newUrl());
        }

        return list;
    }

    public static GvTagList newTagList(int size) {
        GvTagList list = new GvTagList();
        for (int i = 0; i < size; ++i) {
            list.add(newTag());
        }

        return list;
    }

    public static GvChildrenList newChildList(int size) {
        GvChildrenList list = new GvChildrenList();
        for (int i = 0; i < size; ++i) {
            list.add(newChild());
        }

        return list;
    }

    public static GvReviewList newReviewList(int size) {
        GvReviewList list = new GvReviewList();
        for (int i = 0; i < size; ++i) {
            list.add(newReviewOverview());
        }

        return list;
    }

    public static GvCommentList.GvComment newComment() {
        return new GvCommentList.GvComment(STRING_GENERATOR.nextParagraph());
    }

    public static GvImageList.GvImage newImage() {
        return new GvImageList.GvImage(BitmapMocker.nextBitmap(RAND.nextBoolean()),
                LatLngMocker.newLatLng(), RandomStringGenerator.nextSentence(),
                RAND.nextBoolean());
    }

    public static GvLocationList.GvLocation newLocation() {
        return new GvLocationList.GvLocation(LatLngMocker.newLatLng(),
                RandomStringGenerator.nextWord());
    }

    public static GvFactList.GvFact newFact() {
        return new GvFactList.GvFact(RandomStringGenerator.nextWord(),
                RandomStringGenerator.nextWord());
    }

    public static GvUrlList.GvUrl newUrl() {
        try {
            URL url = new URL("http://www." + RandomStringGenerator.nextWord() + ".co.uk");
            return new GvUrlList.GvUrl(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static GvChildrenList.GvChildReview newChild() {
        return new GvChildrenList.GvChildReview(RandomStringGenerator.nextWord(),
                RatingMocker.nextRating());
    }

    public static GvTagList.GvTag newTag() {
        return new GvTagList.GvTag(RandomStringGenerator.nextWord());
    }

    public static GvReviewList.GvReviewOverview newReviewOverview() {
        Calendar c = Calendar.getInstance();
        Date d = new Date();
        c.setTime(d);
        c.add(Calendar.DATE, RAND.nextInt(365));
        Date date = c.getTime();
        return new GvReviewList.GvReviewOverview(ReviewId.generateId().toString(),
                RandomStringGenerator.nextWord(), date,
                RandomStringGenerator.nextWord(), RAND.nextFloat() * 5,
                BitmapMocker.nextBitmap(RAND.nextBoolean()), RandomStringGenerator.nextSentence()
                , RandomStringGenerator.nextWord());
    }

    public static GvSocialPlatformList.GvSocialPlatform newSocialPlatform() {
        return new GvSocialPlatformList.GvSocialPlatform(RandomStringGenerator.nextWord(),
                RAND.nextInt(100) ^ 2);
    }
}
