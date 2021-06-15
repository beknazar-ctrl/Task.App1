package kg.geektech.taskapp.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import kg.geektech.taskapp.R;

public class PhoneFragment extends Fragment {

    private EditText editPhone, editCode;
    private Button btnContinue, btnContinueCode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private NavController navController;
    private String code;
    private ProgressBar progressBar;
    private TextView timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timer=view.findViewById(R.id.timer);
        progressBar = view.findViewById(R.id.progress_bar);
        editPhone = view.findViewById(R.id.editPhone);
        editCode = view.findViewById(R.id.editCode);
        btnContinue = view.findViewById(R.id.btnContinue);
        btnContinueCode = view.findViewById(R.id.btnContinueCode);
        btnContinueCode.setOnClickListener(v -> {
            if (editCode.getText().toString().trim().equals(code)) {
                navController.navigateUp();
            }
        });

        new CountDownTimer(50000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String text = String.format(Locale.getDefault(), "%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                timer.setText(text);
                timer.setVisibility(View.GONE);
            }
            @Override
            public void onFinish() {
                Navigation.findNavController(view).popBackStack();
            }
        }.start();
        view.findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editPhone.getText().toString().isEmpty()) {
                    requestSMS();
                    editPhone.setVisibility(View.GONE);
                    editCode.setVisibility(View.VISIBLE);
                    btnContinue.setVisibility(View.GONE);
                    btnContinueCode.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    timer.setVisibility(View.VISIBLE);
                }
                btnSent();
            }
        });
        initCallbacks();
    }



    private void initCallbacks() {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                //               Log.e("Phone", "onVerificationCompleted");
                //               Log.e("Phone", phoneAuthCredential.getSmsCode());
                //code = phoneAuthCredential.getSmsCode();
            }

            @Override
            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                Log.e("Phone", "onVerificationFailed" + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                code = s;
                Log.e("Phone","code sent");
                progressBar.setVisibility(View.INVISIBLE);
            }
        };
    }

    private void requestSMS() {
        String phone = editPhone.getText().toString();
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(callbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }



    private void btnSent() {
        btnContinueCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editCode.getText().toString().trim().isEmpty()){
                    Toast.makeText(requireContext(), "введите код", Toast.LENGTH_SHORT).show();
                    return;
                }

                String edit = editCode.getText().toString();
                if (code != null){
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(code,edit);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.INVISIBLE);
                            if (task.isSuccessful()){
                                Toast.makeText(requireContext(), "удачно", Toast.LENGTH_SHORT).show();
                                NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main);
                                navController.navigate(R.id.navigation_home);

                            }else {
                                Toast.makeText(requireContext(), "введите корректно код", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });
    }
}