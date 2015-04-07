/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Model.MdCommentList;
import com.chdryra.android.reviewer.Model.MdDataList;
import com.chdryra.android.reviewer.Model.MdFactList;
import com.chdryra.android.reviewer.Model.MdImageList;
import com.chdryra.android.reviewer.Model.MdLocationList;
import com.chdryra.android.reviewer.Model.MdUrlList;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataType;
import com.chdryra.android.reviewer.View.GvFactList;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvLocationList;
import com.chdryra.android.reviewer.View.GvUrlList;
import com.chdryra.android.testutils.BitmapMocker;
import com.chdryra.android.testutils.RandomDate;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class MdDataMocker {
    private static final RandomString STRING_GENERATOR = new RandomString();
    private static final String       URL_LABEL        = "Google";
    private static final String       URL              = "http://www.google.co.uk";
    private static final Random       RAND             = new Random();
    private static URL sUrl;

    private final ReviewId mReviewId;

    public MdDataMocker(ReviewId id) {
        mReviewId = id;
    }

    //Just a convenient method even if it uses GvType.....
    public MdDataList getData(GvDataType dataType, int size) {
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
        } else {
            return null;
        }
    }

    public MdCommentList newCommentList(int size) {
        MdCommentList list = new MdCommentList(mReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newComment());
        }

        return list;
    }

    public MdImageList newImageList(int size) {
        MdImageList list = new MdImageList(mReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newImage());
        }

        return list;
    }

    public MdLocationList newLocationList(int size) {
        MdLocationList list = new MdLocationList(mReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newLocation());
        }

        return list;
    }

    public MdFactList newFactList(int size) {
        MdFactList list = new MdFactList(mReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newFact());
        }

        return list;
    }

    public MdUrlList newUrlList(int size) {
        MdUrlList list = new MdUrlList(mReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newUrl());
        }

        return list;
    }

    public MdCommentList.MdComment newComment() {
        return new MdCommentList.MdComment(STRING_GENERATOR.nextParagraph(), RAND.nextBoolean(),
                mReviewId);
    }

    public MdImageList.MdImage newImage() {
        return new MdImageList.MdImage(BitmapMocker.nextBitmap(RAND.nextBoolean()),
                RandomDate.nextDate(), RandomString.nextSentence(),
                RAND.nextBoolean(), mReviewId);
    }

    public MdLocationList.MdLocation newLocation() {
        return new MdLocationList.MdLocation(RandomLatLng.nextLatLng(),
                RandomString.nextWord(), mReviewId);
    }

    public MdFactList.MdFact newFact() {
        return new MdFactList.MdFact(RandomString.nextWord(),
                RandomString.nextWord(),
                mReviewId);
    }

    public MdUrlList.MdUrl newUrl() {
        if (sUrl == null) {
            try {
                sUrl = new URL(URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return new MdUrlList.MdUrl(URL_LABEL, sUrl, mReviewId);
    }
}
