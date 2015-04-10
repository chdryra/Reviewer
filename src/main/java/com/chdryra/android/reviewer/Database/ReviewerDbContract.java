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

public final class ReviewerDbContract implements DbContract {
    public static final String                         NAME_REVIEW_ID  = "review_id";
    public static final ReviewerDbTable<RowReview>     REVIEWS_TABLE   = TableReviews.get();
    public static final ReviewerDbTable<RowReviewNode> TREES_TABLE     = TableReviewTrees.get();
    public static final ReviewerDbTable<RowComment>    COMMENTS_TABLE  = TableComments.get();
    public static final ReviewerDbTable<RowFact>       FACTS_TABLE     = TableFacts.get();
    public static final ReviewerDbTable<RowLocation>   LOCATIONS_TABLE = TableLocations.get();
    public static final ReviewerDbTable<RowImage>      IMAGES_TABLE    = TableImages.get();
    public static final ReviewerDbTable<RowAuthor>     AUTHORS_TABLE   = TableAuthors.get();
    public static final ReviewerDbTable<RowTag>        TAGS_TABLE      = TableTags.get();

    private static ReviewerDbContract    sContract;
    private        ArrayList<DbTableDef> mTables;
    private        ArrayList<String>     mTableNames;

    private ReviewerDbContract() {
        mTables = new ArrayList<>();
        mTables.add(REVIEWS_TABLE);
        mTables.add(TREES_TABLE);
        mTables.add(COMMENTS_TABLE);
        mTables.add(FACTS_TABLE);
        mTables.add(LOCATIONS_TABLE);
        mTables.add(IMAGES_TABLE);
        mTables.add(AUTHORS_TABLE);
        mTables.add(TAGS_TABLE);

        mTableNames = new ArrayList<>();
        for (DbTableDef table : mTables) {
            mTableNames.add(table.getName());
        }
    }

    public static ReviewerDbContract getContract() {
        if (sContract == null) sContract = new ReviewerDbContract();
        return sContract;
    }

    @Override
    public ArrayList<DbTableDef> getTableDefinitions() {
        return mTables;
    }

    @Override
    public ArrayList<String> getTableNames() {
        return mTableNames;
    }

    public static class TableReviews extends ReviewerDbTable<RowReview> {
        public static final String TABLE_NAME               = "Reviews";
        public static final String COLUMN_NAME_REVIEW_ID = NAME_REVIEW_ID;
        public static final String COLUMN_NAME_AUTHOR_ID = "author_id";
        public static final String COLUMN_NAME_PUBLISH_DATE = "publish_date";
        public static final String COLUMN_NAME_SUBJECT = "subject";
        public static final String COLUMN_NAME_RATING = "rating";

        private static TableReviews sTable;

        private TableReviews() {
            super(TABLE_NAME, RowReview.class);
            addPrimaryKey(COLUMN_NAME_REVIEW_ID, SQL.StorageType.TEXT);
            addColumn(COLUMN_NAME_AUTHOR_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_PUBLISH_DATE, SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_SUBJECT, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_RATING, SQL.StorageType.REAL, SQL.Nullable.FALSE);
            addForeignKeyConstraint(new String[]{COLUMN_NAME_AUTHOR_ID}, TableAuthors.get());
        }

        private static TableReviews get() {
            if (sTable == null) sTable = new TableReviews();
            return sTable;
        }
    }

    public static class TableReviewTrees extends ReviewerDbTable<RowReviewNode> {
        public static final String TABLE_NAME                    = "ReviewTrees";
        public static final String COLUMN_NAME_REVIEW_NODE_ID    = "review_node_id";
        public static final String COLUMN_NAME_REVIEW_ID         = NAME_REVIEW_ID;
        public static final String COLUMN_NAME_PARENT_NODE_ID    = "parent_node_id";
        public static final String COLUMN_NAME_RATING_IS_AVERAGE = "is_average";

        private static TableReviewTrees sTable;

        private TableReviewTrees() {
            super(TABLE_NAME, RowReviewNode.class);
            addPrimaryKey(COLUMN_NAME_REVIEW_NODE_ID, SQL.StorageType.TEXT);
            addColumn(COLUMN_NAME_REVIEW_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_PARENT_NODE_ID, SQL.StorageType.TEXT, SQL.Nullable.TRUE);
            addColumn(COLUMN_NAME_RATING_IS_AVERAGE, SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
            addForeignKeyConstraint(new String[]{COLUMN_NAME_REVIEW_ID}, REVIEWS_TABLE);
            addForeignKeyConstraint(new String[]{COLUMN_NAME_PARENT_NODE_ID}, this);
        }

        private static TableReviewTrees get() {
            if (sTable == null) sTable = new TableReviewTrees();
            return sTable;
        }
    }

    public static class TableComments extends ReviewerDbTable<RowComment> {
        public static final String TABLE_NAME              = "Comments";
        public static final String COLUMN_NAME_COMMENT_ID  = "comment_id";
        public static final String COLUMN_NAME_REVIEW_ID   = NAME_REVIEW_ID;
        public static final String COLUMN_NAME_COMMENT     = "comment";
        public static final String COLUMN_NAME_IS_HEADLINE = "is_headline";

        private static TableComments sTable;

