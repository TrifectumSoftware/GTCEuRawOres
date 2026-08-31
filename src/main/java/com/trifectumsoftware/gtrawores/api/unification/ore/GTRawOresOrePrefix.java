package com.trifectumsoftware.gtrawores.api.unification.ore;

import com.trifectumsoftware.gtrawores.api.unification.material.info.GTRawOresMaterialIconType;
import gregtech.api.unification.ore.OrePrefix;

import static gregtech.api.GTValues.M;

public class GTRawOresOrePrefix {

    public static final OrePrefix rawOre = new OrePrefix("rawOre", M, null,
            GTRawOresMaterialIconType.rawOre, 0,
            OrePrefix.Conditions.hasOreProperty);
}
