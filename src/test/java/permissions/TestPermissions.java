package permissions;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO: more tests
public class TestPermissions {

    private Player player;

    @BeforeEach
    public void init() {
        MinecraftServer.init(); // for entity manager
        player = new Player(UUID.randomUUID(), "TestPlayer", null) {
            @Override
            protected void playerConnectionInit() {
            }

            @Override
            public boolean isOnline() {
                return false;
            }
        };
    }

    @Test
    public void noPermission() {
        assertFalse(player.hasPermission(Permission.class));
    }

    class PermTest1 implements Permission<Object> {
        @Override
        public boolean isValidFor(@NotNull CommandSender commandSender, Object data) {
            return true;
        }
    }

    class PermTest2 implements Permission<Object> {
        @Override
        public boolean isValidFor(@NotNull CommandSender commandSender, Object data) {
            return true;
        }
    }

    @Test
    public void hasPermissionClass() {
        assertFalse(player.hasPermission(Permission.class));
        player.addPermission(new PermTest1());
        assertTrue(player.hasPermission(PermTest1.class));
        assertFalse(player.hasPermission(PermTest2.class));
        assertTrue(player.hasPermission(Permission.class)); // allow superclasses

        player.addPermission(new PermTest2());
        assertTrue(player.hasPermission(PermTest2.class));
    }

    class BooleanPerm implements Permission<Object> {
        private final boolean value;

        BooleanPerm(boolean v) {
            this.value = v;
        }

        @Override
        public boolean isValidFor(@NotNull CommandSender commandSender, Object data) {
            return value;
        }
    }

    @Test
    public void hasTwoPermissionsOfSameClassButContradictEachOther() {
        player.addPermission(new BooleanPerm(true));
        assertTrue(player.hasPermission(BooleanPerm.class));
        player.addPermission(new BooleanPerm(false));
        assertFalse(player.hasPermission(BooleanPerm.class)); // all permissions must be valid
    }

    @Test
    public void singlePermission() {
        Permission p = (commandSender, data) -> true;
        player.addPermission(p);
        assertTrue(p.isValidFor(player, null));
        assertTrue(player.hasPermission(p));
        assertTrue(player.hasPermission(Permission.class));
    }

    @AfterEach
    public void cleanup() {

    }
}
