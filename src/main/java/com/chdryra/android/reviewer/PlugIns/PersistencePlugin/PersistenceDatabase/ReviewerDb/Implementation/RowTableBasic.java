package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Interfaces.RowEntry;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class RowTableBasic implements Iterable<RowEntry<?>>, DbTableRow{
    protected abstract int size();

    protected abstract RowEntry<?> getEntry(int position);

    @Override
    public Iterator<RowEntry<?>> iterator() {
        return new RowIterator();
    }

    protected NoSuchElementException noElement() {
        throw new NoSuchElementException("No more elements left");
    }

    private class RowIterator implements Iterator<RowEntry<?>> {
        private int mPosition = 0;

        @Override
        public boolean hasNext() {
            return mPosition < size();
        }

        @Override
        public RowEntry<?> next() {
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
