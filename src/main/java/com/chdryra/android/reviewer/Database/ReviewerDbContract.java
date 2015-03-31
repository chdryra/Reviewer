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
    public static final String NAME    = "Reviewer.db";
    public static final int    VERSION = 1;

    private static ReviewerDbContract     sContract;
    private ArrayList<SQLiteTableDefinition> mTables;

    private ReviewerDbContract() {
        mTables = new ArrayList<>();
        mTables.add(TableReviews.get());
        mTables.add(TableComments.get());
        mTables.add(TableFacts.get());
        mTables.add(TableLocations.get());
        mTables.add(TableImages.get());
        mTables.add(TableTags.get());
    }

    public static ReviewerDbContract getContract() {
        if (sContract == null) sContract = new ReviewerDbContract();
        return sContract;
    }

    @Override
    public String getDatabaseName() {
        return NAME;
    }

    @Override
    public int getVersionNumber() {
        return VERSION;
    }

    @Override
    public ArrayList<SQLiteTableDefinition> getTableDefinitions() {
        return mTables;
    }

    public static class TableReviews extends SQLiteTableDefinition {
        public static final String TABLE_NAME = "reviews";
        private static SQLiteTableDefinition sTable;

        private TableReviews() {
            super(TABLE_NAME);
            addPrimaryKey("review_id", SQL.StorageType.TEXT);
            addColumn("parent_id", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn("author_id", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn("publish_date", SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
            addColumn("subject", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn("rating", SQL.StorageType.REAL, SQL.Nullable.FALSE);
        }

        public static SQLiteTableDefinition get() {
            if (sTable == null) sTable = new TableReviews();
            return sTable;
        }
    }

    public static class TableComments extends SQLiteTableDefinition {
        public static final String TABLE_NAME = "comments";
        private static SQLiteTableDefinition sTable;

        private TableComments() {
            super(TABLE_NAME);
            addPrimaryKey("comment_id", SQL.StorageType.TEXT);
            addColumn("review_id", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn("comment", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn("is_headline", SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
            addForeignKeyConstraint(new String[]{"review_id"}, TableReviews.get());
        }

        public static SQLiteTableDefinition get() {
            if (sTable == null) sTable = new TableComments();
            return sTable;
        }
    }

    public static class TableFacts extends SQLiteTableDefinition {
        public static final String TABLE_NAME = "facts";
        private static SQLiteTableDefinition sTable;

        private TableFacts() {
            super(TABLE_NAME);
            addPrimaryKey("fact_id", SQL.StorageType.TEXT);
            addColumn("review_id", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn("label", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn("value", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn("is_url", SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
            addForeignKeyConstraint(new String[]{"review_id"}, TableReviews.get());
        }

        public static SQLiteTableDefinition get() {
            if (sTable == null) sTable = new TableFacts();
            return sTable;
        }
    }

    public static class TableLocations extends SQLiteTableDefinition {
        public static final String TABLE_NAME = "locations";
        private static SQLiteTableDefinition sTable;

        private TableLocations() {
            super(TABLE_NAME);
            addPrimaryKey("location_id", SQL.StorageType.TEXT);
            addColumn("review_id", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn("latitude", SQL.StorageType.REAL, SQL.Nullable.FALSE);
            addColumn("longitude", SQL.StorageType.REAL, SQL.Nullable.FALSE);
            addColumn("name", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addForeignKeyConstraint(new String[]{"review_id"}, TableReviews.get());
        }

        public static SQLiteTableDefinition get() {
            if (sTable == null) sTable = new TableLocations();
            return sTable;
        }
    }

    public static class TableImages extends SQLiteTableDefinition {
        public static final String TABLE_NAME = "images";
        private static SQLiteTableDefinition sTable;

        private TableImages() {
            super(TABLE_NAME);
            addPrimaryKey("image_id", SQL.StorageType.TEXT);
            addColumn("review_id", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
            addColumn("bitmap", SQL.StorageType.BLOB, SQL.Nullable.FALSE);
            addColumn("caption", SQL.StorageType.TEXT, SQL.Nullable.TRUE);
            addColumn("is_cover", SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
            addForeignKeyConstraint(new String[]{"review_id"}, TableReviews.get());
        }

        public static SQLiteTableDefinition get() {
            if (sTable == null) sTable = new TableImages();
            return sTable;
        }
    }

    public static class TableTags extends SQLiteTableDefinition {
        public static final String TABLE_NAME = "tags";
        private static SQLiteTableDefinition sTable;

        private TableTags() {
            super(TABLE_NAME);
            addPrimaryKey("tag", SQL.StorageType.TEXT);
            addColumn("reviews", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        }

        public static SQLiteTableDefinition get() {
            if (sTable == null) sTable = new TableTags();
            return sTable;
        }
    }
}
