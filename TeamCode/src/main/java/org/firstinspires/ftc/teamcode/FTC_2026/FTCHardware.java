package org.firstinspires.ftc.teamcode.FTC_2026;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class FTCHardware {
    //DC MOTORS
    //Wheels
    public DcMotor frontRightWheel;
    public DcMotor frontLeftWheel;
    public DcMotor backLeftWheel;
    public DcMotor backRightWheel;

    //Arm
    public DcMotor revolverMotor;

    //Intake
    public DcMotor leftLaunchMotor;
    public DcMotor rightLaunchMotor;

    //SERVOS
    //Claw
    public CRServo rightClaw;
    public CRServo leftClaw;

    public Servo poker;
    // Initialize everything
    public void init(HardwareMap hwMap) {
        try {
            // Map all hardware from the configuration
            frontRightWheel = hwMap.get(DcMotor.class, "frontRightWheel");
            frontLeftWheel = hwMap.get(DcMotor.class, "frontLeftWheel");
            backRightWheel = hwMap.get(DcMotor.class, "backRightWheel");
            backLeftWheel = hwMap.get(DcMotor.class, "backLeftWheel");

            revolverMotor = hwMap.get(DcMotor.class, "revolverMotor");

            poker = hwMap.get(Servo.class,"poker");
            leftLaunchMotor = hwMap.get(DcMotor.class, "leftLaunchMotor");
            rightLaunchMotor = hwMap.get(DcMotor.class, "rightLaunchMotor");

            rightClaw = hwMap.get(CRServo.class, "rightClaw");
            leftClaw = hwMap.get(CRServo.class, "leftClaw");
            // Apply configuration
            setWheelDirections();
            setArmDirection();
            setClawDirection();
            setLaunchDirection();
            setZeroPowerBehavior();

        } catch (Exception e) {
            telemetry.addData("Hardware init error", e.getMessage());
            telemetry.update();
        }
    }

    // --- Helper configuration methods ---
    private void setWheelDirections() {
        frontLeftWheel.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightWheel.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftWheel.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightWheel.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    private void setArmDirection() {
        revolverMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    private void setLaunchDirection() {
        leftLaunchMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightLaunchMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    private void setClawDirection(){
        rightClaw.setDirection(DcMotorSimple.Direction.FORWARD);
        leftClaw.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    private void setZeroPowerBehavior() {
        frontLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // Optional: stop all motors
    public void stopAllMotors() {
        frontLeftWheel.setPower(0);
        frontRightWheel.setPower(0);
        backLeftWheel.setPower(0);
        backRightWheel.setPower(0);

    }
}
