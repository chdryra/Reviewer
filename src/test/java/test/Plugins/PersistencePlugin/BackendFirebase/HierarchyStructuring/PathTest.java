/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.HierarchyStructuring;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.BackendFirebase.Structuring.Path;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PathTest {
    @Test
    public void testStaticPathRootAndElements() {
        assertThat(Path.path("a", "b", "c", "d"), is("a/b/c/d"));
    }

    @Test
    public void testStaticPathRootOnly() {
        assertThat(Path.path("a"), is("a"));
    }

    @Test
    public void testStaticPathNoRootOneElementsOnly() {
        assertThat(Path.path("", "a"), is("a"));
    }

    @Test
    public void testStaticPathNoRootElementsOnly() {
        assertThat(Path.path("", "a", "b", "c", "d"), is("a/b/c/d"));
    }

    @Test
    public void testStaticPathNoRootNoElements() {
        assertThat(Path.path("", "", "", "", ""), is(""));
    }
}
