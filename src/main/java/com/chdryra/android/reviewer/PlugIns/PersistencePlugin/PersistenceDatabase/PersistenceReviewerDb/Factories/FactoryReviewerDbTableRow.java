/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Factories;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Implementation.RowAuthorImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Implementation.RowCommentImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Implementation.RowFactImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Implementation.RowImageImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Implementation.RowLocationImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Implementation.RowReviewImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Implementation.RowTagImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.FactoryDbTableRow;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 02/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerDbTableRow implements FactoryDbTableRow {
    private Map<Class<?>, Class<?>> mConstructorMap;

    public FactoryReviewerDbTableRow() {
        mConstructorMap = new HashMap<>();
        mConstructorMap.put(RowReview.class, RowReviewImpl.class);
        mConstructorMap.put(RowAuthor.class, RowAuthorImpl.class);
        mConstructorMap.put(RowTag.class, RowTagImpl.class);
        mConstructorMap.put(RowComment.class, RowCommentImpl.class);
        mConstructorMap.put(RowFact.class, RowFactImpl.class);
        mConstructorMap.put(RowLocation.class, RowLocationImpl.class);
        mConstructorMap.put(RowImage.class, RowImageImpl.class);
    }

    @Override
    public <T extends DbTableRow> T emptyRow(Class<T> rowClass) {
        try {
            Class<?> rowImplClass = mConstructorMap.get(rowClass);
            return rowClass.cast(rowImplClass.newInstance());
        } catch (InstantiationException e) {
            throw getRuntimeException(rowClass, e);
        } catch (IllegalAccessException e) {
            throw getRuntimeException(rowClass, e);
        }
    }

    @Override
    public <T extends DbTableRow, D> T newRow(Class<T> rowClass, D toInsert) {
        try {
            Class<?> rowImplClass = mConstructorMap.get(rowClass);
            Constructor<?> c = ConstructorUtils.getMatchingAccessibleConstructor(rowImplClass,
                    toInsert.getClass());
            return rowClass.cast(c.newInstance(toInsert));
        } catch (InstantiationException e) {
            throw getRuntimeException(rowClass, e);
        } catch (IllegalAccessException e) {
            throw getRuntimeException(rowClass, e);
        } catch (InvocationTargetException e) {
            throw getRuntimeException(rowClass, e);
        }
    }

    @Override
    public <T extends DbTableRow, D> T newRow(Class<T> rowClass, D toInsert, int index) {
        try {
            Class<?> rowImplClass = mConstructorMap.get(rowClass);
            Constructor<?> c = ConstructorUtils.getMatchingAccessibleConstructor(rowImplClass,
                    toInsert.getClass(), Integer.class);
            return rowClass.cast(c.newInstance(toInsert, index));
        } catch (InstantiationException e) {
            throw getRuntimeException(rowClass, e);
        } catch (IllegalAccessException e) {
            throw getRuntimeException(rowClass, e);
        } catch (InvocationTargetException e) {
            throw getRuntimeException(rowClass, e);
        }
    }

    @NonNull
    private <T extends DbTableRow> RuntimeException getRuntimeException(Class<T> rowClass,
                                                                        IllegalAccessException e) {
        return new RuntimeException("Couldn't access class " + rowClass.getName(), e);
    }

    @NonNull
    private <T extends DbTableRow> RuntimeException getRuntimeException(Class<T> rowClass,
                                                                        InstantiationException e) {
        return new RuntimeException("Couldn't instantiate class " + rowClass.getName(), e);
    }

    @NonNull
    private <T extends DbTableRow> RuntimeException getRuntimeException(Class<T> rowClass,
                                                                        InvocationTargetException
                                                                                e) {
        return new RuntimeException("Couldn't invoke class " + rowClass.getName(), e);
    }
}
