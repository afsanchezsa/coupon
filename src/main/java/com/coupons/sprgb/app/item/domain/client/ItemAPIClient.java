package com.coupons.sprgb.app.item.domain.client;

import com.coupons.sprgb.app.item.domain.dto.ItemDto;

public interface ItemAPIClient {
    ItemDto getItemData(String itemId);
}
