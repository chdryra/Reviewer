package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.PublishDate;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation
        .ReviewPublisherImpl;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Models.UserModel.Author;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewPublisher {
    private final Author mAuthor;

    public Author getAuthor() {
        return mAuthor;
    }

    public FactoryReviewPublisher(Author author) {
        mAuthor = author;
    }

    public ReviewPublisher newPublisher() {
        Date date = new Date();
        PublishDate publishDate = new PublishDate(date.getTime());
        return new ReviewPublisherImpl(mAuthor, publishDate);
    }
}
