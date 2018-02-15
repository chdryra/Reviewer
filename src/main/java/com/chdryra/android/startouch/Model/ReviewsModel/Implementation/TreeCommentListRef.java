/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentListRef;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryMdReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.VisitorFactory;
import com.chdryra.android.startouch.Model.TreeMethods.Factories.FactoryNodeTraverser;

/**
 * Created by: Rizwan Choudrey
 * On: 29/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeCommentListRef extends TreeListReferences<DataComment, CommentRef, CommentListRef> implements CommentListRef {
    public TreeCommentListRef(ReviewNode root,
                              FactoryMdReference referenceFactory,
                              FactoryNodeTraverser traverserFactory,
                              VisitorFactory.ListVisitor<CommentListRef> visitorFactory) {
        super(root, referenceFactory, traverserFactory, visitorFactory);
    }

    @Override
    public void toSentences(final CommentRef.SentencesCallback callback) {
        toItemReferences(new ItemReferencesCallback<DataComment, CommentRef>() {
            @Override
            public void onItemReferences(IdableList<CommentRef> references) {
                getReferenceFactory().newSentencesCollector(references).collectAll(callback);
            }
        });
    }

}
