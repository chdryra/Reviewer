package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.PublishDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewStamp;

/**
 * Created by: Rizwan Choudrey
 * On: 18/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomPublisher {
    //Static methods
    public static ReviewStamp nextPublisher() {
        DatumAuthor author = RandomAuthor.nextAuthor();
        PublishDate date = RandomPublishDate.nextDate();
        return new ReviewStamp(author, date);
    }
}
