package org.firstinspires.ftc.teamcode.RDL_2026;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RDLHardware {
    //FTC
    //DCMOTORS
    //Wheels
    public DcMotor frontRightWheel;
    public DcMotor frontLeftWheel;
    public DcMotor backLeftWheel;
    public DcMotor backRightWheel;

    //RDL
    //DCMOTORS
    //Arm
    public DcMotor backClaw;
    public DcMotor frontClaw;
    public DcMotor backestClaw;
    public DcMotor armMotor;

    public Servo armHolder;


    public void init(HardwareMap hwMap) {
        frontRightWheel = hwMap.get(DcMotor.class, "frontRightWheel");
        frontLeftWheel = hwMap.get(DcMotor.class, "frontLeftWheel");
        backLeftWheel = hwMap.get(DcMotor.class, "backLeftWheel");
        backRightWheel = hwMap.get(DcMotor.class, "backRightWheel");

        backClaw = hwMap.get(DcMotor.class, "backClaw");
        backestClaw = hwMap.get(DcMotor.class, "backestClaw");
        frontClaw = hwMap.get(DcMotor.class, "frontClaw");
        armMotor = hwMap.get(DcMotor.class, "armMotor");

        setWheelDirections();
        setArmDirection();
        setZeroPowerBehavior();
    }

    private void setWheelDirections() {
        frontLeftWheel.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightWheel.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    private void setArmDirection() {
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontClaw.setDirection(DcMotorSimple.Direction.FORWARD);
        backClaw.setDirection(DcMotorSimple.Direction.FORWARD);
        backestClaw.setDirection(DcMotorSimple.Direction.FORWARD);
        armMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    private void setZeroPowerBehavior() {
        frontLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontClaw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backClaw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backestClaw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // Optional: stop all motors
    public void stopAllMotors() {
        frontLeftWheel.setPower(0);
        frontRightWheel.setPower(0);
        backLeftWheel.setPower(0);
        backRightWheel.setPower(0);
        armMotor.setPower(0);
        frontClaw.setPower(0);
        backClaw.setPower(0);
        backestClaw.setPower(0);
    }
}
