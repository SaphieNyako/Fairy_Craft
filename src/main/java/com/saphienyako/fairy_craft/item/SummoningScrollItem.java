package com.saphienyako.fairy_craft.item;

import com.saphienyako.fairy_craft.entity.ModEntities;
import com.saphienyako.fairy_craft.entity.SpringPixieEntity;
import com.saphienyako.fairy_craft.entity.base.PixieBase;
import com.saphienyako.fairy_craft.entity.base.intereface.IOwnable;
import com.saphienyako.fairy_craft.entity.base.intereface.ISummonable;
import com.saphienyako.fairy_craft.network.FairyCraftNetwork;
import com.saphienyako.fairy_craft.network.ParticleMessage;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;

import static com.saphienyako.fairy_craft.item.ModItems.*;

public class SummoningScrollItem<T extends LivingEntity> extends Item {


    public SummoningScrollItem(Properties pProperties) {
        super(pProperties);
    }

    protected void prepareEntity(LivingEntity entity, @Nonnull UseOnContext context) {
        entity.setPos(context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ());
        if (entity instanceof ISummonable summoned) {
            summoned.setSummonPos(context.getClickedPos().immutable());
        }
        if (entity instanceof IOwnable owned) {
            owned.setOwner(Objects.requireNonNull(context.getPlayer()));
        }
    }

    protected @NotNull EntityType<? extends PixieBase> returnLivingEntity(){
        if(this.equals(SUMMONING_SCROLL_SPRING_PIXIE.get())){
            return ModEntities.SPRING_PIXIE.get();
        } else if (this.equals(SUMMONING_SCROLL_SUMMER_PIXIE.get())) {
            return ModEntities.SUMMER_PIXIE.get();
        } else if (this.equals(SUMMONING_SCROLL_AUTUMN_PIXIE.get())){
            return ModEntities.AUTUMN_PIXIE.get();
        } else if (this.equals(SUMMONING_SCROLL_WINTER_PIXIE.get())){
            return ModEntities.WINTER_PIXIE.get();
        }

        return null;

    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        if (context.getPlayer() != null) {
            if (!context.getLevel().isClientSide) {


                LivingEntity entity = returnLivingEntity().create(context.getLevel());

                if (entity != null) {
                    if (context.getItemInHand().hasCustomHoverName()) {
                        entity.setCustomName(context.getItemInHand().getHoverName());
                    }

                    this.prepareEntity(entity, context);

                    context.getLevel().addFreshEntity(entity);

                    FairyCraftNetwork.sendParticles(context.getLevel(), ParticleMessage.Type.DANDELION_FLUFF, context.getClickedPos());

                    //TODO add sound?

                    if (!context.getPlayer().isCreative()) {
                        context.getItemInHand().shrink(1);
                    }
                }
            }
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
        }
        return InteractionResult.PASS;
    }

}
