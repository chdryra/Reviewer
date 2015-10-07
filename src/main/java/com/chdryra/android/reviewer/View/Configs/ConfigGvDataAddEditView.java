/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer.View.Configs;

import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityEditUrlBrowser;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityViewReviewLocation;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataEdit;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataView;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;
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
        mMap.put(GvTagList.GvTag.TYPE,
                new AddEditViewUis(AddTag.class, EditTag.class, ViewTag.class));
        mMap.put(GvCriterionList.GvCriterion.TYPE,
                new AddEditViewUis(AddChild.class, EditChild.class, ViewChild.class));
        mMap.put(GvCommentList.GvComment.TYPE,
                new AddEditViewUis(AddComment.class, EditComment.class, ViewComment.class));
        mMap.put(GvImageList.GvImage.TYPE,
                new AddEditViewUis(null, EditImage.class, ViewImage.class));
        mMap.put(GvFactList.GvFact.TYPE,
                new AddEditViewUis(AddFact.class, EditFact.class, ViewFact.class));
        mMap.put(GvLocationList.GvLocation.TYPE,
                new AddEditViewUis(AddLocation.class, EditLocation.class,
                        ActivityViewReviewLocation.class));
        mMap.put(GvUrlList.GvUrl.TYPE,
                new AddEditViewUis(ActivityEditUrlBrowser.class, ActivityEditUrlBrowser.class,
                        ActivityEditUrlBrowser.class));
        mMap.put(GvSubjectList.GvSubject.TYPE,
                new AddEditViewUis(null, null, ViewSubject.class));
        mMap.put(GvDateList.GvDate.TYPE,
                new AddEditViewUis(null, null, ViewDate.class));
    }

    //Static methods
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

    //Classes
    //Tag
    public static class AddTag extends DialogGvDataAdd<GvTagList.GvTag> {
        //Constructors
        public AddTag() {
            super(GvTagList.GvTag.TYPE);
        }
    }

    //Child
    public static class AddChild extends
            DialogGvDataAdd<GvCriterionList.GvCriterion> {
        //Constructors
        public AddChild() {
            super(GvCriterionList.GvCriterion.TYPE);
        }
    }

    //Comment
    public static class AddComment extends DialogGvDataAdd<GvCommentList.GvComment> {
        //Constructors
        public AddComment() {
            super(GvCommentList.GvComment.TYPE);
        }
    }

    //Fact
    public static class AddFact extends DialogGvDataAdd<GvFactList.GvFact> {
        //Constructors
        public AddFact() {
            super(GvFactList.GvFact.TYPE);
        }
    }

    //Location
    public static class AddLocation extends DialogGvDataAdd<GvLocationList.GvLocation> {
        //Constructors
        public AddLocation() {
            super(GvLocationList.GvLocation.TYPE);
        }
    }

    //Editors
    //Tag
    public static class EditTag extends DialogGvDataEdit<GvTagList.GvTag> {
        //Constructors
        public EditTag() {
            super(GvTagList.GvTag.TYPE);
        }
    }

    //Child
    public static class EditChild extends DialogGvDataEdit<GvCriterionList.GvCriterion> {
        //Constructors
        public EditChild() {
            super(GvCriterionList.GvCriterion.TYPE);
        }
    }

    //Comment
    public static class EditComment extends DialogGvDataEdit<GvCommentList.GvComment> {
        //Constructors
        public EditComment() {
            super(GvCommentList.GvComment.TYPE);
        }
    }

    //Image
    public static class EditImage extends DialogGvDataEdit<GvImageList.GvImage> {
        //Constructors
        public EditImage() {
            super(GvImageList.GvImage.TYPE);
        }
    }

    //Fact
    public static class EditFact extends DialogGvDataEdit<GvFactList.GvFact> {
        //Constructors
        public EditFact() {
            super(GvFactList.GvFact.TYPE);
        }
    }

    //Location
    public static class EditLocation extends DialogGvDataEdit<GvLocationList.GvLocation> {
        //Constructors
        public EditLocation() {
            super(GvLocationList.GvLocation.TYPE);
        }
    }

    //Viewers
    //Tag
    public static class ViewTag extends DialogGvDataView<GvTagList.GvTag> {
        //Constructors
        public ViewTag() {
            super(GvTagList.GvTag.TYPE);
        }
    }

    //Child
    public static class ViewChild extends DialogGvDataView<GvCriterionList.GvCriterion> {
        //Constructors
        public ViewChild() {
            super(GvCriterionList.GvCriterion.TYPE);
        }
    }

    //Comment
    public static class ViewComment extends DialogGvDataView<GvCommentList.GvComment> {
        //Constructors
        public ViewComment() {
            super(GvCommentList.GvComment.TYPE);
        }
    }

    //Image
    public static class ViewImage extends DialogGvDataView<GvImageList.GvImage> {
        //Constructors
        public ViewImage() {
            super(GvImageList.GvImage.TYPE);
        }
    }

    //Fact
    public static class ViewFact extends DialogGvDataView<GvFactList.GvFact> {
        //Constructors
        public ViewFact() {
            super(GvFactList.GvFact.TYPE);
        }
    }

    //Location
    public static class ViewLocation extends DialogGvDataView<GvLocationList.GvLocation> {
        //Constructors
        public ViewLocation() {
            super(GvLocationList.GvLocation.TYPE);
        }
    }

    //Subject
    public static class ViewSubject extends DialogGvDataView<GvSubjectList.GvSubject> {
        //Constructors
        public ViewSubject() {
            super(GvSubjectList.GvSubject.TYPE);
        }
    }

    //Date
    public static class ViewDate extends DialogGvDataView<GvDateList.GvDate> {
        //Constructors
        public ViewDate() {
            super(GvDateList.GvDate.TYPE);
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
                               Class<? extends LaunchableUi> edit, Class<? extends LaunchableUi>
                                       view) {
            mAdd = add;
            mEdit = edit;
            mView = view;
        }

        //package private methods
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
