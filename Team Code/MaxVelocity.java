package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Max Velocity", group="Tests")
//@Disabled
public class MaxVelocity extends OpMode
{
    // Declare OpMode variables
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotorEx leftMotor = null;
    private double leftMotorPower = 1.0;
    private double leftMotorInitPosition = 0;

    private DcMotorEx rightMotor = null;
    private double rightMotorPower = 1.0;
    private double rightMotorInitPosition = 0;
    private double currentVelocity = 0.0;
    private double maxVelocity = 0.0;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        initHardware();
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        runTeleOpControls();
        showTelemetry();
    }

    // Run all mechanisms
    public void runTeleOpControls() {

        // Set left and right motors power
        double drive = gamepad1.left_stick_y;
        double turn  = -gamepad1.right_stick_x;
        leftMotorPower = Range.clip(drive + turn, -1.00, 1.00) ;
        rightMotorPower = Range.clip(drive - turn, -1.00, 1.00) ;
        leftMotor.setPower(leftMotorPower);
        rightMotor.setPower(rightMotorPower);

    }

    // Get the max velocity
    public void motorsMaxVelocityTest() {
        rightMotor.setPower(1);
        currentVelocity = rightMotor.getVelocity(); // ticks per second RPM
        if(currentVelocity > maxVelocity) {
            maxVelocity = currentVelocity;
        }
    }

    // Inits all hardware members
    public void initHardware() {
        initLeftMotor();
        initRightMotor();
    }

    // Inits the left motor
    public void initLeftMotor() {
        leftMotor = hardwareMap.get(DcMotorEx.class, "motorEsquerda");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        leftMotor.setPower(0);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // Inits the right motor
    public void initRightMotor() {
        rightMotor = hardwareMap.get(DcMotorEx.class, "motorDireita");
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setPower(0);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void showTelemetry() {

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Left Motor ", "Power (%.2f), Encoder (%.2f)", leftMotor.getPower(), leftMotor.getCurrentPosition());
        telemetry.addData("Right Motor", "Power (%.2f), Encoder (%.2f)", rightMotor.getPower(), rightMotor.getCurrentPosition());
        telemetry.addData("Motors Velocity", "Current velocity (%.2f) Max velocity (%.2f)", currentVelocity, maxVelocity);
        telemetry.update();

    }
}
