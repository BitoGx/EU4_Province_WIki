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
import id.web.bitocode.eu4provincewiki.model.ProvinceModel;

public class AdapterProvinceRecyclerView extends RecyclerView.Adapter<AdapterProvinceRecyclerView.ViewHolder> implements Filterable
{
  
  private List<ProvinceModel> provinceModels;
  private List<ProvinceModel> provinceModelFull;
  
  public AdapterProvinceRecyclerView(List<ProvinceModel> provinceModel)
  {
    this.provinceModels = provinceModel;
    provinceModelFull = new ArrayList<>(provinceModel);
  }
  
  public class ViewHolder extends RecyclerView.ViewHolder
  {
    private TextView tv_id;
    private TextView tv_name;
    private TextView tv_owner;
    private TextView tv_culture;
    private TextView tv_religion;
    private TextView tv_trade_node;
    private TextView tv_trade_goods;
    private TextView tv_permanent_mod;
    private TextView tv_tax;
    private TextView tv_production;
    private TextView tv_manpower;
    
    public ViewHolder(View view)
    {
      super(view);
      tv_id = view.findViewById(R.id.tv_provId);
      tv_name = view.findViewById(R.id.tv_provTerritory);
      tv_owner = view.findViewById(R.id.tv_provOwner);
      tv_culture = view.findViewById(R.id.tv_provCulture);
      tv_religion = view.findViewById(R.id.tv_provReligion);
      tv_trade_node = view.findViewById(R.id.tv_provTradeNode);
      tv_trade_goods = view.findViewById(R.id.tv_provTradeGoods);
      tv_permanent_mod = view.findViewById(R.id.tv_provPermanentMod);
      tv_tax = view.findViewById(R.id.tv_provTax);
      tv_production = view.findViewById(R.id.tv_provProduction);
      tv_manpower = view.findViewById(R.id.tv_provManpower);
    }
  }
  
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
  {
    View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_provinces,viewGroup,false);
    return new ViewHolder(itemView);
  }
  
  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
  {
    ProvinceModel data = provinceModels.get(i);
    viewHolder.tv_id.setText(String.valueOf(data.getId()));
    viewHolder.tv_name.setText(data.getName());
    viewHolder.tv_owner.setText(data.getOwner());
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
    return provinceModels.size();
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
      List<ProvinceModel> filteredList = new ArrayList<>();
  
      if(constraint == null || constraint.length() == 0)
      {
        filteredList.addAll(provinceModelFull);
      }
      else
      {
        String filterPattern = constraint.toString().toLowerCase().trim();
    
        for(ProvinceModel provinceModels : provinceModelFull)
        {
          if(provinceModels.getName().toLowerCase().contains(filterPattern) || String.valueOf(provinceModels.getId()).toLowerCase().contains(filterPattern))
          {
            filteredList.add(provinceModels);
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
      provinceModels.clear();
      provinceModels.addAll((List) results.values);
      notifyDataSetChanged();
    }
  };
}
