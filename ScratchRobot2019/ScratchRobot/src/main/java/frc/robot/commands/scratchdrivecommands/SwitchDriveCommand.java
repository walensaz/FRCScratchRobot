/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.scratchdrivecommands;


import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.commands.drivetraincommands.TeleOpTankDrive;
import frc.robot.commands.scratchdrivecommands.ScratchDrive;
import frc.robot.subsystems.DriveTrainSubsystem;
/**
 * Add your docs here.
 */
public class SwitchDriveCommand extends InstantCommand {

  private boolean isInScratchDrive;
  private DriveTrainSubsystem driveTrain;
  private OI oi;
  /**
   * Add your docs here.
   */
  public SwitchDriveCommand(DriveTrainSubsystem driveTrain, OI oi) {
    super();
    isInScratchDrive = true;
    this.driveTrain = driveTrain;
    this.oi = oi;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }



  @Override
  protected void execute() {
      isInScratchDrive = !isInScratchDrive;
      System.out.println("Activating command as " + isInScratchDrive);
      if(isInScratchDrive) {
          driveTrain.getDefaultCommand().cancel();
          driveTrain.setDefaultCommand(new ScratchDrive(5809, driveTrain, .5));
      } else {
          driveTrain.getDefaultCommand().cancel();
          driveTrain.setDefaultCommand(new TeleOpTankDrive(oi.getDriverController(), driveTrain));
      }
  }

}
