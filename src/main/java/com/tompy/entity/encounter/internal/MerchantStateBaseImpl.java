package com.tompy.entity.encounter.internal;

import com.tompy.entity.encounter.api.Merchant;

import java.util.Objects;

public abstract class MerchantStateBaseImpl {
    protected final Merchant merchant;

    public MerchantStateBaseImpl(Merchant merchant) {
        this.merchant = Objects.requireNonNull(merchant, "Merchant cannot be null.");
    }
}
