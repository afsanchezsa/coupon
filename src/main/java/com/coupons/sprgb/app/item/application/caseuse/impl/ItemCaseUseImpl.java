package com.coupons.sprgb.app.item.application.caseuse.impl;

import com.coupons.sprgb.app.item.application.caseuse.ItemCaseUse;
import com.coupons.sprgb.app.item.application.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
@RequiredArgsConstructor
public class ItemCaseUseImpl implements ItemCaseUse {
    private final ItemService itemService;

    @Override
    public List<String> getOptimalList(List<String> itemIds, Float amount) {
        Map<String,Float>itemPrices=itemService.getPrices(itemIds);
        return itemService.calculate(itemPrices,amount);
    }
}
