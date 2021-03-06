/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.TagsModel;

import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.TagsModel.Implementation.TagsManagerImpl;
import com.chdryra.android.corelibrary.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.corelibrary.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.corelibrary.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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
    public void tagItemWithNewTagAddsNewItemTag() {
        String id = RandomString.nextWord();
        String tag = RandomString.nextWord();

        mManager.tagItem(id, tag);

        ItemTagCollection tags = mManager.getTags();
        assertThat(tags.size(), is(1));
        ItemTag itemTag = tags.getItemTag(0);
        assertThat(itemTag.isTag(tag), is(true));
        assertThat(itemTag.getItemIds().size(), is(1));
        assertThat(itemTag.getItemIds().get(0), is(id));
    }

    @Test
    public void tagItemWithExistingTagAddsItemIdToExistingItemTag() {
        String id1 = RandomString.nextWord();
        String id2 = RandomString.nextWord();
        String tag = RandomString.nextWord();

        mManager.tagItem(id1, tag);
        mManager.tagItem(id2, tag);
        ItemTagCollection tags = mManager.getTags();

        assertThat(tags.size(), is(1));
        ItemTag itemTag = tags.getItemTag(0);
        assertThat(itemTag.getItemIds().size(), is(2));
        assertThat(itemTag.getItemIds().get(0), is(id1));
        assertThat(itemTag.getItemIds().get(1), is(id2));
    }

    @Test
    public void tagItemWithArrayOfNewTagsCreatesNewItemTags() {
        ArrayList<String> tags = getTags();

        String id = RandomString.nextWord();
        mManager.tagItem(id, tags);

        ItemTagCollection tagsCollection = mManager.getTags();
        checkInArrayOfTags(tags, tagsCollection);
        for (ItemTag tag : tagsCollection) {
            assertThat(tag.tagsItem(id), is(true));
        }
    }

    @Test
    public void tagItemWithArrayOfExistingTagsAddsItemIdToItemTags() {
        ArrayList<String> tags = getTags();

        String id1 = RandomString.nextWord();
        String id2 = RandomString.nextWord();
        mManager.tagItem(id1, tags);
        mManager.tagItem(id2, tags);

        ItemTagCollection tagsCollection = mManager.getTags();
        checkInArrayOfTags(tags, tagsCollection);
        for (ItemTag tag : tagsCollection) {
            assertThat(tag.tagsItem(id1), is(true));
            assertThat(tag.tagsItem(id2), is(true));
        }
    }

    @Test
    public void tagsItemReturnsTrueIfTagsItem() {
        String id1 = RandomString.nextWord();
        String id2 = RandomString.nextWord();
        String tag = RandomString.nextWord();

        assertThat(mManager.tagsItem(id1, tag), is(false));
        assertThat(mManager.tagsItem(id2, tag), is(false));

        mManager.tagItem(id1, tag);

        assertThat(mManager.tagsItem(id1, tag), is(true));
        assertThat(mManager.tagsItem(id2, tag), is(false));
    }

    @Test
    public void tagItemWithArrayOfTagsCreatesNewTagsOrAddsIdToExistingTagsAppropriately() {
        String idExisiting = "id1";
        String idAll = "id2";

        String new1 = "new1";
        String new2 = "new2";
        String exisiting1 = "exisiting1";
        String exisiting2 = "exisiting2";

        ArrayList<String> tags = new ArrayList<>();
        tags.add(new1);
        tags.add(exisiting1);
        tags.add(new2);
        tags.add(exisiting2);

        mManager.tagItem(idExisiting, exisiting1);
        mManager.tagItem(idExisiting, exisiting2);
        mManager.tagItem(idAll, tags);

        ItemTagCollection tagsCollection = mManager.getTags();
        checkInArrayOfTags(tags, tagsCollection);
        checkCorrectItemIdsTagged(idExisiting, idAll, new1, new2, exisiting1, exisiting2,
                tagsCollection);
    }

    @Test
    public void clearTagsRemovesItemIdFromTags() {
        String id1 = "abc";
        String id2 = "def";
        String id3 = "ghi";
        String tag1 = "tag1";
        String tag2 = "tag2";

        mManager.tagItem(id1, tag1);
        mManager.tagItem(id2, tag1);
        mManager.tagItem(id1, tag2);
        mManager.tagItem(id3, tag2);

        mManager.clearTags(id1);
        ItemTagCollection tags = mManager.getTags();
        assertThat(tags.size(), is(2));
        ItemTag itemTag = tags.getItemTag(0);
        assertThat(itemTag.tagsItem(id1), is(false));
        assertThat(itemTag.tagsItem(id2), is(true));

        itemTag = tags.getItemTag(1);
        assertThat(itemTag.tagsItem(id1), is(false));
        assertThat(itemTag.tagsItem(id3), is(true));
    }

    @Test
    public void clearTagsRemovesItemTagIfNothingElseTaggedWithThatItemTag() {
        String id1 = "abc";
        String id2 = "def";
        String tag = "tag";

        mManager.tagItem(id1, tag);
        mManager.tagItem(id2, tag);
        ItemTagCollection tags = mManager.getTags();
        assertThat(tags.size(), is(1));
        mManager.clearTags(id1);

        tags = mManager.getTags();
        assertThat(tags.size(), is(1));
        mManager.clearTags(id2);

        tags = mManager.getTags();
        assertThat(tags.size(), is(0));
    }

    @Test
    public void getTagsReturnsAllTags() {
        ArrayList<String> tags1 = getTags();
        String id1 = "id1";

        mManager.tagItem(id1, tags1);
        ItemTagCollection tagsCollection = mManager.getTags();

        checkInArrayOfTags(tags1, tagsCollection);

        ArrayList<String> tags2 = getTags();
        String id2 = "id1";
        mManager.tagItem(id2, tags2);
        tagsCollection = mManager.getTags();
        ArrayList<String> allTags = new ArrayList<>(tags1);
        allTags.addAll(tags2);
        checkInArrayOfTags(allTags, tagsCollection);
    }

    @Test
    public void getTagsForGivenIdReturnsCorrectTags() {
        ArrayList<String> tags = getTags();
        ArrayList<String> id2Tags = new ArrayList<>();
        id2Tags.add(tags.get(2));
        id2Tags.add(tags.get(3));

        String id1 = "id1";
        String id2 = "id2";
        mManager.tagItem(id1, tags);
        mManager.tagItem(id2, id2Tags);

        ItemTagCollection tagCollection = mManager.getTags(id2);
        checkInArrayOfTags(id2Tags, tagCollection);
        for (ItemTag tag : tagCollection) {
            assertThat(tag.tagsItem(id2), is(true));
        }
    }

    @NonNull
    private ArrayList<String> getTags() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(RandomString.nextWordLowerCase());
        tags.add(RandomString.nextWordLowerCase());
        tags.add(RandomString.nextWordLowerCase());
        tags.add(RandomString.nextWordLowerCase());
        return tags;
    }

    private void checkCorrectItemIdsTagged(String idExisiting, String idAll, String new1, String
            new2, String exisiting1, String exisiting2, ItemTagCollection tagsCollection) {
        for (ItemTag tag : tagsCollection) {
            if (tag.isTag(new1) || tag.isTag(new2)) {
                assertThat(tag.getItemIds().size(), is(1));
                assertThat(tag.tagsItem(idAll), is(true));
            } else if (tag.isTag(exisiting1) || tag.isTag(exisiting2)) {
                assertThat(tag.getItemIds().size(), is(2));
                assertThat(tag.tagsItem(idExisiting), is(true));
                assertThat(tag.tagsItem(idAll), is(true));
            }
        }
    }

    private void checkInArrayOfTags(ArrayList<String> tags, ItemTagCollection tagsCollection) {
        assertThat(tagsCollection.size(), is(tags.size()));
        ArrayList<String> tagsLeft = new ArrayList<>(tags);
        for (ItemTag tag : tagsCollection) {
            String tagString = tag.getTag().toLowerCase();
            assertThat(tagsLeft.contains(tagString), is(true));
            tagsLeft.remove(tagString);
        }
    }
}
