package com.chdryra.android.reviewer.Database.Interfaces;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;

import java.util.ArrayList;

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

    @Override
    ArrayList<DbTable<? extends DbTableRow>> getTables();

    @Override
    ArrayList<String> getTableNames();
}
