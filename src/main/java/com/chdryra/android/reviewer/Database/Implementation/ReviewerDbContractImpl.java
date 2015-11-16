/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 30 March, 2015
 */

package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.Database.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.Database.Interfaces.RowComment;
import com.chdryra.android.reviewer.Database.Interfaces.RowFact;
import com.chdryra.android.reviewer.Database.Interfaces.RowImage;
import com.chdryra.android.reviewer.Database.Interfaces.RowLocation;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.Database.Interfaces.RowTag;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/03/2015
 * Email: rizwan.choudrey@gmail.com
 */

public final class ReviewerDbContractImpl implements ReviewerDbContract {
    private String mColumnNameReviewId;
    private TableAuthors mAuthorsTable;
    private TableTags mTagsTable;
    private TableReviews mReviewsTable;
    private TableComments mCommentsTable;
    private TableFacts mFactsTable;
    private TableImages mImagesTable;
    private TableLocations mLocationsTable;

    ArrayList<DbTable<? extends DbTableRow>> mTablesList;
    ArrayList<String> mTableNames;

    public ReviewerDbContractImpl(String columnNameReviewId, TableAuthors authorsTable, TableTags
            tagsTable, TableReviews reviewsTable, TableComments commentsTable, TableFacts
                                          factsTable, TableImages imagesTable, TableLocations locationsTable) {
        mColumnNameReviewId = columnNameReviewId;
        mAuthorsTable = authorsTable;
        mTagsTable = tagsTable;
        mReviewsTable = reviewsTable;
        mCommentsTable = commentsTable;
        mFactsTable = factsTable;
        mImagesTable = imagesTable;
        mLocationsTable = locationsTable;

        mTablesList = new ArrayList<>();
        mTablesList.add(getAuthorsTable());
        mTablesList.add(getReviewsTable());
        mTablesList.add(getCommentsTable());
        mTablesList.add(getFactsTable());
        mTablesList.add(getLocationsTable());
        mTablesList.add(getImagesTable());
        mTablesList.add(getTagsTable());

        mTableNames = new ArrayList<>();
        for (DbTable table : getTables()) {
            mTableNames.add(table.getName());
        }
    }

    //Overridden
    @Override
    public ArrayList<DbTable<? extends DbTableRow>> getTables() {
        return mTablesList;
    }

    @Override
    public ArrayList<String> getTableNames() {
        return mTableNames;
    }

    @Override
    public String getColumnNameReviewId() {
        return mColumnNameReviewId;
    }

    @Override
    public DbTable<RowReview> getReviewsTable() {
        return mReviewsTable;
    }

    @Override
    public DbTable<RowComment> getCommentsTable() {
        return mCommentsTable;
    }

    @Override
    public DbTable<RowFact> getFactsTable() {
        return mFactsTable;
    }

    @Override
    public DbTable<RowLocation> getLocationsTable() {
        return mLocationsTable;
    }

    @Override
    public DbTable<RowImage> getImagesTable() {
        return mImagesTable;
    }

    @Override
    public DbTable<RowAuthor> getAuthorsTable() {
        return mAuthorsTable;
    }

    @Override
    public DbTable<RowTag> getTagsTable() {
        return mTagsTable;
    }
}
