/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 April, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.Controller.DataValidator;
import com.chdryra.android.reviewer.Model.Author;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowAuthor implements ReviewerDbRow.TableRow {
    public static String USER_ID     = ReviewerDbContract.TableAuthors.COLUMN_NAME_USER_ID;
    public static String AUTHOR_NAME = ReviewerDbContract.TableAuthors.COLUMN_NAME_NAME;

    private String mUserId;
    private String mName;

    public RowAuthor() {
    }

    public RowAuthor(Author author) {
        mUserId = author.getUserId().toString();
        mName = author.getName();
    }

    public RowAuthor(Cursor cursor) {
        mUserId = cursor.getString(cursor.getColumnIndexOrThrow(USER_ID));
        mName = cursor.getString(cursor.getColumnIndexOrThrow(AUTHOR_NAME));
    }

    @Override
    public String getRowId() {
        return mUserId;
    }

    @Override
    public String getRowIdColumnName() {
        return USER_ID;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(USER_ID, mUserId);
        values.put(AUTHOR_NAME, mName);

        return values;
    }

    @Override
    public boolean hasData() {
        return DataValidator.validateString(getRowId());
    }
}
