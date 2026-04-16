package com.pz.beyond;

import com.mojang.logging.LogUtils;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Config
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ModConfigSpec.Builder BUILDER;



    public static final ModConfigSpec SPEC;


    static {
        BUILDER = new ModConfigSpec.Builder();




        SPEC = BUILDER.build();
    }

}
