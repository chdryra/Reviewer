package com.chdryra.android.reviewer.Database;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
interface ReviewerDbTables {
    String getColumnNameReviewId();

    ReviewerDbTable<RowReview> getReviewsTable();

    ReviewerDbTable<RowComment> getCommentsTable();

    ReviewerDbTable<RowFact> getFactsTable();

    ReviewerDbTable<RowLocation> getLocationsTable();

    ReviewerDbTable<RowImage> getImagesTable();

    ReviewerDbTable<RowAuthor> getAuthorsTable();

    ReviewerDbTable<RowTag> getTagsTable();
}
