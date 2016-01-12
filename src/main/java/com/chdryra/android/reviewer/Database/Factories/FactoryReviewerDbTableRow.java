/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 April, 2015
 */

package com.chdryra.android.reviewer.Database.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.Database.Implementation.RowAuthorImpl;
import com.chdryra.android.reviewer.Database.Implementation.RowCommentImpl;
import com.chdryra.android.reviewer.Database.Implementation.RowFactImpl;
import com.chdryra.android.reviewer.Database.Implementation.RowImageImpl;
import com.chdryra.android.reviewer.Database.Implementation.RowLocationImpl;
import com.chdryra.android.reviewer.Database.Implementation.RowReviewImpl;
import com.chdryra.android.reviewer.Database.Implementation.RowTagImpl;
import com.chdryra.android.reviewer.Database.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.Database.Interfaces.RowComment;
import com.chdryra.android.reviewer.Database.Interfaces.RowFact;
import com.chdryra.android.reviewer.Database.Interfaces.RowImage;
import com.chdryra.android.reviewer.Database.Interfaces.RowLocation;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.Database.Interfaces.RowTag;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by: Rizwan Choudrey
 * On: 02/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerDbTableRow {

    public <T extends DbTableRow> T emptyRow(Class<T> rowClass) {
        try {
            return rowClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Couldn't instantiate class " + rowClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't access class " + rowClass.getName(), e);
        }
    }

    public <T extends DbTableRow> T newRow(RowValues values, Class<T> rowClass) {
        try {
            Constructor c = rowClass.getConstructor(RowValues.class);
            return rowClass.cast(c.newInstance(values));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Couldn't find RowValues constructor for " + rowClass
                    .getName(), e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Couldn't instantiate class " + rowClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't access class " + rowClass.getName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Couldn't invoke class " + rowClass.getName(), e);
        }
    }

    public RowReview newRow(Review review) {
        return new RowReviewImpl(review);
    }

    public RowReview newRow(DataCriterionReview criterion) {
        return new RowReviewImpl(criterion);
    }

    public RowAuthor newRow(DataAuthor author) {
        return new RowAuthorImpl(author);
    }

    public RowTag newRow(ItemTag tag) {
        return new RowTagImpl(tag);
    }

    public RowComment newRow(DataComment comment, int index) {
        return new RowCommentImpl(comment, index);
    }

    public RowFact newRow(DataFact fact, int index) {
        return new RowFactImpl(fact, index);
    }

    public RowLocation newRow(DataLocation location, int index) {
        return new RowLocationImpl(location, index);
    }

    public RowImage newRow(DataImage image, int index) {
        return new RowImageImpl(image, index);
    }
}
