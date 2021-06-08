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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import kg.geektech.taskapp.models.Task;


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

                Task task =new Task(text);
                App.getAppDatabase().taskDao().insert(task);

                saveToFirestore(task);
                Bundle bundle = new Bundle();
                bundle.putString("text",text);
                getParentFragmentManager().setFragmentResult("rk_task",bundle);
        //        close();

            }

            private void saveToFirestore(Task task) {
                FirebaseFirestore.getInstance().collection("tasks").add(task)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentReference> t) {
                                if (t.isSuccessful()){
                                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                                    close();
                                }else {
                                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            private void close(){
                NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main);
                navController.navigateUp();
            }
        });
    }
}