package com.coupons.sprgb.app.item.infrastructure.inbound.controllers;

import com.coupons.sprgb.app.item.domain.client.ItemAPIClient;
import com.coupons.sprgb.app.item.domain.dto.GetOptimalListDto;
import com.coupons.sprgb.app.item.domain.dto.ItemDto;
import com.coupons.sprgb.app.item.infrastructure.outbound.external.MLIFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemAPIClient meliClient;
    @GetMapping("/optimalList")
    public ResponseEntity<List<ItemDto>>getOptimalItemList(@RequestBody GetOptimalListDto getOptimalListDto){
        log.info("{}",meliClient.getItemData("MLA1448885331"));
        return null;
    }
}