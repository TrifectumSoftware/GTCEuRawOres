package com.trifectumsoftware.gtrawores.common;

import com.trifectumsoftware.gtrawores.GTRawOres;
import com.trifectumsoftware.gtrawores.api.unification.ore.GTRawOresOrePrefix;
import gregtech.api.unification.material.event.PostMaterialEvent;
import gregtech.common.blocks.BlockOre;
import gregtech.common.items.MetaItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Random;

@Mod.EventBusSubscriber(modid = GTRawOres.MOD_ID)
public class EventHandlers {

    private static final Random RANDOM = new Random();

    private static final String RAW_ORE_PREFIX = "rawOre";

    @SubscribeEvent
    public static void onPostMaterial(PostMaterialEvent event) {
        MetaItems.addOrePrefix(GTRawOresOrePrefix.rawOre);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onOreRegister(OreDictionary.OreRegisterEvent event) {
        String name = event.getName();
        if (name.startsWith(RAW_ORE_PREFIX) && name.length() > RAW_ORE_PREFIX.length()) {
            String materialPart = name.substring(RAW_ORE_PREFIX.length());
            gregtech.api.unification.OreDictUnifier.registerOre(event.getOre(), "ore" + materialPart);
        }
    }

    @SubscribeEvent
    public static void onHarvestDrops(BlockEvent.HarvestDropsEvent event) {
        World world = event.getWorld();
        if (world.isRemote) return;

        IBlockState state = event.getState();
        if (!(state.getBlock() instanceof BlockOre)) return;

        BlockOre oreBlock = (BlockOre) state.getBlock();
        gregtech.api.unification.material.Material material = oreBlock.material;
        if (material == null) return;

        ItemStack rawOreDrop = gregtech.api.unification.OreDictUnifier.get(
                GTRawOresOrePrefix.rawOre, material);
        if (rawOreDrop.isEmpty()) return;

        int fortune = event.getFortuneLevel();
        EntityPlayer harvester = event.getHarvester();

        if (harvester != null) {
            ItemStack heldItem = harvester.getHeldItemMainhand();
            int silkTouchLevel = EnchantmentHelper.getEnchantmentLevel(
                    Enchantments.SILK_TOUCH, heldItem);
            if (silkTouchLevel > 0) {
                return;
            }
        }

        event.getDrops().clear();

        int amount = 1;
        if (fortune > 0) {
            int bonus = RANDOM.nextInt(fortune + 2) - 1;
            if (bonus > 0) {
                amount += bonus;
            }
        }

        ItemStack dropStack = rawOreDrop.copy();
        dropStack.setCount(amount);
        event.getDrops().add(dropStack);
    }
}
