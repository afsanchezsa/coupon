package com.coupons.sprgb.app.item.infrastructure.outbound.external.config;

import com.coupons.sprgb.app.item.domain.client.ItemAPIClient;
import com.coupons.sprgb.app.item.domain.dto.ItemDto;
import com.coupons.sprgb.app.item.infrastructure.outbound.external.MLIFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@Slf4j
public class ItemFallbackFactory implements FallbackFactory<MLIFeignClient> {
    @Override
    public MLIFeignClient create(Throwable cause) {
        return new MLIFeignClient() {
            @Override
            public Optional<ItemDto> getItemData(String itemId) {
                log.error("error requesting item api:{} for itemId:{}",cause,itemId);
                return Optional.empty();
            }
        };
    }
}
