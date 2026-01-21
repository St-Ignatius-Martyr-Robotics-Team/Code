package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Robocats Auto Blue", group="Robocats")
public class RobocatsAutoBlue extends LinearOpMode {

    // ============================================================
    //                >>> ADJUST THESE VALUES <<<
    // ============================================================

    private final double DRIVE_POWER = 0.5;
    private final double TURN_POWER = 0.45;

    private final long DRIVE_TO_FIRST_POINT = 900;
    private final long TURN_LEFT_TIME = 1500;  // base left turn time
        private final long DRIVE_AFTER_TURN = 1500;

    private final double SHOOTER_POWER = 1;
    private final long SHOOTER_SPINUP_TIME = 1500;

    private final double FEEDER_STOP = 0.5;
    private final double FEEDER_FORWARD = 1.0;
    private final long FEED_TIME = 550;
    private final long FEED_REST = 500;
    private final int ARTIFACT_COUNT = 3;

    private final long RETURN_BACKWARD_EXTRA = 200;

    // ============================================================
    // Hardware
    // ============================================================

    private DcMotor driveLeft, driveRight;
    private DcMotor shooterLeft, shooterRight;
    private DcMotor intake;
    private Servo feeder;

    @Override
    public void runOpMode() {

        // Map hardware
        driveLeft = hardwareMap.get(DcMotor.class, "driveLeft");
        driveRight = hardwareMap.get(DcMotor.class, "driveRight");
        shooterLeft = hardwareMap.get(DcMotor.class, "shooterLeft");
        shooterRight = hardwareMap.get(DcMotor.class, "shooterRight");
        intake = hardwareMap.get(DcMotor.class, "intake");
        feeder = hardwareMap.get(Servo.class, "feeder");

        driveLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveRight.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterRight.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        feeder.setPosition(FEEDER_STOP);

        telemetry.addLine("Robocats Auto Blue READY");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        // ============================================================
        //                       AUTONOMOUS
        // ============================================================

        telemetryStep("Driving to first point", DRIVE_POWER);
        driveForward(DRIVE_POWER, DRIVE_TO_FIRST_POINT);

        telemetryStep("Turning LEFT", TURN_POWER);
        turnLeft((long)(TURN_LEFT_TIME * 0.95)); // slightly shorter, less dramatic

        telemetryStep("Driving to shooting position", DRIVE_POWER);
        driveForward(DRIVE_POWER, DRIVE_AFTER_TURN);

        telemetry.addData("Step", "Spinning up shooters");
        telemetry.update();
        shooterLeft.setPower(SHOOTER_POWER);
        shooterRight.setPower(SHOOTER_POWER);
        sleep(SHOOTER_SPINUP_TIME);

        // Shoot artifacts
        for (int i = 0; i < ARTIFACT_COUNT; i++) {
            telemetry.addData("Step", "Feeding artifact " + (i + 1));
            telemetry.update();
            feeder.setPosition(FEEDER_FORWARD);
            sleep(FEED_TIME);
            feeder.setPosition(FEEDER_STOP);
            sleep(FEED_REST);
        }

        shooterLeft.setPower(0);
        shooterRight.setPower(0);

        telemetry.addData("Step", "Shooting Complete — Returning to Start");
        telemetry.update();

        // ============================================================
        //               RETURN TO ORIGINAL START POSITION
        // ============================================================

        telemetryStep("Returning: Driving Backward (1)", DRIVE_POWER);
        driveBackward(DRIVE_POWER, DRIVE_AFTER_TURN + RETURN_BACKWARD_EXTRA);

        telemetryStep("Returning: Turning RIGHT", TURN_POWER);
        turnRightReturn((long)(TURN_LEFT_TIME * 1.05)); // keep previous dramatic right

        telemetryStep("Returning: Driving Backward (2)", DRIVE_POWER);
        driveBackward(DRIVE_POWER, DRIVE_TO_FIRST_POINT);

        telemetry.addLine("AUTO COMPLETE — Returned to Start");
        telemetry.update();
    }

    // ============================================================
    // Helper Functions
    // ============================================================

    private void telemetryStep(String stepName, double power) {
        telemetry.addData("Step", stepName);
        telemetry.addData("Power", power);
        telemetry.update();
    }

    private void driveForward(double power, long timeMS) {
        driveLeft.setPower(power);
        driveRight.setPower(power);
        sleep(timeMS);
        driveLeft.setPower(0);
        driveRight.setPower(0);
    }

    private void driveBackward(double power, long timeMS) {
        driveLeft.setPower(-power);
        driveRight.setPower(-power);
        sleep(timeMS);
        driveLeft.setPower(0);
        driveRight.setPower(0);
    }

    private void turnLeft(long timeMS) {
        long boostTime = (long)(timeMS * 0.40);
        long coastTime = timeMS - boostTime;

        driveLeft.setPower(-1.0);
        driveRight.setPower(1.0);
        sleep(boostTime);

        driveLeft.setPower(-TURN_POWER);
        driveRight.setPower(TURN_POWER);
        sleep(coastTime);

        driveLeft.setPower(0);
        driveRight.setPower(0);
    }

    private void turnRightReturn(long timeMS) {
        long boostTime = (long)(timeMS * 0.35);
        long coastTime = timeMS - boostTime;

        driveLeft.setPower(1.0);
        driveRight.setPower(-1.0);
        sleep(boostTime);

        driveLeft.setPower(TURN_POWER);
        driveRight.setPower(-TURN_POWER);
        sleep(coastTime);

        driveLeft.setPower(0);
        driveRight.setPower(0);
    }
}
