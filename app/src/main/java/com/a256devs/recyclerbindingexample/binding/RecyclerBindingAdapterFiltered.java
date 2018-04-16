package com.a256devs.recyclerbindingexample.binding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.AbstractList;
import java.util.ArrayList;

public class RecyclerBindingAdapterFiltered<T extends Comparable<? super T>>
        extends RecyclerView.Adapter<RecyclerBindingAdapterFiltered.BindingHolder> {

    protected int holderLayout, variableId, objectId, positionId;
    protected AbstractList<T> items = new ArrayList<>();
    protected AbstractList<T> filtereditems = new ArrayList<>();
    protected OnItemClickListener<T> onItemClickListener;
    protected SparseArray<OnItemClickListener<T>> viewListeners;
    protected Object object;

    protected T filterItem;
    private int totalItems = 0;

    public RecyclerBindingAdapterFiltered() {}

    public RecyclerBindingAdapterFiltered(int holderLayout, int variableId, AbstractList<T> items) {
        this.holderLayout = holderLayout;
        this.variableId = variableId;
        this.items = items;
        filtereditems.clear();
        filtereditems.addAll(items);
    }

    public RecyclerBindingAdapterFiltered(int holderLayout, int variableId, AbstractList<T> items, int objectId, Object object, SparseArray<OnItemClickListener<T>> viewListeners) {
        this(holderLayout, variableId, items);
        this.objectId = objectId;
        this.object = object;
        this.viewListeners = viewListeners;
    }

    public void filter(T item) {
        filterItem = item;
        filtereditems.clear();
        if(item==null) {
            filtereditems.addAll(items);
        } else {
            for (T t : items) {
                // Need Compare
                if (t.compareTo(item) == 0) {
                    filtereditems.add(t);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void addNewItems(AbstractList<T> items) {
        if (items.size() == 0) {
            return;
        }
        int startposition = filtereditems.size();
        this.items.addAll(items);
        //if(filterItem!=null) filter(filterItem); else filtereditems.addAll(items);
        filtereditems.addAll(items);
        // need calculate filtered start position and size
        int newsize = filtereditems.size();
        if(newsize>startposition) {
            notifyItemRangeInserted(startposition, newsize-startposition);
        }
    }

    public void setItems(AbstractList<T> items) {
        this.items = items;
        //filter(filterItem);
        filtereditems.clear();
        filtereditems.addAll(items);
        notifyDataSetChanged();
    }

    public void setFiltereditems(AbstractList<T> items, T item) {
        this.items = items;
        filter(item);
    }

    public AbstractList<T> getAdapterItems() {
        return items;
    }

    public AbstractList<T> getAdapterFilteredItems() {
        return filtereditems;
    }

    public void deleteItem(int position) {
        //problem need find filtered item in items
        T item = filtereditems.get(position);
        items.remove(item);
        filtereditems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, filtereditems.size());
    }

    public boolean isAllItemsLoaded() {
        return getAllItemCount()>=totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    @Override
    public RecyclerBindingAdapterFiltered.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(holderLayout, parent, false);
        return new BindingHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerBindingAdapterFiltered.BindingHolder holder, int position) {
        //final T item = items.get(position); //items was replaces with filtereditems
        final T item = filtereditems.get(position);
        holder.getBinding().getRoot().setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(position, item);
        });
        holder.getBinding().setVariable(variableId, item);
        if(object!=null) {
            holder.getBinding().setVariable(objectId, object);
        }
        if(onBindHolderListener!=null) onBindHolderListener.onBind(holder, position, item);
        if(viewListeners!=null) {
            for (int i = 0; i < viewListeners.size(); i++) {
                int key = viewListeners.keyAt(i);
                View childView = holder.getBinding().getRoot().findViewById(key);
                if (childView != null) {
                    childView.setOnClickListener(view -> {
                        if (viewListeners.get(key) != null) {
                            try {
                                viewListeners.get(key).onItemClick(position, item);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return filtereditems.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return filtereditems.size(); //items was replaces with filtereditems
    }

    public int getAllItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener<T> {
        void onItemClick(int position, T item);
    }

    public interface OnBindHolderListener<T> {
        void onBind(RecyclerBindingAdapterFiltered.BindingHolder holder, int position, T item);
    }

    protected OnBindHolderListener<T> onBindHolderListener;

    public void setOnBindHolderListener(OnBindHolderListener<T> onBindHolderListener) {
        this.onBindHolderListener = onBindHolderListener;
    }

    public class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BindingHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}