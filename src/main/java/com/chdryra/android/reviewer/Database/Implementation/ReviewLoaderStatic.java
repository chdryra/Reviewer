package com.chdryra.android.reviewer.Database.Implementation;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.PublishDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.Interfaces.BuilderReview;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewLoaderDb;
import com.chdryra.android.reviewer.Database.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.Database.Interfaces.RowComment;
import com.chdryra.android.reviewer.Database.Interfaces.RowFact;
import com.chdryra.android.reviewer.Database.Interfaces.RowImage;
import com.chdryra.android.reviewer.Database.Interfaces.RowLocation;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

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
    public Review loadReview(RowReview reviewRow, ReviewLoaderDb database, SQLiteDatabase db) {
        if (!reviewRow.hasData(mValidator)) return null;

        String subject = reviewRow.getSubject();
        float rating = reviewRow.getRating();
        int ratingWeight = reviewRow.getRatingWeight();
        boolean isAverage = reviewRow.isRatingIsAverage();
        PublishDate publishDate = new PublishDate(reviewRow.getPublishDate());

        String reviewId = reviewRow.getReviewId().toString();

        ArrayList<RowComment> comments = loadComments(database, db, reviewId);
        ArrayList<RowFact> facts = loadFacts(database, db, reviewId);
        ArrayList<RowLocation> locations = loadLocations(database, db, reviewId);
        ArrayList<RowImage> images = loadImages(database, db, reviewId);
        ArrayList<Review> critList = loadCriteria(database, db, reviewId);
        DataAuthor author = loadAuthor(database, db, reviewRow.getAuthorId());

        ReviewDataHolder reviewDb = new ReviewDataHolderImpl(reviewRow.getReviewId(), author,
                publishDate, subject, rating, ratingWeight, comments, images, facts, locations,
                critList, isAverage);

        return mBuilder.createReview(reviewDb);
    }

    private ArrayList<Review> loadCriteria(ReviewLoaderDb database, SQLiteDatabase db, String
            reviewId) {
        return database.loadReviewsFromDbWhere(db, RowReview.COLUMN_PARENT_ID, reviewId);
    }

    private ArrayList<RowImage> loadImages(ReviewLoaderDb database, SQLiteDatabase db, String reviewId) {
        DbTable<RowImage> imagesTable = database.getImagesTable();
        return database.loadFromDataTable(db, imagesTable, reviewId);
    }

    private ArrayList<RowLocation> loadLocations(ReviewLoaderDb database, SQLiteDatabase db, String
            reviewId) {
        DbTable<RowLocation> locationsTable = database.getLocationsTable();
        return database.loadFromDataTable(db, locationsTable, reviewId);
    }

    private ArrayList<RowFact> loadFacts(ReviewLoaderDb database, SQLiteDatabase db, String reviewId) {
        DbTable<RowFact> factsTable = database.getFactsTable();
        return database.loadFromDataTable(db, factsTable, reviewId);
    }

    private ArrayList<RowComment> loadComments(ReviewLoaderDb database, SQLiteDatabase db, String
            reviewId) {
        DbTable<RowComment> commentsTable = database.getCommentsTable();
        return database.loadFromDataTable(db, commentsTable, reviewId);
    }

    @NonNull
    private DataAuthor loadAuthor(ReviewLoaderDb database, SQLiteDatabase db, String userId) {
        String column = RowAuthor.COLUMN_USER_ID;
        RowAuthor authorRow = database.getRowWhere(db, database.getAuthorsTable(), column, userId);
        return new DatumAuthor(authorRow.getName(), authorRow.getUserId());
    }
}
