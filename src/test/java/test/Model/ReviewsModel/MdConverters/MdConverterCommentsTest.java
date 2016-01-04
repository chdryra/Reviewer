package test.Model.ReviewsModel.MdConverters;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdComment;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters
        .MdConverterComments;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Random;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 04/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterCommentsTest {
    private static final int NUM = 10;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private MdConverterComments mConverter;

    @Before
    public void setup() {
        mConverter = new MdConverterComments();
    }

    @Test
    public void convertDatum() {
        DataComment comment = newDatum();
        MdComment mdComment = mConverter.convert(comment);
        checkDatum(comment, mdComment);
    }

    @Test
    public void convertDatumWithCorrectReviewId() {
        DataComment datum = newDatum();
        MdComment mdDatum = mConverter.convert(datum, datum.getReviewId());
        checkDatum(datum, mdDatum);
    }

    @Test
    public void convertDatumWithWrongReviewIdThrowsIllegalArgumentExceptionException() {
        expectedException.expect(IllegalArgumentException.class);
        mConverter.convert(newDatum(), RandomReviewId.nextReviewId());
    }

    @Test
    public void convertIterable() {
        ArrayList<DataComment> data = new ArrayList<>();
        for (int i = 0; i < NUM; ++i) {
            data.add(newDatum());
        }
        ReviewId id = RandomReviewId.nextReviewId();

        MdDataList<MdComment> mdData = mConverter.convert(data, id);

        assertThat(mdData.getReviewId().toString(), is(id.toString()));
        assertThat(mdData.size(), is(NUM));
        for (int i = 0; i < NUM; ++i) {
            checkDatum(data.get(i), mdData.getItem(i));
        }
    }

    @Test
    public void convertIdable() {
        ReviewId id = RandomReviewId.nextReviewId();
        IdableDataList<DataComment> data = new IdableDataList<>(id);
        for (int i = 0; i < NUM; ++i) {
            data.add(newDatum());
        }

        MdDataList<MdComment> mdData = mConverter.convert(data);

        assertThat(mdData.getReviewId().toString(), is(id.toString()));
        assertThat(mdData.size(), is(NUM));
        for (int i = 0; i < NUM; ++i) {
            checkDatum(data.getItem(i), mdData.getItem(i));
        }
    }

    @Test
    public void convertIdableViaConvertIterableWithCorrectReviewIdWorks() {
        ReviewId id = RandomReviewId.nextReviewId();
        IdableDataList<DataComment> data = new IdableDataList<>(id);
        for (int i = 0; i < NUM; ++i) {
            data.add(newDatum());
        }

        MdDataList<MdComment> mdData = mConverter.convert(data, id);

        assertThat(mdData.getReviewId().toString(), is(id.toString()));
        assertThat(mdData.size(), is(NUM));
        for (int i = 0; i < NUM; ++i) {
            checkDatum(data.getItem(i), mdData.getItem(i));
        }
    }

    @Test
    public void convertIdableViaConvertIterableWithWrongReviewIdThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        ReviewId id = RandomReviewId.nextReviewId();
        IdableDataList<DataComment> data = new IdableDataList<>(id);
        for (int i = 0; i < NUM; ++i) {
            data.add(newDatum());
        }

        mConverter.convert(data, RandomReviewId.nextReviewId());
    }

    @NonNull
    private DataComment newDatum() {
        return new Comment();
    }

    private void checkDatum(DataComment comment, MdComment mdComment) {
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
