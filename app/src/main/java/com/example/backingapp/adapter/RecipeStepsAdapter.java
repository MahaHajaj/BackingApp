package com.example.backingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.backingapp.R;
import com.example.backingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsViewHolder> {

    private final AdapterOnClickHandler mClickHandler;
    private ArrayList<Step> steps;
    private Context context;

    public RecipeStepsAdapter(ArrayList<Step> steps, Context context, AdapterOnClickHandler mClickHandler) {
        this.steps = steps;
        this.context = context;
        this.mClickHandler = mClickHandler;
    }

    @NonNull
    @Override
    public RecipeStepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutId = R.layout.step_list;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, viewGroup, false);
        return new RecipeStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsViewHolder recipeStepsViewHolder, int i) {
        Step step = steps.get(i);
        recipeStepsViewHolder.step.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (null == steps) return 0;
        return steps.size();
    }

    public void setStepsData(ArrayList<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    public interface AdapterOnClickHandler {
        void onClick(int adapterPosition);
    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.step)
        TextView step;

        RecipeStepsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }
}
