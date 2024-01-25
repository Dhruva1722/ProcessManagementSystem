package com.example.processscannerstockmarket.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.processscannerstockmarket.Channel3;
import com.example.processscannerstockmarket.ChannelsActivity.Channel1;
import com.example.processscannerstockmarket.ChannelsActivity.Channel2;
import com.example.processscannerstockmarket.MainActivity;
import com.example.processscannerstockmarket.R;
import com.example.processscannerstockmarket.ScannerAdapter;
import com.example.processscannerstockmarket.ScannerData;

import java.util.ArrayList;
import java.util.List;


public class DeviceFragment extends Fragment {
    private TextView relay1,relay2, relay3,relay4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device, container, false);


        ListView listView = view.findViewById(R.id.scannerList);

        List<ScannerData> scannerItemList = new ArrayList<>();

        scannerItemList.add(new ScannerData("J Type", "300.00C\u00B0", "channel 1"));
        scannerItemList.add(new ScannerData("K Type ", "30.00C\u00B0", "Channel 2"));
        scannerItemList.add(new ScannerData("PT/PT.1", "410.00C\u00B0", "Channel 3"));
        scannerItemList.add(new ScannerData("J Type", "320.00C\u00B0", "Channel 4"));
        scannerItemList.add(new ScannerData("0-10V", "110.00 V", "Channel 5"));
        scannerItemList.add(new ScannerData("4-20mA", "200.01 mA", "Channel 6"));
        scannerItemList.add(new ScannerData("PT/PT.1", "220.00C\u00B0", "Channel 7"));
        scannerItemList.add(new ScannerData("0-10V", "000.00V", "Channel 8"));
        scannerItemList.add(new ScannerData("0-20mA", "310.00mA", "Channel 9"));
        scannerItemList.add(new ScannerData("K Type", "310.00C\u00B0", "Channel 10"));
        scannerItemList.add(new ScannerData("0-20mA", "310.00mA", "Channel 11"));
        scannerItemList.add(new ScannerData("J Type ", "100.00C\u00B0", "Channel 12"));
        scannerItemList.add(new ScannerData("PT/PT.1", "410.00C\u00B0", "Channel 13"));
        scannerItemList.add(new ScannerData("0-20mA", "310.55mA", "Channel 14"));
        scannerItemList.add(new ScannerData("J Type ", "205.00C\u00B0", "Channel 15"));
        scannerItemList.add(new ScannerData("K Type", "180.00C\u00B0", "Channel 16"));


        ScannerAdapter adapter = new ScannerAdapter(requireContext(), scannerItemList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int channelNumber = position + 1;

                // Launch the corresponding activity for the clicked channel
                Class<?> channelActivityClass = getChannelActivityClass(channelNumber);
                if (channelActivityClass != null) {
                    Intent intent = new Intent(requireContext(), channelActivityClass);
                    startActivity(intent);
                }
            }
        });


        relay1 = view.findViewById(R.id.r1);
        relay2 = view.findViewById(R.id.r2);
        relay3 = view.findViewById(R.id.r3);
        relay4 = view.findViewById(R.id.r4);
        Handler handler = new Handler();


        final List<TextView> relayTextViews = new ArrayList<>();
        relayTextViews.add(relay1);
        relayTextViews.add(relay2);
        relayTextViews.add(relay3);
        relayTextViews.add(relay4);


        final int textColor = Color.BLACK;
        final long[] blinkIntervals = {500, 1000, 1500, 2000};

        for (int i = 0; i < relayTextViews.size(); i++) {
            final TextView currentRelay = relayTextViews.get(i);

            int finalI = i;
            Runnable blinkRunnable = new Runnable() {
                @Override
                public void run() {

                    currentRelay.setTextColor(currentRelay.getTextColors().getDefaultColor() == textColor ?
                            Color.RED : textColor);

                    handler.postDelayed(this, blinkIntervals[finalI]);
                }
            };
            handler.post(blinkRunnable);
        }


        return view;
    }

    private Class<?> getChannelActivityClass(int channelNumber) {
        switch (channelNumber) {
            case 1:
                return Channel1.class;
            case 2:
                return Channel2.class;
            case 3:
                return Channel3.class;
            default:
                return null;
        }
    }


}
