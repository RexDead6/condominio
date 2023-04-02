package com.rex.condominio.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.rex.condominio.fragments.servicios.FacturasAdminFragment;
import com.rex.condominio.fragments.servicios.PagerFacturaAdminFragment;
import com.rex.condominio.utils.TipoFactura;

public class PagerFacturasAdminAdapter extends FragmentStateAdapter {

    private int idSer;

    public PagerFacturasAdminAdapter(@NonNull FacturasAdminFragment fragmentActivity, int idSer) {
        super(fragmentActivity);
        this.idSer = idSer;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0: fragment = new PagerFacturaAdminFragment(TipoFactura.PENDIENTE, idSer); break;
            case 1: fragment = new PagerFacturaAdminFragment(TipoFactura.CONFIRMADA, idSer); break;
            case 2: fragment = new PagerFacturaAdminFragment(TipoFactura.CANCELADA, idSer); break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
