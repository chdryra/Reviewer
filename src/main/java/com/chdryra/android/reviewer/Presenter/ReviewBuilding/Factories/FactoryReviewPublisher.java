package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.PublishDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ReviewPublisherImpl;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewPublisher;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewPublisher {
    private final DataAuthor mAuthor;

    public DataAuthor getAuthor() {
        return mAuthor;
    }

    public FactoryReviewPublisher(DataAuthor author) {
        mAuthor = author;
    }

    public ReviewPublisher newPublisher() {
        Date date = new Date();
        PublishDate publishDate = new PublishDate(date.getTime());
        return new ReviewPublisherImpl(mAuthor, publishDate);
    }
}
