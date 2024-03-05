package com.drake.brv.layoutmanager;

import android.util.Pair;

import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcatAdapterUtils {

    public static Pair<? extends RecyclerView.Adapter<?>,Integer> offsetPositionOnAdapter(RecyclerView.Adapter<?> adapter, int position){
        if (adapter instanceof ConcatAdapter){
            return offsetPositionOnConcatAdapter((ConcatAdapter) adapter,position);
        }
        return new Pair<>(adapter,position);
    }

    public static Pair<? extends RecyclerView.Adapter<?>,Integer> offsetPositionOnConcatAdapter(ConcatAdapter concatAdapter, int position){
        for (Map.Entry<RecyclerView.Adapter<?>,Integer> entry : getReverseList(getAllAdapterStartPositionMap(concatAdapter.getAdapters(),new AtomicInteger(0)).entrySet())){
            if (position+1 > entry.getValue()){
                return new Pair<>(entry.getKey(),position-entry.getValue());
            }
        }
        return new Pair<>(concatAdapter,position);
    }

    public static Map<RecyclerView.Adapter<?>,Integer> getAllAdapterStartPositionMap(List<? extends RecyclerView.Adapter<? extends RecyclerView.ViewHolder>> adapters, AtomicInteger startPosition){
        Map<RecyclerView.Adapter<?>,Integer> adapterStartPositionMap = new LinkedHashMap<>();
        for (RecyclerView.Adapter<?> adapter : adapters){
            if (adapter instanceof ConcatAdapter){
                adapterStartPositionMap.putAll(getAllAdapterStartPositionMap(((ConcatAdapter) adapter).getAdapters(),startPosition));
            } else {
                adapterStartPositionMap.put(adapter,startPosition.get());
                startPosition.set(startPosition.get()+adapter.getItemCount());
            }
        }
        return adapterStartPositionMap;
    }

    public static int getBeforeCount(ConcatAdapter concatAdapter, RecyclerView.Adapter<?> dest){
        AtomicInteger count = new AtomicInteger(0);
        if (findAndCountBefore(concatAdapter,dest,count)){
            return count.get();
        }
        return 0;
    }

    public static boolean findAndCountBefore(ConcatAdapter concatAdapter, RecyclerView.Adapter<?> dest, AtomicInteger count){
        for (RecyclerView.Adapter<?> adapter : concatAdapter.getAdapters()) {
            if (adapter == dest) {
                return true;
            }
            if (adapter instanceof ConcatAdapter){
                if (findAndCountBefore((ConcatAdapter) adapter,dest, count)){
                    return true;
                }
            } else {
                count.set(count.get() + adapter.getItemCount());
            }
        }
        return false;
    }

    public static int getAfterCount(ConcatAdapter concatAdapter, RecyclerView.Adapter<?> dest){
        AtomicInteger count = new AtomicInteger(0);
        if (findAndCountAfter(concatAdapter,dest,count)){
            return count.get();
        }
        return 0;
    }

    public static boolean findAndCountAfter(ConcatAdapter concatAdapter, RecyclerView.Adapter<?> dest, AtomicInteger count){
        for (RecyclerView.Adapter<?> adapter : getReverseList(concatAdapter.getAdapters())) {
            if (adapter == dest) {
                return true;
            }
            if (adapter instanceof ConcatAdapter){
                if (findAndCountAfter((ConcatAdapter) adapter,dest, count)){
                    return true;
                }
            } else {
                count.set(count.get() + adapter.getItemCount());
            }
        }
        return false;
    }

    private static <T> List<T> getReverseList(Collection<T> list){
        List<T> reverseList = new ArrayList<>(list);
        Collections.reverse(reverseList);
        return reverseList;
    }

}
