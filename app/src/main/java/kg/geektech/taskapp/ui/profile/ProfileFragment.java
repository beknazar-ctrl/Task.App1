package kg.geektech.taskapp.ui.profile;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

import kg.Prefs;
import kg.geektech.taskapp.R;

public class ProfileFragment extends Fragment {
    private ImageView imageView;
    private EditText editText;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult
            (new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    imageView.setImageURI(uri);
                }
            });

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view,
                              @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText=view.findViewById(R.id.profile_userName);
        imageView = view.findViewById(R.id.profile_image_fragment);
        Prefs prefs = new Prefs(getContext());
        editText.setText(prefs.getString("autoSave"));


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.putString("autoSave", s.toString());
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });


    }
}


