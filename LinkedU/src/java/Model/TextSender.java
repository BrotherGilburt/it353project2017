/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Keegan
 */
public class TextSender {

    public static java.util.List<java.lang.String> getCarriers() {

        java.util.List<java.lang.String> tempList = getCarriers_1();
        return tempList;
    }

    public static void sendSMS(java.lang.String provider, java.lang.String number, java.lang.String message) {
        sendSMS_1(provider, number, message);
    }

    private static java.util.List<java.lang.String> getCarriers_1() {
        edu.ilstu.it.TextSenderService service = new edu.ilstu.it.TextSenderService();
        edu.ilstu.it.TextSender port = service.getTextSenderPort();
        return port.getCarriers();
    }

    private static void sendSMS_1(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) {
        edu.ilstu.it.TextSenderService service = new edu.ilstu.it.TextSenderService();
        edu.ilstu.it.TextSender port = service.getTextSenderPort();
        port.sendSMS(arg0, arg1, arg2);
    }

}
