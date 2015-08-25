package com.chdryra.android.reviewer.test.Adapter.ReviewAdapterModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.TagCollector;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.Tagging.TagsManager;
import com.chdryra.android.reviewer.Model.TreeMethods.VisitorTreeFlattener;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 25/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TagCollectorTest extends TestCase {
    @SmallTest
    public void testCollectTags() {
        GvTagList tags = GvDataMocker.newTagList(5, false);
        ReviewNode node = ReviewMocker.newReviewNode(false);
        GvTagList appliedTags = new GvTagList();
        Random rand = new Random();
        for (ReviewNode tagged : VisitorTreeFlattener.flatten(node.getParent())) {
            int numTags = rand.nextInt(5) + 1;
            GvTagList tagsAvailable = new GvTagList(tags);
            for (int i = 0; i < numTags; ++i) {
                int index = rand.nextInt(tagsAvailable.size());
                GvTagList.GvTag tagToApply = tagsAvailable.getItem(index);
                TagsManager.tag(tagged.getId(), tagToApply.get());
                appliedTags.add(tagToApply);
                tagsAvailable.remove(tagToApply);
            }
        }

        TagCollector collector = new TagCollector(node.getParent());
        GvTagList collected = collector.collectTags();
        assertEquals(appliedTags.size(), collected.size());
        appliedTags.sort();
        collected.sort();
        for (int i = 0; i < appliedTags.size(); ++i) {
            assertEquals(appliedTags.getItem(i).get(), collected.getItem(i).get());
        }
    }
}
