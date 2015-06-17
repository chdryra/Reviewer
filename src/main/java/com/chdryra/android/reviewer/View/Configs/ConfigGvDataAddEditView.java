/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer.View.Configs;

import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityEditLocationMap;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityEditUrlBrowser;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataEdit;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataView;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;

import java.util.HashMap;

/**
 * Created by: Rizwan Choudrey
 * On: 21/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Defines the adder, editor and display UIs to use with each data type.
 */
public final class ConfigGvDataAddEditView {
    private static ConfigGvDataAddEditView sConfig;
    private final HashMap<GvDataType, AddEditViewUis> mMap = new HashMap<>();

    private ConfigGvDataAddEditView() {
        mMap.put(GvTagList.TYPE, new AddEditViewUis(AddTag.class, EditTag.class, ViewTag.class));
        mMap.put(GvChildList.TYPE, new AddEditViewUis(AddChild.class, EditChild.class, ViewChild
                .class));
        mMap.put(GvCommentList.TYPE, new AddEditViewUis(AddComment.class, EditComment.class,
                ViewComment.class));
        mMap.put(GvImageList.TYPE, new AddEditViewUis(null, EditImage.class, ViewImage.class));
        mMap.put(GvFactList.TYPE, new AddEditViewUis(AddFact.class, EditFact.class, ViewFact
                .class));
        mMap.put(GvLocationList.TYPE, new AddEditViewUis(AddLocation.class, EditLocation.class,
                ActivityEditLocationMap.class));
        mMap.put(GvUrlList.TYPE, new AddEditViewUis(ActivityEditUrlBrowser.class,
                ActivityEditUrlBrowser.class, ActivityEditUrlBrowser.class));
    }

    public static Class<? extends LaunchableUi> getAddClass(GvDataType dataType) {
        return get().mMap.get(dataType).getAddClass();
    }

    public static Class<? extends LaunchableUi> getEditClass(GvDataType dataType) {
        return get().mMap.get(dataType).getEditClass();
    }

    public static Class<? extends LaunchableUi> getViewClass(GvDataType dataType) {
        return get().mMap.get(dataType).getViewClass();
    }

    private static ConfigGvDataAddEditView get() {
        if (sConfig == null) sConfig = new ConfigGvDataAddEditView();

        return sConfig;
    }

    //Adders
    //Need these subclasses as can't programmatically instantiate classes that utilise generics.

    //Tag
    public static class AddTag extends DialogGvDataAdd<GvTagList.GvTag> {
        public AddTag() {
            super(GvTagList.GvTag.class);
        }
    }

    //Child
    public static class AddChild extends
            DialogGvDataAdd<GvChildList.GvChildReview> {
        public AddChild() {
            super(GvChildList.GvChildReview.class);
        }
    }

    //Comment
    public static class AddComment extends DialogGvDataAdd<GvCommentList.GvComment> {
        public AddComment() {
            super(GvCommentList.GvComment.class);
        }
    }

    //Fact
    public static class AddFact extends DialogGvDataAdd<GvFactList.GvFact> {
        public AddFact() {
            super(GvFactList.GvFact.class);
        }
    }

    //Location
    public static class AddLocation extends DialogGvDataAdd<GvLocationList.GvLocation> {
        public AddLocation() {
            super(GvLocationList.GvLocation.class);
        }
    }

    //Editors
    //Tag
    public static class EditTag extends DialogGvDataEdit<GvTagList.GvTag> {
        public EditTag() {
            super(GvTagList.GvTag.class);
        }
    }

    //Child
    public static class EditChild extends DialogGvDataEdit<GvChildList.GvChildReview> {
        public EditChild() {
            super(GvChildList.GvChildReview.class);
        }
    }

    //Comment
    public static class EditComment extends DialogGvDataEdit<GvCommentList.GvComment> {
        public EditComment() {
            super(GvCommentList.GvComment.class);
        }
    }

    //Image
    public static class EditImage extends DialogGvDataEdit<GvImageList.GvImage> {
        public EditImage() {
            super(GvImageList.GvImage.class);
        }
    }

    //Fact
    public static class EditFact extends DialogGvDataEdit<GvFactList.GvFact> {
        public EditFact() {
            super(GvFactList.GvFact.class);
        }
    }

    //Location
    public static class EditLocation extends DialogGvDataEdit<GvLocationList.GvLocation> {
        public EditLocation() {
            super(GvLocationList.GvLocation.class);
        }
    }

    //Viewers
    //Tag
    public static class ViewTag extends DialogGvDataView<GvTagList.GvTag> {
        public ViewTag() {
            super(GvTagList.GvTag.class);
        }
    }

    //Child
    public static class ViewChild extends DialogGvDataView<GvChildList.GvChildReview> {
        public ViewChild() {
            super(GvChildList.GvChildReview.class);
        }
    }

    //Comment
    public static class ViewComment extends DialogGvDataView<GvCommentList.GvComment> {
        public ViewComment() {
            super(GvCommentList.GvComment.class);
        }
    }

    //Image
    public static class ViewImage extends DialogGvDataView<GvImageList.GvImage> {
        public ViewImage() {
            super(GvImageList.GvImage.class);
        }
    }

    //Fact
    public static class ViewFact extends DialogGvDataView<GvFactList.GvFact> {
        public ViewFact() {
            super(GvFactList.GvFact.class);
        }
    }

    //Location
    public static class ViewLocation extends DialogGvDataView<GvLocationList.GvLocation> {
        public ViewLocation() {
            super(GvLocationList.GvLocation.class);
        }
    }

    /**
     * Packages together an add and edit UI.
     */
    class AddEditViewUis {
        private final Class<? extends LaunchableUi> mAdd;
        private final Class<? extends LaunchableUi> mEdit;
        private final Class<? extends LaunchableUi> mView;

        private AddEditViewUis(Class<? extends LaunchableUi> add,
                Class<? extends LaunchableUi> edit, Class<? extends LaunchableUi> view) {
            mAdd = add;
            mEdit = edit;
            mView = view;
        }

        Class<? extends LaunchableUi> getAddClass() {
            return mAdd;
        }

        Class<? extends LaunchableUi> getEditClass() {
            return mEdit;
        }

        Class<? extends LaunchableUi> getViewClass() {
            return mView;
        }
    }
}
