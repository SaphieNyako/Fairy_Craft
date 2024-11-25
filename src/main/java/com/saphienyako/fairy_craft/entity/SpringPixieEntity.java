package com.saphienyako.fairy_craft.entity;

import com.saphienyako.fairy_craft.entity.base.FlyingFairyBase;
import com.saphienyako.fairy_craft.item.ModItems;
import com.saphienyako.fairy_craft.network.FairyCraftNetwork;
import com.saphienyako.fairy_craft.network.ParticleMessage;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Random;

public class SpringPixieEntity extends FlyingFairyBase {

    //No boredome mechanics
    //No Alignment
    //No Geckolib animations
    //TODO clipping
    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(SpringPixieEntity.class, EntityDataSerializers.INT);
    public final AnimationState IDLE_ANIMATION = new AnimationState();
    public final AnimationState SPELL_CASTING_ANIMATION = new AnimationState();
    protected SpringPixieEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    //Can always follow players

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
      //  this.goalSelector.addGoal(20, new AbilityGoal(this)); TODO
      //  this.goalSelector.addGoal(50, new FeywildPanicGoal(this, 0.003, 13)); TODO
      //  this.goalSelector.addGoal(10, new TameCheckingGoal(this, false, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false))); TODO
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, 0);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
     if (!this.dead && !this.isDeadOrDying()) {
            if (this.getState() == State.CASTING) {
                this.SPELL_CASTING_ANIMATION.animateWhen(this.getState() == State.CASTING, 1);
            }
        }
        if(this.getState() == State.IDLE){
            this.IDLE_ANIMATION.startIfStopped(1);
        }
    }

    @Nonnull
    @Override
    @OverridingMethodsMustInvokeSuper
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        InteractionResult superResult = super.interactAt(player, hitVec, hand);
        ItemStack stack = player.getItemInHand(hand);
        if (superResult == InteractionResult.PASS) {
            if (!stack.isEmpty() && player instanceof ServerPlayer && this.tryAcceptGift((ServerPlayer) player, hand)) {
                player.swing(hand, true);

                //GIVE COOKIE, HEAL, DISSAPEAR
            } else if (player.getItemInHand(hand).is(Items.COOKIE) && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
                this.heal(3);
                if (!this.isTamed() && player instanceof ServerPlayer && this.owner == null) {
                    Random random = new Random();
                    if (random.nextInt(4) == 0) {
                        this.spawnAtLocation(new ItemStack(ModItems.PIXIE_DUST.get()));
                        this.playSound(SoundEvents.ENDERMAN_TELEPORT);
                        this.discard();
                        player.sendSystemMessage(Component.translatable("message.fairy_craft.pixie_feed")); //TODO message, make for all pixies
                    }
                }
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
                FairyCraftNetwork.sendParticles(this.level(), ParticleMessage.Type.FAIRY_HEART, this.getOnPos());
                player.swing(hand, true);

                //NAME TAG
            } else if (player.getItemInHand(hand).getItem() == Items.NAME_TAG) {
                setCustomName(player.getItemInHand(hand).getHoverName().copy());
                setCustomNameVisible(true);
                if (!level().isClientSide) {
                    player.sendSystemMessage(Component.translatable("message.fairy_craft.pixie_name")); //TODO message, make for all pixies
                }

                //PIXIE ORB
            } /*  else if (!this.isTamed() && this.getOwner() == null && player instanceof ServerPlayer && player.getItemInHand(hand).getItem() == Items.ENDER_EYE) {
                player.swing(player.getUsedItemHand(), true);
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
                player.addItem(new ItemStack(ModItems.pixieOrb));
                this.remove(RemovalReason.DISCARDED);

                //QUEST
            } */ else if (this.isTamed() && player instanceof ServerPlayer && this.owner != null && this.owner.equals(player.getUUID())) {
                //TODO QUEST
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return superResult;
        }
    }

    private boolean tryAcceptGift(ServerPlayer player, InteractionHand hand) {
      /*  ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty()) {

            if (QuestData.get(player).checkComplete(FeyGiftTask.INSTANCE, input)) {
                if (!player.isCreative()) stack.shrink(1);
                player.sendSystemMessage(Component.translatable("message.feywild." + this.alignment.id + "_fey_thanks"));
                return true;
            }
        } */
        //TODO Quest GIFT
        return false;
    }

    public SpringPixieEntity.State getState() {
        SpringPixieEntity.State[] states = SpringPixieEntity.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(SpringPixieEntity.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    /*  TODO Ability
    @Override
    protected Ability<?> getDefaultAbility() {
        return ModAbilities.flowerWalk;
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.springSparkleParticle;
    }

    public Ability<?> getAbility() {
        if (this.ability == null) this.ability = this.getDefaultAbility();
        return this.ability;
    }

    public void setAbility(Ability<?> ability) {
        this.ability = ability;
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        NbtX.putResource(nbt, "PixieAbility", this.getAbility().id());
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setAbility(Ability.get(NbtX.getResource(nbt, "PixieAbility"), this.getDefaultAbility()));
    } */

    public enum State {
        IDLE, POSE, FLYING, CASTING
    }

}
