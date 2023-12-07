package com.marcelo.java;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.marcelo.java.fragments.FragmentAddProduct;
import com.marcelo.java.fragments.FragmentManagerProduct;
import com.marcelo.java.fragments.FragmentStatisticsMarket;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    private boolean pageSlid, menuSelected;

    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBottomNavigationView();
        setFragmentsPager();
    }

    private void setFragmentsPager() {// Configuarar páginas e fragments
        viewPager = findViewById(R.id.fragments_content);

        FragmentAddProduct fragmentAddProduct = new FragmentAddProduct();
        FragmentManagerProduct fragmentManagerProduct = new FragmentManagerProduct();
        FragmentStatisticsMarket fragmentStatisticsMarket = new FragmentStatisticsMarket();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(fragmentAddProduct);
        fragments.add(fragmentManagerProduct);
        fragments.add(fragmentStatisticsMarket);

        pagerAdapter = new PagerAdapter(this, fragments);

        viewPager.setAdapter(pagerAdapter);
        setCallbackPager();
    }

    private void setCallbackPager() { // Callback para saber quando um fragment específico foi selecionado
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (menuSelected){
                    Log.i("LogMain", "Menu foi selecionado");
                    menuSelected = false;
                    return;
                }
                pageSlid = true; // Se passou pela condição acima, o usuário deslizou

                int menuSelected = bottomNavigationView.getSelectedItemId();
                if (position == 1) {
                    Log.i("LogMain", "swiped: manager");
                    if (menuSelected != R.id.menu_main_manager) {
                        bottomNavigationView.setSelectedItemId(R.id.menu_main_manager);
                    }
                } else if (position == 2) {
                    Log.i("LogMain", "swiped: statistics");
                    if (menuSelected != R.id.menu_main_statistics) {
                        bottomNavigationView.setSelectedItemId(R.id.menu_main_statistics);
                    }
                } else
                if (menuSelected != R.id.menu_main_add) {
                    Log.i("LogMain", "swiped: Add");
                    bottomNavigationView.setSelectedItemId(R.id.menu_main_add);
                }
            }
        });
    }

    public void setBottomNavigationView() { // Configurar BottomNavigationView do activity_main.xml
        bottomNavigationView = findViewById(R.id.nav_buttonNavigation);
        bottomNavigationView.setOnItemSelectedListener(items -> {
            int item = items.getItemId();
            if (pageSlid){
                Log.i("LogMain", "the user swipes the page");
                pageSlid = false;
                return true;
            }
            menuSelected = true;
            int selected = 0; // Default click FragmentAddProduct
            if (item == R.id.menu_main_manager) {
                selected = 1;
            } else if (item == R.id.menu_main_statistics) {
                selected = 2;
            }
            viewPager.setCurrentItem(selected);
            Log.i("LogMain", "Fragment: " + pagerAdapter.getFragmentPager().get(selected).getTag());
            return true;
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
