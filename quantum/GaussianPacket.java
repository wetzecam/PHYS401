public class GaussianPacket {
  double w, x0, p0;
  double w42;
  double norm;

  public GaussianPacket(double width, double center, double momentum) {
    w = width;
    w42 = 4*w*w;
    x0 = center;
    p0 = momentum;
    norm = Math.pow(2*Math.PI*w*w, -0.25);
  }

  public double getReal(double x) {
    return norm*Math.exp(-(x-x0)*(x-x0)/w42)*Math.cos(p0*(x-x0));
  }

  public double getImaginary(double x) {
    return norm*Math.exp(-(x-x0)*(x-x0)/w42)*Math.sin(p0*(x-x0));
  }
}
