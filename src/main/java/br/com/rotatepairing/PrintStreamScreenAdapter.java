package br.com.rotatepairing;

import java.io.*;

public class PrintStreamScreenAdapter extends PrintStream {

    public PrintStreamScreenAdapter(Screen screen) throws IOException {
        super(pipeTo(screen));
    }

    private static OutputStream pipeTo(Screen screen) throws IOException {
        final PipedOutputStream output = new PipedOutputStream();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(new PipedInputStream(output)));

        new Thread(() -> {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    screen.show(line);
                }
            } catch (IOException e) {
            }
        }, "piped-screen").start();

        return output;
    }
}
