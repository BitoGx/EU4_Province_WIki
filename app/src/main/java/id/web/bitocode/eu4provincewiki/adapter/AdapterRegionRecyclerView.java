package id.web.bitocode.eu4provincewiki.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.List;
import id.web.bitocode.eu4provincewiki.R;
import id.web.bitocode.eu4provincewiki.model.RegionsModel;

public class AdapterRegionRecyclerView extends RecyclerView.Adapter<AdapterRegionRecyclerView.ViewHolder>
{
  
  private List<RegionsModel> regionsModels;
  private onRegionListener onRegionListener;
  
  public AdapterRegionRecyclerView(List<RegionsModel> regionsModels, onRegionListener onRegionListener)
  {
    this.regionsModels = regionsModels;
    this.onRegionListener = onRegionListener;
  }
  
  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
  {
    public TextView tv_region;
    public TextView tv_continent;
    onRegionListener onRegionListener;
    
    public ViewHolder(View view, onRegionListener onRegionListener)
    {
      super(view);
      tv_region = view.findViewById(R.id.tv_regRegion);
      tv_continent = view.findViewById(R.id.tv_regContinent);
      this.onRegionListener = onRegionListener;
      view.setOnClickListener(this);
    }
  
    @Override
    public void onClick(View v)
    {
      onRegionListener.onRegionClick(getAdapterPosition());
    }
  }
  
  public interface onRegionListener
  {
    void onRegionClick(int position);
  }
  
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
  {
    View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_region, viewGroup, false);
    return new ViewHolder(itemView,onRegionListener);
  }
  
  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
  {
    RegionsModel data = regionsModels.get(i);
    viewHolder.tv_region.setText(data.getName());
    viewHolder.tv_continent.setText(data.getRegion());
  }
  
  @Override
  public int getItemCount()
  {
    return regionsModels.size();
  }
  
}
