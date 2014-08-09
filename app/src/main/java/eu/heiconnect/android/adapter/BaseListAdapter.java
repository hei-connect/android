package eu.heiconnect.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import eu.heiconnect.android.view.CellView;

public class BaseListAdapter<T> extends BaseAdapter {

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------

    protected Context context;
    protected Class<? extends CellView<T>> cellViewClass;
    protected List<T> objectList;

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public BaseListAdapter(Context context, Class<? extends CellView<T>> cellViewClass, List<T> objectList) {
        super();
        this.objectList = objectList;
        this.context = context;
        this.cellViewClass = cellViewClass;
    }

    // ----------------------------------
    // OVERRIDEN METHODS
    // ----------------------------------

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        CellView<T> cell = cellViewClass.cast(convertView);

        if (cell == null) {
            try {
                cell = createView(position);
            } catch (Exception e) {
                Log.e("BaseListAdapter", "ResultError during constructor call", e);
                throw new RuntimeException("ResultError during constructor call", e);
            }
        }

        if (cell != null) {
            bindView(position, cell);
        }

        return cell;
    }

    @Override
    public int getCount() {
        return objectList.size();
    }

    @Override
    public T getItem(int position) {
        return objectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // ----------------------------------
    // PUBLIC METHODS
    // ----------------------------------

    public void refill(List<T> objectList) {
        clear();
        addAll(objectList);
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public void clear() {
        objectList.clear();
    }

    public void addAll(List<T> objectList) {
        this.objectList.addAll(objectList);
    }

    public List<T> getObjectList() {
        return objectList;
    }

    // ----------------------------------
    // PROTECTED METHODS
    // ----------------------------------

    protected CellView<T> createView(int position) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return createView();
    }

    protected CellView<T> createView() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return cellViewClass.getConstructor(Context.class).newInstance(context);
    }

    protected void bindView(int position, CellView<T> cell) {
        T modelObject = getItem(position);
        cell.setData(modelObject, position);
    }
}
