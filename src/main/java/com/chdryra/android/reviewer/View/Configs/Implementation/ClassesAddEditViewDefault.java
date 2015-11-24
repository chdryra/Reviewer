/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer.View.Configs.Implementation;

import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityEditUrlBrowser;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityViewLocation;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ClassesAddEditView;
import com.chdryra.android.reviewer.View.Dialogs.Implementation.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.Dialogs.Implementation.DialogGvDataEdit;
import com.chdryra.android.reviewer.View.Dialogs.Implementation.DialogGvDataView;
import com.chdryra.android.reviewer.View.Dialogs.Implementation.AddEditComment;
import com.chdryra.android.reviewer.View.Dialogs.Implementation.AddEditCriterion;
import com.chdryra.android.reviewer.View.Dialogs.Implementation.AddEditFact;
import com.chdryra.android.reviewer.View.Dialogs.Implementation.AddEditTag;
import com.chdryra.android.reviewer.View.Dialogs.Implementation.LayoutEditImage;
import com.chdryra.android.reviewer.View.Dialogs.Implementation.LayoutEditLocation;
import com.chdryra.android.reviewer.View.Dialogs.Implementation.ViewLayoutComment;
import com.chdryra.android.reviewer.View.Dialogs.Implementation.ViewLayoutCriterion;
import com.chdryra.android.reviewer.View.Dialogs.Implementation.ViewLayoutDate;
import com.chdryra.android.reviewer.View.Dialogs.Implementation.ViewLayoutFact;
import com.chdryra.android.reviewer.View.Dialogs.Implementation.ViewLayoutImage;
import com.chdryra.android.reviewer.View.Dialogs.Implementation.ViewLayoutSubject;
import com.chdryra.android.reviewer.View.Dialogs.Implementation.ViewLayoutTag;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvComment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvCriterion;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDate;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvFact;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvLocation;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvSubject;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvTag;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvUrl;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

import java.util.HashMap;

/**
 * Created by: Rizwan Choudrey
 * On: 21/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Defines the adder, editor and display UIs to use with each data type.
 */
public final class ClassesAddEditViewDefault implements ClassesAddEditView {
    private final HashMap<GvDataType<?>, ClassesHolder<? extends GvData>> mMap = new HashMap<>();

    public ClassesAddEditViewDefault() {
        mMap.put(GvTag.TYPE,
                new ClassesHolder<>(GvTag.TYPE,
                        AddTag.class, EditTag.class, ViewTag.class,
                        ViewLayoutTag.class, AddEditTag.class));

        mMap.put(GvCriterion.TYPE,
                new ClassesHolder<>(GvCriterion.TYPE,
                        AddCriterion.class, EditCriterion.class, ViewCriterion.class,
                        ViewLayoutCriterion.class, AddEditCriterion.class));

        mMap.put(GvComment.TYPE,
                new ClassesHolder<>(GvComment.TYPE,
                        AddComment.class, EditComment.class, ViewComment.class,
                        ViewLayoutComment.class, AddEditComment.class));

        mMap.put(GvImage.TYPE,
                new ClassesHolder<>(GvImage.TYPE,
                        null, EditImage.class, ViewImage.class,
                        ViewLayoutImage.class, LayoutEditImage.class));

        mMap.put(GvFact.TYPE,
                new ClassesHolder<>(GvFact.TYPE,
                        AddFact.class, EditFact.class, ViewFact.class,
                        ViewLayoutFact.class, AddEditFact.class));

        mMap.put(GvLocation.TYPE,
                new ClassesHolder<>(GvLocation.TYPE,
                        AddLocation.class, EditLocation.class,
                        ActivityViewLocation.class, null, LayoutEditLocation.class));

        mMap.put(GvUrl.TYPE,
                new ClassesHolder<>(GvUrl.TYPE,
                        ActivityEditUrlBrowser.class, ActivityEditUrlBrowser.class,
                        ActivityEditUrlBrowser.class, null, null));

        mMap.put(GvSubject.TYPE,
                new ClassesHolder<>(GvSubject.TYPE,
                        null, null, ViewSubject.class, ViewLayoutSubject.class, null));

        mMap.put(GvDate.TYPE,
                new ClassesHolder<>(GvDate.TYPE,
                        null, null, ViewDate.class, ViewLayoutDate.class, null));
    }

