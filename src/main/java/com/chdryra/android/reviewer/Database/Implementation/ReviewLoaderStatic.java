package com.chdryra.android.reviewer.Database.Implementation;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.PublishDate;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.Interfaces.BuilderReview;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Database.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.Database.Interfaces.RowComment;
import com.chdryra.android.reviewer.Database.Interfaces.RowFact;
import com.chdryra.android.reviewer.Database.Interfaces.RowImage;
import com.chdryra.android.reviewer.Database.Interfaces.RowLocation;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.Models.UserModel.UserId;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLoaderStatic implements ReviewLoader {
    private DataValidator mValidator;
    private BuilderReview mBuilder;
    

    public ReviewLoaderStatic(BuilderReview builder, DataValidator validator) {
        mBuilder = builder;
        mValidator = validator;
    }

    @Override
    public Review loadReview(RowReview reviewRow, ReviewerDb database, SQLiteDatabase db) {
        if (!reviewRow.hasData(mValidator)) return null;
        
        String reviewId = reviewRow.getReviewId();

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

        ArrayList<Review> critList = database.loadReviewsFromDbWhere(db,
                RowReview.COLUMN_PARENT_ID, reviewId);

        ReviewDataHolder reviewDb = new ReviewDataHolderImpl(reviewId, author, publishDate, subject, rating,
                ratingWeight, comments, images, facts, locations, critList, isAverage);

        return mBuilder.createReview(reviewDb);
    }
}
