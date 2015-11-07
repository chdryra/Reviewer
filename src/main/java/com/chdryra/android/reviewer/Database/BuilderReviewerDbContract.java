package com.chdryra.android.reviewer.Database;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuilderReviewerDbContract {

    public ReviewerDbContract newContract() {
        Tables tables = new Tables();
        tables.mAuthorsTable = new TableAuthors();
        tables.mTagsTable = new TableTags();
        tables.mReviewsTable = new TableReviews(tables.mAuthorsTable);
        tables.mCommentsTable = new TableComments(tables.mReviewsTable);
        tables.mFactsTable = new TableFacts(tables.mReviewsTable);
        tables.mImagesTable = new TableImages(tables.mReviewsTable);
        tables.mLocationsTable = new TableLocations(tables.mReviewsTable);
        tables.mColumnNameReviewId = RowReview.COLUMN_REVIEW_ID;

        return new ReviewerDbContract(tables);
    }

    private class Tables implements ReviewerDbTables {
        private String mColumnNameReviewId;
        private TableAuthors mAuthorsTable;
        private TableTags mTagsTable;
        private TableReviews mReviewsTable;
        private TableComments mCommentsTable;
        private TableFacts mFactsTable;
        private TableImages mImagesTable;
        private TableLocations mLocationsTable;

        @Override
        public String getColumnNameReviewId() {
            return mColumnNameReviewId;
        }

        @Override
        public ReviewerDbTable<RowReview> getReviewsTable() {
            return mReviewsTable;
        }

        @Override
        public ReviewerDbTable<RowComment> getCommentsTable() {
            return mCommentsTable;
        }

        @Override
        public ReviewerDbTable<RowFact> getFactsTable() {
            return mFactsTable;
        }

        @Override
        public ReviewerDbTable<RowLocation> getLocationsTable() {
            return mLocationsTable;
        }

        @Override
        public ReviewerDbTable<RowImage> getImagesTable() {
            return mImagesTable;
        }

        @Override
        public ReviewerDbTable<RowAuthor> getAuthorsTable() {
            return mAuthorsTable;
        }

        @Override
        public ReviewerDbTable<RowTag> getTagsTable() {
            return mTagsTable;
        }
    }
}
