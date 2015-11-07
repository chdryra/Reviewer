package com.chdryra.android.reviewer.Model.TagsModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterable collection of ReviewTags.
 */
public class ReviewTagCollection implements Iterable<ReviewTag> {
    private final ArrayList<ReviewTag> mTags;

    ReviewTagCollection() {
        mTags = new ArrayList<>();
    }

    ReviewTagCollection(ReviewTagCollection tags) {
        mTags = new ArrayList<>(tags.mTags);
    }

    public int size() {
        return mTags.size();
    }

    public ReviewTag getItem(int position) {
        return mTags.get(position);
    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> tagArray = new ArrayList<>();
        for (ReviewTag tag : mTags) {
            tagArray.add(tag.getTag());
        }
        return tagArray;
    }

    ReviewTag get(String tag) {
        ReviewTag rTag = null;
        for (ReviewTag reviewTag : mTags) {
            if (reviewTag.equals(tag)) {
                rTag = reviewTag;
                break;
            }
        }

        return rTag;
    }

    void add(ReviewTag tag) {
        if (!mTags.contains(tag)) {
            mTags.add(tag);
        }
    }

    void remove(ReviewTag tag) {
        mTags.remove(tag);
    }

    //Overridden
    @Override
    public Iterator<ReviewTag> iterator() {
        return new ReviewTagIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewTagCollection)) return false;

        ReviewTagCollection that = (ReviewTagCollection) o;

        return mTags.equals(that.mTags);

    }

    @Override
    public int hashCode() {
        return mTags.hashCode();
    }

    public class ReviewTagIterator implements Iterator<ReviewTag> {
        int position = 0;

        //Overridden
        @Override
        public boolean hasNext() {
            return position < size() && getItem(position) != null;
        }

        @Override
        public ReviewTag next() {
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
                ReviewTagCollection.this.remove(getItem(position));
            }
        }
    }


}
