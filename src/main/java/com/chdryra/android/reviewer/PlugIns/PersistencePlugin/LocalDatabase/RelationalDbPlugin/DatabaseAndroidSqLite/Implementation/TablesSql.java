/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin
        .DatabaseAndroidSqLite.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb
        .Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb
        .Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb
        .Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb
        .Interfaces.ForeignKeyConstraint;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 31/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TablesSql {
    private static final String CHECKS_OFF = "SET foreign_key_checks = 0;";
    private static final String CHECKS_ON = "SET foreign_key_checks = 1;";

    private SqLiteTypeDefinitions mDefs;

    public TablesSql(SqLiteTypeDefinitions defs) {
        mDefs = defs;
    }

    public String createTableSql(DbTable<? extends DbTableRow> table) {
        String colDef = getColumnDefinitions(table);
        String pkDef = getPrimaryKeyDefinition(table);
        String fkDef = getFkConstraintsDefinition(table);

        String definition = SQL.CREATE_TABLE + table.getName() + SQL.OPEN_BRACKET;
        definition += SQL.NEW_LINE + colDef;
        definition += pkDef.length() > 0 ? SQL.COMMA + SQL.NEW_LINE + pkDef : "";
        definition += fkDef.length() > 0 ? SQL.COMMA + SQL.NEW_LINE + fkDef : "";
        definition += SQL.NEW_LINE + SQL.CLOSE_BRACKET;

        return definition;
    }

    public String dropAllTablesSql(ArrayList<String> tableNames) {
        String tables = StringUtils.join(tableNames.toArray(), ",");
        String dropString = SQL.DROP_TABLE_IF_EXISTS + tables + SQL.SEMICOLON;

        String definition = CHECKS_OFF + SQL.NEW_LINE;
        definition += dropString + SQL.NEW_LINE;
        definition += CHECKS_ON;

        return definition;
    }

    public Query getFromTableWhereQuery(DbTable<? extends DbTableRow> table,
                                        @Nullable String column, @Nullable String value) {
        if(column != null && table.getColumn(column) == null) {
            throw new IllegalArgumentException("Column " + column + " not found in table "
                    + table.getName());
        }

        if(column != null && value == null && !table.getColumn(column).getNullable().isNullable()) {
            throw new IllegalArgumentException("Column " + column + " not nullable");
        }

        boolean isNull = value == null;
        String val = isNull ? SQL.SPACE + SQL.IS_NULL : SQL.SPACE + SQL.BIND_STRING;
        String whereClause = column != null ? " " + SQL.WHERE + column + val : "";
        String query = SQL.SELECT + SQL.ALL + SQL.FROM + table.getName() + whereClause;
        String[] args = isNull ? null : new String[]{value};

        return new Query(query, args);
    }

    public Query bindColumnWithValue(String columnName, String val) {
        return new Query(columnName + SQL.SPACE + SQL.BIND_STRING, new String[]{val});
    }

    private String getColumnDefinitions(DbTable<? extends DbTableRow> table) {
        ArrayList<DbColumnDefinition> columns = table.getColumns();
        String definition = "";
        if (columns.size() == 0) return definition;

        for (int i = 0; i < columns.size() - 1; ++i) {
            definition += getColumnDefinition(columns.get(i)) + SQL.COMMA + SQL.NEW_LINE;
        }
        definition += getColumnDefinition(columns.get(columns.size() - 1));

        return definition;
    }

    private String getColumnDefinition(DbColumnDefinition column) {
        String definition = column.getName() + SQL.SPACE + getTypeName(column);
        definition += column.getNullable().isNullable() ? "" : SQL.SPACE + SQL.NOT_NULL;

        return definition;
    }

    private String getTypeName(DbColumnDefinition column) {
        return mDefs.getSqlTypeName(column.getType());
    }

    private String getPrimaryKeyDefinition(DbTable<? extends DbTableRow> table) {
        ArrayList<DbColumnDefinition> pks = table.getPrimaryKeys();
        String definition = "";
        if (pks.size() == 0) return definition;

        definition = SQL.PRIMARY_KEY + SQL.OPEN_BRACKET;
        definition += getCommaSeparatedNames(pks) + SQL.CLOSE_BRACKET;

        return definition;
    }

    private String getFkConstraintsDefinition(DbTable<? extends DbTableRow> table) {
        ArrayList<ForeignKeyConstraint<? extends DbTableRow>> constraints;
        constraints = table.getForeignKeyConstraints();
        String definition = "";
        if (constraints.size() == 0) return definition;

        for (int i = 0; i < constraints.size() - 1; ++i) {
            definition += getFkConstraintDefinition(constraints.get(i)) + SQL.COMMA + SQL.NEW_LINE;
        }
        definition += getFkConstraintDefinition(constraints.get(constraints.size() - 1));

        return definition;
    }

    private String getFkConstraintDefinition(ForeignKeyConstraint<? extends DbTableRow>
                                                     constraint) {
        DbTable<?> fkTable = constraint.getForeignTable();

        String definition = SQL.FOREIGN_KEY + SQL.OPEN_BRACKET;
        definition += getCommaSeparatedNames(constraint.getFkColumns()) + SQL.CLOSE_BRACKET;
        definition += SQL.SPACE + SQL.REFERENCES;
        definition += fkTable.getName() + SQL.OPEN_BRACKET;
        definition += getCommaSeparatedNames(fkTable.getPrimaryKeys()) + SQL.CLOSE_BRACKET;

        return definition;
    }

    private String getCommaSeparatedNames(ArrayList<DbColumnDefinition> cols) {
        String cs = "";
        for (DbColumnDefinition column : cols) {
            cs += column.getName() + SQL.COMMA;
        }

        return cs.substring(0, cs.length() - 1);
    }

    public class Query {
        private String mQuery;
        private String mArgs[];

        public Query(String query, @Nullable String[] args) {
            mQuery = query.trim();
            mArgs = args;
        }

        public String getQuery() {
            return mQuery;
        }

        public String[] getArgs() {
            return mArgs;
        }
    }

}
