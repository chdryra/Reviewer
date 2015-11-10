/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 11 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSocialPlatformList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.testutils.BitmapMocker;
import com.chdryra.android.testutils.RandomDate;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;
import com.google.android.gms.maps.model.LatLng;

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
    public static final ArrayList<GvDataType> TYPES = ConfigGvDataUi.BUILD_TYPES;
    private static final RandomString STRING_GENERATOR = new RandomString();
    private static final Random RAND = new Random();

    //Static methods
    //Just a convenient method even if it uses GvType.....
    public static GvDataList getData(GvDataType dataType, int size, boolean withId) {
        if (dataType.equals(GvCommentList.GvComment.TYPE)) {
            return newCommentList(size, withId);
        } else if (dataType.equals(GvFactList.GvFact.TYPE)) {
            return newFactList(size, withId);
        } else if (dataType.equals(GvImageList.GvImage.TYPE)) {
            return newImageList(size, withId);
        } else if (dataType.equals(GvLocationList.GvLocation.TYPE)) {
            return newLocationList(size, withId);
        } else if (dataType.equals(GvUrlList.GvUrl.TYPE)) {
            return newUrlList(size, withId);
        } else if (dataType.equals(GvTagList.GvTag.TYPE)) {
            return newTagList(size, withId);
        } else if (dataType.equals(GvCriterionList.GvCriterion.TYPE)) {
            return newChildList(size, withId);
        } else if (dataType.equals(GvReviewOverviewList.GvReviewOverview.TYPE)) {
            return newReviewList(size, withId);
        } else if (dataType.equals(GvAuthorList.GvAuthor.TYPE)) {
            return newAuthorList(size, withId);
        } else if (dataType.equals(GvSubjectList.GvSubject.TYPE)) {
            return newSubjectList(size, withId);
        } else {
            return null;
        }
    }

    public static GvDataList getData(GvDataType dataType, int size) {
        return getData(dataType, size, false);
    }

    //Just a convenient method even if it uses GvType.....
    public static GvData getDatum(GvDataType dataType, boolean withId) {
        if (dataType.equals(GvCommentList.GvComment.TYPE)) {
            return newComment(getId(withId));
        } else if (dataType.equals(GvFactList.GvFact.TYPE)) {
            return newFact(getId(withId));
        } else if (dataType.equals(GvImageList.GvImage.TYPE)) {
            return newImage(getId(withId));
        } else if (dataType.equals(GvLocationList.GvLocation.TYPE)) {
            return newLocation(getId(withId));
        } else if (dataType.equals(GvUrlList.GvUrl.TYPE)) {
            return newUrl(getId(withId));
        } else if (dataType.equals(GvTagList.GvTag.TYPE)) {
            return newTag(getId(withId));
        } else if (dataType.equals(GvCriterionList.GvCriterion.TYPE)) {
            return newChild(getId(withId));
        } else if (dataType.equals(GvReviewOverviewList.GvReviewOverview.TYPE)) {
            return newReviewOverview(getId(withId));
        } else if (dataType.equals(GvAuthorList.GvAuthor.TYPE)) {
            return newAuthor(getId(withId));
        } else if (dataType.equals(GvSubjectList.GvSubject.TYPE)) {
            return newSubject(getId(withId));
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

    public static GvTagList newTagList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvTagList list = new GvTagList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newTag(id));
        }

        return list;
    }

    public static GvCriterionList newChildList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvCriterionList list = new GvCriterionList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newChild(id));
        }

        return list;
    }

    public static GvReviewOverviewList newReviewList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvReviewOverviewList list = new GvReviewOverviewList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newReviewOverview(id));
        }

        return list;
    }

    public static GvAuthorList newAuthorList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvAuthorList list = new GvAuthorList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newAuthor(id));
        }

        return list;
    }

    public static GvSubjectList newSubjectList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvSubjectList list = new GvSubjectList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newSubject(id));
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

    public static GvCriterionList.GvCriterion newChild(GvReviewId id) {
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();

        return new GvCriterionList.GvCriterion(id, subject, rating);
    }

    public static GvTagList.GvTag newTag(GvReviewId id) {
        return new GvTagList.GvTag(id, RandomString.nextWord());
    }

    public static GvSubjectList.GvSubject newSubject(GvReviewId id) {
        return new GvSubjectList.GvSubject(id, RandomString.nextWord());
    }

    public static GvAuthorList.GvAuthor newAuthor(GvReviewId id) {
        Author author = RandomAuthor.nextAuthor();
        return new GvAuthorList.GvAuthor(id, author.getName(), author.getUserId().toString());
    }

    public static GvDateList.GvDate newDate() {
        return new GvDateList.GvDate(RandomDate.nextDate());
    }

    public static GvReviewOverviewList.GvReviewOverview newReviewOverview(GvReviewId parentId) {
        Author author = RandomAuthor.nextAuthor();
        Date date = RandomDate.nextDate();
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();
        Bitmap bitmap = BitmapMocker.nextBitmap(RAND.nextBoolean());
        String comment = RandomString.nextSentence();
        ArrayList<String> locations = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            locations.add(RandomString.nextWord());
            tags.add(RandomString.nextWord());
        }
        GvReviewId id = GvReviewId.getId(RandomReviewId.nextId().toString());

        return new GvReviewOverviewList.GvReviewOverview(parentId, id.toString(), author, date,
                subject, rating, bitmap, comment, locations, tags);
    }

    public static GvSocialPlatformList.GvSocialPlatform newSocialPlatform() {
        return new GvSocialPlatformList.GvSocialPlatform(RandomString.nextWord(),
                RAND.nextInt(100) ^ 2);
    }

    private static GvReviewId getId(boolean withId) {
        return withId ? GvReviewId.getId(RandomReviewId.nextId().toString()) : null;
    }
}
