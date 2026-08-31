package com.trifectumsoftware.gtrawores;

import gregtech.api.GregTechAPI;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = GTRawOres.MOD_ID, name = GTRawOres.MOD_NAME, version = GTRawOres.VERSION,
        dependencies = "required-after:gregtech;")
public class GTRawOres {

    public static final String MOD_ID = "gtrawores";
    public static final String MOD_NAME = "GregTech Raw Ores";
    public static final String VERSION = "1.0.0";

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("GregTech Raw Ores pre-initialization");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("GregTech Raw Ores initialization");
    }

    @SubscribeEvent
    public static void registerMaterialRegistry(gregtech.api.unification.material.event.MaterialRegistryEvent event) {
        GregTechAPI.materialManager.createRegistry(MOD_ID);
    }
}
