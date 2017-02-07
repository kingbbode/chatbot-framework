package com.kingbbode.bot.util;

import com.kingbbode.bot.common.base.cell.AbstractBrainCell;

import java.util.*;

/**
 * Created by YG on 2016-04-12.
 */
public class BrainUtil {
    public static List sortByValue(Map<String, String> map) {
        List<String> list = new ArrayList();
        list.addAll(map.keySet());

        Collections.sort(list, new Comparator() {

            public int compare(Object o1, Object o2) {
                int n1 = Integer.parseInt(map.get(o1).split("@")[0]);
                int n2 = Integer.parseInt(map.get(o2).split("@")[0]);

                return n1 > n2 ? -1 : 1;
            }
        });
        Collections.reverse(list);
        return list;
    }


    public static <T extends AbstractBrainCell> String explainDetail(Set<Map.Entry<String, T>> entrySet) {
        StringBuilder stringBuilder = new StringBuilder();
        entrySet.stream()
                .forEach(entry -> {
                            stringBuilder.append(entry.getKey());
                            stringBuilder.append(" : ");
                            stringBuilder.append(entry.getValue().explain());
                            stringBuilder.append("\n");
                        }
                );
        return stringBuilder.toString();
    }

    public static <T extends AbstractBrainCell> String explainSimple(Set<Map.Entry<String, T>> entrySet) {
        StringBuilder stringBuilder = new StringBuilder();
        entrySet.stream()
                .forEach(entry -> {
                            stringBuilder.append(entry.getKey());
                            stringBuilder.append("\n");
                        }
                );
        return stringBuilder.toString();
    }
    
    public static String explainForKnowledge(Set<Map.Entry<String, List<String>>> entrySet){
        StringBuilder stringBuilder = new StringBuilder();
        entrySet.stream()
                .forEach(entry ->{
                    stringBuilder.append(entry.getKey());
                    stringBuilder.append(" - 지식깊이 : ");
                    stringBuilder.append(entry.getValue().size());
                    stringBuilder.append("\n");
                });
        
        return stringBuilder.toString();
    }
}
