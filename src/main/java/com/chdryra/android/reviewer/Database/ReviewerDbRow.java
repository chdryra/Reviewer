/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 April, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.Model.Author;
import com.chdryra.android.reviewer.Model.MdCommentList;
import com.chdryra.android.reviewer.Model.MdFactList;
import com.chdryra.android.reviewer.Model.MdImageList;
import com.chdryra.android.reviewer.Model.MdLocationList;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by: Rizwan Choudrey
 * On: 02/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbRow {
    public static final String SEPARATOR = ":";

    public interface TableRow {
        public String getRowId();

        public String getRowIdColumnName();

        public ContentValues getContentValues();

        public boolean hasData();
    }

    public static <T extends TableRow> T emptyRow(Class<T> rowClass) {
        try {
            return rowClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Couldn't instantiate class " + rowClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't access class " + rowClass.getName(), e);
        }
    }

    public static <T extends TableRow> T newRow(Cursor cursor, Class<T> rowClass) {
        try {
            Constructor c = rowClass.getConstructor(Cursor.class);
            return rowClass.cast(c.newInstance(cursor));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Couldn't find Cursor constructor for " + rowClass
                    .getName(), e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Couldn't instantiate class " + rowClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't access class " + rowClass.getName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Couldn't invoke class " + rowClass.getName(), e);
        }
    }

    public static TableRow newRow(ReviewNode node) {
        return new RowReviewNode(node);
    }

    public static TableRow newRow(Review review) {
        return new RowReview(review);
    }

    public static TableRow newRow(Author author) {
        return new RowAuthor(author);
    }

    public static TableRow newRow(TagsManager.ReviewTag tag) {
        return new RowTag(tag);
    }

    public static TableRow newRow(MdCommentList.MdComment comment, int index) {
        return new RowComment(comment, index);
    }

    public static TableRow newRow(MdFactList.MdFact fact, int index) {
        return new RowFact(fact, index);
    }

    public static TableRow newRow(MdLocationList.MdLocation location, int index) {
        return new RowLocation(location, index);
    }

    public static TableRow newRow(MdImageList.MdImage image, int index) {
        return new RowImage(image, index);
    }
}
