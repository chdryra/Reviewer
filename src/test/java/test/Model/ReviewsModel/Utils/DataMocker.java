/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.ReviewsModel.Utils;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumUrl;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataUrl;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.testutils.BitmapMocker;
import com.chdryra.android.testutils.RandomDate;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import test.TestUtils.RandomAuthor;
import test.TestUtils.RandomDataDate;
import test.TestUtils.RandomRating;
import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataMocker {
    private static final RandomString STRING_GENERATOR = new RandomString();
    private static final String URL_LABEL = "Google";
    private static final String URL = "http://www.google.co.uk/";
    private static final Random RAND = new Random();
    private static java.net.URL sUrl;

    private final ReviewId mReviewId;

    //Constructors
    public DataMocker() {
        this(RandomReviewId.nextReviewId());
    }

    public DataMocker(ReviewId id) {
        mReviewId = id;
    }

    public IdableList<DataCriterion> newCriterionList(int size) {
        IdableList<DataCriterion> list = new IdableDataList<>(mReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newCriterion());
        }

        return list;
    }

    public IdableList<DataComment> newCommentList(int size) {
        IdableList<DataComment> list = new IdableDataList<>(mReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newComment());
        }

        return list;
    }

    public IdableList<DataImage> newImageList(int size) {
        IdableList<DataImage> list = new IdableDataList<>(mReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newImage());
        }

        return list;
    }

    public IdableList<DataLocation> newLocationList(int size) {
        IdableList<DataLocation> list = new IdableDataList<>(mReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newLocation());
        }

        return list;
    }

    public IdableList<DataFact> newFactList(int size) {
        IdableList<DataFact> list = new IdableDataList<>(mReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newFact());
        }

        return list;
    }

    public IdableList<DataUrl> newUrlList(int size) {
        IdableList<DataUrl> list = new IdableDataList<>(mReviewId);
        for (int i = 0; i < size; ++i) {
            list.add(newUrl());
        }

        return list;
    }

    public DataAuthorId newAuthor() {
        return new DatumAuthorId(mReviewId, RandomAuthor.nextAuthorId().toString());
    }

    public DataDate newDate() {
        return new DatumDate(mReviewId, RandomDate.nextDate().getTime());
    }

    public DataSubject newSubject() {
        return new DatumSubject(mReviewId, RandomString.nextWord());
    }

    public DataRating newRating() {
        return new DatumRating(mReviewId, RandomRating.nextRating(), 1);
    }

    public DataCriterion newCriterion() {
        return new DatumCriterion(mReviewId, newSubject().getSubject(), newRating().getRating());
    }

    public DataComment newComment() {
        return new DatumComment(mReviewId, STRING_GENERATOR.nextParagraph(), RAND.nextBoolean());
    }

    public DataImage newImage() {
        return new DatumImage(mReviewId, BitmapMocker.nextBitmap(RAND.nextBoolean()),
                RandomDataDate.nextDateTime(), RandomString.nextSentence(),
                RandomLatLng.nextLatLng(), RAND.nextBoolean());
    }

    public DataLocation newLocation() {
        return new DatumLocation(mReviewId, RandomLatLng.nextLatLng(),
                RandomString.nextWord());
    }

    public DataFact newFact() {
        return new DatumFact(mReviewId, RandomString.nextWord(), RandomString.nextWord()
        );
    }

    public DataUrl newUrl() {
        if (sUrl == null) {
            try {
                sUrl = new URL(URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return new DatumUrl(mReviewId, URL_LABEL, sUrl);
    }
}
