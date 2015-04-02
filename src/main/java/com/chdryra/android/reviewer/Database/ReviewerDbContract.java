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
    private static ReviewerDbContract sContract;
    private        ArrayList<SQLiteTableDefinition> mTables;
    private        ArrayList<String>                mTableNames;

    private ReviewerDbContract() {
        mTables = new ArrayList<>();
        mTables.add(TableReviews.get());
        mTables.add(TableReviewTrees.get());
        mTables.add(TableComments.get());
        mTables.add(TableFacts.get());
        mTables.add(TableLocations.get());
        mTables.add(TableImages.get());
        mTables.add(TableTags.get());
        mTables.add(TableAuthors.get());

        mTableNames = new ArrayList<>();
        for (SQLiteTableDefinition table : mTables) {
            mTableNames.add(table.getName());
        }
    }

    public static ReviewerDbContract getContract() {
        if (sContract == null) sContract = new ReviewerDbContract();
        return sContract;
    }

    @Override
    public ArrayList<SQLiteTableDefinition> getTableDefinitions() {
        return mTables;
    }

    @Override
    public ArrayList<String> getTableNames() {
        return mTableNames;
    }

    public static class TableReviews extends ReviewerDbTable {
        public static final String TABLE_NAME               = "Reviews";
        public static final String COLUMN_NAME_REVIEW_ID    = "review_id";
        public static final String COLUMN_NAME_AUTHOR_ID    = "author_id";
        public static final String COLUMN_NAME_PUBLISH_DATE = "publish_date";
        public static final String COLUMN_NAME_SUBJECT      = "subject";
        public static final String COLUMN_NAME_RATING       = "rating";


        private static ReviewerDbTable sTable;

        private TableReviews() {
            super(TABLE_NAME);
            addPrimaryKey(COLUMN_NAME_REVIEW_ID, SQL.StorageType.TEXT);
            addColumn(COLUMN_NAME_AUTHOR_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_PUBLISH_DATE, SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_SUBJECT, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_RATING, SQL.StorageType.REAL, SQL.Nullable.FALSE);
            addForeignKeyConstraint(new String[]{COLUMN_NAME_AUTHOR_ID}, TableAuthors.get());
        }

        public static ReviewerDbTable get() {
            if (sTable == null) sTable = new TableReviews();
            return sTable;
        }
    }

    public static class TableReviewTrees extends ReviewerDbTable {
        public static final String TABLE_NAME                    = "ReviewTrees";
        public static final String COLUMN_NAME_REVIEW_NODE_ID    = "review_node_id";
        public static final String COLUMN_NAME_REVIEW_ID         = "review_id";
        public static final String COLUMN_NAME_PARENT_NODE_ID    = "parent_node_id";
        public static final String COLUMN_NAME_RATING_IS_AVERAGE = "is_average";

        private static ReviewerDbTable sTable;

        private TableReviewTrees() {
            super(TABLE_NAME);
            addPrimaryKey(COLUMN_NAME_REVIEW_NODE_ID, SQL.StorageType.TEXT);
            addColumn(COLUMN_NAME_REVIEW_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_PARENT_NODE_ID, SQL.StorageType.TEXT, SQL.Nullable.TRUE);
            addColumn(COLUMN_NAME_RATING_IS_AVERAGE, SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
            addForeignKeyConstraint(new String[]{COLUMN_NAME_REVIEW_ID}, TableReviews.get());
            addForeignKeyConstraint(new String[]{COLUMN_NAME_PARENT_NODE_ID}, this);
        }

        public static ReviewerDbTable get() {
            if (sTable == null) sTable = new TableReviewTrees();
            return sTable;
        }
    }

    public static class TableComments extends ReviewerDbTable {
        public static final String TABLE_NAME              = "Comments";
        public static final String COLUMN_NAME_COMMENT_ID  = "comment_id";
        public static final String COLUMN_NAME_REVIEW_ID   = "review_id";
        public static final String COLUMN_NAME_COMMENT     = "comment";
        public static final String COLUMN_NAME_IS_HEADLINE = "is_headline";

        private static ReviewerDbTable sTable;

        private TableComments() {
            super(TABLE_NAME);
            addPrimaryKey(COLUMN_NAME_COMMENT_ID, SQL.StorageType.TEXT);
            addColumn(COLUMN_NAME_REVIEW_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_COMMENT, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_IS_HEADLINE, SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
            addForeignKeyConstraint(new String[]{COLUMN_NAME_REVIEW_ID}, TableReviews.get());
        }

        public static ReviewerDbTable get() {
            if (sTable == null) sTable = new TableComments();
            return sTable;
        }
    }

    public static class TableFacts extends ReviewerDbTable {
        public static final String TABLE_NAME            = "Facts";
        public static final String COLUMN_NAME_FACT_ID   = "fact_id";
        public static final String COLUMN_NAME_REVIEW_ID = "review_id";
        public static final String COLUMN_NAME_LABEL     = "label";
        public static final String COLUMN_NAME_VALUE     = "value";
        public static final String COLUMN_NAME_IS_URL    = "is_url";

        private static ReviewerDbTable sTable;

        private TableFacts() {
            super(TABLE_NAME);
            addPrimaryKey(COLUMN_NAME_FACT_ID, SQL.StorageType.TEXT);
            addColumn(COLUMN_NAME_REVIEW_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_LABEL, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_VALUE, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_IS_URL, SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
            addForeignKeyConstraint(new String[]{COLUMN_NAME_REVIEW_ID}, TableReviews.get());
        }

        public static ReviewerDbTable get() {
            if (sTable == null) sTable = new TableFacts();
            return sTable;
        }
    }

    public static class TableLocations extends ReviewerDbTable {
        public static final String TABLE_NAME              = "Locations";
        public static final String COLUMN_NAME_LOCATION_ID = "location_id";
        public static final String COLUMN_NAME_REVIEW_ID   = "review_id";
        public static final String COLUMN_NAME_LATITUDE    = "latitude";
        public static final String COLUMN_NAME_LONGITUDE   = "longitude";
        public static final String COLUMN_NAME_NAME        = "name";

        private static ReviewerDbTable sTable;

        private TableLocations() {
            super(TABLE_NAME);
            addPrimaryKey(COLUMN_NAME_LOCATION_ID, SQL.StorageType.TEXT);
            addColumn(COLUMN_NAME_REVIEW_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_LATITUDE, SQL.StorageType.REAL, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_LONGITUDE, SQL.StorageType.REAL, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_NAME, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addForeignKeyConstraint(new String[]{COLUMN_NAME_REVIEW_ID}, TableReviews.get());
        }

        public static ReviewerDbTable get() {
            if (sTable == null) sTable = new TableLocations();
            return sTable;
        }
    }

    public static class TableImages extends ReviewerDbTable {
        public static final String TABLE_NAME            = "Images";
        public static final String COLUMN_NAME_IMAGE_ID  = "image_id";
        public static final String COLUMN_NAME_REVIEW_ID = "review_id";
        public static final String COLUMN_NAME_BITMAP    = "bitmap";
        public static final String COLUMN_NAME_CAPTION   = "caption";
        public static final String COLUMN_NAME_IS_COVER  = "is_cover";

        private static ReviewerDbTable sTable;

        private TableImages() {
            super(TABLE_NAME);
            addPrimaryKey(COLUMN_NAME_IMAGE_ID, SQL.StorageType.TEXT);
            addColumn(COLUMN_NAME_REVIEW_ID, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_BITMAP, SQL.StorageType.BLOB, SQL.Nullable.FALSE);
            addColumn(COLUMN_NAME_CAPTION, SQL.StorageType.TEXT, SQL.Nullable.TRUE);
            addColumn(COLUMN_NAME_IS_COVER, SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
            addForeignKeyConstraint(new String[]{COLUMN_NAME_REVIEW_ID}, TableReviews.get());
        }

        public static ReviewerDbTable get() {
            if (sTable == null) sTable = new TableImages();
            return sTable;
        }
    }

    public static class TableTags extends ReviewerDbTable {
        public static final String TABLE_NAME          = "Tags";
        public static final String COLUMN_NAME_TAG     = "tag";
        public static final String COLUMN_NAME_REVIEWS = "reviews";

        private static ReviewerDbTable sTable;

        private TableTags() {
            super(TABLE_NAME);
            addPrimaryKey(COLUMN_NAME_TAG, SQL.StorageType.TEXT);
            addColumn(COLUMN_NAME_REVIEWS, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        }

        public static ReviewerDbTable get() {
            if (sTable == null) sTable = new TableTags();
            return sTable;
        }
    }

    public static class TableAuthors extends ReviewerDbTable {
        public static final String TABLE_NAME          = "Authors";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_NAME    = "name";

        private static ReviewerDbTable sTable;

        private TableAuthors() {
            super(TABLE_NAME);
            addPrimaryKey(COLUMN_NAME_USER_ID, SQL.StorageType.TEXT);
            addColumn(COLUMN_NAME_NAME, SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        }

        public static ReviewerDbTable get() {
            if (sTable == null) sTable = new TableTags();
            return sTable;
        }
    }

    public static class ReviewerDbTable extends SQLiteTableDefinition {
        public ReviewerDbTable(String tableName) {
            super(tableName);
        }
    }
}
