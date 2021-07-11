package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;

class LauncherTest {

    static final Launcher server = new Launcher();

    @Test
    public void main() {
        Launcher.main(new String[]{"3001"});
        Launcher.main(new String[]{"3000", "http://localhost:3001"});
    }
}