        private TableComments() {
            super(TABLE_NAME, RowComment.class);
            addPrimaryKey(COLUMN_NAME_COMMENT_ID, SQL.StorageType.TEXT);
            addColumn(COLUMN_NAME_REVIEW_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_COMMENT, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_IS_HEADLINE, SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
            addForeignKeyConstraint(new String[]{COLUMN_NAME_REVIEW_ID}, REVIEWS_TABLE);
        }

        private static TableComments get() {
            if (sTable == null) sTable = new TableComments();
            return sTable;
        }
    }

    public static class TableFacts extends ReviewerDbTable<RowFact> {
        public static final String TABLE_NAME            = "Facts";
        public static final String COLUMN_NAME_FACT_ID   = "fact_id";
        public static final String COLUMN_NAME_REVIEW_ID = NAME_REVIEW_ID;
        public static final String COLUMN_NAME_LABEL     = "label";
        public static final String COLUMN_NAME_VALUE     = "value";
        public static final String COLUMN_NAME_IS_URL    = "is_url";

        private static TableFacts sTable;

        private TableFacts() {
            super(TABLE_NAME, RowFact.class);
            addPrimaryKey(COLUMN_NAME_FACT_ID, SQL.StorageType.TEXT);
            addColumn(COLUMN_NAME_REVIEW_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_LABEL, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_VALUE, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_IS_URL, SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
            addForeignKeyConstraint(new String[]{COLUMN_NAME_REVIEW_ID}, REVIEWS_TABLE);
        }

        private static TableFacts get() {
            if (sTable == null) sTable = new TableFacts();
            return sTable;
        }
    }

    public static class TableLocations extends ReviewerDbTable<RowLocation> {
        public static final String TABLE_NAME              = "Locations";
        public static final String COLUMN_NAME_LOCATION_ID = "location_id";
        public static final String COLUMN_NAME_REVIEW_ID   = NAME_REVIEW_ID;
        public static final String COLUMN_NAME_LATITUDE    = "latitude";
        public static final String COLUMN_NAME_LONGITUDE   = "longitude";
        public static final String COLUMN_NAME_NAME        = "name";

        private static TableLocations sTable;

        private TableLocations() {
            super(TABLE_NAME, RowLocation.class);
            addPrimaryKey(COLUMN_NAME_LOCATION_ID, SQL.StorageType.TEXT);
            addColumn(COLUMN_NAME_REVIEW_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_LATITUDE, SQL.StorageType.REAL, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_LONGITUDE, SQL.StorageType.REAL, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_NAME, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addForeignKeyConstraint(new String[]{COLUMN_NAME_REVIEW_ID}, REVIEWS_TABLE);
        }

        private static TableLocations get() {
            if (sTable == null) sTable = new TableLocations();
            return sTable;
        }
    }

    public static class TableImages extends ReviewerDbTable<RowImage> {
        public static final String TABLE_NAME            = "Images";
        public static final String COLUMN_NAME_IMAGE_ID  = "image_id";
        public static final String COLUMN_NAME_REVIEW_ID = NAME_REVIEW_ID;
        public static final String COLUMN_NAME_BITMAP    = "bitmap";
        public static final String COLUMN_NAME_IMAGE_DATE   = "image_date";
        public static final String COLUMN_NAME_CAPTION   = "caption";
        public static final String COLUMN_NAME_IS_COVER  = "is_cover";

        private static TableImages sTable;

        private TableImages() {
            super(TABLE_NAME, RowImage.class);
            addPrimaryKey(COLUMN_NAME_IMAGE_ID, SQL.StorageType.TEXT);
            addColumn(COLUMN_NAME_REVIEW_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_BITMAP, SQL.StorageType.BLOB, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_IMAGE_DATE, SQL.StorageType.REAL, SQL.Nullable.TRUE);
            addColumn(COLUMN_NAME_CAPTION, SQL.StorageType.TEXT, SQL.Nullable.TRUE);
            addColumn(COLUMN_NAME_IS_COVER, SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
            addForeignKeyConstraint(new String[]{COLUMN_NAME_REVIEW_ID}, REVIEWS_TABLE);
        }

        private static TableImages get() {
            if (sTable == null) sTable = new TableImages();
            return sTable;
        }
    }

    public static class TableTags extends ReviewerDbTable<RowTag> {
        public static final String TABLE_NAME          = "Tags";
        public static final String COLUMN_NAME_TAG     = "tag";
        public static final String COLUMN_NAME_REVIEWS = "reviews";

        private static TableTags sTable;

        private TableTags() {
            super(TABLE_NAME, RowTag.class);
            addPrimaryKey(COLUMN_NAME_TAG, SQL.StorageType.TEXT);
            addColumn(COLUMN_NAME_REVIEWS, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        }

        private static TableTags get() {
            if (sTable == null) sTable = new TableTags();
            return sTable;
        }
    }

    public static class TableAuthors extends ReviewerDbTable<RowAuthor> {
        public static final String TABLE_NAME          = "Authors";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_NAME    = "name";

        private static TableAuthors sTable;

        private TableAuthors() {
            super(TABLE_NAME, RowAuthor.class);
            addPrimaryKey(COLUMN_NAME_USER_ID, SQL.StorageType.TEXT);
            addColumn(COLUMN_NAME_NAME, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        }

        private static TableAuthors get() {
            if (sTable == null) sTable = new TableAuthors();
            return sTable;
        }
    }
}
