package id.web.bitocode.eu4provincewiki.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import id.web.bitocode.eu4provincewiki.R;
import id.web.bitocode.eu4provincewiki.model.RegionsModel;

public class AdapterRegionRecyclerView extends RecyclerView.Adapter<AdapterRegionRecyclerView.ViewHolder> implements Filterable
{
  
  private List<RegionsModel> regionsModels;
  private List<RegionsModel> regionsModelsFull;
  private onRegionListener onRegionListener;
  
  public AdapterRegionRecyclerView(List<RegionsModel> regionsModels, onRegionListener onRegionListener)
  {
    this.regionsModels = regionsModels;
    this.onRegionListener = onRegionListener;
    regionsModelsFull = new ArrayList<>(regionsModels);
  }
  
  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
  {
    private TextView tv_name;
    private TextView tv_tax;
    private TextView tv_production;
    private TextView tv_manpower;
    private TextView tv_territory;
    private TextView tv_region;
    private onRegionListener onRegionListener;
  
    ViewHolder(View view, onRegionListener onRegionListener)
    {
      super(view);
      tv_region = view.findViewById(R.id.tv_regRegion);
      tv_name = view.findViewById(R.id.tv_regState);
      tv_tax = view.findViewById(R.id.tv_regTax);
      tv_production = view.findViewById(R.id.tv_regProduction);
      tv_manpower = view.findViewById(R.id.tv_regManpower);
      tv_territory = view.findViewById(R.id.tv_regTerritory);
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
    viewHolder.tv_region.setText(data.getRegion());
    viewHolder.tv_name.setText(data.getName());
    viewHolder.tv_tax.setText(String.valueOf(data.getTotal_Tax()));
    viewHolder.tv_production.setText(String.valueOf(data.getTotal_Production()));
    viewHolder.tv_manpower.setText(String.valueOf(data.getTotal_Manpower()));
    viewHolder.tv_territory.setText(String.valueOf(data.getTotal_Territory()));
  }
  
  @Override
  public int getItemCount()
  {
    return regionsModels.size();
  }

  @Override
  public Filter getFilter()
  {
    return searchFilter;
  }
  
  private Filter searchFilter = new Filter()
  {
    @Override
    protected FilterResults performFiltering(CharSequence constraint)
    {
      List<RegionsModel> filteredList = new ArrayList<>();
      
      if(constraint == null || constraint.length() == 0)
      {
        filteredList.addAll(regionsModelsFull);
      }
      else
      {
        String filterPattern = constraint.toString().toLowerCase().trim();
        
        for(RegionsModel regionsModels : regionsModelsFull)
        {
          if(regionsModels.getRegion().toLowerCase().contains(filterPattern) || regionsModels.getName().toLowerCase().contains(filterPattern))
          {
            filteredList.add(regionsModels);
          }
        }
      }
      FilterResults results = new FilterResults();
      results.values = filteredList;
      return  results;
    }
  
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results)
    {
      regionsModels.clear();
      regionsModels.addAll((List) results.values);
      notifyDataSetChanged();
    }
  };
}
