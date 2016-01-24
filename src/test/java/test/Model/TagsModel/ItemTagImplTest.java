/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.TagsModel;

import com.chdryra.android.reviewer.Model.Implementation.TagsModel.ItemTagImpl;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ItemTagImplTest {
    @Test
    public void constructorCapitalisesTag() {
        String tagString = "tag";
        String id = "id";
        ItemTag tag = new ItemTagImpl(tagString, id);
        assertThat(tag.getTag(), is("Tag"));
    }

    @Test
    public void testAddItemIdAndGetItemIds() {
        String tagString = "tag";
        String id0 = "id0";
        ItemTagImpl tag = new ItemTagImpl(tagString, id0);

        assertThat(tag.getItemIds().size(), is(1));

        String id1 = "id1";
        String id2 = "id2";
        tag.addItemId(id1);
        tag.addItemId(id2);

        assertThat(tag.getItemIds().size(), is(3));
        assertThat(tag.getItemIds().get(0), is(id0));
        assertThat(tag.getItemIds().get(1), is(id1));
        assertThat(tag.getItemIds().get(2), is(id2));
    }

    @Test
    public void testTagsItemReturnsCorrectBoolean() {
        String tagString = "tag";
        String id0 = "id0";
        ItemTagImpl tag = new ItemTagImpl(tagString, id0);

        String id1 = "id1";
        String id2 = "id2";
        tag.addItemId(id1);

        assertThat(tag.tagsItem(id0), is(true));
        assertThat(tag.tagsItem(id1), is(true));
        assertThat(tag.tagsItem(id2), is(false));
    }

    @Test
    public void testRemoveItemRemovesOnlySpecifiedItem() {
        String tagString = "tag";
        String id0 = "id0";
        String id1 = "id1";
        String id2 = "id2";

        ItemTagImpl tag = new ItemTagImpl(tagString, id0);
        tag.addItemId(id1);
        tag.addItemId(id2);

        assertThat(tag.getItemIds().size(), is(3));
        assertThat(tag.tagsItem(id0), is(true));
        assertThat(tag.tagsItem(id1), is(true));
        assertThat(tag.tagsItem(id2), is(true));

        tag.removeItemId(id1);

        assertThat(tag.getItemIds().size(), is(2));
        assertThat(tag.tagsItem(id0), is(true));
        assertThat(tag.tagsItem(id1), is(false));
        assertThat(tag.tagsItem(id2), is(true));
    }

    @Test
    public void testCompareToSameCase() {
        String tagString1 = "tag";
        String tagString2 = "tag";
        String id1 = "id1";
        String id2 = "id2";
        ItemTagImpl tag1 = new ItemTagImpl(tagString1, id1);
        ItemTagImpl tag2 = new ItemTagImpl(tagString2, id2);

        assertThat(tag1.compareTo(tag2), is(0));
    }

    @Test
    public void testCompareToIgnoresCase() {
        String tagStringLower = "tag";
        String tagStringUpper = "Tag";
        String id = "id0";
        ItemTagImpl tagLower = new ItemTagImpl(tagStringLower, id);
        ItemTagImpl tagUpper = new ItemTagImpl(tagStringUpper, id);

        assertThat(tagLower.compareTo(tagUpper), is(0));
    }

    @Test
    public void testCompareToAlphabetical() {
        String tagStringAbc = "abc";
        String tagStringCdf = "def";
        String id = "id";
        ItemTagImpl tagAbc = new ItemTagImpl(tagStringAbc, id);
        ItemTagImpl tagCdf = new ItemTagImpl(tagStringCdf, id);

        assertThat(tagAbc.compareTo(tagCdf), lessThan(0));
        assertThat(tagCdf.compareTo(tagAbc), greaterThan(0));
    }
}
