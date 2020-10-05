package websocket;

import me.zach.main.Server;
import org.java_websocket.server.DefaultSSLWebSocketServerFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.security.KeyStore;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Launcher2 {

    public static final ScheduledExecutorService threads = Executors.newScheduledThreadPool(3);

    public static void main(String[] args) throws Exception {
        ScratchServer s = new ScratchServer(8887);
        s.start();
        System.out.println("ChatServer started on port: " + s.getPort());
        Server server = new Server(5809);
        SocketListener listener = new SocketListener(server, s);
        Launcher.threads.scheduleAtFixedRate(listener::run, 10L, 100L, TimeUnit.MILLISECONDS);

    }

}
