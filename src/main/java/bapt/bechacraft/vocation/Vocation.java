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

    private final boolean visible;

    private final String name;
    private final Identifier id;
    private VocationDisplay display;
    private final Vocation parent;
    private final Map<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public Vocation(String name, Vocation parent) {
        this(name, parent, 0, 0, false, Maps.newHashMap());
    }

    public Vocation(String name, Vocation parent, int x, int y, boolean visible, Map<EntityAttribute, EntityAttributeModifier> attributeModifiers) {
        this.visible = visible;
        this.name = name;
        Identifier iconId = new Identifier(Bechacraft.MOD_ID, ICON_PATH_PREFIX + name + ".png");
        this.parent = parent;
        this.id = new Identifier(Bechacraft.MOD_ID, name);
        String translationKey = createTranslationKey(name);
        this.attributeModifiers = attributeModifiers;
        this.display = new VocationDisplay(this, iconId, translationKey, x, y, visible);
    }

    public void onApply(PlayerEntity player) {
        for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : this.attributeModifiers.entrySet()) {
            Util.applyModifier(player, entry.getKey(), entry.getValue(), this.getName());
        }
    }

    public void onStart(PlayerEntity player, Vocation oldVocation) {
        if(this != Vocations.NONE)
            player.sendMessage(Text.translatable("msg.bechacraft.vocation_start").append(display.getDisplayName()));
        
        onApply(player);
    }

    public void onStop(PlayerEntity player, Vocation newVocation) {
        for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : this.attributeModifiers.entrySet()) {
            Util.removeModifier(player, entry.getKey(), entry.getValue());
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean revealed(PlayerEntity player) {
        return isVisible() && Vocation.get(player).getFamily().contains(this);
    }

    public boolean unlocked(PlayerEntity player) {
        return revealed(player) && (this.parent == Vocation.get(player) || Vocation.get(player).inherits(this));
    }

    public Vocation getParent() {
        return parent;
    }

    public List<Vocation> getLineage() {
        List<Vocation> lineage = new ArrayList<Vocation>();
        lineage.add(this);
        if(parent != null) {
            lineage.addAll(parent.getLineage());
        }
        return lineage;
    }

    public List<Vocation> getFamily() {
        List<Vocation> lineage = getLineage();
        List<Vocation> family = new ArrayList<Vocation>();
        family.addAll(lineage);
        for(Vocation voc : lineage) {
            for(Vocation c : voc.getChildren()) {
                if(!family.contains(c)) {
                    family.add(c);
                }
            }
        }
        return family;
    }

    public String getName() {
        return name;
    }

    public Identifier getId() {
        return id;
    }

    public VocationDisplay getDisplay() {
        return display;
    }

    public Map<EntityAttribute, EntityAttributeModifier> getAttributeModifiers() {
        return this.attributeModifiers;
    }

    protected static String createTranslationKey(String name) {
        return Util.createTranslationKey("vocation", new Identifier(Bechacraft.MOD_ID, name));
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
        return parent == null ? 0 : (parent.getTier() + 1);
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
            player.sendMessage(Text.translatable("msg.becharcaft.vocation_info_name").append(" : ").append(vocation.getDisplay().getDisplayName()));
    }

    public class VocationData {

        public static String KEY = "vocation";

        public static void write(IEntityDataSaver player, Vocation vocation) {
            if(player == null) return;
            NbtCompound nbt = player.getPersistentData();
            nbt.putString(KEY, vocation.getName());
        }
        
        public static void write(PlayerEntity player, Vocation vocation) {
            write((IEntityDataSaver) player, vocation);
        }

        public static Vocation read(IEntityDataSaver player) {
            if(player == null) return Vocations.NONE;
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
                sync((ServerPlayerEntity) player);
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

        public static void sync(ServerPlayerEntity player) {
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeString(read(player).getName());
            read(player).onApply(player);
            ServerPlayNetworking.send(player, ModMessages.VOCATION_SYNC_S2C, buffer);
        }
    }

    public static class VocationBuilder {

        String name;
        Vocation parent;

        boolean visible = false;
        Map<EntityAttribute, EntityAttributeModifier> attributeModifiers = Maps.newHashMap();
        int x = 0;
        int y = 0;

        boolean inheritsModifiers = true;

        public Vocation build() {

            if(inheritsModifiers)
                for(Map.Entry<EntityAttribute, EntityAttributeModifier> entry : parent.getAttributeModifiers().entrySet())
                    this.addAttributeModifier(entry.getKey(), entry.getValue());
                
            return new Vocation(name, parent, x, y, visible && (parent == null || parent.isVisible()), attributeModifiers);
        }

        public VocationBuilder(String name, Vocation parent) {
            this.name = name;
            this.parent = parent;
        }

        public VocationBuilder visible(boolean b) {
            this.visible = b;
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

        public VocationBuilder displayAt(int x, int y) {
            visible = true;
            this.x = x;
            this.y = y;
            return this;
        }

        public VocationBuilder shift(Vocation vocation, int x, int y) {
            return displayAt(vocation.getDisplay().getX() + x, vocation.getDisplay().getY() + y);
        }

        public VocationBuilder shift(int x, int y) {
            return shift(parent, x, y);
        }

        private void addAttributeModifier(EntityAttribute attribute, EntityAttributeModifier modifier) {
            this.attributeModifiers.put(attribute, modifier);
        }
    }
}