package test.Model.TreeMethods;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation.NodesTraverserIterated;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.NodesTraverser;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.VisitorReviewNode;

import org.junit.Test;

import java.util.ArrayList;

import test.TestUtils.RandomReview;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 20/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class NodesTraverserIteratedTest {
    @Test
    public void traverseVisitsCorrectNumberNodes() {
        ArrayList<ReviewNode> nodes = getNodes();

        Visitor1 visitor1 = new Visitor1();
        Visitor2 visitor2 = new Visitor2();
        Visitor3 visitor3 = new Visitor3();

        NodesTraverser traverser = new NodesTraverserIterated(nodes.iterator());
        traverser.addVisitor(visitor1);
        traverser.addVisitor(visitor2);
        traverser.addVisitor(visitor3);

        assertThat(visitor1.mSubjects.size(), is(0));
        assertThat(visitor2.mSubjects.size(), is(0));
        assertThat(visitor3.mSubjects.size(), is(0));

        traverser.traverse();

        assertThat(visitor1.mSubjects.size(), is(nodes.size()));
        assertThat(visitor2.mSubjects.size(), is(nodes.size()));
        assertThat(visitor3.mSubjects.size(), is(nodes.size()));
    }

    @Test
    public void traverseVisitsNodesInIteratedOrder() {
        ArrayList<ReviewNode> nodes = getNodes();

        Visitor1 visitor1 = new Visitor1();
        Visitor2 visitor2 = new Visitor2();
        Visitor3 visitor3 = new Visitor3();

        NodesTraverser traverser = new NodesTraverserIterated(nodes.iterator());
        traverser.addVisitor(visitor1);
        traverser.addVisitor(visitor2);
        traverser.addVisitor(visitor3);

        traverser.traverse();

        for(int i = 0; i < nodes.size(); ++i) {
            ReviewNode node = nodes.get(i);
            assertThat(visitor1.mSubjects.get(i), is(node.getSubject().getSubject() + "1"));
            assertThat(visitor2.mSubjects.get(i), is(node.getSubject().getSubject() + "2"));
            assertThat(visitor3.mSubjects.get(i), is(node.getSubject().getSubject() + "3"));
        }
    }

    @NonNull
    private ArrayList<ReviewNode> getNodes() {
        ArrayList<ReviewNode> nodes = new ArrayList<>();
        nodes.add(RandomReview.nextReviewNode());
        nodes.add(RandomReview.nextReviewNode());
        nodes.add(RandomReview.nextReviewNode());
        nodes.add(RandomReview.nextReviewNode());
        nodes.add(RandomReview.nextReviewNode());
        return nodes;
    }

    private class Visitor1 implements VisitorReviewNode {
        private ArrayList<String> mSubjects = new ArrayList<>();
        @Override
        public void visit(@NonNull ReviewNode reviewNode) {
            mSubjects.add(reviewNode.getSubject().getSubject() + "1");
        }


    }

    private class Visitor2 implements VisitorReviewNode {
        private ArrayList<String> mSubjects = new ArrayList<>();
        @Override
        public void visit(@NonNull ReviewNode reviewNode) {
            mSubjects.add(reviewNode.getSubject().getSubject() + "2");
        }
    }

    private class Visitor3 implements VisitorReviewNode {
        private ArrayList<String> mSubjects = new ArrayList<>();
        @Override
        public void visit(@NonNull ReviewNode reviewNode) {
            mSubjects.add(reviewNode.getSubject().getSubject() + "3");
        }
    }
}
