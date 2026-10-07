package org.firstinspires.ftc.teamcode.autonomous;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

/*
To add another path, do this:

1. Add path state, usually named "PATH_X", under: public enum PathState {}
2. Add another: private final Pose <name> = new Pose(x, y, Math.toRadians(<heading>));
3. Add the path into the chain,where PATH_X to PATH_Y is "pathXPathY", under: private PathChain
4. Build the path under: public void buildPaths() {}
5. Update the paths under: public void statePathUpdate() {}

Find coordinates and headings on https://live.turtletracer.com/
(hold ctrl to follow link)

The official PedroPathing visualiser is here (but TurtleTracer Visualiser is better):
https://visualizer.pedropathing.com/
 */



@Autonomous
public class TestPedroAuto extends OpMode {
    private Follower follower;
    private Timer pathTimer, opModeTimer;

    public enum PathState {
        PATH_1,
        PATH_2,
        PATH_3 //just prints "All paths complete" to telemetry, delete this comment if actual path added

    }

    PathState pathState;

    // STARTING POS
    private final Pose startPose = new Pose(10.10, 24.34, Math.toRadians(90));

    // PATH POS
    private final Pose path1 = new Pose(59.62, 38.63, Math.toRadians(90));
    private final Pose path2 = new Pose(12.00, 107.58, Math.toRadians(90));

    // CONTROL POINTS (angle is always 0 degrees)
    private final Pose ctrl1_Path1 = new Pose(59.61904761904762, 10.628571428571423, Math.toRadians(0));
    private final Pose ctrl1_Path2 = new Pose(10.095238095238095, 30.438095238095244, Math.toRadians(0));


    private PathChain startPosePath1, path1Path2; //path2Path3

    public void buildPaths() {
        // put in pose for starting pose to ending pose
        startPosePath1 = follower.pathBuilder()
                .addPath(new BezierCurve(startPose, ctrl1_Path1, path1))
                .setConstantHeadingInterpolation(path1.getHeading())
                .build();
        path1Path2 = follower.pathBuilder()
                .addPath(new BezierCurve(path1, ctrl1_Path2, path2))
                .setConstantHeadingInterpolation(path2.getHeading())
                .build();
//        path2Path3 = follower.pathBuilder()
//                .addPath(new BezierLine(path2, path3))
//                .setLinearHeadingInterpolation(path2.getHeading(), path3.getHeading())
//                .build();

    }

    public void statePathUpdate() {
        switch(pathState) {
            case PATH_1:
                follower.followPath(startPosePath1, true); // holdEnd keeps robot stopped at end
                setPathState(PathState.PATH_2);
                break;
            case PATH_2:
                if (follower.isBusy()) {
                    pathTimer.resetTimer();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 3) {
                    follower.followPath(path1Path2, true);
                    setPathState(PathState.PATH_3);
                }
                break;
            case PATH_3:
                if (!follower.isBusy()) {
                    telemetry.addLine("All paths complete"); //remove if actual path added
                    //follower.followPath(path2Path3, true);
                    //setPathState(PathState.PATH_4);
                }
                break;
            default:
                telemetry.addLine("No State Commanded");
                break;
        }
    }

    public void setPathState(PathState newState) {
        pathState = newState;
        pathTimer.resetTimer();
    }


    // The code for the robot is below, init stuff is above


    @Override
    public void init() {
        pathState = PathState.PATH_1; //starting path state
        pathTimer = new Timer();
        opModeTimer = new Timer();
        follower = Constants.createFollower(hardwareMap);

        buildPaths();
        follower.setPose(startPose);
    }

    public void start() {
        opModeTimer.resetTimer();
        setPathState(pathState);
    }

    @Override
    public void loop() {
        follower.update(); // This is required for every pedropathing loop, so paths are put in
        statePathUpdate(); // This is required for every pedropathing loop, so paths are put in

        telemetry.addData("path state", pathState.toString());
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("path time", pathTimer.getElapsedTimeSeconds());
    }
}