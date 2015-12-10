package test.Model.TreeMethods;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Factories.FactoryTagsManager;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation.TagsGetter;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import test.TestUtils.RandomReview;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 10/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TagsGetterTest {
    TagsManager mTagsManager;
    ArrayList<String> mTags;

    @Before
    public void setup() {
        FactoryTagsManager factory = new FactoryTagsManager();
        mTagsManager = factory.newTagsManager();
        mTags= new ArrayList<>();
        mTags.add(RandomString.nextWordLowerCase());
        mTags.add(RandomString.nextWordLowerCase());
        mTags.add(RandomString.nextWordLowerCase());
        mTags.add(RandomString.nextWordLowerCase());
    }

    @Test
    public void getDataReturnsListOfTagsForReviewNode() {
        ReviewNode node = RandomReview.nextReviewNode(false);
        mTagsManager.tagItem(node.getReviewId(), mTags);

        TagsGetter getter = new TagsGetter(mTagsManager);
        IdableList<DataTag> tags = getter.getData(node);

        assertThat(tags.size(), is(mTags.size()));
        for(DataTag tag : tags) {
            assertThat(tag.getReviewId(), is(node.getReviewId()));
            String tagString = tag.getTag().toLowerCase();
            assertThat(mTags.contains(tagString), is(true));
            mTags.remove(tagString);
        }
    }

    @Test
    public void getDataReturnsEmptyListOfTagsForUntaggedReviewNode() {
        ReviewNode node = RandomReview.nextReviewNode(false);
        TagsGetter getter = new TagsGetter(mTagsManager);
        IdableList<DataTag> tags = getter.getData(node);
        assertThat(tags.size(), is(0));
    }

}
