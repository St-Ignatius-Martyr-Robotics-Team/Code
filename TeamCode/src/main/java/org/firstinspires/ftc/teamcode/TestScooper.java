package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class TestScooper extends OpMode {
    private Servo scooper;

    // These values correspond to servo positions (0.0–1.0)
    // Adjust based on your servo's actual movement range.
    private final double SCOOPER_DOWN = 0.0;  // starting position (0°)
    private final double SCOOPER_UP = 0.5;    // about 90°, may need tuning

    private boolean isUp = false;

    @Override
    public void init() {
        scooper = hardwareMap.get(Servo.class, "scooper");
        scooper.setDirection(Servo.Direction.REVERSE);
        scooper.scaleRange(0.0, 1.0);  // full range just in case
        scooper.setPosition(SCOOPER_DOWN); // start flat
    }

    @Override
    public void loop() {
        // Toggle scooper between two positions with the Y button
        if (gamepad1.y && !isUp) {
            scooper.setPosition(SCOOPER_UP);  // move up ~90°
            isUp = true;
        } else if (gamepad1.a && isUp) {
            scooper.setPosition(SCOOPER_DOWN);  // move back down
            isUp = false;
        }

        telemetry.addData("Scooper Position", scooper.getPosition());
        telemetry.update();
    }
}
