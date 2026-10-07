package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/*


This java class is for defining PedroPathing tuning constants and general information.
Helpful links:
https://www.youtube.com/watch?v=vihb2LPtSK0 (hold ctrl to follow link)
https://www.youtube.com/watch?v=gdkefs_VL-w


 */

public class Constants { // this block is tuning values for the robot
    public static FollowerConstants followerConstants = new FollowerConstants()
        .mass(5.064) // mass of the robot in kg
        .forwardZeroPowerAcceleration(-32.53160060303342)
        .lateralZeroPowerAcceleration(-48.85462563743743)
        .translationalPIDFCoefficients(new PIDFCoefficients(0.04, 0, 0.01, 0.02))
        .headingPIDFCoefficients(new PIDFCoefficients(0.35, 0, 0.015, 0.02))
        .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.025, 0, 0.00001, 0.6, 0.025))
        .centripetalScaling(0.0001)
        ;
    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(0.75)
            .rightFrontMotorName("FR")
            .rightRearMotorName("BR")
            .leftRearMotorName("BL")
            .leftFrontMotorName("FL")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(90.97737181656005)
            .yVelocity(74.99800266055611); //13:47 in video

    public static PinpointConstants localizerConstants = new PinpointConstants() // pinpoint imu designations
            .forwardPodY(-0.25) // Y odometry offset from pinpoint imu (in inches)
            .strafePodX(0) // X odometry offset from pinpoint imu (in inches)
            .distanceUnit(DistanceUnit.INCH) // measurements are in inches
            .hardwareMapName("pinpoint") // name of the pinpoint in driver hub config. Default is "pinpoint"
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD) // reversed if Y wheel is backwards
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD); // reversed if X wheel is backwards

    public static PathConstraints pathConstraints = new PathConstraints(0.99,
            100,
            1.5,
            1);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pinpointLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)

                .build();
    }
}
