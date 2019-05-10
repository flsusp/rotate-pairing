package br.com.rotatepairing.commands;

import br.com.rotatepairing.Environment;
import br.com.rotatepairing.EnvironmentHolder;
import br.com.rotatepairing.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.*;

class RolesCommandTest {

    private Screen screen = mock(Screen.class);

    @BeforeEach
    void setup() throws IOException {
        Path tempDir = Files.createTempDirectory("rotate-pairs");
        tempDir.toFile().deleteOnExit();
        Environment environment = new Environment(tempDir.toString(), screen);
        EnvironmentHolder.setEnvironment(environment);

        PairsCommand.main(new String[]{"init"});
    }

    @Nested
    class WhenInitialRoles {

        @Test
        void listsInitialRoles() {
            PairsCommand.main(new String[]{"roles", "ls"});

            verify(screen).show("> development");
            verify(screen).show("> support");
        }
    }

    @Nested
    class WhenRolesAdded {

        @BeforeEach
        void setup() {
            PairsCommand.main(new String[]{"roles", "add", "analysis"});
        }

        @Test
        void listsRoleAdded() {
            PairsCommand.main(new String[]{"roles", "ls"});

            verify(screen).show("> development");
            verify(screen).show("> support");
            verify(screen).show("> analysis");
        }

        @Nested
        class WhenRolesRemoved {

            @BeforeEach
            void setup() {
                PairsCommand.main(new String[]{"roles", "rm", "support"});
            }

            @Test
            void doesNotListPeopleRemoved() {
                PairsCommand.main(new String[]{"roles", "ls"});

                verify(screen).show("> development");
                verify(screen, never()).show("> support");
            }
        }
    }
}