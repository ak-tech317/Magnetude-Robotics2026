package org.firstinspires.ftc.teamcode.RDL_2026;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="RDL Autonomous", group="RDL")
public class RDL_Auto extends LinearOpMode {

    RDLHardware RDLHardware = new RDLHardware();

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize robot hardware
        RDLHardware.init(hardwareMap);

        double wheelDiamater = 3;
        double wheelCircumferance = wheelDiamater * Math.PI;
        double FRTPR = RDLHardware.frontRightWheel.getMotorType().getTicksPerRev();
        double FLTPR = RDLHardware.frontLeftWheel.getMotorType().getTicksPerRev();
        double BRTPR = RDLHardware.backRightWheel.getMotorType().getTicksPerRev();
        double BLTPR = RDLHardware.backLeftWheel.getMotorType().getTicksPerRev();
        double ticksPerInch = (FRTPR + FLTPR + BRTPR + BLTPR) / 4.0 / wheelCircumferance;


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        while (opModeInInit()) {
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

            movement(ticksPerInch, 12, 0.5, "forward");
            // Wait
            sleep(1000);

            telemetry.addLine("Autonomous complete.");
            telemetry.update();
        }

    }

    private void movement(double ticksPerInch, double distance, double speed, String direction) {
        //Find how far we want to move
        double ticks = distance * ticksPerInch;
        RDLHardware.frontRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RDLHardware.frontLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RDLHardware.backRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RDLHardware.backLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        RDLHardware.frontRightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RDLHardware.frontLeftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RDLHardware.backRightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RDLHardware.backLeftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (direction.equalsIgnoreCase("forward")) {
            RDLHardware.frontRightWheel.setTargetPosition((int) ticks);
            RDLHardware.frontLeftWheel.setTargetPosition((int) ticks);
            RDLHardware.backRightWheel.setTargetPosition((int) ticks);
            RDLHardware.backLeftWheel.setTargetPosition((int) ticks);
        } else if (direction.equalsIgnoreCase("backward")) {
            RDLHardware.frontRightWheel.setTargetPosition(-(int) ticks);
            RDLHardware.frontLeftWheel.setTargetPosition(-(int) ticks);
            RDLHardware.backRightWheel.setTargetPosition(-(int) ticks);
            RDLHardware.backLeftWheel.setTargetPosition(-(int) ticks);
        }

        RDLHardware.frontRightWheel.setPower(speed);
        RDLHardware.frontLeftWheel.setPower(speed);
        RDLHardware.backRightWheel.setPower(speed);
        RDLHardware.backLeftWheel.setPower(speed);

        while (opModeIsActive() &&
                (RDLHardware.frontRightWheel.isBusy() ||
                        RDLHardware.frontLeftWheel.isBusy() ||
                        RDLHardware.backRightWheel.isBusy() ||
                        RDLHardware.backLeftWheel.isBusy())) {

            telemetry.addData("Status", "Moving...");
            telemetry.addData("FR pos", RDLHardware.frontRightWheel.getCurrentPosition());
            telemetry.update();

        }
        RDLHardware.frontRightWheel.setPower(0);
        RDLHardware.frontLeftWheel.setPower(0);
        RDLHardware.backRightWheel.setPower(0);
        RDLHardware.backLeftWheel.setPower(0);

        RDLHardware.frontRightWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RDLHardware.frontLeftWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RDLHardware.backRightWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RDLHardware.backLeftWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
}
