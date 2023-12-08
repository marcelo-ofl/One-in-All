package com.marcelo.java;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.marcelo.java.fragments.FragmentAddProduct;
import com.marcelo.java.fragments.FragmentManagerProduct;
import com.marcelo.java.fragments.FragmentStatisticsMarket;
import com.marcelo.java.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    private boolean pageSlid, menuSelected = true;
    private PagerAdapter pagerAdapter;
    private RelativeLayout snackBar;
    private AppCompatButton snackBarAction;
    private AppCompatTextView snackBarMgs;
    private ProgressBar snackBarProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        snackBar = findViewById(R.id.layout_snackBar);
        snackBarAction = snackBar.findViewById(R.id.snack_btn);
        snackBarMgs = snackBar.findViewById(R.id.snack_mgs);
        snackBarProgress = snackBar.findViewById(R.id.snack_progress);

        setBottomNavigationView();
        setFragmentsPager();
        setSnackBarAction();
    }

    private void setFragmentsPager() { // Configuarar páginas e fragments
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

    private void setCallbackPager() { // Callback para saber quando um fragment específico foi selecionado da página
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (menuSelected) {
                    menuSelected = false;
                    return;
                }

                pageSlid = true; // Se passou pela condição acima, o usuário deslizou

                String fragment = null;
                int menuSelected = bottomNavigationView.getSelectedItemId();
                if (position == 1) {
                    if (menuSelected != R.id.menu_main_manager) {
                        fragment = "Gerenciador";
                        bottomNavigationView.setSelectedItemId(R.id.menu_main_manager);
                    }
                } else if (position == 2) {
                    if (menuSelected != R.id.menu_main_statistics) {
                        fragment = "Estatisticas";
                        bottomNavigationView.setSelectedItemId(R.id.menu_main_statistics);
                    }
                } else if (menuSelected != R.id.menu_main_add) {
                    fragment = "Adicionar";
                    bottomNavigationView.setSelectedItemId(R.id.menu_main_add);
                }
                setSnackBar("Deslizou para: " + fragment, "Fechar", false);
            }
        });
    }

    public void setBottomNavigationView() { // Configurar BottomNavigationView
        bottomNavigationView = findViewById(R.id.nav_buttonNavigation);
        bottomNavigationView.setOnItemSelectedListener(items -> {
            int item = items.getItemId();
            if (pageSlid) {
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
            if (viewPager.getCurrentItem() != selected) { // Mudar a pagina apenas se for diferente da atual
                viewPager.setCurrentItem(selected);
            }
            return true;
        });
    }

    public void setSnackBar(String mgs, String btnAction, boolean progress) {
        if (snackBar.getVisibility() == View.GONE){
            setVisibleSnackBar(View.VISIBLE);
        }

        snackBarMgs.setText(mgs);

        if (progress) {
            snackBarAction.setVisibility(View.GONE);
            snackBarProgress.setVisibility(View.VISIBLE);
        } else {
            snackBarProgress.setVisibility(View.GONE);
            snackBarAction.setText(btnAction);
        }
    }

    private void setSnackBarAction(){
        snackBarAction.setOnClickListener(view -> setVisibleSnackBar(View.GONE));
    }


    public void setVisibleSnackBar(int visibility) {
        Utils.setAnimation(this, visibility, snackBar);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
