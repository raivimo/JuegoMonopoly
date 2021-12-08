/*
 * Proyecto EjerciciosProgramacionJava - Archivo Monopoly.java - Compañia DAW
 * Licencia Creative Commons BY-NC-SA 4.0
 * http://creativecommnos.org/licenses/by-nc-sa/4.0/
 */
package juegomonopoly;

import java.util.Scanner;

/**
 *
 * @author Raimon Vilar Morera <raimonv@gmail.com>
 * @version 1.0
 * @date 29 nov. 2021 19:11:58
 */
public class Monopoly {

    public static String calles[] = {"SALIDA", "RONDA DE VALENCIA", "CAJA DE COMUNIDAD", "PLAZA LAVAPIES", "IMPUESTO SOBRE EL CAPITAL",
        "ESTACIÓN DE GOYA", "GLORIETA CUATRO CAMINOS", "SUERTE", "AVENIDA DE REINA VICTORIA", "CALLE BRAVO MURILLO", "CARCEL/VISITAS",
        "GLORIETA DE BILBAO", "COMPAÑIA DE ELECTRICIDAD", "CALLE ALBERTO AGUILERA", "CALLE FUENCARRAL", "ESTACIÓN DE LAS DELICIAS",
        "AVD. FELIPE II", "CAJA DE COMUNIDAD", "CALLE VELÁZQUEZ", "CALLE SERRANO", "PARKING GRATUITO", "AVENIDA DE AMERICA", "SUERTE",
        "CALLE MARIA DE MOLINA", "CALLE DE CEA BERMÚDEZ", "ESTACIÓN DEL MEDIODIA", "AVDA. DE LOS REYES CATÓLICOS", "CALLE BAILÉN", "COMPAÑÍA DISTRIBUCIÓN DE AGUAS",
        "PLAZA ESPAÑA", "VÉ A LA CÁRCEL", "PUERTA DEL SOL", "CALLE DE ALCALÁ", "CAJA DE COMUNIDAD", "GRAN VÍA", "ESTACIÓN DEL NORTE", "SUERTE",
        "PASEO DE LA CASTELLANA", "IMPUESTO DE LUJO", "PASEO DEL PRADO"};

    public static double[] precioCalles = {200, 60, 0, 60, 200, 200, 100, 0, 100, 120, 0, 140, 150, 140, 160, 200, 180, 0, 180, 200, 0, 220, 0, 220, 240,
        200, 260, 260, 150, 280, 0, 300, 300, 0, 320, 200, 0, 350, 100, 400};

    public static double[] precioAlquiler = new double[40];
    public static double[] precioCasas = new double[40];
    public static int[] libreOcomprada = new int[40];
    public static int[] comprarCasas = new int [40];

    public static int numJugadores;

    public static String[] nombreJugador;
    public static double[] dineroJugador;
    public static int[] posicionJugador;
   

   
    public static int contador = 0, opcion = 0;
    public static boolean salir = false;

    //*ESTA FUNCIÓN SE ENCARGARÁ DE MOSTAR EL MENÚ PRINCIPAL DEL JUEGO Y LLAMA A numJugadores()
    public static void menu() {
        System.out.println("---BIENVENIDO AL JUEGO DEL MONOPOLY----");
        System.out.println("-------POR RAIMON VILAR MORERA---------");
        System.out.println("LAS INSTRUCCIONES DEL JUEGO SON LAS SIGUIENTES:");
        System.out.println("SE TE ASIGNARÁ UNA CANTIDAD DE DINERO INICIAL (200€)");
        System.out.println("Y OBLIGATORIAMENTE HAS DE TIRAR LOS DADOS EN CADA TURNO.");
        System.out.println("SEGUN LA CASILLA QUE CAIGAS: SUERTE, CAJA DE AHORROS, CÁRCEL O CALLE");
        System.out.println("TENDRÁS OPCION DE COMPRAR O PASAR SIN COMPRAR. EN EL CASO DE QUE CAIGAS");
        System.out.println("EN UN CALLE COMPRADA POR OTRO JUGADOR, SE TE EXIGIRÁ EL ALQUILER.");
        System.out.println("UNA VEZ TENGAS UNA PROPIEDAD, PODRÁS EDIFICAR EN ELLA, HACIENDO QUE EL VALOR");
        System.out.println("DEL ALQUILER SEA MÁS CARO.");
        System.out.println("GANARÁ EL ÚLTIMO JUGADOR QUE NO HAYA QUEDADO EN BANCARROTA.");
        numJugadores();
    }

