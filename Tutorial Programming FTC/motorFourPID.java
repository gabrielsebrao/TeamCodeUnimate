//Copyright (c) 2017 FIRST. All rights reserved.

package Youtube;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

/** Configuration File
 *  COntrol Hub:
 *  Motor Port 00: motorOne
 *  Motor Port 01: motorTwo
 *  Motor Port 02: motorThree
 *  Motor Port 03: motorFour
 */

@TeleOp(group="Primary")
@Disabled
public class motorFourPID extends LinearOpMode
{

    // Global variables here
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

    private DcMotorEx motorFour;
    private double motorFourInitPower = 0;
    private double motorFourMaxPower = 1.0;
    private double motorFourCurrentVelocity = 0.0;
    private double motorFourMaxVelocity = 0.0;

    // PID part - test maxVelocity to find target value
    // Target Value should be no more than 80% of max velocity

    private double motorFourTargetVelocity = 800.0;
    private double motorFourResultMaxVelocityTest = 2800.0; // Enter max velocity from test
    private double F = 32767.0 / motorFourResultMaxVelocityTest;
    private double kP = F * 0.1; // Discuss P term oscillation
    private double kI = kP * 0.1; // I term nudges you to your target over time
    private double kD = kP * 0.01; // D applies a breaking force to control over shoot. Keep this term small to avoid noise
    private double position = 5.0;

    @Override
    public void runOpMode() throws InterruptedException{

        initHardware();

        while(!isStarted()) {
            motorTelemetry();
        }

        waitForStart();

        while(opModeIsActive()) {

            runTeleOpControls();
            getMotorFourMaxVelocityTest();
            runMotorFour(motorFourTargetVelocity);
            motorTelemetry();
        }
    }

    public void initHardware() {
        initMotorOne();
        initMotorTwo();
        initMotorThree();
        initMotorFour();
    }

    private void initMotorFour() {
        motorFour = hardwareMap.get(DcMotorEx.class, "motorFour");
        motorFour.setDirection(DcMotorEx.Direction.FORWARD);
        motorFour.setPower(motorFourInitPower);
        motorFour.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        motorFour.setVelocityPIDFCoefficients(kP,kI,kD,F);
        motorFour.setPositionPIDFCoefficients(position);
        motorFour.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        motorFour.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
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

    public void runTeleOpControls() {

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

    public void getMotorFourMaxVelocityTest() {
        motorFour.setPower(motorFourMaxPower);
        motorFourCurrentVelocity = motorFour.getVelocity(); // ticks per second
        if(motorFourCurrentVelocity > motorFourMaxVelocity) {
            motorFourMaxVelocity = motorFourCurrentVelocity;
        }
    }

    public void runMotorFour(double velocity) {
        motorFour.setVelocity(velocity);
        motorFourCurrentVelocity = motorFour.getVelocity();
        if(motorFourCurrentVelocity > motorFourMaxVelocity) {
            motorFourMaxVelocity = motorFourCurrentVelocity;
        }
    }

    public void motorTelemetry() {
        telemetry.addData("motorOne", "Enconder: %2d, Power: %.2f", motorOne.getCurrentPosition(), motorOne.getPower());

        telemetry.addData("motorTwo", "Enconder: %2d, Power: %.2f", motorOne.getCurrentPosition(), motorOne.getPower());

        telemetry.addData("Note", "Tap Y to reset encoders.");
        telemetry.addData("motorThree", "Enconder: %2d, Power: %.2f", motorOne.getCurrentPosition(), motorOne.getPower());

        telemetry.addData("Current Power", motorFour.getPower());
        telemetry.addData("Maximum Velocity", motorFourMaxVelocity);
        telemetry.addData("Current Velocity", motorFourCurrentVelocity);
        telemetry.update();
    }
}
