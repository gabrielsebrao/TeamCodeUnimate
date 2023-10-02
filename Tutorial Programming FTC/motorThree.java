//Copyright (c) 2017 FIRST. All rights reserved.

package Youtube;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/** Configuration File
 *  COntrol Hub:
 *  Motor Port 00: motorOne
 *  Motor Port 01: motorTwo
 *  Motor Port 02: motorThree
 */

@TeleOp(group="Primary")
@Disabled
public class motorThree extends LinearOpMode
{

    // Variables here
    private DcMotor motorOne;
    private double motorOneInitPower = 0.0;
    private double motorOnePower = 1.0;

    private DcMotor motorTwo;
    private double motorTwoInitPower = 0.0;
    private double motorTwoPower = 1.0;
    private double motorTwoSensitivity = 0.75;

    private DcMotor motorThree;
    private double motorThreeInitPower = 0.0;
    private double motorThreePower = 1.0;
    private int motorThreePositionOne = 0;
    private int motorThreePositionTwo = 1000;

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
        initMotorThree();
    }

    private void initMotorThree() {
        motorThree = hardwareMap.get(DcMotor.class, "motorThree");
        motorThree.setDirection(DcMotor.Direction.REVERSE);
        motorThree.setPower(motorThreeInitPower);
        motorThree.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorThree.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        motorThree.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
        motorOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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

        if(gamepad2.left_bumper) {
            runMotorThreeToPosition(motorThreePositionOne);
        }
        if(gamepad2.right_bumper) {
            runMotorThreeToPosition(motorThreePositionTwo);
        }

        if(gamepad2.y) {
            resetEncoders();
        }

    }

    public void runMotorThreeToPosition(int position) {
        motorThree.setTargetPosition(position);
        motorThree.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorThree.setPower(motorThreePower);
        while(motorThree.isBusy()) {
            motorTelemetry();
        }
        motorThree.setPower(motorThreeInitPower); // optional
    }

    public void resetEncoders() {
        motorThree.setPower(motorThreeInitPower);
        motorThree.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorThree.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void motorTelemetry() {
        telemetry.addData("motorOne", "Enconder: %2d, Power: %.2f", motorOne.getCurrentPosition(), motorOne.getPower());
        telemetry.addData("motorTwo", "Enconder: %2d, Power: %.2f", motorOne.getCurrentPosition(), motorOne.getPower());
        telemetry.addData("Note", "Tap Y to reset encoders.");
        telemetry.addData("motorThree", "Enconder: %2d, Power: %.2f", motorOne.getCurrentPosition(), motorOne.getPower());
        telemetry.update();
    }
}
