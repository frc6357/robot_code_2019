package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.SPI;

/**
 * This class provides support for the CIU AMT 203V absolute encoder with SPI interface.
 */
public class SPIEncoderAMT203V
{
    private final SPI encoder;

    // First the host will issue a command, then the encoder may respond with
    // wait responses (0xA5) until ready. Once ready the encoder will echo the
    // original command received from the master, followed by the requested data.
    private final static byte RESPONSE_WAIT = (byte) 0xA5;

    // This command causes a read of the current position
    private final static byte COMMAND_RD_POS = (byte) 0x10;

    // This command sets the current position to zero and saves this setting in
    // the EEPROM.
    private final static byte COMMAND_SET_ZERO_POINT = (byte) 0x70;

    // The maximum number of wait polls before we decide that the encoder isn't 
    // going to work.
    private final static int POS_READ_MAX_POLLS = 50;

    // The last value read from the encoder.
    private int latestReading  = 0;

    // The resolution of the encoder in pulses per revolution.
    private int pulsesPerRev   = 1024;

    /**
     * Constructor:
     *
     * Ports can be set to one of the specified enums:
     * { kMXP, kOnboardCS0, kOnboardCS1, kOnboardCS2, kOnboardCS3 }
     */
    public SPIEncoderAMT203V(SPI.Port port, int pulsesPerRotation)
    {
        encoder = new SPI(port);
        pulsesPerRev = pulsesPerRotation;

        encoder.setClockRate(1000000);
        encoder.setClockActiveLow();
        encoder.setSampleDataOnLeadingEdge();
        encoder.setChipSelectActiveLow();
        encoder.setMSBFirst();
    }

    public void reset()
    {
        setZeroPoint();
    }

    public int getAngleDegrees()
    {
        latestReading = get();
        return (int)(((double)latestReading * 360.0) / (double)pulsesPerRev);
    }

    public double getAngleRadians()
    {
        latestReading = get();
        return (((double)latestReading * 2 * Math.PI) / (double)pulsesPerRev);
    }

    private void setZeroPoint()
    {
        boolean isGood;
        byte[] response = new byte[1];

        isGood = doTransaction(COMMAND_SET_ZERO_POINT, 1, response, POS_READ_MAX_POLLS);

        if(!isGood)
        {
            System.out.println("SPI encoder read error!");
        }
    }

    public int get()
    {
        boolean isGood;
        byte[] response = new byte[3];

        isGood = doTransaction(COMMAND_RD_POS, 3, response, POS_READ_MAX_POLLS);

        if(isGood)
        {
            return ((int)response[2] | (((int)response[1] & 0xFF) << 8));
        }
        else
        {
            System.out.println("SPI encoder read error!");
            return 0;
        }
    }

    /**
     * Perform a command transaction to the encoder. This sends a command byte
     * and waits for any response before reading it and returning it to the 
     * caller.
     * 
     * @param command    The command byte to send.
     * @param numToRead  The number of bytes of response data expected (following the 
     *                   echo of the original command byte).
     * @param response   Storage for the returned bytes. This must be at least 1 byte long 
     *                   because we use it during polling prior to reading the actual response.
     * @param timeout    Number of polling attempts to wait before declaring a timeout error.
     * 
     * @return Returns true on success or false if we timed out before receiving a response.
     */
    private boolean doTransaction(byte command, int numToRead, byte[] response, int timeout)
    {
        int pollAttempts = 0;
        int readIndex    = 0;
        byte[] sendByte  = new byte[1];

        // Send the command byte.
        sendByte[0] = command;
        encoder.write(sendByte, 1);

        // Flush the garbage byte read during command transmission.
        encoder.read(false, response, 1);

        // While we read WAIT responses, keep polling.
        response[0] = RESPONSE_WAIT;
        while(response[0] == RESPONSE_WAIT)
        {
            // Have we tried too many times already?
            pollAttempts++;
            if(pollAttempts > timeout)
            {
                return false;
            }

            // Read from the encoder again.
            encoder.read(true, response, 1);
        }

        // While we still have values to read, read bytes.
        readIndex++;
        while(readIndex < numToRead);
        {
            encoder.read(true, sendByte, 1);
            response[readIndex] = sendByte[0];
            readIndex++;
        }

         return true;
     }
}