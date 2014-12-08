/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

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
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class RDataMocker {
    private static final RandomStringGenerator STRING_GENERATOR = new RandomStringGenerator();
    private static final Review                REVIEW           = ReviewMocker.newReview();
    private static final String URL  = "http://www.google.co.uk";
    private static final Random RAND = new Random();
    private static URL sUrl;

    private RDataMocker() {
    }

    public static RDCommentList newCommentList(int size) {
        RDCommentList list = new RDCommentList();
        for (int i = 0; i < size; ++i) {
            list.add(newComment());
        }

        return list;
    }

    public static RDImageList newImageList(int size) {
        RDImageList list = new RDImageList();
        for (int i = 0; i < size; ++i) {
            list.add(newImage());
        }

        return list;
    }

    public static RDLocationList newLocationList(int size) {
        RDLocationList list = new RDLocationList();
        for (int i = 0; i < size; ++i) {
            list.add(newLocation());
        }

        return list;
    }

    public static RDFactList newFactList(int size) {
        RDFactList list = new RDFactList();
        for (int i = 0; i < size; ++i) {
            list.add(newFact());
        }

        return list;
    }

    public static RDUrlList newUrlList(int size) {
        RDUrlList list = new RDUrlList();
        for (int i = 0; i < size; ++i) {
            list.add(newUrl());
        }

        return list;
    }

    public static RDCommentList.RDComment newComment() {
        return new RDCommentList.RDComment(STRING_GENERATOR.nextParagraph(), REVIEW);
    }

    public static RDImageList.RDImage newImage() {
        return new RDImageList.RDImage(BitmapMocker.getBitmap(RAND.nextBoolean()),
                LatLngMocker.newLatLng(), RandomStringGenerator.nextSentence(),
                RAND.nextBoolean(), REVIEW);
    }

    public static RDLocationList.RDLocation newLocation() {
        return new RDLocationList.RDLocation(LatLngMocker.newLatLng(),
                RandomStringGenerator.nextWord(), REVIEW);
    }

    public static RDFactList.RDFact newFact() {
        return new RDFactList.RDFact(RandomStringGenerator.nextWord(),
                RandomStringGenerator.nextWord(),
                REVIEW);
    }

    public static RDUrlList.RDUrl newUrl() {
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
