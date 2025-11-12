package org.firstinspires.ftc.teamcode.RDL_2026;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="OtherRDL", group = "RDL")
public class RDL_Teleop2 extends LinearOpMode{
    //all the hardware and initialization
    RDL2Hardware otherRDLhardware = new RDL2Hardware();
    private final double threshold = 0.2;
    @Override
    public void runOpMode() {
        otherRDLhardware.init(hardwareMap);
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
        otherRDLhardware.frontRight.setPower(vertical+horizontal+turn);
        otherRDLhardware.frontLeft.setPower(vertical+horizontal+turn);
        otherRDLhardware.backRight.setPower(vertical+horizontal+turn);
        otherRDLhardware.backLeft.setPower(vertical+horizontal+turn);
    }
    private void armControl(){
        double armStop = 0.0;
        double armPower = 0.5;

        if(gamepad1.right_trigger > 0){
            otherRDLhardware.armMotor.setPower(armPower);
        }else if(gamepad1.left_bumper){
            otherRDLhardware.armMotor.setPower(-armPower);
        }else{
            otherRDLhardware.armMotor.setPower(armStop);
        }
    }
    private void clawControl(){
        //First number is back motor, second is front
        double[] claw1pos = {0.5, 0.0};
        double[] claw2pos = {0.5, 0.0};

        boolean apressy = gamepad1.aWasPressed();
        int a = 0;
        int b = 0;
        boolean bpressy = gamepad1.bWasPressed();

        if(gamepad1.a){
            if (apressy && (a == 0)) {
                otherRDLhardware.backClaw.setPosition(claw1pos[0]);
                a = 1;
            }
            if (apressy && (a == 1)) {
                otherRDLhardware.backClaw.setPosition(claw1pos[1]);
                a = 0;
            }
        }

        if(gamepad1.y) {
            if (bpressy && (b == 0)) {
                otherRDLhardware.frontClaw.setPosition(claw1pos[0]);
                b = 1;
            }
            if (bpressy && (b == 1)) {
                otherRDLhardware.frontClaw.setPosition(claw1pos[1]);
                b = 0;
            }
        }

    }
}