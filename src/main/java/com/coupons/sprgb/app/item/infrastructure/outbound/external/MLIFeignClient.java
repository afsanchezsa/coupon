package com.coupons.sprgb.app.item.infrastructure.outbound.external;

import com.coupons.sprgb.app.item.domain.client.ItemAPIClient;
import com.coupons.sprgb.app.item.domain.dto.ItemDto;
import com.coupons.sprgb.app.item.infrastructure.outbound.external.config.ItemFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(name = "MLIFeignClient",url = "${meli.api.client.url}",fallbackFactory = ItemFallbackFactory.class)
public interface MLIFeignClient extends ItemAPIClient {
    @Override
    @RequestMapping(method = RequestMethod.GET,value = "/items/{itemId}")
    Optional<ItemDto> getItemData(@PathVariable("itemId") String itemId);
}
