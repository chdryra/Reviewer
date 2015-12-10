package test.Model.TagsModel;

import com.chdryra.android.reviewer.Model.Implementation.TagsModel.TagsManagerImpl;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

import org.apache.commons.lang3.text.WordUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TagsManagerImplTest {
    private TagsManager mManager;

    @Before
    public void setup() {
        mManager = new TagsManagerImpl();
    }

    @Test
    public void newInstanceHasNoTags() {
        assertThat(mManager.getTags().size(), is(0));
    }

    @Test
    public void tagItemWithNewTagAddsCorrectItemTag() {
        String id = "abc";
        String tag = "tag";

        mManager.tagItem(id, tag);

        ItemTagCollection tags = mManager.getTags();
        assertThat(tags.size(), is(1));
        ItemTag itemTag = tags.getItemTag(0);
        assertThat(itemTag.getTag(), is(WordUtils.capitalize(tag)));
        assertThat(itemTag.getItemIds().size(), is(1));
        assertThat(itemTag.getItemIds().get(0), is(id));
    }
}
