package com.coupons.sprgb.app.item.application.caseuse.impl;


import com.coupons.sprgb.app.item.domain.client.ItemAPIClient;
import com.coupons.sprgb.app.item.domain.dto.ItemDto;
import com.coupons.sprgb.app.item.domain.dto.ResponseOptimalListDto;
import com.coupons.sprgb.app.item.infrastructure.outbound.external.MLIFeignClient;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@Slf4j
public class ItemCaseUseImplTest {
    @Autowired
    private ItemCaseUseImpl itemCaseUseImpl;

    @MockBean
    private ItemAPIClient itemAPIClient;

    @MockBean
    private MLIFeignClient mlClient;

@Test
    void testPainLimit(){
        Float amount = 10.0f;

        when(mlClient.getItemData("MLA1")).thenReturn(Optional.of(ItemDto.builder().id("MLA1").price(0.9f).build()));
        when(mlClient.getItemData("MLA2")).thenReturn(Optional.of(ItemDto.builder().id("MLA2").price(0.9f).build()));
        when(mlClient.getItemData("MLA3")).thenReturn(Optional.of(ItemDto.builder().id("MLA3").price(0.9f).build()));
        when(mlClient.getItemData("MLA4")).thenReturn(Optional.of(ItemDto.builder().id("MLA4").price(0.9f).build()));
        when(mlClient.getItemData("MLA5")).thenReturn(Optional.of(ItemDto.builder().id("MLA5").price(0.9f).build()));
        when(mlClient.getItemData("MLA6")).thenReturn(Optional.of(ItemDto.builder().id("MLA6").price(0.9f).build()));
        when(mlClient.getItemData("MLA7")).thenReturn(Optional.of(ItemDto.builder().id("MLA7").price(0.9f).build()));
        when(mlClient.getItemData("MLA8")).thenReturn(Optional.of(ItemDto.builder().id("MLA8").price(0.9f).build()));
        when(mlClient.getItemData("MLA9")).thenReturn(Optional.of(ItemDto.builder().id("MLA9").price(0.9f).build()));
        when(mlClient.getItemData("MLA10")).thenReturn(Optional.of(ItemDto.builder().id("MLA10").price(0.9f).build()));
        when(mlClient.getItemData("MLA11")).thenReturn(Optional.of(ItemDto.builder().id("MLA11").price(0.9f).build()));
        List<String>itemdIds=List.of("MLA1","MLA2","MLA3","MLA4","MLA5","MLA6","MLA7","MLA8","MLA9","MLA10","MLA11");
        ResponseOptimalListDto response=itemCaseUseImpl.getOptimalList(itemdIds,amount);
        log.info("{}",response);
        assertEquals(9.9f,response.getTotal());

    }
    @Test
    void testEmptyList(){
        Float amount = 8.0f;

        when(mlClient.getItemData("MLA1")).thenReturn(Optional.of(ItemDto.builder().id("MLA1").price(10f).build()));
        when(mlClient.getItemData("MLA2")).thenReturn(Optional.of(ItemDto.builder().id("MLA2").price(30f).build()));

        List<String>itemdIds=List.of("MLA1","MLA2");
        ResponseOptimalListDto response=itemCaseUseImpl.getOptimalList(itemdIds,amount);
        log.info("{}",response);
        assertEquals(0f,response.getTotal());
        assertTrue(response.getItemIds().isEmpty());
    }
}
