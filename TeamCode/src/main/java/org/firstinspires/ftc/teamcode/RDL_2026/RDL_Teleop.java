package org.firstinspires.ftc.teamcode.RDL_2026;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware;

@TeleOp(name="RDL Teleop", group = "RDL")
public class RDL_Teleop extends LinearOpMode{
    //all the hardware and initialization
    hardware hardware = new hardware();
    private final double threshold = 0.2;
    @Override
    public void runOpMode() {
        hardware.init(hardwareMap);
        hardware.setDirection();
        while(opModeInInit()){
            telemetry.addLine("Robot is initialized");
            telemetry.update();
        }
        waitForStart();
        while(opModeIsActive()){
                driveControls();
                armControl();
                clawControl();

        }
    }

    private void driveControls(){
        double vertical = Math.abs(gamepad1.right_stick_y) > threshold ? gamepad1.right_stick_y : 0;
        double horizontal = Math.abs(gamepad1.right_stick_x) > threshold ? gamepad1.right_stick_x: 0;
        double turn = Math.abs(gamepad1.left_stick_x) > threshold ? gamepad1.left_stick_x: 0;
        hardware.frontRightWheel.setPower(vertical+horizontal+turn);
        hardware.frontLeftWheel.setPower(vertical+horizontal+turn);
        hardware.backRightWheel.setPower(vertical+horizontal+turn);
        hardware.backLeftWheel.setPower(vertical+horizontal+turn);
    }
    private void armControl(){
        double armStop = 0.0;
        double armPower = Math.abs(gamepad1.left_stick_y) > threshold ? gamepad1.left_stick_y : armStop;
        hardware.armMotor.setPower(armPower);
    }
    private void clawControl(){
        //First number is back motor, second is front
        int[] intakePowerLevel = {-1, 1};
        int[] outtakePowerLevel = {1, -1};

        if(gamepad1.dpad_up){
            hardware.backClaw.setPower(intakePowerLevel[0]);
            hardware.frontClaw.setPower(intakePowerLevel[1]);
        }else if(gamepad1.dpad_down){
            hardware.backClaw.setPower(outtakePowerLevel[0]);
            hardware.frontClaw.setPower(outtakePowerLevel[1]);
        }else{
            hardware.backClaw.setPower(0);
            hardware.frontClaw.setPower(0);
        }

    }
}
