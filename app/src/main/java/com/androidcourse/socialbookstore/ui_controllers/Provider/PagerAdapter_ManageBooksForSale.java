package com.androidcourse.socialbookstore.ui_controllers.Provider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter_ManageBooksForSale extends FragmentStatePagerAdapter {

    private int amountOfTabs;
    private Bundle bundle;

    private Fragment_AddBooksForSale fragment_addBooksForSale;
    private Fragment_EditBooksForSale fragment_editBooksForSale;
    private Fragment_DeleteBooksForSale fragment_deleteBooksForSale;
    public PagerAdapter_ManageBooksForSale(FragmentManager fragmentManager, int amountOfTabs, String infoForFragments) {

        super(fragmentManager);
        this.amountOfTabs = amountOfTabs;
        this.bundle=new Bundle();
        this.bundle.putString("providerID", infoForFragments);

        this.fragment_addBooksForSale= new Fragment_AddBooksForSale();
        this.fragment_editBooksForSale= new Fragment_EditBooksForSale();
        this.fragment_deleteBooksForSale= new Fragment_DeleteBooksForSale();
        fragment_addBooksForSale.setArguments(bundle);
        fragment_editBooksForSale.setArguments(bundle);
        fragment_deleteBooksForSale.setArguments(bundle);
        fragment_addBooksForSale.setPagerAdapter(this);
        fragment_deleteBooksForSale.setPagerAdapter(this);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return fragment_addBooksForSale;
            case 1: return fragment_editBooksForSale;
            case 2: return fragment_deleteBooksForSale;
            default:return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {return POSITION_NONE;}
    @Override
    public int getCount() {
        return amountOfTabs;
    }
}