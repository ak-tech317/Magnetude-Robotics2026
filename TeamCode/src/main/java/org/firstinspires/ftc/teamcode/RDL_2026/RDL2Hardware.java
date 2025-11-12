package org.firstinspires.ftc.teamcode.RDL_2026;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RDL2Hardware {
    //FTC
    //DCMOTORS
    //s
    public DcMotor frontRight;
    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor backRight;

    //RDL
    //DCMOTORS
    //Arm
    public Servo backClaw;
    public Servo frontClaw;
    public DcMotor armMotor;


    public void init(HardwareMap hwMap) {
        frontRight = hwMap.get(DcMotor.class, "frontRight");
        frontLeft = hwMap.get(DcMotor.class, "frontLeft");
        backLeft = hwMap.get(DcMotor.class, "backLeft");
        backRight = hwMap.get(DcMotor.class, "backRight");

        backClaw = hwMap.get(Servo.class, "backClaw");
        frontClaw = hwMap.get(Servo.class, "frontClaw");
        armMotor = hwMap.get(DcMotor.class, "armMotor");

        setWheelDirections();
        setArmDirection();
        setZeroPowerBehavior();
    }

    private void setWheelDirections() {
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    private void setArmDirection() {
        frontClaw.setDirection(Servo.Direction.FORWARD);
        backClaw.setDirection(Servo.Direction.FORWARD);
        armMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    private void setZeroPowerBehavior() {
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // Optional: stop all motors
    public void stopAllMotors() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        armMotor.setPower(0);
    }
}