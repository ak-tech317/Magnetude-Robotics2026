package org.firstinspires.ftc.teamcode.RDL_2026;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="RDL Autonomous", group="RDL")
public class RDL_Auto extends LinearOpMode {

    RDLHardware RDLHardware = new RDLHardware();

    double wheelCircumferance = 10;
    double FRTPR = RDLHardware.frontRightWheel.getMotorType().getTicksPerRev();
    double FLTPR = RDLHardware.frontLeftWheel.getMotorType().getTicksPerRev();
    double BRTPR = RDLHardware.backRightWheel.getMotorType().getTicksPerRev();
    double BLTPR = RDLHardware.backLeftWheel.getMotorType().getTicksPerRev();
    double inchPerWheelRev = wheelCircumferance / FRTPR;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize robot hardware
        RDLHardware.init(hardwareMap);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        while (opModeInInit()){
            telemetry.addData("Front right wheel TPR: ", FRTPR);
            telemetry.addData("Front left wheel TPR: ", FLTPR);
            telemetry.addData("Back right wheel TPR: ", BRTPR);
            telemetry.addData("Back left wheel TPR: ", BLTPR);
            telemetry.update();
        }
        while (opModeIsActive()) {
            // Add your autonomous movement or sequence logic here later
            telemetry.addLine("Autonomous running...");
            telemetry.update();

            // Wait
            sleep(1000);

            telemetry.addLine("Autonomous complete.");
            telemetry.update();
        }

    }
    private void movement(double distance, double speed, String direction){
        //Find how far we want to move
        double ticks = distance * inchPerWheelRev;

        RDLHardware.frontRightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RDLHardware.frontLeftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RDLHardware.backRightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RDLHardware.backLeftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        RDLHardware.frontRightWheel.setTargetPosition((int) ticks);
        RDLHardware.frontLeftWheel.setTargetPosition((int) ticks);
        RDLHardware.backRightWheel.setTargetPosition((int) ticks);
        RDLHardware.backLeftWheel.setTargetPosition((int) ticks);

        if(direction.equalsIgnoreCase("forward")) {
            RDLHardware.frontRightWheel.setPower(speed);
            RDLHardware.frontLeftWheel.setPower(speed);
            RDLHardware.backRightWheel.setPower(speed);
            RDLHardware.backLeftWheel.setPower(speed);
        }else if(direction.equalsIgnoreCase("backward")) {
            RDLHardware.frontRightWheel.setPower(-speed);
            RDLHardware.frontLeftWheel.setPower(-speed);
            RDLHardware.backRightWheel.setPower(-speed);
            RDLHardware.backLeftWheel.setPower(-speed);

        }else if(direction.equalsIgnoreCase("left")) {
            RDLHardware.frontRightWheel.setPower(speed);
            RDLHardware.frontLeftWheel.setPower(-speed);
            RDLHardware.backRightWheel.setPower(speed);
            RDLHardware.backLeftWheel.setPower(-speed);
        }else if(direction.equalsIgnoreCase("right")) {
            RDLHardware.frontRightWheel.setPower(-speed);
            RDLHardware.frontLeftWheel.setPower(speed);
            RDLHardware.backRightWheel.setPower(-speed);
            RDLHardware.backLeftWheel.setPower(speed);
        }

        //Move that distance at that direction in a given speed

    }
}
