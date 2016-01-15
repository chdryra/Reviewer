package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Interfaces.RowEntry;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class RowTableBasic {
    protected abstract int size();

    protected abstract RowEntry<?> getEntry(int position);

    public Iterator<RowEntry<?>> iterator() {
        return new RowIterator();
    }

    private class RowIterator implements Iterator<RowEntry<?>> {
        String NO_ELEMENT = "No more elements left";
        int mPosition = 0;

        private RowIterator() {

        }

        @Override
        public boolean hasNext() {
            return mPosition < size();
        }

        @Override
        public RowEntry<?> next() {
            if (hasNext()) {
                return getEntry(mPosition++);
            } else {
                throw new NoSuchElementException(NO_ELEMENT);
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
