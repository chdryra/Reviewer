/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 30 March, 2015
 */

package com.chdryra.android.reviewer.Database;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/03/2015
 * Email: rizwan.choudrey@gmail.com
 */

public final class ReviewerDbContract implements DbContract, ReviewerDbTables {
    private ReviewerDbTables mTables;
    private ArrayList<DbTable> mTablesList;
    private ArrayList<String> mTableNames;

    public ReviewerDbContract(ReviewerDbTables tables) {
        mTables = tables;

        mTablesList = new ArrayList<>();
        mTablesList.add(tables.getAuthorsTable());
        mTablesList.add(tables.getReviewsTable());
        mTablesList.add(tables.getCommentsTable());
        mTablesList.add(tables.getFactsTable());
        mTablesList.add(tables.getLocationsTable());
        mTablesList.add(tables.getImagesTable());
        mTablesList.add(tables.getTagsTable());

        mTableNames = new ArrayList<>();
        for (DbTable table : mTablesList) {
            mTableNames.add(table.getName());
        }
    }

    //Overridden
    @Override
    public ArrayList<DbTable> getTables() {
        return mTablesList;
    }

    @Override
    public ArrayList<String> getTableNames() {
        return mTableNames;
    }

    @Override
    public String getColumnNameReviewId() {
        return mTables.getColumnNameReviewId();
    }

    @Override
    public DbTable<RowReview> getReviewsTable() {
        return mTables.getReviewsTable();
    }

    @Override
    public DbTable<RowComment> getCommentsTable() {
        return mTables.getCommentsTable();
    }

    @Override
    public DbTable<RowFact> getFactsTable() {
        return mTables.getFactsTable();
    }

    @Override
    public DbTable<RowLocation> getLocationsTable() {
        return mTables.getLocationsTable();
    }

    @Override
    public DbTable<RowImage> getImagesTable() {
        return mTables.getImagesTable();
    }

    @Override
    public DbTable<RowAuthor> getAuthorsTable() {
        return mTables.getAuthorsTable();
    }

    @Override
    public DbTable<RowTag> getTagsTable() {
        return mTables.getTagsTable();
    }
}
