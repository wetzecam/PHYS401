import org.opensourcephysics.numerics.Function;

public class Synthesize implements Function {
  double[] cosCoefficients, sinCoefficients;
  // cos & sin series coeff.
  double a0;  // the constant term
  double omega0;

  public Synthesize(double period, double a0, double[] cosCoef, double[] sinCoef){
    omega0 = Math.PI*2/period;
    cosCoefficients = cosCoef;
    sinCoefficients = sinCoef;
    this.a0 = a0;
  }

  public double evaluate(double x) {
    double f = a0/2;
    // sum the cosines
    for(int i=0; i < cosCoefficients.length; i++){
      f += cosCoefficients[i]*Math.cos(omega0*x*(i+1));
    }
    // sum the cosines
    for(int i=0; i < cosCoefficients.length; i++){
      f += sinCoefficients[i]*Math.sin(omega0*x*(i+1));
    }
    return f;
  }
}
