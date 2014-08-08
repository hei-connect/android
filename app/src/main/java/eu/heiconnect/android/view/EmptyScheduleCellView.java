package eu.heiconnect.android.view;

import android.content.Context;
import android.util.AttributeSet;

import eu.heiconnect.android.R;
import eu.heiconnect.android.webservice.schedule.Course;

public class EmptyScheduleCellView extends CellView<Course> {

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public EmptyScheduleCellView(Context context) {
        super(context);
        initializeView();
    }

    public EmptyScheduleCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public EmptyScheduleCellView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }

    // ----------------------------------
    // OVERRIDEN METHODS
    // ----------------------------------
    @Override
    protected void bindData(Course course) {
        // Do nothing :)
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void initializeView() {
        getInflater().inflate(R.layout.cellview_empty_schedule, this);
    }
}
