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

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DbContractExecutor {
    private static final String CHECKS_OFF = "SET foreign_key_checks = 0;";
    private static final String CHECKS_ON = "SET foreign_key_checks = 1;";
    private static final String TAG = "DbCreator";

    public void createDatabase(DbContract contract, SQLiteDatabase db) {
        ArrayList<DbTable> tableDefs = contract.getTableDefinitions();
        for (DbTable tableDef : tableDefs) {
            try {
                String command = getCreateTableSql(tableDef);
                Log.i(TAG, "Executing SQL:\n" + command);
                db.execSQL(command);
            } catch (SQLException e) {
                throw new RuntimeException("Problem creating table " + tableDef.getName(), e);
            }
        }
    }

    public void upgradeDatabase(DbContract contract, SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(dropAllTablesSql(contract.getTableNames()));
        } catch (SQLException e) {
            throw new RuntimeException("Problem dropping tables", e);
        }
    }

    private String getCreateTableSql(DbTable table) {
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

    private String getColumnDefinitions(DbTable table) {
        ArrayList<DbTable.DbColumnDef> columns = table.getAllColumns();
        String definition = "";
        if (columns.size() == 0) return definition;

        for (int i = 0; i < columns.size() - 1; ++i) {
            definition += getColumnDefinition(columns.get(i)) + SQL.COMMA + SQL.NEW_LINE;
        }
        definition += getColumnDefinition(columns.get(columns.size() - 1));

        return definition;
    }

    private String getColumnDefinition(DbTable.DbColumnDef column) {
        String definition = column.getName() + SQL.SPACE + column.getType().name();
        definition += column.isNullable() ? "" : SQL.SPACE + SQL.NOT_NULL;

        return definition;
    }

    private String getPrimaryKeyDefinition(DbTable table) {
        ArrayList<DbTable.DbColumnDef> pks = table.getPrimaryKeys();
        String definition = "";
        if (pks.size() == 0) return definition;

        definition = SQL.PRIMARY_KEY + SQL.OPEN_BRACKET;
        definition += getCommaSeparatedNames(pks) + SQL.CLOSE_BRACKET;

        return definition;
    }

    private String getFkConstraintsDefinition(DbTable table) {
        ArrayList<DbTable.ForeignKeyConstraint> constraints = table
                .getForeignKeyConstraints();
        String definition = "";
        if (constraints.size() == 0) return definition;

        for (int i = 0; i < constraints.size() - 1; ++i) {
            definition += getFkConstraintDefinition(constraints.get(i)) + SQL.COMMA + SQL.NEW_LINE;
        }
        definition += getFkConstraintDefinition(constraints.get(constraints.size() - 1));

        return definition;
    }

    private String getFkConstraintDefinition(DbTable.ForeignKeyConstraint
                                                     constraint) {
        DbTable fkTable = constraint.getForeignTable();

        String definition = SQL.FOREIGN_KEY + SQL.OPEN_BRACKET;
        definition += getCommaSeparatedNames(constraint.getFkColumns()) + SQL.CLOSE_BRACKET;
        definition += SQL.SPACE + SQL.REFERENCES;
        definition += fkTable.getName() + SQL.OPEN_BRACKET;
        definition += getCommaSeparatedNames(fkTable.getPrimaryKeys()) + SQL.CLOSE_BRACKET;

        return definition;
    }

    private String dropAllTablesSql(ArrayList<String> tableNames) {
        String tables = StringUtils.join(tableNames.toArray(), ",");
        String dropString = SQL.DROP_TABLE_IF_EXISTS + tables + SQL.SEMICOLON;

        String definition = CHECKS_OFF + SQL.NEW_LINE;
        definition += dropString + SQL.NEW_LINE;
        definition += CHECKS_ON;

        return definition;
    }

    private String getCommaSeparatedNames(ArrayList<DbTable.DbColumnDef> cols) {
        String cs = "";
        for (DbTable.DbColumnDef column : cols) {
            cs += column.getName() + SQL.COMMA;
        }

        return cs.substring(0, cs.length() - 1);
    }
}
