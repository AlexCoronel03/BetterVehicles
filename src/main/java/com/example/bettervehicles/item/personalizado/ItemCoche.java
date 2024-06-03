package com.example.bettervehicles.item.personalizado;

import com.example.bettervehicles.entidad.EntidadMod;
import com.example.bettervehicles.entidad.personalizado.EntidadCoche;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class ItemCoche extends Item {

    int fuel;
    public ItemCoche(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide) {
            BlockPos bp = pContext.getClickedPos();
            EntidadCoche car = new EntidadCoche(EntidadMod.COCHE.get(), pContext.getLevel());
            CompoundTag ct = pContext.getItemInHand().getTag();
            if (ct != null) {
                car.setNBT(ct);
            }
            car.teleportTo(bp.getX(),bp.getY()+1.3,bp.getZ());
            pContext.getLevel().addFreshEntity(car);
            pContext.getItemInHand().setCount(pContext.getItemInHand().getCount() - 1);
        }

        return InteractionResult.SUCCESS;
    }


    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (pStack.getTag() != null) {
            pTooltipComponents.add(Component.translatable("item.bettervehicles.combustible").withStyle(ChatFormatting.GRAY).append("§7: §8" + pStack.getTag().getInt("COMBUSTIBLE") + " ml"));
            pTooltipComponents.add(Component.translatable("block.minecraft.chest").withStyle(ChatFormatting.GRAY).append("§7: §8").append(pStack.getTag().getBoolean("COFRE") ? Component.translatable("gui.yes").withStyle(ChatFormatting.GREEN) : Component.translatable("gui.no").withStyle(ChatFormatting.DARK_GRAY)));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
