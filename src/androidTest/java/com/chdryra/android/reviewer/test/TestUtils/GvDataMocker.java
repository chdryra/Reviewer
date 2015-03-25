/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 11 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.GvChildList;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvData;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvDataType;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.GvReviewId;
import com.chdryra.android.reviewer.GvReviewList;
import com.chdryra.android.reviewer.GvSocialPlatformList;
import com.chdryra.android.reviewer.GvTagList;
import com.chdryra.android.reviewer.GvUrlList;
import com.chdryra.android.reviewer.ReviewId;
import com.chdryra.android.testutils.BitmapMocker;
import com.chdryra.android.testutils.RandomDate;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;
import com.google.android.gms.maps.model.LatLng;

import org.apache.commons.lang3.ArrayUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 11/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataMocker {
    public static final  GvDataType[] DATATYPES        = {GvCommentList.TYPE,
            GvFactList.TYPE, GvImageList.TYPE, GvLocationList.TYPE, GvUrlList.TYPE, GvTagList.TYPE,
            GvChildList.TYPE};
    public static final  GvDataType[] TYPES            = ArrayUtils.addAll(DATATYPES,
            GvReviewList.TYPE);
    private static final RandomString STRING_GENERATOR = new RandomString();
    private static final Random       RAND             = new Random();

    //Just a convenient method even if it uses GvType.....
    public static GvDataList getData(GvDataType dataType, int size, boolean withId) {
        if (dataType == GvCommentList.TYPE) {
            return newCommentList(size, withId);
        } else if (dataType == GvFactList.TYPE) {
            return newFactList(size, withId);
        } else if (dataType == GvImageList.TYPE) {
            return newImageList(size, withId);
        } else if (dataType == GvLocationList.TYPE) {
            return newLocationList(size, withId);
        } else if (dataType == GvUrlList.TYPE) {
            return newUrlList(size, withId);
        } else if (dataType == GvTagList.TYPE) {
            return newTagList(size);
        } else if (dataType == GvChildList.TYPE) {
            return newChildList(size, withId);
        } else if (dataType == GvReviewList.TYPE) {
            return newReviewList(size, withId);
        } else {
            return null;
        }
    }

    public static GvDataList getData(GvDataType dataType, int size) {
        return getData(dataType, size, false);
    }

    //Just a convenient method even if it uses GvType.....
    public static GvData getDatum(GvDataType dataType, boolean withId) {
        if (dataType == GvCommentList.TYPE) {
            return newComment(withId);
        } else if (dataType == GvFactList.TYPE) {
            return newFact(withId);
        } else if (dataType == GvImageList.TYPE) {
            return newImage(withId);
        } else if (dataType == GvLocationList.TYPE) {
            return newLocation(withId);
        } else if (dataType == GvUrlList.TYPE) {
            return newUrl(withId);
        } else if (dataType == GvTagList.TYPE) {
            return newTag();
        } else if (dataType == GvChildList.TYPE) {
            return newChild(withId);
        } else if (dataType == GvReviewList.TYPE) {
            return newReviewOverview(withId);
        } else {
            return null;
        }
    }

    public static GvData getDatum(GvDataType dataType) {
        return getDatum(dataType, false);
    }

    public static GvCommentList newCommentList(int size, boolean withId) {
        GvCommentList list = new GvCommentList();
        for (int i = 0; i < size; ++i) {
            list.add(newComment(withId));
        }

        GvReviewId id = new GvReviewId(ReviewId.generateId());
        return withId ? new GvCommentList(id, list) : list;
    }

    public static GvImageList newImageList(int size, boolean withId) {
        GvImageList list = new GvImageList();
        for (int i = 0; i < size; ++i) {
            list.add(newImage(withId));
        }

        GvReviewId id = new GvReviewId(ReviewId.generateId());
        return withId ? new GvImageList(id, list) : list;
    }

    public static GvLocationList newLocationList(int size, boolean withId) {
        GvLocationList list = new GvLocationList();
        for (int i = 0; i < size; ++i) {
            list.add(newLocation(withId));
        }

        GvReviewId id = new GvReviewId(ReviewId.generateId());
        return withId ? new GvLocationList(id, list) : list;
    }

    public static GvFactList newFactList(int size, boolean withId) {
        GvFactList list = new GvFactList();
        for (int i = 0; i < size; ++i) {
            list.add(newFact(withId));
        }

        GvReviewId id = new GvReviewId(ReviewId.generateId());
        return withId ? new GvFactList(id, list) : list;
    }

    public static GvUrlList newUrlList(int size, boolean withId) {
        GvUrlList list = new GvUrlList();
        for (int i = 0; i < size; ++i) {
            list.add(newUrl(withId));
        }

        GvReviewId id = new GvReviewId(ReviewId.generateId());
        return withId ? new GvUrlList(id, list) : list;
    }

    public static GvTagList newTagList(int size) {
        GvTagList list = new GvTagList();
        for (int i = 0; i < size; ++i) {
            list.add(newTag());
        }

        return list;
    }

    public static GvChildList newChildList(int size, boolean withId) {
        GvChildList list = new GvChildList();
        for (int i = 0; i < size; ++i) {
            list.add(newChild(withId));
        }

        GvReviewId id = new GvReviewId(ReviewId.generateId());
        return withId ? new GvChildList(id, list) : list;
    }

    public static GvReviewList newReviewList(int size, boolean withId) {
        GvReviewList list = new GvReviewList();
        for (int i = 0; i < size; ++i) {
            list.add(newReviewOverview(withId));
        }

        GvReviewId id = new GvReviewId(ReviewId.generateId());
        return withId ? new GvReviewList(id, list) : list;
    }

    public static GvCommentList.GvComment newComment(boolean withId) {
        String comment = STRING_GENERATOR.nextParagraph();
        boolean isHeadline = RAND.nextBoolean();
        GvReviewId id = new GvReviewId(ReviewId.generateId());

        return withId ? new GvCommentList.GvComment(id, comment, isHeadline) : new GvCommentList
                .GvComment(comment, isHeadline);
    }

    public static GvImageList.GvImage newImage(boolean withId) {
        Bitmap bitmap = BitmapMocker.nextBitmap(RAND.nextBoolean());
        Date date = RandomDate.nextDate();
        LatLng latLng = RandomLatLng.nextLatLng();
        String caption = RandomString.nextSentence();
        Boolean isCover = RAND.nextBoolean();
        GvReviewId id = new GvReviewId(ReviewId.generateId());

        return withId ? new GvImageList.GvImage(id, bitmap, date, latLng, caption,
                isCover) : new GvImageList.GvImage(bitmap, date, latLng, caption, isCover);
    }

    public static GvLocationList.GvLocation newLocation(boolean withId) {
        LatLng latLng = RandomLatLng.nextLatLng();
        String name = RandomString.nextWord();
        GvReviewId id = new GvReviewId(ReviewId.generateId());

        return withId ? new GvLocationList.GvLocation(id, latLng, name) : new GvLocationList
                .GvLocation(latLng, name);
    }

    public static GvFactList.GvFact newFact(boolean withId) {
        String label = RandomString.nextWord();
        String value = RandomString.nextWord();
        GvReviewId id = new GvReviewId(ReviewId.generateId());

        return withId ? new GvFactList.GvFact(id, label, value) : new GvFactList.GvFact(label,
                value);
    }

    public static GvUrlList.GvUrl newUrl(boolean withId) {
        String label = RandomString.nextWord();
        String urlString = "http://www." + RandomString.nextWord() + ".co.uk";
        GvReviewId id = new GvReviewId(ReviewId.generateId());

        try {
            URL url = new URL(urlString);
            return withId ? new GvUrlList.GvUrl(id, label, url) : new GvUrlList.GvUrl(label, url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static GvChildList.GvChildReview newChild(boolean withId) {
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();
        GvReviewId id = new GvReviewId(ReviewId.generateId());

        return withId ? new GvChildList.GvChildReview(id, subject, rating)
                : new GvChildList.GvChildReview(subject, rating);
    }

    public static GvTagList.GvTag newTag() {
        return new GvTagList.GvTag(RandomString.nextWord());
    }

    public static GvReviewList.GvReviewOverview newReviewOverview(boolean withId) {
        String id = ReviewId.generateId().toString();
        String author = RandomString.nextWord();
        Date date = RandomDate.nextDate();
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();
        Bitmap bitmap = BitmapMocker.nextBitmap(RAND.nextBoolean());
        String comment = RandomString.nextSentence();
        String location = RandomString.nextWord();
        GvReviewId parentId = new GvReviewId(ReviewId.generateId());

        return withId ? new GvReviewList.GvReviewOverview(parentId, id, author, date, subject,
                rating, bitmap, comment, location) :
                new GvReviewList.GvReviewOverview(id, author, date, subject, rating, bitmap,
                        comment, location);
    }

    public static GvSocialPlatformList.GvSocialPlatform newSocialPlatform() {
        return new GvSocialPlatformList.GvSocialPlatform(RandomString.nextWord(),
                RAND.nextInt(100) ^ 2);
    }
}
