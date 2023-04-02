package com.rex.condominio.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.rex.condominio.activities.FacturasActivity;
import com.rex.condominio.fragments.servicios.PagerFacturaFragment;
import com.rex.condominio.utils.TipoFactura;

public class PagerFacturasAdapter extends FragmentStateAdapter {
    public PagerFacturasAdapter(@NonNull FacturasActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0: fragment = new PagerFacturaFragment(TipoFactura.PENDIENTE); break;
            case 1: fragment = new PagerFacturaFragment(TipoFactura.CONFIRMADA); break;
            case 2: fragment = new PagerFacturaFragment(TipoFactura.CANCELADA); break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
