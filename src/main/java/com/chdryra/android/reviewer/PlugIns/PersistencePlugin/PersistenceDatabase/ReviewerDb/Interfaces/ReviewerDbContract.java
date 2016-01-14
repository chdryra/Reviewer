package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTable;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewerDbContract extends DbContract {
    String getColumnNameReviewId();

    DbTable<RowReview> getReviewsTable();

    DbTable<RowComment> getCommentsTable();

    DbTable<RowFact> getFactsTable();

    DbTable<RowLocation> getLocationsTable();

    DbTable<RowImage> getImagesTable();

    DbTable<RowAuthor> getAuthorsTable();

    DbTable<RowTag> getTagsTable();
}
