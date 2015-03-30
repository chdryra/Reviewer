/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer.View;

import java.util.HashMap;

/**
 * Created by: Rizwan Choudrey
 * On: 21/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Defines the adder, editor and display UIs to use with each data type.
 */
public final class ConfigGvDataAddEdit {
    private static ConfigGvDataAddEdit sConfig;
    private final HashMap<GvDataType, AddEditUis> mMap = new HashMap<>();

    private ConfigGvDataAddEdit() {
        mMap.put(GvTagList.TYPE, new AddEditUis(AddTag.class, EditTag.class));
        mMap.put(GvChildList.TYPE, new AddEditUis(AddChild.class, EditChild.class));
        mMap.put(GvCommentList.TYPE, new AddEditUis(AddComment.class, EditComment.class));
        mMap.put(GvImageList.TYPE, new AddEditUis(null, EditImage.class));
        mMap.put(GvFactList.TYPE, new AddEditUis(AddFact.class, EditFact.class));
        mMap.put(GvLocationList.TYPE, new AddEditUis(AddLocation.class, EditLocation.class));
        mMap.put(GvUrlList.TYPE, new AddEditUis(ActivityEditUrlBrowser.class,
                ActivityEditUrlBrowser.class));
    }

    public static Class<? extends LaunchableUi> getAddClass(GvDataType dataType) {
        return get().mMap.get(dataType).getAddClass();
    }

    public static Class<? extends LaunchableUi> getEditClass(GvDataType dataType) {
        return get().mMap.get(dataType).getEditClass();
    }

    private static ConfigGvDataAddEdit get() {
        if (sConfig == null) sConfig = new ConfigGvDataAddEdit();

        return sConfig;
    }

    //Adders
    //Need these subclasses as can't programmatically instantiate classes that utilise generics.

    //Tag
    public static class AddTag extends DialogAddGvData<GvTagList.GvTag> {
        public AddTag() {
            super(GvTagList.class);
        }
    }

    //Child
    public static class AddChild extends
            DialogAddGvData<GvChildList.GvChildReview> {
        public AddChild() {
            super(GvChildList.class);
        }
    }

    //Comment
    public static class AddComment extends DialogAddGvData<GvCommentList.GvComment> {
        public AddComment() {
            super(GvCommentList.class);
        }
    }

    //Fact
    public static class AddFact extends DialogAddGvData<GvFactList.GvFact> {
        public AddFact() {
            super(GvFactList.class);
        }
    }

    //Location
    public static class AddLocation extends DialogAddGvData<GvLocationList.GvLocation> {
        public AddLocation() {
            super(GvLocationList.class);
        }
    }

    //Editors
    //Tag
    public static class EditTag extends DialogEditGvData<GvTagList.GvTag> {
        public EditTag() {
            super(GvTagList.class);
        }
    }

    //Child
    public static class EditChild extends DialogEditGvData<GvChildList.GvChildReview> {
        public EditChild() {
            super(GvChildList.class);
        }
    }

    //Comment
    public static class EditComment extends DialogEditGvData<GvCommentList.GvComment> {
        public EditComment() {
            super(GvCommentList.class);
        }
    }

    //Image
    public static class EditImage extends DialogEditGvData<GvImageList.GvImage> {
        public EditImage() {
            super(GvImageList.class);
        }
    }

    //Fact
    public static class EditFact extends DialogEditGvData<GvFactList.GvFact> {
        public EditFact() {
            super(GvFactList.class);
        }
    }

    //Location
    public static class EditLocation extends DialogEditGvData<GvLocationList.GvLocation> {
        public EditLocation() {
            super(GvLocationList.class);
        }
    }

    /**
     * Packages together an add and edit UI.
     */
    class AddEditUis {
        private final Class<? extends LaunchableUi> mAdd;
        private final Class<? extends LaunchableUi> mEdit;

        private AddEditUis(Class<? extends LaunchableUi> add,
                Class<? extends LaunchableUi> edit) {
            mAdd = add;
            mEdit = edit;
        }

        Class<? extends LaunchableUi> getAddClass() {
            return mAdd;
        }

        Class<? extends LaunchableUi> getEditClass() {
            return mEdit;
        }
    }
}
