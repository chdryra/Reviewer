/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.TreeMethods;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation.VisitorReviewDataGetterImpl;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Interfaces.NodeDataGetter;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.VisitorReviewDataGetter;

import org.junit.Test;

import test.TestUtils.RandomReview;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by: Rizwan Choudrey
 * On: 20/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorReviewDataGetterImplTest {
    @Test
    public void testVisit() {
        ReviewNode node = RandomReview.nextReviewNode();
        VisitorReviewDataGetter<DataComment> visitor = new VisitorReviewDataGetterImpl<>(new CommentsGetter());
        visitor.visit(node);
        IdableCollection<DataComment> comments = visitor.getData();
        IdableList<? extends DataComment> nodeComments = node.getComments();
        assertThat(comments.size(), is(nodeComments.size()));
        for(int i = 0; i < comments.size(); ++i) {
            DataComment comment = nodeComments.getItem(i);
            assertThat(comments.contains(comment), is(true));
            comments.remove(comment);
        }
    }

    private class CommentsGetter implements NodeDataGetter<DataComment> {
        @Override
        public IdableList<DataComment> getData(@NonNull ReviewNode node) {
            IdableList<DataComment> comments = new IdableDataList<>(node.getReviewId());
            comments.addAll(node.getComments());
            return comments;
        }
    }
}
