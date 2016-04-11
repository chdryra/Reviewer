/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

/**
 * Created by: Rizwan Choudrey
 * On: 25/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataDialogs {
    private GvDataDialogs() {}

    //Need these subclasses as can't programmatically instantiate classes that utilise generics.

    //Adders
    public static class AddTag extends DialogGvDataAdd<GvTag> {
        public AddTag() {
            super(GvTag.TYPE);
        }
    }

    public static class AddCriterion extends
            DialogGvDataAdd<GvCriterion> {
        public AddCriterion() {
            super(GvCriterion.TYPE);
        }
    }

    public static class AddComment extends DialogGvDataAdd<GvComment> {
        public AddComment() {
            super(GvComment.TYPE);
        }
    }

    public static class AddFact extends DialogGvDataAdd<GvFact> {
        public AddFact() {
            super(GvFact.TYPE);
        }
    }

    public static class AddLocation extends DialogGvDataAdd<GvLocation> {
        public AddLocation() {
            super(GvLocation.TYPE);
        }
    }

    //Editors
    public static class EditTag extends DialogGvDataEdit<GvTag> {
        public EditTag() {
            super(GvTag.TYPE);
        }
    }

    public static class EditCriterion extends DialogGvDataEdit<GvCriterion> {
        public EditCriterion() {
            super(GvCriterion.TYPE);
        }
    }

    public static class EditComment extends DialogGvDataEdit<GvComment> {
        public EditComment() {
            super(GvComment.TYPE);
        }
    }

    public static class EditImage extends DialogGvDataEdit<GvImage> {
        public EditImage() {
            super(GvImage.TYPE);
        }
    }

    public static class EditFact extends DialogGvDataEdit<GvFact> {
        public EditFact() {
            super(GvFact.TYPE);
        }
    }

    public static class EditLocation extends DialogGvDataEdit<GvLocation> {
        public EditLocation() {
            super(GvLocation.TYPE);
        }
    }

    //Viewers
    public static class ViewTag extends DialogGvDataView<GvTag> {
        public ViewTag() {
            super(GvTag.TYPE);
        }
    }

    public static class ViewCriterion extends DialogGvDataView<GvCriterion> {
        public ViewCriterion() {
            super(GvCriterion.TYPE);
        }
    }

    public static class ViewComment extends DialogGvDataView<GvComment> {
        public ViewComment() {
            super(GvComment.TYPE);
        }
    }

    public static class ViewImage extends DialogGvDataView<GvImage> {
        public ViewImage() {
            super(GvImage.TYPE);
        }
    }

    public static class ViewFact extends DialogGvDataView<GvFact> {
        public ViewFact() {
            super(GvFact.TYPE);
        }
    }

    public static class ViewAuthor extends DialogGvDataView<GvAuthor> {
        public ViewAuthor() {
            super(GvAuthor.TYPE);
        }
    }

    public static class ViewSubject extends DialogGvDataView<GvSubject> {
        public ViewSubject() {
            super(GvSubject.TYPE);
        }
    }

    public static class ViewDate extends DialogGvDataView<GvDate> {
        public ViewDate() {
            super(GvDate.TYPE);
        }
    }
}
