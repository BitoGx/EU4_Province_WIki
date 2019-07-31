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
import id.web.bitocode.eu4provincewiki.model.TerritoryModel;

public class AdapterTerritoryRecyclerView extends RecyclerView.Adapter<AdapterTerritoryRecyclerView.ViewHolder> implements Filterable
{
  
  private List<TerritoryModel> territoryModels;
  private List<TerritoryModel> territoryModelFull;
  
  public AdapterTerritoryRecyclerView(List<TerritoryModel> territoryModel)
  {
    this.territoryModels = territoryModel;
    territoryModelFull = new ArrayList<>(territoryModel);
  }
  
  class ViewHolder extends RecyclerView.ViewHolder
  {
    private TextView tv_id;
    private TextView tv_name;
    private TextView tv_owner;
    private TextView tv_state;
    private TextView tv_region;
    private TextView tv_culture;
    private TextView tv_religion;
    private TextView tv_trade_node;
    private TextView tv_trade_goods;
    private TextView tv_permanent_mod;
    private TextView tv_tax;
    private TextView tv_manpower;
    private TextView tv_production;
    
    ViewHolder(View view)
    {
      super(view);
      tv_id = view.findViewById(R.id.tv_territoryId);
      tv_name = view.findViewById(R.id.tv_territoryTerritory);
      tv_state = view.findViewById(R.id.tv_territoryState);
      tv_region = view.findViewById(R.id.tv_territoryRegion);
      tv_owner = view.findViewById(R.id.tv_territoryOwner);
      tv_culture = view.findViewById(R.id.tv_territoryCulture);
      tv_religion = view.findViewById(R.id.tv_territoryReligion);
      tv_trade_node = view.findViewById(R.id.tv_territoryTradeNode);
      tv_trade_goods = view.findViewById(R.id.tv_territoryTradeGoods);
      tv_permanent_mod = view.findViewById(R.id.tv_territoryPermanentMod);
      tv_tax = view.findViewById(R.id.tv_territoryTax);
      tv_production = view.findViewById(R.id.tv_territoryProduction);
      tv_manpower = view.findViewById(R.id.tv_territoryManpower);
    }
  }
  
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
  {
    View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_territory,viewGroup,false);
    return new ViewHolder(itemView);
  }
  
  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
  {
    TerritoryModel data = territoryModels.get(i);
    viewHolder.tv_id.setText(String.valueOf(data.getId()));
    viewHolder.tv_name.setText(data.getName());
    viewHolder.tv_owner.setText(data.getOwner());
    viewHolder.tv_state.setText(data.getState());
    viewHolder.tv_region.setText(data.getRegion());
    viewHolder.tv_culture.setText(data.getCulture());
    viewHolder.tv_religion.setText(data.getReligion());
    viewHolder.tv_trade_node.setText(data.getTrade_Node());
    viewHolder.tv_trade_goods.setText(data.getTrade_Goods());
    viewHolder.tv_permanent_mod.setText(data.getPermanent_Modifiers());
    viewHolder.tv_tax.setText(String.valueOf(data.getTax()));
    viewHolder.tv_production.setText(String.valueOf(data.getProduction()));
    viewHolder.tv_manpower.setText(String.valueOf(data.getManpower()));
  }
  
  @Override
  public int getItemCount()
  {
    return territoryModels.size();
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
      List<TerritoryModel> filteredList = new ArrayList<>();
  
      if (constraint == null || constraint.length() == 0)
      {
        filteredList.addAll(territoryModelFull);
      } else
      {
        String filterPattern = constraint.toString().toLowerCase().trim();
  
        for (TerritoryModel provinceModels : territoryModelFull)
        {
          if (provinceModels.getName().toLowerCase().contains(filterPattern) || String.valueOf(provinceModels.getId()).toLowerCase().contains(filterPattern))
          {
            filteredList.add(provinceModels);
          }
        }
      }
      FilterResults results = new FilterResults();
      results.values = filteredList;
      return results;
    }
  
    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results)
    {
      territoryModels.clear();
      territoryModels.addAll((List) results.values);
      notifyDataSetChanged();
    }
    
  };
}
