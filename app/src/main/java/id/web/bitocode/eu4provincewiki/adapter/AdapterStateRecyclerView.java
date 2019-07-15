package id.web.bitocode.eu4provincewiki.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import id.web.bitocode.eu4provincewiki.MainActivity;
import id.web.bitocode.eu4provincewiki.R;
import id.web.bitocode.eu4provincewiki.model.Eu4Model;

public class AdapterStateRecyclerView extends RecyclerView.Adapter<AdapterStateRecyclerView.ViewHolder>
{
  private List<Eu4Model> eu4Models;
  private Activity mActivity;
  
  public class ViewHolder extends RecyclerView.ViewHolder
  {
    public LinearLayout rl_layout;
    public TextView tv_id, tv_nama;
    
    public ViewHolder(View view)
    {
      super(view);
      rl_layout = view.findViewById(R.id.rl_layout);
      tv_id = view.findViewById(R.id.tv_id);
      tv_nama = view.findViewById(R.id.tv_name);
    }
  }
  
  public AdapterStateRecyclerView(List<Eu4Model> eu4ModelList, Activity activity)
  {
    this.eu4Models = eu4ModelList;
    this.mActivity = activity;
  }
  
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
  {
    View itemView = LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.item_provinces, viewGroup, false);
    return new ViewHolder(itemView);
  }
  
  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
  {
    final Eu4Model movie = eu4Models.get(i);
    
    viewHolder.tv_id.setText(Long.toString(movie.getId()));
    viewHolder.tv_nama.setText(movie.getTerritory());
  
    viewHolder.rl_layout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
      
        Intent goDetail = new Intent(mActivity, MainActivity.class);
        goDetail.putExtra("id", movie.getId());
        goDetail.putExtra("title", movie.getTerritory());
      
        mActivity.startActivity(goDetail);
      
      
      }
    });
  }
  
  @Override
  public int getItemCount()
  {
    return eu4Models.size();
  }
}
