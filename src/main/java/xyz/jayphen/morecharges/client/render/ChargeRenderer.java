package xyz.jayphen.morecharges.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.WindChargeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.AbstractWindChargeEntity;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class ChargeRenderer extends EntityRenderer<AbstractWindChargeEntity> {
    private Identifier texture;
    private final WindChargeEntityModel model;

    public ChargeRenderer(EntityRendererFactory.Context context, Identifier texture) {
        super(context);
        this.texture = texture;
        this.model = new WindChargeEntityModel(context.getPart(EntityModelLayers.WIND_CHARGE));
    }

    public void render(AbstractWindChargeEntity abstractWindChargeEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        float h = (float)abstractWindChargeEntity.age + g;
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getBreezeWind(this.texture, this.getXOffset(h) % 1.0F, 0.0F));
        this.model.setAngles(abstractWindChargeEntity, 0.0F, 0.0F, h, 0.0F, 0.0F);
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        super.render(abstractWindChargeEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    protected float getXOffset(float tickDelta) {
        return tickDelta * 0.03F;
    }

    public Identifier getTexture(AbstractWindChargeEntity abstractWindChargeEntity) {
        return texture;
    }
}
