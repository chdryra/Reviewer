/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.ConditionalDataGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorDataGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.NodeDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryVisitorReviewNode {
    private static final ConditionalDataGetter.Condition IS_LEAF
            = new ConditionalDataGetter.Condition() {
        @Override
        public boolean isTrue(ReviewNode node) {
            return node.isLeaf();
        }
    };

    private <Value extends HasReviewId> VisitorDataGetter<Value> newItemCollector(NodeDataGetter<Value> getter) {
        return new VisitorDataGetter<>(new ConditionalDataGetter<>(IS_LEAF, getter));
    }

    private <List extends ReviewListReference<?, ?>> VisitorDataGetter<List>
    newListCollector(NodeDataGetter<List> getter) {
        return new VisitorDataGetter<>(new ConditionalDataGetter<>(IS_LEAF, getter));
    }

    public VisitorDataGetter<DataSubject> newSubjectsCollector() {
        return newItemCollector(new NodeDataGetter<DataSubject>() {
            @Nullable
            @Override
            public DataSubject getData(@NonNull ReviewNode node) {
                return node.getSubject();
            }
        });
    }

    public VisitorDataGetter<DataAuthorId> newAuthorsCollector() {
        return newItemCollector(new NodeDataGetter<DataAuthorId>() {
            @Nullable
            @Override
            public DataAuthorId getData(@NonNull ReviewNode node) {
                return node.getAuthorId();
            }
        });
    }

    public VisitorDataGetter<DataDate> newDatesCollector() {
        return newItemCollector(new NodeDataGetter<DataDate>() {
            @Nullable
            @Override
            public DataDate getData(@NonNull ReviewNode node) {
                return node.getPublishDate();
            }
        });
    }

    public VisitorDataGetter<ReviewReference> newReviewsCollector() {
        return newItemCollector(new NodeDataGetter<ReviewReference>() {
            @Nullable
            @Override
            public ReviewReference getData(@NonNull ReviewNode node) {
                return node.getReference();
            }
        });
    }
    
    public VisitorDataGetter<RefDataList<DataCriterion>> newCriteriaCollector() {
        return newListCollector(new NodeDataGetter<RefDataList<DataCriterion>>() {
            @Nullable
            @Override
            public RefDataList<DataCriterion> getData(@NonNull ReviewNode node) {
                return node.getCriteria();
            }
        });
    }

    public VisitorDataGetter<RefCommentList> newCommentsCollector() {
        return newListCollector(new NodeDataGetter<RefCommentList>() {
            @Nullable
            @Override
            public RefCommentList getData(@NonNull ReviewNode node) {
                return node.getComments();
            }
        });
    }

    public VisitorDataGetter<RefDataList<DataImage>> newImagesCollector() {
        return newListCollector(new NodeDataGetter<RefDataList<DataImage>>() {
            @Nullable
            @Override
            public RefDataList<DataImage> getData(@NonNull ReviewNode node) {
                return node.getImages();
            }
        });
    }
    
    public VisitorDataGetter<RefDataList<DataLocation>> newLocationsCollector() {
        return newListCollector(new NodeDataGetter<RefDataList<DataLocation>>() {
            @Nullable
            @Override
            public RefDataList<DataLocation> getData(@NonNull ReviewNode node) {
                return node.getLocations();
            }
        });
    }

    public VisitorDataGetter<RefDataList<DataFact>> newFactsCollector() {
        return newListCollector(new NodeDataGetter<RefDataList<DataFact>>() {
            @Nullable
            @Override
            public RefDataList<DataFact> getData(@NonNull ReviewNode node) {
                return node.getFacts();
            }
        });
    }

    public VisitorDataGetter<RefDataList<DataTag>> newTagsCollector() {
        return newListCollector(new NodeDataGetter<RefDataList<DataTag>>() {
            @Nullable
            @Override
            public RefDataList<DataTag> getData(@NonNull ReviewNode node) {
                return node.getTags();
            }
        });
    }
}
