package com.coupons.sprgb.app.item.application.service.impl;

import com.coupons.sprgb.app.item.application.service.ItemService;
import com.coupons.sprgb.app.item.domain.client.ItemAPIClient;
import com.coupons.sprgb.app.item.domain.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {



    //
    //@Qualifier("cachedItemClient")
    //@Qualifier("MLIFeignClientFeignClient")
    private  ItemAPIClient itemAPIClient;

    public ItemServiceImpl(@Qualifier("cachedItemClient")ItemAPIClient itemAPIClient){
        this.itemAPIClient=itemAPIClient;
    }

    @Value("${item.service.precision:1f}")
    private Float precision;
    @Value("${item.service.active.pain.limit:false}")
    private Boolean activePainLimit;
    @Value("${item.service.pain.limit:1f}")
    private Float painLimit;

    @Override
    public List<String> calculate(Map<String, Float> items, Float amount) {
        validateInputs(items, amount);

        Map<String, Integer> roundedItems = calculateRoundedValues(items, precision);
        log.info("Rounded results: {}", roundedItems);

        int[] weights = extractWeights(roundedItems);
        List<String> productNames = new ArrayList<>(roundedItems.keySet());

        int coupon = calculateCoupon(amount, precision);
        log.info("Initial coupon: {}", coupon);

        if (activePainLimit) {
            Float residual = calculateResidual(roundedItems, items,precision);
            log.info("Residual: {}", residual);
            if (exceedsPainLimit(residual)) {
                coupon += (int) Math.floor(residual / precision);
            }
        }

        return optimalKnapSack(coupon, weights, weights, weights.length, productNames);
    }

    private void validateInputs(Map<String, Float> items, Float amount) {
        if (precision == null || precision == 0) {
            throw new IllegalArgumentException("Amount cannot be null or zero.");
        }

        if (items.values().contains(null)) {
            throw new IllegalArgumentException("Values in the map cannot be null.");
        }
    }

    private Map<String, Integer> calculateRoundedValues(Map<String, Float> items, Float precision) {
        return items.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            Float value = entry.getValue();
                            int ceilValue = (int) Math.ceil(value / precision);
                            log.info("Value: {}, Precision: {}, CeilValue: {}", value, precision, ceilValue);
                            return ceilValue;
                        }
                ));
    }

    private int[] extractWeights(Map<String, Integer> roundedItems) {
        return roundedItems.values().stream().mapToInt(Integer::intValue).toArray();
    }

    private int calculateCoupon(Float amount, Float precision) {
        return (int) Math.floor(amount / precision);
    }

    private Float calculateResidual(Map<String, Integer> roundedItems, Map<String, Float> originalItems,Float precision) {
        return roundedItems.entrySet().stream()
                .map(entry -> (entry.getValue()*precision) - originalItems.get(entry.getKey()))
                .reduce(0f, Float::sum);
    }

    private boolean exceedsPainLimit(Float residual) {
        return residual > painLimit;
    }

    private List<String> optimalKnapSack(int W, int wt[],
                                         int val[], int n, List<String> listProductNames) {
        List<String> result = new ArrayList<>();
        int i, w;
        int K[][] = new int[n + 1][W + 1];


        for (i = 0; i <= n; i++) {
            for (w = 0; w <= W; w++) {
                if (i == 0 || w == 0)
                    K[i][w] = 0;
                else if (wt[i - 1] <= w)
                    K[i][w] = Math.max(val[i - 1] +
                            K[i - 1][w - wt[i - 1]], K[i - 1][w]);
                else
                    K[i][w] = K[i - 1][w];
            }
        }


        int res = K[n][W];


        w = W;
        for (i = n; i > 0 && res > 0; i--) {


            if (res == K[i - 1][w])
                continue;
            else {


                result.add(listProductNames.get(i - 1));
                res = res - val[i - 1];
                w = w - wt[i - 1];
            }
        }

        return result;
    }


    @Override
    public Map<String, Float> getPrices(List<String> itemIds) {
        List<ItemDto> itemList = itemIds.parallelStream()
                .filter(itemId -> itemId != null && !itemId.isBlank())
                .map(itemId -> itemAPIClient.getItemData(itemId))
                .filter(optionalItem -> optionalItem.isPresent())
                .map(optionalItem -> optionalItem.get()).collect(Collectors.toList());

        return itemList.stream()
                .collect(Collectors.toMap(ItemDto::getId, ItemDto::getPrice));
    }
}
