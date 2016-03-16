/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowTag;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/03/2015
 * Email: rizwan.choudrey@gmail.com
 */

public final class ReviewerDbContractImpl implements ReviewerDbContract {
    private TableAuthors mAuthorsTable;
    private TableTags mTagsTable;
    private TableReviews mReviewsTable;
    private TableComments mCommentsTable;
    private TableFacts mFactsTable;
    private TableImages mImagesTable;
    private TableLocations mLocationsTable;

    ArrayList<DbTable<? extends DbTableRow>> mTablesList;
    ArrayList<String> mTableNames;

    public ReviewerDbContractImpl(TableAuthors authorsTable, TableTags
            tagsTable, TableReviews reviewsTable, TableComments commentsTable, TableFacts
                                          factsTable, TableImages imagesTable, TableLocations locationsTable) {
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

    @Override
    public ArrayList<DbTable<? extends DbTableRow>> getTables() {
        return mTablesList;
    }

    @Override
    public ArrayList<String> getTableNames() {
        return mTableNames;
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
