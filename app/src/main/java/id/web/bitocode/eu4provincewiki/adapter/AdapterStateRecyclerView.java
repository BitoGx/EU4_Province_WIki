package id.web.bitocode.eu4provincewiki.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import id.web.bitocode.eu4provincewiki.R;
import id.web.bitocode.eu4provincewiki.model.Eu4Model;

public class AdapterStateRecyclerView extends RecyclerView.Adapter<AdapterStateRecyclerView.ViewHolder>
{
  
  private List<Eu4Model> eu4Models;
  
  private AdapterStateRecyclerView(List<Eu4Model> eu4Models)
  {
    this.eu4Models = eu4Models;
  }
  
  public class ViewHolder extends RecyclerView.ViewHolder
  {
    public LinearLayout rl_layout;
    public TextView tv_continent, tv_region, tv_state;
    
    public ViewHolder(View view)
    {
      super(view);
      rl_layout = view.findViewById(R.id.rl_layout);
      tv_continent = view.findViewById(R.id.tv_Continent);
      tv_region = view.findViewById(R.id.tv_Region);
      tv_state = view.findViewById(R.id.tv_State);
    }
  }
  
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
  {
    View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_provinces, viewGroup, false);
    return new ViewHolder(itemView);
  }
  
  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
  {
    Eu4Model data = eu4Models.get(i);
    viewHolder.tv_continent.setText(data.getContinent());
    viewHolder.tv_region.setText(data.getRegion());
    viewHolder.tv_state.setText(data.getState());
  }
  
  @Override
  public int getItemCount()
  {
    return eu4Models.size();
  }
}
