package com.coupons.sprgb.app.item.infrastructure.outbound.external;

import com.coupons.sprgb.app.item.domain.client.ItemAPIClient;
import com.coupons.sprgb.app.item.domain.dto.ItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "MLIFeignClient",url = "${meli.api.client.url}")
public interface MLIFeignClient extends ItemAPIClient {
    @Override
    @RequestMapping(method = RequestMethod.GET,value = "/items/{itemId}")
    ItemDto getItemData(@PathVariable("itemId") String itemId);
}
