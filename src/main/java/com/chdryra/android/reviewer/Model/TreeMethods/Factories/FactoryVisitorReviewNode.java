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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
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

    private <T extends HasReviewId> VisitorDataGetter<T> newItemCollector(NodeDataGetter<T> getter) {
        return new VisitorDataGetter<>(new ConditionalDataGetter<>(IS_LEAF, getter));
    }

    private <T extends HasReviewId> VisitorDataGetter<ReviewListReference<T>>
    newListCollector(NodeDataGetter<ReviewListReference<T>> getter) {
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
    
    public VisitorDataGetter<ReviewListReference<DataCriterion>> newCriteriaCollector() {
        return newListCollector(new NodeDataGetter<ReviewListReference<DataCriterion>>() {
            @Nullable
            @Override
            public ReviewListReference<DataCriterion> getData(@NonNull ReviewNode node) {
                return node.getCriteria();
            }
        });
    }

    public VisitorDataGetter<ReviewListReference<DataComment>> newCommentsCollector() {
        return newListCollector(new NodeDataGetter<ReviewListReference<DataComment>>() {
            @Nullable
            @Override
            public ReviewListReference<DataComment> getData(@NonNull ReviewNode node) {
                return node.getComments();
            }
        });
    }

    public VisitorDataGetter<ReviewListReference<DataImage>> newImagesCollector() {
        return newListCollector(new NodeDataGetter<ReviewListReference<DataImage>>() {
            @Nullable
            @Override
            public ReviewListReference<DataImage> getData(@NonNull ReviewNode node) {
                return node.getImages();
            }
        });
    }
    
    public VisitorDataGetter<ReviewListReference<DataLocation>> newLocationsCollector() {
        return newListCollector(new NodeDataGetter<ReviewListReference<DataLocation>>() {
            @Nullable
            @Override
            public ReviewListReference<DataLocation> getData(@NonNull ReviewNode node) {
                return node.getLocations();
            }
        });
    }

    public VisitorDataGetter<ReviewListReference<DataFact>> newFactsCollector() {
        return newListCollector(new NodeDataGetter<ReviewListReference<DataFact>>() {
            @Nullable
            @Override
            public ReviewListReference<DataFact> getData(@NonNull ReviewNode node) {
                return node.getFacts();
            }
        });
    }

    public VisitorDataGetter<ReviewListReference<DataTag>> newTagsCollector() {
        return newListCollector(new NodeDataGetter<ReviewListReference<DataTag>>() {
            @Nullable
            @Override
            public ReviewListReference<DataTag> getData(@NonNull ReviewNode node) {
                return node.getTags();
            }
        });
    }
}
