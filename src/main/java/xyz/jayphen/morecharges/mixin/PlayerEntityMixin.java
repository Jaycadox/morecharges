package xyz.jayphen.morecharges.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.jayphen.morecharges.charges.earth.EarthChargeEntity;
import xyz.jayphen.morecharges.charges.ender.EnderChargeEntity;

@Mixin(ClientPlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "tickMovement", at = @At("RETURN"))
    public void tickMovement(CallbackInfo cir) {
        var _this = (ClientPlayerEntity)(Object)this;
        for (var e : _this.getWorld().getEntitiesByClass(EnderChargeEntity.class, Box.of(_this.getPos(), 16.0, 16.0, 16.0), earthChargeEntity -> true)) {
            e.applyForce(_this);
        }
    }

}

