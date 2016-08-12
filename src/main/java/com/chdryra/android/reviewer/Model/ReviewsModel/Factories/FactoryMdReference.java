/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.StaticItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.StaticListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeItemReferences;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeItemReferencesSize;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeListReferences;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeListReferencesSize;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.VisitorFactory;
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

    public FactoryMdReference(FactoryNodeTraverser traverserFactory, FactoryVisitorReviewNode 
            visitorFactory) {
        mTraverserFactory = traverserFactory;
        mVisitorFactory = visitorFactory;
    }

    public <T extends HasReviewId> ReviewItemReference<T> newWrapper(ReviewId id, T datum) {
        return new StaticItemReference<>(id, datum);
    }

    public <T extends HasReviewId> ReviewListReference<T> newWrapper(ReviewId id,
                                                                     IdableList<T> data) {
        return new StaticListReference<>(id, data);
    }

    public <T extends HasReviewId> ReviewListReference<T> newSuperClassWrapper(ReviewId id,
                                                                               IdableList<? extends T> data) {
        IdableList<T> list = new IdableDataList<>(data.getReviewId());
        list.addAll(data);
        return new StaticListReference<>(id, list);
    }

    public <T extends HasReviewId> ReviewItemReference<DataSize> newSize(TreeItemReferences<T>
                                                                                 treeRef) {
        return new TreeItemReferencesSize<>(treeRef);
    }

    public <T extends HasReviewId> ReviewItemReference<DataSize> newSize(TreeListReferences<T>
                                                                                 treeRef) {
        return new TreeListReferencesSize<>(treeRef);
    }

    public ReviewListReference<ReviewReference> newReviewsList(ReviewNode root) {
        return newItemList(root, new VisitorFactory.ItemVisitor<ReviewReference>() {
            @Override
            public VisitorDataGetter<ReviewReference> newVisitor() {
                return mVisitorFactory.newReviewsCollector();
            }
        });
    }

    public ReviewListReference<DataSubject> newSubjectsList(ReviewNode root) {
        return newItemList(root, new VisitorFactory.ItemVisitor<DataSubject>() {
            @Override
            public VisitorDataGetter<DataSubject> newVisitor() {
                return mVisitorFactory.newSubjectsCollector();
            }
        });
    }

    public ReviewListReference<DataAuthorId> newAuthorsList(ReviewNode root) {
        return newItemList(root, new VisitorFactory.ItemVisitor<DataAuthorId>() {
            @Override
            public VisitorDataGetter<DataAuthorId> newVisitor() {
                return mVisitorFactory.newAuthorsCollector();
            }
        });
    }

    public ReviewListReference<DataDate> newDatesList(ReviewNode root) {
        return newItemList(root, new VisitorFactory.ItemVisitor<DataDate>() {
            @Override
            public VisitorDataGetter<DataDate> newVisitor() {
                return mVisitorFactory.newDatesCollector();
            }
        });
    }

    public ReviewListReference<DataCriterion> newCriteriaList(ReviewNode root) {
        return newDataList(root, new VisitorFactory.ListVisitor<DataCriterion>() {
            @Override
            public VisitorDataGetter<ReviewListReference<DataCriterion>> newVisitor() {
                return mVisitorFactory.newCriteriaCollector();
            }
        });
    }

    public ReviewListReference<DataComment> newCommentsList(ReviewNode root) {
        return newDataList(root, new VisitorFactory.ListVisitor<DataComment>() {
            @Override
            public VisitorDataGetter<ReviewListReference<DataComment>> newVisitor() {
                return mVisitorFactory.newCommentsCollector();
            }
        });
    }

    public ReviewListReference<DataImage> newImagesList(ReviewNode root) {
        return newDataList(root, new VisitorFactory.ListVisitor<DataImage>() {
            @Override
            public VisitorDataGetter<ReviewListReference<DataImage>> newVisitor() {
                return mVisitorFactory.newImagesCollector();
            }
        });
    }

    public ReviewListReference<DataLocation> newLocationsList(ReviewNode root) {
        return newDataList(root, new VisitorFactory.ListVisitor<DataLocation>() {
            @Override
            public VisitorDataGetter<ReviewListReference<DataLocation>> newVisitor() {
                return mVisitorFactory.newLocationsCollector();
            }
        });
    }

    public ReviewListReference<DataFact> newFactsList(ReviewNode root) {
        return newDataList(root, new VisitorFactory.ListVisitor<DataFact>() {
            @Override
            public VisitorDataGetter<ReviewListReference<DataFact>> newVisitor() {
                return mVisitorFactory.newFactsCollector();
            }
        });
    }

    public ReviewListReference<DataTag> newTagsList(ReviewNode root) {
        return newDataList(root, new VisitorFactory.ListVisitor<DataTag>() {
            @Override
            public VisitorDataGetter<ReviewListReference<DataTag>> newVisitor() {
                return mVisitorFactory.newTagsCollector();
            }
        });
    }
    
    private <T extends HasReviewId> ReviewListReference<T> newDataList(ReviewNode root,
                                                                            VisitorFactory
                                                                                    .ListVisitor<T> visitorFactory) {
        return new TreeListReferences<>(root, this, mTraverserFactory, visitorFactory);
    }

    private <T extends HasReviewId> ReviewListReference<T> newItemList(ReviewNode root,
                                                                       VisitorFactory.ItemVisitor<T>
                                                                               visitorFactory) {
        return new TreeItemReferences<>(root, this, mTraverserFactory, visitorFactory);
    }
}
