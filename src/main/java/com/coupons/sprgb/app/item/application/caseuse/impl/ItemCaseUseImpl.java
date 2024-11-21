package com.coupons.sprgb.app.item.application.caseuse.impl;

import com.coupons.sprgb.app.item.application.caseuse.ItemCaseUse;
import com.coupons.sprgb.app.item.application.service.ItemService;
import com.coupons.sprgb.app.item.domain.dto.ResponseOptimalListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ItemCaseUseImpl implements ItemCaseUse {
    private final ItemService itemService;

    @Override
    public ResponseOptimalListDto getOptimalList(List<String> itemIds, Float amount) {
        Map<String, Float> itemPrices = itemService.getPrices(itemIds);
        List<String> optimalItemList = itemService.calculate(itemPrices, amount);
        Float optimalWaste = optimalItemList.stream().map(optimalItem -> itemPrices.get(optimalItem)).reduce(0f, (accumPrice, itemPrice) -> accumPrice + itemPrice);

        return ResponseOptimalListDto.builder().total(optimalWaste).itemIds(optimalItemList).build();
    }
}
