/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.scratchdrivecommands;

import java.net.Socket;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.scratch.ScratchCommand;
import frc.robot.scratch.SocketWrapper;
import frc.robot.subsystems.DriveTrainSubsystem;

public class ScratchDrive extends Command implements ScratchCommand {
    private double expoFactor = 0.2;
    private DriveTrainSubsystem driveTrain;
    private WPI_TalonSRX leftMaster;
    private WPI_TalonSRX rightMaster;
    private SpeedControllerGroup leftSide, rightSide;
    private SocketWrapper socket;
    private int count, interval;
    private double speed;
    int port;

    public ScratchDrive(int port, DriveTrainSubsystem driveTrain, double speed) {
        this.speed = speed;
        this.port = port;
        this.driveTrain = driveTrain;
        leftMaster = driveTrain.getLeftMaster();
        WPI_TalonSRX leftSlave = driveTrain.getLeftSlave();
        rightMaster = driveTrain.getRightMaster();
        WPI_TalonSRX rightSlave = driveTrain.getRightSlave();
        leftSide = new SpeedControllerGroup(leftMaster, leftSlave);
        rightSide = new SpeedControllerGroup(rightMaster, rightSlave);
        requires(driveTrain);
        count = interval = 50;
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        driveTrain.invertMotors();
        initTalon(leftMaster);
        initTalon(rightMaster);
        try {
            this.socket = new SocketWrapper(new Socket("10.59.76.93", port));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void scratchExecute(Object value) {
        // Negative below is intentional to reverse direction of joystick input
        speed = Double.valueOf(((String)value).split(":")[1]);
        String direction = ((String)value).split(":")[2];
        try {
            String temp = "";
            if((temp = socket.getReader().readLine()) == null || temp.equalsIgnoreCase(ScratchRobot.MOVE_STOP.getIDValue())) {
                drive(0,0);
            } else if(direction.equalsIgnoreCase(ScratchRobot.MOVE_FORWARD.getIDValue())) {
                drive(speed, speed);
            } else if(direction.equalsIgnoreCase(ScratchRobot.MOVE_LEFT.getIDValue())) {
                drive(-speed, speed);
            } else if(temp.equalsIgnoreCase(ScratchRobot.MOVE_RIGHT.getIDValue())) {
                drive(speed, -speed);
            } else if(temp.equalsIgnoreCase(ScratchRobot.MOVE_BACKWARD.getIDValue())) {
                drive(-speed, -speed);
            } else if(temp.contains("s:")) {
                speed = Double.valueOf(temp.split(":")[1]);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void drive(double leftSpeed, double rightSpeed) {
        leftSide.set(adjustSpeed(leftSpeed));
        rightSide.set(adjustSpeed(rightSpeed));
    }

    private double adjustSpeed(double d) {
        if (Math.abs(d) < 0.03) return 0;
        return Math.signum(d) * Math.pow(Math.abs(d), Math.pow(4, expoFactor));
    }

    private void initTalon(WPI_TalonSRX talon) {
        talon.selectProfileSlot(1, 0);
        talon.configPeakOutputForward(1, 0);
        talon.configPeakOutputReverse(-1, 0);
        talon.configNominalOutputForward(0, 0);
        talon.configNominalOutputReverse(0, 0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted() {
        end();
    }

    private enum ScratchRobot {
        MOVE_FORWARD(1),
        MOVE_LEFT(2),
        MOVE_RIGHT(3),
        MOVE_BACKWARD(4),
        MOVE_STOP(0);

        private int id;

        ScratchRobot(int id) {
            this.id = id;
        }

        public String getIDValue() {
            return String.valueOf(id);
        }

        public int getID() {
            return id;
        }


    }


}
