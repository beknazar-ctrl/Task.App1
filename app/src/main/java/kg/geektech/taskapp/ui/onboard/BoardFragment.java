package kg.geektech.taskapp.ui.onboard;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import org.jetbrains.annotations.NotNull;

import kg.Prefs;
import kg.geektech.taskapp.R;
import kg.interfaces.OnItemClickListener;

import static kg.geektech.taskapp.R.id.dots_indicator;


public class BoardFragment extends Fragment {

    private Button btnSkip;
    DotsIndicator dotsIndicator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);


    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSkip = view.findViewById(R.id.skip);
        dotsIndicator = view.findViewById(dots_indicator);
        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        BoardAdapter adapter = new BoardAdapter();
        viewPager.setAdapter(adapter);
        dotsIndicator.setViewPager2(viewPager);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickStart() {
                close();
            }
        });
        btnSkip = view.findViewById(R.id.skip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(R.id.navigation_home);
            }
        });


        requireActivity().getOnBackPressedDispatcher()
                .addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });
    }

    private void navigate(int navigation_home) {
        close();
    }

    private void close() {
        new Prefs(requireContext()).saveBoardState();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }


//    private void navigate(int navigation_home) {
//    }
}