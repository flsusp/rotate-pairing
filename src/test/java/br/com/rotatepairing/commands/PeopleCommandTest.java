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

class PeopleCommandTest {

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
    class WhenEmptyPeople {

        @Test
        void listsEmptyListOfPeople() {
            PairsCommand.main(new String[]{"people", "ls"});

            verify(screen, never()).show(anyString());
        }
    }

    @Nested
    class WhenPeopleAdded {

        @BeforeEach
        void setup() {
            PairsCommand.main(new String[]{"people", "add", "joao", "maria"});
        }

        @Test
        void listsPeopleAdded() {
            PairsCommand.main(new String[]{"people", "ls"});

            verify(screen).show("> joao");
            verify(screen).show("> maria");
        }

        @Nested
        class WhenPeopleRemoved {

            @BeforeEach
            void setup() {
                PairsCommand.main(new String[]{"people", "rm", "joao"});
            }

            @Test
            void doesNotListPeopleRemoved() {
                PairsCommand.main(new String[]{"people", "ls"});

                verify(screen).show("> maria");
                verify(screen, never()).show("> joao");
            }
        }
    }
}
