/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.TestUtils;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumDateTime;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.testutils.RandomDate;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomDataDate {
    public static DataDate nextDate() {
        Date date = RandomDate.nextDate();
        return new DatumDate(RandomReviewId.nextReviewId(), date.getTime());
    }

    public static DateTime nextDateTime() {
        Date date = RandomDate.nextDate();
        return new DatumDateTime(date.getTime());
    }
}
