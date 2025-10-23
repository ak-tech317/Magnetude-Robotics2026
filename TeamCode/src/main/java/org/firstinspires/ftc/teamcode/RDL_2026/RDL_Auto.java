package org.firstinspires.ftc.teamcode.RDL_2026;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware;

@Autonomous(name="RDL Autonomous", group="RDL")
public class RDL_Auto extends LinearOpMode {

    hardware hardware = new hardware();
    initialize initialize = new initialize();

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize robot hardware
        initialize.init(hardwareMap);

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
}
