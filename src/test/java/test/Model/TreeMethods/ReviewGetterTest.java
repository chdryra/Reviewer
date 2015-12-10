package test.Model.TreeMethods;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation.ReviewGetter;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;

import org.junit.Test;

import test.TestUtils.RandomReview;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by: Rizwan Choudrey
 * On: 10/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewGetterTest {

    @Test
    public void reviewGetterReturnsNodeReview() {
        ReviewGetter getter = new ReviewGetter();
        ReviewNode node = RandomReview.nextReviewNode(false);
        IdableList<Review> reviews = getter.getData(node);
        assertThat(reviews.size(), is(1));
        assertThat(reviews.getItem(0), is(node.getReview()));
    }
}
