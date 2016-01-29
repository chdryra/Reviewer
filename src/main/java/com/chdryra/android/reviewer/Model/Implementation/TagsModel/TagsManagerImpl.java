/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Implementation.TagsModel;

import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TagsManagerImpl implements TagsManager {
    private final ItemTagList mTags;

    public TagsManagerImpl() {
        mTags = new ItemTagList();
    }

    @Override
    public void tagItem(String id, String tag) {
        ItemTagImpl itemTag = (ItemTagImpl) mTags.getItemTag(tag);
        if (itemTag == null) {
            mTags.add(new ItemTagImpl(tag, id));
        } else if(!itemTag.tagsItem(id)){
            itemTag.addItemId(id);
        }
    }

    @Override
    public boolean tagsItem(String id, String tag) {
        ItemTagImpl itemTag = (ItemTagImpl) mTags.getItemTag(tag);
        return itemTag != null && itemTag.tagsItem(id);
    }

    @Override
    public void tagItem(String id, ArrayList<String> tags) {
        for(String tag : tags) {
            tagItem(id, tag);
        }
    }

    @Override
    public boolean untagItem(String id, ItemTag tag) {
        ItemTagImpl tagImpl = (ItemTagImpl) tag;
        if (tagImpl.tagsItem(id)) {
            tagImpl.removeItemId(id);
            if (tag.getItemIds().size() == 0) {
                mTags.remove(tagImpl);
                return true;
            }
        }

        return false;
    }

    @Override
    public ItemTagCollection getTags() {
        return new ItemTagList(mTags);
    }

    @Override
    public ItemTagCollection getTags(String id) {
        ItemTagList tags = new ItemTagList();
        for (ItemTag tag : mTags) {
            if (tag.tagsItem(id)) tags.add(tag);
        }

        return tags;
    }
}
