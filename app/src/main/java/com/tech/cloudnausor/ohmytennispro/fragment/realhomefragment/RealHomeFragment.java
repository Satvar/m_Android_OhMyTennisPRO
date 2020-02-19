package com.tech.cloudnausor.ohmytennispro.fragment.realhomefragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.realhomepage.RealMainPageActivity;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Constant;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Menus;

public class RealHomeFragment extends Fragment {


    private boolean mSearchCheck;
    private TextView mTxtDownload;
    ViewGroup viewGroup;
    View dialogView;

    public RealHomeFragment newInstance(String text){
        RealHomeFragment mFragment = new RealHomeFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(Constant.TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.content_home, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        SearchView searchView = (SearchView) (menu.findItem(Menus.SEARCH).getActionView());
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText)searchView.findViewById(androidx.appcompat.R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(OnQuerySearchView);
        menu.findItem(Menus.ADD).setVisible(true);
        menu.findItem(Menus.EDIT).setVisible(false);
        menu.findItem(Menus.UPDATE).setVisible(false);
        menu.findItem(Menus.SEARCH).setVisible(false);

        mSearchCheck = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {

            case Menus.ADD:
//                Rect displayRectangle = new Rect();
//                Window window = getActivity().getWindow();
//                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
//                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.DialogTheme);
//                viewGroup = getView().findViewById(android.R.id.content);
//                dialogView = LayoutInflater.from(this.getContext()).inflate(R.layout.layout_avaibility_calender, viewGroup, false);
//                dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
//                dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
//
//                builder.setView(dialogView);
//                final AlertDialog alertDialog = builder.create();
//                alertDialog.show();
                break;


            case Menus.UPDATE:
                break;

            case Menus.SEARCH:
                mSearchCheck = true;
                break;
        }
        return true;
    }

    private SearchView.OnQueryTextListener OnQuerySearchView = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String arg0) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean onQueryTextChange(String arg0) {
            // TODO Auto-generated method stub
            if (mSearchCheck){
                // implement your search here
            }
            return false;
        }
    };

}
