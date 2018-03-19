/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.TreeMethods;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableCollection;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.TreeMethods.Implementation.VisitorDataGetter;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.NodeDataGetter;

import org.junit.Test;

import test.TestUtils.RandomReview;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 20/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ItemGetterTest {
    @Test
    public void testVisit() {
        ReviewNode node = RandomReview.nextReviewNode();
        VisitorDataGetter<DataComment> visitor = new VisitorDataGetter.ItemGetter<>(new
                CommentsGetter());
        visitor.visit(node);
        IdableCollection<DataComment> comments = visitor.getData();
        IdableList<? extends DataComment> nodeComments = node.getData();
        assertThat(comments.size(), is(nodeComments.size()));
        for (int i = 0; i < comments.size(); ++i) {
            DataComment comment = nodeComments.get(i);
            assertThat(comments.contains(comment), is(true));
            comments.remove(comment);
        }
    }

    private class CommentsGetter implements NodeDataGetter<DataComment> {
        @Override
        public IdableList<DataComment> getData(@NonNull ReviewNode node) {
            IdableList<DataComment> comments = new IdableDataList<>(node.getReviewId());
            comments.addAll(node.getData());
            return comments;
        }
    }
}
