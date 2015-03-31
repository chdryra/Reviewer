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

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DbCreator {
    private static final String TAG = "DbCreator";
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
        ArrayList<SQLiteTableDefinition> tableDefs = mContract.getTableDefinitions();
        for (SQLiteTableDefinition tableDef : tableDefs) {
            try {
                db.execSQL(getDropTableSql(tableDef, oldVersion, newVersion));
            } catch (SQLException e) {
                throw new RuntimeException("Problem dropping table " + tableDef.getName(), e);
            }
        }
    }

    private String getCreateTableSql(SQLiteTableDefinition table) {
        String colDef = getColumnDefinitions(table.getAllColumns());
        String pkDef = getPrimaryKeyDefinition(table.getPrimaryKeys());
        String fkDef = getFkConstraintsDefinition(table.getForeignKeyConstraints());

        String definition = SQL.CREATE_TABLE + SQL.SPACE + table.getName() + SQL.OPEN_BRACKET;
        definition += SQL.NEW_LINE + getColumnDefinitions(table.getAllColumns());
        definition += pkDef.length() > 0 ? SQL.COMMA + SQL.NEW_LINE + pkDef : "";
        definition += fkDef.length() > 0 ? SQL.COMMA + SQL.NEW_LINE + fkDef : "";
        definition += SQL.NEW_LINE + SQL.CLOSE_BRACKET;

        return definition;
    }

    private String getColumnDefinitions(ArrayList<SQLiteTableDefinition.SQLiteColumn> columns) {
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

    private String getPrimaryKeyDefinition(ArrayList<SQLiteTableDefinition.SQLiteColumn> pks) {
        String definition = "";
        if (pks.size() == 0) return definition;

        definition += SQL.PRIMARY_KEY + SQL.OPEN_BRACKET;
        for (SQLiteTableDefinition.SQLiteColumn pk : pks) {
            definition += pk.getName() + SQL.COMMA;
        }
        definition = definition.substring(0, definition.length() - 1);
        definition += SQL.CLOSE_BRACKET;

        return definition;
    }

    private String getFkConstraintsDefinition(ArrayList<SQLiteTableDefinition
            .ForeignKeyConstraint> constraints) {
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
        String definition = SQL.FOREIGN_KEY + SQL.OPEN_BRACKET;
        for (SQLiteTableDefinition.SQLiteColumn column : constraint.getFkColumns()) {
            definition += column.getName() + SQL.COMMA;
        }
        definition = definition.substring(0, definition.length() - 1) + SQL.CLOSE_BRACKET;
        definition += SQL.SPACE + SQL.REFERENCES + SQL.SPACE;


        SQLiteTableDefinition pkTable = constraint.getPkTable();
        definition += pkTable.getName() + SQL.OPEN_BRACKET;
        for (SQLiteTableDefinition.SQLiteColumn column : pkTable.getPrimaryKeys()) {
            definition += column.getName() + SQL.COMMA;
        }

        return definition.substring(0, definition.length() - 1) + SQL.CLOSE_BRACKET;
    }

    private String getDropTableSql(SQLiteTableDefinition table, int oldVersion, int newVersion) {
        return SQL.DROP_TABLE_IF_EXISTS + SQL.SPACE + table.getName();
    }
}
