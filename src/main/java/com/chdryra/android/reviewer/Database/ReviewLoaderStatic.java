package com.chdryra.android.reviewer.Database;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewData.MdIdableList;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.MdReviewId;
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
    

    public ReviewLoaderStatic(ReviewDataHolder.BuilderReviewUser builder, DataValidator validator) {
        mBuilder = builder;
        mValidator = validator;
    }

    public Review loadReview(RowReview reviewRow, ReviewerDb database, SQLiteDatabase db) {
        if (!reviewRow.hasData(mValidator)) return null;
        
        String reviewId = reviewRow.getReviewId();
        MdReviewId id = MdReviewId.fromString(reviewId);
        
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

        MdIdableList<Review> critList = database.loadReviewsFromDbWhere(db,
                RowReview.COLUMN_PARENT_ID, reviewId);

        ReviewDataHolder reviewDb = new ReviewDataHolder(id, author, publishDate, subject, rating,
                ratingWeight, comments, images, facts, locations, critList, isAverage);

        return mBuilder.createUserReview(reviewDb);
    }
}
