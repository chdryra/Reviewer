/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 October, 2014
 */

package com.chdryra.android.reviewer.Models.TagsModel;

//import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;

/**
 * The singleton that manages the tagging and untagging of Reviews.
 */
public interface TagsManager {
    ItemTagCollection getTags();

    ItemTagCollection getTags(String id);

    ArrayList<String> getTagsArray(String id);

    void tagItem(String id, String tag);

    void tagItem(String id, ArrayList<String> tags);

    boolean untagItem(String id, ItemTag tag);
}
