package com.negodya1.customizablemaxdurability.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Mixin(value = ItemStack.class, priority = 400)
public class ItemMixin {

    @Inject(method = "getMaxDamage", at = @At("HEAD"), cancellable = true)
    public void customDurability_getMaxDamage(CallbackInfoReturnable<Integer> cir) {
        ItemStack ths = (ItemStack) (Object) this;

        if (ths.hasTag())
            if (ths.getTag().contains("MaxDamage"))
                cir.setReturnValue(ths.getTag().getInt("MaxDamage"));
    }

    @Inject(method = "getBarWidth", at = @At("HEAD"), cancellable = true)
    public void customDurability_getBarWidth(CallbackInfoReturnable<Integer> cir) {
        ItemStack ths = (ItemStack) (Object) this;

        if (ths.hasTag())
            if (ths.getTag().contains("MaxDamage"))
                cir.setReturnValue(Math.round(13.0F - (float)ths.getDamageValue() * 13.0F / ths.getTag().getInt("MaxDamage")));
    }

    @Inject(method = "getBarColor", at = @At("HEAD"), cancellable = true)
    public void customDurability_getBarColor(CallbackInfoReturnable<Integer> cir) {
        ItemStack ths = (ItemStack) (Object) this;

        if (ths.hasTag())
            if (ths.getTag().contains("MaxDamage")) {
                float stackMaxDamage = ths.getTag().getInt("MaxDamage");
                float f = Math.max(0.0F, (stackMaxDamage - (float)ths.getDamageValue()) / stackMaxDamage);
                cir.setReturnValue(Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F));
            }
    }

}
