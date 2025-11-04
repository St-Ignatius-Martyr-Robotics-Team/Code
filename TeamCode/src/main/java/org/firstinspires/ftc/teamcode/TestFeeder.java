package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp
public class TestFeeder extends OpMode {
    private CRServo feeder;

    private boolean feederOn = false;
    private double feederPower = 1.0; // Forward speed (can be negative to reverse direction)

    @Override
    public void init() {
        feeder = hardwareMap.get(CRServo.class, "feeder");
        feeder.setDirection(CRServo.Direction.FORWARD);
    }

    @Override
    public void loop() {
        // Toggle feeder on/off with Y
        if (gamepad1.y && !feederOn) {
            feeder.setPower(feederPower);
            feederOn = true;
        } else if (gamepad1.y && feederOn) {
            feeder.setPower(0.0);
            feederOn = false;
        }

        // Reverse direction with A (only if it's running)
        if (gamepad1.a && feederOn) {
            feederPower = -feederPower;
            feeder.setPower(feederPower);
        }

        telemetry.addData("Feeder Power", feederPower);
        telemetry.addData("Feeder Running", feederOn);
        telemetry.update();
    }
}
