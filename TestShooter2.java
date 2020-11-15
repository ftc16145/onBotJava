package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Remove a @Disabled the on the next line or two (if present) to add this opmode to the Driver Station OpMode list,
 * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
 */
@TeleOp

public class TestShooter2 extends LinearOpMode {
    private Blinker expansion_Hub_2;
    private Servo back;
    private Servo front;
    private Gyroscope imu;
    private DcMotor shooter;


    @Override
    public void runOpMode() {
        expansion_Hub_2 = hardwareMap.get(Blinker.class, "Expansion Hub 2");
        back = hardwareMap.get(Servo.class, "back");
        front = hardwareMap.get(Servo.class, "front");
        imu = hardwareMap.get(Gyroscope.class, "imu");
        shooter = hardwareMap.get(DcMotor.class, "shooter");

        double servoPosition = 0.5;
        double servoDirection = 0.02;
        double motorSpeed = 0.9;
        double motorDirection = 0.00;

        ElapsedTime et = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        double currentMillis = et.milliseconds();
        int currentPosition = shooter.getCurrentPosition();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            servoPosition += servoDirection;
            if( servoPosition > 1.0 || servoPosition < 0.0 ) {
                servoDirection = -servoDirection;
                servoPosition += servoDirection;
            }

            motorSpeed += motorDirection;
            if( motorSpeed < 0.0 || motorSpeed > 1.0 ) {
                motorDirection = -motorDirection;
                motorSpeed += motorDirection;
            }

            front.setPosition(servoPosition);
            back.setPosition(servoPosition);
            shooter.setPower(motorSpeed);

            telemetry.addData("Status", "Running");

            int newPosition = shooter.getCurrentPosition();
            double newMillis = et.milliseconds();

            if( newPosition != currentPosition && newMillis != currentMillis ) {
                int dPos = (newPosition-currentPosition);
                double dMsec = (newMillis-currentMillis);
                int rpm = (int)( (newPosition-currentPosition) * 1000.0 / 28.0 / (newMillis-currentMillis) * 60.0 );
                currentMillis = newMillis;
                currentPosition = newPosition;

                telemetry.addData("RPM", rpm );
                telemetry.addData("dMillis", dMsec );
                telemetry.addData("dPosition", dPos );

            }

            telemetry.update();
            sleep(50);
        }
    }
}
