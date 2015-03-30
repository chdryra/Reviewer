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
public final class ReviewerDbContract {
    public static final String NAME    = "Reviewer.db";
    public static final int    VERSION = 1;
    private static ReviewerDbContract     sContract;
    private        ArrayList<SQLiteTable> mTables;

    private ReviewerDbContract() {
        mTables = new ArrayList<>();
        mTables.add(new TableReviews());
        mTables.add(new TableComments());
        mTables.add(new TableFacts());
        mTables.add(new TableLocations());
        mTables.add(new TableImages());
        mTables.add(new TableTags());
    }

    public static ReviewerDbContract getContract() {
        if (sContract == null) sContract = new ReviewerDbContract();
        return sContract;
    }

    private static class TableReviews extends SQLiteTable {
        public static final String TABLE_NAME = "reviews";

        public static final SQLiteColumn COLUMN_REVIEW_ID =
                new SQLitePrimaryKeyColumn("review_id", SQL.StorageType.TEXT);

        public static final SQLiteColumn COLUMN_PARENT_ID =
                new SQLiteColumn("parent_id", SQL.StorageType.TEXT, SQL.Nullable.TRUE);
        public static final SQLiteColumn COLUMN_AUTHOR_ID =
                new SQLiteColumn("author_id", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        public static final SQLiteColumn COLUMN_DATE      =
                new SQLiteColumn("publish_date", SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
        public static final SQLiteColumn COLUMN_SUBJECT   =
                new SQLiteColumn("subject", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        public static final SQLiteColumn COLUMN_RATING    =
                new SQLiteColumn("rating", SQL.StorageType.REAL, SQL.Nullable.FALSE);

        private TableReviews() {
            super(TABLE_NAME, (SQLitePrimaryKeyColumn) COLUMN_REVIEW_ID,
                    new SQLiteColumn[]{COLUMN_PARENT_ID, COLUMN_AUTHOR_ID, COLUMN_DATE,
                            COLUMN_SUBJECT, COLUMN_RATING});
        }
    }

    public static class TableComments extends SQLiteTable {
        public static final String TABLE_NAME = "comments";

        public static final SQLiteColumn COLUMN_COMMENT_ID =
                new SQLitePrimaryKeyColumn("comment_id", SQL.StorageType.TEXT);

        public static final SQLiteColumn COLUMN_REVIEW_ID   =
                new SQLiteColumn("review_id", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        public static final SQLiteColumn COLUMN_COMMENT     =
                new SQLiteColumn("comment", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        public static final SQLiteColumn COLUMN_IS_HEADLINE =
                new SQLiteColumn("is_headline", SQL.StorageType.INTEGER, SQL.Nullable.FALSE);

        private TableComments() {
            super(TABLE_NAME, (SQLitePrimaryKeyColumn) COLUMN_COMMENT_ID,
                    new SQLiteColumn[]{COLUMN_REVIEW_ID, COLUMN_COMMENT, COLUMN_IS_HEADLINE});
        }
    }

    public static class TableFacts extends SQLiteTable {
        public static final String TABLE_NAME = "facts";

        public static final SQLiteTable.SQLiteColumn COLUMN_FACT_ID =
                new SQLiteTable.SQLitePrimaryKeyColumn("fact_id", SQL.StorageType.TEXT);

        public static final SQLiteTable.SQLiteColumn COLUMN_REVIEW_ID =
                new SQLiteTable.SQLiteColumn("review_id", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        public static final SQLiteTable.SQLiteColumn COLUMN_LABEL     =
                new SQLiteTable.SQLiteColumn("label", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        public static final SQLiteTable.SQLiteColumn COLUMN_VALUE     =
                new SQLiteTable.SQLiteColumn("value", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        public static final SQLiteTable.SQLiteColumn COLUMN_IS_URL    =
                new SQLiteTable.SQLiteColumn("is_url", SQL.StorageType.INTEGER,
                        SQL.Nullable.FALSE);

        private TableFacts() {
            super(TABLE_NAME, (SQLiteTable.SQLitePrimaryKeyColumn) COLUMN_FACT_ID,
                    new SQLiteTable.SQLiteColumn[]{COLUMN_REVIEW_ID, COLUMN_LABEL, COLUMN_VALUE,
                            COLUMN_IS_URL});
        }
    }

    public static class TableLocations extends SQLiteTable {
        public static final String TABLE_NAME = "locations";

        public static final SQLiteTable.SQLiteColumn COLUMN_LOCATION_ID =
                new SQLiteTable.SQLitePrimaryKeyColumn("location_id", SQL.StorageType.TEXT);

        public static final SQLiteTable.SQLiteColumn COLUMN_REVIEW_ID =
                new SQLiteTable.SQLiteColumn("review_id", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        public static final SQLiteTable.SQLiteColumn COLUMN_LATITUDE  =
                new SQLiteTable.SQLiteColumn("latitude", SQL.StorageType.REAL, SQL.Nullable.FALSE);
        public static final SQLiteTable.SQLiteColumn COLUMN_LONGITUDE =
                new SQLiteTable.SQLiteColumn("longitude", SQL.StorageType.REAL,
                        SQL.Nullable.FALSE);
        public static final SQLiteTable.SQLiteColumn COLUMN_NAME      =
                new SQLiteTable.SQLiteColumn("name", SQL.StorageType.TEXT, SQL.Nullable.FALSE);

        private TableLocations() {
            super(TABLE_NAME, (SQLiteTable.SQLitePrimaryKeyColumn) COLUMN_LOCATION_ID,
                    new SQLiteTable.SQLiteColumn[]{COLUMN_REVIEW_ID, COLUMN_LATITUDE,
                            COLUMN_LONGITUDE,
                            COLUMN_NAME});
        }
    }

    public static class TableImages extends SQLiteTable {
        public static final String TABLE_NAME = "images";

        public static final SQLiteTable.SQLiteColumn COLUMN_IMAGE_ID =
                new SQLiteTable.SQLitePrimaryKeyColumn("image_id", SQL.StorageType.TEXT);

        public static final SQLiteTable.SQLiteColumn COLUMN_REVIEW_ID =
                new SQLiteTable.SQLiteColumn("review_id", SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        public static final SQLiteTable.SQLiteColumn COLUMN_BITMAP    =
                new SQLiteTable.SQLiteColumn("bitmap", SQL.StorageType.BLOB, SQL.Nullable.FALSE);
        public static final SQLiteTable.SQLiteColumn COLUMN_CAPTION   =
                new SQLiteTable.SQLiteColumn("caption", SQL.StorageType.TEXT, SQL.Nullable.TRUE);
        public static final SQLiteColumn             COLUMN_DATE      =
                new SQLiteColumn("date_time", SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
        public static final SQLiteTable.SQLiteColumn COLUMN_IS_COVER  =
                new SQLiteTable.SQLiteColumn("is_cover", SQL.StorageType.INTEGER,
                        SQL.Nullable.FALSE);

        private TableImages() {
            super(TABLE_NAME, (SQLiteTable.SQLitePrimaryKeyColumn) COLUMN_IMAGE_ID,
                    new SQLiteTable.SQLiteColumn[]{COLUMN_REVIEW_ID, COLUMN_BITMAP, COLUMN_CAPTION,
                            COLUMN_DATE, COLUMN_IS_COVER});
        }
    }

    public static class TableTags extends SQLiteTable {
        public static final String TABLE_NAME = "tags";

        public static final SQLiteTable.SQLiteColumn COLUMN_TAG =
                new SQLiteTable.SQLitePrimaryKeyColumn("tag", SQL.StorageType.TEXT);

        public static final SQLiteTable.SQLiteColumn COLUMN_REVIEWS =
                new SQLiteTable.SQLiteColumn("reviews", SQL.StorageType.TEXT, SQL.Nullable.FALSE);

        private TableTags() {
            super(TABLE_NAME, (SQLiteTable.SQLitePrimaryKeyColumn) COLUMN_TAG,
                    new SQLiteTable.SQLiteColumn[]{COLUMN_REVIEWS});
        }
    }
}
