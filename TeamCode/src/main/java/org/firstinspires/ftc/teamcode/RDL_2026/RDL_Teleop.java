package org.firstinspires.ftc.teamcode.RDL_2026;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="RDL Teleop", group = "RDL")
public class RDL_Teleop extends LinearOpMode{
    //all the hardware and initialization
    RDLHardware RDLHardware = new RDLHardware();

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
        double threshold = 0.2;
        double vertical = Math.abs(gamepad1.right_stick_y) > threshold ? 0.8* -gamepad1.right_stick_y : 0;
        double turn = Math.abs(gamepad1.right_stick_x) > threshold ? 0.5* gamepad1.right_stick_x: 0;
        RDLHardware.frontRightWheel.setPower(vertical-(1.5 * turn));
        RDLHardware.frontLeftWheel.setPower(vertical+(1.5 * turn));
        RDLHardware.backRightWheel.setPower(vertical-(turn));
        RDLHardware.backLeftWheel.setPower(vertical+(turn));
    }
    private void armControl(){
        double armStop = 0.05;
        double armPower = 1;

        if(gamepad1.right_trigger > 0){
            RDLHardware.armMotor.setPower(armPower);
        }else if(gamepad1.left_trigger > 0){
            RDLHardware.armMotor.setPower(0.5 * -armPower);
        }else{
            RDLHardware.armMotor.setPower(armStop);
        }

        if(gamepad1.a){
            RDLHardware.armHolder.setPower(1);
        }else{
            RDLHardware.armHolder.setPower(0);
        }
    }
    private void clawControl(){
        //First number is back motor, second is front
        int[] intakePowerLevel = {1, -1, 1};
        int[] outtakePowerLevel = {-1, 1, -1};

        if(gamepad1.left_bumper){
            RDLHardware.backClaw.setPower(intakePowerLevel[0]);
            RDLHardware.frontClaw.setPower(intakePowerLevel[1]);
            RDLHardware.backestClaw.setPower(intakePowerLevel[2]);
        }else if(gamepad1.right_bumper){
            RDLHardware.backClaw.setPower(outtakePowerLevel[0]);
            RDLHardware.frontClaw.setPower(outtakePowerLevel[1]);
            RDLHardware.backestClaw.setPower(outtakePowerLevel[2]);
        }else{
            RDLHardware.backClaw.setPower(0);
            RDLHardware.frontClaw.setPower(0);
            RDLHardware.backestClaw.setPower(0);
        }
    }
}