package com.chdryra.android.reviewer.Model.ReviewData;

import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.Model.UserData.UserId;

/**
 * Created by: Rizwan Choudrey
 * On: 18/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewPublisher {
    private Author mAuthor;
    private PublishDate mDate;
    private int mIndex;

    //Constructors
    public ReviewPublisher(Author author, PublishDate date) {
        mAuthor = author;
        mDate = date;
    }

    //public methods
    public Author getAuthor() {
        return mAuthor;
    }

    public PublishDate getDate() {
        return mDate;
    }

    public UserId getUserId() {
        return mAuthor.getUserId();
    }

    public long getTime() {
        return mDate.getTime();
    }

    public int getIncrement() {
        return mIndex++;
    }
}
