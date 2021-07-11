package fr.lernejo.navy_battle;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LauncherTest {

    static final Launcher server = new Launcher();

    @Test
    public void main() {
        Launcher.main(new String[]{"3000", "http://localhost:3000"});
    }
}
