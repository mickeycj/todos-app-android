package ssd.project.mickeycj.todosapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import ssd.project.mickeycj.todosapp.R;
import ssd.project.mickeycj.todosapp.model.Item;
import ssd.project.mickeycj.todosapp.view.OnViewHolderClickListener;

/**
 * Created by user on 4/6/60.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private Context context;

    private List<Item> itemList;

    private OnViewHolderClickListener onViewHolderClickListener;

    public ItemListAdapter(List<Item> itemList, OnViewHolderClickListener onViewHolderClickListener) {
        this.itemList = itemList;
        this.onViewHolderClickListener = onViewHolderClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewHolderClickListener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.titleTextView.setText(item.getTitle());
        holder.createdAtTextView.setText(new SimpleDateFormat("MMMM dd, yyyy").format(item.getCreatedAt()));
        holder.isDoneTextView.setText((item.isDone()) ? "Done" : "Not Done");
        holder.layout.setBackgroundResource((item.isDone()) ? R.drawable.bg_green : R.drawable.bg_red);
    }

    @Override
    public int getItemCount() { return itemList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private FrameLayout layout;

        private TextView titleTextView, createdAtTextView,isDoneTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            layout = (FrameLayout) itemView.findViewById(R.id.layout_item_status);

            titleTextView = (TextView) itemView.findViewById(R.id.textview_item_title_viewholder);
            createdAtTextView = (TextView) itemView.findViewById(R.id.textview_item_created_at_viewholder);
            isDoneTextView = (TextView) itemView.findViewById(R.id.textview_item_is_done_viewholder);
        }
    }
}
