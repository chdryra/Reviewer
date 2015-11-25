/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 April, 2015
 */

package com.chdryra.android.reviewer.Database.Factories;

import android.database.Cursor;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.Database.Implementation.RowAuthorImpl;
import com.chdryra.android.reviewer.Database.Implementation.RowCommentImpl;
import com.chdryra.android.reviewer.Database.Implementation.RowFactImpl;
import com.chdryra.android.reviewer.Database.Implementation.RowImageImpl;
import com.chdryra.android.reviewer.Database.Implementation.RowLocationImpl;
import com.chdryra.android.reviewer.Database.Implementation.RowReviewImpl;
import com.chdryra.android.reviewer.Database.Implementation.RowTagImpl;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ItemTag;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by: Rizwan Choudrey
 * On: 02/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDbTableRow {

    public <T extends DbTableRow> T emptyRow(Class<T> rowClass) {
        try {
            return rowClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Couldn't instantiate class " + rowClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't access class " + rowClass.getName(), e);
        }
    }

    public <T extends DbTableRow> T newRow(Cursor cursor, Class<T> rowClass) {
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

    public DbTableRow newRow(Review review) {
        return new RowReviewImpl(review);
    }

    public DbTableRow newRow(DataCriterion criterion) {
        return new RowReviewImpl(criterion);
    }

    public DbTableRow newRow(DataAuthor author) {
        return new RowAuthorImpl(author);
    }

    public DbTableRow newRow(ItemTag tag) {
        return new RowTagImpl(tag);
    }

    public DbTableRow newRow(DataComment comment, int index) {
        return new RowCommentImpl(comment, index);
    }

    public DbTableRow newRow(DataFact fact, int index) {
        return new RowFactImpl(fact, index);
    }

    public DbTableRow newRow(DataLocation location, int index) {
        return new RowLocationImpl(location, index);
    }

    public DbTableRow newRow(DataImage image, int index) {
        return new RowImageImpl(image, index);
    }
}
