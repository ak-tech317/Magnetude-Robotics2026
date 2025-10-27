package org.firstinspires.ftc.teamcode.FTC_2026;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="FTC Autonomous", group="FTC")
public class FTC_Auto extends LinearOpMode {

    FTCHardware FTCHardware = new FTCHardware();

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize robot hardware
        FTCHardware.init(hardwareMap);

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
