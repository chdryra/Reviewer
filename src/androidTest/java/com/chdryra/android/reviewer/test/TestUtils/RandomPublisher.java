package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewPublisher;
import com.chdryra.android.reviewer.Model.UserData.Author;

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
