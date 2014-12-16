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
import com.chdryra.android.reviewer.GvTagList;
import com.chdryra.android.reviewer.GvUrlList;
import com.chdryra.android.testutils.BitmapFileMocker;
import com.chdryra.android.testutils.LatLngMocker;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 11/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataMocker {
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

    public static GvCommentList.GvComment newComment() {
        return new GvCommentList.GvComment(STRING_GENERATOR.nextParagraph());
    }

    public static GvImageList.GvImage newImage() {
        return new GvImageList.GvImage(BitmapFileMocker.getBitmap(RAND.nextBoolean()),
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
                RAND.nextFloat() * 5);
    }

    public static GvTagList.GvTag newTag() {
        return new GvTagList.GvTag(RandomStringGenerator.nextWord());
    }
}