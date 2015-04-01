/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 31 March, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DbCreator {
    private static final String CHECKS_OFF = "SET foreign_key_checks = 0;";
    private static final String CHECKS_ON  = "SET foreign_key_checks = 1;";
    private static final String TAG        = "DbCreator";

    private DbContract mContract;

    public DbCreator(DbContract contract) {
        mContract = contract;
    }

    public void createDatabase(SQLiteDatabase db) {
        ArrayList<SQLiteTableDefinition> tableDefs = mContract.getTableDefinitions();
        for (SQLiteTableDefinition tableDef : tableDefs) {
            try {
                String command = getCreateTableSql(tableDef);
                Log.i(TAG, "Executing SQL:\n" + command);
                db.execSQL(command);
            } catch (SQLException e) {
                throw new RuntimeException("Problem creating table " + tableDef.getName(), e);
            }
        }
    }

    public void upgradeDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(dropAllTablesSql());
        } catch (SQLException e) {
            throw new RuntimeException("Problem dropping tables", e);
        }
    }

    private String getCreateTableSql(SQLiteTableDefinition table) {
        String colDef = getColumnDefinitions(table);
        String pkDef = getPrimaryKeyDefinition(table);
        String fkDef = getFkConstraintsDefinition(table);

        String definition = SQL.CREATE_TABLE + SQL.SPACE + table.getName() + SQL.OPEN_BRACKET;
        definition += SQL.NEW_LINE + colDef;
        definition += pkDef.length() > 0 ? SQL.COMMA + SQL.NEW_LINE + pkDef : "";
        definition += fkDef.length() > 0 ? SQL.COMMA + SQL.NEW_LINE + fkDef : "";
        definition += SQL.NEW_LINE + SQL.CLOSE_BRACKET;

        return definition;
    }

    private String getColumnDefinitions(SQLiteTableDefinition table) {
        ArrayList<SQLiteTableDefinition.SQLiteColumn> columns = table.getAllColumns();
        String definition = "";
        if (columns.size() == 0) return definition;

        for (int i = 0; i < columns.size() - 1; ++i) {
            definition += getColumnDefinition(columns.get(i)) + SQL.COMMA + SQL.NEW_LINE;
        }
        definition += getColumnDefinition(columns.get(columns.size() - 1));

        return definition;
    }

    private String getColumnDefinition(SQLiteTableDefinition.SQLiteColumn column) {
        String definition = column.getName() + SQL.SPACE + column.getType().name();
        definition += column.isNullable() ? "" : SQL.SPACE + SQL.NOT_NULL;

        return definition;
    }

    private String getPrimaryKeyDefinition(SQLiteTableDefinition table) {
        ArrayList<SQLiteTableDefinition.SQLiteColumn> pks = table.getPrimaryKeys();
        String definition = "";
        if (pks.size() == 0) return definition;

        definition = SQL.PRIMARY_KEY + SQL.OPEN_BRACKET;
        definition += getCommaSeparatedNames(pks) + SQL.CLOSE_BRACKET;

        return definition;
    }

    private String getFkConstraintsDefinition(SQLiteTableDefinition table) {
        ArrayList<SQLiteTableDefinition.ForeignKeyConstraint> constraints = table
                .getForeignKeyConstraints();
        String definition = "";
        if (constraints.size() == 0) return definition;

        for (int i = 0; i < constraints.size() - 1; ++i) {
            definition += getFkConstraintDefinition(constraints.get(i)) + SQL.COMMA + SQL.NEW_LINE;
        }
        definition += getFkConstraintDefinition(constraints.get(constraints.size() - 1));

        return definition;
    }

    private String getFkConstraintDefinition(SQLiteTableDefinition.ForeignKeyConstraint
            constraint) {
        SQLiteTableDefinition fkTable = constraint.getForeignTable();

        String definition = SQL.FOREIGN_KEY + SQL.OPEN_BRACKET;
        definition += getCommaSeparatedNames(constraint.getFkColumns()) + SQL.CLOSE_BRACKET;
        definition += SQL.SPACE + SQL.REFERENCES + SQL.SPACE;
        definition += fkTable.getName() + SQL.OPEN_BRACKET;
        definition += getCommaSeparatedNames(fkTable.getPrimaryKeys()) + SQL.CLOSE_BRACKET;

        return definition;
    }

    private String dropAllTablesSql() {
        String tables = StringUtils.join(mContract.getTableNames().toArray(), ",");
        String dropString = SQL.DROP_TABLE_IF_EXISTS + SQL.SPACE + tables + SQL.SEMICOLON;

        String definition = CHECKS_OFF + SQL.NEW_LINE;
        definition += dropString + SQL.NEW_LINE;
        definition += CHECKS_ON;

        return definition;
    }

    private String getCommaSeparatedNames(ArrayList<SQLiteTableDefinition.SQLiteColumn> cols) {
        String cs = "";
        for (SQLiteTableDefinition.SQLiteColumn column : cols) {
            cs += column.getName() + SQL.COMMA;
        }

        return cs.substring(0, cs.length() - 1);
    }
}
