import java.util.*;

public class Main {

    public static void show_menu(){
        System.out.println("Bienvenido al conversor de monedas");
        System.out.println("=========== MENU ============");
        System.out.println("Ingrese el número de la opción de conversion");
        System.out.println("1. DOLAR a PESO COLOMBIANO (COP)");
        System.out.println("2. PESO COLOMBIANO a DOLAR (USD)");
        System.out.println("3. PESO ARGENTINO a DOLAR (USD)");
        System.out.println("4. Conversion personalizada");
        System.out.println("5. Mostrar todas monedas soportadas");
        System.out.println("6. SALIR");
    }

    public static int read_valid_int(Scanner en){
        int option = 0;
        while(true){
            if(en.hasNextInt()){
                option = en.nextInt();
                break;
            }else{
                System.out.println("Ingresa un numero de opcion válido");
                en.next();
            }
        }
        return option;
    }

    public static double read_valid_double(Scanner en){
        double ans = 0;
        while(true){
            if(en.hasNextDouble()){
                ans = en.nextDouble();
                break;
            }else{
                System.out.println("Ingresa un valor válido");
                en.next();
            }
        }
        return ans;
    }

    public static void print_conversion(String base, String target, double value, ApiConnection api){
        double conversion_rate = api.get_conversion_rate(base, target);
        double answer = value * conversion_rate;
        System.out.printf("Conversion realizada:\n%f (%s) equivalen a %f (%s)\n\n", value, base, answer, target);
    }

    public static void main(String[] args) {
        ApiConnection api = new ApiConnection();
        Scanner en = new Scanner(System.in);
        TreeSet<String> st = new TreeSet<>();
        List<String> supported_codes = api.get_supported_codes();
        for(String i: supported_codes){
            int start = i.indexOf('"');
            int end = i.indexOf('"', 2);
            st.add(i.substring(start + 1, end));
        }

        while(true){
            show_menu();

            int option = read_valid_int(en);

            if(option == 1){
                System.out.println("Ingresa el valor en dolares (USD):");
                double value = read_valid_double(en);
                print_conversion("USD", "COP", value, api);
            }else if(option == 2){
                System.out.println("Ingresa el valor en pesos colombianos (COP):");
                double value = read_valid_double(en);
                print_conversion("COP", "USD", value, api);
            }else if(option == 3){
                System.out.println("Ingresa el valor en peso argentino (ARS):");
                double value = read_valid_double(en);
                print_conversion("ARS", "USD", value, api);
            }else if(option == 4){
                System.out.println("Para conocer la monedas soportadas puedes usar la opcion 5");
                System.out.println("Ingresa la moneda inicial en su abreviacion EJ. USD, ARS");
                String base = en.next();
                if(!st.contains(base)){
                    System.out.printf("La moneda (%s) no ha sido encontrada, intentalo nuevamente\n", base);
                    continue;
                }
                System.out.println("Ingresa la moneda final EJ. COP, ZAR");
                String target = en.next();
                if(!st.contains(target)){
                    System.out.printf("La moneda (%s) no ha sido encontrada, intentalo nuevamente\n", base);
                    continue;
                }
                double value = read_valid_double(en);
                print_conversion(base, target, value, api);
            }else if(option == 5){
                System.out.println("MONEDAS SOPORTADAS");
                for(var i: supported_codes){
                    System.out.println(i);
                }
            }else if(option == 6){
                System.out.println("Has salido");
                break;
            }


//            var to = en.next();
//            var base = en.next();
//            Double conversion_rate = api.get_conversion_rate(base, to);
//
//            System.out.println("Ingrese la cantidad");
//            Double val = en.nextDouble();
//            System.out.printf("Conversion realizada:\n%f %s es %f %s", val, base, val * conversion_rate, to);
        }
    }
}