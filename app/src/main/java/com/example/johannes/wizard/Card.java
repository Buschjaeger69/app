package com.example.johannes.wizard;

import java.util.Random;

/**
 * Created by Johannes on 29.10.2017.
 */

public class Card {

    static int gruppe = 0;
    static int farbgruppe = 0;
    static int kartenwert = 0;
    static int kartennr = 0;

    static int[] kartenarray = new int[60];
    static Random r = new Random();
    static int rand;
    static int tmp;
    static int hoechsteKarte = 0;
    static int gezogeneKarte = 0;
    static int platznummer = 0;
    static int karte1 = 0;
    static int karte2 = 0;
    static String ausgabe = "";
    static int random = 0;
    static int nr = 0;
    static boolean kannGespieltWerden = false;
    static int gleicheFarbkarten = 0;


    public static void erzeugen() {
        for (int i = 0; i < 60; i++) {
            kartenarray[i] = i + 1;
        }

    }


    public static void mischen() {

        for (int i = kartenarray.length - 1; i > 0; i--) {
            random = r.nextInt(i + 1);
            tmp = kartenarray[random];
            kartenarray[random] = kartenarray[i];
            kartenarray[i] = tmp;
        }
        gezogeneKarte = 0;
    }


    public static void kartenzuordnung(int kartennr) {
        if (kartennr <= 4) {
            gruppe = 3;
            kartenwert = 14;
            farbgruppe = 0;
        } else {
            if (kartennr <= 8) {
                gruppe = 1;
                kartenwert = 0;
                farbgruppe = 0;
            } else {
                if (kartennr <= 21) {
                    gruppe = 2;
                    kartenwert = kartennr - 8;
                    farbgruppe = 1;
                } else {
                    if (kartennr <= 34) {
                        gruppe = 2;
                        kartenwert = kartennr - 21;
                        farbgruppe = 2;
                    } else {
                        if (kartennr <= 47) {
                            gruppe = 2;
                            kartenwert = kartennr - 34;
                            farbgruppe = 3;
                        } else {
                            gruppe = 2;
                            kartenwert = kartennr - 47;
                            farbgruppe = 4;
                        }
                    }

                }
            }
        }
    }

    public static void farbkartenzurueck(){
        gleicheFarbkarten=0;
    }

    public static boolean Farbzugabe(int mittlereKarte, int kartenhand) {

        kartenzuordnung(mittlereKarte);
        int tempfarbgruppe1 = getFarbgruppe();
        int tempgruppe1 = getGruppe();
        kartenzuordnung(kartenhand);
        int tempfarbgruppe2 = getFarbgruppe();
        int tempgruppe2 = getGruppe();
        if (tempgruppe1 == 1 || tempgruppe1 == 3) {
            kannGespieltWerden = true;
        } else {
            if (tempgruppe2 == 2) {

                if (tempfarbgruppe1 == tempfarbgruppe2) {
                    kannGespieltWerden = true;
                    gleicheFarbkarten=1;

                } else {
                    //Ausnahme: keine Karten derselben Farbe
                    kannGespieltWerden = false;

                }
            } else {
                kannGespieltWerden = true;
            }

        }

        return kannGespieltWerden;
    }

    public static int getGruppe() {
        return gruppe;
    }

    public static int getKartenwert() {
        return kartenwert;
    }

    public static int getFarbzugabe() {
        return gleicheFarbkarten;
    }

    public static int getFarbgruppe() {
        return farbgruppe;
    }

    public static int getKartennr() {
        return kartennr;
    }




    public static int hoeher(int mittlereKarte, int gelegteKarte) {
        kartenzuordnung(mittlereKarte);
        int gruppe1 = gruppe;
        int kartenwert1 = kartenwert;
        int farbgruppe1 = farbgruppe;

        kartenzuordnung(gelegteKarte);
        int gruppe2 = gruppe;
        int kartenwert2 = kartenwert;
        int farbgruppe2 = farbgruppe;


        if (gruppe1 < 3) {
            if (gruppe1 == 1) {
                if (gruppe2 == 1) {
                    hoechsteKarte = 0;
                } else {
                    hoechsteKarte = 1;
                }
            } else {

                if (gruppe2 == 3) {
                    hoechsteKarte = 1;
                } else {
                    if (gruppe2 == 1) {
                        hoechsteKarte = 0;
                    } else {

                        if (farbgruppe2 == farbgruppe1) {
                            if (kartenwert2 > kartenwert1) {
                                hoechsteKarte = 1;
                            } else {
                                hoechsteKarte = 0;
                            }
                        } else {
                            hoechsteKarte = 0;
                        }
                    }
                }
            }

        } else {
            hoechsteKarte = 0;
        }
        return hoechsteKarte;
    }


    public static int karteziehen() {
        nr = kartenarray[gezogeneKarte];
        gezogeneKarte = gezogeneKarte + 1;
        return nr;
    }





}


