package test.Model.TreeMethods;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableItems;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewTreeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation
        .TreeFlattenerImpl;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Interfaces.TreeFlattener;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNodeComponent;

import org.junit.Test;

import java.util.ArrayList;

import test.TestUtils.RandomReview;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

/**
 * Created by: Rizwan Choudrey
 * On: 20/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeFlattenerImplTest {

    @Test
    public void testFlatten() {
        ReviewNodeComponent root = RandomReview.nextReviewNodeComponent();
        ArrayList<Review> expectedReviews = constructTreeAndGetReviews(root);
        assertThat(expectedReviews.size(), greaterThan(0));

        TreeFlattener flattener = new TreeFlattenerImpl(new FactoryVisitorReviewNode(),
                new FactoryReviewTreeTraverser());

        IdableItems<Review> reviews = flattener.flatten(root);
        assertThat(reviews.size(), is(expectedReviews.size()));
        for(Review review : reviews) {
            assertThat(expectedReviews.contains(review), is(true));
            expectedReviews.remove(review);
        }
    }

    private ArrayList<Review> constructTreeAndGetReviews(ReviewNodeComponent root) {
        ReviewNodeComponent child1 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent child2 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent child3 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent grandchild11 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent grandchild12 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent grandchild21 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent grandchild31 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent greatgrandchild111 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent greatgrandchild112 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent greatgrandchild211 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent greatgrandchild212 = RandomReview.nextReviewNodeComponent();

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

        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(root.getReview());
        reviews.add(child1.getReview());
        reviews.add(child2.getReview());
        reviews.add(child3.getReview());
        reviews.add(grandchild11.getReview());
        reviews.add(grandchild12.getReview());
        reviews.add(grandchild21.getReview());
        reviews.add(grandchild31.getReview());
        reviews.add(greatgrandchild111.getReview());
        reviews.add(greatgrandchild112.getReview());
        reviews.add(greatgrandchild211.getReview());
        reviews.add(greatgrandchild212.getReview());

        return reviews;
    }
}
