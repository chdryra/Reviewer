/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Factories.FactoryReferences;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.NodeCoverReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeItemRefSize;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeListRefSize;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeListReferences;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeRefCommentList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeRefDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeRefItemList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.VisitorFactory;
import com.chdryra.android.reviewer.Model.TreeMethods.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 02/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryMdReference {
    private final FactoryReferences mReferenceFactory;
    private final FactoryNodeTraverser mTraverserFactory;
    private final FactoryVisitorReviewNode mVisitorFactory;

    public FactoryMdReference(FactoryReferences referenceFactory,
                              FactoryNodeTraverser traverserFactory,
                              FactoryVisitorReviewNode visitorFactory) {
        mReferenceFactory = referenceFactory;
        mTraverserFactory = traverserFactory;
        mVisitorFactory = visitorFactory;
    }

    public FactoryReferences getReferenceFactory() {
        return mReferenceFactory;
    }

    public <Value extends HasReviewId> ReviewItemReference<DataSize> newSize(TreeRefItemList<Value> treeRef) {
        return new TreeItemRefSize<>(treeRef);
    }

    public <Value extends HasReviewId> ReviewItemReference<DataSize> newSize(TreeListReferences<Value, ?, ?> treeRef) {
        return new TreeListRefSize<>(treeRef);
    }

    public ReviewItemReference<DataImage> newCoverReferenceForNode(ReviewNode node) {
        return new NodeCoverReference(node);
    }

    public RefDataList<ReviewReference> newReviewsList(ReviewNode root) {
        return newItemList(root, new VisitorFactory.ItemVisitor<ReviewReference>() {
            @Override
            public VisitorDataGetter<ReviewReference> newVisitor() {
                return mVisitorFactory.newReviewsCollector();
            }
        });
    }

    public RefDataList<DataSubject> newSubjectsList(ReviewNode root) {
        return newItemList(root, new VisitorFactory.ItemVisitor<DataSubject>() {
            @Override
            public VisitorDataGetter<DataSubject> newVisitor() {
                return mVisitorFactory.newSubjectsCollector();
            }
        });
    }

    public RefDataList<DataAuthorId> newAuthorsList(ReviewNode root) {
        return newItemList(root, new VisitorFactory.ItemVisitor<DataAuthorId>() {
            @Override
            public VisitorDataGetter<DataAuthorId> newVisitor() {
                return mVisitorFactory.newAuthorsCollector();
            }
        });
    }

    public RefDataList<DataDate> newDatesList(ReviewNode root) {
        return newItemList(root, new VisitorFactory.ItemVisitor<DataDate>() {
            @Override
            public VisitorDataGetter<DataDate> newVisitor() {
                return mVisitorFactory.newDatesCollector();
            }
        });
    }

    public RefDataList<DataCriterion> newCriteriaList(ReviewNode root) {
        return newDataList(root, new VisitorFactory.ListVisitor<RefDataList<DataCriterion>>() {
            @Override
            public VisitorDataGetter<RefDataList<DataCriterion>> newVisitor() {
                return mVisitorFactory.newCriteriaCollector();
            }
        });
    }

    public RefCommentList newCommentsList(ReviewNode root) {
        return newCommentList(root, new VisitorFactory.ListVisitor<RefCommentList>() {
            @Override
            public VisitorDataGetter<RefCommentList> newVisitor() {
                return mVisitorFactory.newCommentsCollector();
            }
        });
    }

    public RefDataList<DataImage> newImagesList(ReviewNode root) {
        return newDataList(root, new VisitorFactory.ListVisitor<RefDataList<DataImage>>() {
            @Override
            public VisitorDataGetter<RefDataList<DataImage>> newVisitor() {
                return mVisitorFactory.newImagesCollector();
            }
        });
    }

    public RefDataList<DataLocation> newLocationsList(ReviewNode root) {
        return newDataList(root, new VisitorFactory.ListVisitor<RefDataList<DataLocation>>() {
            @Override
            public VisitorDataGetter<RefDataList<DataLocation>> newVisitor() {
                return mVisitorFactory.newLocationsCollector();
            }
        });
    }

    public RefDataList<DataFact> newFactsList(ReviewNode root) {
        return newDataList(root, new VisitorFactory.ListVisitor<RefDataList<DataFact>>() {
            @Override
            public VisitorDataGetter<RefDataList<DataFact>> newVisitor() {
                return mVisitorFactory.newFactsCollector();
            }
        });
    }

    public RefDataList<DataTag> newTagsList(ReviewNode root) {
        return newDataList(root, new VisitorFactory.ListVisitor<RefDataList<DataTag>>() {
            @Override
            public VisitorDataGetter<RefDataList<DataTag>> newVisitor() {
                return mVisitorFactory.newTagsCollector();
            }
        });
    }
    
    private <Value extends HasReviewId> RefDataList<Value>
    newDataList(ReviewNode root, VisitorFactory.ListVisitor<RefDataList<Value>> visitorFactory) {
        return new TreeRefDataList<>(root, this, mTraverserFactory, visitorFactory);
    }

    private RefCommentList newCommentList(ReviewNode root, VisitorFactory.ListVisitor<RefCommentList> visitorFactory) {
        return new TreeRefCommentList(root, this, mTraverserFactory, visitorFactory);
    }

    private <T extends HasReviewId> RefDataList<T>
    newItemList(ReviewNode root, VisitorFactory.ItemVisitor<T> visitorFactory) {
        return new TreeRefItemList<>(root, this, mTraverserFactory, visitorFactory);
    }
}
