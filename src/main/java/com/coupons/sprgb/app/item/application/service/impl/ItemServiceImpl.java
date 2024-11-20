package com.coupons.sprgb.app.item.application.service.impl;

import com.coupons.sprgb.app.item.application.service.ItemService;
import com.coupons.sprgb.app.item.domain.client.ItemAPIClient;
import com.coupons.sprgb.app.item.domain.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemAPIClient itemAPIClient;

    @Override
    public List<String> calculate(Map<String, Float> items, Float amount) {


        List<Map.Entry<String, Float>> itemList = new ArrayList<>(items.entrySet());

        itemList.sort(Map.Entry.comparingByValue());

        List<String> selectedItems = new ArrayList<>();
        float total = 0;


        for (Map.Entry<String, Float> item : itemList) {
            if (total + item.getValue() <= amount) {
                selectedItems.add(item.getKey());
                total += item.getValue();
            } else {
                break;
            }
        }

        return selectedItems;

    }

    @Override
    public Map<String, Float> getPrices(List<String> itemIds) {
        List<ItemDto> itemList = itemIds.parallelStream()
                .filter(itemId -> itemId != null && !itemId.isBlank())
                .map(itemId -> itemAPIClient.getItemData(itemId))
                .filter(optionalItem-> optionalItem.isPresent())
                .map(optionalItem->optionalItem.get()).collect(Collectors.toList());
        return itemList.stream()
                .collect(Collectors.toMap(ItemDto::getId, ItemDto::getPrice));
    }
}
