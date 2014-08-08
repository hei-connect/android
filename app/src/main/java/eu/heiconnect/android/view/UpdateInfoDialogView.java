package eu.heiconnect.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import eu.heiconnect.android.R;

public class UpdateInfoDialogView extends LinearLayout {

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public UpdateInfoDialogView(Context context) {
        super(context);
        initializeView(context);
    }

    public UpdateInfoDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }


    public UpdateInfoDialogView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView(context);
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_update_info_dialog, this);
    }
}
