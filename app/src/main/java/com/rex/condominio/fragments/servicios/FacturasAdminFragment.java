package com.rex.condominio.fragments.servicios;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.rex.condominio.R;
import com.rex.condominio.adapters.PagerFacturasAdminAdapter;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FacturasAdminFragment extends Fragment {

    private TabLayout tab_facturas;
    private ViewPager2 viewPager;
    private int idSer;

    public FacturasAdminFragment(int idSer){
        this.idSer = idSer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_facturas_admin, container, false);

        tab_facturas = v.findViewById(R.id.tab_facturas);
        viewPager = v.findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerFacturasAdminAdapter(this, idSer));
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tab_facturas.getTabAt(position).select();
            }
        });
        tab_facturas.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }
}