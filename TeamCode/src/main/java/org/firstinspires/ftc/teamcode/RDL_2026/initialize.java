package org.firstinspires.ftc.teamcode.RDL_2026;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.teamcode.hardware;

public class initialize {

    public hardware hardware = new hardware(); // reuse your existing hardware class

    // Initialize everything
    public void init(HardwareMap hwMap) {
        try {
            // Map all hardware from the configuration
            hardware.frontRightWheel = hwMap.get(DcMotor.class, "frontRightWheel");
            hardware.frontLeftWheel = hwMap.get(DcMotor.class, "frontLeftWheel");
            hardware.backRightWheel = hwMap.get(DcMotor.class, "backRightWheel");
            hardware.backLeftWheel = hwMap.get(DcMotor.class, "backLeftWheel");

            hardware.frontClaw = hwMap.get(DcMotor.class, "frontClaw");
            hardware.backClaw = hwMap.get(DcMotor.class, "backClaw");
            hardware.armMotor = hwMap.get(DcMotor.class, "armMotor");

            // Apply configuration
            setWheelDirections();
            setArmDirection();
            setZeroPowerBehavior();

        } catch (Exception e) {
            telemetry.addData("Hardware init error", e.getMessage());
            telemetry.update();
        }
    }

    // --- Helper configuration methods ---
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
        hardware.frontLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hardware.frontRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hardware.backLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hardware.backRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        hardware.armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hardware.frontClaw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hardware.backClaw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // Optional: stop all motors
    public void stopAllMotors() {
        hardware.frontLeftWheel.setPower(0);
        hardware.frontRightWheel.setPower(0);
        hardware.backLeftWheel.setPower(0);
        hardware.backRightWheel.setPower(0);
        hardware.armMotor.setPower(0);
        hardware.frontClaw.setPower(0);
        hardware.backClaw.setPower(0);
    }
}
