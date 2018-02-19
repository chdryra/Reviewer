/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Factories;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Factories.FactoryReferences;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.NodeCoverReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.TreeItemRefSize;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.TreeListRefSize;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.TreeListReferences;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.TreeCommentListRef;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.TreeDataListRef;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.TreeItemListRef;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.VisitorFactory;
import com.chdryra.android.startouch.Model.TreeMethods.Factories.FactoryNodeTraverser;
import com.chdryra.android.startouch.Model.TreeMethods.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.startouch.Model.TreeMethods.Implementation.VisitorDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 02/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataReference {
    private final FactoryReferences mReferenceFactory;
    private final FactoryNodeTraverser mTraverserFactory;
    private final FactoryVisitorReviewNode mVisitorFactory;

    public FactoryDataReference(FactoryReferences referenceFactory,
                                FactoryNodeTraverser traverserFactory,
                                FactoryVisitorReviewNode visitorFactory) {
        mReferenceFactory = referenceFactory;
        mTraverserFactory = traverserFactory;
        mVisitorFactory = visitorFactory;
    }

    public FactoryReferences getReferenceFactory() {
        return mReferenceFactory;
    }

    public <Value extends HasReviewId> ReviewItemReference<DataSize> newSize(TreeItemListRef<Value> treeRef) {
        return new TreeItemRefSize<>(treeRef);
    }

    public <Value extends HasReviewId> ReviewItemReference<DataSize> newSize(TreeListReferences<Value, ?, ?> treeRef) {
        return new TreeListRefSize<>(treeRef);
    }

    public ReviewItemReference<DataImage> newCoverReferenceForNode(ReviewNode node) {
        return new NodeCoverReference(node);
    }

    public DataListRef<ReviewReference> newReviewsList(ReviewNode root) {
        return newItemList(root, new VisitorFactory.ItemVisitor<ReviewReference>() {
            @Override
            public VisitorDataGetter<ReviewReference> newVisitor() {
                return mVisitorFactory.newReviewsCollector();
            }
        });
    }

    public DataListRef<DataSubject> newSubjectsList(ReviewNode root) {
        return newItemList(root, new VisitorFactory.ItemVisitor<DataSubject>() {
            @Override
            public VisitorDataGetter<DataSubject> newVisitor() {
                return mVisitorFactory.newSubjectsCollector();
            }
        });
    }

    public DataListRef<DataAuthorId> newAuthorsList(ReviewNode root) {
        return newItemList(root, new VisitorFactory.ItemVisitor<DataAuthorId>() {
            @Override
            public VisitorDataGetter<DataAuthorId> newVisitor() {
                return mVisitorFactory.newAuthorsCollector();
            }
        });
    }

    public DataListRef<DataDate> newDatesList(ReviewNode root) {
        return newItemList(root, new VisitorFactory.ItemVisitor<DataDate>() {
            @Override
            public VisitorDataGetter<DataDate> newVisitor() {
                return mVisitorFactory.newDatesCollector();
            }
        });
    }

    public DataListRef<DataCriterion> newCriteriaList(ReviewNode root) {
        return newDataList(root, new VisitorFactory.ListVisitor<DataListRef<DataCriterion>>() {
            @Override
            public VisitorDataGetter<DataListRef<DataCriterion>> newVisitor() {
                return mVisitorFactory.newCriteriaCollector();
            }
        });
    }

    public CommentListRef newCommentsList(ReviewNode root) {
        return newCommentList(root, new VisitorFactory.ListVisitor<CommentListRef>() {
            @Override
            public VisitorDataGetter<CommentListRef> newVisitor() {
                return mVisitorFactory.newCommentsCollector();
            }
        });
    }

    public DataListRef<DataImage> newImagesList(ReviewNode root) {
        return newDataList(root, new VisitorFactory.ListVisitor<DataListRef<DataImage>>() {
            @Override
            public VisitorDataGetter<DataListRef<DataImage>> newVisitor() {
                return mVisitorFactory.newImagesCollector();
            }
        });
    }

    public DataListRef<DataLocation> newLocationsList(ReviewNode root) {
        return newDataList(root, new VisitorFactory.ListVisitor<DataListRef<DataLocation>>() {
            @Override
            public VisitorDataGetter<DataListRef<DataLocation>> newVisitor() {
                return mVisitorFactory.newLocationsCollector();
            }
        });
    }

    public DataListRef<DataFact> newFactsList(ReviewNode root) {
        return newDataList(root, new VisitorFactory.ListVisitor<DataListRef<DataFact>>() {
            @Override
            public VisitorDataGetter<DataListRef<DataFact>> newVisitor() {
                return mVisitorFactory.newFactsCollector();
            }
        });
    }

    public DataListRef<DataTag> newTagsList(ReviewNode root) {
        return newDataList(root, new VisitorFactory.ListVisitor<DataListRef<DataTag>>() {
            @Override
            public VisitorDataGetter<DataListRef<DataTag>> newVisitor() {
                return mVisitorFactory.newTagsCollector();
            }
        });
    }
    
    private <Value extends HasReviewId> DataListRef<Value>
    newDataList(ReviewNode root, VisitorFactory.ListVisitor<DataListRef<Value>> visitorFactory) {
        return new TreeDataListRef<>(root, this, mTraverserFactory, visitorFactory);
    }

    private CommentListRef newCommentList(ReviewNode root, VisitorFactory.ListVisitor<CommentListRef> visitorFactory) {
        return new TreeCommentListRef(root, this, mTraverserFactory, visitorFactory);
    }

    private <T extends HasReviewId> DataListRef<T>
    newItemList(ReviewNode root, VisitorFactory.ItemVisitor<T> visitorFactory) {
        return new TreeItemListRef<>(root, this, mTraverserFactory, visitorFactory);
    }
}
