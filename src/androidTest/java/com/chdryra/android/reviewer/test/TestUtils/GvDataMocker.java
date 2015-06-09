/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 11 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.Model.Author;
import com.chdryra.android.reviewer.View.GvChildList;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvData;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvDataType;
import com.chdryra.android.reviewer.View.GvFactList;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvLocationList;
import com.chdryra.android.reviewer.View.GvReviewId;
import com.chdryra.android.reviewer.View.GvReviewList;
import com.chdryra.android.reviewer.View.GvSocialPlatformList;
import com.chdryra.android.reviewer.View.GvTagList;
import com.chdryra.android.reviewer.View.GvUrlList;
import com.chdryra.android.testutils.BitmapMocker;
import com.chdryra.android.testutils.RandomDate;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;
import com.google.android.gms.maps.model.LatLng;

import org.apache.commons.lang3.ArrayUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
            return newComment(getId(withId));
        } else if (dataType == GvFactList.TYPE) {
            return newFact(getId(withId));
        } else if (dataType == GvImageList.TYPE) {
            return newImage(getId(withId));
        } else if (dataType == GvLocationList.TYPE) {
            return newLocation(getId(withId));
        } else if (dataType == GvUrlList.TYPE) {
            return newUrl(getId(withId));
        } else if (dataType == GvTagList.TYPE) {
            return newTag();
        } else if (dataType == GvChildList.TYPE) {
            return newChild(getId(withId));
        } else if (dataType == GvReviewList.TYPE) {
            return newReviewOverview(getId(withId));
        } else {
            return null;
        }
    }

    public static GvData getDatum(GvDataType dataType) {
        return getDatum(dataType, false);
    }

    public static GvCommentList newCommentList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvCommentList list = new GvCommentList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newComment(id));
        }

        return list;
    }

    public static GvImageList newImageList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvImageList list = new GvImageList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newImage(id));
        }

        return list;
    }

    public static GvLocationList newLocationList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvLocationList list = new GvLocationList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newLocation(id));
        }

        return list;
    }

    public static GvFactList newFactList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvFactList list = new GvFactList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newFact(id));
        }

        return list;
    }

    public static GvUrlList newUrlList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvUrlList list = new GvUrlList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newUrl(id));
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

    public static GvChildList newChildList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvChildList list = new GvChildList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newChild(id));
        }

        return list;
    }

    public static GvReviewList newReviewList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvReviewList list = new GvReviewList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newReviewOverview(id));
        }

        return list;
    }

    public static GvCommentList.GvComment newComment(GvReviewId id) {
        String comment = STRING_GENERATOR.nextParagraph();
        boolean isHeadline = RAND.nextBoolean();

        return new GvCommentList.GvComment(id, comment, isHeadline);
    }

    public static GvImageList.GvImage newImage(GvReviewId id) {
        Bitmap bitmap = BitmapMocker.nextBitmap(RAND.nextBoolean());
        Date date = RandomDate.nextDate();
        String caption = RandomString.nextSentence();
        Boolean isCover = RAND.nextBoolean();

        return new GvImageList.GvImage(id, bitmap, date, caption, isCover);
    }

    public static GvLocationList.GvLocation newLocation(GvReviewId id) {
        LatLng latLng = RandomLatLng.nextLatLng();
        String name = RandomString.nextWord();

        return new GvLocationList.GvLocation(id, latLng, name);
    }

    public static GvFactList.GvFact newFact(GvReviewId id) {
        String label = RandomString.nextWord();
        String value = RandomString.nextWord();

        return new GvFactList.GvFact(id, label, value);
    }

    public static GvUrlList.GvUrl newUrl(GvReviewId id) {
        String label = RandomString.nextWord();
        String urlString = "http://www." + RandomString.nextWord() + ".co.uk";

        try {
            URL url = new URL(urlString);
            return new GvUrlList.GvUrl(id, label, url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new GvUrlList.GvUrl();
        }
    }

    public static GvChildList.GvChildReview newChild(GvReviewId id) {
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();

        return new GvChildList.GvChildReview(id, subject, rating);
    }

    public static GvTagList.GvTag newTag() {
        return new GvTagList.GvTag(RandomString.nextWord());
    }

    public static GvReviewList.GvReviewOverview newReviewOverview(GvReviewId parentId) {
        Author author = RandomAuthor.nextAuthor();
        Date date = RandomDate.nextDate();
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();
        Bitmap bitmap = BitmapMocker.nextBitmap(RAND.nextBoolean());
        String comment = RandomString.nextSentence();
        ArrayList<String> locations = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            locations.add(RandomString.nextWord());
        }
        GvReviewId id = GvReviewId.getId(RandomReviewId.nextId().toString());

        return new GvReviewList.GvReviewOverview(parentId, id.toString(), author, date, subject,
                rating, bitmap, comment, locations);
    }

    public static GvSocialPlatformList.GvSocialPlatform newSocialPlatform() {
        return new GvSocialPlatformList.GvSocialPlatform(RandomString.nextWord(),
                RAND.nextInt(100) ^ 2);
    }

    private static GvReviewId getId(boolean withId) {
        return withId ? GvReviewId.getId(RandomReviewId.nextId().toString()) : null;
    }
}
