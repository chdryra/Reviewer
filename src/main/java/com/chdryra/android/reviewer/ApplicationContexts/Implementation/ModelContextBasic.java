/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryBinders;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ModelContextBasic implements ModelContext {
    private FactoryReviews mFactoryReviews;
    private TagsManager mTagsManager;
    private FactoryVisitorReviewNode mVisitorsFactory;
    private FactoryNodeTraverser mTreeTraversersFactory;
    private FactoryBinders mBindersFactory;

    public void setTagsManager(TagsManager tagsManager) {
        mTagsManager = tagsManager;
    }

    public void setVisitorsFactory(FactoryVisitorReviewNode visitorsFactory) {
        mVisitorsFactory = visitorsFactory;
    }

    public void setTreeTraversersFactory(FactoryNodeTraverser treeTraversersFactory) {
        mTreeTraversersFactory = treeTraversersFactory;
    }

    public void setReviewsFactory(FactoryReviews factoryReviews) {
        mFactoryReviews = factoryReviews;
    }

    public void setBindersFactory(FactoryBinders bindersFactory) {
        mBindersFactory = bindersFactory;
    }

    @Override
    public FactoryReviews getReviewsFactory() {
        return mFactoryReviews;
    }

    @Override
    public FactoryVisitorReviewNode getVisitorsFactory() {
        return mVisitorsFactory;
    }

    @Override
    public FactoryNodeTraverser getNodeTraversersFactory() {
        return mTreeTraversersFactory;
    }

    @Override
    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    @Override
    public FactoryBinders getBindersFactory() {
        return mBindersFactory;
    }
}
