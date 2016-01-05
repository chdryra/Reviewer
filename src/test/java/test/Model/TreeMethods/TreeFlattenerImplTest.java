package test.Model.TreeMethods;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
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
        ArrayList<Review> expectedReviews = RandomReview.constructTreeAndGetReviews(root);
        assertThat(expectedReviews.size(), greaterThan(0));

        TreeFlattener flattener = new TreeFlattenerImpl(new FactoryVisitorReviewNode(),
                new FactoryNodeTraverser());

        IdableCollection<Review> reviews = flattener.flatten(root);
        assertThat(reviews.size(), is(expectedReviews.size()));
        for(Review review : reviews) {
            assertThat(expectedReviews.contains(review), is(true));
            expectedReviews.remove(review);
        }
    }
}
