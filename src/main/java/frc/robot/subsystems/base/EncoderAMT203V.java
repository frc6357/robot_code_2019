package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.SPI;

public class EncoderAMT203V
{

    private final SPI encoder;

    /**
     * First the host will issue a command, then the encoder may respond with
     * wait responses (0xA5) until ready. Once ready the encoder will echo the
     * original command received from the master, followed by the requested data.
     */
    private final byte RESPONSE_WAIT = (byte) 0xA5;

    /**
     * This command causes a read of the current position
     */
    private final byte RD_POS_COMMAND = (byte) 0x10;

    /**
     * Success byte if zero was set correctly
     */
    private final byte SUCCESS_ZERO_SET = (byte) 0x08;

    /**
     * Byte received when idle
     */
    private final byte RECEIVE_IDLE = (byte) 0xA5;

    /**
     * This no operation command is ignored by the encoder and simply causes the
     * next byte of data to be read. The encoder will respond with 0xA5 if there
     * is no remaining data to be sent.
     */
    private final byte NO_OPERATION_COMMAND = (byte) 0x00;

    /**
     * This command sets the current position to zero and saves this setting in
     * the EEPROM.
     */
    private final byte SET_ZERO_POINT_COMMAND = (byte) 0x70;

    /**
     * All commands are 8 bits long.
     */
    private final int COMMAND_BIT_LENGTH = 8;

    /**
     * It is recommended that the master leave a 20 μs delay between reads to avoid
     * extending the read time by forcing wait sequences.
     */
    private final int READ_DELAY = 20;

    /**
     * MCU 12 bit position register is updated from every 48 μs / hz
     */
    private final int MCU_REGISTER_UPDATE_RATE = 48;

    // Holds the position of the encoder
    private byte[] positionPacket;

    // Packet to send
    private byte[] sendPacket;

    private byte[] receivedPacket;

    /**
     * Constructor:
     *
     * Ports can be set to one of the specified enums:
     * { kMXP, kOnboardCS0, kOnboardCS1, kOnboardCS2, kOnboardCS3 }
     */
    public EncoderAMT203V(SPI.Port port)
    {
        encoder = new SPI(port);


        sendPacket = new byte[1];
        receivedPacket = new byte[1];

        positionPacket = new byte[2];

        encoder.setClockRate(1000000);
        encoder.setClockActiveLow();
        encoder.setSampleDataOnLeadingEdge();
        encoder.setChipSelectActiveLow();
        encoder.setMSBFirst();
    }

    public int getAngle()
    {
        return 0;
    }

    public void setZeroPoint()
    {
        boolean zeroPointSet = false;

        while(!zeroPointSet)
        {
            // Set the packet to set zero point byte
            setSendPacket(SET_ZERO_POINT_COMMAND);

            // Write set zero point command
            encoder.write(sendPacket, sendPacket.length);
            clearSendPacket();

            // Read the next value of the CLK throwing away value recieved on write
            encoder.read(false, receivedPacket, receivedPacket.length);

            // While the received packet is not a success message
            while(receivedPacket[0] != SUCCESS_ZERO_SET)
            {
                clearReceivePacket();

                setSendPacket(RESPONSE_WAIT);
                encoder.write(sendPacket, sendPacket.length);

                encoder.read(true, receivedPacket, receivedPacket.length);
            }
            zeroPointSet = true;
            System.out.println("Encoder Zero Set");
        }
    }

    // DW: Did you mean to make this private?
    private int read()
    {
        boolean havePosition = false;

        while(!havePosition)
        {
            // DW: Using a method here seems overkill when all you're doing is
            // writing a byte into an array. Maybe just set the value inline without
            // all the creation/destruction of buffers via setSendPacket/clearSendPacket?
            // It's better to create the byte arrays once on initialization and just 
            // reuse them - "new" is an expensive operation.

            // Set the packet to read position command
            setSendPacket(RD_POS_COMMAND);

            // Write read position command
            encoder.write(sendPacket, sendPacket.length);
            clearSendPacket();

            // Read the next value of the CLK throwing away value recieved on write
            encoder.read(false, receivedPacket, receivedPacket.length);

            // DW: The value in receivedPacket[0] will be undefined after the last
            // line. You can't then check it in the next line. Theres a possibility (albeit
            // low) that it just happens to contain RD_POS_COMMAND.

            // While the received packet is not a success message
            while(receivedPacket[0] != RD_POS_COMMAND)
            {
                // Clear recieve packet array
                clearReceivePacket();

                // DW: Based on our discussion, I dont think you want to do this here.
                //     You can read the next byte using encoder.read(true, receivedPacket, 1);
                //     Once you see the RD_POS_COMMAND, you can then do a read(true, receivedPacket, 2);
                //     to read both the position bytes. This will need a little state machine,
                //     though, but should be a lot more efficient than the current loop.

                // Set the packet to send to wait command
                setSendPacket(RESPONSE_WAIT);

                // Do a transaction. Loop will end when the recieved packet is the same of the command
                encoder.transaction(sendPacket, receivedPacket, sendPacket.length);
            }

            // Clear received packet
            clearReceivePacket();

            // Send a wait command to get the next byte
            encoder.transaction(sendPacket, receivedPacket, sendPacket.length);

            // The lower 4 bits of this packet will be the upper 4 of the 12 bit position
            positionPacket[0] = receivedPacket[0];

            // Clear received packet
            clearReceivePacket();

            // Send a wait command to get the next byte
            encoder.transaction(sendPacket, receivedPacket, sendPacket.length);

            // This packet is the lower 8 bits of the 12 bit position
            positionPacket[1] = receivedPacket[0];

            havePosition = true;

            // DW: You don't want to do this if you'll be reading the encoder every
            // 20mS! For debug it's fine but perhaps a better idea would be to send the
            // position back to the driver station via SmartDashboard? Actually, it would
            // be better to have the higher level class do this only when it is needed.
            System.out.println("Encoder has position");
        }

        // DW: You may find this gives unexpected results though this depends upon
        // how Java handles conversion of bytes to ints. If it does all the logic on
        // the byte type first then converts the result to int, this will fail because
        // shifting a byte left by 8 effectively clears all the bits! To guard against
        // this, cast both the bytes to int before doing the logic operations:
        //
        // return ((int)positionPacket[1] | (((int)positionPacket[0] & 0xFF) << 8)));
        return (int) (positionPacket[1] | ((positionPacket[0] & 0xFF) << 8));
    }

    // DW: I reckon these functions are unnecessary and merely slow down the 
    //     operation. Statically allocation the receive buffer (3 bytes) and the
    //     send buffer (1 byte) and just reuse. Don't create/destroy since this
    //     is pretty enormously expensive in CPU cycles.
    private void setSendPacket(byte packet)
    {
        sendPacket[0] = packet;
    }

    private void clearSendPacket()
    {
        sendPacket = new byte[1];
    }

    private void clearReceivePacket()
    {
        receivedPacket = new byte[1];
    }
}