/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.ReviewsModel.Utils;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdAuthor;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdComment;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCriterion;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDate;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdFact;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdImage;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdLocation;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdRating;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdSubject;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdUrl;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.ReviewUser;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.testutils.BitmapMocker;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Random;

import test.TestUtils.RandomAuthor;
import test.TestUtils.RandomRating;
import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdDataMocker {
    private static final RandomString STRING_GENERATOR = new RandomString();
    private static final String URL_LABEL = "Google";
    private static final String URL = "http://www.google.co.uk/";
    private static final Random RAND = new Random();
    private static java.net.URL sUrl;

    private final MdReviewId mMdReviewId;
    private final FactoryReviewNode mFactory;

    //Constructors
    public MdDataMocker() {
        this(RandomReviewId.nextMdReviewId());
    }

    public MdDataMocker(MdReviewId id) {
        mMdReviewId = id;
        mFactory = new FactoryReviewNode();
    }

    public MdDataList<MdCriterion> newCriterionList(int size) {
        MdDataList<MdCriterion> list = new MdDataList<>(mMdReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newCriterion());
        }

        return list;
    }

    public MdDataList<MdComment> newCommentList(int size) {
        MdDataList<MdComment> list = new MdDataList<>(mMdReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newComment());
        }

        return list;
    }

    public MdDataList<MdImage> newImageList(int size) {
        MdDataList<MdImage> list = new MdDataList<>(mMdReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newImage());
        }

        return list;
    }

    public MdDataList<MdLocation> newLocationList(int size) {
        MdDataList<MdLocation> list = new MdDataList<>(mMdReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newLocation());
        }

        return list;
    }

    public MdDataList<MdFact> newFactList(int size) {
        MdDataList<MdFact> list = new MdDataList<>(mMdReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newFact());
        }

        return list;
    }

    public MdDataList<MdUrl> newUrlList(int size) {
        MdDataList<MdUrl> list = new MdDataList<>(mMdReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newUrl());
        }

        return list;
    }

    public MdAuthor newAuthor() {
        DataAuthor author = RandomAuthor.nextAuthor();
        return new MdAuthor(mMdReviewId, author.getName(), author.getUserId());
    }

    public MdDate newDate() {
        return new MdDate(mMdReviewId, Math.min(new Date().getTime(), RAND.nextLong()));
    }

    public MdSubject newSubject() {
        return new MdSubject(mMdReviewId, RandomString.nextWord());
    }

    public MdRating newRating() {
        return new MdRating(mMdReviewId, RandomRating.nextRating(), 1);
    }

    public MdCriterion newCriterion() {
        return new MdCriterion(mMdReviewId, randomCriterionReview());
    }

    public MdComment newComment() {
        return new MdComment(mMdReviewId, STRING_GENERATOR.nextParagraph(), RAND.nextBoolean());
    }

    public MdImage newImage() {
        return new MdImage(mMdReviewId, BitmapMocker.nextBitmap(RAND.nextBoolean()),
                newDate(), RandomString.nextSentence(), RAND.nextBoolean());
    }

    public MdLocation newLocation() {
        return new MdLocation(mMdReviewId, RandomLatLng.nextLatLng(),
                RandomString.nextWord());
    }

    public MdFact newFact() {
        return new MdFact(mMdReviewId, RandomString.nextWord(), RandomString.nextWord()
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

    private Review randomCriterionReview() {
        return new ReviewUser(mMdReviewId, newAuthor(), newDate(), newSubject(), newRating(),
                newCommentList(0), newImageList(0), newFactList(0), newLocationList(0),
                new MdDataList<MdCriterion>(mMdReviewId), false, mFactory);
    }
}
