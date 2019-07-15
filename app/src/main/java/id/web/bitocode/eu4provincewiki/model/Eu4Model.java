package id.web.bitocode.eu4provincewiki.model;

public class Eu4Model
{
  
  private String Continent;
  private String Culture;
  private Long Id;
  private Long Manpower;
  private String Owner;
  private String Permanent_Modifiers;
  private Long Production;
  private String Region;
  private String Religion;
  private String State;
  private Long Tax;
  private String Territory;
  private String Trade_Goods;
  private String Trade_Node;
  
  public Eu4Model()
  {
    //EMPTY you ask i answer IDK
  }
  
  public Eu4Model(String continent, String culture, Long id, Long manpower, String owner, String permanent_Modifiers, Long production, String region, String religion, String state, Long tax, String territory, String trade_Goods, String trade_Node)
  {
    Continent = continent;
    Culture = culture;
    Id = id;
    Manpower = manpower;
    Owner = owner;
    Permanent_Modifiers = permanent_Modifiers;
    Production = production;
    Region = region;
    Religion = religion;
    State = state;
    Tax = tax;
    Territory = territory;
    Trade_Goods = trade_Goods;
    Trade_Node = trade_Node;
  }
  
  public String getContinent()
  {
    return Continent;
  }
  
  public void setContinent(String continent)
  {
    Continent = continent;
  }
  
  public String getCulture()
  {
    return Culture;
  }
  
  public void setCulture(String culture)
  {
    Culture = culture;
  }
  
  public Long getId()
  {
    return Id;
  }
  
  public void setId(Long id)
  {
    Id = id;
  }
  
  public Long getManpower()
  {
    return Manpower;
  }
  
  public void setManpower(Long manpower)
  {
    Manpower = manpower;
  }
  
  public String getOwner()
  {
    return Owner;
  }
  
  public void setOwner(String owner)
  {
    Owner = owner;
  }
  
  public String getPermanent_Modifiers()
  {
    return Permanent_Modifiers;
  }
  
  public void setPermanent_Modifiers(String permanent_Modifiers)
  {
    Permanent_Modifiers = permanent_Modifiers;
  }
  
  public Long getProduction()
  {
    return Production;
  }
  
  public void setProduction(Long production)
  {
    Production = production;
  }
  
  public String getRegion()
  {
    return Region;
  }
  
  public void setRegion(String region)
  {
    Region = region;
  }
  
  public String getReligion()
  {
    return Religion;
  }
  
  public void setReligion(String religion)
  {
    Religion = religion;
  }
  
  public String getState()
  {
    return State;
  }
  
  public void setState(String state)
  {
    State = state;
  }
  
  public Long getTax()
  {
    return Tax;
  }
  
  public void setTax(Long tax)
  {
    Tax = tax;
  }
  
  public String getTerritory()
  {
    return Territory;
  }
  
  public void setTerritory(String territory)
  {
    Territory = territory;
  }
  
  public String getTrade_Goods()
  {
    return Trade_Goods;
  }
  
  public void setTrade_Goods(String trade_Goods)
  {
    Trade_Goods = trade_Goods;
  }
  
  public String getTrade_Node()
  {
    return Trade_Node;
  }
  
  public void setTrade_Node(String trade_Node)
  {
    Trade_Node = trade_Node;
  }
}
