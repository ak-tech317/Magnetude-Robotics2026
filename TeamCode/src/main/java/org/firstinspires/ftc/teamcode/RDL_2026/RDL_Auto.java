package org.firstinspires.ftc.teamcode.RDL_2026;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="RDL Autonomous", group="RDL")
public class RDL_Auto extends LinearOpMode {

    RDLHardware RDLHardware = new RDLHardware();
    double ticksPerFoot = 140;
    double ticksPer360 = 1095;
    double ticksPerDegree = ticksPer360/360;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize robot hardware
        RDLHardware.init(hardwareMap);

        telemetry.addLine("Initialized and ready");
        telemetry.update();

        // Wait for start
        waitForStart();

        if (opModeIsActive()) {
            telemetry.addLine("Autonomous started...");
            telemetry.update();

            // Move forward 12 inches
            movement(1, 0.2, "forward");
            movement(1, 0.2, "backward");

            // Turn tests
            turn(360, 0.1, "right");
            turn(360, 0.1, "left");

            telemetry.addLine("Autonomous complete.");
            telemetry.update();
            sleep(1000);
        }
    }

    private void movement(double distance, double speed, String direction) {
        double ticks = distance * ticksPerFoot;
        int target = (int) ticks;

        telemetry.addData("Target Ticks: ", target);
        telemetry.update();

        // Reset encoders once before move
        resetEncoders();

        // Set target positions
        if(direction.equalsIgnoreCase("forward")) {
            RDLHardware.frontRightWheel.setTargetPosition(target);
            RDLHardware.frontLeftWheel.setTargetPosition(target);
            RDLHardware.backRightWheel.setTargetPosition(target);
            RDLHardware.backLeftWheel.setTargetPosition(target);
        }else if(direction.equalsIgnoreCase("backward")) {
            RDLHardware.frontRightWheel.setTargetPosition(-target);
            RDLHardware.frontLeftWheel.setTargetPosition(-target);
            RDLHardware.backRightWheel.setTargetPosition(-target);
            RDLHardware.backLeftWheel.setTargetPosition(-target);
        }

        // Set mode to run to position
        setAllModes(DcMotor.RunMode.RUN_TO_POSITION);

        // Power all wheels evenly
        RDLHardware.frontRightWheel.setPower(Math.abs(speed));
        RDLHardware.frontLeftWheel.setPower(Math.abs(speed));
        RDLHardware.backRightWheel.setPower(Math.abs(speed));
        RDLHardware.backLeftWheel.setPower(Math.abs(speed));

        // Timeout safeguard
        double startTime = getRuntime();

        while (opModeIsActive() &&
                (RDLHardware.frontRightWheel.isBusy() ||
                        RDLHardware.frontLeftWheel.isBusy() ||
                        RDLHardware.backRightWheel.isBusy() ||
                        RDLHardware.backLeftWheel.isBusy())) {

            telemetry.addData("Moving", "Direction: %s | Target: %d ticks", direction, target);
            telemetry.addData("FR pos", RDLHardware.frontRightWheel.getCurrentPosition());
            telemetry.addData("FL pos", RDLHardware.frontLeftWheel.getCurrentPosition());
            telemetry.addData("BR pos", RDLHardware.backRightWheel.getCurrentPosition());
            telemetry.addData("BL pos", RDLHardware.backLeftWheel.getCurrentPosition());
            telemetry.update();

            // keep your original break condition exactly as written
            if (Math.abs(RDLHardware.frontRightWheel.getCurrentPosition()) >= target ||
                    Math.abs(RDLHardware.frontLeftWheel.getCurrentPosition()) >= target ||
                    Math.abs(RDLHardware.backRightWheel.getCurrentPosition()) >= target ||
                    Math.abs(RDLHardware.backLeftWheel.getCurrentPosition()) >= target) {
                break;
            }

            // timeout fail-safe
            if (getRuntime() - startTime > 5) break;
        }

        stopAll();
        setAllModes(DcMotor.RunMode.RUN_USING_ENCODER);
        sleep(500);
    }

    private void turn(double degrees, double speed, String direction) {
        double ticksPer360 = 1095;
        double ticksPerDegree = ticksPer360 / 360.0;
        int tickTarget = (int)(degrees * ticksPerDegree);

        resetEncoders();

        // Set target positions depending on direction
        if (direction.equalsIgnoreCase("right")) {
            RDLHardware.frontRightWheel.setTargetPosition(-tickTarget);
            RDLHardware.backRightWheel.setTargetPosition(-tickTarget);
            RDLHardware.frontLeftWheel.setTargetPosition(tickTarget);
            RDLHardware.backLeftWheel.setTargetPosition(tickTarget);
        } else if (direction.equalsIgnoreCase("left")) {
            RDLHardware.frontRightWheel.setTargetPosition(tickTarget);
            RDLHardware.backRightWheel.setTargetPosition(tickTarget);
            RDLHardware.frontLeftWheel.setTargetPosition(-tickTarget);
            RDLHardware.backLeftWheel.setTargetPosition(-tickTarget);
        } else {
            telemetry.addLine("Invalid direction (use 'left' or 'right')");
            telemetry.update();
            return;
        }

        // Set to run to position
        setAllModes(DcMotor.RunMode.RUN_TO_POSITION);

        // Apply your intentional power balance
        RDLHardware.frontRightWheel.setPower(1.5 * speed);
        RDLHardware.frontLeftWheel.setPower(1.5 * speed);
        RDLHardware.backRightWheel.setPower(speed);
        RDLHardware.backLeftWheel.setPower(speed);

        // Monitor encoder progress with a clean stop condition
        while (true) {
            int FR = Math.abs(RDLHardware.frontRightWheel.getCurrentPosition());
            int FL = Math.abs(RDLHardware.frontLeftWheel.getCurrentPosition());
            int BR = Math.abs(RDLHardware.backRightWheel.getCurrentPosition());
            int BL = Math.abs(RDLHardware.backLeftWheel.getCurrentPosition());

            telemetry.addData("Turning", "%s %.1f°", direction, degrees);
            telemetry.addData("Targets", "Tick Target: %d", tickTarget);
            telemetry.addData("FR", FR);
            telemetry.addData("FL", FL);
            telemetry.addData("BR", BR);
            telemetry.addData("BL", BL);
            telemetry.update();

            // Break if average encoder position reaches target
            double avg = (FR + FL) / 2.0;
            if (avg - 5 >= tickTarget) break;

            // Optional fail-safe timeout
            if (!RDLHardware.frontRightWheel.isBusy() && !RDLHardware.frontLeftWheel.isBusy() &&
                !RDLHardware.backRightWheel.isBusy() && !RDLHardware.backLeftWheel.isBusy())
                break;
        }

        stopAll();
        setAllModes(DcMotor.RunMode.RUN_USING_ENCODER);
        sleep(300);
    }



    // --- utility methods ---
    private void resetEncoders() {
        // Stop and reset all encoders
        RDLHardware.frontRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RDLHardware.frontLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RDLHardware.backRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RDLHardware.backLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Wait briefly to let the hardware sync
        sleep(100);

        // Set to RUN_USING_ENCODER so encoders start counting again
        RDLHardware.frontRightWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RDLHardware.frontLeftWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RDLHardware.backRightWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RDLHardware.backLeftWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Optional sanity check (shows encoder counts in telemetry)
        telemetry.addData("Encoders Reset",
                "FR:%d FL:%d BR:%d BL:%d",
                RDLHardware.frontRightWheel.getCurrentPosition(),
                RDLHardware.frontLeftWheel.getCurrentPosition(),
                RDLHardware.backRightWheel.getCurrentPosition(),
                RDLHardware.backLeftWheel.getCurrentPosition());
        telemetry.update();
        sleep(1000);
    }


    private void setAllModes(DcMotor.RunMode mode) {
        RDLHardware.frontRightWheel.setMode(mode);
        RDLHardware.frontLeftWheel.setMode(mode);
        RDLHardware.backRightWheel.setMode(mode);
        RDLHardware.backLeftWheel.setMode(mode);
    }

    private void stopAll() {
        RDLHardware.frontRightWheel.setPower(0);
        RDLHardware.frontLeftWheel.setPower(0);
        RDLHardware.backRightWheel.setPower(0);
        RDLHardware.backLeftWheel.setPower(0);
    }
}
