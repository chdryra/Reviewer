/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 April, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.database.Cursor;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Interfaces.Data.DataAuthor;
import com.chdryra.android.reviewer.Interfaces.Data.DataComment;
import com.chdryra.android.reviewer.Interfaces.Data.DataCriterion;
import com.chdryra.android.reviewer.Interfaces.Data.DataFact;
import com.chdryra.android.reviewer.Interfaces.Data.DataImage;
import com.chdryra.android.reviewer.Interfaces.Data.DataLocation;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.TagsModel.ItemTag;

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
            Constructor c = rowClass.getConstructor(Cursor.class, DataValidator.class);
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
        return new RowReview(review);
    }

    public DbTableRow newRow(DataCriterion criterion) {
        return new RowReview(criterion);
    }

    public DbTableRow newRow(DataAuthor author) {
        return new RowAuthor(author);
    }

    public DbTableRow newRow(ItemTag tag) {
        return new RowTag(tag);
    }

    public DbTableRow newRow(DataComment comment, int index) {
        return new RowComment(comment, index);
    }

    public DbTableRow newRow(DataFact fact, int index) {
        return new RowFact(fact, index);
    }

    public DbTableRow newRow(DataLocation location, int index) {
        return new RowLocation(location, index);
    }

    public DbTableRow newRow(DataImage image, int index) {
        return new RowImage(image, index);
    }
}
