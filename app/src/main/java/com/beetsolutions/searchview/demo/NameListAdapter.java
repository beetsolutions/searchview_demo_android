package com.beetsolutions.searchview.demo;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.beetsolutions.searchview.demo.databinding.NameItemBinding;

import java.util.Comparator;
import java.util.List;

final class NameListAdapter extends RecyclerView.Adapter<NameListAdapter.NameViewHolder> {

    private final Comparator<Name> nameComparator;
    private final LayoutInflater inflater;

    private final SortedList<Name> sortedList = new SortedList<>(Name.class, new SortedList.Callback<Name>() {
        @Override
        public int compare(Name a, Name b) {
            return nameComparator.compare(a, b);
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(Name oldItem, Name newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(Name item1, Name item2) {
            return item1 == item2;
        }
    });

    NameListAdapter(Context context, Comparator<Name> comparator) {
        inflater = LayoutInflater.from(context);
        nameComparator = comparator;
    }

    @Override
    public NameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final NameItemBinding binding = NameItemBinding.inflate(inflater, parent, false);
        return new NameViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(NameViewHolder holder, int position) {
        final Name model = sortedList.get(position);
        holder.bind(model);
    }

    public void add(Name model) {
        sortedList.add(model);
    }

    public void remove(Name model) {
        sortedList.remove(model);
    }

    public void add(List<Name> models) {
        sortedList.addAll(models);
    }

    public void remove(List<Name> models) {
        sortedList.beginBatchedUpdates();
        for (Name model : models) {
            sortedList.remove(model);
        }
        sortedList.endBatchedUpdates();
    }

    public void replaceAll(List<Name> models) {
        sortedList.beginBatchedUpdates();
        for (int i = sortedList.size() - 1; i >= 0; i--) {
            final Name model = sortedList.get(i);
            if (!models.contains(model)) {
                sortedList.remove(model);
            }
        }
        sortedList.addAll(models);
        sortedList.endBatchedUpdates();
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    class NameViewHolder extends RecyclerView.ViewHolder {

        private final NameItemBinding binding;

        NameViewHolder(NameItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Name name) {
            binding.setViewModel(name);
        }
    }
}
