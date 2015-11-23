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
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataEdit;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataView;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvComment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvCriterion;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDate;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvFact;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvLocation;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvSubject;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvTag;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvUrl;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;

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
    private final HashMap<GvDataType, ClassesHolder> mMap = new HashMap<>();

    public ClassesAddEditViewDefault() {
        mMap.put(GvTag.TYPE,
                new ClassesHolder(AddTag.class, EditTag.class, ViewTag.class));
        mMap.put(GvCriterion.TYPE,
                new ClassesHolder(AddChild.class, EditChild.class, ViewChild.class));
        mMap.put(GvComment.TYPE,
                new ClassesHolder(AddComment.class, EditComment.class, ViewComment.class));
        mMap.put(GvImage.TYPE,
                new ClassesHolder(null, EditImage.class, ViewImage.class));
        mMap.put(GvFact.TYPE,
                new ClassesHolder(AddFact.class, EditFact.class, ViewFact.class));
        mMap.put(GvLocation.TYPE,
                new ClassesHolder(AddLocation.class, EditLocation.class,
                        ActivityViewLocation.class));
        mMap.put(GvUrl.TYPE,
                new ClassesHolder(ActivityEditUrlBrowser.class, ActivityEditUrlBrowser.class,
                        ActivityEditUrlBrowser.class));
        mMap.put(GvSubject.TYPE,
                new ClassesHolder(null, null, ViewSubject.class));
        mMap.put(GvDate.TYPE,
                new ClassesHolder(null, null, ViewDate.class));
    }

    @Override
    public ClassesHolder getUiClasses(GvDataType<? extends GvData> dataType) {
        return mMap.get(dataType);
    }

    //Adders
    //Need these subclasses as can't programmatically instantiate classes that utilise generics.

    //Classes
    //Tag
    public static class AddTag extends DialogGvDataAdd<GvTag> {
        //Constructors
        public AddTag() {
            super(GvTag.TYPE);
        }
    }

    //Child
    public static class AddChild extends
            DialogGvDataAdd<GvCriterion> {
        //Constructors
        public AddChild() {
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
    public static class EditChild extends DialogGvDataEdit<GvCriterion> {
        //Constructors
        public EditChild() {
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
    public static class ViewChild extends DialogGvDataView<GvCriterion> {
        //Constructors
        public ViewChild() {
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
