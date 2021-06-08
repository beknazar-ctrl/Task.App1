package kg.geektech.taskapp.ui.onboard;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import kg.geektech.taskapp.R;
import kg.interfaces.OnItemClickListener;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private String[] titles = new String[]{"Ассаллоому аьлайкум", "Hi", "Привет"};
    private int[] imageOnboard = new int[]{R.drawable.hello, R.drawable.images, R.drawable.unnamed400x400};

    private OnItemClickListener onItemClickListener;


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle;
        private ImageView itemImage;
        private Button btnStart;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            itemImage = itemView.findViewById(R.id.pagerImage);
            btnStart = itemView.findViewById(R.id.btnStart);
            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClickStart();
                }
            });

//            btnStart.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    openHome.openHomeClick();
//                }
//            });

        }

        public void bind(int position) {
            textTitle.setText(titles[position]);
            itemImage.setImageResource(imageOnboard[position]);
            if (position == titles.length - 1) {
                btnStart.setVisibility(View.VISIBLE);
            } else {
                btnStart.setVisibility(View.GONE);

            }

        }
    }

}
