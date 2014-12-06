/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.RDCommentList;
import com.chdryra.android.reviewer.RDFactList;
import com.chdryra.android.reviewer.RDImageList;
import com.chdryra.android.reviewer.RDLocationList;
import com.chdryra.android.reviewer.RDUrlList;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.testutils.BitmapMocker;
import com.chdryra.android.testutils.LatLngMocker;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class RDataMocker {
    private static final RandomStringGenerator STRING_GENERATOR = new RandomStringGenerator();
    private static final Review                REVIEW           = ReviewMocker.newReview();
    private static final Bitmap BITMAP = BitmapMocker.getBitmap();
    private static final String URL    = "http://www.google.co.uk";
    private static URL sUrl;

    private RDataMocker() {
    }
//
//    public static <T extends RData> RDList<T> newRDList(Class<T> c, int size) {
//        RDList<T> list = new RDList<>();
//        for (int i = 0; i < size; ++i)
//            list.add((T) newRData(c));
//
//        return list;
//    }
//
//    public static <T extends RData> RData newRData(Class<T> c) {
//        if (c == RDCommentList.RDComment.class) {
//            return newComment();
//        } else if (c == RDImageList.RDImage.class) {
//            return newImage();
//        } else {
//            return null;
//        }
//    }

    private static RDCommentList newCommentList(int size) {
        RDCommentList list = new RDCommentList();
        for (int i = 0; i < size; ++i) {
            list.add(newComment());
        }

        return list;
    }

    private static RDCommentList.RDComment newComment() {
        return new RDCommentList.RDComment(STRING_GENERATOR.nextParagraph(), REVIEW);
    }

    private static RDImageList.RDImage newImage() {
        return new RDImageList.RDImage(BITMAP, LatLngMocker.newLatLng(),
                RandomStringGenerator.nextSentence(),
                false, REVIEW);
    }

    private static RDLocationList.RDLocation newLocation() {
        return new RDLocationList.RDLocation(LatLngMocker.newLatLng(),
                RandomStringGenerator.nextWord(), REVIEW);
    }

    private static RDFactList.RDFact newFact() {
        return new RDFactList.RDFact(RandomStringGenerator.nextWord(),
                RandomStringGenerator.nextWord(),
                REVIEW);
    }

    private static RDUrlList.RDUrl newUrl() {
        if (sUrl == null) {
            try {
                sUrl = new URL(URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return new RDUrlList.RDUrl(sUrl, REVIEW);
    }
}
