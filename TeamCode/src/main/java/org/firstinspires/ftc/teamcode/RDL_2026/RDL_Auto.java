package org.firstinspires.ftc.teamcode.RDL_2026;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="RDL Autonomous", group="RDL")
public class RDL_Auto extends LinearOpMode {
    private final ElapsedTime gameRuntime = new ElapsedTime();
    private final ElapsedTime armRuntime = new ElapsedTime();
    private final ElapsedTime clawRuntime = new ElapsedTime();
    RDLHardware RDLHardware = new RDLHardware();

    double driveSpeed = 0.5;
    double turnSpeed = 0.2;
    double ticksPerFoot = 140;
    double ticksPer360 = 1095;
    double ticksPerDegree = ticksPer360 / 360;

    double[] intakePowerLevel = {1, -1, 1};
    double[] outtakePowerLevel = {-1, 1, -1};
    double armUpTime = 0.8;
    double armDownTime = 1.5;
    double armHold = 0.4;

    @Override
    public void runOpMode() throws InterruptedException {
        RDLHardware.init(hardwareMap);
        while(opModeInInit()){
            armRuntime.reset();
            clawRuntime.reset();
            gameRuntime.reset();
            telemetry.addLine("Initialized and ready");
            telemetry.update();
        }
        waitForStart();
        while (opModeIsActive() && gameRuntime.seconds() < 30) {
            telemetry.addLine("Autonomous started...");
            telemetry.update();
            sleep(500);

            turn(360, turnSpeed, "left");
            sleep(500);
            turn(360, turnSpeed, "right");
            //Ball collection command
//            extend();
//            goToWheel();
//            ballCollection();
//            goBackToStart();

            //Autonomous complete
            sleep(500);
            telemetry.addLine("Autonomous complete.");
            telemetry.update();
        }
    }
    private void extend(){
        RDLHardware.armHolder.setPosition(0);
        claw("outtake", 3);
        arm("up", 1);
        RDLHardware.armMotor.setPower(armHold);
        sleep(1000);
    }
    private void goToWheel(){
        //Turn right 90 degrees
        //Move forward 2 feet
        //Turn left 90 degrees
        //Move forward 18 feet
        //Turn left 90 degrees
        //Move forward two feet
        turn(90, turnSpeed, "right");
        movement(2, driveSpeed, "forward");
        turn(90, turnSpeed, "left");
        movement(18, driveSpeed, "forward");
        turn(90, turnSpeed, "left");
        movement(2, driveSpeed, "forward");
    }
    private void ballCollection(){
        arm("up", armUpTime);
        RDLHardware.armMotor.setPower(armHold);
        claw("intake", 1.0);
        movement(1, driveSpeed, "forward");
        for(int i = 0; i < 3; i++) {
            arm("down", armDownTime);
            movement(0.5, driveSpeed, "backward");
            arm("up", armUpTime);
            movement(0.5, driveSpeed, "forward");

        }
    }
    private void goBackToStart(){
        //Go backwards two feet
        //Turn left 90 degrees
        //Go forward 18 feet
        //Turn right 90 degrees
        //Go forward two feet
        //Spit out the balls
        movement(2, driveSpeed, "backward");
        turn(90, turnSpeed, "left");
        movement(18, driveSpeed, "forward");
        turn(90, turnSpeed, "right");
        movement(2, driveSpeed, "forward");
        claw("outtake", 30 - gameRuntime.seconds());
    }
    private void movement(double distance, double speed, String direction) {
        int tickTarget = (int) (distance * ticksPerFoot);
        resetEncoders();

        if (direction.equalsIgnoreCase("forward")) {
            RDLHardware.frontRightWheel.setTargetPosition(tickTarget);
            RDLHardware.frontLeftWheel.setTargetPosition(tickTarget);
            RDLHardware.backRightWheel.setTargetPosition(tickTarget);
            RDLHardware.backLeftWheel.setTargetPosition(tickTarget);
        } else if (direction.equalsIgnoreCase("backward")) {
            RDLHardware.frontRightWheel.setTargetPosition(-tickTarget);
            RDLHardware.frontLeftWheel.setTargetPosition(-tickTarget);
            RDLHardware.backRightWheel.setTargetPosition(-tickTarget);
            RDLHardware.backLeftWheel.setTargetPosition(-tickTarget);
        } else {
            telemetry.addLine("Invalid direction (use 'forward' or 'backward')");
            telemetry.update();
            return;
        }

        setAllModes(DcMotor.RunMode.RUN_TO_POSITION);
        RDLHardware.frontRightWheel.setPower(1.5 * speed);
        RDLHardware.frontLeftWheel.setPower(1.5 * speed);
        RDLHardware.backRightWheel.setPower(speed);
        RDLHardware.backLeftWheel.setPower(speed);

        while (true) {
            int FR = Math.abs(RDLHardware.frontRightWheel.getCurrentPosition());
            int FL = Math.abs(RDLHardware.frontLeftWheel.getCurrentPosition());
            int BR = Math.abs(RDLHardware.backRightWheel.getCurrentPosition());
            int BL = Math.abs(RDLHardware.backLeftWheel.getCurrentPosition());

            telemetry.addData("Moving", "%s %.2f ft", direction, distance);
            telemetry.addData("Targets", "Tick Target: %d", tickTarget);
            telemetry.addData("FR", FR);
            telemetry.addData("FL", FL);
            telemetry.addData("BR", BR);
            telemetry.addData("BL", BL);
            telemetry.update();

            double avg = (FR + FL) / 2.0;
            if (avg >= tickTarget) break;

            if (!RDLHardware.frontRightWheel.isBusy() && !RDLHardware.frontLeftWheel.isBusy() &&
                    !RDLHardware.backRightWheel.isBusy() && !RDLHardware.backLeftWheel.isBusy()) {
                break;
            }
        }

        stopAll();
        setAllModes(DcMotor.RunMode.RUN_USING_ENCODER);
        sleep(300);
    }

