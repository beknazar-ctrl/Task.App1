package kg.geektech.taskapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kg.Prefs;
import kg.geektech.taskapp.App;
import kg.geektech.taskapp.R;
import kg.geektech.taskapp.models.Task;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter();
//        List<Task> list= App.getAppDatabase().taskDao().getAll();
//        adapter.addItems(list);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        roomInit();
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.recyclerView);
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTaskFragment();
            }
        });

        setResultListener();
//        initList();
    }

    private void roomInit(){
        App.getAppDatabase().taskDao().getAll().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> list) {
                initList(list);
            }
        });
    }

    private void initList(List<Task>list) {
        adapter=new TaskAdapter();
        adapter.addItems(list);
        recyclerView.setAdapter(adapter);
    }

    private void setResultListener() {
        getParentFragmentManager().setFragmentResultListener("rk_task",
                getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
                        String text = result.getString("text");
                        Task task = new Task(text);
                        adapter.addItem(task);
                        Log.e("Home", "text:" + text);


                    }
                });
    }

    private void openTaskFragment() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.taskFragment);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_clear_preferences,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort:
                sortRoom();
                Toast.makeText(requireActivity(), "sorted", Toast.LENGTH_SHORT).show();
                return true;

            case  R.id.btn_clear:
                Prefs prefs = new Prefs(getContext());
                prefs.clearPref();
                Toast.makeText(getContext(), "cleared", Toast.LENGTH_SHORT).show();
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void sortRoom() {

        App.getAppDatabase().taskDao().sortAsc().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task>tasks) {
                adapter.notifyDataSetChanged();
                initList(tasks);
            }
        });
    }
}