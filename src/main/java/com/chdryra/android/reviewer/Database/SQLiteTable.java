/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 30 March, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 30/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class SQLiteTable implements BaseColumns {
    private String                  mTableName;
    private SQLiteColumn            mPrimaryKey;
    private ArrayList<SQLiteColumn> mColumns;

    public SQLiteTable(String tableName, SQLitePrimaryKeyColumn pk, SQLiteColumn[] otherColumns) {
        mTableName = tableName;
        mPrimaryKey = pk;
        mColumns = new ArrayList<>(Arrays.asList(otherColumns));
    }

    public String getName() {
        return mTableName;
    }

    public SQLiteColumn getPrimaryKey() {
        return mPrimaryKey;
    }

    public ArrayList<SQLiteColumn> getOtherColumns() {
        return mColumns;
    }

    public static class SQLiteColumn {
        private String          mColumnName;
        private SQL.StorageType mType;
        private boolean         mIsNullable;

        public SQLiteColumn(String columnName, SQL.StorageType type,
                SQL.Nullable nullable) {
            mColumnName = columnName;
            mType = type;
            mIsNullable = nullable == SQL.Nullable.TRUE;
        }

        public String getName() {
            return mColumnName;
        }

        public SQL.StorageType getType() {
            return mType;
        }

        public boolean isPrimaryKey() {
            return false;
        }

        public boolean isNullable() {
            return mIsNullable;
        }
    }

    public static class SQLitePrimaryKeyColumn extends SQLiteColumn {
        public SQLitePrimaryKeyColumn(String columnName, SQL.StorageType type) {
            super(columnName, type, SQL.Nullable.FALSE);
        }

        @Override
        public boolean isPrimaryKey() {
            return true;
        }
    }
}
