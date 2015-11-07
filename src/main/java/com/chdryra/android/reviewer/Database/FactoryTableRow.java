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
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCriterionList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.UserData.Author;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by: Rizwan Choudrey
 * On: 02/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryTableRow {
    private DataValidator mValidator;

    public FactoryTableRow(DataValidator validator) {
        mValidator = validator;
    }

    public <T extends TableRow> T emptyRow(Class<T> rowClass) {
        try {
            return rowClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Couldn't instantiate class " + rowClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't access class " + rowClass.getName(), e);
        }
    }

    public <T extends TableRow> T newRow(Cursor cursor, Class<T> rowClass) {
        try {
            Constructor c = rowClass.getConstructor(Cursor.class, DataValidator.class);
            return rowClass.cast(c.newInstance(cursor, mValidator));
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

    public TableRow newRow(Review review) {
        return new RowReview(review, mValidator);
    }

    public TableRow newRow(MdCriterionList.MdCriterion criterion) {
        return new RowReview(criterion, mValidator);
    }

    public TableRow newRow(Author author) {
        return new RowAuthor(author, mValidator);
    }

    public TableRow newRow(TagsManager.ReviewTag tag) {
        return new RowTag(tag, mValidator);
    }

    public TableRow newRow(MdCommentList.MdComment comment, int index) {
        return new RowComment(comment, index, mValidator);
    }

    public TableRow newRow(MdFactList.MdFact fact, int index) {
        return new RowFact(fact, index, mValidator);
    }

    public TableRow newRow(MdLocationList.MdLocation location, int index) {
        return new RowLocation(location, index, mValidator);
    }

    public TableRow newRow(MdImageList.MdImage image, int index) {
        return new RowImage(image, index, mValidator);
    }
}
