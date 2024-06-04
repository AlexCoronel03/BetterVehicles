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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemCoche extends Item {

    public ItemCoche(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide) {
            BlockPos bp = pContext.getClickedPos();
            EntidadCoche coche = new EntidadCoche(EntidadMod.COCHE.get(), pContext.getLevel());
            CompoundTag ct = pContext.getItemInHand().getTag();

            Map<Enchantment, Integer> encantamientos = pContext.getItemInHand().getAllEnchantments();
            boolean tieneAgiAcuatica = encantamientos.containsKey(Enchantments.DEPTH_STRIDER);
            boolean tieneVelAlma = encantamientos.containsKey(Enchantments.SOUL_SPEED);

            if (ct != null) {
                coche.setNBT(ct);
            }

            if(tieneAgiAcuatica && tieneVelAlma){
                coche.setNivelAgiAcuatica(encantamientos.getOrDefault(Enchantments.DEPTH_STRIDER, 0));
                coche.setNivelVelAlma(encantamientos.getOrDefault(Enchantments.SOUL_SPEED, 0));
            } else if (tieneVelAlma) {
                coche.setNivelVelAlma(encantamientos.getOrDefault(Enchantments.SOUL_SPEED, 0));
            } else if (tieneAgiAcuatica) {
                coche.setNivelAgiAcuatica(encantamientos.getOrDefault(Enchantments.DEPTH_STRIDER, 0));
            }
            coche.teleportTo(bp.getX(),bp.getY()+1.3,bp.getZ());
            pContext.getLevel().addFreshEntity(coche);
            pContext.getItemInHand().setCount(pContext.getItemInHand().getCount() - 1);
        }

        return InteractionResult.SUCCESS;
    }


    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return true;
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (pStack.getTag() != null) {
            pTooltipComponents.add(Component.translatable("item.bettervehicles.combustible").withStyle(ChatFormatting.GRAY).append("§7: §8" + pStack.getTag().getInt("COMBUSTIBLE") + " ml"));
            pTooltipComponents.add(Component.translatable("block.minecraft.chest").withStyle(ChatFormatting.GRAY).append("§7: §8").append(pStack.getTag().getBoolean("COFRE") ? Component.translatable("gui.yes").withStyle(ChatFormatting.GREEN) : Component.translatable("gui.no").withStyle(ChatFormatting.DARK_GRAY)));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == Enchantments.DEPTH_STRIDER || enchantment == Enchantments.SOUL_SPEED;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(book);
        for (Enchantment enchantment : enchantments.keySet()) {
            if (enchantment != Enchantments.DEPTH_STRIDER && enchantment != Enchantments.SOUL_SPEED) {
                return false;
            }
        }
        return true;
    }

    public void encantamientosAplicados(ItemStack stack, EntidadCoche coche) {
        Map<Enchantment, Integer> encantamientos = stack.getAllEnchantments();
        boolean tieneAgiAcuatica = encantamientos.containsKey(Enchantments.DEPTH_STRIDER);
        boolean tieneVelAlma = encantamientos.containsKey(Enchantments.SOUL_SPEED);

        try{
            coche.level().players().get(0).sendSystemMessage(Component.literal("Enc Agi: " + tieneAgiAcuatica + "\nEnc Vel: " + tieneVelAlma));
        } catch(Exception e){

        }


        if(tieneAgiAcuatica && tieneVelAlma){
            coche.setNivelAgiAcuatica(encantamientos.getOrDefault(Enchantments.DEPTH_STRIDER, 0));
            coche.setNivelVelAlma(encantamientos.getOrDefault(Enchantments.SOUL_SPEED, 0));
        } else if (tieneVelAlma) {
            coche.setNivelVelAlma(encantamientos.getOrDefault(Enchantments.SOUL_SPEED, 0));
        } else if (tieneAgiAcuatica) {
            coche.setNivelAgiAcuatica(encantamientos.getOrDefault(Enchantments.DEPTH_STRIDER, 0));
        }
    }

    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag tag = super.getShareTag(stack);
        if (tag == null) {
            tag = new CompoundTag();
        }

        Map<Enchantment, Integer> encantamientos = EnchantmentHelper.getEnchantments(stack);
        for (Enchantment encantamiento : encantamientos.keySet()) {
            tag.putInt(encantamiento.toString(), encantamientos.get(encantamiento));
        }

        return tag;
    }


    @Override
    public void readShareTag(ItemStack stack, CompoundTag tag) {
        super.readShareTag(stack, tag);
        if (tag != null) {
            if (tag.contains("DepthStriderLevel")) {
                stack.enchant(Enchantments.DEPTH_STRIDER, tag.getInt("DepthStriderLevel"));
            }
            if (tag.contains("SoulSpeedLevel")) {
                stack.enchant(Enchantments.SOUL_SPEED, tag.getInt("SoulSpeedLevel"));
            }
        }
    }
}
