package kg.geektech.taskapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;


public class TaskFragment extends Fragment {
    private EditText editText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.edit_text);
        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }

            private void save() {
                String text = editText.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("text",text);
                getParentFragmentManager().setFragmentResult("rk_task",bundle);
                close();

            }
            private void close(){
                NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main);
                navController.navigateUp();
            }
        });
    }
}