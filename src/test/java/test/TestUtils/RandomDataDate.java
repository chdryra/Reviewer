package test.TestUtils;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.testutils.RandomDate;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomDataDate {
    public static DataDateReview nextDateReview() {
        Date date = RandomDate.nextDate();
        return new DatumDateReview(RandomReviewId.nextReviewId(), date.getTime());
    }

    public static DataDate nextDate() {
        return nextDateReview();
    }
}
