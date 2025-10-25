package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class hardware {
    //FTC
    //DCMOTORS
    //Wheels
    public DcMotor frontRightWheel;
    public DcMotor frontLeftWheel;
    public DcMotor backLeftWheel;
    public DcMotor backRightWheel;

    //SERVOS
    //Claw
//    public CRServo frontRightClaw;
//    public CRServo frontLeftClaw;
//    public CRServo backRightClaw;
//    public CRServo backLeftClaw;
    //SENSORS

    //RDL
    //DCMOTORS
    //Arm
    public DcMotor backClaw;
    public DcMotor frontClaw;
    public DcMotor armMotor;


    public void init(HardwareMap hwMap) {
        frontRightWheel = hwMap.get(DcMotor.class, "frontRightWheel");
        frontLeftWheel = hwMap.get(DcMotor.class, "frontLeftWheel");
        backLeftWheel = hwMap.get(DcMotor.class, "backLeftWheel");
        backRightWheel = hwMap.get(DcMotor.class, "backRightWheel");

//        frontRightClaw = hwMap.get(CRServo.class, "frontRightClaw");
//        frontLeftClaw = hwMap.get(CRServo.class, "frontLeftClaw");
//        backRightClaw = hwMap.get(CRServo.class, "backRightClaw");
//        backLeftClaw = hwMap.get(CRServo.class, "backLeftClaw");

        backClaw = hwMap.get(DcMotor.class, "backClaw");
        frontClaw = hwMap.get(DcMotor.class, "frontClaw");
        armMotor = hwMap.get(DcMotor.class, "armMotor");
    }

    public void setDirection() {
        frontRightWheel.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeftWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftWheel.setDirection(DcMotorSimple.Direction.FORWARD);
    }
}
