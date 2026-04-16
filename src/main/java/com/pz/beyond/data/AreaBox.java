package com.pz.beyond.data;

import net.minecraft.nbt.CompoundTag;

public record AreaBox(int minX , int minZ , int maxX , int maxZ) {

    public boolean contains(double x, double z) {
        return x >= minX && x <= maxX && z >= minZ && z <= maxZ;
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("minX", minX);
        tag.putInt("minZ", minZ);
        tag.putInt("maxX", maxX);
        tag.putInt("maxZ", maxZ);
        return tag;
    }

    public static AreaBox load(CompoundTag tag) {
        return new AreaBox(
                tag.getInt("minX"),
                tag.getInt("minZ"),
                tag.getInt("maxX"),
                tag.getInt("maxZ")
        );
    }

}
