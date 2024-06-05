package com.example.bettervehicles.entidad.personalizado;

import com.example.bettervehicles.BetterVehicles;
import com.example.bettervehicles.item.ModItems;
import com.example.bettervehicles.item.personalizado.ItemCoche;
import com.example.bettervehicles.net.ControlMensajesCoche;
import com.example.bettervehicles.ui.Car.ContenedorCoche;
import com.example.sonidos.SonidosMod;
import com.example.sonidos.SonidoLoopInicio;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class EntidadCoche extends Entity implements Container{

    @OnlyIn(Dist.CLIENT)
    SonidoLoopInicio sls;

    int tickPersonalizado;

    private static final EntityDataAccessor<Boolean> WINPUT = SynchedEntityData.defineId(EntidadCoche.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> AINPUT = SynchedEntityData.defineId(EntidadCoche.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> DINPUT = SynchedEntityData.defineId(EntidadCoche.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> SINPUT = SynchedEntityData.defineId(EntidadCoche.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> UPINPUT = SynchedEntityData.defineId(EntidadCoche.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> COMBUSTIBLE = SynchedEntityData.defineId(EntidadCoche.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> VELOCIDAD = SynchedEntityData.defineId(EntidadCoche.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Boolean> COFRE = SynchedEntityData.defineId(EntidadCoche.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> RUEDAS_CLAVOS = SynchedEntityData.defineId(EntidadCoche.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> FLOTADOR = SynchedEntityData.defineId(EntidadCoche.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> NIVEL_DEL_ALMA = SynchedEntityData.defineId(EntidadCoche.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> NIVEL_AGI_ACUATICA = SynchedEntityData.defineId(EntidadCoche.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> VELOCIDAD_MAXIMA = SynchedEntityData.defineId(EntidadCoche.class, EntityDataSerializers.FLOAT);

    protected SimpleContainer INVENTARIO;

    private final ItemStack cocheItemStack;

    public void setNivelAgiAcuatica(int nivelAgiAcuatica) {
        entityData.set(NIVEL_AGI_ACUATICA, nivelAgiAcuatica);
    }

    public void setNivelVelAlma(int nivelVelAlma) {
        entityData.set(NIVEL_DEL_ALMA, nivelVelAlma);
    }

    public void updateControls(boolean winput, boolean ainput, boolean dinput, boolean sinput, boolean upinput) {
        boolean cambia = false;
        if (entityData.get(WINPUT) != winput) {
            entityData.set(WINPUT, winput);
            cambia = true;
        }

        if (entityData.get(AINPUT) != ainput) {
            entityData.set(AINPUT, ainput);
            cambia = true;
        }

        if (entityData.get(DINPUT) != dinput) {
            entityData.set(DINPUT, dinput);
            cambia = true;
        }

        if (entityData.get(SINPUT) != sinput) {
            entityData.set(SINPUT, sinput);
            cambia = true;
        }

        if (entityData.get(UPINPUT) != upinput) {
            entityData.set(UPINPUT, upinput);
            cambia = true;
        }

        if (level().isClientSide && cambia) {
            BetterVehicles.INSTANCE.sendToServer(new ControlMensajesCoche(winput, ainput, dinput, sinput, upinput));
        }
    }


    public EntidadCoche(EntityType<?> p_19870_, Level level) {
        super(p_19870_, level);
        this.setInvulnerable(false);
        this.setMaxUpStep(0.5F);
        INVENTARIO = new SimpleContainer(27);
        if (level.isClientSide()) {
            clientConstructor();
        }
        this.cocheItemStack = new ItemStack(ModItems.COCHE.get());
    }

    @OnlyIn(Dist.CLIENT)
    public void clientConstructor() {
        sls = new SonidoLoopInicio(this, SonidosMod.ENGINE_SOUND.get(), SoundSource.MASTER);
        Minecraft.getInstance().getSoundManager().play(sls);
    }


    @Override
    protected void defineSynchedData() {
        entityData.define(WINPUT, false);
        entityData.define(AINPUT, false);
        entityData.define(DINPUT, false);
        entityData.define(SINPUT, false);
        entityData.define(UPINPUT, false);
        entityData.define(COFRE, false);
        entityData.define(VELOCIDAD, 0.0F);
        entityData.define(COMBUSTIBLE, 0);
        entityData.define(RUEDAS_CLAVOS, false);
        entityData.define(FLOTADOR, false);
        entityData.define(NIVEL_AGI_ACUATICA, 0);
        entityData.define(NIVEL_DEL_ALMA, 0);
        entityData.define(VELOCIDAD_MAXIMA, 1.25F);

    }

    @Override
    public float getStepHeight() {
        return tieneRuedasClavos() ? 1F : 0.5F;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return super.hurt(source, amount);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        entityData.set(COMBUSTIBLE, nbt.getInt("COMBUSTIBLE"));
        entityData.set(COFRE, nbt.getBoolean("COFRE"));
        this.INVENTARIO = new SimpleContainer(27);
        entityData.set(NIVEL_DEL_ALMA, nbt.getInt("SoulSpeedLevel"));
        entityData.set(NIVEL_AGI_ACUATICA, nbt.getInt("DepthStriderLevel"));
        leerInventario(nbt, "INVENTARIO", INVENTARIO);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        nbt.putInt("COMBUSTIBLE", entityData.get(COMBUSTIBLE));
        guardarInventario(nbt, "INVENTARIO", INVENTARIO);
        nbt.putBoolean("COFRE", entityData.get(COFRE));
        nbt.putInt("DepthStriderLevel", entityData.get(NIVEL_AGI_ACUATICA));
        nbt.putInt("SoulSpeedLevel", entityData.get(NIVEL_DEL_ALMA));
    }

    public static void leerInventario(CompoundTag compound, String nombre, Container inv) {
        if (compound.contains(nombre)) {
            ListTag tagList = compound.getList(nombre, 10);

            for(int i = 0; i < tagList.size(); ++i) {
                CompoundTag slot = tagList.getCompound(i);
                int j = slot.getInt("Slot");
                if (j >= 0 && j < inv.getContainerSize()) {
                    inv.setItem(j, ItemStack.of(slot));
                }
            }

        }
    }

    public CompoundTag getNBT() {
        CompoundTag ct = new CompoundTag();
        ct.putInt("COMBUSTIBLE", entityData.get(COMBUSTIBLE));
        guardarInventario(ct, "INVENTARIO", INVENTARIO);
        ct.putBoolean("COFRE", entityData.get(COFRE));
        ct.putInt("DepthStriderLevel", entityData.get(NIVEL_AGI_ACUATICA));
        ct.putInt("SoulSpeedLevel", entityData.get(NIVEL_DEL_ALMA));
        return ct;
    }

    public void setNBT(CompoundTag ct) {
        entityData.set(COMBUSTIBLE, ct.getInt("COMBUSTIBLE"));
        entityData.set(COFRE, ct.getBoolean("COFRE"));
        this.INVENTARIO = new SimpleContainer(27);
        entityData.set(NIVEL_DEL_ALMA, ct.getInt("SoulSpeedLevel"));
        entityData.set(NIVEL_AGI_ACUATICA, ct.getInt("DepthStriderLevel"));
        leerInventario(ct, "INVENTARIO", INVENTARIO);
    }

    public static void guardarInventario(CompoundTag compound, String nombre, Container inv) {
        ListTag tagList = new ListTag();

        for(int i = 0; i < inv.getContainerSize(); ++i) {
            if (!inv.getItem(i).isEmpty()) {
                CompoundTag slot = new CompoundTag();
                slot.putInt("Slot", i);
                inv.getItem(i).save(slot);
                tagList.add(slot);
            }
        }

        compound.put(nombre, tagList);
    }

    public boolean tieneRuedasClavos() {
        if (!level().isClientSide()) {
            entityData.set(RUEDAS_CLAVOS, false);
            for (int x = 0; x < INVENTARIO.getContainerSize(); x++) {
                if (INVENTARIO.getItem(x).getItem() == ModItems.RUEDAS_CLAVOS.get()) {
                    entityData.set(RUEDAS_CLAVOS, true);
                }
            }
        }
        return entityData.get(RUEDAS_CLAVOS);
    }


    public boolean tieneFlotador() {
        if (!level().isClientSide()) {
            entityData.set(FLOTADOR, false);
            for (int x = 0; x < INVENTARIO.getContainerSize(); x++) {
                if (INVENTARIO.getItem(x).getItem() == ModItems.FLOTADOR.get()) {
                    entityData.set(FLOTADOR, true);
                }
            }
        }
        return entityData.get(FLOTADOR);
    }


    private void controlCoche() {

        if(isInWater()){
            if(entityData.get(NIVEL_AGI_ACUATICA) == 1) {
                entityData.set(VELOCIDAD_MAXIMA, 1.75F);
            } else if (entityData.get(NIVEL_AGI_ACUATICA) == 2) {
                entityData.set(VELOCIDAD_MAXIMA, 2F);
            } else if (entityData.get(NIVEL_AGI_ACUATICA) == 3) {
                entityData.set(VELOCIDAD_MAXIMA, 2.50F);
            }
        } else {
            if(entityData.get(NIVEL_DEL_ALMA) == 1) {
                entityData.set(VELOCIDAD_MAXIMA, 1.5F);
            } else if (entityData.get(NIVEL_DEL_ALMA) == 2) {
                entityData.set(VELOCIDAD_MAXIMA, 1.75F);
            } else if (entityData.get(NIVEL_DEL_ALMA) == 3) {
                entityData.set(VELOCIDAD_MAXIMA, 2.25F);
            }
        }

        float rotacionDelta = 0;

        float MODIFICADOR_ROTACION = 1;
        float ROTACION_MIN = 1;
        float ROTACION_MAX = 3;

        float rotationVELOCIDAD = 0;
        if (Math.abs(entityData.get(VELOCIDAD)) > 0.02F) {
            rotationVELOCIDAD = Mth.abs((float) (MODIFICADOR_ROTACION / (float) Math.pow(entityData.get(VELOCIDAD), 2)));

            rotationVELOCIDAD = Mth.clamp(rotationVELOCIDAD, ROTACION_MIN, ROTACION_MAX);
        }

        rotacionDelta = 0;

        if (entityData.get(VELOCIDAD) < 0) {
            rotationVELOCIDAD = -rotationVELOCIDAD;
        }

        if (entityData.get(AINPUT) && getPassengers().size()>0) {
            rotacionDelta -= rotationVELOCIDAD;
        }

        if (entityData.get(DINPUT) && getPassengers().size()>0) {
            rotacionDelta += rotationVELOCIDAD;
        }



        setYRot(getYRot() + rotacionDelta);

        float vertVELOCIDAD = 0f;

        if (entityData.get(WINPUT) && entityData.get(SINPUT)) {

        } else if (entityData.get(WINPUT) && getPassengers().size() > 0 && entityData.get(COMBUSTIBLE) > 0) {
            int frenando = 1;
            if (entityData.get(VELOCIDAD) < 0) {
                frenando = 3;
            }
            if (isInWater()) {
                if(entityData.get(NIVEL_AGI_ACUATICA) == 1) {
                    entityData.set(VELOCIDAD, entityData.get(VELOCIDAD) + 0.002F * frenando);
                } else if (entityData.get(NIVEL_AGI_ACUATICA) == 2) {
                    entityData.set(VELOCIDAD, entityData.get(VELOCIDAD) + 0.0035F * frenando);
                } else if (entityData.get(NIVEL_AGI_ACUATICA) == 3) {
                    entityData.set(VELOCIDAD, entityData.get(VELOCIDAD) + 0.0055F * frenando);
                } else {
                    entityData.set(VELOCIDAD, entityData.get(VELOCIDAD) + 0.001F * frenando);
                }
            } else {
                if(entityData.get(NIVEL_DEL_ALMA) == 1) {
                    entityData.set(VELOCIDAD, entityData.get(VELOCIDAD) + 0.0065F * frenando);
                } else if (entityData.get(NIVEL_DEL_ALMA) == 2) {
                    entityData.set(VELOCIDAD, entityData.get(VELOCIDAD) + 0.0075F * frenando);
                } else if (entityData.get(NIVEL_DEL_ALMA) == 3) {
                    entityData.set(VELOCIDAD, entityData.get(VELOCIDAD) + 0.01F * frenando);
                } else {
                    entityData.set(VELOCIDAD, entityData.get(VELOCIDAD) + 0.005F * frenando);

                }
            }
        } else if (entityData.get(SINPUT) && getPassengers().size() > 0 && entityData.get(COMBUSTIBLE) > 0) {
            int frenando = 1;
            if (entityData.get(VELOCIDAD) > 0) {
                frenando = 3;
            }
            if (isInWater()) {
                entityData.set(VELOCIDAD, entityData.get(VELOCIDAD) - 0.001F * frenando);
            } else {
                entityData.set(VELOCIDAD, entityData.get(VELOCIDAD) - 0.005F * frenando);
            }
        } else {
            if (entityData.get(VELOCIDAD) > 0) {
                entityData.set(VELOCIDAD, entityData.get(VELOCIDAD) - 0.02F);
                if (entityData.get(VELOCIDAD) < 0) {
                    entityData.set(VELOCIDAD, 0F);
                }
            } else if (entityData.get(VELOCIDAD) < 0) {
                entityData.set(VELOCIDAD, entityData.get(VELOCIDAD) + 0.02F);
                if (entityData.get(VELOCIDAD) > 0) {
                    entityData.set(VELOCIDAD, 0F);
                }
            }
        }

        if (tieneFlotador() && entityData.get(WINPUT) && entityData.get(UPINPUT) && getPassengers().size() > 0 && entityData.get(COMBUSTIBLE) > 0 && (isInWater() || wasTouchingWater)) {
            vertVELOCIDAD = 0.3f;
        }

        if (entityData.get(VELOCIDAD) > entityData.get(VELOCIDAD_MAXIMA)) {
            entityData.set(VELOCIDAD, entityData.get(VELOCIDAD_MAXIMA));
        } else if (entityData.get(VELOCIDAD) < entityData.get(VELOCIDAD_MAXIMA)/2 * -1) {
            entityData.set(VELOCIDAD, entityData.get(VELOCIDAD_MAXIMA)/3 * -1);
        }

        double rotacionVerticalRadianes = Math.toRadians(this.getYRot()); // Convertir la rotación a radianes
        double mueveX = -Math.sin(rotacionVerticalRadianes) * entityData.get(VELOCIDAD); // Componente X de la velocidad
        double mueveY = Math.cos(rotacionVerticalRadianes) * entityData.get(VELOCIDAD); // Componente Z de la velocidad
        setDeltaMovement(mueveX, vertVELOCIDAD, mueveY);
        try{
            //getPassengers().get(0).sendSystemMessage(Component.literal("Velocidad: " + entityData.get(VELOCIDAD)));
        } catch(Exception e){

        }
    }

    public int obtenerCombustible() {
        return entityData.get(COMBUSTIBLE);
    }

    @OnlyIn(Dist.CLIENT)
    public void comprobarEstadoLoop() {
        if (!estaSonando(sls)) {
            sls = new SonidoLoopInicio(this, SonidosMod.ENGINE_SOUND.get(), SoundSource.MASTER);
            SonidosMod.playSoundLoop(sls, level());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public boolean estaSonando(SoundInstance sound) {
        if (sound == null) {
            return false;
        }
        return Minecraft.getInstance().getSoundManager().isActive(sound);
    }



    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide) {
            if (getPassengers().size() > 0 || obtenerCombustible() > 0) {
                comprobarEstadoLoop();
            }
        }

        tieneFlotador();

        if (tieneRuedasClavos()) {
            this.setMaxUpStep(1);
        } else {
            this.setMaxUpStep(0.5F);
        }

        if (entityData.get(COMBUSTIBLE) > 0 && entityData.get(VELOCIDAD) != 0) {
            entityData.set(COMBUSTIBLE, entityData.get(COMBUSTIBLE) - 1);
            double particleX = 1.65 * Math.cos(Math.toRadians(getYRot()+180+90));
            double particleZ = 1.65 * Math.sin(Math.toRadians(getYRot()+180+90));
            level().addParticle(ParticleTypes.LARGE_SMOKE, getX() + particleX, getY()+0.25, getZ() + particleZ, 0, 0.1, 0);
            level().addParticle(ParticleTypes.FLAME, getX() + particleX, getY(), getZ() + particleZ, 0, 0.05, 0);

        } else if (entityData.get(COMBUSTIBLE) > 0 && entityData.get(VELOCIDAD) == 0 && !getPassengers().isEmpty()) {
            if (tickPersonalizado % 5 == 0) {
                entityData.set(COMBUSTIBLE, entityData.get(COMBUSTIBLE) - 1);
            }
        }

        if (!getPassengers().isEmpty()) {
            Player p = (Player) (getPassengers().get(0));
            p.displayClientMessage(Component.translatable("item.bettervehicles.combustible").append(": " + entityData.get(COMBUSTIBLE) + " ml") , true);
        }

        controlCoche();
        if (isInWater() && !tieneFlotador()) {
            move(MoverType.SELF, getDeltaMovement().multiply(new Vec3(0.2, 1, 0.2)));
        } else {
            move(MoverType.SELF, getDeltaMovement());
        }



        if(!level().isClientSide){
            syncPacketPositionCodec(getX(), getY(), getZ());
        }


        // Aplicamos el movimiento a la entidad



        if (!this.onGround()) {
            if (isInWater()) {
                if (tieneFlotador()) {
                    if (tickPersonalizado % 20 == 0) {
                        romperFlotador();
                    }

                    if (isUnderWater()) {
                        this.move(MoverType.SELF, new Vec3(0, 0.2, 0));
                    } else {
                        this.move(MoverType.SELF, new Vec3(0, 0, 0));
                    }
                } else {
                    this.move(MoverType.SELF, new Vec3(0, -0.15, 0));
                }

            } else {
                this.move(MoverType.SELF, new Vec3(0, -0.6, 0));
            }
        }

        if (!isInWater() && tieneRuedasClavos()) {
            if (entityData.get(VELOCIDAD) != 0 && tickPersonalizado % 75 == 0) {
                romperRuedas();
            }
        }

        tickPersonalizado++;

        if (tickPersonalizado > 30000) {
            tickPersonalizado = 0;
        }
    }

    public void romperFlotador() {
        for (int x = 0; x < INVENTARIO.getContainerSize(); x++) {
            if (INVENTARIO.getItem(x).getItem() == ModItems.FLOTADOR.get()) {
                INVENTARIO.getItem(x).setDamageValue(INVENTARIO.getItem(x).getDamageValue() + 1);
                if (INVENTARIO.getItem(x).getDamageValue() >= INVENTARIO.getItem(x).getMaxDamage()) {
                    INVENTARIO.removeItemType(INVENTARIO.getItem(x).getItem(), 1);
                }
                break;
            }
        }
    }


    public void romperRuedas() {
        for (int x = 0; x < INVENTARIO.getContainerSize(); x++) {
            if (INVENTARIO.getItem(x).getItem() == ModItems.RUEDAS_CLAVOS.get()) {
                INVENTARIO.getItem(x).setDamageValue(INVENTARIO.getItem(x).getDamageValue() + 1);
                if (INVENTARIO.getItem(x).getDamageValue() >= INVENTARIO.getItem(x).getMaxDamage()) {
                    INVENTARIO.removeItemType(INVENTARIO.getItem(x).getItem(), 1);
                }
                break;
            }
        }
    }

    @Override
    public void startOpen(Player player) {
        INVENTARIO.startOpen(player);
    }

    public boolean tieneCofre() {
        return entityData.get(COFRE);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return INVENTARIO.canPlaceItem(index, stack);
    }

    @Override
    public int getMaxStackSize() {
        return INVENTARIO.getMaxStackSize();
    }

    @Override
    public void stopOpen(Player player) {
        INVENTARIO.stopOpen(player);
    }


    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).getItem().equals(ModItems.ITEM_COMBUSTIBLE.get())) {
            entityData.set(COMBUSTIBLE, entityData.get(COMBUSTIBLE) + 300);
            player.getItemInHand(hand).setCount(player.getItemInHand(hand).getCount() - 1);
            player.displayClientMessage(Component.translatable("item.bettervehicles.combustible").append(": " + entityData.get(COMBUSTIBLE) + " ml"), true);
        } else if (player.getItemInHand(hand).getItem().equals(Items.CHEST) && !entityData.get(COFRE)) {
            entityData.set(COFRE, true);
            if (!level().isClientSide) {
                player.getItemInHand(hand).setCount(player.getItemInHand(hand).getCount() - 1);
                player.displayClientMessage(Component.translatable("item.bettervehicles.cofreDisponible"), false);
            }
        } else {
            if (player.isCrouching() && !level().isClientSide && player instanceof ServerPlayer && entityData.get(COFRE)) {
                NetworkHooks.openScreen((ServerPlayer) player, new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return EntidadCoche.this.getDisplayName();
                    }

                    @Nullable
                    @Override
                    public AbstractContainerMenu createMenu(int i, Inventory playerINVENTARIO, Player playerEntity) {
                        return new ContenedorCoche(i, EntidadCoche.this, playerINVENTARIO);
                    }
                }, packetBuffer -> packetBuffer.writeUUID(getUUID()));
            } else {
                player.startRiding(this);
            }

        }
        return InteractionResult.SUCCESS;
    }


    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    public int getNivelAlma() {
        return entityData.get(NIVEL_DEL_ALMA);
    }

    public int getNivelAgAcuatica() {
        return entityData.get(NIVEL_AGI_ACUATICA);
    }


    @Override
    protected void positionRider(Entity passenger, MoveFunction move) {
        var pos = this.position().add(0, passenger.getMyRidingOffset()-0.15, 0);
        move.accept(passenger, pos.x, pos.y, pos.z);
    }

    @Override
    public int getContainerSize() {
        return INVENTARIO.getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return INVENTARIO.isEmpty();
    }

    @Override
    public ItemStack getItem(int p_18941_) {
        return INVENTARIO.getItem(p_18941_);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return INVENTARIO.removeItem(index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return INVENTARIO.removeItemNoUpdate(index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        INVENTARIO.setItem(index, stack);
    }

    @Override
    public void setChanged() {
        INVENTARIO.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return INVENTARIO.stillValid(player);
    }

    @Override
    public void clearContent() {
        INVENTARIO.clearContent();
    }
}
