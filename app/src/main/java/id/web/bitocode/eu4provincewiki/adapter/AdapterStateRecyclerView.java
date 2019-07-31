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
import id.web.bitocode.eu4provincewiki.model.StateModel;

public class AdapterStateRecyclerView extends RecyclerView.Adapter<AdapterStateRecyclerView.ViewHolder> implements Filterable
{
  
  private List<StateModel> stateModels;
  private List<StateModel> stateModelsFull;
  private onStateListener onStateListener;
  
  public AdapterStateRecyclerView(List<StateModel> stateModels, onStateListener onStateListener)
  {
    this.stateModels = stateModels;
    this.onStateListener = onStateListener;
    stateModelsFull = new ArrayList<>(stateModels);
  }
  
  public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener
  {
    private TextView tv_name;
    private TextView tv_tax;
    private TextView tv_production;
    private TextView tv_manpower;
    private TextView tv_territory;
    private onStateListener onStateListener;
    
    ViewHolder(View view, onStateListener onStateListener)
    {
      super(view);
      tv_name = view.findViewById(R.id.tv_statRegion);
      tv_tax = view.findViewById(R.id.tv_statTax);
      tv_production = view.findViewById(R.id.tv_statProduction);
      tv_manpower = view.findViewById(R.id.tv_statManpower);
      tv_territory = view.findViewById(R.id.tv_statTerritory);
      this.onStateListener = onStateListener;
      view.setOnClickListener(this);
    }
  
    @Override
    public void onClick(View v)
    {
      onStateListener.onStateClick(getAdapterPosition());
    }
  }
  
  public interface onStateListener
  {
    void onStateClick(int position);
  }
  
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
  {
    View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_states,viewGroup,false);
    return new ViewHolder(itemView,onStateListener);
  }
  
  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
  {
    StateModel data = stateModels.get(i);
    viewHolder.tv_name.setText(data.getName());
    viewHolder.tv_tax.setText(String.valueOf(data.getTotal_Tax()));
    viewHolder.tv_production.setText(String.valueOf(data.getTotal_Production()));
    viewHolder.tv_manpower.setText(String.valueOf(data.getTotal_Manpower()));
    viewHolder.tv_territory.setText(String.valueOf(data.getTotal_Territory()));
  }
  
  @Override
  public int getItemCount()
  {
    return stateModels.size();
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
      List<StateModel> filteredList = new ArrayList<>();
      
      if(constraint == null || constraint.length() == 0)
      {
        filteredList.addAll(stateModelsFull);
      }
      else
      {
        String filterPattern = constraint.toString().toLowerCase().trim();
        
        for(StateModel stateModels : stateModelsFull)
        {
          if(stateModels.getName().toLowerCase().contains(filterPattern) || stateModels.getName().toLowerCase().contains(filterPattern))
          {
            filteredList.add(stateModels);
          }
        }
      }
      FilterResults results = new FilterResults();
      results.values = filteredList;
      return  results;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results)
    {
      stateModels.clear();
      stateModels.addAll((List) results.values);
      notifyDataSetChanged();
    }
  };
  
}
