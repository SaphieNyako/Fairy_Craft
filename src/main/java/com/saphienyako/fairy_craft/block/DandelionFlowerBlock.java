package com.saphienyako.fairy_craft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class DandelionFlowerBlock extends GiantFlowerBlock{
    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 3);

    public DandelionFlowerBlock(int height) {
        super(height);
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(VARIANT);
    }

    @Override
    protected void tickFlower(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(VARIANT) == 3 && level.random.nextInt(3) == 0) {
            level.setBlock(pos, state.setValue(VARIANT, 2), 3);
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (this.replaceFlower(level, pos.above(3 - state.getValue(PART)))) {
            if (!level.isClientSide && player instanceof ServerPlayer) {
                // Forge notifies the client of the block break before calling this
                // So we just tell the client that the block is still there
                ((ServerPlayer) player).connection.send(new ClientboundBlockUpdatePacket(level, pos));
               // QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, SpecialTaskAction.DANDELION);
                //TODO Add Quest
            }
            return false;
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    private boolean replaceFlower(@Nonnull Level level, @Nonnull BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() == this && state.getValue(PART) == 3 && state.getValue(VARIANT) == 2) {
            if (!level.isClientSide) {
                level.setBlock(pos, state.setValue(VARIANT, 3), 3);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRemove(@Nonnull BlockState oldState, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean moving) {
        super.onRemove(oldState, level, pos, newState, moving);
        if (oldState.getValue(VARIANT) == 2) {
          //  FeywildMod.getNetwork().sendParticles(level, ParticleMessage.Type.DANDELION_FLUFF, pos); //TODO Network
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getDestroyProgress(@Nonnull BlockState state, @Nonnull Player player, @Nonnull BlockGetter level, @Nonnull BlockPos pos) {
        return state.getValue(PART) == 3 && state.getValue(VARIANT) == 2 ? 1 : super.getDestroyProgress(state, player, level, pos);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void animateFlower(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(VARIANT) == 2 && random.nextDouble() < 0.4) {
            double windStrength = Math.cos((double) level.getGameTime() / 2000) / 8;
            double windX = Math.cos((double) level.getGameTime() / 1200) * windStrength;
            double windZ = Math.sin((double) level.getGameTime() / 1000) * windStrength;
            level.addParticle(ParticleTypes.END_ROD, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), windX, 0, windZ);
        }
    }

    @Override
    public BlockState flowerState(LevelAccessor level, BlockPos pos, RandomSource random) {
        return this.defaultBlockState().setValue(VARIANT, random.nextInt(3));
    }
}
