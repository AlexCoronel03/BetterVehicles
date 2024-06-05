package com.example.bettervehicles.bloques.personalizado;

import com.example.bettervehicles.bloques.entidad.BloquesModEntidades;
import com.example.bettervehicles.bloques.entidad.SintetizadorCombustibleEntidadBloque;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class SintetizadorCombustible extends BaseEntityBlock {
    public static final VoxelShape FORMA = Block.box(0, 0, 0, 16, 12, 16);

    public static final BooleanProperty ENCENDIDO = BooleanProperty.create("on");

    public SintetizadorCombustible(Properties propiedades) {
        super(propiedades);
        registerDefaultState(stateDefinition.any().setValue(ENCENDIDO, false));
    }

    @Override
    public int getLightEmission(BlockState estado, BlockGetter nivel, BlockPos posicion) {
        return estado.getValue(ENCENDIDO) ? 10 : 0;
    }

    @Override
    public VoxelShape getShape(BlockState estado, BlockGetter nivel, BlockPos posicion, CollisionContext contexto) {
        return FORMA;
    }

    @Override
    public RenderShape getRenderShape(BlockState estado) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState estado, Level nivel, BlockPos posicion, BlockState nuevoEstado, boolean esMoviendo) {
        if (nivel.isClientSide) {
            Minecraft.getInstance().getSoundManager().stop();
        }
        estado.setValue(ENCENDIDO, false);
        getLightEmission(estado, nivel, posicion);
        if (estado.getBlock() != nuevoEstado.getBlock()) {
            BlockEntity entidadBloque = nivel.getBlockEntity(posicion);
            if (entidadBloque instanceof SintetizadorCombustibleEntidadBloque) {
                ((SintetizadorCombustibleEntidadBloque) entidadBloque).soltarItems();
            }
        }

        super.onRemove(estado, nivel, posicion, nuevoEstado, esMoviendo);
    }

    @Override
    public InteractionResult use(BlockState estado, Level nivel, BlockPos posicion, Player jugador, InteractionHand mano, BlockHitResult resultado) {
        if (!nivel.isClientSide()) {
            BlockEntity entidad = nivel.getBlockEntity(posicion);
            if (entidad instanceof SintetizadorCombustibleEntidadBloque) {
                NetworkHooks.openScreen(((ServerPlayer) jugador), (SintetizadorCombustibleEntidadBloque) entidad, posicion);
            } else {
                throw new IllegalStateException("Proveedor de contenido inexistente");
            }
        }

        return InteractionResult.sidedSuccess(nivel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos posicion, BlockState estado) {
        return new SintetizadorCombustibleEntidadBloque(posicion, estado);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> constructor) {
        constructor.add(ENCENDIDO);
        super.createBlockStateDefinition(constructor);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level nivel, BlockState estado, BlockEntityType<T> tipoEntidadBloque) {

        return createTickerHelper(tipoEntidadBloque, BloquesModEntidades.SINTETIZADOR_DE_COMBUSTIBLE_BE.get(),
                (nivel1, posicion1, estado1, entidadBloque) -> entidadBloque.tick(nivel1, posicion1, estado1));
    }
}
