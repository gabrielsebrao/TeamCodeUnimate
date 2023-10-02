//Copyright (c) 2017 FIRST. All rights reserved.

package Youtube;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/** Configuration File
 *  COntrol Hub:
 *  Motor Port 00: motorOne
 *  Motor Port 01: motorTwo
 */

@TeleOp(group="Primary")
@Disabled
public class motorTwo extends LinearOpMode
{

    // Variables here
    private DcMotor motorOne;
    private double motorOneInitPower = 0.0;
    private double motorOnePower = 1.0;

    private DcMotor motorTwo;
    private double motorTwoInitPower = 0.0;
    private double motorTwoPower = 1.0;
    private double motorTwoSensitivity = 0.75;

    @Override
    public void runOpMode() throws InterruptedException{
        initHardware();
        while(!isStarted()) {
            motorTelemetry();
        }
        waitForStart();
        while(opModeIsActive()) {
            teleOpControls();
            motorTelemetry();
        }
    }

    public void initHardware() {
        initMotorOne();
        initMotorTwo();
    }

    private void initMotorTwo() {
        motorTwo = hardwareMap.get(DcMotor.class, "motorTwo");
        motorTwo.setDirection(DcMotor.Direction.REVERSE);
        motorTwo.setPower(motorTwoInitPower);
        motorTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        motorTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void initMotorOne() {
        motorOne = hardwareMap.get(DcMotor.class, "motorOne");
        motorOne.setDirection(DcMotor.Direction.REVERSE);
        motorOne.setPower(motorOneInitPower);
        motorOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        motorOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void teleOpControls() {
        if(gamepad2.x) {
            motorOne.setPower(-motorOnePower);
        }
        if(gamepad2.a) {
            motorOne.setPower(motorOneInitPower);
        }
        if(gamepad2.b) {
            motorOne.setPower(motorOnePower);
        }

        motorTwo.setPower(gamepad1.right_stick_y);

    }

    public void motorTelemetry() {
        telemetry.addData("motorOne", "Enconder: %.2d, Power: %.2d", motorOne.getCurrentPosition(), motorOne.getPower());
        telemetry.addData("motorTwo", "Enconder: %.2d, Power: %.2d", motorOne.getCurrentPosition(), motorOne.getPower());
        telemetry.update();
    }
}
