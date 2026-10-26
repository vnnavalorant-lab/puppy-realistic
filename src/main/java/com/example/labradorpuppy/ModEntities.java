package com.example.labradorpuppy;

import com.example.labradorpuppy.entity.PuppyEntity;
import com.example.labradorpuppy.mixin.SpawnRestrictionAccessor;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public final class ModEntities {
    private ModEntities() {}

    /** Entity id: labrador_puppy:puppy  (use /summon labrador_puppy:puppy) */
    public static final EntityType<PuppyEntity> PUPPY = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(LabradorPuppyMod.MOD_ID, "puppy"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PuppyEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.65f))
                    .trackRangeBlocks(64)
                    .build());

    public static void init() {
        FabricDefaultAttributeRegistry.register(PUPPY, PuppyEntity.createPuppyAttributes());

        // Natural spawning (remove this block if you only want the spawn egg / /summon)
        SpawnRestrictionAccessor.callRegister(PUPPY, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS,
                        BiomeKeys.MEADOW, BiomeKeys.FOREST),
                SpawnGroup.CREATURE, PUPPY, 6, 2, 4);
    }
}
