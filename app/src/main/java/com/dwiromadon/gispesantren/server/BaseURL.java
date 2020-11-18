package com.dwiromadon.gispesantren.server;

public class BaseURL {

//    public static String baseUrl = "http://192.168.18.9:5000/";
//    public static String baseUrl = "http://172.31.0.59:5000/";
    public static String baseUrl = "http://192.168.18.9:5000/";
//    public static String baseUrl = "http://172.31.0.86:5000/";

    public static String inputPesantren       = baseUrl + "pesantren/input";
    public static String updatePesantren      = baseUrl + "pesantren/ubah/";
    public static String inputLogo            = baseUrl + "pesantren/inputlogopesantren/";
    public static String getJarak             = baseUrl + "pesantren/getjarak/";
    public static String ubahDataPesantren    = baseUrl + "pesantren/ubahdata/";
    public static String ubahGambar           = baseUrl + "pesantren/ubahgambar/";
    public static String hapusPesantren       = baseUrl + "pesantren/hapusdata/";

    //input history
    public static String inputHistory           = baseUrl + "history/input";
    public static String getHistory             = baseUrl + "history/getjarak";
    public static String hapusHistory           = baseUrl + "history/hapus/";
}