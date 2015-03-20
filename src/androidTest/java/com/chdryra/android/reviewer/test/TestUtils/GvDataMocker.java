/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 11 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.GvChildList;
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
import com.chdryra.android.testutils.RandomDate;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;

import org.apache.commons.lang3.ArrayUtils;

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
    public static final  GvDataList.GvDataType[] DATATYPES        = {GvCommentList.TYPE,
            GvFactList.TYPE, GvImageList.TYPE, GvLocationList.TYPE, GvUrlList.TYPE, GvTagList.TYPE,
            GvChildList.TYPE};
    public static final  GvDataList.GvDataType[] TYPES            = ArrayUtils.addAll(DATATYPES,
            GvReviewList.TYPE);
    private static final RandomString            STRING_GENERATOR = new RandomString();
    private static final Random                  RAND             = new Random();

    //Just a convenient method even if it uses GvType.....
    public static GvDataList getData(GvDataList.GvDataType dataType, int size) {
        if (dataType == GvCommentList.TYPE) {
            return newCommentList(size);
        } else if (dataType == GvFactList.TYPE) {
            return newFactList(size);
        } else if (dataType == GvImageList.TYPE) {
            return newImageList(size);
        } else if (dataType == GvLocationList.TYPE) {
            return newLocationList(size);
        } else if (dataType == GvUrlList.TYPE) {
            return newUrlList(size);
        } else if (dataType == GvTagList.TYPE) {
            return newTagList(size);
        } else if (dataType == GvChildList.TYPE) {
            return newChildList(size);
        } else if (dataType == GvReviewList.TYPE) {
            return newReviewList(size);
        } else {
            return null;
        }
    }

    //Just a convenient method even if it uses GvType.....
    public static GvDataList.GvData getDatum(GvDataList.GvDataType dataType) {
        if (dataType == GvCommentList.TYPE) {
            return newComment();
        } else if (dataType == GvFactList.TYPE) {
            return newFact();
        } else if (dataType == GvImageList.TYPE) {
            return newImage();
        } else if (dataType == GvLocationList.TYPE) {
            return newLocation();
        } else if (dataType == GvUrlList.TYPE) {
            return newUrl();
        } else if (dataType == GvTagList.TYPE) {
            return newTag();
        } else if (dataType == GvChildList.TYPE) {
            return newChild();
        } else if (dataType == GvReviewList.TYPE) {
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

    public static GvChildList newChildList(int size) {
        GvChildList list = new GvChildList();
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
        return new GvCommentList.GvComment(STRING_GENERATOR.nextParagraph(), RAND.nextBoolean());
    }

    public static GvImageList.GvImage newImage() {
        return new GvImageList.GvImage(BitmapMocker.nextBitmap(RAND.nextBoolean()),
                RandomDate.nextDate(), RandomLatLng.nextLatLng(), RandomString.nextSentence(),
                RAND.nextBoolean());
    }

    public static GvLocationList.GvLocation newLocation() {
        return new GvLocationList.GvLocation(RandomLatLng.nextLatLng(),
                RandomString.nextWord());
    }

    public static GvFactList.GvFact newFact() {
        return new GvFactList.GvFact(RandomString.nextWord(),
                RandomString.nextWord());
    }

    public static GvUrlList.GvUrl newUrl() {
        try {
            URL url = new URL("http://www." + RandomString.nextWord() + ".co.uk");
            return new GvUrlList.GvUrl(RandomString.nextWord(), url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static GvChildList.GvChildReview newChild() {
        return new GvChildList.GvChildReview(RandomString.nextWord(),
                RandomRating.nextRating());
    }

    public static GvTagList.GvTag newTag() {
        return new GvTagList.GvTag(RandomString.nextWord());
    }

    public static GvReviewList.GvReviewOverview newReviewOverview() {
        Calendar c = Calendar.getInstance();
        Date d = new Date();
        c.setTime(d);
        c.add(Calendar.DATE, RAND.nextInt(365));
        Date date = c.getTime();
        return new GvReviewList.GvReviewOverview(ReviewId.generateId().toString(),
                RandomString.nextWord(), date,
                RandomString.nextWord(), RAND.nextFloat() * 5,
                BitmapMocker.nextBitmap(RAND.nextBoolean()), RandomString.nextSentence()
                , RandomString.nextWord());
    }

    public static GvSocialPlatformList.GvSocialPlatform newSocialPlatform() {
        return new GvSocialPlatformList.GvSocialPlatform(RandomString.nextWord(),
                RAND.nextInt(100) ^ 2);
    }
}
