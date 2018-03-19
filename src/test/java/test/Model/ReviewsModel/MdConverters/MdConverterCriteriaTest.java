/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.ReviewsModel.MdConverters;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdCriterion;
import com.chdryra.android.startouch.Model.ReviewsModel.MdConverters.MdConverterCriteria;
import com.chdryra.android.testutils.RandomString;

import test.TestUtils.DataEquivalence;
import test.TestUtils.RandomRating;
import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 04/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterCriteriaTest extends MdConverterBasicTest<DataCriterion, MdCriterion> {
    public MdConverterCriteriaTest() {
        super(new MdConverterCriteria());
    }

    @Override
    protected DataCriterion newDatum() {
        return new Criterion();
    }

    @Override
    protected void checkDatumEquivalence(DataCriterion datum, MdCriterion mdDatum) {
        DataEquivalence.checkEquivalence(datum, mdDatum);
    }

    @Override
    protected void checkDatumEquivalence(DataCriterion datum, MdCriterion mdDatum, ReviewId
            mdDatumId) {
        DataEquivalence.checkEquivalence(datum, mdDatum, mdDatumId);
    }

    private static class Criterion implements DataCriterion {
        private ReviewId mId;
        private String mSubject;
        private float mRating;

        public Criterion() {
            mId = RandomReviewId.nextReviewId();
            mSubject = RandomString.nextWord();
            mRating = RandomRating.nextRating();
        }

        @Override
        public String getSubject() {
            return mSubject;
        }

        @Override
        public float getRating() {
            return mRating;
        }

        @Override
        public ReviewId getReviewId() {
            return mId;
        }

        @Override
        public boolean hasData(DataValidator validator) {
            return true;
        }
    }
}
