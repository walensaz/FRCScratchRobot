package websocket;

import me.zach.main.MultiThreadedServer;
import me.zach.main.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class SocketListener implements Runnable {
    private MultiThreadedServer server;
    private ScratchServer scratchServer;

    public SocketListener(Server server, ScratchServer scratchServer) {
        System.out.println("Starting up Socket Layer!");
        this.server = new MultiThreadedServer(server);
        Launcher.threads.schedule(this.server, 1, TimeUnit.MILLISECONDS);
        this.scratchServer = scratchServer;
    }

    @Override
    public void run() {
        Socket newSocket = null;
        newSocket = server.listen();
        if (newSocket != null) {
            System.out.println("We have connected with the robot!");
            handleConnection(newSocket);
        }
    }

    private void handleConnection(Socket socket) {
        try {
            Bridge.mainBridge = new Bridge(socket, scratchServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}