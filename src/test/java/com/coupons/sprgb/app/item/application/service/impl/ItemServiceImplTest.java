package com.coupons.sprgb.app.item.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
public class ItemServiceImplTest {

    @Test
    void test(){
        Map<String, Float> items = new HashMap<>();
        items.put("MLA1", 100.0f);
        items.put("MLA2", 210.0f);
        items.put("MLA4", 80.0f);
        items.put("MLA5", 90.0f);
        Float amount = 500.0f;
        /*ItemServiceImpl itemService = new ItemServiceImpl();
        List<String> optimized= itemService.calculate(items,amount);
        log.info("{}",optimized);*/
    }
}
