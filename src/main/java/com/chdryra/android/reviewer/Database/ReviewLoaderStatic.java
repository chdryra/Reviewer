package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.UserData.Author;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLoaderStatic implements ReviewerDb.ReviewLoader{
    ReviewDataHolder.BuilderReviewUser mBuilder;

    public ReviewLoaderStatic(ReviewDataHolder.BuilderReviewUser builder) {
        mBuilder = builder;
    }

    public Review loadReview(RowReview reviewRow, ReviewerDb database, SQLiteDatabase db) {
        if (!reviewRow.hasData()) return null;

        ContentValues values = reviewRow.getContentValues();

        String reviewId = values.getAsString(RowReview.COLUMN_REVIEW_ID);
        ReviewId id = ReviewId.fromString(values.getAsString(RowReview.COLUMN_REVIEW_ID));
        String userId = values.getAsString(RowReview.COLUMN_AUTHOR_ID);
        Author author = database.getRowWhere(db, database.getAuthorsTable(), RowAuthor
                .COLUMN_USER_ID, userId).toAuthor();
        PublishDate publishDate = PublishDate.then(values.getAsLong(RowReview.COLUMN_PUBLISH_DATE));
        String subject = values.getAsString(RowReview.COLUMN_SUBJECT);
        float rating = values.getAsFloat(RowReview.COLUMN_RATING);
        MdCommentList comments = database.loadFromDataTable(db, database.getCommentsTable(),
                reviewId, MdCommentList.class);
        MdFactList facts = database.loadFromDataTable(db, database.getFactsTable(), reviewId,
                MdFactList.class);
        MdLocationList locations = database.loadFromDataTable(db, database.getLocationsTable(),
                reviewId, MdLocationList.class);
        MdImageList images = database.loadFromDataTable(db, database.getImagesTable(), reviewId,
                MdImageList.class);
        IdableList<Review> critList = database.loadReviewsFromDbWhere(db, RowReview
                .COLUMN_PARENT_ID, reviewId);
        boolean isAverage = values.getAsBoolean(RowReview.COLUMN_RATING_IS_AVERAGE);

        ReviewDataHolder reviewDb = new ReviewDataHolder(id, author, publishDate, subject, rating,
                comments, images, facts, locations, critList, isAverage);

        return mBuilder.createReviewUser(reviewDb);
    }
}
