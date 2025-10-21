package org.firstinspires.ftc.teamcode.RDL_2026;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware;

@TeleOp(name="RDL Teleop", group = "RDL")
public class RDL_Teleop extends LinearOpMode{
    hardware hardware = new hardware();
    private final double threshold = 0.2;
    @Override
    public void runOpMode() throws InterruptedException {
        initWheel();
        initArm();
        setWheelDirections();
        setArmDirection();

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





    //INITIALIZATION
    public void initWheel(){
        try {
            hardware.frontRightWheel = hardwareMap.get(DcMotor.class, "frontRightWheel");
            hardware.frontLeftWheel = hardwareMap.get(DcMotor.class, "frontLEftWheel");
            hardware.backRightWheel = hardwareMap.get(DcMotor.class, "backRightWheel");
            hardware.backLeftWheel = hardwareMap.get(DcMotor.class, "backLeftWheel");
        }catch(Exception e){
            telemetry.addData("Error occurred initializing the wheel:", e);
            telemetry.update();
        }
    }
    public void setWheelDirections(){
        hardware.frontLeftWheel.setDirection(DcMotorSimple.Direction.FORWARD);
        hardware.frontRightWheel.setDirection(DcMotorSimple.Direction.FORWARD);
        hardware.backLeftWheel.setDirection(DcMotorSimple.Direction.FORWARD);
        hardware.backRightWheel.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    public void initArm(){
        try{
            hardware.frontClaw = hardwareMap.get(DcMotor.class, "frontClaw");
            hardware.backClaw = hardwareMap.get(DcMotor.class, "backClaw");
            hardware.armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        } catch(Exception e){
            telemetry.addData("Error occurred initializing the arm:", e);
            telemetry.update();
        }
    }
    public void setArmDirection(){
        hardware.frontClaw.setDirection(DcMotorSimple.Direction.FORWARD);
        hardware.backClaw.setDirection(DcMotorSimple.Direction.FORWARD);
        hardware.armMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    private void driveCommand(double vertical, double horizontal, double turn){
        hardware.frontRightWheel.setPower(vertical+horizontal+turn);
        hardware.frontLeftWheel.setPower(vertical+horizontal+turn);
        hardware.backRightWheel.setPower(vertical+horizontal+turn);
        hardware.backLeftWheel.setPower(vertical+horizontal+turn);
    }





    //CONTROLS
    private void driveControls(){
        double verticalControls = Math.abs(gamepad1.right_stick_y) > threshold ? gamepad1.right_stick_y : 0;
        double horizontalControls = Math.abs(gamepad1.right_stick_x) > threshold ? gamepad1.right_stick_y: 0;
        double turnControls = Math.abs(gamepad1.left_stick_x) > threshold ? gamepad1.left_stick_x: 0;
        driveCommand(verticalControls, horizontalControls, turnControls);
    }

    private void armControl(){
        double armStop = 0.1;
        double armPower = Math.abs(gamepad1.left_stick_y) > threshold ? gamepad1.left_stick_y : armStop;
        hardware.armMotor.setPower(armPower);
    }
    private void clawControl(){
        //First number is back motor, second is front
        int[] intakePowerLevel = {-1, 1};
        int[] outtakePowerLevel = {1, -1};

        if(gamepad1.dpad_up){
            hardware.backClaw.setPower(intakePowerLevel[0]);
            hardware.frontClaw.setPower(intakePowerLevel[1]);
        }else if(gamepad1.dpad_down){
            hardware.backClaw.setPower(outtakePowerLevel[0]);
            hardware.frontClaw.setPower(outtakePowerLevel[1]);
        }else{
            hardware.backClaw.setPower(0);
            hardware.frontClaw.setPower(0);
        }

    }
}