public class Drivetrain implements Subsystem{

    private DcMotor rFDrive, rBDrive, lFDrive, lBDrive;

    public Drivetrain(DcMotor rightFrontDrive, DcMotor rightBackDrive, DcMotor leftFrontDrive, DcMotor leftBackDrive)
    {
        rFDrive = rightFrontDrive;
        rBDrive = rightBackDrive;
        lFDrive = leftFrontDrive;
        lBDrive = leftBackDrive;

        setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rFDrive.setDirection(DcMotor.Direction.REVERSE);
        rBDrive.setDirection(DcMotor.Direction.FORWARD);
        lFDrive.setDirection(DcMotor.Direction.REVERSE);
        lBDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    public void drive(double forward, double strafe, double turn)
    {
        //strafe *= 1.1; Guide recommends it to make strafing feel better
        double max;

        double rFPower  = forward - strafe - turn;
        double rBPower  = forward + strafe + turn;
        double lFPower  = forward + strafe - turn;
        double lBPower  = forward - strafe + turn;

        // Normalize the values so no wheel power exceeds 100%. This ensures that the robot maintains the desired motion.
        max = Math.max(Math.abs(lFPower), Math.abs(rFPower));
        max = Math.max(max, Math.abs(lBPower));
        max = Math.max(max, Math.abs(rBPower));

        if (max > 1.0) {
            lFPower  /= max;
            rFPower  /= max;
            lBPower  /= max;
            rBPower  /= max;
        }

        setPower(rFPower, rBPower, lFPower, lBPower);
    }

    public void setPower(double rf, double rb, double lf, double lb)
    {
        rFDrive.setPower(rf);
        rBDrive.setPower(rb);
        lFDrive.setPower(lf);
        lBDrive.setPower(lb);
    }
    public void setPower(double spd)
    {
        setPower(spd, spd, spd, spd);
    }
    public void setMode(DcMotor.RunMode m)
    {
        rFDrive.setMode(m);
        rBDrive.setMode(m);
        lFDrive.setMode(m);
        lBDrive.setMode(m);
    }
    public void stop()
    {
        setPower(0.0);
    }

    public void end() {stop();}
    public void output(Telemetry t) {}
}
