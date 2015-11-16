package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.PublishDate;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Models.UserModel.Author;

/**
 * Created by: Rizwan Choudrey
 * On: 18/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomPublisher {
    //Static methods
    public static ReviewPublisher nextPublisher() {
        Author author = RandomAuthor.nextAuthor();
        PublishDate date = RandomPublishDate.nextDate();
        return new ReviewPublisher(author, date);
    }
}
