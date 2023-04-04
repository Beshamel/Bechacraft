package bapt.bechacraft.vocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import bapt.bechacraft.Bechacraft;
import bapt.bechacraft.networking.ModMessages;
import bapt.bechacraft.util.IEntityDataSaver;
import bapt.bechacraft.util.Util;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Vocation {

    private static final String ICON_PATH_PREFIX = "textures/vocation/";

    private final boolean implemented;

    private final String name;
    private final Identifier id;
    private Identifier iconId;
    private final Vocation parent;
    private final Map<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    private String translationKey;

    public Vocation(String name, Vocation parent) {
        this(name, parent, true, Maps.newHashMap());
    }

    private Vocation(String name, Vocation parent, boolean implemented, Map<EntityAttribute, EntityAttributeModifier> attributeModifiers) {
        this.implemented = implemented;
        this.name = name;
        this.iconId = new Identifier(Bechacraft.MOD_ID, ICON_PATH_PREFIX + name + ".png");
        this.parent = parent;
        this.id = new Identifier(Bechacraft.MOD_ID, name);
        this.translationKey = createTranslationKey(name);
        this.attributeModifiers = attributeModifiers;
    }

    public void onApply(PlayerEntity player) {
        for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : this.attributeModifiers.entrySet()) {
            Util.applyModifier(player, entry.getKey(), entry.getValue(), this.getName());
        }
    }

    public void onStart(PlayerEntity player, Vocation oldVocation) {
        if(this != Vocations.NONE)
            player.sendMessage(Text.translatable("msg.bechacraft.vocation_start").append(this.getDisplayName()));
        
        onApply(player);
    }

    public void onStop(PlayerEntity player, Vocation newVocation) {
        for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : this.attributeModifiers.entrySet()) {
            Util.removeModifier(player, entry.getKey(), entry.getValue());
        }
    }

    public boolean isVisible(PlayerEntity player) {
        return implemented;
    }

    public boolean unlocked(PlayerEntity player) {
        return isVisible(player);
    }

    public Vocation getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public Text getDisplayName() {
        return Text.translatable(this.getTranslationKey());
    }

    public Identifier getId() {
        return id;
    }

    public Identifier getIcon() {
        return iconId;
    }

    public Map<EntityAttribute, EntityAttributeModifier> getAttributeModifiers() {
        return this.attributeModifiers;
    }

    protected static String createTranslationKey(String name) {
        return Util.createTranslationKey("vocation", new Identifier(Bechacraft.MOD_ID, name));
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public List<Vocation> getChildren() {
        List<Vocation> children = new ArrayList<Vocation>();

        for(Vocation voc : Vocations.all())
            if(voc.getParent() == this)
                children.add(voc);
        
        return children;
    }

    public boolean inherits(Vocation vocation) {
        return this == vocation || (parent != null && parent.inherits(vocation));
    }

    public int getTier() {
        return parent == null ? 1 : (parent.getTier() + 1);
    }

    public static void set(PlayerEntity player, Vocation vocation) {
        VocationData.set(player, vocation);
        vocation.onApply(player);
    }

    public static void setIfValid(PlayerEntity player, Vocation vocation) {
        if(vocation.unlocked(player))
            set(player, vocation);
    }
    

    public static void onChange(PlayerEntity player, Vocation lastVocation, Vocation newVocation) {
        if(lastVocation != newVocation) {
            lastVocation.onStop(player, newVocation);
            newVocation.onStart(player, lastVocation);
        }
    }

    public static Vocation get(PlayerEntity player) {
        return VocationData.get(player);
    }

    public static void reset(PlayerEntity player) {
        if(Vocation.get(player) == Vocations.NONE) {
            player.sendMessage(Text.translatable("msg.bechacraft.reset_vocation_fail"));
        } else {
            Vocation.set(player, Vocations.NONE);
            player.sendMessage(Text.translatable("msg.bechacraft.reset_vocation_success"));
        }
    }

    public static void sendInfo(PlayerEntity player, Vocation vocation) {

        if(vocation == Vocations.NONE)
            player.sendMessage(Text.translatable("msg.bechacraft.no_vocation_yet"));
        else
            player.sendMessage(Text.translatable("msg.becharcaft.vocation_info_name").append(" : ").append(vocation.getDisplayName()));
    }

    public class VocationData {

        public static String KEY = "vocation";

        public static void write(IEntityDataSaver player, Vocation vocation) {
            NbtCompound nbt = player.getPersistentData();
            nbt.putString(KEY, vocation.getName());
        }
        
        public static void write(PlayerEntity player, Vocation vocation) {
            write((IEntityDataSaver) player, vocation);
        }

        public static Vocation read(IEntityDataSaver player) {
            NbtCompound nbt = player.getPersistentData();
            Vocation vocation = Vocations.fromName(nbt.getString(KEY));
            return vocation == null ? Vocations.NONE : vocation;
        }
        
        public static Vocation read(PlayerEntity player) {
            return read((IEntityDataSaver) player);
        }
    
        public static void set(PlayerEntity player, Vocation vocation) {
            if(player instanceof ServerPlayerEntity) {
                Vocation lastVocation = read(player);
                write(player, vocation);
                onChange(player, lastVocation, vocation);
                sync((ServerPlayerEntity) player, vocation);
                return;
            }
            if(player instanceof ClientPlayerEntity) {
                PacketByteBuf buffer = PacketByteBufs.create();
                buffer.writeString(vocation.getName());
                ClientPlayNetworking.send(ModMessages.VOCATION_SET_C2S, buffer);
                return;
            }
            Bechacraft.LOGGER.warn("Could not set vocation for " + player.getName() + ", of type " + player.getClass().getName());
        }

        public static void seek() {
            ClientPlayNetworking.send(ModMessages.VOCATION_SEEK_C2S, PacketByteBufs.create());
        }
        
        public static Vocation get(PlayerEntity player) {
            return read(player);
        }

        public static void sync(ServerPlayerEntity player, Vocation vocation) {
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeString(read(player).getName());
            ServerPlayNetworking.send(player, ModMessages.VOCATION_SYNC_S2C, buffer);
        }
    }

    public static class VocationBuilder {

        String name;
        Vocation parent;

        boolean implemented = true;
        Map<EntityAttribute, EntityAttributeModifier> attributeModifiers = Maps.newHashMap();

        boolean inheritsModifiers = true;

        public Vocation build() {

            if(inheritsModifiers)
                for(Map.Entry<EntityAttribute, EntityAttributeModifier> entry : parent.getAttributeModifiers().entrySet())
                    this.addAttributeModifier(entry.getKey(), entry.getValue());
                
            return new Vocation(name, parent, implemented, attributeModifiers);
        }

        public VocationBuilder(String name, Vocation parent) {
            this.name = name;
            this.parent = parent;
        }

        public VocationBuilder implemented(boolean b) {
            this.implemented = b;
            return this;
        }

        public VocationBuilder addAttributeModifier(EntityAttribute attribute, String name, double amount, EntityAttributeModifier.Operation operation) {
            EntityAttributeModifier entityAttributeModifier = new EntityAttributeModifier(name, amount, operation);
            this.addAttributeModifier(attribute, entityAttributeModifier);
            return this;
        }

        public VocationBuilder inheritsModifiers(boolean b) {
            this.inheritsModifiers = b;
            return this;
        }

        private void addAttributeModifier(EntityAttribute attribute, EntityAttributeModifier modifier) {
            this.attributeModifiers.put(attribute, modifier);
        }
    }
}