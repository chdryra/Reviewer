package com.chdryra.android.reviewer.Model;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation
        .ReviewTreeComponent;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.TestUtils.RandomReviewId;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2015
 * Email: rizwan.choudrey@gmail.com
 */

@RunWith(MockitoJUnitRunner.class)
public class ReviewTreeComponentTest {

    @Test
    public void newComponentHasNoParent() {
        ReviewTreeComponent component = newComponent();
        assertThat(component.getParent(), is(nullValue()));
        assertThat(component.getChildren().size(), is(0));
    }

    @Test
    public void newComponentHasNoChildren() {
        ReviewTreeComponent component = newComponent();
        assertThat(component.getChildren().size(), is(0));
    }

    @Test
    public void newComponentHasNoRoot() {
        ReviewTreeComponent component = newComponent();
        assertThat(component.getRoot(), is(nullValue()));
    }

    @Test
    public void addChildAddsChildIfNotAlreadyAChild() {
        ReviewTreeComponent parent = newComponent();
        ReviewTreeComponent child = newComponent();

        assertThat(parent.getChildren().size(), is(0));

        parent.addChild(child);

        assertThat(parent.getChildren().size(), is(1));
        assertThat(parent.getChildren().getItem(0), is((ReviewNode)child));
    }

    @Test
    public void addChildSetsParentOfChildIfNotAlreadyAChild() {
        ReviewTreeComponent parent = newComponent();
        ReviewTreeComponent child = newComponent();

        assertThat(child.getParent(), is(nullValue()));

        parent.addChild(child);

        assertThat(child.getParent(), is((ReviewNode)parent));
    }

    @NonNull
    private ReviewTreeComponent newComponent() {
        Review review = mock(Review.class);
        MdReviewId id = RandomReviewId.nextMdReviewId();
        return new ReviewTreeComponent(id, review, false);
    }
}
