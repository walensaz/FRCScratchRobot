/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.scratch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Add your docs here.
 */
public class SocketWrapper {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public SocketWrapper(Socket socket) {
        try {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void sendData(String data) {
        writer.println(data);
        writer.flush();
    }

    public BufferedReader getReader() {
        return reader;
    }


}
