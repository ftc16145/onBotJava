package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class DavidShooter extends LinearOpMode {
    
    DcMotorEx shooter;
    Servo front, back;
    double encoder, rpm;

    // todo: write your code here
    @Override
    public void runOpMode(){
        shooter = hardwareMap.get( DcMotorEx.class, "shooter" );
        shooter.setZeroPowerBehavior( DcMotorEx.ZeroPowerBehavior.FLOAT );
        shooter.setDirection( DcMotorEx.Direction.REVERSE );

        
        front = hardwareMap.get( Servo.class, "front" );
        back = hardwareMap.get( Servo.class, "back" );
         front.setPosition(.5);
         back.setPosition(.5);
        waitForStart();
        
        while(opModeIsActive()){
            shooter.setPower(1);
        
            telemetry.addData("Test","Hello" );
            encoder = shooter.getVelocity();
            rpm = encoder*(60/28);
            telemetry.addData("RPM",rpm);
            
            if(rpm >=5000){
                front.setPosition(.2);
                back.setPosition(.2);
                
            }else{
                front.setPosition(.5);
                back.setPosition(.5);
            }

            
            telemetry.update();
            
        }
    }
}
