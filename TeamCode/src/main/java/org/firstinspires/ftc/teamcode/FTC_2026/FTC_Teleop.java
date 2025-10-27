package org.firstinspires.ftc.teamcode.FTC_2026;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="FTC Teleop", group = "FTC")
public class FTC_Teleop extends LinearOpMode{
    //all the hardware and initialization
    FTCHardware FTCHardware = new FTCHardware();
    private final double threshold = 0.2;
    @Override
    public void runOpMode() {
        FTCHardware.init(hardwareMap);
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
        FTCHardware.frontRightWheel.setPower(vertical+horizontal+turn);
        FTCHardware.frontLeftWheel.setPower(vertical+horizontal+turn);
        FTCHardware.backRightWheel.setPower(vertical+horizontal+turn);
        FTCHardware.backLeftWheel.setPower(vertical+horizontal+turn);
    }
    private void armControl(){
        double armStop = 0.0;
        double armPower = Math.abs(gamepad1.left_stick_y) > threshold ? gamepad1.left_stick_y : armStop;

    }
    private void clawControl(){
        
    }
}