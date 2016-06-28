/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.TagsGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorDataGetterImpl;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorLeavesCollector;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorNumLeaves;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryVisitorReviewNode {
    public VisitorDataGetter<DataTag> newTagsCollector(TagsManager tagsManager) {
        return new VisitorDataGetterImpl<>(new TagsGetter(tagsManager));
    }

    public VisitorDataGetter<ReviewNode> newLeavesCollector(ReviewId rootId) {
        return new VisitorLeavesCollector(rootId);
    }

    public VisitorNumLeaves newNumLeavesVisitor() {
        return new VisitorNumLeaves();
    }
}
