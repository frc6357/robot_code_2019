package frc.robot.utils.filters;

/*
 * Creates a cubic curve on the filter
 * 0.0 correlates to 0.0, and 1.0 correlates to "coeff" and
 * -1.0 correlates to "-coeff". The transfer function is
 * 
 * output = coefficient * (input^3)
 */
public class ExponentialFilter extends Filter
{
    private double coefficient; // Coefficient or gain to multiply by

    /**
     * Default constructor, gives a coefficient of coef
     * 
     * @param coef
     *            The coefficient of the cubic function. This can be positive or negative, allowing
     *            the joystick value to be inverted at the same time as the filter is applied.
     */
    public ExponentialFilter(double coef)
    {
        coefficient = coef;
    }

    /**
     * Filters the input using a scaled cubic transfer function.
     * 
     * @param rawAxis
     *            the data to be read in, from -1 to 1
     * @return the cubic relation of that data
     */
    @Override
    public double filter(double rawAxis)
    {
        return coefficient * Math.pow(rawAxis, 3);
    }

    /**
     * Sets the coefficient of the cubic function
     * 
     * @param c
     *            the coefficient, which must be greater than zero
     */
    public void setCoef(double c)
    {
        coefficient = Math.abs(c);
    }
}
