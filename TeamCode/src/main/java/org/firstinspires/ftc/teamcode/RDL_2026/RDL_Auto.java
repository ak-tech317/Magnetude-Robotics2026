package org.firstinspires.ftc.teamcode.RDL_2026;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware;

@Autonomous(name="RDL Autonomous", group="RDL")
public class RDL_Auto extends LinearOpMode {

    hardware hardware = new hardware();

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize robot hardware
        initWheel();
        initArm();
        setWheelDirections();
        setArmDirection();
        setZeroPowerBehavior();

        // Telemetry to show that robot initialized correctly
        telemetry.addLine("Robot is initialized and ready");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        if (opModeIsActive()) {
            // Add your autonomous movement or sequence logic here later
            telemetry.addLine("Autonomous running...");
            telemetry.update();

            // Example placeholder wait (replace later)
            sleep(1000);

            telemetry.addLine("Autonomous complete.");
            telemetry.update();
        }
    }

    // --- Initialization Methods ---
    private void initWheel() {
        try {
            hardware.frontRightWheel = hardwareMap.get(DcMotor.class, "frontRightWheel");
            hardware.frontLeftWheel = hardwareMap.get(DcMotor.class, "frontLEftWheel");
            hardware.backRightWheel = hardwareMap.get(DcMotor.class, "backRightWheel");
            hardware.backLeftWheel = hardwareMap.get(DcMotor.class, "backLeftWheel");
        } catch (Exception e) {
            telemetry.addData("Error initializing wheels: ", e.getMessage());
            telemetry.update();
        }
    }

    private void initArm() {
        try {
            hardware.frontClaw = hardwareMap.get(DcMotor.class, "frontClaw");
            hardware.backClaw = hardwareMap.get(DcMotor.class, "backClaw");
            hardware.armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        } catch (Exception e) {
            telemetry.addData("Error initializing arm: ", e.getMessage());
            telemetry.update();
        }
    }

    private void setWheelDirections() {
        hardware.frontLeftWheel.setDirection(DcMotorSimple.Direction.FORWARD);
        hardware.frontRightWheel.setDirection(DcMotorSimple.Direction.FORWARD);
        hardware.backLeftWheel.setDirection(DcMotorSimple.Direction.FORWARD);
        hardware.backRightWheel.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    private void setArmDirection() {
        hardware.frontClaw.setDirection(DcMotorSimple.Direction.FORWARD);
        hardware.backClaw.setDirection(DcMotorSimple.Direction.FORWARD);
        hardware.armMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    private void setZeroPowerBehavior() {
        // Make all wheels brake when power is zero
        hardware.frontLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hardware.frontRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hardware.backLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hardware.backRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Optionally, brake the arm and claw as well
        hardware.armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hardware.frontClaw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hardware.backClaw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
