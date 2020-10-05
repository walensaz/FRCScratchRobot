/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
  public static int leftTalonPort = 0;
  public static int rightTalonPort = 1;

  public static int LEFT_MASTER_TALON_ID = 2;
  public static int LEFT_SLAVE_TALON_ID = 1;
  public static int RIGHT_MASTER_TALON_ID = 4;
  public static int RIGHT_SLAVE_TALON_ID = 3;

  public static int LIFT_TALON_ID = 5;

  public static int INTAKE_TALON_ID = 6;
}
