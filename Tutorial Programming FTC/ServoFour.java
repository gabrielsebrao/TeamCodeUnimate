//Copyright (c) 2017 FIRST. All rights reserved.

package Youtube;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

/** Configuration File
 *  Control Hub:
 *  Port 00: servoOne
 *  Port 01: servoTwo
 *  Port 02: crServoThree
 *  Port 03: crServoFour
 */

@TeleOp(group="Primary")
@Disabled
public class ServoFour extends LinearOpMode
{

    private Servo servoOne;
    private double servoOneInitPosition = 0.0;
    private double servoOnePositionOne = 0.5;
    private double servoOnePositionTwo = 1.0;
    private int servoOneDelay = 10;

    private Servo servoTwo;
    private double servoTwoInitPosition = 0.50;
    private double servoTwoSensitivity = 1.0;

    private CRServo crServoThree;
    private double crServoThreeInitPower = 0.0;
    private double crServoThreePower = 1.0;

    private CRServo crServoFour;
    private double crServoFourInitPower = 0.0;
    private double crServoFourSensitivity = 0.5;
    private double crServoFourBuffer = 0.01;


    @Override
    public void runOpMode() throws InterruptedException{
        initHardware();
        while(!isStarted()) {
            servoOneTelemetry();
        }
        waitForStart();
        while(opModeIsActive()) {
            servoOneTelemetry();
        }

    }

    public void runOpModeControls() {
        if(gamepad1.a) {
            servoOne.setPosition(servoOnePositionOne);
        }
        if(gamepad1.b) {
            servoOne.setPosition(servoOnePositionTwo);
        }
        if(gamepad1.right_bumper) {
            servoOneSlower(servoOnePositionTwo, servoOnePositionOne, servoOneDelay);
        }

        servoTwo.setPosition(servoTwoInitPosition + (gamepad1.right_stick_y * servoTwoSensitivity));

        if(gamepad1.dpad_left) {
            crServoThree.setPower(-crServoThreePower);
        }
        if(gamepad1.dpad_right) {
            crServoThree.setPower(crServoThreePower);
        }
        if(gamepad1.dpad_down) {
            crServoThree.setPower(0);
        }

        crServoFour.setPower(gamepad1.left_stick_y * crServoFourSensitivity);
    }

    public void servoOneSlower(double endPosition, double startPosition, int delay) {
        double range = ((endPosition - startPosition) * 100);
        for(int i = 0; i <= range; i++) {
            servoOne.setPosition(startPosition);
            sleep(delay);
            startPosition += 0.01;
        }
    }

    public void initHardware() {
        initServoOne();
        initServoTwo();
        initCrServoThree();
        initCrServoFour();
    }

    public void initServoOne() {
        servoOne = hardwareMap.get(Servo.class, "servoOne");
        servoOne.setDirection(Servo.Direction.REVERSE);
        servoOne.setPosition(servoOneInitPosition);
    }

    public void initServoTwo() {
        servoTwo = hardwareMap.get(Servo.class, "servoTwo");
        servoTwo.setDirection(Servo.Direction.FORWARD);
        servoTwo.setPosition(servoTwoInitPosition);
    }

    public void initCrServoThree() {
        crServoThree = hardwareMap.get(CRServo.class, "crServoThree");
        crServoThree.setDirection(CRServo.Direction.REVERSE);
        crServoThree.setPower(crServoThreeInitPower);
    }

    public void initCrServoFour() {
        crServoFour = hardwareMap.get(CRServo.class, "crServoFour");
        crServoFour.setDirection(CRServo.Direction.FORWARD);
        crServoFour.setPower(crServoFourInitPower);
    }

    public void servoOneTelemetry() {
        telemetry.addData("Servo One", null);
        telemetry.addData("Position: ", servoOne.getPosition());
        telemetry.addData("Direction: ", servoOne.getDirection());

        telemetry.addData("Servo Two", null);
        telemetry.addData("Position: ", servoTwo.getPosition());
        telemetry.addData("Direction: ", servoTwo.getDirection());

        telemetry.addData("Servo Three", null);
        telemetry.addData("Position: ", servoTwo.getPosition());
        telemetry.addData("Direction: ", servoTwo.getDirection());

        telemetry.addData("Servo Three", null);
        telemetry.addData("Power: ", crServoThree.getPower());
        telemetry.addData("Direction: ", crServoThree.getDirection());

        telemetry.addData("Servo Three", null);
        telemetry.addData("Power: ", crServoFour.getPower());
        telemetry.addData("Direction: ", crServoFour.getDirection());
        telemetry.addData("Left Stick Y: ", gamepad1.left_stick_y);
        telemetry.update();
    }
}
