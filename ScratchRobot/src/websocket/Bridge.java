package websocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Bridge {

    public static Bridge mainBridge;

    Socket socket;
    ScratchServer scratchServer;
    PrintWriter socketWriter;
    BufferedReader socketReader;

    public Bridge(Socket socket, ScratchServer scratchServer) throws IOException {
        this.socket = socket;
        this.scratchServer = scratchServer;
        this.socketWriter = new PrintWriter(socket.getOutputStream());
        this.socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void send(String string) {
        socketWriter.println(string);
        socketWriter.flush();
    }

}
