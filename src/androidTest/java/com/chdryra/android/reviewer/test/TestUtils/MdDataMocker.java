/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdComment;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCommentList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdFact;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdFactList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdImage;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdImageList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdLocation;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdLocationList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdUrl;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdUrlList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
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
    private static final String URL_LABEL = "Google";
    private static final String URL = "http://www.google.co.uk/";
    private static final Random RAND = new Random();
    private static URL sUrl;

    private final MdReviewId mMdReviewId;

    //Constructors
    public MdDataMocker() {
        mMdReviewId = RandomReviewId.nextId();
    }

    public MdDataMocker(MdReviewId id) {
        mMdReviewId = id;
    }

    //Just a convenient method even if it uses GvType.....
    public MdDataList getData(GvDataType dataType, int size) {
        if (dataType.equals(GvComment.TYPE)) {
            return newCommentList(size);
        } else if (dataType.equals(GvFact.TYPE)) {
            return newFactList(size);
        } else if (dataType.equals(GvImage.TYPE)) {
            return newImageList(size);
        } else if (dataType.equals(GvLocation.TYPE)) {
            return newLocationList(size);
        } else if (dataType.equals(GvUrl.TYPE)) {
            return newUrlList(size);
        } else {
            return null;
        }
    }

    public MdCommentList newCommentList(int size) {
        MdCommentList list = new MdCommentList(mMdReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newComment());
        }

        return list;
    }

    public MdImageList newImageList(int size) {
        MdImageList list = new MdImageList(mMdReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newImage());
        }

        return list;
    }

    public MdLocationList newLocationList(int size) {
        MdLocationList list = new MdLocationList(mMdReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newLocation());
        }

        return list;
    }

    public MdFactList newFactList(int size) {
        MdFactList list = new MdFactList(mMdReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newFact());
        }

        return list;
    }

    public MdUrlList newUrlList(int size) {
        MdUrlList list = new MdUrlList(mMdReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newUrl());
        }

        return list;
    }

    public MdComment newComment() {
        return new MdComment(mMdReviewId, STRING_GENERATOR.nextParagraph(), RAND.nextBoolean()

        );
    }

    public MdImage newImage() {
        return new MdImage(mMdReviewId, BitmapMocker.nextBitmap(RAND.nextBoolean()),
                RandomDate.nextDate(), RandomString.nextSentence(),
                RAND.nextBoolean());
    }

    public MdLocation newLocation() {
        return new MdLocation(mMdReviewId, RandomLatLng.nextLatLng(),
                RandomString.nextWord());
    }

    public MdFact newFact() {
        return new MdFact(mMdReviewId, RandomString.nextWord(),
                RandomString.nextWord()
        );
    }

    public MdUrl newUrl() {
        if (sUrl == null) {
            try {
                sUrl = new URL(URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return new MdUrl(mMdReviewId, URL_LABEL, sUrl);
    }
}
