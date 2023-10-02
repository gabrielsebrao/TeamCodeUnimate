//Copyright (c) 2017 FIRST. All rights reserved.

package Youtube;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/** Configuration File
 *  COntrol Hub:
 */

@TeleOp(group="Primary")
@Disabled
public class Test extends LinearOpMode
{

    // Variables here

    @Override
    public void runOpMode() throws InterruptedException{
        initHardware();
        while(!isStarted()) {}
        waitForStart();
        while(opModeIsActive()) {}

    }

    public void initHardware() {
    }
}
