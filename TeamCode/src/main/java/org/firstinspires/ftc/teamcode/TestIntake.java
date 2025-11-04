package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class TestIntake extends OpMode {
    private DcMotor intakeLeft;
    private DcMotor intakeRight;

    private double power = 0.5;

    @Override
    public void init() {
        intakeLeft = hardwareMap.get(DcMotor.class, "intakeLeft");
        intakeRight = hardwareMap.get(DcMotor.class, "intakeRight");

        intakeLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        if(gamepad1.b) {
            intakeLeft.setPower(0.65);
            intakeRight.setPower(0.65);
        }
    }
}