    private void turn(double degrees, double speed, String direction) {
        int tickTarget = (int)(degrees * ticksPerDegree);
        resetEncoders();

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

        setAllModes(DcMotor.RunMode.RUN_TO_POSITION);
        RDLHardware.frontRightWheel.setPower(1.5 * speed);
        RDLHardware.frontLeftWheel.setPower(1.5 * speed);
        RDLHardware.backRightWheel.setPower(speed);
        RDLHardware.backLeftWheel.setPower(speed);

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

            double avg = (FR + FL) / 2.0;
            if (avg - 5 >= tickTarget) break;

            if (!RDLHardware.frontRightWheel.isBusy() && !RDLHardware.frontLeftWheel.isBusy() &&
                    !RDLHardware.backRightWheel.isBusy() && !RDLHardware.backLeftWheel.isBusy())
                break;
        }

        stopAll();
        setAllModes(DcMotor.RunMode.RUN_USING_ENCODER);
        sleep(300);
    }

    private void resetEncoders() {
        RDLHardware.frontRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RDLHardware.frontLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RDLHardware.backRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RDLHardware.backLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(100);
        RDLHardware.frontRightWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RDLHardware.frontLeftWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RDLHardware.backRightWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RDLHardware.backLeftWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Encoders Reset",
                "FR:%d FL:%d BR:%d BL:%d",
                RDLHardware.frontRightWheel.getCurrentPosition(),
                RDLHardware.frontLeftWheel.getCurrentPosition(),
                RDLHardware.backRightWheel.getCurrentPosition(),
                RDLHardware.backLeftWheel.getCurrentPosition());
        telemetry.update();
    }

    private void claw(String direction,double time) {
        clawRuntime.reset();
        DcMotor[] claws = {RDLHardware.backClaw, RDLHardware.frontClaw, RDLHardware.backestClaw};
        double[] powers = new double[0];

           if (direction.equalsIgnoreCase("intake")) {
               powers = intakePowerLevel;
           } else if (direction.equalsIgnoreCase("outtake")) {
               powers = outtakePowerLevel;
           }

           for (int i = 0; i < claws.length; i++) {
               claws[i].setPower(powers[i]);
           }
           clawRuntime.reset();
    }

    private void arm(String direction, double time){
        armRuntime.reset();
        while (armRuntime.seconds() < time) {
            if (direction.equalsIgnoreCase("up")) {
                RDLHardware.armMotor.setPower(1);
            } else if (direction.equalsIgnoreCase("down")) {
                RDLHardware.armMotor.setPower(0);
            }
        }
        armRuntime.reset();
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
