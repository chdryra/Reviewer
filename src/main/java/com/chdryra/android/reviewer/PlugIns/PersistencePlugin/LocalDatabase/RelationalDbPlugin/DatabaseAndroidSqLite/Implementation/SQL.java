/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.DatabaseAndroidSqLite.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 30/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SQL {
    public static final String CREATE_TABLE = "CREATE TABLE ";
    public static final String PRIMARY_KEY = "PRIMARY KEY";
    public static final String FOREIGN_KEY = "FOREIGN KEY";
    public static final String NOT_NULL = "NOT NULL";
    public static final String REFERENCES = "REFERENCES ";
    public static final String SELECT = "SELECT ";
    public static final String FROM = "FROM ";
    public static final String WHERE = "WHERE ";
    public static final String DISTINCT = "DISTINCT ";
    public static final String ALL = "* ";
    public static final String LIMIT = "LIMIT ";
    public static final String COMMA = ",";
    public static final String SEMICOLON = ";";
    public static final String NEW_LINE = "\n";
    public static final String SPACE = " ";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSE_BRACKET = ")";
    public static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
    public static final String BIND_STRING = "= ? ";
    public static final String IS_NULL = "IS NULL ";

    private SQL() {
    }
}
