//Copyright (c) 2017 FIRST. All rights reserved.

package Youtube;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/** Configuration File
 *  COntrol Hub:
 */

@TeleOp(group="Primary")
@Disabled
public class ServoOne extends LinearOpMode
{

    private Servo servoOne;
    private double servoOneInitPosition = 0.0;
    private double servoOnePositionOne = 0.5;
    private double servoOnePositionTwo = 1.0;
    private int servoOneDelay = 10;

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
    }

    public void initServoOne() {
        servoOne = hardwareMap.get(Servo.class, "servoOne");
        servoOne.setDirection(Servo.Direction.FORWARD);
        servoOne.setPosition(servoOneInitPosition);
    }

    public void servoOneTelemetry() {
        telemetry.addData("Position: ", servoOne.getPosition());
        telemetry.addData("Direction: ", servoOne.getDirection());
        telemetry.update();
    }
}
