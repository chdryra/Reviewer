/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.ReviewsModel.MdConverters;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdComment;
import com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters.MdConverterComments;
import com.chdryra.android.testutils.RandomString;

import java.util.Random;

import test.TestUtils.DataEquivalence;
import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 04/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterCommentsTest extends MdConverterBasicTest<DataComment, MdComment> {
    public MdConverterCommentsTest() {
        super(new MdConverterComments());
    }

    @Override
    protected DataComment newDatum() {
        return new Comment();
    }

    @Override
    protected void checkDatumEquivalence(DataComment datum, MdComment mdDatum) {
        DataEquivalence.checkEquivalence(datum, mdDatum);
    }

    private static class Comment implements DataComment {
        private static final Random RAND = new Random();
        private String mComment;
        private boolean mIsHeadline;
        private ReviewId mId;

        public Comment() {
            mComment = RandomString.nextSentence();
            mIsHeadline = RAND.nextBoolean();
            mId = RandomReviewId.nextReviewId();
        }

        @Override
        public String getComment() {
            return mComment;
        }

        @Override
        public boolean isHeadline() {
            return mIsHeadline;
        }

        @Override
        public ReviewId getReviewId() {
            return mId;
        }

        @Override
        public boolean hasData(DataValidator dataValidator) {
            return true;
        }
    }
}
