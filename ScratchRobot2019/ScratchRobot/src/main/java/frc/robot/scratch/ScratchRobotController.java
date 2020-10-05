/*
 * By Zachary Walensa (Class of 2020)
 * Team 5976
 * Catholic Memorial CyberSaders
 * Made in 2019
 */


package frc.robot.scratch;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.scratchdrivecommands.ScratchDrive;
import frc.robot.subsystems.DriveTrainSubsystem;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScratchRobotController implements Runnable {

    private DriveTrainSubsystem driveTrain;

    private SocketWrapper socket;
    private final int PORT = 5809;
    private static ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);

    private ArrayList<ScratchCommand> scratchCommands;

    public void init(Subsystem... subsystems) throws IOException {

        this.driveTrain = (DriveTrainSubsystem) subsystems[0];
        this.socket = new SocketWrapper(new Socket("10.59.76.93", PORT));
        scratchCommands = new ArrayList<>();
    }


    public void start() {
        scheduler.scheduleAtFixedRate(this, 40L, 15L, TimeUnit.MILLISECONDS);
        this.scratchCommands.add(new ScratchCommand("d", new ScratchDrive(5809, driveTrain, .5)));
    }

    /**
     * This is where you will put any commands you would want to use with scratch
     * Each command should have its own identifier, I would either use id's or simple letters
     * These ids will be used to send data between the scratchX program and this
     * Values will be sent along with these and I would recommend passing those into these commands.
     *
     * Or just come up with your own way! :D
     */


    public void run() {
        String temp = "";
        final String id;
        final String info;
        try {
            if((temp = socket.getReader().readLine()) != null) {
                id = temp.split(":")[0];
                temp = temp.replace(id + ":", "");
                info = temp;
                this.scratchCommands.forEach((ScratchCommand command) -> {
                    if(command.getId().equalsIgnoreCase(id.trim())) {
                        command.getCommand().scratchExecute(info);
                    }
                });
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    class ScratchCommand {

        private frc.robot.scratch.ScratchCommand Command;
        private String id;

        ScratchCommand(String id, frc.robot.scratch.ScratchCommand command) {
            Command = command;
            this.id = id;
        }

        private frc.robot.scratch.ScratchCommand getCommand() {
            return Command;
        }

        public void setCommand(frc.robot.scratch.ScratchCommand command) {
            Command = command;
        }

        private String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }


}
