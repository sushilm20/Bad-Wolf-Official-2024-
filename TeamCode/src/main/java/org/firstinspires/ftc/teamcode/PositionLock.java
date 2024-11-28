package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Position Lock", group="Linear OpMode")
public class PositionLock extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront = null;
    private boolean grip = true;
    private DcMotor leftBack = null;
    private DcMotor rightFront = null;
    private DcMotor rightBack = null;
    private DcMotor rightElevator = null;
    private DcMotor leftElevator = null;
    private Servo rightElevatorServo = null;
    private Servo leftElevatorServo = null;
    private Servo masterClaw = null;
    private boolean masterClawPosition = false;
    private double speedMultiplier = 1.0; // Speed multiplier

    // Variables to store position lock
    private int leftFrontPos = 0;
    private int leftBackPos = 0;
    private int rightFrontPos = 0;
    private int rightBackPos = 0;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "No status for you");
        telemetry.update();

        // Initialize hardware variables
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        rightElevator = hardwareMap.get(DcMotor.class, "rightElevator");
        leftElevator = hardwareMap.get(DcMotor.class, "leftElevator");
        rightElevatorServo = hardwareMap.get(Servo.class, "rightElevatorServo");
        leftElevatorServo = hardwareMap.get(Servo.class, "leftElevatorServo");
        masterClaw = hardwareMap.get(Servo.class, "masterClaw");

        // Set motors to run using encoders
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // Store the initial position
        leftFrontPos = leftFront.getCurrentPosition();
        leftBackPos = leftBack.getCurrentPosition();
        rightFrontPos = rightFront.getCurrentPosition();
        rightBackPos = rightBack.getCurrentPosition();

        while (opModeIsActive()) {
            // Your existing control code here...

            // Check if there's no controller input
            if (gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == 0 && gamepad1.right_stick_x == 0 && gamepad1.right_stick_y == 0) {
                // If no input, move the motors back to the original position
                leftFront.setTargetPosition(leftFrontPos);
                leftBack.setTargetPosition(leftBackPos);
                rightFront.setTargetPosition(rightFrontPos);
                rightBack.setTargetPosition(rightBackPos);

                leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                // Apply a power to move the motors
                leftFront.setPower(1.0);
                leftBack.setPower(1.0);
                rightFront.setPower(1.0);
                rightBack.setPower(1.0);

                // Wait until target position is reached
                while (leftFront.isBusy() && leftBack.isBusy() && rightFront.isBusy() && rightBack.isBusy()) {
                    // Optionally do something while waiting
                }

                // Stop all motors
                leftFront.setPower(0);
                leftBack.setPower(0);
                rightFront.setPower(0);
                rightBack.setPower(0);

                // Set motors back to RUN_USING_ENCODER mode
                leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            } else {
                // Update the stored position when there's input
                leftFrontPos = leftFront.getCurrentPosition();
                leftBackPos = leftBack.getCurrentPosition();
                rightFrontPos = rightFront.getCurrentPosition();
                rightBackPos = rightBack.getCurrentPosition();
            }

            // Update telemetry
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "leftFront (%.2f), leftBack (%.2f), rightFront (%.2f), rightBack (%.2f)", leftFront.getPower(), leftBack.getPower(), rightFront.getPower(), rightBack.getPower());
            telemetry.update();
        }
    }
}