    //ESTA FUNCIÓN SE ENCARGARA DE INICAR EL JUEGO PREGUNTANDO CUANTOS JUGADORES DESSEAN JUGAR Y SU NOMBRE
    public static int numJugadores() {
        Scanner in = new Scanner(System.in);
        System.out.println("¿CUANTOS JUGADORES SON?");
        numJugadores = in.nextInt();
        nombreJugador = new String[numJugadores];
        in.nextLine();
        if (numJugadores <= 4) {
            for (int i = 0; i < numJugadores; i++) {
                System.out.println("NOMBRE DEL JUGADOR " + (i + 1) + ":");
                nombreJugador[i] = in.nextLine();
            }
        } else {
            System.out.println("LO SIENTO. EL MAXIMO NUMERO DE JUGADORES ES 4.");
        }
        //UNA VEZ SABEMOS EL Nº DE JUGADORES, PODEMOS CREAR LOS ARRAYS CORRESPONDIENTES (LO HE INTENTADO EN EL MAIN, PERO NO FUNCIONABA)
        dineroJugador = new double[numJugadores];
        posicionJugador = new int[numJugadores];

        return numJugadores;
    }
    
    //MENU DEL JUEGADOR DONDE LE DÁ A ALEGIR DIFERENTES OPCIONES DE JUEGO.    
    public static void menuJugador() {
        System.out.println("");
        System.out.println("1. TIRAR DADO");
        System.out.println("2. COMPRAR CALLE");
        System.out.println("3. COMPRAR CASA");
        System.out.println("4. FINALIZAR TURNO");
    }

    //REALIZA OPERACION SELECCIONADA DEL MENU DE JUGADORES
    public static int realizarOperacion(int opcion) {
        Scanner in = new Scanner(System.in);
        opcion = in.nextInt();
        switch (opcion) {
            case 1:
                tirarDados();
                break;
            case 2:
                comprarCalle(libreOcomprada);
                break;
            case 3:
                comprarCasas(libreOcomprada);
                break;
            case 4:
                salir = true;
                break;
        }
        return opcion;
    }
    
    //ESTA FUNCION GOBIERNA EL TURNO DE JUGADORES Y GESTIONA LA COMPRA Y PAGO DE LAS CALLES Y CASAS
    public static void turnoJugador() {
        if (contador < numJugadores) {
            for (int i = contador; i < (contador + 1); i++) {
                System.out.println("ES EL TURNO DE:       " + nombreJugador[i]);
                System.out.println("DINERO DISPONIBLE:    " + dineroJugador[i] + " €");
                System.out.println("ESTAS EN LA CALLE:    " + calles[posicionJugador[i]]);
                System.out.println("");
            }
            System.out.println("TIENES EN PROPIEDAD LAS SIGUIENTES CALLES:");
            for (int j = 0; j < libreOcomprada.length ; j++) {
                if(libreOcomprada[j] == contador)
                    System.out.println(calles[j]);
                }
            cobrarAlquiler(libreOcomprada);
            cobrarCasas(comprarCasas);
            
            
            do {
                menuJugador();
                realizarOperacion(opcion);
            } while (salir == false);
            contador++;
        }
        else
            contador=0;

}

    //ASIGNA EL DINERO INICIAL DE LOS JUGADORES EN FUNCIÓN DE SU NUMERO.
    public static void dineroInicial() {
        for (int i = 0; i < numJugadores; i++) {
            dineroJugador[i] += 2000;
        }
    }

    //SE ENCARGA DE TIRAR LOS DADOS ALEATORIAMENTE Y AVANZAR AUTOMATICAMENTE A LA POSICION CORRESPONDIENTE AL JUGADOR
    public static void tirarDados() {
        int dado1, dado2, res;
        dado1 = (int) (1 + Math.random() * 6);
        dado2 = (int) (1 + Math.random() * 6);
        res = dado1 + dado2;
        System.out.println("DADO 1: " + dado1);
        System.out.println("DADO 2: " + dado2);
        System.out.println("HAS SACADO UN: " + res);
        System.out.println("");
        //MOVEMOS EL ARRAY POSICION DEL JUGADOR, SEGUN EL TURNO GOBERNADO POR CONTADOR AL RESULTADO CORRESPONDIENTE POR LOS DADOS 
        for (int i = contador; i < (contador + 1) ; i++) {
            //METEMOS UN IF PARA CONTROLAR QUE LE PUEDA DAR VUELTAS AL TABLERO Y DE ESA MANERA ASEGURAR LA CONTINUIDAD EN EL JUEGO
            if(posicionJugador[contador] + res >= 39 ){
                posicionJugador[contador] = 40 - res;
                System.out.println("HAS CAIDO EN CALLE: " + calles[posicionJugador[i]]);
                System.out.println("");
            }
            else{
                posicionJugador[contador] += res;
                System.out.println("HAS CAIDO EN CALLE: " + calles[posicionJugador[i]]);
                System.out.println("");
            }
        }
    }
    
