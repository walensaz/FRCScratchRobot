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

public class Launcher {

    public static final ScheduledExecutorService threads = Executors.newScheduledThreadPool(3);

    public static void main(String[] args) throws Exception {
        ScratchServer s = new ScratchServer(8887);
        // load up the key store
        String STORETYPE = "JKS";
        String KEYSTORE = "D:\\Programming\\keystore.jks";
        String STOREPASSWORD = "password";
        String KEYPASSWORD = "password";

        KeyStore ks = KeyStore.getInstance( STORETYPE );
        File kf = new File( KEYSTORE );
        ks.load( new FileInputStream( kf ), STOREPASSWORD.toCharArray() );

        KeyManagerFactory kmf = KeyManagerFactory.getInstance( "SunX509" );
        kmf.init( ks, KEYPASSWORD.toCharArray() );
        TrustManagerFactory tmf = TrustManagerFactory.getInstance( "SunX509" );
        tmf.init( ks );

        SSLContext sslContext = null;
        sslContext = SSLContext.getInstance( "TLS" );
        sslContext.init( kmf.getKeyManagers(), tmf.getTrustManagers(), null );

        s.setWebSocketFactory( new DefaultSSLWebSocketServerFactory( sslContext ) );
        s.start();
        System.out.println("ChatServer started on port: " + s.getPort());
        Server server = new Server(5809);
        SocketListener listener = new SocketListener(server, s);
        Launcher.threads.scheduleAtFixedRate(listener::run, 10L, 100L, TimeUnit.MILLISECONDS);


    }

}
