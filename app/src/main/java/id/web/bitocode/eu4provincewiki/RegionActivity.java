package id.web.bitocode.eu4provincewiki;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class RegionActivity extends AppCompatActivity
{
  
  private DrawerLayout dl;
  private ActionBarDrawerToggle dt;
  private NavigationView nv;
  private Intent start;
  private Intent getData;
  
  private String Region;
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_region);
    
    Intent getData = getIntent();
    String Region = getData.getStringExtra("Region");
  
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(Region);
  
    dl = findViewById(R.id.activity_region);
    dt = new ActionBarDrawerToggle(this, dl,R.string.Open,R.string.Close);
    
    dl.addDrawerListener(dt);
    dt.syncState();
  
    nv = findViewById(R.id.nvRegion);
    nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
    {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
      {
        int id = menuItem.getItemId();
        switch (id)
        {
          case R.id.navhome:
            start = new Intent(RegionActivity.this, MainActivity.class);
            startActivity(start);
            break;
        
          case R.id.navstatefav:
            start = new Intent(RegionActivity.this, FavouriteStateActivity.class);
            startActivity(start);
            break;
        
          case R.id.navstate:
            start = new Intent(RegionActivity.this, StateActivity.class);
            startActivity(start);
            break;
        
          case R.id.navterritory:
            start = new Intent(RegionActivity.this, TerritoryActivity.class);
            startActivity(start);
            break;
        
          case R.id.navterrain:
            start = new Intent(RegionActivity.this, TerrainActivity.class);
            startActivity(start);
            break;
        
          default:
            return true;
        }
        return true;
      }
    });
    
  }
}