    //CONTROLA QUE ESTEN LIBRES LAS PROPIEDADES
    public static void libreOcomprada() {
        for (int i = 0; i < libreOcomprada.length; i++) {
            //RECORREMOS EL ARRAY Y LO COMPARAMOS CON UN VALOR, QUE INDICA QUE ESTÁ LIBRE.
            if (libreOcomprada[i] == 5) {
                System.out.println("ESTA CALLE ESTÁ LIBRE. PUEDES COMPRARLA.");
            } else {
                System.out.println("LO SIENTO, ESTA CALLE PERTENECE A OTRO JUGADOR.");
            }
        }
    }
    
    //LLAMA A libreOcomprada PARA SABER SI ESTÁ LIBRE LA CALLE Y EN CASO DE QUE ASÍ SEA ASIGNA EL VALOR DEL CONTADOR A LA PROPIEDAD
    //DEL JUGADOR COMPRADA Y ASÍ RELACIONARLAS.
    public static void comprarCalle(int[] libreOcomprada) {
        Scanner in = new Scanner(System.in);
        //POSICIONAMOS AL JUGADOR Y CONTROLAMOS EL BUCLE PARA QUE SOLO DEJE HACER 1 ITERACIÓN
        for (int i = posicionJugador[contador]; i < (posicionJugador[contador]) + 1 ; i++) {
            //COMPROBAMOS QUE ESTÉ LIBRE
            if (libreOcomprada[i] == 5) {
                System.out.println("EL PRECIO DE ESTA CALLE ES DE: " + precioCalles[i] + " €. PULSA 1 PARA COMPRAR.");
                //COMPROBAMOS QUE TENGA DINERO PARA ADQUIRIRLA Y ASIGNA CONTADOR PARA SABER QUE JUGADOR LA HA COMPRADO.
                if (dineroJugador[contador] >= precioCalles[i]) {
                    libreOcomprada[i] = in.nextInt();
                    libreOcomprada[i] = contador;
                    dineroJugador[contador] -= precioCalles[i];
                    System.out.println("TE QUEDAN : " + dineroJugador[contador] + "€");
                    System.out.println("");
                }
                else 
                    System.out.println("LO SIENTO. NO TIENES SUFICIENTE DINERO PARA AFRONTAR EL PAGO DE ESTA PROPIEDAD");
                }
            else
                System.out.println("LO SIENTO. ESTA CALLE PERTENECE YA A OTRO JUGADOR");
        }
    }
    
    //ASIGNA EL PAGO DEL ALGUILER EN FUNCION DE LA CALLE DONDE ESTÉ POSICIONADO EL JUGADOR
    public static void generarAlquiler(double[] precioCalles) {
        for (int i = 0; i < calles.length; i++) {
            precioAlquiler[i] = (precioCalles[i] * 0.22);
        }
    }
    
    //COBRA EL ALQUILER CORRESPONDIENTE DE LA CALLE AL JUGADOR QUE HAYA CAIDO EN ELLA.
    public static void cobrarAlquiler(int[] libreOcomprada) {
        //POSICIONAMOS AL JUGADOR EN LA CALLE SEGUN SU TURNO, QUE ESTÁ CONTROLADO POR CONTADOR
        for (int i = posicionJugador[contador]; i < (posicionJugador[contador]) + 1 ; i++) {
            //COMPARAMOS EL VALOR DEL ARRAY CON EL DEL CONTADOR PARA SABER QUE CALLE PERTENECE A QUE JUGADOR
            switch (libreOcomprada[i]) {  //MAX NUM JUGADORES ES 4.
                case 0:
                    //METEMOS UN IF PARA NO COBRANOS ALQUILER A NOSOTROS MISMOS CUANDO CAIGAMOS EN NUESTRAS PROPIEDADES.
                    //DE ESTO ME LLEVO 4H DAR CON LA SOLUCIÓN :(
                    if (contador != 0){
                        System.out.println("");
                        System.out.println("ESTA CALLE PERTENECE Al JUGADOR: " + nombreJugador[0]);
                        System.out.println("HAS DE PAGARLE " + precioAlquiler[posicionJugador[contador]] + " € EN CONCEPTO DE ALQUIER.");
                        dineroJugador[contador] -= precioAlquiler[posicionJugador[contador]];
                        dineroJugador[0] += precioAlquiler[posicionJugador[contador]];
                    }
                    break;
                case 1:
                    if (contador !=1 ){
                        System.out.println("");
                        System.out.println("ESTA CALLE PERTENECE Al JUGADOR: " + nombreJugador[1]);
                        System.out.println("HAS DE PAGARLE " + precioAlquiler[posicionJugador[contador]] + " € EN CONCEPTO DE ALQUIER.");
                        dineroJugador[contador] -= precioAlquiler[posicionJugador[contador]];
                        dineroJugador[1] += precioAlquiler[posicionJugador[contador]];
                    }
                    break;
                case 2:
                   if (contador != 2){
                        System.out.println("");
                        System.out.println("ESTA CALLE PERTENECE Al JUGADOR: " + nombreJugador[2]);
                        System.out.println("HAS DE PAGARLE " + precioAlquiler[posicionJugador[contador]] + " € EN CONCEPTO DE ALQUIER.");
                        dineroJugador[contador] -= precioAlquiler[posicionJugador[contador]];
                        dineroJugador[2] += precioAlquiler[posicionJugador[contador]];
                   }
                    break;
                case 3:
                    if (contador !=3 ){
                        System.out.println("");
                        System.out.println("ESTA CALLE PERTENECE Al JUGADOR: " + nombreJugador[3]);
                        System.out.println("HAS DE PAGARLE " + precioAlquiler[posicionJugador[contador]] + " € EN CONCEPTO DE ALQUIER.");
                        dineroJugador[contador] -= precioAlquiler[posicionJugador[contador]];
                        dineroJugador[3] += precioAlquiler[posicionJugador[contador]];
                    }
                    break;
            }
        } 
    }
    
