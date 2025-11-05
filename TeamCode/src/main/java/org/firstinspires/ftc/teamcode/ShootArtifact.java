package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ShootArtifact extends OpMode {
    private CRServo feeder;
    private DcMotor shooterLeft;
    private DcMotor shooterRight;
    private Servo scooper;
    private DcMotor intakeLeft;
    private DcMotor intakeRight;
    private double shooterPower = 0.65;
    private double feederPower = 1.0;
    private final double INTAKE_POWER = 0.5;
    private boolean feederOn = false;
    private boolean shootersOn = false;
    private boolean intakesOn = false;
    private final double SCOOPER_DOWN = 0.0;
    private final double SCOOPER_UP = 0.5;
    private boolean isUp = false;

    @Override
    public void init() {
        feeder = hardwareMap.get(CRServo.class, "feeder");
        shooterLeft = hardwareMap.get(DcMotor.class, "shooterLeft");
        shooterRight = hardwareMap.get(DcMotor.class, "shooterRight");
        scooper = hardwareMap.get(Servo.class, "scooper");
        intakeLeft = hardwareMap.get(DcMotor.class, "intakeLeft");
        intakeRight = hardwareMap.get(DcMotor.class, "intakeRight");

        shooterLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterRight.setDirection(DcMotorSimple.Direction.FORWARD);
        feeder.setDirection(CRServo.Direction.REVERSE);
        scooper.setDirection(Servo.Direction.FORWARD);
        intakeLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeRight.setDirection(DcMotorSimple.Direction.REVERSE);

        scooper.scaleRange(0.0, 1.0);
        scooper.setPosition(SCOOPER_DOWN);
    }

    @Override
    public void loop(){

        if (gamepad2.y && !feederOn) {
            feeder.setPower(feederPower);
            feederOn = true;
        } else if (gamepad2.y && feederOn) {
            feeder.setPower(0.0);
            feederOn = false;
        }

        if (gamepad2.a && !shootersOn) {
            shooterLeft.setPower(shooterPower);
            shooterRight.setPower(shooterPower);
            shootersOn = true;
        } else if (gamepad2.a && shootersOn) {
            shooterLeft.setPower(0.0);
            shooterRight.setPower(0.0);
            shootersOn = false;
        }

        if (gamepad2.b && !isUp) {
            scooper.setPosition(SCOOPER_UP);  // move up ~90°
            isUp = true;
        } else if (gamepad2.b && isUp) {
            scooper.setPosition(SCOOPER_DOWN);  // move back down
            isUp = false;
        }

        if(gamepad2.x && !intakesOn) {
            intakeLeft.setPower(INTAKE_POWER);
            intakeRight.setPower(INTAKE_POWER);
            intakesOn = true;
        }else if (gamepad2.x && intakesOn) {
            intakeLeft.setPower(0.0);
            intakeRight.setPower(0.0);
            intakesOn = false;
        }
    }
}
