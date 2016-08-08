/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.StaticItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.StaticListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeDataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeDataReferenceSize;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeInfoReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeInfoReferenceSize;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.VisitorFactory;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 02/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryMdReference {
    private FactoryNodeTraverser mTraverserFactory;
    private FactoryVisitorReviewNode mVisitorFactory;

    public FactoryNodeTraverser getTraverserFactory() {
        return mTraverserFactory;
    }

    public FactoryVisitorReviewNode getVisitorFactory() {
        return mVisitorFactory;
    }

    public <T extends HasReviewId> ReviewItemReference<T> newWrapper(ReviewId id, T datum) {
        return new StaticItemReference<>(id, datum);
    }

    public <T extends HasReviewId> ReviewListReference<T> newWrapper(ReviewId id, IdableList<T>
            data) {
        return new StaticListReference<>(id, data);
    }

    public <T extends HasReviewId> ReviewItemReference<DataSize> newSize(TreeInfoReference<T>
                                                                                 treeRef) {
        return new TreeInfoReferenceSize<>(treeRef);
    }

    public <T extends HasReviewId> ReviewItemReference<DataSize> newSize(TreeDataReference<T>
                                                                                 treeRef) {
        return new TreeDataReferenceSize<>(treeRef);
    }

    public ReviewListReference<ReviewReference> newReviewsReference(ReviewNode root) {
        return new TreeDataReference<>(root, this, mTraverserFactory, new VisitorFactory.ListVisitor<ReviewReference>() {
            @Override
            public VisitorDataGetter<ReviewListReference<ReviewReference>> newVisitor() {
                return mVisitorFactory.newReviewsCollector();
            }
        });
    }

    public ReviewListReference<DataSubject> newSubjectsReference(ReviewNode root) {
        return new TreeInfoReference<>(root, this, mTraverserFactory, new VisitorFactory.InfoVisitor<DataSubject>() {
            @Override
            public VisitorDataGetter<DataSubject> newVisitor() {
                return mVisitorFactory.newSubjectsCollector();
            }
        });
    }

    private <T extends HasReviewId> ReviewListReference<T> newReferenceList(ReviewNode root,
                                                                            VisitorFactory
                                                                                    .ListVisitor<T> visitorFactory) {
        return new TreeDataReference<>(root, this, mTraverserFactory, visitorFactory);
    }

    private <T extends HasReviewId> ReviewListReference<T> newInfoList(ReviewNode root,
                                                                       VisitorFactory
                                                                               .InfoVisitor<T>
                                                                               visitorFactory) {
        return new TreeInfoReference<>(root, this, mTraverserFactory, visitorFactory);
    }
}
