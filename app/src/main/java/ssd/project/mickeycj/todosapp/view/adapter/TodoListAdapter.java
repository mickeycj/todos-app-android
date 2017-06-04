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
import ssd.project.mickeycj.todosapp.model.Todo;

/**
 * Created by user on 4/6/60.
 */

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

    private Context context;

    private List<Todo> todoList;

    public TodoListAdapter(List<Todo> todoList) { this.todoList = todoList; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.viewholder_todo, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Todo todo = todoList.get(position);

        holder.layout.setBackgroundResource(R.drawable.bg_red);

        holder.titleTextView.setText((todo.isImportant()) ? todo.getTitle().toUpperCase() + "!" : todo.getTitle());
        holder.createdAtTextView.setText(new SimpleDateFormat("MMMM dd, yyyy").format(todo.getCreatedAt()));
        holder.doneCountTextView.setText("None");
    }

    @Override
    public int getItemCount() { return todoList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private FrameLayout layout;

        private TextView titleTextView, createdAtTextView, doneCountTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            layout = (FrameLayout) itemView.findViewById(R.id.layout_todo_status);

            titleTextView = (TextView) itemView.findViewById(R.id.textview_todo_title_viewholder);
            createdAtTextView = (TextView) itemView.findViewById(R.id.textview_todo_created_at_viewholder);
            doneCountTextView = (TextView) itemView.findViewById(R.id.textview_todo_done_count_viewholder);
        }
    }
}
