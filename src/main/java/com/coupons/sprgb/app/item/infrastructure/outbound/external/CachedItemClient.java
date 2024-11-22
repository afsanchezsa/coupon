package com.coupons.sprgb.app.item.infrastructure.outbound.external;

import com.coupons.sprgb.app.item.domain.client.ItemAPIClient;
import com.coupons.sprgb.app.item.domain.dto.ItemDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Optional;

@Component("cachedItemClient")
@RequiredArgsConstructor
@Slf4j
public class CachedItemClient implements ItemAPIClient {
    private final MLIFeignClient mlClient;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    @Value("${item.cache.key:ITEM_%s}")
    private String itemKey;



    @Override
    public Optional<ItemDto> getItemData(String itemId)  {

        if (itemId == null)
            return Optional.empty();
        String cacheKey=String.format(itemKey, itemId);
        String itemStr =null;

            itemStr = redisService.get(cacheKey);


        if(itemStr!=null){
            ItemDto cachedItem = castToClass(itemStr, ItemDto.class);
            if (cachedItem != null) {
                return Optional.of(cachedItem);
            }
        }

        Optional<ItemDto> itemDto =mlClient.getItemData(itemId);
        if(itemDto.isPresent()){
            cacheItemDto(cacheKey,itemDto.get());
        }
        return itemDto;

    }

    private void cacheItemDto(String cacheKey,ItemDto itemDto){
        try {
            redisService.set(cacheKey,objectMapper.writeValueAsString(itemDto), Duration.ofMinutes(15));
        } catch (JsonProcessingException e) {
            log.error("error trying to cache key:{}",cacheKey);
        }
    }

    private <T> T castToClass(String strRepresentation, Class<T> clazz) {

        if (strRepresentation == null) {
            return null;
        }
        try {
            return objectMapper.readValue(strRepresentation, clazz);
        } catch (JsonProcessingException e) {
            log.error("error casting item cache:{}",e.getMessage());
            return null;
        }

    }

}