    //AVERIGUA EL PRECIO DE LAS CASES EN FUNCION DEL VALOR DE LAS CALLES
    public static void precioCasas(double[] precioCalles) {
        for (int i = 0; i < calles.length; i++) {
            precioCasas[i] = ((precioCalles[i] * 0.5));
        }
    }
    
    //LLAMA A libreOcomprada PARA SABER SI ESTÁ LIBRE LA CALLE Y EN CASO DE QUE ASÍ SEA ASIGNA EL VALOR DEL CONTADOR A LA PROPIEDAD
    //DEL JUGADOR COMPRADA Y ASÍ RELACIONARLAS.
    public static void comprarCasas(int[] libreOcomprada){
        Scanner in = new Scanner(System.in);
        //POSICIONAMOS AL JUGADOR Y CONTROLAMOS EL BUCLE PARA QUE SOLO DEJE HACER 1 ITERACIÓN
        for (int i = posicionJugador[contador]; i < (posicionJugador[contador] + 1) ; i++) {
            
            if (libreOcomprada[i] == contador) {
                System.out.println("1 CASA EN ESTA CALLE CUESTA: " + precioCasas[posicionJugador[contador]] + " €");
                
                if (dineroJugador[contador] >= precioCasas[posicionJugador[contador]]) {
                    System.out.println("PULSA 1 PARA COMPRAR");
                    comprarCasas[posicionJugador[contador]]=in.nextInt();
                    dineroJugador[contador]-=precioCasas[posicionJugador[contador]];
                    comprarCasas[posicionJugador[contador]]=contador;
                }
                else
                    System.out.println("LO SIENTO. NO TIENES DINERO SUFICIENTE PARA ADQUIRIR UNA CASA.");
                
            }
            else
                System.out.println("LO SIENTO. ESTA CALLE NO ES TUYA. COMPRALA PRIMERO.");
            
        }
    }
    
    public static void cobrarCasas(int[] comprarCasas) {
        for (int i = posicionJugador[contador]; i < (posicionJugador[contador]) + 1 ; i++) {
            switch (comprarCasas[i]) {
                case 1:
                    if (contador != 1){
                    System.out.println("");
                    System.out.println("ESTA CALLE TIENE EDIFICADA " + comprarCasas[posicionJugador[contador]] + " CASA");
                    System.out.println("HAS DE PAGARLE " + precioCasas[posicionJugador[contador]] + " € EN CONCEPTO DE ALQUIER.");
                    dineroJugador[contador] -= precioAlquiler[posicionJugador[contador]];
                    dineroJugador[0] += precioAlquiler[posicionJugador[contador]];
                    }
                    break;
                }
            } 

        }
    

    

   

    public static void main(String[] args) {
        menu(); 
        if(numJugadores <=4 ){
            dineroInicial();
            generarAlquiler(precioCalles);
            precioCasas(precioCalles);
            //PONEMOS A "CINCO" EL TABLERO PARA INDICAR QUE TODAS LAS PROPIEDADES ESTAN LISTAS PARA SU VENTA.
            for (int i = 0; i < libreOcomprada.length; i++) {
                libreOcomprada[i] = 5;
            }
        
            do {
                turnoJugador();
            } while (contador<5);
        }
    }
}
