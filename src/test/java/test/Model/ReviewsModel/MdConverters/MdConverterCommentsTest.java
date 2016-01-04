package test.Model.ReviewsModel.MdConverters;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdComment;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters.MdConverterComments;
import com.chdryra.android.testutils.RandomString;

import java.util.Random;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

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
    protected void checkDatumEquivalence(DataComment comment, MdComment mdComment) {
        assertThat(mdComment.getReviewId().toString(), is(comment.getReviewId().toString()));
        assertThat(mdComment.getComment(), is(comment.getComment()));
        assertThat(mdComment.isHeadline(), is(comment.isHeadline()));
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
