package me.canzhao.itemtrails;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BottomSheetFragment extends BottomSheetDialogFragment{

    public ArrayList<PoiItem> pois;
    public BottomSheetFragment(ArrayList<PoiItem> pois) {
        this.pois = pois;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ListView ListView = view.findViewById(R.id.listv);
        final List<String> pois_name = new ArrayList<String>();
        for(int i = 0; i<pois.size(); i++){
            pois_name.add(pois.get(i).getItemName());
        }

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, pois_name);


        // DataBind ListView with items from ArrayAdapter
        ListView.setAdapter(adapter);

        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Object clickItemObj = adapterView.getAdapter().getItem(index);

                Intent itemIntent = new Intent(getActivity(), ItemsActivity.class);
                itemIntent.putExtra("data", pois.get(index));
                startActivity(itemIntent);
                //Toast.makeText(getActivity().getApplicationContext(), "You clicked " + clickItemObj.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
