package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class TestShooter extends OpMode {
    private DcMotor shooterLeft;
    private DcMotor shooterRight;

    private double power = 0.5;

    @Override
    public void init() {
        shooterLeft = hardwareMap.get(DcMotor.class, "shooterLeft");
        shooterRight = hardwareMap.get(DcMotor.class, "shooterRight");

        shooterLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterRight.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void loop() {

        if(gamepad1.a) {
            shooterLeft.setPower(0.65);
            shooterRight.setPower(0.65);
        }
    }
}
