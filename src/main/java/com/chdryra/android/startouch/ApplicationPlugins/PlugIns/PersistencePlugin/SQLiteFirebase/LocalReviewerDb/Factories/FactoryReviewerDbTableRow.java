/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Factories;


import android.support.annotation.NonNull;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.RowAuthorNameImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.RowCommentImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.RowCriterionImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.RowFactImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.RowImageImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.RowLocationImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.RowReviewImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.RowTagImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowAuthorName;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowCriterion;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowTag;

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
    private final Map<Class<?>, Class<?>> mConstructorMap;

    public FactoryReviewerDbTableRow() {
        mConstructorMap = new HashMap<>();
        mConstructorMap.put(RowReview.class, RowReviewImpl.class);
        mConstructorMap.put(RowCriterion.class, RowCriterionImpl.class);
        mConstructorMap.put(RowAuthorName.class, RowAuthorNameImpl.class);
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
            if (c == null) throw getNoSuchMethodException(rowClass, toInsert.getClass());
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
            if (c == null) throw getNoSuchMethodException(rowClass, toInsert.getClass());
            return rowClass.cast(c.newInstance(toInsert, index));
        } catch (InstantiationException e) {
            throw getRuntimeException(rowClass, e);
        } catch (IllegalAccessException e) {
            throw getRuntimeException(rowClass, e);
        } catch (InvocationTargetException e) {
            throw getRuntimeException(rowClass, e);
        }
    }

    private <T extends DbTableRow, D> RuntimeException getNoSuchMethodException(Class<T> rowClass,
                                                                                Class<D>
                                                                                        insertClass) {
        Exception e = new NoSuchMethodException("No constructor found for " + rowClass.getName() +
                " using parameter type " + insertClass.getName());
        return new RuntimeException(e);
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
