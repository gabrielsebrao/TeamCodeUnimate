//Copyright (c) 2017 FIRST. All rights reserved.

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/** Configuration
 * Control Hub:
 * Motor Port 00: motorEsquerda
 * Motor Port 01: motorDireita
 * Motor Port 02: motorBraco1
 * Motor Port 03: motorBraco2
 * Servo Port 00: servoGarra
 * Servo Port 01: servoBraco
 */

@TeleOp(name="Complete TeleOp", group="TeleOp Mode")
//@Disabled
public class MainOpMode extends OpMode
{
    // Declare OpMode variables
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor = null;
    private double leftMotorPower = 0;

    private DcMotor rightMotor = null;
    private double rightMotorPower = 0;

    private DcMotor armMotorOne = null;
    private double armMotorOnePower = 0;

    private DcMotor armMotorTwo = null;
    private double armMotorTwoPower = 0;

    private Servo clawServo = null;
    private double clawServoPosition = 0;

    private Servo armServo = null;
    private double armServoPosition = 0;

    //Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        initHardware();
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    //Code to run ONCE when the driver hits PLAY
    @Override
    public void start() {
        runtime.reset();
    }

    //Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        runTeleOpControls();
        showTelemetry();
    }

    public void runTeleOpControls() {

        // Set left and right motors power
        double drive = gamepad1.left_stick_y;
        double turn  = -gamepad1.right_stick_x;
        leftMotorPower = Range.clip(drive + turn, -1.00, 1.00) ;
        rightMotorPower = Range.clip(drive - turn, -1.00, 1.00) ;
        leftMotor.setPower(leftMotorPower);
        rightMotor.setPower(rightMotorPower);

        // Set the arm motors power
        if(gamepad1.a) {
            armMotorOnePower = 1;
            armMotorTwoPower = 1;
        } else {
            armMotorOnePower = 0;
            armMotorTwoPower = 0;
        }
        armMotorOne.setPower(armMotorOnePower);
        armMotorTwo.setPower(armMotorTwoPower);

        if(gamepad1.b) {
            clawServoPosition = 1;
        } else {
            clawServoPosition = 0;
        }
        clawServo.setPosition(clawServoPosition);
    }

    // Inits all hardware members
    public void initHardware() {
        initLeftMotor();
        initRightMotor();
        initArmMotorOne();
        initArmMotorTwo();
        initClawServo();
        initArmServo();
    }

    // Inits the left motor
    public void initLeftMotor() {
        leftMotor = hardwareMap.get(DcMotor.class, "motorEsquerda");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        leftMotor.setPower(0);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // Inits the right motor
    public void initRightMotor() {
        rightMotor = hardwareMap.get(DcMotor.class, "motorDireita");
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setPower(0);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // Inits the first arm motor
    public void initArmMotorOne() {
        armMotorOne = hardwareMap.get(DcMotor.class, "motorBraco1");
        armMotorOne.setDirection(DcMotor.Direction.FORWARD);
        armMotorOne.setPower(0);
        armMotorOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        armMotorOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        armMotorOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // Inits the second arm motor
    public void initArmMotorTwo() {
        armMotorTwo = hardwareMap.get(DcMotor.class, "motorBraco2");
        armMotorTwo.setDirection(DcMotor.Direction.FORWARD);
        armMotorTwo.setPower(0);
        armMotorTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        armMotorTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        armMotorTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // Inits the claw servo
    public void initClawServo() {
        clawServo = hardwareMap.get(Servo.class, "servoGarra");
        clawServo.setDirection(Servo.Direction.FORWARD);
    }

    // Inits the arm servo
    public void initArmServo() {
        armServo = hardwareMap.get(Servo.class, "servoBraco");
        armServo.setDirection(Servo.Direction.FORWARD);
    }

    // Show telemetry data
    public void showTelemetry() {
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftMotorPower, rightMotorPower);
        telemetry.update();

    }

}
