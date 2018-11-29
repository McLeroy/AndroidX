package com.airviewdevs.androidx.todos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.airviewdevs.androidx.R;
import com.airviewdevs.androidx.models.Todo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.TodoHolder>{
    private final TodosCallback todosCallback;
    private List<Todo>todos;

    public TodosAdapter(TodosCallback todosCallback) {
        this.todosCallback = todosCallback;
        todos = new ArrayList<>();
    }

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_todo_item, parent, false);
        return new TodoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.titleText.setText(todo.getTitle());
        holder.statusView.setVisibility(todo.isCompleted() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

    public interface TodosCallback {
        void todoClicked(Todo todo);
    }
    class TodoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.title_text)TextView titleText;
        @BindView(R.id.status_view)View statusView;
        TodoHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Todo todo = todos.get(getAdapterPosition());
            todosCallback.todoClicked(todo);
        }
    }
}
