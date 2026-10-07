package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;



@TeleOp
public class michael extends OpMode {
    @Override
    public void init() {

        telemetry.addData("how old is Bergen?","press a to find out");
    }

    double bergenage = 67;

    @Override
    public void loop() {
        if (gamepad1.aWasPressed()) {
            telemetry.addData("how old is Bergen?", bergenage);
        }
    }
}
