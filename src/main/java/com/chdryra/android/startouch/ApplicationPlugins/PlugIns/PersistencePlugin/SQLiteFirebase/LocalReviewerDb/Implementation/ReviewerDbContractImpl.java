/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowAuthorName;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowCriterion;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowTag;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/03/2015
 * Email: rizwan.choudrey@gmail.com
 */

public final class ReviewerDbContractImpl implements ReviewerDbContract {
    private final DbTable<RowAuthorName> mAuthorsTable;
    private final DbTable<RowTag> mTagsTable;
    private final DbTable<RowReview> mReviewsTable;
    private final DbTable<RowCriterion> mCriteriaTable;
    private final DbTable<RowComment> mCommentsTable;
    private final DbTable<RowFact> mFactsTable;
    private final DbTable<RowImage> mImagesTable;
    private final DbTable<RowLocation> mLocationsTable;

    private final ArrayList<DbTable<? extends DbTableRow>> mTablesList;
    private final ArrayList<String> mTableNames;

    public ReviewerDbContractImpl(DbTable<RowAuthorName> authorsTable, DbTable<RowTag> tagsTable,
                                  DbTable<RowReview> reviewsTable, DbTable<RowCriterion>
                                          criteriaTable, DbTable<RowComment> commentsTable,
                                  DbTable<RowFact> factsTable, DbTable<RowImage> imagesTable,
                                  DbTable<RowLocation> locationsTable) {
        mAuthorsTable = authorsTable;
        mTagsTable = tagsTable;
        mReviewsTable = reviewsTable;
        mCriteriaTable = criteriaTable;
        mCommentsTable = commentsTable;
        mFactsTable = factsTable;
        mImagesTable = imagesTable;
        mLocationsTable = locationsTable;

        mTablesList = new ArrayList<>();
        mTablesList.add(getAuthorsTable());
        mTablesList.add(getReviewsTable());
        mTablesList.add(getCriteriaTable());
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
    public DbTable<RowCriterion> getCriteriaTable() {
        return mCriteriaTable;
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
    public DbTable<RowAuthorName> getAuthorsTable() {
        return mAuthorsTable;
    }

    @Override
    public DbTable<RowTag> getTagsTable() {
        return mTagsTable;
    }
}
