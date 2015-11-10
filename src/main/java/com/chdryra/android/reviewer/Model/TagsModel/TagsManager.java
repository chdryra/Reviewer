/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 October, 2014
 */

package com.chdryra.android.reviewer.Model.TagsModel;

import java.util.ArrayList;

//import org.apache.commons.lang3.text.WordUtils;

/**
 * The singleton that manages the tagging and untagging of Reviews.
 */
public class TagsManager {
    private final ItemTagCollection mTags;

    //Constructors
    public TagsManager() {
        mTags = new ItemTagCollection();
    }

    //public methods
    public ItemTagCollection getTags() {
        return new ItemTagCollection(mTags);
    }

    public ItemTagCollection getTags(String id) {
        ItemTagCollection tags = new ItemTagCollection();
        for (ItemTag tag : mTags) {
            if (tag.tagsItem(id)) tags.add(tag);
        }

        return tags;
    }

    public void tagItem(String id, ArrayList<String> tags) {
        for (String tag : tags) {
            tagItem(id, tag);
        }
    }

    public void tagItem(String id, String tag) {
        ItemTag itemTag = mTags.get(tag);
        if (itemTag == null) {
            mTags.add(new ItemTag(tag, id));
        } else {
            itemTag.addItem(id);
        }
    }

    public boolean untagItem(String id, ItemTag tag) {
        if (tag.tagsItem(id)) {
            tag.removeItem(id);
            if (tag.getItemIds().size() == 0) {
                mTags.remove(tag);
                return true;
            }
        }

        return false;
    }
}
