/*
Copyright 2020 FIRST Tech Challenge Team 16145

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
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

public class TestShooter extends LinearOpMode {
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

        double servoPosition=0.5;
        double servoDirection=0.02;
        double motorSpeed=0.0;
        double motorDirection=0.002;

        shooter.setPower(0.0);
        int motorCurrentPosition = shooter.getCurrentPosition();
        
        ElapsedTime t = new ElapsedTime(ElapsedTime.MILLIS_IN_NANO);
        double currentMillis = t.milliseconds();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            servoPosition += servoDirection;
            if( servoPosition > 1.0 || servoPosition < 0.0 ) {
                servoDirection = -servoDirection;
                servoPosition += servoDirection;
            }
            front.setPosition(servoPosition);
            back.setPosition(servoPosition);
            
            motorSpeed += motorDirection;
            if( motorSpeed > 0.7 || motorSpeed < 0.0 ) {
                motorSpeed = -motorDirection;
                motorSpeed += motorDirection;
            }
            shooter.setPower(motorSpeed);
            int newPosition = shooter.getCurrentPosition();
            double newMillis = t.milliseconds();
            
            double rpm = (newPosition - motorCurrentPosition)/ 28 / (newMillis - currentMillis);
            //motorCurrentPositio

            //telemetry.addData("Status", "Running");
            telemetry.addData("RPM", (int)rpm );
            telemetry.update();
            
            sleep(50);
        }
    }
}
