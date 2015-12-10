package test.Model.ReviewsModel.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.ReviewTreeComponent;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.VisitorReviewNode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import test.TestUtils.RandomReview;
import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;

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
    public void newComponentIsItsOwnRoot() {
        ReviewNode component = newComponent();
        assertThat(component.getRoot(), is(component));
    }

    @Test
    public void addChildAddsChildIfNotAlreadyChild() {
        ReviewTreeComponent parent = newComponent();
        ReviewTreeComponent child = newComponent();

        assertThat(parent.getChildren().size(), is(0));

        parent.addChild(child);

        assertThat(parent.getChildren().size(), is(1));
        assertThat(parent.getChildren().getItem(0), is((ReviewNode)child));
    }

    @Test
    public void addChildDoesNotAddChildIfAlreadyChild() {
        ReviewTreeComponent parent = newComponent();
        ReviewTreeComponent child = newComponent();

        parent.addChild(child);
        assertThat(parent.getChildren().size(), is(1));

        parent.addChild(child);
        assertThat(parent.getChildren().size(), is(1));
    }

    @Test
    public void addChildSetsParentOfChildIfNotAlreadyChild() {
        ReviewTreeComponent parent = newComponent();
        ReviewTreeComponent child = newComponent();

        assertThat(child.getParent(), is(nullValue()));

        parent.addChild(child);

        assertThat(child.getParent(), is((ReviewNode)parent));
    }

    @Test
    public void addChildSwitchesParentOfChild() {
        ReviewTreeComponent parentOld = newComponent();
        ReviewTreeComponent parentNew = newComponent();
        ReviewTreeComponent child = newComponent();

        parentOld.addChild(child);
        parentNew.addChild(child);
        assertThat(child.getParent(), is((ReviewNode) parentNew));
    }

    @Test
    public void addChildToNewParentRemovesCorrectChildFromOldParent() {
        ReviewTreeComponent parentOld = newComponent();
        ReviewTreeComponent parentNew = newComponent();
        ReviewTreeComponent child1 = newComponent();
        ReviewTreeComponent child2 = newComponent();

        parentOld.addChild(child1);
        parentOld.addChild(child2);
        assertThat(parentOld.getChildren().size(), is(2));

        parentNew.addChild(child1);
        assertThat(parentOld.getChildren().size(), is(1));
        assertThat(parentOld.getChildren().getItem(0), is((ReviewNode)child2));
    }

    @Test
    public void removeChildRemovesCorrectChildIfAlreadyAChild() {
        ReviewTreeComponent parent = newComponent();
        ReviewTreeComponent child1 = newComponent();
        ReviewTreeComponent child2 = newComponent();

        parent.addChild(child1);
        parent.addChild(child2);
        assertThat(parent.getChildren().size(), is(2));

        parent.removeChild(child1.getReviewId());
        assertThat(parent.getChildren().size(), is(1));
        assertThat(parent.getChildren().getItem(0), is((ReviewNode)child2));
    }

    @Test
    public void removeChildDoesNotRemoveADifferentChildIfNotAChild() {
        ReviewTreeComponent parent = newComponent();
        ReviewTreeComponent child1 = newComponent();
        ReviewTreeComponent child2 = newComponent();

        parent.addChild(child1);
        assertThat(parent.getChildren().size(), is(1));

        parent.removeChild(child2.getReviewId());
        assertThat(parent.getChildren().size(), is(1));
    }

    @Test
    public void removeChildRemovesChildsParentIfAlreadyAChild() {
        ReviewTreeComponent parent = newComponent();
        ReviewTreeComponent child = newComponent();

        parent.addChild(child);
        assertThat(child.getParent(), is((ReviewNode) parent));

        parent.removeChild(child.getReviewId());
        assertThat(child.getParent(), is(nullValue()));
    }

    @Test
    public void setParentSetsParent() {
        ReviewTreeComponent parent = newComponent();
        ReviewTreeComponent child = newComponent();

        assertThat(child.getParent(), is(nullValue()));

        child.setParent(parent);
        assertThat(child.getParent(), is((ReviewNode) parent));
    }

    @Test
    public void setParentAddsChildToParent() {
        ReviewTreeComponent parent = newComponent();
        ReviewTreeComponent child = newComponent();

        assertThat(parent.getChildren().size(), is(0));

        child.setParent(parent);
        assertThat(parent.getChildren().size(), is(1));
        assertThat(parent.getChildren().getItem(0), is((ReviewNode) child));
    }

    @Test
    public void setParentRemovesChildFromOldParent() {
        ReviewTreeComponent parentOld = newComponent();
        ReviewTreeComponent parentNew = newComponent();
        ReviewTreeComponent child1 = newComponent();
        ReviewTreeComponent child2 = newComponent();

        parentOld.addChild(child1);
        parentOld.addChild(child2);
        assertThat(parentOld.getChildren().size(), is(2));

        child1.setParent(parentNew);
        assertThat(parentOld.getChildren().size(), is(1));
        assertThat(parentOld.getChildren().getItem(0), is((ReviewNode) child2));
    }

    @Test
    public void getRootGetsRoot() {
        ReviewTreeComponent greatGrandParent = newComponent();
        ReviewTreeComponent grandParent = newComponent();
        ReviewTreeComponent parent = newComponent();
        ReviewTreeComponent child = newComponent();

        assertThat(child.getRoot(), is((ReviewNode) child));

        greatGrandParent.addChild(grandParent);
        grandParent.addChild(parent);
        parent.addChild(child);

        assertThat(child.getRoot(), is((ReviewNode)greatGrandParent));
    }

    @Test
    public void simpleReviewIsNotExpandable() {
        Review review = RandomReview.nextReview();
        ReviewNode node = new ReviewTreeComponent(new MdReviewId(review.getReviewId()), review, false);
        assertThat(node.isExpandable(), is(false));
    }

    @Test
    public void reviewTreeReviewIsExpandable() {
        ReviewNode review = RandomReview.nextReviewNode(false);
        ReviewNode node = new ReviewTreeComponent(new MdReviewId(review.getReviewId()), review, false);
        assertThat(node.isExpandable(), is(true));
    }

    @Test
    public void expandExpandsReview() {
        ReviewNode review = RandomReview.nextReviewNode(false);
        ReviewNode node = new ReviewTreeComponent(new MdReviewId(review.getReviewId()), review, false);
        assertThat(node.expand(), is(review.getTreeRepresentation()));
    }

    @Test
    public void hasChildReturnsFalseIfNotChildOrTrueIfChild() {
        ReviewTreeComponent parent = newComponent();
        ReviewTreeComponent child = newComponent();

        assertThat(parent.hasChild(child.getReviewId()), is(false));
        parent.addChild(child);
        assertThat(parent.hasChild(child.getReviewId()), is(true));
    }

    @Test
    public void getChildGivenReviewIdReturnsCorrectChild() {
        ReviewTreeComponent parent = newComponent();
        ReviewTreeComponent child1 = newComponent();
        ReviewTreeComponent child2 = newComponent();

        assertThat(parent.getChild(child1.getReviewId()), is(nullValue()));
        parent.addChild(child1);
        parent.addChild(child2);
        assertThat(parent.getChild(child1.getReviewId()), is((ReviewNode) child1));
        assertThat(parent.getChild(child2.getReviewId()), is((ReviewNode) child2));
    }

    @Test
    public void getChildrenReturnsAllChildren() {
        ReviewTreeComponent parent = newComponent();
        ReviewTreeComponent child1 = newComponent();
        ReviewTreeComponent child2 = newComponent();

        assertThat(parent.getChildren().size(), is(0));
        parent.addChild(child1);
        parent.addChild(child2);
        IdableList<ReviewNode> children = parent.getChildren();
        assertThat(children.size(), is(2));
        assertThat(children.getItem(0), is((ReviewNode) child1));
        assertThat(children.getItem(1), is((ReviewNode) child2));
    }

    @Test
    public void acceptVisitorCallsVisitor() {
        ReviewNode node = newComponent();
        VisitorForTest visitor = new VisitorForTest();
        assertThat(visitor.isVisited(), is(false));
        node.acceptVisitor(visitor);
        assertThat(visitor.isVisited(), is(true));
    }

    @Test
    public void getReviewIdReturnsCorrectId() {
        MdReviewId id = RandomReviewId.nextMdReviewId();
        ReviewNode node = newComponent(id);
        assertThat(node.getReviewId(), is(id.toString()));
    }

    @Test
    public void getReviewReturnsCorrectReview() {
        Review review = RandomReview.nextReview();
        ReviewNode node = newComponent(RandomReviewId.nextMdReviewId(), review, false);
        assertThat(node.getReview(), is(review));
    }

    @Test
    public void getSubjectIsReviewsSubject() {
        Review review = RandomReview.nextReview();
        ReviewNode node = newComponent(RandomReviewId.nextMdReviewId(), review, false);
        assertThat(node.getSubject(), is(review.getSubject()));
    }

    @Test
    public void getRatingIsReviewsRatingWhenAverageOfChildrenIsFalse() {
        Review review = RandomReview.nextReview();
        ReviewNodeComponent node = newComponent(RandomReviewId.nextMdReviewId(), review, false);
        ReviewNodeComponent child1 = newComponent();
        ReviewNodeComponent child2 = newComponent();
        ReviewNodeComponent child3 = newComponent();
        node.addChild(child1);
        node.addChild(child2);
        node.addChild(child3);

        assertThat(node.getRating(), is(review.getRating()));
    }

    @Test
    public void getRatingIsChildrensAverageWhenAverageOfChildrenIsTrue() {
        ReviewNodeComponent node
                = newComponent(RandomReviewId.nextMdReviewId(), RandomReview.nextReview(), true);
        ReviewNodeComponent child1 = newComponent();
        ReviewNodeComponent child2 = newComponent();
        ReviewNodeComponent child3 = newComponent();

        assertThat(node.getRating().getRating(), is(0f));
        assertThat(node.getRating().getRatingWeight(), is(0));

        node.addChild(child1);
        node.addChild(child2);
        node.addChild(child3);

        float average = (child1.getRating().getRating()
                + child2.getRating().getRating() + child3.getRating().getRating()) / 3f;

        assertThat(node.getRating().getRating(), is(average));
        assertThat(node.getRating().getRatingWeight(), is(3));
    }

    @Test
    public void getRatingIsWeightedAverageOfAveragesForTreeWhenAverageOfChildrenIsTrue() {
        ReviewNodeComponent node
                = newComponent(RandomReviewId.nextMdReviewId(), RandomReview.nextReview(), true);

        assertThat(node.getRating().getRating(), is(0f));
        assertThat(node.getRating().getRatingWeight(), is(0));

        IdableList<ReviewNodeComponent> children = addChildren(node, 3, true);
        addChildren(children.getItem(0), 10, false);
        addChildren(children.getItem(1), 5, false);
        addChildren(children.getItem(2), 2, false);

        float average = getAverageRating(children);

        assertThat(node.getRating().getRating(), is(average));
        assertThat(node.getRating().getRatingWeight(), is(17));
    }

    @Test
    public void getAuthorIsReviewAuthor() {
        Review review = RandomReview.nextReview();
        ReviewNode node = newComponent(RandomReviewId.nextMdReviewId(), review, false);
        assertThat(node.getAuthor(), is(review.getAuthor()));
    }

    @Test
    public void getPublishDateIsReviewPublishDate() {
        Review review = RandomReview.nextReview();
        ReviewNode node = newComponent(RandomReviewId.nextMdReviewId(), review, false);
        assertThat(node.getPublishDate(), is(review.getPublishDate()));
    }

    @Test
    public void getTreeRepresentationIsItself() {
        ReviewNode node = newComponent();
        assertThat(node.getTreeRepresentation(), is(node));
    }

    @Test
    public void getCriteriaIsForWholeTree() {
        getDataIsForWholeTree(new DataGetter<DataReviewIdable>() {
            @Override
            IdableList<? extends DataReviewIdable> getData(Review review) {
                return review.getCriteria();
            }
        });
    }

    @Test
    public void getCommentsIsForWholeTree() {
        getDataIsForWholeTree(new DataGetter<DataReviewIdable>() {
            @Override
            IdableList<? extends DataReviewIdable> getData(Review review) {
                return review.getComments();
            }
        });
    }

    @Test
    public void getFactsIsForWholeTree() {
        getDataIsForWholeTree(new DataGetter<DataReviewIdable>() {
            @Override
            IdableList<? extends DataReviewIdable> getData(Review review) {
                return review.getFacts();
            }
        });
    }

    @Test
    public void getImagesIsForWholeTree() {
        getDataIsForWholeTree(new DataGetter<DataReviewIdable>() {
            @Override
            IdableList<? extends DataReviewIdable> getData(Review review) {
                return review.getImages();
            }
        });
    }

    @Test
    public void getCoversIsForWholeTree() {
        getDataIsForWholeTree(new DataGetter<DataReviewIdable>() {
            @Override
            IdableList<? extends DataReviewIdable> getData(Review review) {
                return review.getCovers();
            }
        });
    }

    @Test
    public void getLocationsIsForWholeTree() {
        getDataIsForWholeTree(new DataGetter<DataReviewIdable>() {
            @Override
            IdableList<? extends DataReviewIdable> getData(Review review) {
                return review.getLocations();
            }
        });
    }

    private <T extends DataReviewIdable> void getDataIsForWholeTree(DataGetter<T> getter) {
        ReviewNodeComponent node = newComponent(RandomReviewId.nextMdReviewId(), RandomReview.nextReview(), true);
        IdableList<Review> reviews = makeTree(node);
        IdableList<? extends T> dataNode = getter.getData(node);
        IdableList<T> allData = new IdableDataList<>(node.getReviewId());
        for(Review review : reviews) {
            allData.addAll(getter.getData(review));
        }
        assertThat(dataNode.size(), is(allData.size()));
        for(T datum : allData) {
            assertThat(dataNode.contains(datum), is(true));
        }
    }

    @NonNull
    private IdableList<Review> makeTree(ReviewNodeComponent node) {
        IdableList<ReviewNodeComponent> children = addChildren(node, 3, true);
        IdableList<ReviewNodeComponent> grandChildren0 = addChildren(children.getItem(0), 3, false);
        IdableList<ReviewNodeComponent> grandChildren1 = addChildren(children.getItem(1), 3, false);
        IdableList<ReviewNodeComponent> grandChildren2 = addChildren(children.getItem(2), 2, false);
        IdableList<ReviewNodeComponent> ggc = addChildren(grandChildren2.getItem(0), 3, false);

        IdableList<ReviewNodeComponent> allNodes = new IdableDataList<>(node.getReviewId());
        allNodes.add(node);
        allNodes.addAll(children);
        allNodes.addAll(grandChildren0);
        allNodes.addAll(grandChildren1);
        allNodes.addAll(grandChildren2);
        allNodes.addAll(ggc);

        IdableList<Review> reviews = new IdableDataList<>(node.getReviewId());
        for(ReviewNodeComponent component : allNodes) {
            reviews.add(component.getReview());
        }
        return reviews;
    }

    private float getAverageRating(IdableList<? extends ReviewNode> nodes) {
        float rating = 0f;
        int weights = 0;
        for(ReviewNode node : nodes) {
            weights += node.getRating().getRatingWeight();
            rating += node.getRating().getRating() * node.getRating().getRatingWeight();
        }

        return weights > 0 ? rating / weights : 0f;
    }

    private IdableList<ReviewNodeComponent> addChildren(ReviewNodeComponent parent, int num,
                                                        boolean isAverage) {
        IdableList<ReviewNodeComponent> children = new IdableDataList<>(parent.getReviewId());
        for(int i = 0; i < num ; ++i) {
            ReviewNodeComponent child = newComponent(RandomReviewId.nextMdReviewId(),
                    RandomReview.nextReview(), isAverage);
            parent.addChild(child);
            children.add(child);
        }

        return children;
    }

    @NonNull
    private ReviewTreeComponent newComponent() {
        return newComponent(RandomReviewId.nextMdReviewId());
    }

    private ReviewTreeComponent newComponent(MdReviewId id) {
        return newComponent(id, RandomReview.nextReview(), false);
    }

    private ReviewTreeComponent newComponent(MdReviewId id, Review review, boolean isAverage) {
        return new ReviewTreeComponent(id, review, isAverage);
    }

    private class VisitorForTest implements VisitorReviewNode {
        private boolean mVisited = false;
        @Override
        public void visit(ReviewNode reviewNode) {
            mVisited = true;
        }

        public boolean isVisited() {
            return mVisited;
        }
    }

    private abstract class DataGetter<T extends DataReviewIdable> {
        abstract IdableList<? extends T> getData(Review review);
    }
}