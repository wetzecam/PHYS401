

public class NewtonIteration{

//  public static double ffp(double a){
//    return a*(1.0 - a*(Math.PI/4.0) - Math.exp(-a))/((1.0+a)*Math.exp(-a) - 1.0);
//  }

  public static double ffp(double a){
    //return a*(1.0 - a*(Math.pi/4.0) - Math.exp(-a))/((1.0+a)*Math.exp(-a) - 1.0);
    return a*(Math.exp(a) - 1.0 - a*Math.PI/2.0)/(1.0 + (a-1.0)/Math.exp(a));
  }


  public static double f_1(double x, double a){
    return Math.exp(a*x)/(1.0+Math.pow(x,2));
  }
  public static double f_2(double x, double a){
    return x*f_1(x,a);
  }
  public static double f_3(double x, double a){
    return x*f_2(x,a);
  }

  public static double h(double x, double a){
    return 4.0*(1.0-Math.exp(-a))*f_1(x,a)/a;
  }

  public static double h_dot(double x, double a){
    return 4.0*((a+1.0)*Math.exp(-a)-1.0)*f_1(x,a)/Math.pow(a,2) + 4.0*(1.0-Math.exp(-a))*f_2(x,a)/a;
  }

  public static double h_dub_dot(double x, double a){
    double A_1 = -4.0*a*Math.exp(-a)/a - (8.0*(a+1.0)-1.0)/Math.pow(a,3);
    double A_2 = (4.0*(a+1.0)*Math.exp(-a)-1.0)/Math.pow(a,2) + 4.0*Math.exp(-a)/a - 4.0*(1.0-Math.exp(-a))/Math.pow(a,2);
    double A_3 = 4.0*(1.0-Math.exp(-a))/a;

    return A_1*f_1(x,a) + A_2*f_2(x,a) + A_3*f_3(x,a);
  }

  public static double transform(double x, double a, double b){
    return (x*(b - a)/2.0) + ((a+b)/2.0);
  }





  public static double integrate_1(double a_var, double[] x, double[] w, double a, double b){
    double A = (b - a)/2; // scaling factor for change of integration boundaries
    double sum = 0.0;
    for(int i=0; i<x.length; i++){
      sum += w[i]*h(transform(x[i],a,b), a_var)*h_dot(transform(x[i],a,b), a_var);
    }
    return A*sum;
  }

  public static double integrate_2(double a_var, double[] x, double[] w, double a, double b){
    double A = (b - a)/2; // scaling factor for change of integration boundaries
    double sum = 0.0;
    for(int i=0; i<x.length; i++){
      sum += w[i]*h(transform(x[i],a,b), a_var);
    }
    return A*sum;
  }

  public static double integrate_3(double a_var, double[] x, double[] w, double a, double b){
    double A = (b - a)/2; // scaling factor for change of integration boundaries
    double sum = 0.0;
    for(int i=0; i<x.length; i++){
      sum += w[i]*h_dot(transform(x[i],a,b), a_var);
    }
    return A*sum;
  }

  public static double integrate_4(double a_var, double[] x, double[] w, double a, double b){
    double A = (b - a)/2; // scaling factor for change of integration boundaries
    double sum = 0.0;
    for(int i=0; i<x.length; i++){
      sum += w[i]*h_dot(transform(x[i],a,b), a_var)*h_dot(transform(x[i],a,b), a_var);
    }
    return A*sum;
  }

  public static double integrate_5(double a_var, double[] x, double[] w, double a, double b){
    double A = (b - a)/2; // scaling factor for change of integration boundaries
    double sum = 0.0;
    for(int i=0; i<x.length; i++){
      sum += w[i]*h(transform(x[i],a,b), a_var)*h_dub_dot(transform(x[i],a,b), a_var);
    }
    return A*sum;
  }

  public static double integrate_6(double a_var, double[] x, double[] w, double a, double b){
    double A = (b - a)/2; // scaling factor for change of integration boundaries
    double sum = 0.0;
    for(int i=0; i<x.length; i++){
      sum += w[i]*h_dub_dot(transform(x[i],a,b), a_var);
    }
    return A*sum;
  }

  public static double EpsilonyPeperoni(double a){
    double[] x_gauss3 = {-Math.sqrt(3.0/5.0), 0.0, Math.sqrt(3.0/5.0)};
    double[] w_gauss3 = {(5.0/9.0), (8.0/9.0), (5.0/9.0)};

    double NUMERATOR = integrate_1(a,x_gauss3,w_gauss3,0.0,1.0) - integrate_2(a,x_gauss3,w_gauss3,0.0,1.0)*integrate_3(a,x_gauss3,w_gauss3,0.0,1.0);
    double DENOMINATOR = integrate_4(a,x_gauss3,w_gauss3,0.0,1.0) - integrate_3(a,x_gauss3,w_gauss3,0.0,1.0)*integrate_3(a,x_gauss3,w_gauss3,0.0,1.0) + integrate_5(a,x_gauss3,w_gauss3,0.0,1.0) - integrate_2(a,x_gauss3,w_gauss3,0.0,1.0)*integrate_6(a,x_gauss3,w_gauss3,0.0,1.0);
    return NUMERATOR/DENOMINATOR;
  }


  public static void main(String[] args){
    double x_o = Double.valueOf(args[0]);
    double epsilon = 1.0;
    int N=0;
    while(Math.abs(epsilon) >= Math.pow(10,-13)){
      epsilon = -EpsilonyPeperoni(x_o);
      x_o = x_o + epsilon;
      N++;
    }

    System.out.println("PI ~ "+x_o);
    System.out.println("Espilon = "+epsilon);
    System.out.println("Iterations: "+N);
  }
}
