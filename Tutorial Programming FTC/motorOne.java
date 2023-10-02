//Copyright (c) 2017 FIRST. All rights reserved.

package Youtube;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/** Configuration File
 *  COntrol Hub:
 *  Motor Port 00: motorOne
 */

@TeleOp(group="Primary")
@Disabled
public class motorOne extends LinearOpMode
{

    // Variables here
    private DcMotor motorOne;
    private double motorOneInitPower = 0.0;
    private double motorOnePower = 1.0;

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
    }

    public void initMotorOne() {
        motorOne = hardwareMap.get(DcMotor.class, "motorOne");
        motorOne.setDirection(DcMotor.Direction.FORWARD);
        motorOne.setPower(motorOneInitPower);
        motorOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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

    }

    public void motorTelemetry() {
        telemetry.addData("motorOne", "Enconder: %.2d, Power: %.2d", motorOne.getCurrentPosition(), motorOne.getPower());
        telemetry.update();
    }
}