    @Override
    public <T extends GvData> ClassesHolder<T> getUiClasses(GvDataType<T> dataType) {
        //TODO make type safe
        return (ClassesHolder<T>) mMap.get(dataType);
    }

    //Adders
    //Need these subclasses as can't programmatically instantiate classes that utilise generics.
    //Tag
    public static class AddTag extends DialogGvDataAdd<GvTag> {
        //Constructors
        public AddTag() {
            super(GvTag.TYPE);
        }
    }

    //Child
    public static class AddCriterion extends
            DialogGvDataAdd<GvCriterion> {
        //Constructors
        public AddCriterion() {
            super(GvCriterion.TYPE);
        }
    }

    //Comment
    public static class AddComment extends DialogGvDataAdd<GvComment> {
        //Constructors
        public AddComment() {
            super(GvComment.TYPE);
        }
    }

    //Fact
    public static class AddFact extends DialogGvDataAdd<GvFact> {
        //Constructors
        public AddFact() {
            super(GvFact.TYPE);
        }
    }

    //Location
    public static class AddLocation extends DialogGvDataAdd<GvLocation> {
        //Constructors
        public AddLocation() {
            super(GvLocation.TYPE);
        }
    }

    //Editors
    //Tag
    public static class EditTag extends DialogGvDataEdit<GvTag> {
        //Constructors
        public EditTag() {
            super(GvTag.TYPE);
        }
    }

    //Child
    public static class EditCriterion extends DialogGvDataEdit<GvCriterion> {
        //Constructors
        public EditCriterion() {
            super(GvCriterion.TYPE);
        }
    }

    //Comment
    public static class EditComment extends DialogGvDataEdit<GvComment> {
        //Constructors
        public EditComment() {
            super(GvComment.TYPE);
        }
    }

    //Image
    public static class EditImage extends DialogGvDataEdit<GvImage> {
        //Constructors
        public EditImage() {
            super(GvImage.TYPE);
        }
    }

    //Fact
    public static class EditFact extends DialogGvDataEdit<GvFact> {
        //Constructors
        public EditFact() {
            super(GvFact.TYPE);
        }
    }

    //Location
    public static class EditLocation extends DialogGvDataEdit<GvLocation> {
        //Constructors
        public EditLocation() {
            super(GvLocation.TYPE);
        }
    }

    //Viewers
    //Tag
    public static class ViewTag extends DialogGvDataView<GvTag> {
        //Constructors
        public ViewTag() {
            super(GvTag.TYPE);
        }
    }

    //Child
    public static class ViewCriterion extends DialogGvDataView<GvCriterion> {
        //Constructors
        public ViewCriterion() {
            super(GvCriterion.TYPE);
        }
    }

    //Comment
    public static class ViewComment extends DialogGvDataView<GvComment> {
        //Constructors
        public ViewComment() {
            super(GvComment.TYPE);
        }
    }

    //Image
    public static class ViewImage extends DialogGvDataView<GvImage> {
        //Constructors
        public ViewImage() {
            super(GvImage.TYPE);
        }
    }

    //Fact
    public static class ViewFact extends DialogGvDataView<GvFact> {
        //Constructors
        public ViewFact() {
            super(GvFact.TYPE);
        }
    }

    //Subject
    public static class ViewSubject extends DialogGvDataView<GvSubject> {
        //Constructors
        public ViewSubject() {
            super(GvSubject.TYPE);
        }
    }

    //Date
    public static class ViewDate extends DialogGvDataView<GvDate> {
        //Constructors
        public ViewDate() {
            super(GvDate.TYPE);
        }
    }

}
