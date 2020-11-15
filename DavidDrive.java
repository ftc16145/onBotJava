package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class DavidDrive extends LinearOpMode {
    
    DcMotorEx left, right;
    ElapsedTime time;
    double secs, average, target;
    // todo: write your code here
    @Override
    public void runOpMode(){
        left = hardwareMap.get( DcMotorEx.class, "left" );
        left.setZeroPowerBehavior( DcMotorEx.ZeroPowerBehavior.BRAKE );
        left.setDirection( DcMotorEx.Direction.REVERSE );
        right = hardwareMap.get( DcMotorEx.class, "right" );
        right.setZeroPowerBehavior( DcMotorEx.ZeroPowerBehavior.BRAKE );
        right.setDirection( DcMotorEx.Direction.REVERSE );
        time = new ElapsedTime( ElapsedTime.Resolution.SECONDS );
        
        waitForStart();
        time.reset();
        while(opModeIsActive()){
            secs = time.seconds();
        target = 24/(96*(Math.PI/25.4))*537.6;
            telemetry.addData("Test","Potato" );
            
            average = (left.getCurrentPosition()+right.getCurrentPosition())/2;
            telemetry.addData("Average", average);
            
            
            
            if(average <=target){
                left.setPower(.5);
                right.setPower(.5);
            } else {
                left.setPower(0);
                right.setPower(0);
            }
            
          
            telemetry.addData("Time", secs);
            telemetry.update();
            
        }
    }
}
