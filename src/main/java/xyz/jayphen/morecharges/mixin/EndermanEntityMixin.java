package xyz.jayphen.morecharges.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.jayphen.morecharges.MoreCharges;

@Mixin(EndermanEntity.class)
public class EndermanEntityMixin {
    @Inject(method = "dropEquipment", at = @At("RETURN"))
    public void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops, CallbackInfo cir) {
        var _this = (EndermanEntity)(Object)this;
        var angryAt = _this.getAngryAt();
        var attacker = source.getAttacker();

        if (attacker != null && angryAt != null && angryAt == attacker.getUuid()) {
            return;
        }

        for(var i = 0; i < lootingMultiplier + 1; ++i) {
            if (_this.getRandom().nextBoolean()) {
                _this.dropItem(MoreCharges.ENDER_CHARGE);
            }
        }

    }
}
