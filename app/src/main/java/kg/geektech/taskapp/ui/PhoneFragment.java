package kg.geektech.taskapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import kg.geektech.taskapp.R;

public class PhoneFragment extends Fragment {

    private EditText editPhone;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editPhone=view.findViewById(R.id.editPhone);
        view.findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSMS();
            }
        });
        initCallbacks();
    }

    private void initCallbacks() {
        callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("Phone","onVerificationCompleted");
            }

            @Override
            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                Log.e("Phone","onVerificationCompleted" + e.getMessage());

            }
        };
    }

    private void requestSMS(){
        String phone=editPhone.getText().toString();
        PhoneAuthOptions options=PhoneAuthOptions.newBuilder()
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(callbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}