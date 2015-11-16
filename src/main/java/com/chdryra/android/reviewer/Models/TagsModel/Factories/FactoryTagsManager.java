package com.chdryra.android.reviewer.Models.TagsModel.Factories;

import com.chdryra.android.reviewer.Models.TagsModel.Implementation.TagsManagerImpl;
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryTagsManager {
    public TagsManager newTagsManager() {
        return new TagsManagerImpl();
    }
}
