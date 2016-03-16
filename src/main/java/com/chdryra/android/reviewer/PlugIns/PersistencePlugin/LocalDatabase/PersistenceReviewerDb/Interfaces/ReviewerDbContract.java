/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase
        .PersistenceReviewerDb.Interfaces;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb
        .Interfaces.DbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb
        .Interfaces.DbTable;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewerDbContract extends DbContract {
    DbTable<RowReview> getReviewsTable();

    DbTable<RowComment> getCommentsTable();

    DbTable<RowFact> getFactsTable();

    DbTable<RowLocation> getLocationsTable();

    DbTable<RowImage> getImagesTable();

    DbTable<RowAuthor> getAuthorsTable();

    DbTable<RowTag> getTagsTable();
}
