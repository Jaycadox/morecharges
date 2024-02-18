package xyz.jayphen.morecharges;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.AbstractWindChargeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import xyz.jayphen.morecharges.charges.earth.EarthChargeItem;
import xyz.jayphen.morecharges.charges.earth.EarthChargeEntity;
import xyz.jayphen.morecharges.charges.ender.EnderChargeEntity;
import xyz.jayphen.morecharges.charges.ender.EnderChargeItem;

public class MoreCharges implements ModInitializer {
    public static final Item EARTH_CHARGE = Registry.register(Registries.ITEM, new Identifier("morecharges", "earth_charge"), new EarthChargeItem(new Item.Settings()));
    public static final EntityType<EarthChargeEntity> EARTH_CHARGE_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier("morecharges", "earth_charge_entity"),
            FabricEntityTypeBuilder.<EarthChargeEntity>create(SpawnGroup.MISC, EarthChargeEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
                    .trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
                    .build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
    );
    public static final Item ENDER_CHARGE = Registry.register(Registries.ITEM, new Identifier("morecharges", "ender_charge"), new EnderChargeItem(new Item.Settings()));
    public static final EntityType<EnderChargeEntity> ENDER_CHARGE_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier("morecharges", "ender_charge_entity"),
            FabricEntityTypeBuilder.<EnderChargeEntity>create(SpawnGroup.MISC, EnderChargeEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
                    .trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
                    .build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
    );

    private void registerDispenser(Item instance, DispenserCallable create) {
        DispenserBlock.registerBehavior(instance, new ItemDispenserBehavior(){
            @Override
            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                Direction direction = pointer.state().get(DispenserBlock.FACING);
                Position position = DispenserBlock.getOutputLocation(pointer);
                ServerWorld world = pointer.world();
                Random random = world.random;
                double d = random.nextTriangular(direction.getOffsetX(), 0.11485000000000001);
                double e = random.nextTriangular(direction.getOffsetY(), 0.11485000000000001);
                double f = random.nextTriangular(direction.getOffsetZ(), 0.11485000000000001);
                var entitiy = create.apply(world, position.getX() + (double)((float)direction.getOffsetX() * 0.3f), position.getY() + (double)((float)direction.getOffsetY() * 0.3f), position.getZ() + (double)((float)direction.getOffsetZ() * 0.3f), d, e, f);
                entitiy.setVelocity(direction.getVector().getX(), direction.getVector().getY(), direction.getVector().getZ());
                world.spawnEntity(entitiy);
                stack.decrement(1);
                return stack;
            }
        });
    }

    @Override
    public void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.add(EARTH_CHARGE));

        registerDispenser(EARTH_CHARGE, EarthChargeEntity::new);
        registerDispenser(ENDER_CHARGE, EnderChargeEntity::new);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> content.add(EARTH_CHARGE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.add(ENDER_CHARGE));
    }
}

@FunctionalInterface
interface DispenserCallable {
    AbstractWindChargeEntity apply(World w, double a, double b, double c, double d, double e, double f);
}