package com.chdryra.android.reviewer.Model.TagsModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterable collection of ReviewTags.
 */
public class ItemTagCollection implements Iterable<ItemTag> {
    private final ArrayList<ItemTag> mTags;

    ItemTagCollection() {
        mTags = new ArrayList<>();
    }

    ItemTagCollection(ItemTagCollection tags) {
        mTags = new ArrayList<>(tags.mTags);
    }

    public int size() {
        return mTags.size();
    }

    public ItemTag getItem(int position) {
        return mTags.get(position);
    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> tagArray = new ArrayList<>();
        for (ItemTag tag : mTags) {
            tagArray.add(tag.getTag());
        }
        return tagArray;
    }

    ItemTag get(String tag) {
        ItemTag rTag = null;
        for (ItemTag itemTag : mTags) {
            if (itemTag.equals(tag)) {
                rTag = itemTag;
                break;
            }
        }

        return rTag;
    }

    void add(ItemTag tag) {
        if (!mTags.contains(tag)) {
            mTags.add(tag);
        }
    }

    void remove(ItemTag tag) {
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
        if (!(o instanceof ItemTagCollection)) return false;

        ItemTagCollection that = (ItemTagCollection) o;

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
            return position < size() && getItem(position) != null;
        }

        @Override
        public ItemTag next() {
            if (hasNext()) {
                return getItem(position++);
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
                ItemTagCollection.this.remove(getItem(position));
            }
        }
    }


}
