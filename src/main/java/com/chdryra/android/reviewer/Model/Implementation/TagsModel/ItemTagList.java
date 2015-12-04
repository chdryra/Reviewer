package com.chdryra.android.reviewer.Model.Implementation.TagsModel;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ItemTagList<T extends ItemTag> extends AbstractCollection<ItemTag> implements ItemTagCollection {
    private final ArrayList<T> mTags;

    ItemTagList() {
        mTags = new ArrayList<>();
    }

    ItemTagList(ItemTagList<T> tags) {
        mTags = new ArrayList<>(tags.mTags);
    }

    @Override
    public int size() {
        return mTags.size();
    }

    @Override
    public T getItemTag(int position) {
        return mTags.get(position);
    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> tagArray = new ArrayList<>();
        for (ItemTag tag : mTags) {
            tagArray.add(tag.getTag());
        }
        return tagArray;
    }

    T get(String tag) {
        T rTag = null;
        for (T itemTag : mTags) {
            if (itemTag.getTag().equals(tag)) {
                rTag = itemTag;
                break;
            }
        }

        return rTag;
    }

    void add(T tag) {
        if (!mTags.contains(tag)) {
            mTags.add(tag);
        }
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends ItemTag> collection) {
        boolean added = false;
        for(ItemTag tag : collection) {
            if(!contains(tag)) {
                add(tag);
                added = true;
            }
        }

        return added;
    }

    void remove(T tag) {
        mTags.remove(tag);
    }

    //Overridden
    @Override
    public Iterator<ItemTag> iterator() {
        return new ReviewTagIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemTagList)) return false;

        ItemTagList that = (ItemTagList) o;

        return mTags.equals(that.mTags);

    }

    @Override
    public int hashCode() {
        return mTags.hashCode();
    }

    public class ReviewTagIterator implements Iterator<ItemTag> {
        int position = 0;

        //Overridden
        @Override
        public boolean hasNext() {
            return position < size() && getItemTag(position) != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                return getItemTag(position++);
            } else {
                throw new NoSuchElementException("No more elements left");
            }
        }

        @Override
        public void remove() {
            if (position <= 0) {
                throw new IllegalStateException("Have to do at least one next() before you " +
                        "can delete");
            } else {
                ItemTagList.this.remove(getItemTag(position));
            }
        }
    }
}
