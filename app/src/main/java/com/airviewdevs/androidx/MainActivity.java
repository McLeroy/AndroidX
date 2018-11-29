package com.airviewdevs.androidx;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.airviewdevs.androidx.api.Resource;
import com.airviewdevs.androidx.models.Todo;
import com.airviewdevs.androidx.todos.TodoViewModel;
import com.airviewdevs.androidx.todos.TodosAdapter;
import com.airviewdevs.androidx.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements TodosAdapter.TodosCallback {

    @BindView(R.id.recycler_view)RecyclerView recyclerView;
    @BindView(R.id.progress_bar)View progressBar;

    private TodoViewModel todoViewModel;
    private TodosAdapter todosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(todosAdapter = new TodosAdapter(this));
        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoViewModel.getTodos().observe(this, observer);
    }

    private Observer<Resource<List<Todo>>>observer = new Observer<Resource<List<Todo>>>() {
        @Override
        public void onChanged(Resource<List<Todo>> listResource) {
            progressBar.setVisibility(listResource.status.equals(Resource.Status.LOADING) ? View.VISIBLE : View.GONE);
            DebugUtils.debug(MainActivity.class, "Data received");
            if (listResource.status == Resource.Status.LOADING) return;
            if (listResource.ex != null)
                Toast.makeText(MainActivity.this, listResource.ex.getMessage(), Toast.LENGTH_SHORT).show();
            if (listResource.data != null)
                todosAdapter.setTodos(listResource.data);
        }
    };

    @OnClick(R.id.fab)
    public void onLoadTodos() {
        todosAdapter.setTodos(new ArrayList<>());
        todoViewModel.getTodos().observe(this, observer);
    }

    @Override
    public void todoClicked(Todo todo) {
        DebugUtils.debug(MainActivity.class, "Todo clicked: "+todo.toString());
    }
}
