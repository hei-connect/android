package eu.heiconnect.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import eu.heiconnect.android.R;
import eu.heiconnect.android.utils.FormatUtils;
import eu.heiconnect.android.webservice.absences.Absence;

public class AbsenceCellView extends CellView<Absence> {

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    private TextView dateTextView;
    private TextView timeTextView;
    private TextView sectionTextView;
    private TextView lengthTextView;

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public AbsenceCellView(Context context) {
        super(context);
        initializeView();
    }

    public AbsenceCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public AbsenceCellView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }

    // ----------------------------------
    // OVERRIDEN METHODS
    // ----------------------------------
    @Override
    protected void bindData(Absence absence) {
        dateTextView.setText(FormatUtils.getShortDateFormat().format(absence.getDate()));
        timeTextView.setText(FormatUtils.getTimeFormat().format(absence.getDate()));
        sectionTextView.setText(absence.getSectionName());
        lengthTextView.setText(getContext().getString(R.string.generic_time_minute, absence.getLength()));
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void initializeView() {
        getInflater().inflate(R.layout.cellview_absence, this);

        dateTextView = (TextView) findViewById(R.id.textview_absence_date);
        timeTextView = (TextView) findViewById(R.id.textview_absence_time);
        sectionTextView = (TextView) findViewById(R.id.textview_absence_section);
        lengthTextView = (TextView) findViewById(R.id.textview_absence_length);
    }
}
