package test.Model.TreeMethods;

import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation
        .DepthFirstPreIterator;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNodeComponent;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;

import test.TestUtils.RandomReview;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by: Rizwan Choudrey
 * On: 20/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DepthFirstPreIteratorTest {
    @Test
    public void HasNextBeforePopIsTrue() {
        ReviewNode node = RandomReview.nextReviewNode();
        DepthFirstPreIterator iterator = new DepthFirstPreIterator(node);
        assertThat(iterator.hasNext(), is(true));
    }

    @Test
    public void firstPopIsRoot() {
        ReviewNode node = RandomReview.nextReviewNode();
        DepthFirstPreIterator iterator = new DepthFirstPreIterator(node);
        assertThat(iterator.next(), is(node));
    }

    @Test
    public void HasNextAfterPopIsFalse() {
        ReviewNode node = RandomReview.nextReviewNode();
        DepthFirstPreIterator iterator = new DepthFirstPreIterator(node);
        iterator.next();
        assertThat(iterator.hasNext(), is(false));
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void nextOnHasNextIsFalseThrowsNoSuchElementException() {
        expectedException.expect(NoSuchElementException.class);
        ReviewNode node = RandomReview.nextReviewNode();
        DepthFirstPreIterator iterator = new DepthFirstPreIterator(node);
        iterator.next();
        iterator.next();
    }

    @Test
    public void removeThrowsUnsupportedOperationException() {
        expectedException.expect(UnsupportedOperationException.class);
        ReviewNode node = RandomReview.nextReviewNode();
        DepthFirstPreIterator iterator = new DepthFirstPreIterator(node);
        iterator.remove();
    }

    @Test
    public void nextReturnsRootThenChildrenIfOnlyChildren() {
        ReviewNodeComponent root = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent child1 = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent child2 = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent child3 = RandomReview.nextReviewNodeComponent(false);
        root.addChild(child1);
        root.addChild(child2);
        root.addChild(child3);

        DepthFirstPreIterator iterator = new DepthFirstPreIterator(root);
        assertThat(iterator.next(), is((ReviewNode)root));
        assertThat(iterator.next(), is((ReviewNode) child3));
        assertThat(iterator.next(), is((ReviewNode)child2));
        assertThat(iterator.next(), is((ReviewNode) child1));
    }

    @Test
    public void nextReturnsRootThenChildThenGrandChildren() {
        ReviewNodeComponent root = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent child1 = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent child2 = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent child3 = RandomReview.nextReviewNodeComponent(false);
        root.addChild(child1);
        child1.addChild(child2);
        child2.addChild(child3);

        DepthFirstPreIterator iterator = new DepthFirstPreIterator(root);
        assertThat(iterator.next(), is((ReviewNode)root));
        assertThat(iterator.next(), is((ReviewNode)child1));
        assertThat(iterator.next(), is((ReviewNode)child2));
        assertThat(iterator.next(), is((ReviewNode)child3));
    }

    @Test
    public void nextGoesNodeFirstThenDescendantsThenSiblings() {
        ReviewNodeComponent root = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent child1 = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent child2 = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent child3 = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent grandchild11 = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent grandchild12 = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent grandchild21 = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent grandchild31 = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent greatgrandchild111 = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent greatgrandchild112 = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent greatgrandchild211 = RandomReview.nextReviewNodeComponent(false);
        ReviewNodeComponent greatgrandchild212 = RandomReview.nextReviewNodeComponent(false);

        root.addChild(child1);
        root.addChild(child2);
        root.addChild(child3);
        child1.addChild(grandchild11);
        child1.addChild(grandchild12);
        child2.addChild(grandchild21);
        child3.addChild(grandchild31);
        grandchild11.addChild(greatgrandchild111);
        grandchild11.addChild(greatgrandchild112);
        grandchild21.addChild(greatgrandchild211);
        grandchild21.addChild(greatgrandchild212);

        DepthFirstPreIterator iterator = new DepthFirstPreIterator(root);
        assertThat(iterator.next(), is((ReviewNode) root));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is((ReviewNode)child3));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is((ReviewNode)grandchild31));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is((ReviewNode)child2));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is((ReviewNode)grandchild21));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is((ReviewNode)greatgrandchild212));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is((ReviewNode)greatgrandchild211));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is((ReviewNode)child1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is((ReviewNode)grandchild12));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is((ReviewNode)grandchild11));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is((ReviewNode)greatgrandchild112));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is((ReviewNode)greatgrandchild111));
        assertThat(iterator.hasNext(), is(false));
    }
}
