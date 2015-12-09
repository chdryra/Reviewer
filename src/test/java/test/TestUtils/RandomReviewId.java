package test.TestUtils;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;
import com.chdryra.android.testutils.RandomString;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomReviewId {
    private static final Random RAND = new Random();

    //Static methods
    public static MdReviewId nextMdReviewId() {
        return new MdReviewId(RandomString.nextWord(), RAND.nextLong(), RAND.nextInt());
    }

    public static String nextIdString() {
        return nextMdReviewId().toString();
    }

    public static GvReviewId nextGvReviewId() {
        return new GvReviewId(nextIdString());
    }
}
