package eu.heiconnect.android.adapter;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import eu.heiconnect.android.view.CellView;
import eu.heiconnect.android.view.EmptyScheduleCellView;
import eu.heiconnect.android.webservice.schedule.Course;

public class ScheduleAdapter extends BaseListAdapter<Course> {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static final int EMPTY_LIST_CELL_VIEW_TYPE = 0;
    private static final int REGULAR_CELL_VIEW_TYPE = 1;

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public ScheduleAdapter(Context context, Class<? extends CellView<Course>> cellViewClass, List<Course> objectList) {
        super(context, cellViewClass, objectList);
    }

    // ----------------------------------
    // OVERRIDEN METHODS
    // ----------------------------------

    @Override
    public int getCount() {
        if (getObjectList().size() == 0) {
            return 1;
        } else {
            return super.getCount();
        }
    }

    @Override
    public Course getItem(int position) {
        if (getObjectList().size() == 0) {
            return new Course();
        } else {
            return super.getItem(position);
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (getObjectList().size() == 0) {
            return EMPTY_LIST_CELL_VIEW_TYPE;
        } else {
            return REGULAR_CELL_VIEW_TYPE;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (getItemViewType(position) == EMPTY_LIST_CELL_VIEW_TYPE) {
            EmptyScheduleCellView emptyScheduleCell = EmptyScheduleCellView.class.cast(convertView);

            if (emptyScheduleCell == null) {
                try {
                    emptyScheduleCell = (EmptyScheduleCellView) createView(position);
                } catch (Exception e) {
                    Log.e("BaseListAdapter", "ResultError during constructor call", e);
                    throw new RuntimeException("ResultError during constructor call", e);
                }
            }

            // No need to bind any data :)

            return emptyScheduleCell;
        } else {
            return super.getView(position, convertView, viewGroup);
        }
    }

    @Override
    protected CellView<Course> createView(int position) throws Fragment.InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        if (getItemViewType(position) == EMPTY_LIST_CELL_VIEW_TYPE) {
            return new EmptyScheduleCellView(getContext());
        } else {
            return super.createView(position);
        }
    }
}
