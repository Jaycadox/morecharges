package xyz.jayphen.morecharges.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.WindChargeEntityRenderer;
import net.minecraft.util.Identifier;
import xyz.jayphen.morecharges.MoreCharges;
import xyz.jayphen.morecharges.client.render.ChargeRenderer;

public class MoreChargesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(MoreCharges.EARTH_CHARGE_ENTITY, (ctx) -> new ChargeRenderer(ctx, new Identifier("morecharges:textures/entity/projectiles/earth_charge.png")));
        EntityRendererRegistry.register(MoreCharges.ENDER_CHARGE_ENTITY, (ctx) -> new ChargeRenderer(ctx, new Identifier("morecharges:textures/entity/projectiles/ender_charge.png")));
    }
}
