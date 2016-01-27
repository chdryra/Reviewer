/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.RowEntry;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class RowTableBasic<DbRow extends DbTableRow>
        implements Iterable<RowEntry<DbRow, ?>>, DbTableRow<DbRow>{

    protected abstract int size();

    protected abstract RowEntry<DbRow, ?> getEntry(int position);

    @Override
    public Iterator<RowEntry<DbRow, ?>> iterator() {
        return new RowIterator();
    }

    protected NoSuchElementException noElement() {
        throw new NoSuchElementException("No more elements left");
    }

    private class RowIterator implements Iterator<RowEntry<DbRow, ?>> {
        private int mPosition = 0;

        @Override
        public boolean hasNext() {
            return mPosition < size();
        }

        @Override
        public RowEntry<DbRow, ?> next() {
            if (hasNext()) {
                return getEntry(mPosition++);
            } else {
                throw noElement();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
