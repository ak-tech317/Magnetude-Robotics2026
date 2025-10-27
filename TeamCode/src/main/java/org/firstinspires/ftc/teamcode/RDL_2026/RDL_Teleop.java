package org.firstinspires.ftc.teamcode.RDL_2026;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="RDL Teleop", group = "RDL")
public class RDL_Teleop extends LinearOpMode{
    //all the hardware and initialization
    RDLHardware RDLHardware = new RDLHardware();
    private final double threshold = 0.2;
    @Override
    public void runOpMode() {
        RDLHardware.init(hardwareMap);
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
        RDLHardware.frontRightWheel.setPower(vertical+horizontal+turn);
        RDLHardware.frontLeftWheel.setPower(vertical+horizontal+turn);
        RDLHardware.backRightWheel.setPower(vertical+horizontal+turn);
        RDLHardware.backLeftWheel.setPower(vertical+horizontal+turn);
    }
    private void armControl(){
        double armStop = 0.0;
        double armPower = 0.5;

        if(gamepad1.right_trigger > 0){
            RDLHardware.armMotor.setPower(armPower);
        }else if(gamepad1.left_bumper){
            RDLHardware.armMotor.setPower(-armPower);
        }else{
            RDLHardware.armMotor.setPower(armStop);
        }
    }
    private void clawControl(){
        //First number is back motor, second is front
        int[] intakePowerLevel = {-1, 1};
        int[] outtakePowerLevel = {1, -1};

        if(gamepad1.right_bumper){
            RDLHardware.backClaw.setPower(intakePowerLevel[0]);
            RDLHardware.frontClaw.setPower(intakePowerLevel[1]);
        }else if(gamepad1.left_bumper){
            RDLHardware.backClaw.setPower(outtakePowerLevel[0]);
            RDLHardware.frontClaw.setPower(outtakePowerLevel[1]);
        }else{
            RDLHardware.backClaw.setPower(0);
            RDLHardware.frontClaw.setPower(0);
        }

    }
}