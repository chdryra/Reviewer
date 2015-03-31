/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 30 March, 2015
 */

package com.chdryra.android.reviewer.Database;

/**
 * Created by: Rizwan Choudrey
 * On: 30/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SQL {
    public static final String CREATE_TABLE         = "CREATE TABLE";
    public static final String PRIMARY_KEY          = "PRIMARY KEY";
    public static final String FOREIGN_KEY          = "FOREIGN KEY";
    public static final String NOT_NULL             = "NOT NULL";
    public static final String REFERENCES           = "REFERENCES";
    public static final String SELECT               = "SELECT";
    public static final String FROM                 = "FROM";
    public static final String WHERE                = "WHERE";
    public static final String DISTINCT             = "DISTINCT";
    public static final String ALL                  = "*";
    public static final String LIMIT                = "LIMIT";
    public static final String COMMA                = ",";
    public static final String NEW_LINE             = "\n";
    public static final String SPACE                = " ";
    public static final String OPEN_BRACKET         = "(";
    public static final String CLOSE_BRACKET        = ")";
    public static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS";

    public enum StorageType {TEXT, REAL, INTEGER, BLOB}

    public enum Nullable {TRUE, FALSE}

    public enum PrimaryKey {TRUE, FALSE}

    private SQL() {
    }

}
