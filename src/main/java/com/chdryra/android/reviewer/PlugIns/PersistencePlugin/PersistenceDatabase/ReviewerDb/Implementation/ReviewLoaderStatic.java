package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.PublishDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.FactoryReviewFromDataHolder;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewerReadableDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLoaderStatic implements ReviewLoader {
    private DataValidator mValidator;
    private FactoryReviewFromDataHolder mFactory;

    public ReviewLoaderStatic(FactoryReviewFromDataHolder factory, DataValidator validator) {
        mFactory = factory;
        mValidator = validator;
    }

    @Override
    public Review loadReview(RowReview reviewRow, ReviewerReadableDb database, TableTransactor db) {
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

        return mFactory.recreateReview(reviewDb);
    }

    private ArrayList<Review> loadCriteria(ReviewerReadableDb database, TableTransactor db, String
            reviewId) {
        return database.loadReviewsWhere(db, RowReview.PARENT_ID.getName(), reviewId);
    }

    private ArrayList<RowImage> loadImages(ReviewerReadableDb database, TableTransactor db, String reviewId) {
        DbTable<RowImage> imagesTable = database.getImagesTable();
        return database.loadFromDataTable(db, imagesTable, reviewId);
    }

    private ArrayList<RowLocation> loadLocations(ReviewerReadableDb database, TableTransactor db, String
            reviewId) {
        DbTable<RowLocation> locationsTable = database.getLocationsTable();
        return database.loadFromDataTable(db, locationsTable, reviewId);
    }

    private ArrayList<RowFact> loadFacts(ReviewerReadableDb database, TableTransactor db, String reviewId) {
        DbTable<RowFact> factsTable = database.getFactsTable();
        return database.loadFromDataTable(db, factsTable, reviewId);
    }

    private ArrayList<RowComment> loadComments(ReviewerReadableDb database, TableTransactor db, String
            reviewId) {
        DbTable<RowComment> commentsTable = database.getCommentsTable();
        return database.loadFromDataTable(db, commentsTable, reviewId);
    }

    @NonNull
    private DataAuthor loadAuthor(ReviewerReadableDb database, TableTransactor db, String userId) {
        RowAuthor authorRow = database.getUniqueRowWhere(db, database.getAuthorsTable(),
                RowAuthor.USER_ID.getName(), userId);
        return new DatumAuthor(authorRow.getName(), authorRow.getUserId());
    }
}
