package com.rex.condominio.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.rex.condominio.fragments.tienda.VentasListasFragment;

public class VentaPagerAdapter extends FragmentStateAdapter {

    private String type;

    public VentaPagerAdapter(@NonNull FragmentActivity fragmentActivity, String type) {
        super(fragmentActivity);
        this.type = type;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0: fragment = new VentasListasFragment("1", type); break;
            case 1: fragment = new VentasListasFragment("2", type); break;
            case 2: fragment = new VentasListasFragment("3", type); break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
