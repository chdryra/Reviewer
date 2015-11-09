package com.chdryra.android.reviewer.Database;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.Model.UserData.UserId;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLoaderStatic implements ReviewerDb.ReviewLoader{
    private DataValidator mValidator;
    private ReviewDataHolder.BuilderReviewUser mBuilder;
    

    public ReviewLoaderStatic(ReviewDataHolder.BuilderReviewUser builder) {
        mBuilder = builder;
    }

//    public Review loadReview(RowReview reviewRow, ReviewerDb database, SQLiteDatabase db) {
//        if (!reviewRow.hasData()) return null;
//
//        ContentValues values = reviewRow.getContentValues();
//
//        String reviewId = values.getAsString(RowReview.COLUMN_REVIEW_ID);
//        ReviewId id = ReviewId.fromString(values.getAsString(RowReview.COLUMN_REVIEW_ID));
//        String userId = values.getAsString(RowReview.COLUMN_AUTHOR_ID);
//        Author author = database.getRowWhere(db, database.getAuthorsTable(), RowAuthor
//                .COLUMN_USER_ID, userId).toAuthor();
//        PublishDate publishDate = new PublishDate(values.getAsLong(RowReview.COLUMN_PUBLISH_DATE));
//        String subject = values.getAsString(RowReview.COLUMN_SUBJECT);
//        float rating = values.getAsFloat(RowReview.COLUMN_RATING);
//        MdCommentList comments = database.loadFromDataTable(db, database.getCommentsTable(),
//                reviewId, MdCommentList.class);
//        MdFactList facts = database.loadFromDataTable(db, database.getFactsTable(), reviewId,
//                MdFactList.class);
//        MdLocationList locations = database.loadFromDataTable(db, database.getLocationsTable(),
//                reviewId, MdLocationList.class);
//        MdImageList images = database.loadFromDataTable(db, database.getImagesTable(), reviewId,
//                MdImageList.class);
//        IdableList<Review> critList = database.loadReviewsFromDbWhere(db, RowReview
//                .COLUMN_PARENT_ID, reviewId);
//        boolean isAverage = values.getAsBoolean(RowReview.COLUMN_RATING_IS_AVERAGE);
//
//        ReviewDataHolder reviewDb = new ReviewDataHolder(id, author, publishDate, subject, rating,
//                comments, images, facts, locations, critList, isAverage);
//
//        return mBuilder.createReviewUser(reviewDb);
//    }

    public Review loadReview(RowReview reviewRow, ReviewerDb database, SQLiteDatabase db) {
        if (!reviewRow.hasData(mValidator)) return null;
        
        String reviewId = reviewRow.getReviewId();
        ReviewId id = ReviewId.fromString(reviewId);
        
        String userId = reviewRow.getAuthorId();
        String column = RowAuthor.COLUMN_USER_ID;
        RowAuthor authorRow = database.getRowWhere(db, database.getAuthorsTable(), column, userId);
        Author author = new Author(authorRow.getName(), UserId.fromString(authorRow.getUserId()));
        
        PublishDate publishDate = new PublishDate(reviewRow.getPublishDate());
        
        String subject = reviewRow.getSubject();
        float rating = reviewRow.getRating();
        int ratingWeight = reviewRow.getRatingWeight();
        boolean isAverage = reviewRow.isRatingIsAverage();
        
        DbTable<RowComment> commentsTable = database.getCommentsTable();
        ArrayList<RowComment> comments = database.loadFromDataTable(db, commentsTable, reviewId);

        DbTable<RowFact> factsTable = database.getFactsTable();
        ArrayList<RowFact> facts = database.loadFromDataTable(db, factsTable, reviewId);

        DbTable<RowLocation> locationsTable = database.getLocationsTable();
        ArrayList<RowLocation> locations = database.loadFromDataTable(db, locationsTable, reviewId);

        DbTable<RowImage> imagesTable = database.getImagesTable();
        ArrayList<RowImage> images = database.loadFromDataTable(db, imagesTable, reviewId);

        IdableList<Review> critList = database.loadReviewsFromDbWhere(db,
                RowReview.COLUMN_PARENT_ID, reviewId);

        ReviewDataHolder reviewDb = new ReviewDataHolder(id, author, publishDate, subject, rating,
                ratingWeight, comments, images, facts, locations, critList, isAverage);

        return mBuilder.createReviewUser(reviewDb);
    }
}
