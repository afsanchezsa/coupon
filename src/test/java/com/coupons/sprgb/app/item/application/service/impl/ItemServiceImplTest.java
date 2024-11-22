package com.coupons.sprgb.app.item.application.service.impl;

import com.coupons.sprgb.app.item.domain.client.ItemAPIClient;
import com.coupons.sprgb.app.item.domain.dto.ItemDto;
import com.coupons.sprgb.app.item.infrastructure.outbound.external.MLIFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

@Slf4j
@SpringBootTest
public class ItemServiceImplTest {

    @Autowired
    private ItemServiceImpl itemService;
    @MockBean
    private ItemAPIClient itemAPIClient;
    @MockBean
    private MLIFeignClient mlClient;

    @Test
    void testDummyCase(){
        Map<String, Float> items = new HashMap<>();
        items.put("MLA1", 100.0f);
        items.put("MLA2", 210.0f);
        items.put("MLA3", 260.0f);
        items.put("MLA4", 80.0f);
        items.put("MLA5", 90.0f);
        Float amount = 500.0f;

        List<String> optimized= itemService.calculate(items,amount);
        assertEquals(4,optimized.size());
        assertTrue(optimized.contains("MLA1"));
        assertTrue(optimized.contains("MLA5"));
        assertTrue(optimized.contains("MLA4"));
        assertTrue(optimized.contains("MLA2"));
        log.info("optimized: {}",optimized);
    }
    @Test
    void testNotGreedyAlgorithm(){
        Map<String, Float> items = new HashMap<>();
        items.put("MLA1", 1f);
        items.put("MLA2", 1f);
        items.put("MLA3", 1f);
        items.put("MLA4", 1f);
        items.put("MLA5", 7.0f);
        Float amount = 10.0f;

        List<String> optimized= itemService.calculate(items,amount);
        assertTrue(optimized.contains("MLA5"));
        log.info("{}",optimized);
    }
    @Test
    void testResidualPainLimit(){
        Map<String, Float> items = new HashMap<>();
        items.put("MLA1", 0.9f);
        items.put("MLA2", 0.9f);
        items.put("MLA3", 0.9f);
        items.put("MLA4", 0.9f);
        items.put("MLA5", 0.9f);
        items.put("MLA6", 0.9f);
        items.put("MLA7", 0.9f);
        items.put("MLA8", 0.9f);
        items.put("MLA9", 0.9f);
        items.put("MLA10", 0.9f);
        items.put("MLA11", 0.9f);
        Float amount = 10.0f;

        List<String> optimized= itemService.calculate(items,amount);
        assertEquals(11,optimized.size());
        log.info("{}",optimized);
    }
    @Test
    void testGetPrices(){
        List<String> itemIds = Arrays.asList("item1", "item2", "item3", null, "", "item4");

        ItemDto item1 = ItemDto.builder().id("item1").price(10.5f).build();
        ItemDto item2 = ItemDto.builder().id("item2").price(20.3f).build();
        ItemDto item3 = ItemDto.builder().id("item3").price(5.7f).build();
        ItemDto item4 = ItemDto.builder().id("item4").price(15.0f).build();





        when(mlClient.getItemData("item1")).thenReturn(Optional.of(item1));
        when(mlClient.getItemData("item2")).thenReturn(Optional.of(item2));
        when(mlClient.getItemData("item3")).thenReturn(Optional.of(item3));
        when(mlClient.getItemData("item4")).thenReturn(Optional.of(item4));
        when(mlClient.getItemData(null)).thenReturn(Optional.empty());
        when(mlClient.getItemData("")).thenReturn(Optional.empty());


        Map<String, Float> prices = itemService.getPrices(itemIds);


        assertEquals(4, prices.size());
        assertEquals(10.5f, prices.get("item1"));
        assertEquals(20.3f, prices.get("item2"));
        assertEquals(5.7f, prices.get("item3"));
        assertEquals(15.0f, prices.get("item4"));

        // Verify interactions with API client
        verify(mlClient, times(1)).getItemData("item1");
        verify(mlClient, times(1)).getItemData("item2");
        verify(mlClient, times(1)).getItemData("item3");
        verify(mlClient, times(1)).getItemData("item4");
        verify(mlClient, times(0)).getItemData(null);
        verify(mlClient, times(0)).getItemData("");
    }
}
